import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.lang.*;
import physics.*;

class FlipperClass extends Gizmo {  
  private boolean pivotRight;
  private boolean pivotDown;
  private boolean leftFlipper;
  private boolean forwardMotion;
  private boolean backwardMotion;
  private double flipperAngle;
  private double startAngle;
  private double flippedAngle;
    
  public FlipperClass(Board gb, String name, int xL, int yL,
		      boolean left) {
    this.gb = gb;
    this.x = xL*PIXELSPERL;
    this.y = yL*PIXELSPERL;
    this.widthInL = 2;
    this.heightInL = 2;
    this.defaultColor = FLIPPERCOLOR;
    this.name = name;
    this.refCoef = 0.95;
    this.leftFlipper = left;
    this.forwardMotion = false;
    this.backwardMotion = false;
    this.flipperAngle = 270.0;
    this.startAngle = 270.0;
    if (leftFlipper) {
      flippedAngle = 0.0;
      pivotRight = false;
      pivotDown = false;
    } else {
      flippedAngle = 180.0;
      pivotRight = true;
      pivotDown = false;
    }
  }

  public String getSaveString() {
    String ss = "";
    if (leftFlipper) {
      ss += "LeftFlipper ";
    } else {
      ss += "RightFlipper ";
    }
    ss += name + " " + (x/PIXELSPERL-1) + " " + (y/PIXELSPERL-1);
    int rotatecount = 0;
    if (!pivotRight && !pivotDown) {
      rotatecount = 0;
    } else if (pivotRight && !pivotDown) {
      rotatecount = 1;
    } else if (pivotRight && pivotDown) {
      rotatecount = 2;
    } else if (!pivotRight && pivotDown) {
      rotatecount = 3;
    }
    if (!leftFlipper) {
      rotatecount += 3;
      rotatecount %= 4;
    }
    for (int i = 0; i < rotatecount; i++) {
      ss += "\nRotate " + name;
    }
    return ss;
  }
  
  public String getDescription() {
    String retstr = "";
    retstr += "Name: " + name + "\n";
    if (leftFlipper) {
      retstr += "Type: LeftFlipper\n"; 
    } else {
      retstr += "Type: RightFlipper\n";
    }
    retstr += "Position: (" + (x/PIXELSPERL) + ","
                                + (y/PIXELSPERL) + ")\n";
    retstr += "Connects to:";
    if (triggers.isEmpty())
      retstr += " (none)";

    return retstr;
  }

  public Point2D.Double getPivot() {
    double radius = PIXELSPERL*0.25;
    double pivotx, pivoty;
    if (pivotRight) {
      pivotx = x + PIXELSPERL*widthInL - radius;
    } else {
      pivotx = x + radius;
    }
    if (pivotDown) {
      pivoty = y + PIXELSPERL*heightInL - radius;
    } else {
      pivoty = y + radius;
    }
    return new Point2D.Double(pivotx, pivoty);
  }

  private Point2D.Double getStartNonPivot() {
    double radiusdiff = PIXELSPERL*1.5;
    Point2D.Double pivot = getPivot();
    //find nonpivot center by extending in direction of startAngle by
    //appropriate radius difference
    double nonpivotx = pivot.x
      + radiusdiff*Math.cos(Math.toRadians(startAngle));
    double nonpivoty = pivot.y
      - radiusdiff*Math.sin(Math.toRadians(startAngle));
    return new Point2D.Double(nonpivotx, nonpivoty);
  }

  
  
  private java.util.List getShapes() {
	    java.util.List shapes = new ArrayList();
	    double radius = PIXELSPERL*0.25;
	    Point2D.Double pivot = getPivot();
	    Point2D.Double nonpivot = getStartNonPivot();
	    double x1 = pivot.x + radius*Math.cos(Math.toRadians(startAngle+90));
	    double y1 = pivot.y - radius*Math.sin(Math.toRadians(startAngle+90));
	    double x2 = pivot.x + radius*Math.cos(Math.toRadians(startAngle-90));
	    double y2 = pivot.y - radius*Math.sin(Math.toRadians(startAngle-90));
	    double x3 = nonpivot.x + radius*Math.cos(Math.toRadians(startAngle-90));
	    double y3 = nonpivot.y - radius*Math.sin(Math.toRadians(startAngle-90));
	    double x4 = nonpivot.x + radius*Math.cos(Math.toRadians(startAngle+90));
	    double y4 = nonpivot.y - radius*Math.sin(Math.toRadians(startAngle+90));
	    double[] xs = {x1, x2, x3, x4};
	    double[] ys = {y1, y2, y3, y4};
	    Arrays.sort(xs);
	    Arrays.sort(ys);
	    Shape rect = new Rectangle2D.Double(xs[0], ys[0],
						xs[2]-xs[0],
						ys[2]-ys[0]);
	    Shape pivel = new Ellipse2D.Double(pivot.x - radius,
					       pivot.y - radius,
					       2*radius, 2*radius);
	    Shape nonpivel = new Ellipse2D.Double(nonpivot.x - radius,
						  nonpivot.y - radius,
						  2*radius, 2*radius);
	    double rotang = Math.toRadians(startAngle - flipperAngle);
	    AffineTransform rotator =
	      AffineTransform.getRotateInstance(rotang, pivot.x, pivot.y);
	 
	    rect = rotator.createTransformedShape(rect);
	    pivel = rotator.createTransformedShape(pivel);
	    nonpivel = rotator.createTransformedShape(nonpivel);
	    
	    shapes.add(rect);
	    shapes.add(pivel);
	    shapes.add(nonpivel);
	    return shapes;
	  }

  public boolean containsPoint(Point p) {
    java.util.List shapes = getShapes();
    for (int i = 0; i < shapes.size(); i++) {
      Shape tempshape = (Shape)shapes.get(i);
      if (tempshape.contains(p)) {
	return true;
      }
    }
    return false;
  }
  
  public void paint(Graphics2D g) {
	  g.setColor(this.FLIPPERCOLOR);
    java.util.List shapes = getShapes();
    for (int i = 0; i < shapes.size(); i++) {
      Shape shapetodraw = (Shape)shapes.get(i);
      g.fill(shapetodraw);
    }
    Point2D.Double pivot = getPivot();
    g.setColor(Color.red);
    g.fill(new Ellipse2D.Double(pivot.x - 2, pivot.y - 2, 4, 4));
  }

@Override
public void rotate() {
	// TODO Auto-generated method stub
	
}


  
}
