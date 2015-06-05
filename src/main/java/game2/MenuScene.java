package me.jtp.swdev.game2;

import java.io.Serializable;

public class MenuScene extends Scene implements Serializable {
  static final long serialVersionUID = 1L;
  
  int upper = 0;
  
  String drawResult() {
    String rows = "";
    for (int i=1; i<=upper; i++) {
      rows += "<br>" + i;
    }
    return rows;
  }
  
  public String method() {
    return "readln";
  }
  
  public String draw() {
    if (upper == 0) {
      return "<br><br>How far to count?<br>1-999: ";
    } else {
      return "<br><br>" + drawResult();
    }
  }
  
  public Scene whereToNext(String input) {
    try {
      upper = Integer.parseInt(input);
    } catch (java.lang.NumberFormatException e) {
      return new OopsScene(this); // know: casting back is not required as is obviously valid in inheritance structure
    }
    return this;
  }
  
  MenuScene() {
    // just to avoid null messiness
  }

  MenuScene(Scene b) {
    super(b);
  }

}

