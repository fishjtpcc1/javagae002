package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;

public class OopsScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;

  private Scene back;

  public String method() {
    return back.method();
  }

  public String draw() {
    return "<br>Que!? ";
  }
  
  public Scene whereToNext(String input, HttpSession datastore) {
    return back.whereToNext(input);
  }
  
  OopsScene(Scene b) {
    back = b;
  }

}
