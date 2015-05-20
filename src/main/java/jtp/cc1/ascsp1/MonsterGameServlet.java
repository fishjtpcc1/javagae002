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
  private static String scene;
  private static String screen;
  private static String method;
  private static Game theGame;

  private String objectData;// to prove server running object reuse behaviour
  
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
  }


  private static String json(String other) {
    return "{ \"screen\": \"" + screen + "\", \"method\": \"" + method + "\", \"other\": \"" + other + "\" }";
  }
  
  
  private static String drawMenu() {
    return "<br>1. New game<br>2. Save game<br>etc...<br>Enter choice: ";
  }
  
  
  private static String drawGame() {
    return "<br>|------" + theGame.s + " " + theGame.i + "------|<br>Enter NSEWM: ";
  }
  
  
  private static String drawGameover() {
    return "<br>LOOSER!!!!<br>Press any key to continue: ";
  }
  
  
  private static String drawGamewon() {
    return "<br>WINNER!!!!<br>Press any key to continue: ";
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
        scene = "gamescene";
        screen = drawGame();
        method = "read";
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
    

  private static void handleGame(String input) {
    switch (input) {
      case "N": case "S": case "E": case "W":
        theGame.s += input + ": ";
        theGame.i ++;
        if (theGame.isWon()) {
          scene = "gamewonscene";
          screen = drawGamewon();
          method = "read";
        } else if (theGame.isOver()) {
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
    mySession.setAttribute("scene", "menuscene");
    mySession.setAttribute("thegame", new Game());
    screen = drawMenu();
    method = "read";
    objectData = "last used by " + mySession.getId() + " @ " + reuseCount;
    // hand back to tier1 to present the initial user state
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json("reuseCount:"+reuseCount+", sid:"+mySession.getId()+", objectData:"+objectData));
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
    Game theGame = (Game)mySession.getAttribute("thegame");
    log.warning("scene:"+scene+", thegame:"+theGame+", input:"+input);
    switch (scene) {
      case "menuscene":
        handleMenu(input);
        break;
      case "gamescene":
        handleGame(input);
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
    mySession.setAttribute("thegame", theGame);
    // hand back to tier1 to present the new user state
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json(""));
  }

}
