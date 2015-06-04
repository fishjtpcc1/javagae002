package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;
import java.util.ArrayList;

public class MenuScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
  
  //public ArrayList<GameSnapshot> datastore; // dirty: sim datastore

  public String method() {
    return "read";
  }
  
  public String draw() {
    return "<br><br>" + drawFiles(datastore) + "<br>1. New game<br>2. Training game<br>3. Open game<br>Enter choice: ";
  }
  
  public Scene whereToNext(String input) {
    switch (input) {
      case "1":
        return new GameScene(1,datastore);
      case "2":
        return new GameScene(2,datastore);
      case "3":
        return new OpenScene(this,datastore);
      default:
        return new OopsScene(this); // know: casting back is not required as is obviously valid in inheritance structure
    }
  }
  
  MenuScene() {
  }

  MenuScene(ArrayList<GameSnapshot> datastore) {
    this.datastore = datastore;
  }

}
