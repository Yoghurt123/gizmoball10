package newGizmo.model;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.JPanel;

import newGizmo.GizmoSettings;
import newGizmo.controller.EventListener;

import GizmoballGUI.animationEventListener;

import physics.Angle;
import physics.Vect;

public class GizmoBoard extends JPanel {
	public static final int L = GizmoSettings.getInstance().getGizmoL();
	private EventListener eventListener;

	private static GizmoBoard instance = null;

	public static GizmoBoard getInstance() {
		if (instance == null)
			instance = new GizmoBoard();
		return instance;
	}

	private GizmoBoard() {
		eventListener = new EventListener();
		ball = new GizmoBall(10, 10, new Vect(0, 100));

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
	 * when is run button pressed
	 */
	public void run() {
		if (ball != null) {
			ball.startBallMovement();
			requestFocus();
			addKeyListener(eventListener);
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

	public void resetReflectiinFlagOnGizmos(AbstractGizmoModel triangleGizmo) {

		for (AbstractGizmoModel m : gizmos) {
			if (m != triangleGizmo)
				m.isReflecting = false;
		}

	}

	public AbstractGizmoModel getGizmoAt(int xClicked, int yClicked) {
		for (AbstractGizmoModel m : gizmos) {
			if ((xClicked > m.x) && (xClicked < m.x + L) && (yClicked > m.y)
					&& (yClicked < m.y + L)) {
				System.out.println(m.getSaveString()); 
				return m;
			}
		}
		return null;
	}

}
