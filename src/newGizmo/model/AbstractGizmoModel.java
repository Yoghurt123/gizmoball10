package newGizmo.model;

import java.awt.Graphics;
import java.util.LinkedList;

import newGizmo.Drawable;
import newGizmo.GizmoDriver;
import newGizmo.GizmoDriver.STATES;
import newGizmo.GizmoSettings;
import newGizmo.SavleLoadable;

public abstract class AbstractGizmoModel implements Drawable, Colisoinable,
		SavleLoadable {
	private int L = GizmoSettings.getInstance().getGizmoL();
	protected boolean isReflecting = false;

	protected class DeactivateTask extends GizmoDriver.GizmoTask {
		public DeactivateTask() {
			GizmoDriver.getInstance().super();
		}

		@Override
		public void onRun(GizmoBoard board) {
			onDeactivationEvent();

		}

	}

	/**
	 * task runed on exacly colison time
	 * 
	 * @author mikiones
	 * 
	 */
	protected class onColisionTimeTask extends GizmoDriver.GizmoTask {
		Object reflecFrom;

		/**
		 * 
		 * @param reflector
		 *            the object Wall circle etc what is reflecting the ball
		 */
		public onColisionTimeTask(Object reflector) {
			GizmoDriver.getInstance().super();
			reflecFrom = reflector;
		}

		@Override
		public void onRun(GizmoBoard board) {
			/**
			 * calculate new ball movement after reflection form reflecform the
			 * reflec from is generic type becouse of ic can be Linesegment,
			 * circle,movinglinesegment etc...
			 */
			onColisionTime(board.getBall(), reflecFrom);
			/**
			 * activate hited gizmo
			 */
			onHitEvent();
		}

	}

	protected double x = 0;
	protected double y = 0;
	protected String name;
	public String getName(){
		return name;
	}

	protected LinkedList<AbstractGizmoModel> linkedGizmos = null;

	public AbstractGizmoModel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * activate all gizmos linked to this one
	 */
	protected void activateLinkedGizmos() {
		if (linkedGizmos != null)
			for (AbstractGizmoModel gizmo : linkedGizmos) {
				gizmo.onActivationEvent();
			}
	}

	/**
	 * deactivate all gizmos linked to this one
	 */
	protected void deactivateLinkedGizmos() {
		if (linkedGizmos != null)
			for (AbstractGizmoModel gizmo : linkedGizmos) {
				gizmo.onDeactivationEvent();
			}
	}

	/**
	 * link gizmo to this one
	 * 
	 * @param gizmo
	 */
	public void linkGizmo(AbstractGizmoModel gizmo) {
		if (linkedGizmos == null)
			linkedGizmos = new LinkedList<AbstractGizmoModel>();
		linkedGizmos.add(gizmo);
	}

	/**
	 * unlink gizmo from this one
	 * 
	 * @param gizmo
	 */
	public void unlinkGizmo(AbstractGizmoModel gizmo) {
		if (linkedGizmos != null) {
			linkedGizmos.remove(gizmo);
			if (linkedGizmos.isEmpty())
				linkedGizmos = null;
		}
	}

	/**
	 * this event is run when ball hit gizmo
	 */
	public void onHitEvent() {
		GizmoDriver.getInstance().runTask(new DeactivateTask(), 1000);
		onActivationEvent();
	}

	/**
	 * this method os runed when gizmo is activ state can be becouse of hited
	 * runed by onHitEvent, or can be activated by linked gizmo to it
	 */
	public abstract void onActivationEvent();

	/**
	 * this event is runed when time of activation of gizmo elapsed it allow
	 * gizmo go back to basic state
	 */
	public abstract void onDeactivationEvent();

	public void setX(int newx){
		this.x = newx;
	}
	
	public void setY(int newy){
		this.y = newy;
	}
	public int getX() {
		return (int )x;	}

	public int getY() {
		return (int)y;
	}
	
	public void setBoundaryBox() {}

	
	protected char keyBind = ' ';
	public void setKeyBind(char c) {
		keyBind = c;
	};
	
	/**
	 * draw connected gizmos as street lines 
	 * @param g
	 */
	public void drawLinkedGizmos(Graphics g)
	{
		if(linkedGizmos!=null)
		for(AbstractGizmoModel m: linkedGizmos)
		{
		g.drawLine(getX()+L/2, getY()+L/2, m.getX()+L/2, m.getY()+L/2);	
		}
	}
	
	public char getKeyBind()
	{
		return keyBind;
	}
}
