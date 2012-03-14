package GizmoballGUI;


import java.awt.*;

import physics.*;

class TriangleClass extends Gizmo {  

  private boolean cornerRight;
  private boolean cornerDown;
   
  public TriangleClass(Board gb, String name, int xL, int yL) {
    this.gb = gb;
    this.x = xL*PIXELSPERL;
    this.y = yL*PIXELSPERL;
    this.widthInL = 1;
    this.heightInL = 1;
    this.defaultColor = TRIANGLECOLOR;
    this.name = name;
    this.refCoef = 1.0;
    this.cornerRight = false;
    this.cornerDown = false;
  }

  public void rotate() {
    if (!cornerRight && !cornerDown) {
      cornerRight = true;
    } else if (cornerRight && !cornerDown) {
      cornerDown = true;
    } else if (cornerRight && cornerDown) {
      cornerRight = false;
    } else if (!cornerRight && cornerDown) {
      cornerDown = false;
    }
  }
  
  public String getSaveString() {
    String ss = "Triangle " + name + " " + (x/PIXELSPERL-1) + " "
      + (y/PIXELSPERL-1);
    int rotatecount = 0;
    if (!cornerRight && !cornerDown) {
      rotatecount = 0;
    } else if (cornerRight && !cornerDown) {
      rotatecount = 1;
    } else if (cornerRight && cornerDown) {
      rotatecount = 2;
    } else if (!cornerRight && cornerDown) {
      rotatecount = 3;
    }
    for (int i = 0; i < rotatecount; i++) {
      ss += "\nRotate " + name;
    }
    return ss;
  }
  
  public String getDescription() {
    String retstr = "";
    retstr += "Name: " + name + "\n";
    retstr += "Type: Triangle\n";
    retstr += "Position: (" + (x/PIXELSPERL) + ","
                                + (y/PIXELSPERL) + ")\n";
    retstr += "Connects to:";
    if (triggers.isEmpty())
      retstr += " (none)";

    return retstr;
  }
  
  private Polygon getPolygon() {
    double midx = x + 0.5*widthInL*PIXELSPERL;
    double midy = y + 0.5*heightInL*PIXELSPERL;
    int cornerx = x;
    if (cornerRight) cornerx += widthInL*PIXELSPERL;
    int cornery = y;
    if (cornerDown) cornery += heightInL*PIXELSPERL;
    int reflectedx = cornerx + (int)(2*(midx - cornerx));
    int reflectedy = cornery + (int)(2*(midy - cornery)); 
    int[] xs = {cornerx, cornerx, reflectedx };
    int[] ys = {cornery, reflectedy, cornery };
    return new Polygon(xs, ys, 3);
  }

  
  public void paint(Graphics2D g) {
    g.setColor(defaultColor);
    g.fillPolygon(getPolygon());
  }

@Override
public boolean containsPoint(Point p) {
	// TODO Auto-generated method stub
	return false;
}

}
