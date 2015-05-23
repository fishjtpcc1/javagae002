package jtp.cc1.ascsp1;

import java.io.Serializable;

public class MenuScene implements SceneObject, Serializable {
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  public String method() {
    return "read";
  }
  public String draw() {
    return "<br>1. New game<br>2. Save game<br>etc...<br>Enter choice: ";
  }
  public SceneObject whereToNext(String input) {
    switch (input) {
      case "1":
        g = new Game();
        return new GameScene();
      case "2":
        return new FilerScene();
      default:
        return new OopsScene((SceneObject)this);
    }
  }
}
