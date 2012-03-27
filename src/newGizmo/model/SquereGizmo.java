package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;

import newGizmo.GizmoDriver;
import newGizmo.GizmoSettings;
import newGizmo.SavleLoadable;
import newGizmo.Utils;
import newGizmo.model.AbstractGizmoModel.DeactivateTask;
import newGizmo.model.AbstractGizmoModel.onColisionTimeTask;
import physics.Geometry; 
import physics.LineSegment;
import physics.Vect;

public class SquereGizmo extends AbstractGizmoModel implements SavleLoadable {
	private final int L = GizmoSettings.getInstance().getGizmoL();

	LineSegment squareLines[] = new LineSegment[4];

	public SquereGizmo(int x, int y) {
		super(x, y);
		setBoundaryBox();
	}

	/**
	 * setup the boundary lines around the box for find the time to collisions
	 * and reflect the ball as needed
	 */
	private void setBoundaryBox() {
		squareLines[0] = new LineSegment(x, y, x + L, y);
		squareLines[1] = new LineSegment(x + L, y, x + L, y + L);
		squareLines[2] = new LineSegment(x + L, y + L, x, y + L);
		squareLines[3] = new LineSegment(x, y + L, x, y);

	}

	private static final Color gizmoColor = GizmoSettings.getInstance()
			.getSquereGizmoColor();
	private static final Color gizmoActivColor = GizmoSettings.getInstance()
			.getSqureGizmoActivatedColor();
	private Color curent = gizmoColor;

	@Override
	public Graphics paint(Graphics g) {

		g.setColor(curent);
		g.fillRect((int) x, (int) y, GizmoSettings.getInstance().getGizmoL(),
				GizmoSettings.getInstance().getGizmoL());
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
		LineSegment templine = squareLines[0];

		for (LineSegment l : squareLines) {
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
	
	@Override
	public String getName(){
		return name;
	}
}
