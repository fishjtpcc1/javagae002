package me.jtp.swdev.game2;

import java.io.Serializable;

public class OpenScene extends Scene implements Serializable {
  static final long serialVersionUID = 1L;
  
  public String method() {
    return "readln";
  }
  
  public String draw() {
    return "<br>" + drawFiles() + "<br>Enter filename: ";
  }
  
  public Scene whereToNext(String input) {
    String localExitState;
    GameSnapshot s = null;
    if (input.isEmpty()) {
      localExitState = "back";
    } else {
      s = getGameSnapshotByName(input.toLowerCase());
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
        g.restartSnapshot(s);
        return new GameScene(this);
      default:
        return new OopsScene(this);
    }
  }
  
  OpenScene(Scene b) {
    super(b);
  }

}
