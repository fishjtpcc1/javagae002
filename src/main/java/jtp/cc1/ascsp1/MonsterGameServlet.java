package jtp.cc1.ascsp1;

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
  
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable

  private String screen = "";
  private String method = "";

  public String json() {
    return "{ \"screen\": \"" + screen + "\", \"method\": \"" + method + "\" }";
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
    mySession.setAttribute("scene", new MenuScene());
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
    screen = "<br>safety screen";
    method = "safety method";
    Scene scene = (Scene)mySession.getAttribute("scene");
    scene.handle(input);
    screen += "<br>input:"+input+", scene:"+scene;
    // hand back to tier1 to present the new user state
    resp.setContentType("text/plain");
    resp.getWriter().println(json());
  }

}
