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

  private String debug = "debug:";
  private String screen = "";
  private String method = "";

  private static String json() {
    return "{ \"screen\": \"" + screen + "\", \"method\": \"" + method + "\" }";
  }
  
  private static String drawMenu() {
    return "<br>[" + super.data + "]" + "<br>1. New game<br>etc...<br>Enter choice: ";
  }
  
  
  /** each scene is an object
   */
  class Scene implements Serializable {
    public String data = "";
    
    public void handle(String input) {
      this.data += input + " ";
      screen = "blank";
      method = "none";
    }
    
  }

  class MenuScene extends Scene {

    public MenuScene() {
      super.data = "newmenu";
      screen = draw();
      method = "read";
    }
    
    private String draw() {
      return "<br>[" + super.data + "]" + "<br>1. New game<br>etc...<br>Enter choice: ";
    }
    
    public void handle(String input) {
      debug += "here:";
      super.handle(input);
      switch (input) {
        default:
          screen = draw();
          method = "read";
      }
    }
    
  }

  class GameScene extends Scene {

    public GameScene() {
      super.data = "newgame";
      screen = draw();
      method = "read";
    }
    
    private String draw() {
      return "<br>[" + super.data + "]" + "--game--<br>Enter choice: ";
    }
    
    public void handle(String input) {
      super.handle(input);
      switch (input) {
        default:
          screen = draw();
          method = "read";
      }
    }
    
  }


  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    // start new session
    HttpSession mySession = req.getSession(true);
    mySession.setAttribute("scene", "menuscene");
    // hand back to tier1 to present the initial user state
    resp.setContentType("text/plain");
    resp.getWriter().println(json());
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
    String scene = (String)mySession.getAttribute("scene");
    debug += "input:"+input+":scene:"+scene;
    select (scene) {
      case "menuscene":
        screen = MonsterGameServlet.drawMenu();
        method = "read";
    }
    //scene.handle(input);
    //screen += "<br>"+debug;
    log.warning(debug);
    // hand back to tier1 to present the new user state
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json());
  }

}
