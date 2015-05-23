package jtp.cc1.ascsp1;

import java.io.Serializable;

public class MenuScene implements SceneObject, Serializable {
  private static final long serialVersionUID = 1L;
  public String method(Game g) {
    return "read";
  }
  public String draw(Game g) {
    return "<br>1. New game<br>2. Save game<br>etc...<br>Enter choice: ";
  }
  public SceneObject whereToNext(Game g, String input) {
    switch (input) {
      case "1":
        g.restart();
        return new GameScene();
      case "2":
        return new FilerScene();
      default:
        return new OopsScene((SceneObject)this);
    }
  }
}
