package jtp.cc1.ascsp1;

import java.io.Serializable;

/** to save complex data in the session class
 * must implement Serializable or runtime error happens
 * must not be a nested class otherwise silent error accessing deserialized members
 */
public class GameSnapshot implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public GridRC userPos;
  public GridRC monsterPos;
  public GridRC goalPos;
  public GridRC[] traps = new GridRC[TRAPS];
  public Boolean monsterIsAwake;
  public String name;

}
