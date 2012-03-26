package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;

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
		g.fillRect(0, (int)y, 700, 20);
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
		double absorbtemp;
//		Geometry.t
		absorbtemp = Geometry.timeUntilWallCollision(abs, ball.getShape(), ball.getVolecity());
		
		if(absorbtemp<35){
			GizmoBall b= new GizmoBall(200, 200, new Vect(0,-0.5));
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
