package jtp.cc1.ascsp1;

import java.io.Serializable;

public class GameScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;

  public String method() {
    return g.method;
  }
  
  public String draw() {
    if (g.isWon()) {
      return "<br><br>" + g.draw() + "<br>WINNER!!!!<br>Press any key to continue: ";
    } else if (g.isLost()) {
      return "<br><br>" + g.draw() + "<br>LOOSER!!!!<br>Press any key to continue: ";
    } else {
      return "<br><br>" + g.draw() + "<br>Enter NSEW/: ";
    }
  }
  
  public Scene whereToNext(String input) {
    switch (g.newState(input.toLowerCase())) {
      case "ispaused":
        return new PausedGameMenuScene(this);
      case "isover":
        return new MenuScene(this);
      case "isinplay":
        return this;
      default:
        return new OopsScene(this);
    }
  }

  GameScene(Scene b) {
    super(b);
  }

}
