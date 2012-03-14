package GizmoballGUI;




class Grid {
	
  private Gizmo grid[][];

  public Grid(int width, int height) {
    grid = new Gizmo[width][height];
  }

  public void add(Gizmo giz) {
    if (giz instanceof Boundary) {
      this.addWalls(giz);
      return;
    }
    
    int x = giz.getX() / Board.PIXELSPERL;
    int y = giz.getY() / Board.PIXELSPERL;

    int width = giz.getWidthInL();
    int height = giz.getHeightInL();

    for (int i = y; i<height+y; i++) {
      for (int j = x; j<width+x; j++) {
	grid[j][i] = giz;
      }
    }
  }

  public void addWalls(Gizmo giz) {
    int width = Board.BOARDWIDTH;
    int height = Board.BOARDHEIGHT;
    
    for (int i = 0; i<width; i++) {
      grid[0][i] = giz;
      grid[height-1][i] = giz;
    }

    for (int j = 1; j<height-1; j++) {
      grid[j][0] = giz;
      grid[j][width-1] = giz;
    }
  }

}
  
