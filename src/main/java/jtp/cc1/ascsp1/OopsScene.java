package jtp.cc1.ascsp1;

import java.io.Serializable;

public class OopsScene implements Serializable, SceneObject {
  private static final long serialVersionUID = 1L;
  private SceneObject back;
  public String method(Game g) {
    return "read";
  }
  public String draw(Game g) {
    return "<br>Que!? ";
  }
  public SceneObject whereToNext(Game g, String input) {
    return back.whereToNext(g, input);
  }
  public OopsScene(SceneObject b) {
    back = b;
  }
}
