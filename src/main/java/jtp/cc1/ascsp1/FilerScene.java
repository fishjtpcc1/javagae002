package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;

public class FilerScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private Scene back;
  
  public String method() {
    return "readln";
  }
  
  public String draw() {
    MonsterGameServlet.logWarning(datastore);
    Game[] savedGames = (Game[])datastore.getAttribute("savedGames");
    String rows = "";
    if (savedGames != null) {
      for (int i=0; i<savedGames.length; i++ ) {
        if (savedGames[i] != null) {
          rows += "<br>" + savedGames[i].name;
        }
      }
    } else {
      rows = "EMPTY";
    }
    return "<br><br>PAUSED<br>" + rows + "<br>Enter filename: ";
  }
  
  public Scene whereToNext(String input) {
    String newFilerState;
    if (input.contains(" ")) {
      newFilerState = "fail";
    } else {
      newFilerState = "success";
      //g.name = input;
    }
    switch (newFilerState) {
      case "success":
        return new MenuScene();
      default:
        return new OopsScene(this);
    }
  }
  
  FilerScene(Scene b) {
    back = b;
  }

}
