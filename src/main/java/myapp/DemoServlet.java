package myapp;

import java.io.IOException;
import javax.servlet.http.*;

public class DemoServlet extends HttpServlet {
  
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  
  private class Model {
    public String screen = "";
    public String state = "";
    
    public Model(String state, String input) {
      switch (state) {
        case "fish":
          switch (input) {
            case "1":
              this.state = "trifle";
              break;
            case "2":
              this.state = "cheese";
              break;
            default:
              this.state = "que!?";
              break;
          }
          break;
        case "chicken":
          switch (input) {
            case "1":
              this.state = "jelly";
              break;
            case "2":
              this.state = "cake";
              break;
            default:
              this.state = "que!?";
              break;
          }
          break;
        default:
          switch (input) {
            case "1":
              this.state = "fish";
              break;
            case "2":
              this.state = "chicken";
              break;
            default:
              this.state = "que!?";
              break;
          }
          break;
      }
    }
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
    Model m = new Model("newgame","");
    resp.setContentType("text/plain");
    resp.getWriter().println("{ \"screen\": \"" + m.screen + "<br>What now, Sir?\", \"statedata\": \""+m.state+"\" }");
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
    String oldState = req.getParameter("statedata");
    String input = req.getParameter("userdata");
    Model m = new Model(oldState,input);
    resp.setContentType("text/plain");
    resp.getWriter().println("{ \"screen\": \"" + m.screen + "<br>What now, Sir?\", \"statedata\": \""+m.state+"\" }");
  }

}
