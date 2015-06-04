package jtpcc1.java.monster;

import java.io.Serializable;
import java.util.ArrayList;

/* must be serializable to rebuild fields to be inherited by children */
public class Scene implements Serializable {

  public Scene back;
  public Game g = new Game(); // canot be static otherwise all users play the same game!!
  public ArrayList<GameSnapshot> datastore; // dirty: sim datastore

  public String insert(String n, GameSnapshot s) {
      s.name = n;
      if (datastore != null) {
        datastore.add(s);
      } else {
        datastore = new ArrayList<GameSnapshot>();
        datastore.add(s);
      }
      return n; // dirty: id
  }
  
  public GameSnapshot getGameSnapshotByName(String n) {
    if (datastore != null) {
      for (int i=0; i<datastore.size(); i++ ) {
        if (datastore.get(i) != null) {
          if (datastore.get(i).name.equals(n)) {
            return datastore.get(i);
          }
        }
      }
    }
    return null;
  }
  
  protected String drawFiles() {
    String rows = "";
    if (datastore != null) {
      for (int i=0; i<datastore.size(); i++ ) {
        if (datastore.get(i) != null) {
          rows += "<br>" + datastore.get(i).name;
        }
      }
    } else {
      rows = "<br>EMPTY";
    }
    return rows;
  }
  
  public String method() {
    return "nomethod";
  }
  
  public String draw() {
    return "<br><br>BLANK";
  }
  
  public Scene whereToNext(String input) {
    return this;
  }
  
  Scene() {
    // for menuscene
  }

  Scene(Scene b) {
    back = b;
    g = b.g;
    datastore = b.datastore;
  }

}
