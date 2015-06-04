package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;
import java.util.ArrayList;

public class SaveScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public Scene back;
  private Game pausedGame;
  //public ArrayList<GameSnapshot> datastore; // dirty: sim datastore

  public String method() {
    return "readln";
  }
  
  public String draw() {
    return "<br><br>PAUSED" + drawFiles(datastore) + "<br>Enter filename: ";
  }
  
  public Scene whereToNext(String input) {
    String localExitState;
    if (input.isEmpty()) {
      localExitState = "back";
    } else if (input.contains(" ")) {
      localExitState = "fail";
    } else {
      localExitState = "success";
      GameSnapshot s = pausedGame.getSnapshot();
      s.name = input;
      if (datastore != null) {
        datastore.add(s);
      } else {
        datastore = new ArrayList<GameSnapshot>();
        datastore.add(s);
      }
    }
    switch (localExitState) {
      case "back":
        return back;
      case "success":
        return new MenuScene(datastore);
      default:
        return new OopsScene(this);
    }
  }
  
  SaveScene(Scene b, ArrayList<GameSnapshot> datastore) {
    back = b;
    pausedGame = ((GameScene)((PausedGameMenuScene)back).back).g;
    this.datastore = datastore;
  }

}
