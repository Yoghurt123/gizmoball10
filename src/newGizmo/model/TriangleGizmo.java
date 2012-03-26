package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import newGizmo.GizmoDriver;
import newGizmo.GizmoSettings;
import newGizmo.Utils;


import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class TriangleGizmo extends AbstractGizmoModel {

	LineSegment triangleLines[] = new LineSegment[3];
	private final int GizmoLength = GizmoSettings.getInstance().getGizmoL();

	public TriangleGizmo(int x, int y) {
		super(x, y);
		SetBoundaryBox();
		// TODO Auto-generated constructor stub
	}

	public void SetBoundaryBox(){

		triangleLines[0] = new LineSegment(x,y,x+GizmoLength,y);
		triangleLines[1] = new LineSegment(x+GizmoLength,y,x+GizmoLength,y-GizmoLength);
		triangleLines[2] = new LineSegment(x,y,x+GizmoLength,y-GizmoLength);
	}
	private static final Color gizmoColor = GizmoSettings.getInstance()
			.getSquereGizmoColor();
	private static final Color gizmoActivColor = GizmoSettings.getInstance()
			.getSqureGizmoActivatedColor();
	private Color curent = gizmoColor;

	@Override
	public Graphics paint(Graphics g) {

		Point p1 = new Point((int)x,(int)y);
		Point p2 = new Point((int)x+GizmoLength,(int)y);
		Point p3 = new Point((int)x+GizmoLength,(int)y-GizmoLength);
	
		int[] xCoordinates = {p1.x,p2.x,p3.x};
		int[] yCoordinates = {p1.y,p2.y,p3.y};
		Polygon triangle = new Polygon(xCoordinates,yCoordinates,xCoordinates.length);
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

		for (LineSegment l : triangleLines) {
			double time = Geometry.timeUntilWallCollision(l, ball.getShape(),
					ball.getVolecity());
			if (tempTime > time) {
				templine = l;
				tempTime = time;
			}
		}

		// when time to collisions is less them tiem tick run timeTask on exacly
		// colision time

		if (!isReflecting)
			if (tempTime < GizmoSettings.getInstance()
					.getBallMovementUpdateDtime()) {


			long msec = Utils.Sec2Msec(tempTime);
			// update ball position on hit moment
			GizmoDriver.getInstance().runTask(ball.newTask(tempTime), msec);
			// run onHit method of gizmo on hit time
			GizmoDriver.getInstance().runTask(new onColisionTimeTask(templine),
					msec);
		}
		return tempTime;
	}

	@Override
	public void onColisionTime(GizmoBall ball, Object o) {
		isReflecting = false;
		if (o instanceof LineSegment) {
			LineSegment linesegment = (LineSegment) o;
			Vect velocity = Geometry.reflectWall(linesegment,
					ball.getVolecity(), 0.75);

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
				+ (x / GizmoSettings.getInstance().getGizmoL() - 1) + " "
				+ (y / GizmoSettings.getInstance().getGizmoL() - 1);
	}

	@Override
	public String getType() {
		return "Triangle";
	}

}
