package newGizmo.model;

import java.awt.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import newGizmo.controller.EventListener;

import GizmoballGUI.animationEventListener;

import physics.Angle;
import physics.Vect;

public class GizmoBoard extends JPanel implements Observer{
	  private EventListener eventListener;
	

	private static GizmoBoard instance = null;

	public static GizmoBoard getInstance() {
		if (instance == null)
			instance = new GizmoBoard();
		return instance;
	}

	private GizmoBoard() {
		eventListener = new EventListener();
		ball = new GizmoBall(20, 20,new Vect(Angle.DEG_315,10));

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
	
	public boolean isFocusable() { return true; }

	/**
	 * when is run button pressted
	 */
	public void run() {
		if (ball != null) {
			ball.startBallMovement();
			addKeyListener(eventListener);
			requestFocus();
		}
	}

	/**
	 * Check all gizmos for coliding ones with ball
	 */
	public void checkColisions() {
		if(ball!=null)
		{
			for(AbstractGizmoModel gizmo:gizmos)
			{
				gizmo.timeToColision(ball);
			}
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		this.repaint();
	}

}
