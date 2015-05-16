package ascsp1;

import java.lang.NumberFormatException;
import java.io.IOException;
import javax.servlet.http.*;

/**
 * MonsterGameServlet
 * Based on Julian's fish and chicken restaurant
 * Receives i/o from tier1 html consolesim via ajax
 */
public class MasterGameServlet extends HttpServlet {
  
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
              this.state = "newgame";
              this.screen = "[SIR AT THE RESTAURANT ENTRANCE]<br>Welcome to Julian's Fish and Chicken Restaurant. My Name is Manuel - I'll be your waiter today Sir. I'll find you your most special table...<br>[press any key]";
              break;
            default:
              // any key pressed
              this.state = "hungryformains";
              this.screen = "[SIR AT HIS TABLE]<br>And what would Sir like for mains?<br>1. Fish £2<br>2. Chicken £3<br>:)?";
              break;
          }
           break;
        case "hungryformains":
          switch (input) {
            case "1":
              this.state = "fish";
              this.screen = "(FISH)<br>Enjoy your meal, Sir.<br>[any key to call Manuel]";
              break;
            case "2":
              this.state = "chicken";
              this.screen = "(CHICKEN)<br>Enjoy your meal, Sir.<br>[any key to call Manuel]";
              break;
            default:
              this.state = "hungryformains";
              this.screen = "Que?";
              break;
          }
          break;
        case "fish":
          this.bill = bill + 2;
          // any key
          this.state = "hungryforpud";
          this.screen = "[SIR AT HIS TABLE]<br>And what would Sir like for pud?<br>1. Cake £2<br>2. Jelly £1<br>:)?";
          break;
        case "chicken":
          this.bill = bill + 3;
          // any key
          this.state = "hungryforpud";
          this.screen = "[SIR AT HIS TABLE]<br>And what would Sir like for pud?<br>1. Cake £2<br>2. Jelly £1<br>:)?";
          break;
        case "hungryforpud":
          this.bill = bill;
          switch (input) {
            case "1":
              this.state = "cake";
              this.screen = "(FLUFFY CAKE)<br>Enjoy your meal, Sir.<br>[any key to call Manuel]";
              break;
            case "2":
              this.state = "jelly";
              this.screen = "(WOBBLY JELLY)<br>Enjoy your meal, Sir.<br>[any key to call Manuel]";
              break;
            default:
              this.state = "hungryforpud";
              this.screen = "Que?";
              break;
          }
          break;
        case "cake":
          this.bill = bill + 2;
          // any key
          this.state = "billdue";
          this.screen = "(BILL + MINT)<br>Your bill Sir<br>Total = £" + this.bill + ".00 (service is not included)<br>:)?[enter amount paid]";
          break;
        case "jelly":
          this.bill = bill + 1;
          // any key
          this.state = "billdue";
          this.screen = "(BILL + MINT)<br>Your bill Sir<br>Total = £" + this.bill + ".00 (service is not included)<br>:)?[enter amount paid]";
          break;
        case "billdue":
          this.bill = bill;
          try {
            int paid = Integer.parseInt(input);
            if (paid >= this.bill) {
              String manuelsSecretComment = "[MANUEL SNIFFS, DERISING THE CHEAPSKATE-SIR AS HE EXITS...]";
              if (paid > this.bill) manuelsSecretComment = "[MANUEL PUTS HIS £" + (paid - this.bill) + " TIP IN HIS TOP POCKET AND THINKS VERY NICE THOUGHTS ABOUT SIR, AS HE WALKS BACK TO THE OTHER TABLES...]";
              this.state = "gameover";
              this.screen = "[MANUEL IS PUTTING ON SIR'S COAT]<br>Thank you, Sir. Goodbye.<br>"+manuelsSecretComment+"<br>[game over]";
            } else {
              this.state = "billdue";
              this.screen = "Que?";
            }
          } catch (NumberFormatException e) {
            this.state = "billdue";
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
    throws IOException, NumberFormatException {
    String oldState = req.getParameter("statedata");
    int bill = Integer.parseInt(req.getParameter("bill"));
    String input = req.getParameter("userdata");
    Model m = new Model(oldState,bill,input);
    resp.setContentType("text/plain");
    resp.getWriter().println("{ \"screen\": \"" + m.screen + "\", \"bill\": " + m.bill + ", \"statedata\": \"" + m.state + "\" }");
  }

}
