package GizmoballGUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.VolatileImage;

import physics.Angle;
import physics.Circle;
import physics.Vect;


public class ball extends Gizmo implements Drawable {

	int posX;
	int posY;
	int vx;
	int vy;
	Vect velocity;
	Vect g;
	moveBall mb;

	
	
	public ball(Board gb, String name, int x, int y) {
		
		this.gb = gb;
		this.x = x*PIXELSPERL;
		this.y = y*PIXELSPERL;
		this.widthInL = 1;
		this.heightInL = 1;
		this.defaultColor = BALLCOLOR;
		this.name = name;
		this.refCoef = 1.0;
		g= new Vect(Angle.DEG_90, 5);
		velocity = new Vect(Angle.DEG_270, 82);
		posX = x;
		posY = y;
		vx = 10;
		vy = 10;
		
	}
	
	
	public void update(double dtime)
	{
	
		
//		System.out.println(velocity.length());
		//if (velocity.length() <1) velocity = new Vect(Angle.ZERO,0);
//		   double dvel = velocity.y()*dtime + 0.5*g.y() *dtime* dtime;
//		   velocity = velocity.plus(g.times(dtime)); 
//           posX += velocity.x()*dtime; 
//        		   
//           posY += dvel;
	}
	public void move(){
		mb.move(x, y,vx,vy, this.boundingBox());
	}
           
          
//           
           
		//posX++;
	
	
	public Graphics paint(Graphics g) {
		g.setColor(Color.black);
		g.fillOval(posX, posY, gizmoBallSize, gizmoBallSize);
		
		return g;
	}

	private int gizmoBallSize = boardLayout.getInstance().getBoxSize()/2;

	public Circle getShape() {
		return new Circle(posX, posY, gizmoBallSize);
	}


	public Vect getVelocity() {
		
		return velocity;
	}

	public void setVelocity(Vect v)
	{
		velocity= v;
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


	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean containsPoint(Point p) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void paint(Graphics2D g) {
		g.setColor(defaultColor);
	    g.fillOval(x, y, (widthInL*PIXELSPERL)/4, (heightInL*PIXELSPERL)/4);
		
	}
	public Rectangle boundingBox() {
		int radius = (widthInL*PIXELSPERL)/4;
		// effect: Returns the smallest rectangle that completely covers the
		// current position of the ball.

		// a Rectangle is the x,y for the upper left corner and then the
		// width and height
		return new Rectangle(x - radius, y - radius, radius + radius + 1,
				radius + radius + 1);
	}
	
}
