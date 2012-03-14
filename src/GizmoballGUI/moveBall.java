package GizmoballGUI;
import java.awt.Rectangle;
import java.util.Observable;


public class moveBall extends Observable {
	
	private int x = (int) ((Math.random() * 100.0) + 100.0);
	private int y = (int) ((Math.random() * 100.0) + 100.0);
	private int vx = (int) ((Math.random() * 10.0) + 10.0);
	private int vy = (int) ((Math.random() * 10.0) + 10.0);
	private int radius = 6;
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
	
	public Rectangle boundingBox() {
		// effect: Returns the smallest rectangle that completely covers the
		// current position of the ball.

		// a Rectangle is the x,y for the upper left corner and then the
		// width and height
		return new Rectangle(x - radius, y - radius, radius + radius + 1,
				radius + radius + 1);
	}

}
