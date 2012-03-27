package newGizmo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import newGizmo.controller.EventListener;
import newGizmo.model.AbstractGizmoModel;
import newGizmo.model.CircleGizmo;
import newGizmo.model.GizmoBall;
import newGizmo.model.GizmoBoard;
import newGizmo.model.GizmoWalls;
import newGizmo.model.LeftFlipper;
import newGizmo.model.RightFlipper;
import newGizmo.model.SquereGizmo;
import newGizmo.model.TriangleGizmo;

public class GizmoBoardView extends Canvas {

	public GizmoBoardView() {
		long ltime = Utils.Sec2Msec(GizmoSettings.getInstance().getBallMovementUpdateDtime());
		GizmoDriver.getInstance().runShudledTask(new GizmoUpdateViewTask(),
				1000, ltime);
	}

	private class GizmoUpdateViewTask extends GizmoDriver.GizmoTask {
		public GizmoUpdateViewTask() {
			GizmoDriver.getInstance().super();
		}

		@Override
		public void onRun(GizmoBoard board) {
			switch (getCurentDriverState())
			{
			case RUN_STATE: 
				break;
			}
			paint(getGraphics());

		}

	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 600, 600);
		g.setColor(Color.BLACK);
		GizmoBall ball = GizmoBoard.getInstance().getBall();



		
	
		
		if (ball != null)
			ball.paint(g);

