package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;

import newGizmo.GizmoDriver;
import newGizmo.GizmoSettings;
import newGizmo.Utils;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class CircleGizmo extends AbstractGizmoModel {

	Circle circleBoundary;

	public CircleGizmo(int x, int y) {
		super(x, y);
		GetCircle();
		// TODO Auto-generated constructor stub
	}

	private final int L = GizmoSettings.getInstance().getGizmoL();

	private static final Color gizmoColor = GizmoSettings.getInstance()
			.getCircleGizmoColor();
	private static final Color gizmoActivColor = GizmoSettings.getInstance()
			.getCircleGizmoActivatedColor();
	private Color curent = gizmoColor;

	@Override
	public Graphics paint(Graphics g) {

		g.setColor(curent);
		g.fillOval(x, y, L, L);
    	return g;

	}
	
	public Circle GetCircle(){
		circleBoundary = new Circle(x, y, 15);
		return circleBoundary;
	}

	@Override
	public void update(double dtime) {
		// nothing

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

	@Override
	public double timeToColision(GizmoBall ball) {
		double tempTime = Double.POSITIVE_INFINITY;

			double time = Geometry.timeUntilCircleCollision(GetCircle(), ball.getShape(),
					ball.getVolecity());
			if (tempTime > time) {
				tempTime = time;
			
		}

		// when time to collisions is less them time tick run timeTask on exactly
		// collision time
		if (tempTime < GizmoSettings.getInstance().getBallMovementUpdateDtime()) {
			long msec = Utils.Sec2Msec(tempTime);
			// update ball position on hit moment
			GizmoDriver.getInstance().runTask(ball.newTask(msec), msec);
			// run onHit method of gizmo on hit time
			GizmoDriver.getInstance().runTask(new onColisionTimeTask(GetCircle()),
					msec);
		}
		return tempTime;

	}

	@Override
	public void onColisionTime(GizmoBall ball, Object o) {
		if (o instanceof Circle) {
			Circle circle = (Circle) o;
			Vect velocity = Geometry.reflectRotatingCircle(circle,GetCircle().getCenter(),
						0,ball.getShape(),ball.getVolecity());
			ball.setVelocity(velocity);
		}

	}

	@Override
	public String getDescription() {

		String retstr = "";
		// retstr += "Name: " + name + "\n";
		retstr += "Type: Circle\n";
		retstr += "Position: (" + (x / GizmoSettings.getInstance().getGizmoL())
				+ "," + (y / GizmoSettings.getInstance().getGizmoL()) + ")\n";
		retstr += "Connects to:";
		// if (triggers.isEmpty())
		// retstr += " (none)";
		return retstr;
	}

	@Override
	public String getSaveString() {
		return "Circle " + name + " "
				+ (x / GizmoSettings.getInstance().getGizmoL() - 1) + " "
				+ (y / GizmoSettings.getInstance().getGizmoL() - 1);
	}

	@Override
	public String getType() {
		return "Circle";
	}
	
}
