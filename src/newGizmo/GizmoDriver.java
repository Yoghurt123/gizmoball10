package newGizmo;

import java.util.Timer;
import java.util.TimerTask;

import newGizmo.model.GizmoBoard;



public class GizmoDriver {

	public enum STATES {
		RUN_STATE, POUSE_STATE, EDIT_STATE;
	}

	private STATES state = STATES.POUSE_STATE;

	private Timer timer = new Timer("GizmoDriver", true);

	private GizmoBoard board = null;

	public abstract class GizmoTask extends TimerTask {

		/**
		 * 
		 * @return the state of driver
		 */
		protected STATES getCurentDriverState() {
			return state;
		}

		/**
		 * 
		 * @param board
		 */
		public abstract void onRun(GizmoBoard board);

		@Override
		public void run() {
				onRun(GizmoBoard.getInstance());
			//System.out.println(this.getClass().getCanonicalName() + " runned");

		}

	}

	private static GizmoDriver instance = null;

	private GizmoDriver() {

	}

	public static GizmoDriver getInstance() {
		if (instance == null)
			instance = new GizmoDriver();
		return instance;
	}

	public void registerBoard(GizmoBoard b) {
		board = b;
	}

	public void runTask(GizmoTask task, long timeto) {
		timer.schedule(task, timeto);

	}

	public void runShudledTask(GizmoTask task, long wait , long time) {
		timer.schedule(task, wait, time);

	}

	public void setState(STATES state) {
		this.state = state;
		System.out.println("State changed to "  + state);

	}

	public STATES getState() {
		// TODO Auto-generated method stub
		return state;
	}
}
