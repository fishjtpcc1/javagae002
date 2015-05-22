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
  public Boolean isOver() {
    return (i >= 3);
  }
  public Boolean isWon() {
    return (data.contains("W"));
  }
  public String newState(String input) {
    String newState;
    switch (input) {
      case "N": case "S": case "E": case "W":
        data += input + ": ";
        i ++;
        if (isWon()) {
          newState = "iswon";
        } else if (isOver()) {
          newState = "isover";
        } else {
          newState = "isinplay";
        }
        break;
      case "P":
        newState = "ispaused";
        break;
      default:
        newState = "oops";
        break;
    }
    return newState;
  }
  public Game() {
    data = "fresh of the press ";
  }
}
