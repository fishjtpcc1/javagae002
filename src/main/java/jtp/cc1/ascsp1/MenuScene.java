package jtp.cc1.ascsp1;

import java.io.Serializable;

public class MenuScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public String method() {
    return "read";
  }
  
  public String draw() {
    return "<br><br>1. New game<br>2. Training game<br>3. Open game<br>Enter choice: ";
  }
  
  public Scene whereToNext(String input) {
    switch (input) {
      case "1":
        return new GameScene(1,this);
      case "2":
        return new GameScene(2,this);
      case "3":
        return new OpenScene(this);
      default:
        return new OopsScene(this); // know: casting back is not required as is obviously valid in inheritance structure
    }
  }
  
  MenuScene() {
    // just to avoid null messiness
  }

  MenuScene(Scene b) {
    super(b);
  }

}
