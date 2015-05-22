package jtp.cc1.ascsp1;

import java.util.logging.Logger;
import java.io.IOException;
import javax.servlet.http.*;

interface SceneObject {
  public String method();
  public String draw();
  public String whereToNext(String input);
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
  
  private SceneObject so(String b, String s) {
    switch (s) {
      case "oops":
        return new OopsScene(b);
      case "gamescene":
        return new GameScene();
      case "filerscene":
        return new FilerScene();
      default:
        return new MenuScene();
    }
  }

  private class OopsScene implements SceneObject {
    private String back;
    public String method() {
      return "read";
    }
    public String draw() {
      return "<br>Que!? ";
    }
    public String whereToNext(String input) {
      return back;
    }
    public OopsScene(String b) {
      back = b;
    }
  }
  
  private class MenuScene implements SceneObject {
    public String method() {
      return "read";
    }
    public String draw() {
      return "<br>1. New game<br>2. Save game<br>etc...<br>Enter choice: ";
    }
    public String whereToNext(String input) {
      switch (input) {
        case "1":
          g = new Game();
          return "gamescene";
        case "2":
          return "filerscene";
        default:
          return "oops";
      }
    }
  }
  
  private class GameScene implements SceneObject {
    public String method() {
      return g.method;
    }
    public String draw() {
      if (g.isWon()) {
        return "<br>WINNER!!!!<br>Press any key to continue: ";
      } else if (g.isLost()) {
        return "<br>LOOSER!!!!<br>Press any key to continue: ";
      } else {
        return "<br>|------" + g.data + "------|<br>Enter NSEWP: ";
      }
    }
    public String whereToNext(String input) {
      switch (g.newState(input)) {
        case "ispaused": case "isover":
          return "menuscene";
        case "isinplay":
          return "gamescene";
        default:
          return "oops";
      }
    }
  }

  private class FilerScene implements SceneObject {
    public String method() {
      return "readln";
    }
    public String draw() {
      return "<br>--my saved files--<br>Enter filename: ";
    }
    public String whereToNext(String input) {
      String newFilerState;
      if (input.contains(" ")) {
        newFilerState = "fail";
      } else {
        newFilerState = "success";
      }
      switch (newFilerState) {
        case "success":
          return "menuscene";
        default:
          return "oops";
      }
    }
  }
  
  // dynamic object stuff
  private Game g;

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    reuseCount ++;
    // start new session
    HttpSession mySession = req.getSession(true);
    // init the gameapp state
    String scene = "menuscene";
    SceneObject so = so(null,scene);
    g = new Game();
    // save state
    mySession.setAttribute("scene", scene);
    mySession.setAttribute("thegame", g);
    // hand back to tier1 to present the initial user state and service access (user can enter his data)
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json(so.draw(), so.method(), "reuseCount:"+reuseCount+", sid:"+mySession.getId()));
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    // resume from where we left off
    HttpSession mySession = req.getSession(false);
    String back = (String)mySession.getAttribute("back");
    String scene = (String)mySession.getAttribute("scene");
    SceneObject so = so(back,scene);
    g = (Game)mySession.getAttribute("thegame"); // created by menu choice and saved here below
    // proceed with this use event
    String input = req.getParameter("input");
    back = scene;
    scene = so.whereToNext(input);
    so = so(back,scene);
    // save state
    mySession.setAttribute("back", back);
    mySession.setAttribute("scene", scene);
    mySession.setAttribute("thegame", g);
    // hand back to tier1 to present the new user state
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json(so.draw(), so.method(), "scene:"+scene+", thegame:"+g+", input:"+input));
  }

}
