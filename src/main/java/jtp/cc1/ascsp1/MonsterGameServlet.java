package jtp.cc1.ascsp1;

import java.util.logging.Logger;
import java.io.Serializable;
import java.io.IOException;
import javax.servlet.http.*;

/**
 * MonsterGameServlet
 * Based on Julian's fish and chicken restaurant
 * Receives i/o from tier1 html consolesim via ajax
 * uses session to store running game
 */
public class MonsterGameServlet extends HttpServlet {
  
  // static are class variables and are not cloned in objects - eg only one logger is used by all instances
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  private static final Logger log = Logger.getLogger(MonsterGameServlet.class.getName());
  private static int reuseCount = 0; // to prove server class reuse behaviour
  
  private String scene;
  private String screen;
  private String method;
  private Game theGame;

  /** to save complex data in the session class must implement Serializable or runtime error happens
   */
  private static class Game implements Serializable {
    public String s = "newgame: ";
    public int i = 0;
    public Boolean isOver() {
      return (i >= 3);
    }
    public Boolean isWon() {
      return (s.contains("W"));
    }
    public String drawGame() {
      return "<br>|------" + s + " " + i + "------|<br>Enter NSEWM: ";
    }
    public String drawGameover() {
      return "<br>LOOSER!!!!<br>Press any key to continue: ";
    }
    public String drawGamewon() {
      return "<br>WINNER!!!!<br>Press any key to continue: ";
    }
    public void handle(String input) {
      switch (input) {
        case "N": case "S": case "E": case "W":
          s += input + ": ";
          i ++;
          if (isWon()) {
            scene = "gamewonscene";
            screen = drawGamewon();
            method = "read";
          } else if (isOver()) {
            scene = "gameoverscene";
            screen = drawGameover();
            method = "read";
          } else {
            scene = "gamescene";
            screen = drawGame();
            method = "read";
          }
          break;
        case "M":
          scene = "menuscene";
          screen = drawMenu();
          method = "read";
          break;
        default:
          scene = "gamescene";
          screen = drawGame();
          method = "read";
          break;
      }
    }
    public Game() {
        scene = "gamescene";
        screen = drawGame();
        method = "read";
    }
  }


  private static String json(String other) {
    return "{ \"screen\": \"" + screen + "\", \"method\": \"" + method + "\", \"other\": \"" + other + "\" }";
  }
  
  
  private static String drawMenu() {
    return "<br>1. New game<br>2. Save game<br>etc...<br>Enter choice: ";
  }
  
  
  private static String drawFilesave() {
    return "<br>Enter filename to save: ";
  }
  
  
  private static String drawFilesavesuccess() {
    return "<br>Success<br>Press any key to continue: ";
  }
  
  
  private static String drawFilesavefail() {
    return "<br>Fail<br>Press any key to continue: ";
  }
  
  
  private static void handleMenu(String input) {
    log.warning("input:"+input);
    switch (input) {
      case "1":
        log.warning("case '1'");
        theGame = new Game();
        break;
      case "2":
        log.warning("case '2'");
        scene = "filesavescene";
        screen = drawFilesave();
        method = "readln";
        break;
      default:
        log.warning("default");
        scene = "menuscene";
        screen = drawMenu();
        method = "read";
        break;
    }
  }
    

  private static void handleGamewon(String input) {
    scene = "menuscene";
    screen = drawMenu();
    method = "read";
  }
    

  private static void handleGameover(String input) {
    scene = "menuscene";
    screen = drawMenu();
    method = "read";
  }
    

  private static void handleFilesave(String input) {
    if (input.contains(" ")) {
      // bad file name
      scene = "filesavefailscene";
      screen = drawFilesavefail();
      method = "readln";
    } else {
      scene = "filesavesuccessscene";
      screen = drawFilesavesuccess();
      method = "read";
    }
  }
    

  private static void handleFilesavesuccess(String input) {
    scene = "menuscene";
    screen = drawMenu();
    method = "read";
  }
    

  private static void handleFilesavefail(String input) {
    scene = "filesavescene";
    screen = drawFilesave();
    method = "read";
  }
    

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    reuseCount ++;
    // start new session
    HttpSession mySession = req.getSession(true);
    // init the gameapp state
    mySession.setAttribute("scene", "menuscene");
    screen = drawMenu();
    method = "read";
    // hand back to tier1 to present the initial user state and service access (user can enter his data)
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json("reuseCount:"+reuseCount+", sid:"+mySession.getId()));
  }


  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    // resume from where we left off
    HttpSession mySession = req.getSession(false);
    String input = req.getParameter("input");
    // proceed with this use event
    screen = "<br>safetyscreen";
    method = "safetymethod";
    scene = (String)mySession.getAttribute("scene");
    Game theGame = (Game)mySession.getAttribute("thegame"); // created by menu choice and save here below
    switch (scene) {
      case "menuscene":
        handleMenu(input);
        break;
      case "gamescene":
        theGame.handle(input);
        break;
      case "gamewonscene":
        handleGamewon(input);
        break;
      case "gameoverscene":
        handleGameover(input);
        break;
      case "filesavescene":
        handleFilesave(input);
        break;
      case "filesavesuccessscene":
        handleFilesavesuccess(input);
        break;
      case "filesavefailscene":
        handleFilesavefail(input);
        break;
    }
    mySession.setAttribute("scene", scene);
    mySession.setAttribute("thegame", theGame); // may be null
    // hand back to tier1 to present the new user state
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json("scene:"+scene+", thegame:"+theGame+", input:"+input));
  }

}
