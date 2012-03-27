package newGizmo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;


import newGizmo.model.AbstractGizmoModel;
import newGizmo.model.CircleGizmo;
import newGizmo.model.GizmoBoard;
import newGizmo.model.GizmoWalls;
import newGizmo.model.SquereGizmo;
import newGizmo.model.TriangleGizmo;


public class loadSave {
	
	private HashMap gizmos;
	private ArrayList activeList;
	private ArrayList hitList;
	private String currentFile;
	GizmoBoardView board;
	
	public void load(String filename) throws RuntimeException {
	    
	    gizmos = null;
	    gizmos = new HashMap();
	    
	    activeList = null;
	    activeList = new ArrayList();
	    hitList = null;
	    hitList = new ArrayList();
	    
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
	    	  
	    	  SquereGizmo sq1 = new SquereGizmo(tempX, tempY);
	    	  GizmoBoard.getInstance().addGizmo(sq1);
	    	  
//		SquereClass square = new SquereClass(this, command.nextToken(),
//						     Integer.parseInt(command.nextToken())+1,
//						     Integer.parseInt(command.nextToken())+1);
		gizmos.put(sq1.getName(), sq1);
//		grid.add(square);
	      } else if (type.equals("Triangle")) {
	    	  
	    	  String tempName = command.nextToken();
	    	  int tempX = Integer.parseInt(command.nextToken()+1);
	    	  int tempY = Integer.parseInt(command.nextToken()+1);
	    	  
	    	  TriangleGizmo tr1 = new TriangleGizmo(tempX, tempY);
	    	  GizmoBoard.getInstance().addGizmo(tr1);
//		TriangleClass triangle = new TriangleClass(this, command.nextToken(),
//							   Integer.parseInt(command.nextToken())+1,
//							   Integer.parseInt(command.nextToken())+1);
//		gizmos.put(triangle.getName(), triangle);
//		grid.add(triangle);
	      } else if (type.equals("Circle")) {
	    	  String tempName = command.nextToken();
	    	  int tempX = Integer.parseInt(command.nextToken()+1);
	    	  int tempY = Integer.parseInt(command.nextToken()+1);
	    	  
	    	  CircleGizmo ci1 = new CircleGizmo(tempX, tempY);
	    	  GizmoBoard.getInstance().addGizmo(ci1);
	    	  
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
		
		String consume = command.nextToken();
		GizmoWalls consumer;
		if (consume.equals("Walls"))
		  consumer = walls;
		else
		  consumer = (GizmoWalls)gizmos.get(consume);
		
		int actionNum = 0;

	    currentFile = filename; 
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
		      Iterator saveElement = gizmos.values().iterator();
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
