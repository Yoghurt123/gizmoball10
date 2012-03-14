package models;

import physics.Vect;
import models.abstracts.AbstractModel;

public class GizmoBall extends AbstractModel {

	private int xPos = 0;
	private int yPos = 0;
	private double mu = 0.025;
	private double mu2 = 0.025;// *L
	private double gravity = 25; // S

	private static final double minVelocity = 0.01; // L/s
	private static final double maxVelocity = 200; // L/s

	private Vect velocity;
	private double v;
	private Vect g;
	private long lastUpdated;

	public int getXpos() {
		return xPos;
	}

	public int getYpos() {
		return yPos;
	}

	public void setXpos(Integer xpos) {
		this.xPos = xpos;
	}

	public void setYpos(Integer ypos) {
		this.yPos = ypos;
	}

	/**
	 * applaying fraction equasion to velocity of ball over dtime time
	 * 
	 * @param dtime
	 *            time science last update of v
	 * @return new velocity of ball on dtime time
	 */

	private double applayFraction(double dtime) {
		return v * (1 - mu * dtime - mu2 * Math.abs(v) * dtime);
	}

	private double applayGravityTOcords(double dtime) {
		return v + gravity * dtime;
	}

	
	public void aplayPhisics()
	{
		double dtime = getSecScLastChange();
		v = applayGravityTOcords(dtime);
		v = applayFraction(dtime);
	}
	
	private double getSecScLastChange() {
		// time science last update of objects ...
		long ntime = System.currentTimeMillis();
		long dtime = ntime - lastUpdated;
		lastUpdated = ntime;
		return (double) dtime / 10000;

	}

}
