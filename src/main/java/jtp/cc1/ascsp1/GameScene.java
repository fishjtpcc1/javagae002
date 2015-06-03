package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;

public class GameScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;

  private final Game g = new Game(); // canot be static otherwise all users play the same game!!

  public String method() {
    return g.method;
  }
  
  public String draw() {
    if (g.isWon()) {
      return "<br><br>" + g.name + g.draw() + "<br>WINNER!!!!<br>Press any key to continue: ";
    } else if (g.isLost()) {
      return "<br><br>" + g.name + g.draw() + "<br>LOOSER!!!!<br>Press any key to continue: ";
    } else {
      return "<br><br>" + g.name + g.draw() + "<br>Enter NSEWP: ";
    }
  }
  
  public Scene whereToNext(String input) {
    switch (g.newState(input)) {
      case "ispaused":
        return new PausedGameMenuScene();
      case "isover":
        return new MenuScene();
      case "isinplay":
        return this;
      default:
        return new OopsScene(this);
    }
  }

  GameScene(int mode) {
    if (mode == 1) {
      g.restart();
    } else {
      g.restartPreset();
    }
  }
  
}
