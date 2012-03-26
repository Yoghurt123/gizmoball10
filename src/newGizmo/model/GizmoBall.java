package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;

import physics.Circle;
import physics.Geometry;
import physics.Vect;

import newGizmo.GizmoDriver;
import newGizmo.GizmoDriver.STATES;
import newGizmo.GizmoSettings;
import newGizmo.Utils;

public class GizmoBall extends AbstractGizmoModel {
	private Vect volecity = null;
	private static final double Gravity = GizmoSettings.getInstance()
			.getGravity();
	private static final double tickLen = GizmoSettings.getInstance()
			.getBallMovementUpdateDtime();
	private static final double mu = GizmoSettings.getInstance()
			.getMuConstatnt();
	private static final double mu2 = GizmoSettings.getInstance()
			.getMuConstatnt();

	public GizmoBall(int x, int y, Vect volecity) {
		super(x, y);
		this.volecity = volecity;
	}

	public class BallMoveTask extends GizmoDriver.GizmoTask {

		double dtime;

		/**
		 * 
		 * @param dtime
		 *            time science last update
		 */
		public BallMoveTask(double dtime) {
			GizmoDriver.getInstance().super();
			this.dtime = dtime;
		}

		@Override
		public void onRun(GizmoBoard board) {
			switch (getCurentDriverState()) {
			case RUN_STATE:

				update(dtime);
				board.checkColisions();
				break;

			default:
				moving = false;
				cancel();

				break;
			}

		}

	}

	public boolean moving = false;
	public static final Color color_of_ball = GizmoSettings.getInstance()
			.getBallColor();
	private int radius = 10;

	@Override
	public Graphics paint(Graphics g) {
		g.setColor(color_of_ball);
		g.fillOval((int)x, (int)y, radius, radius);
		return g;
	}

	@Override
	public void update(double dtime) {

		/**
		 * the normal tick ...
		 */

		double vx = volecity.x();
		double vy = volecity.y();

		System.out.println("vx:" + vx + " vy:" + vy);
		System.out.println("x:" + x + " y:" + y);

		// gravity
		vy = vy + Gravity * dtime;

		// fractions
		// vx = vx * (1 - (mu * dtime) - (mu2 * Math.abs(vx) * dtime));
		// vy = vy * (1 - (mu * dtime) - (mu2 * Math.abs(vy) * dtime));
		volecity = new Vect(vx, vy);
		x = (x + (vx * dtime));
		y = (y + (vy * dtime));

		// System.out.println(volecity.length());
		// // if (velocity.length() <1) velocity = new Vect(Angle.ZERO,0);
		// double dvel = volecity.y() + 0.5 * Gravity.y();// *dtime* dtime;
		// volecity = volecity.plus(Gravity.times(1));
		// x += volecity.x();
		//
		// y += dvel;

	}

	public void startBallMovement() {
		if (!moving) {
			long ms = Utils.Sec2Msec(GizmoSettings.getInstance()
					.getBallMovementUpdateDtime());
			GizmoDriver.getInstance().runShudledTask(
					new BallMoveTask(GizmoSettings.getInstance()
							.getBallMovementUpdateDtime()), 0, ms);
			moving = true;
		}

	}

	public Circle getShape() {
		return new Circle(x, y, 10);
	}

	public Vect getVolecity() {
		return volecity;
	}

	public void setVelocity(Vect volicity) {
		this.volecity = volicity;
	}

	@Override
	public double timeToColision(GizmoBall ball) {
		double timeto = Geometry.timeUntilBallBallCollision(getShape(),
				getVolecity(), ball.getShape(), ball.getVolecity());

		/**
		 * if timetilcollision is before full tick run task on hit time
		 */
		if (timeto < GizmoSettings.getInstance().getBallMovementUpdateDtime()) {

			long timetoL = Utils.Sec2Msec(timeto);
			GizmoDriver.getInstance().runTask(new BallMoveTask(timetoL),
					timetoL);

		}

		return timeto;
	}

	@Override
	public void onColisionTime(GizmoBall ball, Object re) {
		Vect newVolicity = Geometry.reflectCircle(getShape().getCenter(), ball
				.getShape().getCenter(), ball.getVolecity());
		ball.setVelocity(newVolicity);

	}

	public BallMoveTask newTask(double time) {
		return new BallMoveTask(time);
	}

	// /////////////UNUSSED///////////////
	@Override
	public void onHitEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivationEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeactivationEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSaveString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
