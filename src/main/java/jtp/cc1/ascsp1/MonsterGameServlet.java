package jtp.cc1.ascsp1;

import java.util.logging.Logger;
import java.io.IOException;
import javax.servlet.http.*;

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
  
  public static String drawGame(Game g) {
    return "<br>|------" + g.data + "------|<br>Enter NSEWP: ";
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
  
  private static String drawFilesavesuccess() {
    return "<br>Success<br>Press any key to continue: ";
  }
  
  private static String drawFilesavefail() {
    return "<br>Fail<br>Press any key to continue: ";
  }
  
  // dynamic object stuff
  static String scene;
  private String screen;
  private String method;

  private Game theGame;

  private void handleGamewon(String input) {
    scene = "menuscene";
    screen = drawMenu();
    method = "read";
  }
    
  private void handleGameover(String input) {
    scene = "menuscene";
    screen = drawMenu();
    method = "read";
  }
    
  private void handleFilesave(String input) {
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
    
  private void handleFilesavesuccess(String input) {
    scene = "menuscene";
    screen = drawMenu();
    method = "read";
  }
    
  private void handleFilesavefail(String input) {
    scene = "filesavescene";
    screen = drawFilesave();
    method = "read";
  }
    
  private void handleMenu(String input) {
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
    resp.getWriter().println(MonsterGameServlet.json(screen, method, "reuseCount:"+reuseCount+", sid:"+mySession.getId()));
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
    theGame = (Game)mySession.getAttribute("thegame"); // created by menu choice and saved here below
    // do input and route states
    switch (scene) {
      case "menuscene":
        switch (input) {
          case "1":
            theGame = new Game();
            scene = "gamescene";
            screen = theGame.drawGame();
            method = "read";
            break;
          case "2":
            scene = "filesavescene";
            screen = drawFilesave();
            method = "readln";
            break;
          default:
            scene = "menuscene";
            screen = drawMenu();
            method = "read";
            break;
        }
        break;
      case "gamescene":
        switch (theGame.updateState(input));
        case "iswon":
          scene = "gamewonscene";
          screen = drawGamewon();
          method = "read";
        case "isover":
          scene = "gameoverscene";
          screen = drawGameover();
          method = "read";
        case "ispaused":
          scene = "menuscene";
          screen = drawMenu();
          method = "read";
        case "isinplay":
          scene = "gamescene";
          screen = drawGame(theGame);
          method = "read";
        }
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
    resp.getWriter().println(MonsterGameServlet.json(screen, method, "scene:"+scene+", thegame:"+theGame+", input:"+input));
  }

}
