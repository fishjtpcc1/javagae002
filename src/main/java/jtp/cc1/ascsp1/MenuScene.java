package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;

public class MenuScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
  public String method() {
    return "read";
  }
  public String draw() {
    return "<br><br>1. New game<br>2. Training game<br>Enter choice: ";
  }
  public Scene whereToNext(Game g, String input) {
    switch (input) {
      case "1":
        g.restart();
        return new GameScene();
      case "2":
        g.restartPreset();
        return new GameScene();
      default:
        return new OopsScene((Scene)this); // know: must cast to expected type - even tho implied
    }
  }
  
}
