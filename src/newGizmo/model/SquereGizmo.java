package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;

import newGizmo.GizmoDriver;
import newGizmo.GizmoSettings;
import newGizmo.Utils;
import newGizmo.model.AbstractGizmoModel.DeactivateTask;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class SquereGizmo extends AbstractGizmoModel {
	private final int L = GizmoSettings.getInstance().getGizmoL();

	LineSegment squereLines[] = new LineSegment[4];

	public SquereGizmo(int x, int y) {
		super(x, y);
		setBountryBox();
	}

	/**
	 * setup the bourtly lines around the box for find the time to clisons and
	 * reflect the ball as needed
	 */
	private void setBountryBox() {
		squereLines[0] = new LineSegment(x, y, x + L, y);
		squereLines[1] = new LineSegment(x + L, y, x + L, y + L);
		squereLines[2] = new LineSegment(x + L, y + L, x, y + L);
		squereLines[3] = new LineSegment(x, y + L, x, y);

	}

	private static final Color gizmoColor = GizmoSettings.getInstance()
			.getSquereGizmoColor();
	private static final Color gizmoActivColor = GizmoSettings.getInstance()
			.getSqureGizmoActivatedColor();
	private Color curent = gizmoColor;

	@Override
	public Graphics paint(Graphics g) {

		g.setColor(curent);
		g.fillRect(x, y, GizmoSettings.getInstance().getGizmoL(), GizmoSettings
				.getInstance().getGizmoL());
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

	@Override
	public double timeToColision(GizmoBall ball) {
		double tempTime = Double.POSITIVE_INFINITY;
		LineSegment templine = squereLines[0];

		for (LineSegment l : squereLines) {
			double time = Geometry.timeUntilWallCollision(l, ball.getShape(),
					ball.getVolecity());
			if (tempTime > time) {
				templine = l;
				tempTime = time;
			}
		}

		// when time to collisions is less them tiem tick run timeTask on exacly
		// colision time
		if (tempTime < GizmoSettings.getInstance().getBallMovementUpdateDtime()) {
			long msec = Utils.Sec2Msec(tempTime);
			// update ball position on hit moment
			GizmoDriver.getInstance().runTask(ball.newTask(msec), msec);
			// run onHit method of gizmo on hit time
			GizmoDriver.getInstance().runTask(new onColisionTimeTask(templine),
					msec);
		}
		return tempTime;

	}

	@Override
	public void onColisionTime(GizmoBall ball, Object o) {
		if (o instanceof LineSegment) {
			LineSegment linesegment = (LineSegment) o;
			Vect valocity = Geometry.reflectWall(linesegment,
					ball.getVolecity());
			ball.setVolecity(valocity);
		}

	}

	@Override
	public String getDescription() {

		String retstr = "";
		// retstr += "Name: " + name + "\n";
		retstr += "Type: Square\n";
		retstr += "Position: (" + (x / GizmoSettings.getInstance().getGizmoL())
				+ "," + (y / GizmoSettings.getInstance().getGizmoL()) + ")\n";
		retstr += "Connects to:";
		// if (triggers.isEmpty())
		// retstr += " (none)";
		return retstr;
	}

	@Override
	public String getSaveString() {
		return "Square " + name + " "
				+ (x / GizmoSettings.getInstance().getGizmoL() - 1) + " "
				+ (y / GizmoSettings.getInstance().getGizmoL() - 1);
	}

	@Override
	public String getType() {
		return "Square";
	}
}
