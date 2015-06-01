package jtp.cc1.ascsp1;

import java.io.Serializable;

/** a point structure
 */
public class GridRC implements Serializable {
  public int row;
  public int col;

  // Object does have equals but @Overrride is not needed
  public Boolean equals(GridRC o) {
    return (row == o.row && col == o.col);
  }
  
  GridRC(int r, int c) {
    row = r;
    col = c;
  }
}
