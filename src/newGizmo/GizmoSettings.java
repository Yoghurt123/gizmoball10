package newGizmo;

import java.awt.Color;

import physics.Angle;
import physics.Vect;

/**
 * This clas purpos is to keep all constants in one place easy to change when
 * needed on extra time setings can/will be stored/readed from conf file using
 * java.util.preporties utility class;
 * 
 * @author mikiones
 * 
 */
public class GizmoSettings {
	private GizmoSettings() {
		// TODO Auto-generated constructor stub
	}

	private static GizmoSettings instance = null;

	public static GizmoSettings getInstance() {
		if (instance == null)
			instance = new GizmoSettings();
		return instance;
	}

	public Color getBallColor() {
		return Color.black;
	}

	public Color getSquereGizmoColor() {
		return Color.red;
	}

	public Color getSqureGizmoActivatedColor() {
		return Color.gray;
	}

	public int getGizmoL() {
		return 30;
	}

	public Vect getGravityVector() {
		return new Vect(Angle.DEG_90, 5);
	}

	/**
	 * return frequency in second how often is updated ball
	 * 
	 * @return
	 */
	public double getBallMovementUpdateDtime() {

		return 10.0/1000;
	}
	
	public long getScreenRefreshRate()
	{
		return 60;
	}

	public Color getCircleGizmoColor() {
		return Color.CYAN;
	}

	public Color getCircleGizmoActivatedColor() {

		return Color.gray;
	}

	public double getGravity() {
		return (double)25;// / getGizmoL();
	}

	public double getMuConstatnt() {
		return 0.025;
	}

	public double getMu2Constatnt() {
		return 0.025 / getGizmoL();
	}
}
