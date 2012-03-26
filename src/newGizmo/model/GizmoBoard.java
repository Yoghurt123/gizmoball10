package newGizmo.model;

import java.awt.List;
import java.util.ArrayList;

import newGizmo.controller.EventListener;

import GizmoballGUI.animationEventListener;

import physics.Angle;
import physics.Vect;

public class GizmoBoard {
	private EventListener eventListener;

	private static GizmoBoard instance = null;

	public static GizmoBoard getInstance() {
		if (instance == null)
			instance = new GizmoBoard();
		return instance;
	}

	private GizmoBoard() {
		eventListener = new EventListener();
		ball = new GizmoBall(20, 20, new Vect(Angle.DEG_315, 10));

	}

	ArrayList<AbstractGizmoModel> gizmos = new ArrayList<AbstractGizmoModel>();
	GizmoBall ball = null;

	public ArrayList<AbstractGizmoModel> getGizmos() {
		return gizmos;
	}

	/**
	 * add gizmo to board
	 * 
	 * @param gizmo
	 */
	public void addGizmo(AbstractGizmoModel gizmo) {
		gizmos.add(gizmo);
	}

	public GizmoBall getBall() {
		return ball;
	}

	public void setBall(GizmoBall ball) {
		this.ball = ball;
	}

	/**
	 * when is run button pressted
	 */
	public void run() {
		if (ball != null) {
			ball.startBallMovement();
		}
	}

	/**
	 * Check all gizmos for colliding ones with ball
	 */
	public void checkColisions() {
		if (ball != null) {
			for (AbstractGizmoModel gizmo : gizmos) {
				gizmo.timeToColision(ball);
			}
		}

	}

}
