package me.jtp.swdev.game2;

import java.io.Serializable;

public class OopsScene extends Scene implements Serializable {
  static final long serialVersionUID = 1L;

  public String method() {
    return back.method();
  }

  public String draw() {
    return "<br>Que!? ";
  }
  
  public Scene whereToNext(String input) {
    return back.whereToNext(input);
  }
  
  OopsScene(Scene b) {
    super(b);
  }

}
