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

  private static String json() {
    return "{ \"screen\": \"" + screen + "\", \"method\": \"" + method + "\" }";
  }
  
  
  private static String drawMenu() {
    return "<br>1. New game<br>etc...<br>Enter choice: ";
  }
  
  
  private static String drawGame() {
    return "<br>|------game screen------|<br>Enter choice: ";
  }
  
  
  private static void handleMenu(String input) {
    log.warning("input:"+input);
    switch (input) {
      case "1":
        log.warning("case '1'");
        scene = "gamescene";
        screen = drawGame();
        method = "read";
      default:
        log.warning("default");
        scene = "menuscene";
        screen = drawMenu();
        method = "read";
    }
  }
    

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    // start new session
    HttpSession mySession = req.getSession(true);
    mySession.setAttribute("scene", "menuscene");
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
    log.warning("scene:"+scene+", input:"+input);
    switch (scene) {
      case "menuscene":
        handleMenu(input);
    }
    //scene.handle(input);
    //screen += "<br>"+debug;
    // hand back to tier1 to present the new user state
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json());
  }

}
