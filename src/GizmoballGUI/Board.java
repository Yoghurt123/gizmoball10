package GizmoballGUI;


import javax.swing.*;
import java.awt.*;
import java.util.*;

import javax.swing.Timer;
import java.io.*;
import physics.*;

public class Board extends JPanel implements Observer {

  protected static final int BOARDWIDTH = 22;
  protected static final int BOARDHEIGHT = 22;
  protected static final int PIXELSPERL = 30;
  protected static final int TICKSPERBLINK = 10;
  protected static final int TICKSPERHITBLINK = 20;
  protected static final int ABSORBERFIREVY = -50;
  protected static final int FLIPPERANGVEL = 1080;
  protected static final Color BLINKCOLOR = Color.YELLOW;
  protected static final Color WALLCOLOR = (new Color(235,235,235));
  protected static final Color SQUARECOLOR = Color.RED;
  protected static final Color CIRCLECOLOR = Color.GREEN;
  protected static final Color TRIANGLECOLOR = Color.CYAN;
  protected static final Color ABSORBERCOLOR = Color.MAGENTA;
  protected static final Color FLIPPERCOLOR = Color.ORANGE;
  protected static final Color BALLCOLOR = Color.WHITE;
  protected static final Color GRIDCOLOR = Color.CYAN;
 

  private Timer timer;
  private boolean mode;
  private Gizmo Walls;
  
  private HashMap gizmos;
  
  private Grid grid;
  
  private Gizmo active;

  private ArrayList activeList;
  private ArrayList hitList;

  private String currentFile;

  private String infoString;
  private StringBuffer soundEffects;
  private ballModel ball;
  private animationEventListener eventListener;

  public Board(ballModel bm) {

    super();
    ball = bm;
    
    ball.addObserver(this);
    
    eventListener = new animationEventListener(ball);
    
    this.setBackground(new Color(0,0,0));

    gizmos = new HashMap();
    activeList = new ArrayList();
    hitList = new ArrayList();

    grid = new Grid(BOARDWIDTH, BOARDHEIGHT);

    currentFile = "default.gb";
    infoString = "";
    soundEffects = new StringBuffer("");

    Walls = new Boundary(this);
    grid.addWalls(Walls);
    timer = new Timer(50, eventListener);
    mode = false;
   // ball b;

  }
  
  public boolean isFocusable() { return true; }
  
//  private static Board instance = null;
//
//	public static Board getInstance() {
//		if (instance == null)
//			instance = new Board();
//		return instance;
//	}

  public void paint(Graphics g) {

    Graphics2D g2 = (Graphics2D)g;
    super.paint(g);
    Walls.paint(g2);
    if (!mode)
      paintGrid(g);
    ball.paint(g2);
    
    Iterator gizmoPainter = gizmos.values().iterator();
    while (gizmoPainter.hasNext()) {
      Gizmo newGiz = (Gizmo)gizmoPainter.next();
      newGiz.paint(g2);
    }

  }
  
  public void setMode(boolean m) {
	  // modifies: this
	  // effects: changes the mode to <m>.

	  if (mode == true) {
		// we're about to change mode: turn off all the old listeners
		removeMouseListener(eventListener);
		removeMouseMotionListener(eventListener);
		removeKeyListener(eventListener);
	  }

	  mode = m;

	  if (mode == true) {
		// the mode is true: turn on the listeners
		addMouseListener(eventListener);
		addMouseMotionListener(eventListener);
		addKeyListener(eventListener);
		requestFocus();           // make sure keyboard is directed to us
		timer.start();
	  }
	  else {
		timer.stop();
	  }
	}
  
  public void addFlipper(String name, String type, int x, int y, boolean d){
	  if (!mode){
		  Gizmo tempGiz = null;
		  
		  if (type.equals("FlipperL"))
			  tempGiz = new FlipperClass(this, name, x, y, d);
		  else if (type.equals("FlipperR"))
			  tempGiz = new FlipperClass(this, name, x, y, d);
		  gizmos.put(name, tempGiz);
		  System.out.println(gizmos);
		  active = (Gizmo)gizmos.get(name);
		  
		  repaint(active.boundingBox());
		  requestFocus();
	  }
  }
    
  public void addGizmo(String name, String type, int x, int y) {
    if (!mode) {
      
      Gizmo tempGiz = null;
    
      if (type.equals("Square"))
	tempGiz = new SquareClass(this, name, x, y);
      else if (type.equals("Circle"))
	tempGiz = new CircleClass(this, name, x, y);
      else if (type.equals("Triangle"))
	tempGiz = new TriangleClass(this, name, x, y);
//      else if (type.equals("Ball"))
//    tempGiz = new ball(this, name, x, y);
      gizmos.put(name, tempGiz);
      active = (Gizmo)gizmos.get(name);
      
      repaint(active.boundingBox());
      requestFocus();
    }
  }
  
//  public void delteGizmo(int x, int y){
//	  gizmos.remove(gizmos.)
//  }

  public void addAbsorber(String name, String type,int x, int y, int width, int height) {
    if (!mode) {
      
      Gizmo tempGiz = null;
      
      if (type.equals("Absorber"))
	tempGiz = new Absorber(this, name, x, y, width, height);

      if (tempGiz != null) {
	gizmos.put(name, tempGiz);
	active = (Gizmo)gizmos.get(name);
      
	repaint(active.boundingBox());
	requestFocus();
      }
    }
  }


