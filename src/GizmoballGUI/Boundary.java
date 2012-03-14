package GizmoballGUI;


import java.awt.*;
import java.util.*;
import physics.*;

class Boundary extends Gizmo {  

  public Boundary(Board gb) {
    this.gb = gb;
    this.x = 0;
    this.y = 0;
    this.widthInL = BOARDWIDTH;
    this.heightInL = BOARDHEIGHT;
    this.defaultColor = WALLCOLOR;
    this.name = "OuterWalls";
    this.refCoef = 1.0;
  }

  
  public String getDescription() {
    String retstr = "";
    retstr += "Name: OuterWalls\n";
    retstr += "Type: OuterWalls\n";
    retstr += "Position: (0,0)\n";
    retstr += "Connects to:";
    if (triggers.isEmpty())
      retstr += " (none)";

    return retstr;
  }

  public boolean containsPoint(Point p) {

    if (p.getX() <= x+PIXELSPERL
	|| p.getX() >= x+widthInL*PIXELSPERL-PIXELSPERL
	|| p.getY() <= y+PIXELSPERL
	|| p.getY() >= y+heightInL*PIXELSPERL-PIXELSPERL)
      return true;
    else
      return false;
  }

  public void paint(Graphics2D g) {
    g.setColor(defaultColor);
    g.fillRect(x, y, PIXELSPERL, heightInL*PIXELSPERL);
    g.fillRect(x, y, widthInL*PIXELSPERL, PIXELSPERL);
    g.fillRect(x+widthInL*PIXELSPERL-PIXELSPERL, y,
	       PIXELSPERL, heightInL*PIXELSPERL);
    g.fillRect(x, y+heightInL*PIXELSPERL-PIXELSPERL,
	       widthInL*PIXELSPERL, PIXELSPERL);
  }


@Override
public void rotate() {
	// TODO Auto-generated method stub
	
}


@Override
public String getSaveString() {
	// TODO Auto-generated method stub
	return null;
}


}
