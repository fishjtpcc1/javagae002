package jtp.cc1.ascsp1;

import java.util.logging.Logger;
import java.io.IOException;
import javax.servlet.http.*;

interface SceneObject {
  public String method(Game g);
  public String draw(Game g);
  public SceneObject whereToNext(Game g, String input);
}
    
/**
 * MonsterGameServlet
 * Based on Julian's fish and chicken restaurant
 * Receives i/o from tier1 html consolesim via ajax
 * uses session to store running game
 * user view state model:
 *  start: menu
 *  menu:
 *    1: new game
 *    2: filesave
 *    invalid: oops: menu
 *  game:
 *    paused: menu
 *    won: win
 *    lost: lost
 *    invalid: oops: game
 *  filesave:
 *    success: menu
 *    fail: opps: filesave
 *  win: menu
 *  lost: menu
 */
public class MonsterGameServlet extends HttpServlet {
  
  // statics are class variables and are not cloned in objects - eg only one logger is used by all instances
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  private static final Logger log = Logger.getLogger(MonsterGameServlet.class.getName());
  private static int reuseCount = 0; // to prove server class reuse behaviour
  
  private static String json(String screen, String method, String other) {
    return "{ \"screen\": \"" + screen + "\", \"method\": \"" + method + "\", \"other\": \"" + other + "\" }";
  }
  
  @Override // to help me prevent stupid polymorphic mistakes, the @Override annotation is used here to assert to compiler that this method is present in the superclass
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    reuseCount ++;
    // start new session
    HttpSession mySession = req.getSession(true);
    // init the gameapp state
    // String scene = "menuscene";
    Game g = new Game();
    SceneObject here = new MenuScene();
    // save state
    // mySession.setAttribute("scene", scene);
    mySession.setAttribute("here", here);
    mySession.setAttribute("thegame", g);
    // hand back to tier1 to present the initial user state and service access (user can enter his data)
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json(here.draw(g), here.method(g), "reuseCount:"+reuseCount+", sid:"+mySession.getId()));
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    // resume from where we left off
    HttpSession mySession = req.getSession(false);
    //String back = (String)mySession.getAttribute("back");
    //String scene = (String)mySession.getAttribute("scene");
    SceneObject here = (SceneObject)mySession.getAttribute("here");
    //SceneObject so = so(back,scene); // casting as a generic interface grants permission for calling decendents' stuff
    Game g = (Game)mySession.getAttribute("thegame"); // created by menu choice and saved here below
    // proceed with this use event
    String input = req.getParameter("input");
    //back = scene;
    here = here.whereToNext(g, input); // strictly controlled polymorphism in action
    //so = so(back,scene); // so is needed for final msg handling
    // save state
    //mySession.setAttribute("back", back);
    //mySession.setAttribute("scene", scene);
    mySession.setAttribute("here", here);
    mySession.setAttribute("thegame", g);
    // hand back to tier1 to present the new user state
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json(here.draw(g), here.method(g), "here:"+here+", thegame:"+g+", input:"+input));
  }

}
