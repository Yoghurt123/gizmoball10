package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;

import newGizmo.GizmoDriver;
import newGizmo.GizmoSettings;
import newGizmo.Utils;
import newGizmo.model.AbstractGizmoModel.onColisionTimeTask;
import physics.Circle;
import physics.Geometry;

import physics.Vect;

public class CircleGizmo extends AbstractGizmoModel {

	Circle circleBoundary;

	public CircleGizmo(int x, int y) {
		super(x, y);
		SetCircle();
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
		g.fillOval((int) x, (int) y, L, L);
		return g;

	}

	public void SetCircle() {
		circleBoundary = new Circle(x, y, L / 2 );
	}

	public Circle GetCircle() {
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
		//
		tempTime = Geometry.timeUntilCircleCollision(GetCircle(),
				ball.getShape(), ball.getVolecity());
		// if (tempTime > time) {
		// tempTime = time;
		// }
		//
		// // when time to collisions is less them tiem tick run timeTask on
		// exacly
		// // colision time
		//
		if (!isReflecting)
			if (tempTime < GizmoSettings.getInstance()
					.getBallMovementUpdateDtime()) {

				isReflecting = true;
				long msec = Utils.Sec2Msec(tempTime);
				// update ball position on hit moment
				GizmoDriver.getInstance().runTask(ball.newTask(tempTime), msec);
				// run onHit method of gizmo on hit time
				GizmoDriver.getInstance().runTask(
						new onColisionTimeTask(GetCircle()), msec);
			}

		// if(time<0.00000001){
		// Vect centre = new Vect(x, y);
		//
		// Vect velocity =
		// Geometry.reflectCircle(centre,ball.getShape().getCenter(),
		// ball.getVolecity());
		//
		// ball.setVelocity(velocity);
		//
		// }
		// return time;
		return tempTime;
	}

	@Override
	public void onColisionTime(GizmoBall ball, Object o) {
		Vect centre = new Vect(x, y);
		GizmoBoard.getInstance().resetReflectiinFlagOnGizmos(null);
		Vect velocity = Geometry.reflectCircle(centre, ball.getShape()
				.getCenter(), ball.getVolecity());

		ball.setVelocity(velocity);

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
