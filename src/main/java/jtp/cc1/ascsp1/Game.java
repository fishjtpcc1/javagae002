package jtp.cc1.ascsp1;

import java.io.Serializable;

/** to save complex data in the session class
 * must implement Serializable or runtime error happens
 * fail: works when private static but not when public - as if interface is recognised but no implementation code is attached
 */
public class Game implements Serializable {
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  public String s = "newgame: ";
  private int i = 0;
  private Boolean isPaused = false;
  public Boolean isOver() {
    return (i >= 3);
  }
  public Boolean isWon() {
    return (s.contains("W"));
  }
  public Boolean isPaused() {
    return isPaused;
  }
  public String drawGame() {
    return "<br>|------" + s + " " + i + "------|<br>Enter NSEWM: ";
  }
  public String drawGameover() {
    return "<br>LOOSER!!!!<br>Press any key to continue: ";
  }
  public String drawGamewon() {
    return "<br>WINNER!!!!<br>Press any key to continue: ";
  }
  public void handle(String input) {
    switch (input) {
      case "N": case "S": case "E": case "W":
        s += input + ": ";
        i ++;
        if (isWon()) {
          MonsterGameServlet.scene = "gamewonscene";
          screen = drawGamewon();
          method = "read";
        } else if (isOver()) {
          scene = "gameoverscene";
          screen = drawGameover();
          method = "read";
        } else {
          scene = "gamescene";
          screen = drawGame();
          method = "read";
        }
        break;
      case "M":
        isPaused = true;
        scene = "menuscene";
        screen = MonsterGameServlet.drawMenu();
        method = "read";
        break;
      default:
        scene = "gamescene";
        screen = drawGame();
        method = "read";
        break;
    }
  }
  public Game() {
      scene = "gamescene";
      screen = drawGame();
      method = "read";
  }
}
