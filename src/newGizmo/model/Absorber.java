package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;

import physics.Angle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class Absorber extends AbstractGizmoModel {

	LineSegment abs = null;
	public Absorber(int x, int y) {
		super(x, y);
		SetBoundary();

	}

	@Override
	public Graphics paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, y, 700, 20);
		return g;
	}

	@Override
	public void update(double dtime) {
		// TODO Auto-generated method stub
		
	}
	
	public void SetBoundary(){
		abs = new LineSegment(0,y,900,y);
	}
	
	public LineSegment getRect() {
	
	return abs;
}

	@Override
	public double timeToColision(GizmoBall ball) {
		double absorbtemp = 0;
		absorbtemp = Geometry.timeUntilWallCollision(getRect(), ball.getShape(), ball.getVolecity());
		if(absorbtemp<0.5){
			GizmoBall b= new GizmoBall(200,400,new Vect(Angle.DEG_270, 50));
			GizmoBoard.getInstance().setBall(b);
			b.startBallMovement();

		}
		return absorbtemp;	
	}

	@Override
	public void onColisionTime(GizmoBall ball, Object reflectigfrom) {
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

	@Override
	public void onActivationEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeactivationEvent() {
		// TODO Auto-generated method stub
		
	}

	

	
}