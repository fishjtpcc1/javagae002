package jtp.cc1.ascsp1;

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

  class TheGame {
    void TheGame() {
      String this.data = "newgame ";
    }
  }
  
  
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    HttpSession mySession = req.getSession();
    if (mySession.isNew()) {
      mySession.setAttribute("theGame") = new TheGame();
    }
    String input = req.getParameter("input");
    String method = "readln";
    mySession.getAttribute("theGame").data += input + " ";
    resp.setContentType("text/plain");
    resp.getWriter().println("{ \"screen\": \"" + "you said:" + input + "<br>theGame:" + mySession.getAttribute("theGame").data + "\", \"method\": \"" + method + "\" }");
  }

}
