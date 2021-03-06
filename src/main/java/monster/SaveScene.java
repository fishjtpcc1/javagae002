package me.jtp.swdev.monster;

import java.io.Serializable;

public class SaveScene extends Scene implements Serializable {
  static final long serialVersionUID = 1L;
  
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
      String id = insert(input.toLowerCase(), g.getSnapshot());
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
  
  SaveScene(Scene b) {
    super(b);
  }

}
