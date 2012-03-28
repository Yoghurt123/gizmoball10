
package newGizmo.model;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.JPanel;

import newGizmo.controller.EventListener;



import physics.Angle;
import physics.Vect;

public class GizmoBoard extends JPanel{
	private EventListener eventListener;

	private static GizmoBoard instance = null;

	public static GizmoBoard getInstance() {
		if (instance == null)
			instance = new GizmoBoard();
		return instance;
	}

	private GizmoBoard() {
		eventListener = new EventListener();
		ball = new GizmoBall(580, 400, new Vect(0, -200));

	}

	ArrayList<AbstractGizmoModel> gizmos = new ArrayList<AbstractGizmoModel>();
	GizmoBall ball = null;

	public ArrayList<AbstractGizmoModel> getGizmos() {
		return gizmos;
	}
	
	public void setGizmos(ArrayList<AbstractGizmoModel> deletedGizmos){
		this.gizmos = deletedGizmos; 
	}

	public void removeGizmo(AbstractGizmoModel model)
	{
		gizmos.remove(model);
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

	public void resetReflectiinFlagOnGizmos(AbstractGizmoModel object) {
		for (AbstractGizmoModel m: gizmos)
			m.isReflecting = false;
		
	}

	public AbstractGizmoModel getGizmoByCord(int x, int y)
	{
		for (AbstractGizmoModel mo : gizmos) {
			if (mo.getX() == x && mo.getY() == y) {
				return mo;
			}
		}
		return null;

		
	}
	
	public void acctivateGizmoByItsKey(char k)
	{
		for (AbstractGizmoModel m:gizmos)
		{
			System.out.println("key assigned:" + m.getKeyBind());
			if(m.getKeyBind() == k)
			{
				
				m.onHitEvent();
			}
		}
	}
	
}
