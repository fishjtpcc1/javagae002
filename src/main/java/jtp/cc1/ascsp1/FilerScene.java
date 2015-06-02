package jtp.cc1.ascsp1;

import java.io.Serializable;

public class FilerScene implements SceneObject, Serializable {
  private static final long serialVersionUID = 1L;
  
  public String method(Game g) {
    return "readln";
  }
  
  public String draw(Game g) {
    String rows = "";
    for (int i=0; i<savedGames.length; i++ ) {
      if (savedGames[i]) {
        rows += "<br>" + savedGames[i].name;
      }
    }
    return "<br>" + rows + "<br>Enter filename: ";
  }
  
  public SceneObject whereToNext(Game g, String input) {
    String newFilerState;
    if (input.contains(" ")) {
      newFilerState = "fail";
    } else {
      newFilerState = "success";
    }
    switch (newFilerState) {
      case "success":
        return new MenuScene();
      default:
        return new OopsScene((SceneObject)this);
    }
  }
  
}
