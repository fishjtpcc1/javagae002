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
  private GridRC monsterPos = new GridRC(1,1);
  public String data;
  public String method = "read";
  
  public String draw() {
    String rows = "";
    for (int i=0; i<3; i++ ) {
      String row = "<br>|";
      for (int j=0; j<3; j++) {
        row  += board[i][j] + "|";
      }
      rows += row;
    }
    return rows;
  }
  
  public void restart() {
    i = 0;
    data = "recycled (but still wonderful) ";
    userPos = new GridRC(0,0);
    monsterPos = new GridRC(1,1);
    for (int i=0; i<3; i++ ) {
      for (int j=0; j<3; j++) {
        if (userPos.equals(new GridRC(i,j))) {
          board[i][j] = 'U';
        } else if (monsterPos.equals(new GridRC(i,j))) {
          board[i][j] = 'M';
        } else {
          board[i][j] = '-';
        }
      }
    }
  }
  
  public Boolean isLost() {
    return (userPos.equals(monsterPos));
  }

  public Boolean isWon() {
    return (userPos.equals(new GridRC(2,2)));
  }

  public String newState(String input) {
    String newState;
    // last exit state may have displayed WON/LOST so substate check here to move on afer any key pressed
    if (isWon() || isLost()) {
      newState = "isover";
    } else {
      switch (input) {
        case "N":
          if (userPos.row > 0) {
            board[userPos.row][userPos.col] = '-';
            userPos.row--;
            board[userPos.row][userPos.col] = 'U';
          }
          data += input + ": ";
          i ++;
          newState = "isinplay";
          break;
        case "S":
          if (userPos.row < 2) {
            board[userPos.row][userPos.col] = '-';
            userPos.row++;
            board[userPos.row][userPos.col] = 'U';
          }
          data += input + ": ";
          i ++;
          newState = "isinplay";
          break;
        case "E":
          if (userPos.col < 2) {
            board[userPos.row][userPos.col] = '-';
            userPos.col++;
            board[userPos.row][userPos.col] = 'U';
          }
          data += input + ": ";
          i ++;
          newState = "isinplay";
          break;
        case "W":
          if (userPos.col > 0) {
            board[userPos.row][userPos.col] = '-';
            userPos.col--;
            board[userPos.row][userPos.col] = 'U';
          }
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
    i = 0;
    data = "fresh of the press ";
  }
}
