package jtp.cc1.ascsp1;

import java.io.Serializable;

public class MenuScene implements SceneObject, Serializable {
  private static final long serialVersionUID = 1L;
  public String method(Game g) {
    return "read";
  }
  public String draw(Game g, Game[] savedGames) {
    return "<br><br>1. New game<br>2. Training game<br>Enter choice: ";
  }
  public SceneObject whereToNext(Game g, String input) {
    switch (input) {
      case "1":
        g.restart();
        return new GameScene();
      case "2":
        g.restartPreset();
        return new GameScene();
      default:
        return new OopsScene((SceneObject)this);
    }
  }
}
