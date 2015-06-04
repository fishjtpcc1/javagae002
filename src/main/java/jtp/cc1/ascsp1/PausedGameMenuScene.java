package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;
import java.util.ArrayList;

public class PausedGameMenuScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;

  public Scene back;
  public ArrayList<GameSnapshot> datastore; // dirty: sim datastore

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
        return new SaveScene(((GameScene)back).g, datastore);
      case "3":
        return new MenuScene(datastore);
      default:
        return new OopsScene(this);
    }
  }
  
  PausedGameMenuScene(Scene b, ArrayList<GameSnapshot> datastore) {
    back = b;
    this.datastore = datastore;
  }

}
