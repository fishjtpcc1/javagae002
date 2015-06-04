package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SaveScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private Game pausedGame;
  public ArrayList<GameSnapshot> datastore; // dirty: sim datastore

  public String method() {
    return "readln";
  }
  
  public String draw() {
    return "<br><br>PAUSED" + drawFiles(datastore) + "<br>Enter filename: ";
  }
  
  public Scene whereToNext(String input) {
    String localExitState;
    if (input.contains(" ")) {
      localExitState = "fail";
    } else {
      localExitState = "success";
      pausedGame.name = input;
      if (datastore != null) {
        datastore.add(pausedGame.getSnapshot());
      } else {
        datastore = new ArrayList<GameSnapshot>();
        datastore.add(pausedGame.getSnapshot());
      }
    }
    switch (localExitState) {
      case "success":
        return new MenuScene(datastore);
      default:
        return new OopsScene(this);
    }
  }
  
  SaveScene(Game g, ArrayList<GameSnapshot> datastore) {
    pausedGame = g;
    this.datastore = datastore;
  }

}
