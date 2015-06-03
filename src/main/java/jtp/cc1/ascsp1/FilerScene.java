package jtp.cc1.ascsp1;

import java.io.Serializable;
import javax.servlet.http.*;
import java.util.List;
import java.util.Arrays;

public class FilerScene extends Scene implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private Game pausedGame;
  public List<Game> datastore; // dirty: sim datastore

  public String method() {
    return "readln";
  }
  
  public String draw() {
    return "<br><br>PAUSED" + drawFiles(datastore) + "<br>Enter filename: ";
  }
  
  public Scene whereToNext(String input) {
    String newFilerState;
    if (input.contains(" ")) {
      newFilerState = "fail";
    } else {
      newFilerState = "success";
      if (datastore != null) {
        datastore.add(pausedGame);
      } else {
        datastore = Arrays.asList(pausedGame);
      }
      pausedGame.name = input;
    }
    switch (newFilerState) {
      case "success":
        return new MenuScene(this);
      default:
        return new OopsScene(this);
    }
  }
  
  FilerScene(Game g, List<Game> datastore) {
    pausedGame = g;
    this.datastore = datastore;
  }

}
