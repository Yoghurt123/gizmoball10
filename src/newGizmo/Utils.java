package newGizmo;

public class Utils {
	/**
	 * convert miliseconds to seconds
	 * 
	 * @param ms
	 * @return
	 */
	public static double Msec2Sec(long ms) {
		return ms / 1000;
	}

	/**
	 * convert seconds to miliseconds
	 * 
	 * @param sec
	 * @return
	 */
	public static long Sec2Msec(double sec) {
		return (long) (1000 * sec);
	}
}
