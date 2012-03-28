package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.*;

import newGizmo.GizmoDriver;
import newGizmo.GizmoSettings;
import newGizmo.Utils;
import newGizmo.model.AbstractGizmoModel.onColisionTimeTask;
import physics.*;

public class LeftFlipper extends AbstractGizmoModel {
	private AffineTransform transform = new AffineTransform();
	private int deg = 0;

	Point p1 = null;
	Point p2 = null;

	private int rotating = 0;

	Circle c1 = null;
	Circle c2 = null;

	LineSegment l1 = null;
	LineSegment l2 = null;

	public LeftFlipper(int x, int y) {
		super(x, y);
		setBoundaryBox();
		p1 = new Point((int) x, (int) y + L / 2);
		p2 = new Point((int) x + L, (int) y + L / 2);
		// TODO Auto-generated constructor stub
	}

	private final int L = GizmoSettings.getInstance().getGizmoL();
	LineSegment LeftFlipperLines[] = new LineSegment[4];

	public void setBoundaryBox() {
		c1 = new Circle(x, y, L / 3);
		c2 = new Circle(x, y, L / 3);

	}

	public Point RotatePoint(Point pointTorotate, Point orgin, double degree) {
		Point ret = new Point();
		pointTorotate.x += (0 - orgin.x);
		pointTorotate.y += (0 - orgin.y);
		ret.x = (int) ((pointTorotate.x * Math.cos(degree * (Math.PI / 180))) - (pointTorotate.y * Math
				.sin(degree * (Math.PI / 180))));
		ret.y = (int) (Math.sin(degree * (Math.PI / 180)) * pointTorotate.x + Math
				.cos(degree * (Math.PI / 180)) * pointTorotate.y);
		ret.x += (0 + orgin.x);
		ret.y += (0 + orgin.y);
		return ret;

	}

	@Override
	public Graphics paint(Graphics g) {
		//rotating();
		
		// rotating();
		
		Point temp = RotatePoint(p2, p1, deg);

		g.drawLine(p1.x, p1.y, temp.x, temp.y);

		// Area shape = new Area(new Rectangle2D.Double(x, y, L/2, L*2));
		// transform.setToRotation(Math.toRadians(deg), x, y);
		// shape.transform(transform);
		//
		// Graphics2D g2d = (Graphics2D)g;
		// g2d.setColor(Color.BLUE);
		// g2d.fill(shape);

		return g;
	}

	public void rotating() {
		if (rotating == 1) {
			if (deg <= 0 && deg > -90) {
				deg -= 15;
				System.out.println("Rotating");
			} else
				rotating = 0;
			if (rotating == -1) {
				if (deg > 0) {
					deg += 15;
				} else
					rotating = 0;
			}
		}

	}

	@Override
	public void update(double dtime) {
		// TODO Auto-generated method stub

	}

	@Override
	public double timeToColision(GizmoBall ball) {
		double tempTime = Double.POSITIVE_INFINITY;
		// LineSegment templine = LeftFlipperLines[0];
		//
		// for (LineSegment l : LeftFlipperLines) {
		// double time = Geometry.timeUntilWallCollision(l, ball.getShape(),
		// ball.getVolecity());
		// if (tempTime > time) {
		// templine = l;
		// tempTime = time;
		// }
		// }
		//
		// // when time to collisions is less them tiem tick run timeTask on
		// exacly
		// // colision time
		// if (tempTime <
		// GizmoSettings.getInstance().getBallMovementUpdateDtime()) {
		// long msec = Utils.Sec2Msec(tempTime);
		// // update ball position on hit moment
		// GizmoDriver.getInstance().runTask(ball.newTask(msec), msec);
		// // run onHit method of gizmo on hit time
		// GizmoDriver.getInstance().runTask(new onColisionTimeTask(templine),
		// msec);
		// }
		return tempTime;

	}

	@Override
	public void onColisionTime(GizmoBall ball, Object o) {
		if (o instanceof LineSegment) {
			LineSegment linesegment = (LineSegment) o;
			Vect valocity = Geometry.reflectWall(linesegment,
					ball.getVolecity());
			ball.setVelocity(valocity);
		}

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSaveString() {
		return "LeftFlipper " + name + " "
				+ ((int) x / GizmoSettings.getInstance().getGizmoL() - 1) + " "
				+ ((int) y / GizmoSettings.getInstance().getGizmoL() - 1);
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
