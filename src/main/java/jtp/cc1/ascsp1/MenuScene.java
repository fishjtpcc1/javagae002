package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;

public class MenuScene implements Scene, Serializable {
  private static final long serialVersionUID = 1L;
  public String method() {
    return "read";
  }
  public String draw() {
    return "<br><br>1. New game<br>2. Training game<br>Enter choice: ";
  }
  public Scene whereToNext(Game g, String input) {
    switch (input) {
      case "1":
        g.restart();
        return new GameScene();
      case "2":
        g.restartPreset();
        return new GameScene();
      default:
        return new OopsScene((Scene)this);
    }
  }
  
  /* mo input yet: sets up session data of current scene = this, sends screen image to tier1
   */
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    // save state
    HttpSession mySession = req.getSession(true);
    mySession.setAttribute("here", this);
    // hand back to tier1 to present the initial user state and service access (user can enter his data)
    resp.setContentType("text/plain");
    resp.getWriter().println(MonsterGameServlet.json(draw(), method(), "sid:"+mySession.getId()));
  }
  
}
