package jtp.cc1.ascsp1;

import java.io.Serializable;

private class FilerScene implements SceneObject, Serializable {
  private static final long serialVersionUID = 1L; // know: because HttpServlet is serializable
  public String method() {
    return "readln";
  }
  public String draw() {
    return "<br>--my saved files--<br>Enter filename: ";
  }
  public SceneObject whereToNext(String input) {
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
