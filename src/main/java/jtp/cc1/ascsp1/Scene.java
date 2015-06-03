package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;
import java.io.IOException;

abstract class Scene {

  static String json(String screen, String method, String other) {
    return "{ \"screen\": \"" + screen + "\", \"method\": \"" + method + "\", \"other\": \"" + other + "\" }";
  }

  public String method() {
    return "nomethod";
  }
  public String draw() {
    return "blank";
  }
 
   /* no input yet: sets up session data of current SceneI = this, sends screen image to tier1
   */
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    // save state
    HttpSession mySession = req.getSession(true);
    mySession.setAttribute("here", this);
    // hand back to tier1 to present the initial user state and service access (user can enter his data)
    resp.setContentType("text/plain");
    resp.getWriter().println(json(draw(), method(), "sid:"+mySession.getId()));
  }
  
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    HttpSession mySession = req.getSession(false);
    String input = req.getParameter("input");
    Scene next = this.whereToNext(input); // strictly controlled polymorphism in action
    // save state
    mySession.setAttribute("here", next);
    // hand back to tier1 to present the initial user state and service access (user can enter his data)
    resp.setContentType("text/plain");
    resp.getWriter().println(json(draw(), method(), "sid:"+mySession.getId()));
  }
  
}
