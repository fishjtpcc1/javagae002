package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;
import java.util.ArrayList;

public class SaveScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public Scene back;
  private Game pausedGame;

  public String method() {
    return "readln";
  }
  
  public String draw() {
    return "<br><br>PAUSED" + drawFiles() + "<br>Enter filename: ";
  }
  
  public Scene whereToNext(String input) {
    String localExitState;
    if (input.isEmpty()) {
      localExitState = "back";
    } else if (input.contains(" ")) {
      localExitState = "fail";
    } else {
      String id = insert(input, pausedGame.getSnapshot());
      localExitState = "success";
    }
    switch (localExitState) {
      case "back":
        return back;
      case "success":
        return new MenuScene(this);
      default:
        return new OopsScene(this);
    }
  }
  
  SaveScene(Game g, Scene b) {
    back = b;
    pausedGame = g;
    this.datastore = b.datastore;
  }

}
