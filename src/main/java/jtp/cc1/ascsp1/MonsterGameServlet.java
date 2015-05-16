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
  
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws java.io.IOException {
    String input = req.getParameter("input");
    String method = "read";
    resp.setContentType("text/plain");
    resp.getWriter().println("{ \"screen\": \"" + "you said:" + input + "\", \"method\": \"" + method + "\" }");
  }

}
