package myapp;

import java.lang.IllegalArgumentException;
import java.io.IOException;
import javax.servlet.http.*;

/**
 * julian's fish and chicken restaurant
 */
public class DemoServlet extends HttpServlet {
  
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  
  private class Model {
    public String screen = "";
    public String state = "";
    public int bill = 0;
    
    public Model(String oldState, int bill, String input) {
      switch (oldState) {
        case "newgame":
          this.bill = 0;
          switch (input) {
            case "":
              // first use - no input
              this.screen = "Hello, Sir! Welcome to Julian's Fish and Chicken Restaurant. My Name is Manuel - I'll be your waiter today. Please come in and I wiil find you a very special table that's perfect for you - I know just what Sir would like...<br>:)?";
              break;
            default:
              // any key pressed
              this.state = "hungryformains";
              this.screen = "[SIR AT HIS TABLE]<br>And what would Sir like for mains?<br>1. Fish<br>2. Chicken<br>:)?";
              break;
          }
           break;
        case "hungryformains":
          switch (input) {
            case "1":
              this.state = "fish";
              this.screen = "(FISH)<br>Enjoy your meal, Sir.[any key to call Manuel]";
              break;
            case "2":
              this.state = "chicken";
              this.screen = "(CHICKEN)<br>Enjoy your meal, Sir.[any key to call Manuel]";
              break;
            default:
              this.screen = "Que?";
              break;
          }
          break;
        case "fish":
          this.bill = bill + 2;
          // any key
          this.state = "hungryforpud";
          this.screen = "[SIR AT HIS TABLE]<br>And what would Sir like for pud?<br>1. Cake<br>2. Jelly<br>:)?";
          break;
        case "chicken":
          this.bill = bill + 3;
          // any key
          this.state = "hungryforpud";
          this.screen = "[SIR AT HIS TABLE]<br>And what would Sir like for pud?<br>1. Cake<br>2. Jelly<br>:)?";
          break;
        case "hungryforpud":
          switch (input) {
            case "1":
              this.state = "cake";
              this.screen = "(FLUFFY CAKE)<br>Enjoy your meal, Sir.[any key to call Manuel]";
              break;
            case "2":
              this.state = "jelly";
              this.screen = "(WOBBLY JELLY)<br>Enjoy your meal, Sir.[any key to call Manuel]";
              break;
            default:
              this.screen = "Que?";
              break;
          }
          break;
        case "cake":
          this.bill = bill + 2;
          // any key
          this.state = "billdue";
          this.screen = "[(BILL + MINT)]<br>Your bill Sir<br>Total = £" + this.bill + ".00 (service is not included)<br>:)?[enter amount paid]";
          break;
        case "jelly":
          this.bill = bill + 1;
          // any key
          this.state = "billdue";
          this.screen = "[(BILL + MINT)]<br>Your bill Sir<br>Total = £" + this.bill + ".00 (service is not included)<br>:)?[enter amount paid]";
          break;
        case "billdue":
          try {
            int paid = Integer(input).intValue();
            if (paid >= this.bill) {
              this.state = "gameover";
              this.screen = "[MANUEL IS PUTTING ON SIR'S COAT]<br>Thank you, Sir. Goodbye.<br>[game over]";
            } else {
              this.screen = "Que?";
            }
          } catch (Exception e) {
            this.screen = "Que?";
          }
          break;
      }
    }
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
    Model m = new Model("newgame",0,"");
    resp.setContentType("text/plain");
    resp.getWriter().println("{ \"screen\": \"" + m.screen + "\", \"bill\": " + m.bill + ", \"statedata\": \"" + m.state + "\" }");
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, IllegalArgumentException {
    String oldState = req.getParameter("statedata");
    int bill = Integer(req.getParameter("bill")).intValue();
    String input = req.getParameter("userdata");
    Model m = new Model(oldState,bill,input);
    resp.setContentType("text/plain");
    resp.getWriter().println("{ \"screen\": \"" + m.screen + "\", \"bill\": " + m.bill + ", \"statedata\": \"" + m.state + "\" }");
  }

}
