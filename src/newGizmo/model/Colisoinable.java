package newGizmo.model;

import newGizmo.GizmoDriver;

public interface  Colisoinable {
	
	/**
	 * calculate tiem to collsiion
	 * 
	 * @param ball
	 * @return
	 */
	public abstract double timeToColision(GizmoBall ball);

	/**
	 * on colison time runed event
	 * 
	 * @param ball
	 */
	public abstract void onColisionTime(GizmoBall ball,Object reflectigfrom);
}
