package jtp.cc1.ascsp1;

import java.io.Serializable;

/** a point structure
 */
public class GridRC implements Serializable, Cloneable {
  public int row;
  public int col;

  // Object does have equals but @Overrride is not needed
  public Boolean equals(GridRC o) {
    return (row == o.row && col == o.col);
  }
  
  public GridRC clone() {
    // know: shallow copy but that's aok as fields are all primitives
    GridRC minime = new GridRC(this);
    return minime;
  }
  
  GridRC(int r, int c) {
    row = r;
    col = c;
  }
  
  GridRC(GridRC t) {
    row = t.row;
    col = t.col;
  }
  
}
