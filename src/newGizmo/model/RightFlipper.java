package newGizmo.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import newGizmo.GizmoSettings;

public class RightFlipper extends AbstractGizmoModel {
	private AffineTransform transform = new AffineTransform();
	private int deg = 0;
	private boolean rotating = false;
	
	public RightFlipper(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	private final int L = GizmoSettings.getInstance().getGizmoL();

	@Override
	public Graphics paint(Graphics g) {
		rotating();
		
		Area shape = new Area(new RoundRectangle2D.Double(x, y, L/2, L*2, L/2, L/2));
		transform.setToRotation(Math.toRadians(deg), x, y);
		shape.transform(transform);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.BLUE);
		g2d.fill(shape);
		
		return g;
	}
	
	public void rotating(){
		if(rotating == true){
			if(deg< 90){
				deg = +15;
				System.out.println("Rotating");
			}
			else
				rotating = false;
		}
		
	}

	@Override
	public void update(double dtime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double timeToColision(GizmoBall ball) {
		// TODO Auto-generated method stub
		return 0;
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
