package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;
import java.util.List;

public class FilerScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private Scene back;

  public String method() {
    return "readln";
  }
  
  public String draw() {
    List<Game> savedGames = (List<Game>)datastore.getAttribute("savedGames");
    String rows = "";
    if (savedGames != null) {
      for (int i=0; i<savedGames.size(); i++ ) {
        if (savedGames.get(i) != null) {
          rows += "<br>" + savedGames.get(i).name;
        }
      }
    } else {
      rows = "<br>EMPTY";
    }
    return "<br><br>PAUSED" + rows + "<br>Enter filename: ";
  }
  
  public Scene whereToNext(String input) {
    String newFilerState;
    if (input.contains(" ")) {
      newFilerState = "fail";
    } else {
      newFilerState = "success";
      List<Game> savedGames = (List<Game>)datastore.getAttribute("savedGames");
      Game pausedGame = ((GameScene)((PausedGameMenuScene)back).back).g;
      if (savedGames != null) {
        savedGames.add(pausedGame);
      } else {
        savedGames = Arrays.asList(pausedGame);
      }
      pausedGame.name = input;
      datastore.setAttribute("savedGames", savedGames);
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
