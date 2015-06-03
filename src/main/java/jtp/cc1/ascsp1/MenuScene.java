package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;

public class MenuScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
  public String method() {
    return "read";
  }
  public String draw() {
    return "<br><br>1. New game<br>2. Training game<br>Enter choice: ";
  }
  public Scene whereToNext(String input) {
    switch (input) {
      case "1":
        g.restart();
        return new GameScene();
      case "2":
        g.restartPreset();
        return new GameScene();
      default:
        return new OopsScene(this); // know: casting back is not required as is obviously valid in inheritance structure
    }
  }
  
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    HttpSession mySession = req.getSession(false);
    String input = req.getParameter("input");
    Scene next = whereToNext(input); // strictly controlled polymorphism in action
    // save state
    mySession.setAttribute("here", next);
    // hand back to tier1 to present the initial user state and service access (user can enter his data)
    resp.setContentType("text/plain");
    resp.getWriter().println(json(draw(), method(), "sid:"+mySession.getId()));
  }
  
}
