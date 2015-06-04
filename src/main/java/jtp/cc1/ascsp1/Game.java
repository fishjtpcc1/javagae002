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
  private static final int TRAPS = 2;
  private static final Random rand = new Random();
  private int i;
  private char[][] board = new char[BOARD_ROWS][BOARD_COLS];
  private GridRC userPos;
  private GridRC monsterPos;
  private GridRC goalPos;
  private GridRC[] traps = new GridRC[TRAPS];
  private Boolean monsterIsAwake;
  private String oneTimeMsg = "";

  public String data;
  public String method = "read";
  
  public Boolean matches(GridRC[] a, GridRC p) {
    for (int i=0; i<a.length; i++) {
      if (a[i].equals(p)) {
        return true;
      }
    }
    return false;
  }
  
  public String draw() {
    String rows = "";
    for (int i=0; i<board.length; i++ ) {
      String row = "<br>|";
      for (int j=0; j<board[i].length; j++) {
        GridRC p = new GridRC(i,j);
        if (userPos.equals(p)) {
          row += 'U';
        } else if (monsterPos.equals(p)) {
          row += 'M';
        } else if (goalPos.equals(p)) {
          row += 'G';
        } else if (matches(traps, p)) {
          row += 'T';
        } else {
          row += '-';
        }
        row += "|";
      }
      rows += row;
    }
    return rows + oneTimeMsg;
  }
  
  public void restart() {
    i = 0;
    data = "recycled (but still wonderful) ";
    userPos = new GridRC(0,0);
    monsterPos = new GridRC(1+rand.nextInt(BOARD_ROWS-1),1+rand.nextInt(BOARD_COLS-1));
    goalPos = new GridRC(1+rand.nextInt(BOARD_ROWS-1),1+rand.nextInt(BOARD_COLS-1));
    for (int i=0; i<traps.length; i++) {
      GridRC trapPos = new GridRC(1+rand.nextInt(BOARD_ROWS-1),1+rand.nextInt(BOARD_COLS-1));
      while (trapPos.equals(monsterPos) || trapPos.equals(goalPos)) {
        trapPos = new GridRC(1+rand.nextInt(BOARD_ROWS-1),1+rand.nextInt(BOARD_COLS-1));
      }
      traps[i] = trapPos;
    }
    monsterIsAwake = false;
    oneTimeMsg = "<br>GO!!";
  }
  
  public void restartPreset() {
    i = 0;
    data = "preset";
    userPos = new GridRC(0,0);
    monsterPos = new GridRC(2,2);
    goalPos = new GridRC(4,4);
    traps[0] = new GridRC(3,3);
    traps[1] = new GridRC(1,1);
    monsterIsAwake = false;
    oneTimeMsg = "<br>GO!!";
  }
  
  public GameSnapshot getSnapshot() {
    GameSnapshot s = new GameSnapshot();
    // do not use simple object ref assignment otherwise snapshots share memory
    s.userPos = new GridRC(userPos);
    s.monsterPos = new GridRC(monsterPos);
    s.goalPos = new GridRC(goalPos);
    // need to deep clone traps so array.clone() is no good
    s.traps = new GridRC[traps.length];
    for (int i=0; i<traps.length; i++) {
      s.traps[i] = traps[i].clone();
    }
    s.monsterIsAwake = monsterIsAwake;
    return s;
  }
  
  public void restartSnapshot(GameSnapshot s) {
    i = 0;
    data = "snapshot";
    userPos = s.userPos.clone();
    monsterPos = s.monsterPos.clone();
    goalPos = s.goalPos.clone();
    // need to deep clone traps so array.clone() is no good
    traps = new GridRC[s.traps.length];
    for (int i=0; i<traps.length; i++) {
      traps[i] = s.traps[i].clone();
    }
    monsterIsAwake = s.monsterIsAwake;
    oneTimeMsg = "<br>" + s.name + "...";
  }
  
  public Boolean isLost() {
    return (userPos.equals(monsterPos));
  }

  public Boolean isWon() {
    return (userPos.equals(goalPos));
  }

  private void makeUserMove(String input) {
    switch (input) {
      case "N":
        if (userPos.row > 0) {
          userPos.row--;
        }
        break;
      case "S":
        if (userPos.row < BOARD_ROWS-1) {
          userPos.row++;
        }
        break;
      case "E":
        if (userPos.col < BOARD_COLS-1) {
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
  
  // move only one square h or v
  private void makeMonsterMove() {
    GridRC m0 = new GridRC(monsterPos.row,monsterPos.col);;
    if (userPos.row < monsterPos.row) {
        monsterPos.row--;
    } else if (userPos.row > monsterPos.row) {
        monsterPos.row++;
    } else if (userPos.col < monsterPos.col) {
        monsterPos.col--;
     } else if (userPos.col > monsterPos.col) {
        monsterPos.col++;
    }
    // swap goal
    if (monsterPos.equals(goalPos)) {
      goalPos = m0;
    }
  }
  
  public String newState(String input) {
    String newState;
    oneTimeMsg = "";
    // last exit state may have displayed WON/LOST so substate check here to move on afer any key pressed
    if (isWon() || isLost()) {
      newState = "isover";
    } else {
      switch (input) {
        case "N": case "S": case "E": case "W":
          makeUserMove(input);
          if(!isWon()) {
            if (matches(traps, userPos)) {
              if (!monsterIsAwake) {
                oneTimeMsg = "<br>MONSTER IS AWAKE!!";
              }
              monsterIsAwake = true;
            }
            if (monsterIsAwake) {
              makeMonsterMove();
            }
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
