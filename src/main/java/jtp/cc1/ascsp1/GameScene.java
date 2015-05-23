package jtp.cc1.ascsp1;

import java.io.Serializable;

public class GameScene implements SceneObject, Serializable {
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  public String method() {
    return g.method;
  }
  public String draw() {
    if (g.isWon()) {
      return "<br>WINNER!!!!<br>Press any key to continue: ";
    } else if (g.isLost()) {
      return "<br>LOOSER!!!!<br>Press any key to continue: ";
    } else {
      return "<br>|------" + g.data + "------|<br>Enter NSEWP: ";
    }
  }
  public SceneObject whereToNext(String input) {
    switch (g.newState(input)) {
      case "ispaused": case "isover":
        return new MenuScene();
      case "isinplay":
        return new GameScene();
      default:
        return new OopsScene((SceneObject)this);
    }
  }
}
