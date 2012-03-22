package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;

import physics.Angle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

public class Absorber extends AbstractGizmoModel {


	public Absorber(int x, int y) {
		super(x, y);

	}

	@Override
	public Graphics paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 650, 700, 650);
		return g;
	}

	@Override
	public void update(double dtime) {
		// TODO Auto-generated method stub
		
	}
	
	public LineSegment getRect() {
	
	return new LineSegment(0, 450, 900, 450);
}

	@Override
	public double timeToColision(GizmoBall ball) {
		
		double absorbtemp = Geometry.timeUntilWallCollision(getRect(), ball.getShape(), ball.getVolecity());
	    if(absorbtemp<0.5){
	 		GizmoBall b=new GizmoBall(200,400,new Vect(Angle.DEG_270, 55));
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
