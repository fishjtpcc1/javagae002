package jtp.cc1.ascsp1;

import java.io.Serializable;

/** to save complex data in the session class
 * must implement Serializable or runtime error happens
 * must not be a nested class otherwise silent error accessing deserialized members
 */
public class Game implements Serializable {
  private static final long serialVersionUID = 1L;
  private int i;
  private char[][] board = new char[3][3];
  private GridRC userPos = new GridRC(0,0);
  public String data;
  public String method = "read";
  
  public String draw() {
    String rows = "";
    for (int i=0; i<3; i++ ) {
      String row = "|";
      for (int j=0; j<3; j++) {
        row  += board[i][j] + "|";
      }
      rows += row + "<br>";
    }
    return rows;
  }
  
  public void restart() {
    i = 0;
    data = "recycled (but still wonderful) ";
    for (int i=0; i<3; i++ ) {
      for (int j=0; j<3; j++) {
        if (userPos.row == i && userPos.col == j) {
          board[i][j] = 'U';
        } else {
          board[i][j] = '-';
        }
      }
    }
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
        case "N":
        case "S":
        case "E":
        case "W":
          data += input + ": ";
          i ++;
          userPos.row ++;
          newState = "isinplay";
        case "N":
          if (userPos.row > 0) {
            userPos.row--;
          }
          break;
        case "S":
          if (userPos.row < 2) {
            userPos.row++;
          }
          break;
        case "E":
          if (userPos.col < 2) {
            userPos.col++;
          }
          break;
        case "W":
          if (userPos.col > 0) {
            userPos.col--;
          }
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
    i = 0;
    data = "fresh of the press ";
  }
}
