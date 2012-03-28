package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;

import newGizmo.GizmoDriver;
import newGizmo.GizmoSettings;
import newGizmo.Utils;
import newGizmo.model.AbstractGizmoModel.onColisionTimeTask;
import physics.*;

public class LeftFlipper extends AbstractGizmoModel {
	private AffineTransform transform = new AffineTransform();
	private int deg = 0;
	private int rotating = 1;
	

	public LeftFlipper(int x, int y) {
		super(x, y);
		setBoundaryBox();
		// TODO Auto-generated constructor stub
	}
	private final int L = GizmoSettings.getInstance().getGizmoL();
	LineSegment LeftFlipperLines[] = new LineSegment[4];
	
	private void setBoundaryBox() {
		LeftFlipperLines[0] = new LineSegment(x, y, x + L, y);
		LeftFlipperLines[1] = new LineSegment(x + L, y, x + L, y + L);
		LeftFlipperLines[2] = new LineSegment(x + L, y + L, x, y + L);
		LeftFlipperLines[3] = new LineSegment(x, y + L, x, y);

	}

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
			if(deg<= 0 && deg > -90){
				deg -= 15;
				System.out.println("Rotating");
			}
			else
				rotating = 0;
			if(rotating == -1){
				if(deg > 0){
					deg += 15;
				}
				else
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
		LineSegment templine = LeftFlipperLines[0];

		for (LineSegment l : LeftFlipperLines) {
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

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSaveString() {
		return "LeftFlipper " + name + " "
				+ ((int)x)+" "
				+ ((int)y);
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
