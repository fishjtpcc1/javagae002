package myapp;

import java.io.IOException;
import javax.servlet.http.*;

public class DemoServlet extends HttpServlet {
  
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
    String oldState = "";
    String input = "";
    String newState = updateState(oldState,input);
    resp.setContentType("text/plain");
    resp.getWriter().println("{ \"screen\": \"" + newState + "<br>What now, Sir?\", \"statedata\": \""+newState+"\" }");
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
    String oldState = req.getParameter("statedata");
    String input = req.getParameter("userdata");
    String newState = updateState(oldState,input);
    resp.setContentType("text/plain");
    resp.getWriter().println("{ \"screen\": \"" + newState + "<br>What now, Sir?\", \"statedata\": \""+newState+"\" }");
  }

  private String updateState(String oldState, String input){
    String newState;
    switch (input) {
      case "1":
        newState = "[1]Your fish, Sir.<br>1:trifle 2:cheese";
        break;
      case "2":
        newState = "[2]Your chicken, Sir.<br>1:trifle 2:cheese";
        break;
      default:
        newState = "[NEW GAME]<br>1:fish 2:chicken";
        break;
    }
    return newState;
  }
  
}
