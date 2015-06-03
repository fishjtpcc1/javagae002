package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;

public class PausedGameMenuScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;

  public Scene back;

  public String method() {
    return "read";
  }
  
  public String draw() {
    return "<br><br>PAUSED<br>1. Resume game<br>2. Save game<br>3. Quit<br>Enter choice: ";
  }
  
  public Scene whereToNext(String input) {
    switch (input) {
      case "1":
        return back;
      case "2":
        return new FilerScene(this);
      case "3":
        return new MenuScene(this);
      default:
        return new OopsScene(this);
    }
  }
  
  PausedGameMenuScene(Scene b) {
    back = b;
    datastore = b.datastore;
  }

}
