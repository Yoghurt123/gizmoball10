package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import newGizmo.GizmoDriver;
import newGizmo.GizmoSettings;
import newGizmo.Utils;
import newGizmo.model.AbstractGizmoModel.onColisionTimeTask;

public class RightFlipper extends AbstractGizmoModel {
	private AffineTransform transform = new AffineTransform();
	private static int deg = 0;
	private static int rotating = 0;
	
	public RightFlipper(int x, int y) {
		super(x, y);
		setBoundaryBox();
		// TODO Auto-generated constructor stub
	}
	
LineSegment RightFlipperLines[] = new LineSegment[4];
	
	private void setBoundaryBox() {
		RightFlipperLines[0] = new LineSegment(x, y, x + L, y);
		RightFlipperLines[1] = new LineSegment(x + L, y, x + L, y + L);
		RightFlipperLines[2] = new LineSegment(x + L, y + L, x, y + L);
		RightFlipperLines[3] = new LineSegment(x, y + L, x, y);

	}
	
	private final int L = GizmoSettings.getInstance().getGizmoL();

	@Override
	public Graphics paint(Graphics g) {
		rotating();
		
		Area shape = new Area(new Rectangle2D.Double(x, y, L/2, L*2));
		transform.setToRotation(Math.toRadians(deg), x, y);
		shape.transform(transform);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.BLUE);
		g2d.fill(shape);
		
		return g;
	}
	
	public void rotating(){
		if(rotating == 1){
			if(deg< 90){
				deg = +15;
				System.out.println("Rotating");
			}
			else
				rotating = 0;
		}
		
	}

	@Override
	public double timeToColision(GizmoBall ball) {
		double tempTime = Double.POSITIVE_INFINITY;
		LineSegment templine = RightFlipperLines[0];

		for (LineSegment l : RightFlipperLines) {
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
			ball.setVelocity(valocity);
		}

	}
	
	public static void triggerEvent() {
        if (rotating == 0 ) { // flipper is still
                if (deg == 0)
                        rotating = 1;
                if (deg == 90)
                        rotating = -1;
        }
        else // flipper is currently moving
                rotating = -rotating;       
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

	@Override
	public void update(double dtime) {
		// TODO Auto-generated method stub
		
	}

}
