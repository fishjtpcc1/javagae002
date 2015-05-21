package jtp.cc1.ascsp1;

import java.io.Serializable;

/** to save complex data in the session class
 * must implement Serializable or runtime error happens
 * must not be a nested class otherwise silent error accessing deserialized members
 */
public class Game implements Serializable {
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  private int i = 0;
  private Boolean isPaused = false;
  public String data;
  public Boolean isOver() {
    return (i >= 3);
  }
  public Boolean isWon() {
    return (s.contains("W"));
  }
  public Boolean isPaused() {
    return isPaused;
  }
  public void updateState(String input) {
    switch (input) {
      case "N": case "S": case "E": case "W":
        data += input + ": ";
        i ++;
        break;
      case "P":
        isPaused = true;
        break;
      default:
        // dirty: bad input - try again
        break;
    }
    if (isWon()) {
      return "iswon";
    } else if (isOver()) {
      return "isover";
    } else if (isPaused()) {
      return "ispaused";
    } else {
      return "isinplay";
    }
  }
  public Game() {
    data = "fresh of the press ";
  }
}
