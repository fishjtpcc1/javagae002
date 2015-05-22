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
 */
public class MonsterGameServlet extends HttpServlet {
  
  // statics are class variables and are not cloned in objects - eg only one logger is used by all instances
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  private static final Logger log = Logger.getLogger(MonsterGameServlet.class.getName());
  private static int reuseCount = 0; // to prove server class reuse behaviour
  
  private static String json(String screen, String method, String other) {
    return "{ \"screen\": \"" + screen + "\", \"method\": \"" + method + "\", \"other\": \"" + other + "\" }";
  }
  
  public static String drawMenu() {
    return "<br>1. New game<br>2. Save game<br>etc...<br>Enter choice: ";
  }
  
  public static String drawGameover() {
    return "<br>LOOSER!!!!<br>Press any key to continue: ";
  }

  public static String drawGamewon() {
    return "<br>WINNER!!!!<br>Press any key to continue: ";
  }

  private static String drawFilesave() {
    return "<br>Enter filename to save: ";
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
          return "gamescene";
        case "2":
          return "filesavescene";
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
      return "<br>|------" + g.data + "------|<br>Enter NSEWP: ";
    }
    public String whereToNext(String input) {
      switch (g.newState(input)) {
        case "iswon":
          return "gamewonscene";
        case "isover":
          return "gameoverscene";
        case "ispaused":
          return "menuscene";
        case "isinplay":
          return "gamescene";
        default:
          return "oops";
      }
    }
  }

  // dynamic object stuff
  private String scene;
  private String screen;
  private String method;
  private SceneObject so;
  private Game g;

  private String updateFilerState(String input) {
    if (input.contains(" ")) {
      return "fail";
    } else {
      return "success";
    }
  }
    
  private void routeAndDo(String input) {
    switch (scene) {
      case "oops":
        scene = so.whereToNext(input);
        switch (scene) {
          case "gamescene":
            so = new GameScene();
            screen = so.draw();
            method = so.method();
            break;
          case "filesavescene":
            screen = drawFilesave();
            method = "readln";
            break;
          case "menuscene":
            so = new MenuScene();
            screen = so.draw();
            method = so.method();
            break;
        }
        break;
      case "menuscene":
        scene = so.whereToNext(input);
        switch (scene) {
          case "gamescene":
            g = new Game();
            so = new GameScene();
            screen = so.draw();
            method = so.method();
            break;
          case "filesavescene":
            screen = drawFilesave();
            method = "readln";
            break;
          default:
            so = new OopsScene("menuscene");
            screen = so.draw();
            method = so.method();
            break;
        }
        break;
      case "gamescene":
        scene = so.whereToNext(input);
        switch (scene) {
          case "gamewonscene":
            screen = drawGamewon();
            method = "read";
            break;
          case "gameoverscene":
            screen = drawGameover();
            method = "read";
            break;
          case "menuscene":
            so = new MenuScene();
            screen = so.draw();
            method = so.method();
            break;
          case "gamescene":
            screen = so.draw();
            method = so.method();
            break;
          default:
            so = new OopsScene(scene);
            screen = so.draw();
            method = so.method();
            break;
        }
        break;
      case "gamewonscene": case "gameoverscene":
        scene = "menuscene";
        so = new MenuScene();
        screen = so.draw();
        method = so.method();
        break;
     case "filesavescene":
        switch (updateFilerState(input)) {
          case "success":
            so = new MenuScene();
            screen = so.draw();
            method = so.method();
            break;
          default:
            so = new OopsScene(scene);
            screen = so.draw();
            method = so.method();
            break;
      }
      break;
    }
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    reuseCount ++;
    // start new session
    HttpSession mySession = req.getSession(true);
    // init the gameapp state
    scene = "menuscene";
    so = new MenuScene();
    g = new Game();
    screen = so.draw();
    method = so.method();
    mySession.setAttribute("scene", scene);
    mySession.setAttribute("thegame", g);
    // hand back to tier1 to present the initial user state and service access (user can enter his data)
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json(screen, method, "reuseCount:"+reuseCount+", sid:"+mySession.getId()));
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    // resume from where we left off
    HttpSession mySession = req.getSession(false);
    String input = req.getParameter("input");
    // proceed with this use event
    scene = (String)mySession.getAttribute("scene");
    g = (Game)mySession.getAttribute("thegame"); // created by menu choice and saved here below
    // do input and route states to new so
    routeAndDo(input); // sets so
    // save state
    mySession.setAttribute("scene", scene);
    mySession.setAttribute("thegame", g);
    // hand back to tier1 to present the new user state
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json(screen, method, "scene:"+scene+", thegame:"+g+", input:"+input));
  }

}
