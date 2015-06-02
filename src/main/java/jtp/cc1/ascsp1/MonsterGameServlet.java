package jtp.cc1.ascsp1;

import java.util.logging.Logger;
import java.io.IOException;
import javax.servlet.http.*;

interface SceneObject {
  public String method(Game g);
  public String draw(Game g, Game[] savedGames);
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
    Game g = new Game("fred.1");
    Game[] savedGames = new Game[3];
    int currentGameIndex = 0;
    savedGames[currentGameIndex] = g;
    SceneObject here = new MenuScene();
    // save state
    mySession.setAttribute("here", here);
    mySession.setAttribute("savedGames", savedGames);
    mySession.setAttribute("currentGameIndex", currentGameIndex);
    // hand back to tier1 to present the initial user state and service access (user can enter his data)
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json(here.draw(g, savedGames), here.method(g), "reuseCount:"+reuseCount+", sid:"+mySession.getId()));
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    // resume from where we left off
    HttpSession mySession = req.getSession(false);
    SceneObject here = (SceneObject)mySession.getAttribute("here"); // know: casting IS required tho implied in assignment
    Game[] savedGames = (Game[])mySession.getAttribute("savedGames");
    int currentGameIndex = (int)mySession.getAttribute("currentGameIndex");
    Game g = savedGames[currentGameIndex];
    // proceed with this use event
    String input = req.getParameter("input");
    here = here.whereToNext(g, input); // strictly controlled polymorphism in action
    // save state
    mySession.setAttribute("here", here);
    mySession.setAttribute("savedGames", savedGames);
    mySession.setAttribute("currentGameIndex", currentGameIndex);
    // hand back to tier1 to present the new user state
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json(here.draw(g,savedGames), here.method(g), "here:"+here+", thegame:"+g+", input:"+input));
  }

}
