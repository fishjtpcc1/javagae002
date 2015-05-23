package jtp.cc1.ascsp1;

import java.io.Serializable;

/** to save complex data in the session class
 * must implement Serializable or runtime error happens
 * must not be a nested class otherwise silent error accessing deserialized members
 */
public class Game implements Serializable {
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  private int i = 0;
  public String data;
  public String method = "read";
  public void restart() {
    i = 0;
    data = "fresh of the press ";
  }
  public Boolean isLost() {
    return (i >= 3);
  }
  public Boolean isWon() {
    return (data.contains("W"));
  }
  public String newState(String input) {
    String newState;
    if (isWon() || isLost()) {
      newState = "isover";
    } else {
      switch (input) {
        case "N": case "S": case "E": case "W":
          data += input + ": ";
          i ++;
          newState = "isinplay";
          break;
        case "P":
          newState = "ispaused";
          break;
        default:
          newState = "oops";
          break;
      }
    }
    return newState;
  }
  public Game() {
    restart();
  }
}
