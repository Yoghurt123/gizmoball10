package newGizmo.model;

import java.awt.Graphics;

import newGizmo.GizmoDriver;
import newGizmo.GizmoSettings;
import newGizmo.Utils;
import newGizmo.model.AbstractGizmoModel.onColisionTimeTask;

import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class GizmoWalls extends AbstractGizmoModel {
	private int xlen = 0;
	private int ylen = 0;
	
	LineSegment walls[];

	public GizmoWalls(int x, int y, int xlen, int ylen) {
		super(x, y);
		this.xlen = xlen;
		this.ylen = ylen;
		walls = new LineSegment[4];
		setWalls();
	}

	private void setWalls() {
		walls[0] = new LineSegment(x, y, x + xlen, y);
		walls[1] = new LineSegment(x + xlen, y, x + xlen, y + ylen);
		walls[2] = new LineSegment(x + xlen, y + ylen, x, y + ylen);
		walls[3] = new LineSegment(x, y + ylen, x, y);
	}

	@Override
	public Graphics paint(Graphics g) {
		g.drawLine((int)x, (int)y, (int)x + xlen, (int)y);
		g.drawLine((int)x + xlen, (int)y, (int)x + xlen, (int)y + ylen);
		g.drawLine((int)x + xlen, (int)y + ylen, (int)x, (int)y + ylen);
		g.drawLine((int)x, (int)y + ylen, (int)x, (int)y);
		return g;

	}

	@Override
	public void update(double dtime) {
		// TODO Auto-generated method stub

	}

	@Override
	public double timeToColision(GizmoBall ball) {
		double tempTime = Double.POSITIVE_INFINITY;
		LineSegment templine = walls[0];

		for (LineSegment l : walls) {
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
				isReflecting = true;

				// update ball position on hit moment
				GizmoDriver.getInstance().runTask(ball.newTask(tempTime), msec);
				// run onHit method of gizmo on hit time
				GizmoDriver.getInstance().runTask(
						new onColisionTimeTask(templine), msec);
			}
		return tempTime;
	}

	@Override
	public void onColisionTime(GizmoBall ball, Object reflectigfrom) {
		isReflecting = false;
		if (reflectigfrom instanceof LineSegment) {
			LineSegment linesegment = (LineSegment) reflectigfrom;
			Vect velocity = Geometry.reflectWall(linesegment,
					ball.getVolecity(), 0.8);
			ball.setVelocity(velocity);
		}

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
