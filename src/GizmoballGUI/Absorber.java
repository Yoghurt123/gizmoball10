package GizmoballGUI;

import java.awt.*;
import java.util.*;
import physics.*;

class Absorber extends Gizmo {  

  private java.util.List balls;

  public Absorber(Board gb, String name, int x1L, int y1L,
		       int x2L, int y2L) {
    this.gb = gb;
    this.x = x1L*PIXELSPERL;
    this.y = y1L*PIXELSPERL;
    this.widthInL = x2L-x1L;
    this.heightInL = y2L-y1L;
    this.defaultColor = ABSORBERCOLOR;
    this.name = name;
    this.refCoef = 0.0;
    this.balls = new ArrayList();
  }
  
  public String getSaveString() {
    return "Absorber " + name + " " + (x/PIXELSPERL-1) + " "
      + (y/PIXELSPERL-1) + " " + (x/PIXELSPERL + widthInL-1) + " "
      + (y/PIXELSPERL + heightInL-1);
  }
  

  public String getDescription() {
    String retstr = "";
    retstr += "Name: " + name + "\n";
    retstr += "Type: Absorber\n";
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
