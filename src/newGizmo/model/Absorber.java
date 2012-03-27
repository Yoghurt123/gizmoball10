package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;

import newGizmo.GizmoDriver;
import newGizmo.GizmoSettings;
import newGizmo.Utils;
import newGizmo.model.AbstractGizmoModel.onColisionTimeTask;

import physics.Angle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class Absorber extends AbstractGizmoModel {

	int length = GizmoSettings.getInstance().getGizmoL();

	LineSegment abs;

	public Absorber(int x, int y) {
		super(x, y);
		SetBoundary();
	}

	@Override
	public Graphics paint(Graphics g) {
		g.setColor(Color.BLACK);

		g.fillRect(0, (int) y, 600, 20);

		return g;
	}

	@Override
	public void update(double dtime) {
		// TODO Auto-generated method stub

	}

	public void SetBoundary() {
		abs = new LineSegment(0, y, 20 * length, y);
	}

	public LineSegment getRect() {

		return abs;
	}

	@Override
	public double timeToColision(GizmoBall ball) {
		double tempTime;

		tempTime = Geometry.timeUntilWallCollision(abs, ball.getShape(),
				ball.getVolecity());
		if (!isReflecting)
			if (tempTime < GizmoSettings.getInstance()
					.getBallMovementUpdateDtime()) {
				isReflecting = true;
				long msec = Utils.Sec2Msec(tempTime);
				// update ball position on hit moment
				GizmoDriver.getInstance().runTask(ball.newTask(tempTime), msec);
				// run onHit method of gizmo on hit time
				GizmoDriver.getInstance().runTask(new onColisionTimeTask(null),
						msec);
			}
		return tempTime;
	}

	@Override
	public void onColisionTime(GizmoBall ball, Object reflectigfrom) {
		isReflecting = false;
		System.out.println("on colision");
		GizmoBall b = new GizmoBall(580, (int)y, new Vect(0, -200));
		GizmoBoard.getInstance().setBall(b);
		b.startBallMovement();
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

	@Override
	public void onActivationEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeactivationEvent() {
		// TODO Auto-generated method stub

	}

}
