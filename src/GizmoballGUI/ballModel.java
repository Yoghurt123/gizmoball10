package GizmoballGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Observable;

public class ballModel extends Observable {


	/*
	 * @author murray wood based on MIT Example.java
	 * The MODEL - just the ball.
	 */

	
		// Overview: A BouncingBall is a mutable data type. It simulates a
		// rubber ball bouncing inside a two dimensional box.

		private int x = (int) ((Math.random() * 100.0) + 100.0);
		private int y = (int) ((Math.random() * 100.0) + 100.0);
		private int vx = (int) ((Math.random() * 10.0) + 10.0);
		private int vy = (int) ((Math.random() * 10.0) + 10.0);
		private int radius = 6;

		private Color color = new Color(255, 255, 255);

		public ballModel() {
			super();
		}

		public Color getColor() {
			return color;
		}

		public int getRadius() {
			return radius;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
		
		public Graphics paint (Graphics g){
			g.setColor(getColor());
			g.fillOval(x, y, radius+radius, radius+radius);
			return g;
			
		}

		public void move() {
			// modifies: this
			// effects: Move the ball according to its velocity. Reflections off
			// walls cause the ball to change direction.

			// Build the repaint area;
			Rectangle oldPos = this.boundingBox();

			x += vx;
			if (x <= radius) {
				x = radius;
				vx = -vx;
			}
			if (x >= 500 - radius) {
				x = 500 - radius;
				vx = -vx;
			}

			y += vy;
			if (y <= radius) {
				y = radius;
				vy = -vy;
			}
			if (y >= 500 - radius) {
				y = 500 - radius;
				vy = -vy;
			}

			// Add to repaint area
			Rectangle repaintArea = oldPos.union(this.boundingBox());

			// Cause an update to the View with repaint area as argument
			// Need to set changed before calling notifyObservers

			this.setChanged();
			this.notifyObservers(repaintArea);

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
			return new Rectangle(x - radius, y - radius, radius + radius + 1,
					radius + radius + 1);
		}
	}

