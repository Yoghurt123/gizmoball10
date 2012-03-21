package newGizmo;

import java.awt.Color;

import physics.Angle;
import physics.Vect;

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
		return 20;
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

		return 0.25;
	}

}