  public void load(String filename) throws RuntimeException {
    
    gizmos = null;
    gizmos = new HashMap();
    
    activeList = null;
    activeList = new ArrayList();
    hitList = null;
    hitList = new ArrayList();


    grid = null;
    grid = new Grid(BOARDWIDTH, BOARDHEIGHT);

    Walls = null;
    Walls = new Boundary(this);
    grid.addWalls(Walls);

    
    String file = fileRead(filename);
    StringTokenizer lineReader = new StringTokenizer(file, "\n");
    
    while (lineReader.hasMoreTokens()) {
      String line = lineReader.nextToken();


      StringTokenizer command = new StringTokenizer(line);

      String type = command.nextToken();
      if (type.equals("Square")) {
	SquareClass square = new SquareClass(this, command.nextToken(),
					     Integer.parseInt(command.nextToken())+1,
					     Integer.parseInt(command.nextToken())+1);
	gizmos.put(square.getName(), square);
	grid.add(square);
      } else if (type.equals("Triangle")) {
	TriangleClass triangle = new TriangleClass(this, command.nextToken(),
						   Integer.parseInt(command.nextToken())+1,
						   Integer.parseInt(command.nextToken())+1);
	gizmos.put(triangle.getName(), triangle);
	grid.add(triangle);
      } else if (type.equals("Circle")) {
	CircleClass circle = new CircleClass(this, command.nextToken(),
					     Integer.parseInt(command.nextToken())+1,
					     Integer.parseInt(command.nextToken())+1);
	gizmos.put(circle.getName(), circle);
	grid.add(circle);
      } else if (type.equals("Absorber")) {
	Absorber absorber = new Absorber(this, command.nextToken(),
						   Integer.parseInt(command.nextToken())+1,
						   Integer.parseInt(command.nextToken())+1,
						   Integer.parseInt(command.nextToken())+1,
						   Integer.parseInt(command.nextToken())+1);
	gizmos.put(absorber.getName(), absorber);
	grid.add(absorber);
      } else if (type.equals("Rotate")) {
	String name = command.nextToken();
	((Gizmo)gizmos.get(name)).rotate();
      } else if (type.equals("Connect")) {
	String giz = command.nextToken();
	if (giz.equals("Walls"))
	    active = Walls;
	else
	  active = (Gizmo)gizmos.get(giz);
	
	String consume = command.nextToken();
	Gizmo consumer;
	if (consume.equals("Walls"))
	  consumer = Walls;
	else
	  consumer = (Gizmo)gizmos.get(consume);
	
	int actionNum = 0;

    currentFile = filename;
      }
    }
  }

  public HashMap getGizmos() {
    HashMap gizmoCopy = (HashMap)gizmos.clone();
    gizmoCopy.put("Walls", Walls);
    return gizmoCopy;
  }

  public Grid getGrid() {
    return grid;
  }

  private static String fileRead(String filename) {
    if (filename == null)
      throw new RuntimeException("No file specified");
    String answer = new String();
    try {
      BufferedReader in = new BufferedReader(new FileReader(filename));
      for (String line = in.readLine(); line != null;
	   line = in.readLine()) {
	if (!(line.trim().equals("") || line.startsWith("#")))
	  answer += line.trim() + "\n";
      }
    }
    catch (Exception e) {
      throw new RuntimeException("File not accessible");
    }

    return answer;
  }

  public String getInfo() {
    if (active != null)
      infoString = active.getDescription();
    else
      infoString = "";

    return infoString;
  }

  public void rotate() {
    if (active != null)
      active.rotate();
  }



  public boolean getMode() {
    return mode;
  }

  public void paintGrid(Graphics g) {
	  if(mode == false){
    g.setColor(GRIDCOLOR);
    for (int i = 1; i < BOARDWIDTH; i++) {
      g.drawLine(i*PIXELSPERL, PIXELSPERL,
                 i*PIXELSPERL, (BOARDHEIGHT-1)*PIXELSPERL);
    }
    for (int i = 1; i < BOARDHEIGHT; i++) {
      g.drawLine(PIXELSPERL, i*PIXELSPERL,
                 (BOARDWIDTH-1)*PIXELSPERL, i*PIXELSPERL);
    }
	  }
  }

  public Gizmo getGizmoAt(Point p) {
    
    Iterator clickCheck = gizmos.values().iterator();
      while (clickCheck.hasNext()) {
	Gizmo nextGiz = (Gizmo)clickCheck.next();
	if (nextGiz.containsPoint(p))
	  return nextGiz;
      }

      return null;
  }
  
  public void save(String filename) {
	    PrintStream output = System.out;
	    output.println("Filename: " + filename);
	    
	    try {
	      if (!((filename.substring(filename.length()-3)).equals(".gb")))
		filename = filename +".gb";
	      
	      
	      
	      
	      
	      
	      
	      
	      output = new PrintStream(new FileOutputStream(filename));
	      Iterator saveElement = gizmos.values().iterator();
	      while (saveElement.hasNext()) {
		Gizmo nextGiz = (Gizmo)saveElement.next();
		output.println(nextGiz.getSaveString());
	      }

	      currentFile = filename;
	      
	    } catch (FileNotFoundException e) {
	      output.println("File " + filename + " not found. Please try again.");
	    }
	  }

@Override
public void update(Observable o, Object arg) {
	
	// Observable Model passes a rectangle of damaged area
	// to be updated during painting	
	Rectangle repaintArea = (Rectangle) arg;
				
	// Have Swing tell the AnimationWindow to run its paint()
	// method.  One could also call repaint(), but this would
	// repaint the entire window as opposed to only the portion that
	// has changed.
	this.repaint();

//	repaint(repaintArea.x,
//	repaintArea.y,
//	repaintArea.width,
//	repaintArea.height);
}

 }

