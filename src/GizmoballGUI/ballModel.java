package GizmoballGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Observable;

import physics.Angle;
import physics.Vect;

public class ballModel extends Observable {


	/*
	 * @author murray wood based on MIT Example.java
	 * The MODEL - just the ball.
	 */

	
		// Overview: A BouncingBall is a mutable data type. It simulates a
		// rubber ball bouncing inside a two dimensional box.

		private int vx = (int) ((Math.random() * 10.0) + 10.0);
		private int vy = (int) ((Math.random() * 10.0) + 10.0);
		private int radius = 6;
		private int PIXELSPERL = 30;
		int posX;
		int posY;
		Vect velocity;
		Vect g;

		private Color color = new Color(255, 255, 255);

		public ballModel(int x,int y) {
			super();
			// Set start angle for the ball
			g= new Vect(Angle.DEG_90, 5);
			//Set initial launch velocity
			velocity = new Vect(Angle.DEG_315, 20);
			posX = x;
			posY = y;
			
		}

		public Color getColor() {
			return color;
		}

		public int getRadius() {
			return radius;
		}

		public int getX() {
			return this.posX;
		}

		public int getY() {
			return this.posY;
		}
		
		public Graphics paint (Graphics g){
			g.setColor(getColor());
			g.fillOval(posX, posY, radius+radius, radius+radius);
			return g;
			
		}
		
		public void update()
		{
			Rectangle oldPos = this.boundingBox();
			
			if (velocity.length() <2) velocity = new Vect(Angle.ZERO,0);
			   double dvel = velocity.y() + 0.5*g.y();//*0.01*0.01;//* time^2
			   velocity = velocity.plus(g.times(1)); 
	           posX += velocity.x();
	           posY += dvel;
	           
	           posX += vx;
				if (posX <= (radius + PIXELSPERL)) {
					posX = (radius + PIXELSPERL);
					vx = -vx;
				}
				if (posX >= PIXELSPERL*20 - radius) {
					posX = PIXELSPERL*20 - radius;
					vx = -vx;
				}

				posY += vy;
				if (posY <= (radius + PIXELSPERL)) {
					posY = (radius + PIXELSPERL);
					vy = -vy;
				}
				if (posY >= PIXELSPERL*20 - radius) {
					posY = PIXELSPERL*20 - radius;
					vy = -vy;
				}
	        // Add to repaint area
				Rectangle repaintArea = oldPos.union(this.boundingBox());

				// Cause an update to the View with repaint area as argument
				// Need to set changed before calling notifyObservers

				this.setChanged();
				this.notifyObservers(repaintArea);
				
				
	        		   
		}

		public Vect GetVelocity(){
			return velocity;
		}
		
		public void SetVelocity(Vect v){
			velocity = v;
		}

		public void randomBump() {
			// modifies: this
			// effects: Changes the velocity of the ball by a random amount
			vx += (int) ((Math.random() * 10.0) - 5.0);
			vx = -vx;
			vy += (int) ((Math.random() * 10.0) - 5.0);
			vy = -vy;
		}

		/*
		 * All painting has gone to the VIEW
		 * 
		 * public void paint(Graphics g) { // modifies: the Graphics object <g>. //
		 * effects: paints a circle on <g> reflecting the current position // of the
		 * ball.
		 * 
		 * // the "clip rectangle" is the area of the screen that needs to be //
		 * modified Rectangle clipRect = g.getClipBounds();
		 * 
		 * // For this tiny program, testing whether we need to redraw is // kind of
		 * silly. But when there are lots of objects all over the // screen this is
		 * a very important performance optimization if
		 * (clipRect.intersects(this.boundingBox())) { g.setColor(color);
		 * g.fillOval(x-radius, y-radius, radius+radius, radius+radius); } }
		 */

		public Rectangle boundingBox() {
			// effect: Returns the smallest rectangle that completely covers the
			// current position of the ball.

			// a Rectangle is the x,y for the upper left corner and then the
			// width and height
			return new Rectangle(posX - radius, posY - radius, radius + radius + 1,
					radius + radius + 1);
		}
	}

