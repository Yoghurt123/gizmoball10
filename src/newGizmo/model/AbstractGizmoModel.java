package newGizmo.model;

import java.util.LinkedList;

import newGizmo.Drawable;
import newGizmo.GizmoDriver;
import newGizmo.SavleLoadable;

public abstract class AbstractGizmoModel implements Drawable, Colisoinable,
		SavleLoadable {

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
		 *            the object Wall cyrcle etc what is reflecting the ball
		 */
		public onColisionTimeTask(Object reflector) {
			GizmoDriver.getInstance().super();
			reflecFrom = reflector;
		}

		@Override
		public void onRun(GizmoBoard board) {
			/**
			 * calculate new ball movement after reflection
			 */
			onColisionTime(board.getBall(), reflecFrom);
			/**
			 * activate hited gizmo
			 */
			onHitEvent();
		}

	}

	protected int x = 0;
	protected int y = 0;
	protected String name;

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
			;
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

	public abstract void onHitEvent();

	public abstract void onActivationEvent();

	public abstract void onDeactivationEvent();
}
