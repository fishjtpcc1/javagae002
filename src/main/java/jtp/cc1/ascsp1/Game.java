package jtp.cc1.ascsp1;

import java.io.Serializable;
import java.util.Random;

/** to save complex about in the session class
 * must implement Serializable or runtime error happens
 * must not be a nested class otherwise silent error accessing deserialized members
 */
public class Game implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final int BOARD_ROWS = 5;
  private static final int BOARD_COLS = 7;
  private static final int TRAPS = 2;
  private static final Random rand = new Random();
  private int moves;
  private char[][] board = new char[BOARD_ROWS][BOARD_COLS];
  private GameSnapshot data = new GameSnapshot();
  private String oneTimeMsg = "";

  public String about;
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
        if (data.userPos.equals(p)) {
          row += 'U';
        } else if (data.monsterPos.equals(p)) {
          row += 'M';
        } else if (data.goalPos.equals(p)) {
          row += 'G';
        } else if (matches(data.traps, p)) {
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
    data.userPos = new GridRC(0,0);
    data.monsterPos = new GridRC(1+rand.nextInt(BOARD_ROWS-1),1+rand.nextInt(BOARD_COLS-1));
    data.goalPos = new GridRC(1+rand.nextInt(BOARD_ROWS-1),1+rand.nextInt(BOARD_COLS-1));
    data.traps = new GridRC[TRAPS];
    for (int i=0; i<data.traps.length; i++) {
      GridRC trapPos = new GridRC(1+rand.nextInt(BOARD_ROWS-1),1+rand.nextInt(BOARD_COLS-1));
      while (trapPos.equals(data.monsterPos) || trapPos.equals(data.goalPos)) {
        trapPos = new GridRC(1+rand.nextInt(BOARD_ROWS-1),1+rand.nextInt(BOARD_COLS-1));
      }
      data.traps[i] = trapPos;
    }
    data.monsterIsAwake = false;

    moves = 0;
    about = "recycled (but still wonderful) ";
    oneTimeMsg = "<br>GO!!";
  }
  
  public void restartPreset() {
    data.userPos = new GridRC(0,0);
    data.monsterPos = new GridRC(0,2);
    data.goalPos = new GridRC(0,1);
    data.traps = new GridRC[TRAPS];
    data.traps[0] = new GridRC(3,3);
    data.traps[1] = new GridRC(1,1);
    data.monsterIsAwake = true;

    moves = 0;
    about = "preset";
    oneTimeMsg = "<br>GO!!";
  }
  
  public GameSnapshot getSnapshot() {
    GameSnapshot s = new GameSnapshot();
    // do not use simple object ref assignment otherwise snapshots share memory
    s.userPos = new GridRC(data.userPos);
    s.monsterPos = new GridRC(data.monsterPos);
    s.goalPos = new GridRC(data.goalPos);
    // need to deep clone traps so array.clone() is no good
    s.traps = new GridRC[data.traps.length];
    for (int i=0; i<data.traps.length; i++) {
      s.traps[i] = data.traps[i].clone();
    }
    s.monsterIsAwake = data.monsterIsAwake;
    return s;
  }
  
  public void restartSnapshot(GameSnapshot s) {
    data.userPos = s.userPos.clone();
    data.monsterPos = s.monsterPos.clone();
    data.goalPos = s.goalPos.clone();
    // need to deep clone traps so array.clone() is no good
    data.traps = new GridRC[s.traps.length];
    for (int i=0; i<data.traps.length; i++) {
      data.traps[i] = s.traps[i].clone();
    }
    data.monsterIsAwake = s.monsterIsAwake;

    moves = 0;
    about = "snapshot";
    oneTimeMsg = "<br>" + s.name + "...";
  }
  
  public Boolean isLost() {
    return (data.userPos.equals(data.monsterPos));
  }

  public Boolean isWon() {
    return (data.userPos.equals(data.goalPos));
  }

  private void makeUserMove(String input) {
    switch (input) {
      case "n":
        if (data.userPos.row > 0) {
          data.userPos.row--;
        }
        break;
      case "s":
        if (data.userPos.row < BOARD_ROWS-1) {
          data.userPos.row++;
        }
        break;
      case "e":
        if (data.userPos.col < BOARD_COLS-1) {
          data.userPos.col++;
        }
        break;
      case "w":
        if (data.userPos.col > 0) {
          data.userPos.col--;
        }
        break;
    }
  }
  
  // move only one square h or v
  private void makeMonsterMove() {
    GridRC m0 = new GridRC(data.monsterPos.row,data.monsterPos.col);;
    if (data.userPos.row < data.monsterPos.row) {
        data.monsterPos.row--;
    } else if (data.userPos.row > data.monsterPos.row) {
        data.monsterPos.row++;
    } else if (data.userPos.col < data.monsterPos.col) {
        data.monsterPos.col--;
     } else if (data.userPos.col > data.monsterPos.col) {
        data.monsterPos.col++;
    }
    // swap goal
    if (data.monsterPos.equals(data.goalPos)) {
      data.goalPos = m0;
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
        case "n": case "s": case "e": case "w":
          makeUserMove(input);
          if(!isWon()) {
            if (matches(data.traps, data.userPos)) {
              if (!data.monsterIsAwake) {
                oneTimeMsg = "<br>MONSTER IS AWAKE!!";
              }
              data.monsterIsAwake = true;
            }
            if (data.monsterIsAwake) {
              makeMonsterMove();
            }
          }
          moves ++;
          newState = "isinplay";
          break;
        case "/":
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
    moves = 0;
    about = "fresh of the press";
  }
  
}
