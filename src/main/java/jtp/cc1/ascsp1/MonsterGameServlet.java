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
  private static String scene;
  private static String screen;
  private static String method;
  private static Game theGame;


  private static class Game {
    public String s = "newgame: ";
    public int i = 0;
    public Boolean isOver() {
      return (i >= 3);
    }
  }


  private static String json() {
    return "{ \"screen\": \"" + screen + "\", \"method\": \"" + method + "\" }";
  }
  
  
  private static String drawMenu() {
    return "<br>1. New game<br>etc...<br>Enter choice: ";
  }
  
  
  private static String drawGame() {
    return "<br>|------" + theGame.s + " " + theGame.i + " " + theGame.isOver() + "------|<br>Enter choice: ";
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
      default:
        log.warning("default");
        scene = "menuscene";
        screen = drawMenu();
        method = "read";
        break;
    }
  }
    

  private static void handleGame(String input) {
    log.warning("input:"+input);
    switch (input) {
      case "N": case "S": case "E": case "W":
        log.warning("case 'NSEW'");
        theGame.s += input + ": ";
        theGame.i ++;
        if (theGame.isOver()) {
          scene = "menuscene";
          screen = drawMenu();
          method = "read";
        } else {
          scene = "gamescene";
          screen = drawGame();
          method = "read";
        }
        break;
      case "M":
        log.warning("M");
        scene = "menuscene";
        screen = drawMenu();
        method = "read";
        break;
      default:
        log.warning("default");
        scene = "gamescene";
        screen = drawGame();
        method = "read";
        break;
    }
  }
    

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    // start new session
    HttpSession mySession = req.getSession(true);
    mySession.setAttribute("scene", "menuscene");
    mySession.setAttribute("thegame", new Game());
    screen = drawMenu();
    method = "read";
    // hand back to tier1 to present the initial user state
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json());
  }


  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    // resume from where we left off
    HttpSession mySession = req.getSession(false);
    String input = req.getParameter("input");
    // proceed with this use event
    screen = "<br>safetyscreen";
    method = "safetymethod";
    //MenuScene scene = new MenuScene();
    scene = (String)mySession.getAttribute("scene");
    Game theGame = (Game)mySession.getAttribute("thegame");
    log.warning("scene:"+scene+", thegame:"+theGame+", theGame.s:"+theGame.s+", input:"+input);
    switch (scene) {
      case "menuscene":
        handleMenu(input);
        break;
      case "gamescene":
        handleGame(input);
        break;
    }
    //scene.handle(input);
    //screen += "<br>"+debug;
    mySession.setAttribute("scene", scene);
    mySession.setAttribute("thegame", theGame);
    // hand back to tier1 to present the new user state
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json());
  }

}
