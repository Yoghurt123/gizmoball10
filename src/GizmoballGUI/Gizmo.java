package GizmoballGUI;




import java.awt.*;
import java.util.*;

class Gizmo {

  protected static final int BOARDWIDTH = Board.BOARDWIDTH;
  protected static final int BOARDHEIGHT = Board.BOARDHEIGHT;
  protected static final int PIXELSPERL = Board.PIXELSPERL;
  protected static final int TICKSPERBLINK = Board.TICKSPERBLINK;
  protected static final int TICKSPERHITBLINK = Board.TICKSPERHITBLINK;
  protected static final int ABSORBERFIREVY = Board.ABSORBERFIREVY;
  protected static final int FLIPPERANGVEL = Board.FLIPPERANGVEL;
  protected static final Color BLINKCOLOR = Board.BLINKCOLOR;
  protected static final Color WALLCOLOR = Board.WALLCOLOR;
  protected static final Color SQUARECOLOR = Board.SQUARECOLOR;
  protected static final Color CIRCLECOLOR = Board.CIRCLECOLOR;
  protected static final Color TRIANGLECOLOR = Board.TRIANGLECOLOR;
  protected static final Color ABSORBERCOLOR = Board.ABSORBERCOLOR;
  protected static final Color FLIPPERCOLOR = Board.FLIPPERCOLOR;
  protected static final Color BALLCOLOR = Board.BALLCOLOR;

  protected Board gb;
  protected int x;
  protected int y;
  protected int widthInL;
  protected int heightInL;
  protected Color defaultColor;
  protected boolean recentlyhit = false;
  protected java.util.List triggers = new ArrayList();
  private int boxSize = 20;
  
  protected String name;
  protected double refCoef;


  public void hitblink() {
    if (!recentlyhit) {
      int red = defaultColor.getRed();
      int green = defaultColor.getGreen();
      int blue = defaultColor.getBlue();
      int diff = Math.abs(red - green) + Math.abs(green-blue) + Math.abs(blue-red);
      double scale = 0.2*diff;
      Color newcolor =
	new Color(Math.min(255, (int)(defaultColor.getRed()+scale+30)),
		  Math.min(255, (int)(defaultColor.getGreen()+scale+30)),
		  Math.min(255, (int)(defaultColor.getBlue()+scale+30)));
      defaultColor = newcolor;
    }

  }

  public void rotate(){};


  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }

  public int getWidthInL() {
    return widthInL;
  }

  public int getHeightInL() {
    return heightInL;
  }

  public String getName() {
    return name;
  }

  public double getRefCoef() {
    return refCoef;
  }

  public Rectangle boundingBox() {
    return new Rectangle(x, y, widthInL*PIXELSPERL, heightInL*PIXELSPERL);
  }

  public  String getSaveString(){
	return name;};

  public  String getDescription(){
	return name;};

  public  boolean containsPoint(Point p){
	return recentlyhit;};

  public  void paint(Graphics2D g){};

  public boolean equals(Object obj) {
    if (obj instanceof Gizmo) {
      Gizmo other = (Gizmo)obj;
      if (other.widthInL == this.widthInL
	  && other.heightInL == this.heightInL
	  && other.refCoef == this.refCoef
	  && other.defaultColor.equals(this.defaultColor)
	  && other.name.equals(this.name) ) {
	return true;
      }
    }
    return false;
  }
  
  private static Gizmo instance = null;

  //Why the heck is singleton done from abstract class for all gizmos ?!
	public static Gizmo getInstance() {
		if (instance == null)
			instance = new Gizmo();
		return instance;
	}
	
	public int getBoxSize() {
		return boxSize;
	}
  
}
