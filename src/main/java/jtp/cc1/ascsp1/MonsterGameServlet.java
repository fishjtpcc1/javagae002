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
  
  class TheGame implements Serializable {
    public String data = "";
    public TheGame() {
      this.data = "newgame ";
    }
  }
  
  
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    HttpSession mySession = req.getSession();
    if (mySession.isNew()) {
      mySession.setAttribute("theGame", new TheGame());
    }
    String input = req.getParameter("input");
    TheGame myGame = (TheGame) mySession.getAttribute("theGame");
    myGame.data += input + " ";
    screen = "<br>you said:" + input + "<br>theGame:" + myGame.data + ": what now? ";
    method = "readln";
    resp.setContentType("text/plain");
    resp.getWriter().println("{ \"screen\": \"" + screen + "\", \"method\": \"" + method + "\" }");
  }

}
