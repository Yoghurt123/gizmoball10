package GizmoballGUI;

public class AnimateRunnable implements Runnable {

	/*
	 * A simple implementation of Runnable to support animation makes a callback
	 * to caller.animate() to do an animation step
	 * 
	 * Version of 31/10/11 includes a sleepTime in constructor
	 */

	private Callback caller;
	private boolean keepGoing;
	private long sleepTime;

	public AnimateRunnable(Callback caller, long sleepTime) {
		this.caller = caller;
		this.sleepTime = sleepTime;
	}

	public void run() {
		keepGoing = true;
		try {
			while (keepGoing) {
				caller.animate();
				Thread.sleep(sleepTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopSoon() {
		keepGoing = false;
	}

	public interface Callback {
		public void animate();
	}

}

