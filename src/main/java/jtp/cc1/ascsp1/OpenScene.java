package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.Arrays;

public class OpenScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private Game pausedGame;
  public ArrayList<Game> datastore; // dirty: sim datastore

  public String method() {
    return "readln";
  }
  
  public String draw() {
    return "<br><br>PAUSED" + drawFiles(datastore) + "<br>Enter filename: ";
  }
  
  private Game getGameByName(String n, ArrayList<Game> datastore) {
    if (datastore != null) {
      for (int i=0; i<datastore.size(); i++ ) {
        if (datastore.get(i) != null) {
          if (datastore.get(i).name == n) {
            return datastore.get(i);
          }
        }
      }
    }
    return null;
  }
  
  public Scene whereToNext(String input) {
    String localExitState;
    pausedGame = getGameByName(input, datastore);
    if (pausedGame == null) {
      localExitState = "fail";
    } else {
      localExitState = "success";
    }
    switch (localExitState) {
      case "success":
        return new GameScene(pausedGame, datastore);
      default:
        return new OopsScene(this);
    }
  }
  
  OpenScene(ArrayList<Game> datastore) {
    this.datastore = datastore;
  }

}
