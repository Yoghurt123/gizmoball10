

import java.awt.*;

import physics.*;

class SquareClass extends Gizmo {  

  public SquareClass(Board gb, String name, int xL, int yL) {
    this.gb = gb;
    this.x = xL*PIXELSPERL;
    this.y = yL*PIXELSPERL;
    this.widthInL = 1;
    this.heightInL = 1;
    this.defaultColor = SQUARECOLOR;
    this.name = name;
    this.refCoef = 1.0;
  }
  

  public String getSaveString() {
    return "Square " + name + " " + (x/PIXELSPERL-1) + " " + (y/PIXELSPERL-1);
  }
  
  public String getDescription() {
    String retstr = "";
    retstr += "Name: " + name + "\n";
    retstr += "Type: Square\n";
    retstr += "Position: (" + (x/PIXELSPERL) + ","
                                + (y/PIXELSPERL) + ")\n";
    retstr += "Connects to:";
    if (triggers.isEmpty())
      retstr += " (none)";

    return retstr;
  }
  
  
  public void paint(Graphics2D g) {
    g.setColor(defaultColor);
    g.fillRect(x, y, widthInL*PIXELSPERL, heightInL*PIXELSPERL);
  }


@Override
public void rotate() {
	// TODO Auto-generated method stub
	
}


@Override
public boolean containsPoint(Point p) {
	// TODO Auto-generated method stub
	return false;
}
 
}
