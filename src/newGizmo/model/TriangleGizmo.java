package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import newGizmo.GizmoDriver;
import newGizmo.GizmoSettings;
import newGizmo.Utils;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class TriangleGizmo extends AbstractGizmoModel {

	LineSegment triangleLines[] = new LineSegment[3];
	Circle corner1, corner2, corner3;
	private final int GizmoLength = GizmoSettings.getInstance().getGizmoL();
	int rotate = 1;

	public TriangleGizmo(int x, int y) {
		super(x, y);
		SetBoundaryBox(rotate);
		// TODO Auto-generated constructor stub
	}

	public void SetBoundaryBox(int rotate) {

		// switch(rotate){
		// case 1:{
		triangleLines[0] = new LineSegment(x, y, x + GizmoLength, y);
		triangleLines[1] = new LineSegment(x + GizmoLength, y, x + GizmoLength,
				y + GizmoLength); 
		triangleLines[2] = new LineSegment(x, y, x + GizmoLength, y
				+ GizmoLength);
		Circle corner1 = new Circle(x, y, 0);
		Circle corner2 = new Circle(x + GizmoLength, y, 0);
		Circle corner3 = new Circle(x + GizmoLength, y + GizmoLength, 0);

			
//		}
//		case 2:{
//			triangleLines[0] = new LineSegment(x,y,x+GizmoLength,y);
//			triangleLines[1] = new LineSegment(x+GizmoLength,y,x,y+GizmoLength);
//			triangleLines[2] = new LineSegment(x,y+GizmoLength,x,y);
//		}
//		case 3:{
//			triangleLines[0] = new LineSegment(x,y,x+GizmoLength,y+GizmoLength);
//			triangleLines[1] = new LineSegment(x+GizmoLength,y+GizmoLength,x,y+GizmoLength);
//			triangleLines[2] = new LineSegment(x,y+GizmoLength,x,y);
//		}
//		case 4:{
//			triangleLines[0] = new LineSegment(x+GizmoLength,y,x+GizmoLength,y+GizmoLength);
//			triangleLines[1] = new LineSegment(x+GizmoLength,y,x,y+GizmoLength);
//			triangleLines[2] = new LineSegment(x,y+GizmoLength,x+GizmoLength,y);
//		}
//		}



	}

	public void Rotate() {
		if (rotate == 4) {
			this.rotate = 0;
		}
		this.rotate++;

	}

	private static final Color gizmoColor = GizmoSettings.getInstance()
			.getSquereGizmoColor();
	private static final Color gizmoActivColor = GizmoSettings.getInstance()
			.getSqureGizmoActivatedColor();
	private Color curent = gizmoColor;

	@Override
	public Graphics paint(Graphics g) {
		Point p1 = null, p2 = null, p3 = null;
		// switch(rotate){
		// case 1:{

		p1 = new Point((int) x, (int) y);
		p2 = new Point((int) x + GizmoLength, (int) y);
		p3 = new Point((int) x + GizmoLength, (int) y + GizmoLength);
		// }
		// case 2:{
		// p1 = new Point((int)x,(int)y);
		// p2 = new Point((int)x+GizmoLength,(int)y);
		// p3 = new Point((int)x,(int)y+GizmoLength);
		// }
		// case 3:{
		// p1 = new Point((int)x,(int)y);
		// p2 = new Point((int)x+GizmoLength,(int)y+GizmoLength);
		// p3 = new Point((int)x,(int)y+GizmoLength);
		// }
		// case 4:{
		// p1 = new Point((int)x+GizmoLength,(int)y);
		// p2 = new Point((int)x+GizmoLength,(int)y+GizmoLength);
		// p3 = new Point((int)x,(int)y+GizmoLength);
		// }
		// }
		int[] xCoordinates = { p1.x, p2.x, p3.x };
		int[] yCoordinates = { p1.y, p2.y, p3.y };
		Polygon triangle = new Polygon(xCoordinates, yCoordinates,
				xCoordinates.length);
		g.setColor(curent);
		g.fillPolygon(triangle);
		return g;

	}

	@Override
	public void update(double dtime) {
		// nohing

	}

	@Override
	public void onActivationEvent() {
		curent = gizmoActivColor;
		activateLinkedGizmos();
	}

	@Override
	public void onDeactivationEvent() {
		curent = gizmoColor;
		deactivateLinkedGizmos();
	}

	public double timeToColision(GizmoBall ball) {
		double tempTime = Double.POSITIVE_INFINITY;
		LineSegment templine = triangleLines[0];
		double time = 0;

		for (LineSegment l : triangleLines) {
			time = Geometry.timeUntilWallCollision(l, ball.getShape(),


					ball.getVolecity());

			if (tempTime > time) {
				templine = l;
				tempTime = time;
			}
		}
			
		if (!isReflecting)
			if (tempTime < GizmoSettings.getInstance()
					.getBallMovementUpdateDtime()) {
				isReflecting = true; 
				long msec = Utils.Sec2Msec(tempTime);
				// update ball position on hit moment
				GizmoDriver.getInstance().runTask(ball.newTask(tempTime), msec);
				// run onHit method of gizmo on hit time
				GizmoDriver.getInstance().runTask(
						new onColisionTimeTask(templine), msec);
			}
		return tempTime;

	}

	@Override
	public void onColisionTime(GizmoBall ball, Object o) {
		isReflecting = false;
		if (o instanceof LineSegment) {
			LineSegment linesegment = (LineSegment) o;
			Vect velocity = Geometry.reflectWall(linesegment,
					ball.getVolecity());
			GizmoBoard.getInstance().resetReflectiinFlagOnGizmos(this);
			ball.setVelocity(velocity);
		}
	}

	@Override
	public String getDescription() {

		String retstr = "";
		// retstr += "Name: " + name + "\n";
		retstr += "Type: Triangle\n";
		retstr += "Position: (" + (x / GizmoSettings.getInstance().getGizmoL())
				+ "," + (y / GizmoSettings.getInstance().getGizmoL()) + ")\n";
		retstr += "Connects to:";
		// if (triggers.isEmpty())
		// retstr += " (none)";
		return retstr;
	}

	@Override
	public String getSaveString() {
		return "Triangle " + name + " "
				+ ((int)x / GizmoSettings.getInstance().getGizmoL() - 1) + " "
				+ ((int)y / GizmoSettings.getInstance().getGizmoL() - 1);
	}

	@Override
	public String getType() {
		return "Triangle";
	}

}
