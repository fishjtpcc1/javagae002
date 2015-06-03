package jtp.cc1.ascsp1;

import java.io.Serializable;

public class OopsScene implements Serializable, Scene {
  private static final long serialVersionUID = 1L;
  private Scene back;
  public String method(Game g) {
    return back.method(g);
  }
  public String draw(Game g, Game[] savedGames) {
    return "<br>Que!? ";
  }
  public Scene whereToNext(Game g, String input) {
    return back.whereToNext(g, input);
  }
  public OopsScene(Scene b) {
    back = b;
  }
}
