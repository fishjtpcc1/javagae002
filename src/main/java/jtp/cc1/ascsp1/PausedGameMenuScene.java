package jtp.cc1.ascsp1;

import java.io.Serializable;

public class PausedGameMenuScene implements SceneObject, Serializable {
  private static final long serialVersionUID = 1L;
  public String method(Game g) {
    return "read";
  }
  public String draw(Game g) {
    return "<br><br>PAUSED<br>1. Resume game<br>2. Save game<br>3. Quit<br>Enter choice: ";
  }
  public SceneObject whereToNext(Game g, String input) {
    switch (input) {
      case "1":
        return new GameScene();
      case "2":
        return new FilerScene();
      case "3":
        return new MenuScene();
      default:
        return new OopsScene((SceneObject)this);
    }
  }
}