		for (AbstractGizmoModel giz : GizmoBoard.getInstance().getGizmos()) {
			giz.paint(g);
		}

	}
	
	private HashMap gizmos;
	private ArrayList activeList;
	private ArrayList hitList;
	private String currentFile;
	//GizmoBoardView board;
	
	public void load(String filename) throws RuntimeException {
	    
	    gizmos = null;
	    gizmos = new HashMap();
	    
	    activeList = null;
	    activeList = new ArrayList<Object>();
	    hitList = null;
	    hitList = new ArrayList<Object>();
	    
	    currentFile = "default.gb";

//	    Walls = null;
//	    Walls = new Boundary(this);
	    GizmoWalls walls = new GizmoWalls(0, 0, 600, 600);
	    GizmoBoard.getInstance().addGizmo(walls);

	    
	    String file = fileRead(filename);
	    StringTokenizer lineReader = new StringTokenizer(file, "\n");
	    
	    while (lineReader.hasMoreTokens()) {
	      String line = lineReader.nextToken();


	      StringTokenizer command = new StringTokenizer(line);

	      String type = command.nextToken();
	      if (type.equals("Square")) {
	    	  String tempName = command.nextToken();
	    	  int tempX = Integer.parseInt(command.nextToken()+1);
	    	  int tempY = Integer.parseInt(command.nextToken()+1);
	    	  System.out.println("Square" + ((tempX*3)-3) + " " + ((tempY*3)-3));
	    	  SquereGizmo sq1 = new SquereGizmo((tempX*3)-3, (tempY*3)-3);
	    	  GizmoBoard.getInstance().addGizmo(sq1);
	    	  
//		SquereClass square = new SquereClass(this, command.nextToken(),
//						     Integer.parseInt(command.nextToken())+1,
//						     Integer.parseInt(command.nextToken())+1);
		gizmos.put(sq1.getName(), sq1);
//		grid.add(square);
	      } else if (type.equals("Triangle")) {
	    	  
	    	  String tempName = command.nextToken();
	    	  int tempX = Integer.parseInt(command.nextToken()+1);
	    	  int tempY = Integer.parseInt(command.nextToken()+2);
	    	  
	    	  System.out.println("Triangle" + ((tempX*3)-3) + " " + ((tempY*3)-6));
	    	  
	    	  TriangleGizmo tr1 = new TriangleGizmo((tempX*3)-3, (tempY*3)-6);
	    	  GizmoBoard.getInstance().addGizmo(tr1);
	    	  gizmos.put(tr1.getName(), tr1);
//		TriangleClass triangle = new TriangleClass(this, command.nextToken(),
//							   Integer.parseInt(command.nextToken())+1,
//							   Integer.parseInt(command.nextToken())+1);
//		gizmos.put(triangle.getName(), triangle);
//		grid.add(triangle);
	      } else if (type.equals("Circle")) {
	    	  String tempName = command.nextToken();
	    	  int tempX = Integer.parseInt(command.nextToken()+1);
	    	  int tempY = Integer.parseInt(command.nextToken()+2);
	    	  
	    	  System.out.println("Circle" + ((tempX*3)-3) + " " + ((tempY*3)-6));
	    	  
	    	  CircleGizmo ci1 = new CircleGizmo((tempX*3)-3, (tempY*3)-6);
	    	  GizmoBoard.getInstance().addGizmo(ci1);
	    	  gizmos.put(ci1.getName(), ci1);
	      }else if (type.equals("LeftFlipper")){
	    	  String tempName = command.nextToken();
	    	  int tempX = Integer.parseInt(command.nextToken()+1);
	    	  int tempY = Integer.parseInt(command.nextToken()+2);
	    	  
	    	  System.out.println("Left Flipper" + ((tempX*3)-3) + " " + ((tempY*3)-6));
	    	  
	    	  LeftFlipper  fl1 = new LeftFlipper((tempX*3)-3,(tempY*3)-6);
	    	  GizmoBoard.getInstance().addGizmo(fl1);
	      }else if (type.equals("RightFlipper")){
	    	  String tempName = command.nextToken();
	    	  int tempX = Integer.parseInt(command.nextToken()+1);
	    	  int tempY = Integer.parseInt(command.nextToken()+2);
	    	  
	    	  System.out.println("Right Flipper" + ((tempX*3)-3) + " " + ((tempY*3)-6));
	    	  
	    	  RightFlipper  fr1 = new RightFlipper((tempX*3)-3,(tempY*3)-6);
	    	  GizmoBoard.getInstance().addGizmo(fr1);
	      }
//		CircleClass circle = new CircleClass(this, command.nextToken(),
//						     Integer.parseInt(command.nextToken())+1,
//						     Integer.parseInt(command.nextToken())+1);
//		gizmos.put(circle.getName(), circle);
//		grid.add(circle);
//	      } else if (type.equals("Absorber")) {
//		Absorber absorber = new Absorber(this, command.nextToken(),
//							   Integer.parseInt(command.nextToken())+1,
//							   Integer.parseInt(command.nextToken())+1,
//							   Integer.parseInt(command.nextToken())+1,
//							   Integer.parseInt(command.nextToken())+1);
//		gizmos.put(absorber.getName(), absorber);
//		grid.add(absorber);
//	      } else if (type.equals("Rotate")) {
//		String name = command.nextToken();
//		((Gizmo)gizmos.get(name)).rotate();
//	      } else if (type.equals("Connect")) {
//		String giz = command.nextToken();
//		if (giz.equals("Walls"))
//		    active = Walls;
//		else
//		  active = (Gizmo)gizmos.get(giz);
	      else{
	    	  
//		
//		String consume = command.nextToken();
//		GizmoWalls consumer;
//		if (consume.equals("Walls"))
//		  consumer = walls;
//		else
//		  consumer = (GizmoWalls)gizmos.get(consume);
//		
//		int actionNum = 0;
//
//	    currentFile = filename;
	      }
	    }
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
	 
	 public void save(String filename) {
		   PrintStream output = System.out;
		    output.println("Filename: " + filename);
		    
		    try {
		      if (!((filename.substring(filename.length()-3)).equals(".txt")))
			filename = filename +".txt";
		     
		      output = new PrintStream(new FileOutputStream(filename));
		      Iterator saveElement = GizmoBoard.getInstance().getGizmos().iterator();
		      while (saveElement.hasNext()) {
			AbstractGizmoModel nextGiz = (AbstractGizmoModel)saveElement.next();
			output.println(nextGiz.getSaveString());
		      }

		      currentFile = filename;
		      
		    } catch (FileNotFoundException e) {
		      output.println("File " + filename + " not found. Please try again.");
		    }
		  }

}
