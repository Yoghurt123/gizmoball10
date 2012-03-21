package newGizmo.model;

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
			onColisionTime(board.getBall(),reflecFrom);
			/**
			 * activate hited gizmo
			 */
			onHitEvent();
		}

	}

	protected int x = 0;
	protected int y = 0;
	protected String name;

	public AbstractGizmoModel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public abstract void onHitEvent();

	public abstract void onActivationEvent();

	public abstract void onDeactivationEvent();
}
