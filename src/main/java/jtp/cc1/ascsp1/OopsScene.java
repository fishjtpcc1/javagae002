package jtp.cc1.ascsp1;

import java.io.Serializable;

public class OopsScene implements Serializable, SceneI {
  private static final long serialVersionUID = 1L;
  private SceneI back;
  public String method(Game g) {
    return back.method(g);
  }
  public String draw(Game g, Game[] savedGames) {
    return "<br>Que!? ";
  }
  public SceneI whereToNext(Game g, String input) {
    return back.whereToNext(g, input);
  }
  public OopsScene(SceneI b) {
    back = b;
  }
}
