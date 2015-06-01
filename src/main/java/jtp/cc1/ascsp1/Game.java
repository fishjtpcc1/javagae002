package jtp.cc1.ascsp1;

import java.io.Serializable;
import java.util.Random;

/** to save complex data in the session class
 * must implement Serializable or runtime error happens
 * must not be a nested class otherwise silent error accessing deserialized members
 */
public class Game implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final int BOARD_ROWS = 5;
  private static final int BOARD_COLS = 7;
  private static final Random() rand = new Random();
  private int i;
  private char[][] board = new char[BOARD_ROWS][BOARD_COLS];
  private GridRC userPos = new GridRC(0,0);
  private GridRC monsterPos = new GridRC(1+rand.nextInt(BOARD_ROWS-1),1+rand.nextInt(BOARD_COLS-1));
  public String data;
  public String method = "read";
  
  public String draw() {
    String rows = "";
    for (int i=0; i<board.length; i++ ) {
      String row = "<br>|";
      for (int j=0; j<board[i].length; j++) {
        if (userPos.equals(new GridRC(i,j))) {
          row += 'U';
        } else if (monsterPos.equals(new GridRC(i,j))) {
          row += 'M';
        } else {
          row += '-';
        }
        row += "|";
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
  }
  
  public Boolean isLost() {
    return (userPos.equals(monsterPos));
  }

  public Boolean isWon() {
    return (userPos.equals(new GridRC(2,2)));
  }

  private void makeUserMove(String input) {
    switch (input) {
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
    }
  }
  
  private void makeMonsterMove() {
    if (userPos.row < monsterPos.row) {
      monsterPos.row--;
    }
    if (userPos.row > monsterPos.row) {
      monsterPos.row++;
    }
    if (userPos.col < monsterPos.col) {
      monsterPos.col--;
    }
    if (userPos.col > monsterPos.col) {
      monsterPos.col++;
    }
  }
  
  public String newState(String input) {
    String newState;
    // last exit state may have displayed WON/LOST so substate check here to move on afer any key pressed
    if (isWon() || isLost()) {
      newState = "isover";
    } else {
      switch (input) {
        case "N": case "S": case "E": case "W":
          makeUserMove(input);
          makeMonsterMove();
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
