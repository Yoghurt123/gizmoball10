import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import physics.Geometry;
import physics.LineSegment;

public class gizmoBoard implements Drawable {

	class onNearRefTimeTimmer extends TimerTask
	{

	double timeto;
		LineSegment line;
		public onNearRefTimeTimmer(LineSegment l, double t) {
		line =l;
		timeto = t;
		}
		@Override
		public void run() {
			System.out.println("on colision");
			b.update(timeto);
			b.setVelocity(Geometry.reflectWall(line, b.getVelocity(), 0.6));
			
		}
		
	}
	
	
	boardLayout settings;
	ball b;
	ArrayList<Object> gizmos = new ArrayList<Object>();
	LineSegment[] walls;
	
	Timer t = new  Timer();

	private static boardLayout instance = null;

	public static boardLayout getInstace() {
		if (instance == null)
			instance = new boardLayout();
		return instance;
	}

	
	void setBall(ball b)
	{
		this.b = b;
	}
	
	
	public gizmoBoard() {

		this.settings = boardLayout.getInstance();

		int gredXsize = settings.getHorisSize();
		int gredYsize = settings.getVertSize();

		int horiz = boardLayout.getInstance().getHorisSize();
		int vert = boardLayout.getInstance().getVertSize();
		b=new ball(null, null, vert/2,horiz-20);
//		b = new ball(100, 100);

		walls = new LineSegment[4];
		// x y x1 y1
		walls[0] = new LineSegment(0, 0, 0, gredYsize);
		walls[1] = new LineSegment(0, gredYsize, gredXsize, gredYsize);
		walls[2] = new LineSegment(0, 0, gredXsize, 0);
		walls[3] = new LineSegment(gredXsize, 0, gredXsize, gredYsize);
		//addGizmo(new Absorber());
	}

	public void addGizmo(Object gizmo)
	{
	//	if((gizmo instanceof Drawable)&&(gizmo instanceof IGizmo))
		gizmos.add(gizmo);
	}
	public Graphics paint(Graphics g) {

		for (int i = 0; i <= settings.getVerBoxes(); i++) {
			int cf = settings.getBoxSize() * i;
			g.drawLine(cf, 0, cf, settings.getHorisSize());
		}

		for (int i = 0; i <= settings.getHorBoxes(); i++) {
			int cf = settings.getBoxSize() * i;
			g.drawLine(0, cf, settings.getVertSize(), cf);
		}

		for (Object d : gizmos)

			if (d instanceof Drawable)
				((Drawable) d).paint(g);

		b.paint(g);

		return g;
	}

	public void update(double dtime) {
		b.update(dtime);
		timeUntilCollision();

		
	}

	public double timeUntilCollision() {
		double time = Double.POSITIVE_INFINITY, temp = 0;

		for (LineSegment line : walls) {
			temp = Geometry.timeUntilWallCollision(line, b.getShape(),
					b.getVelocity());
			
			
			
			if (temp < 1) {
				t.schedule(new onNearRefTimeTimmer(line,temp), (long) (temp*1000000));	
				
			}

			for (Object d : gizmos) {
				//if (d instanceof IGizmo) {
					//((IGizmo) d).timeToCollision(b);

				}
			}

		//}
		return time;
	}

}
