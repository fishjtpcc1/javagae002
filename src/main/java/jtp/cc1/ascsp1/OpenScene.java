package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.Arrays;

public class OpenScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public Scene back;

  public String method() {
    return "readln";
  }
  
  public String draw() {
    return "<br>" + drawFiles() + "<br>Enter filename: ";
  }
  
  private GameSnapshot getGameSnapshotByName(String n, ArrayList<GameSnapshot> datastore) {
    if (datastore != null) {
      for (int i=0; i<datastore.size(); i++ ) {
        if (datastore.get(i) != null) {
          if (datastore.get(i).name.equals(n)) {
            return datastore.get(i);
          }
        }
      }
    }
    return null;
  }
  
  public Scene whereToNext(String input) {
    String localExitState;
    GameSnapshot s = null;
    if (input.isEmpty()) {
      localExitState = "back";
    } else {
      s = getGameSnapshotByName(input, datastore);
      if (s == null) {
        localExitState = "fail";
      } else {
        localExitState = "success";
      }
    }
    switch (localExitState) {
      case "back":
        return back;
      case "success":
        return new GameScene(s, this);
      default:
        return new OopsScene(this);
    }
  }
  
  OpenScene(Scene b) {
    back = b;
    this.datastore = b.datastore;
  }

}
