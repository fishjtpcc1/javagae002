package myapp;

import java.io.IOException;
import javax.servlet.http.*;

public class DemoServlet extends HttpServlet {
  
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
    String oldState = "newgame";
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
    switch (oldState) {
      case "fish":
        switch (input) {
          case "1":
            newState = "trifle";
            break;
          case "2":
            newState = "cheese";
            break;
          default:
            newState = "que!?";
            break;
        }
        break;
      case "chicken":
        switch (input) {
          case "1":
            newState = "jelly";
            break;
          case "2":
            newState = "cake";
            break;
          default:
            newState = "que!?";
            break;
        }
        break;
      default:
        switch (input) {
          case "1":
            newState = "fish";
            break;
          case "2":
            newState = "chicken";
            break;
          default:
            newState = "que!?";
            break;
        }
        break;
    }
    return newState;
  }
  
}
