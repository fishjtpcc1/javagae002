package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;

public class FilerScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
  
/*
  public String method(Game g) {
    return "readln";
  }
  
  public String draw(Game g, Game[] savedGames) {
    String rows = "";
    for (int i=0; i<savedGames.length; i++ ) {
      if (savedGames[i] != null) {
        rows += "<br>" + savedGames[i].name;
      }
    }
    return "<br>" + rows + "<br>Enter filename: ";
  }
  
  public Scene whereToNext(Game g, String input) {
    String newFilerState;
    if (input.contains(" ")) {
      newFilerState = "fail";
    } else {
      newFilerState = "success";
      g.name = input;
    }
    switch (newFilerState) {
      case "success":
        return new MenuScene();
      default:
        return new OopsScene(this);
    }
  }
  */
}
