package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;

public class OopsScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
/*  
  private Scene back;
  public String method() {
    return back.method();
  }
  public String draw(Game g, Game[] savedGames) {
    return "<br>Que!? ";
  }
  public SceneI whereToNext(Game g, String input) {
    return back.whereToNext(g, input);
  }
  */
  public OopsScene(Scene b) {
    back = b;
  }

}
