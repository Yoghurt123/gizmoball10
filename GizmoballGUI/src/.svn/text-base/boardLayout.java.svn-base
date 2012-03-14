import java.awt.HeadlessException;
import java.io.ObjectInputStream.GetField;
import java.security.GuardedObject;
import java.util.ArrayList;

public class boardLayout {

	/**
	 * lenght of box in pixels
	 */

	private int boxSize;
	private int verticalSize;
	private int horisontallSize;

	/**
	 * Setup the size of basic play grid, total size is vert*psize x horis *
	 * psize in pixels
	 * 
	 * @param vert
	 *            number of greed plates
	 * @param horis
	 *            number of greed plates on vertical
	 * @param psize
	 */

	boardLayout() {

		verticalSize = 50;
		horisontallSize = 50;
		boxSize = 15;
	}

	public static void setGizmoSettings(int vert, int horis, int psize) {
		getInstance().verticalSize = vert;
		getInstance().horisontallSize = horis;
		getInstance().boxSize = psize;

	}

	private static boardLayout instance = null;

	public static boardLayout getInstance() {
		if (instance == null)
			instance = new boardLayout();
		return instance;
	}

	public int getVertSize() {
		return boxSize * verticalSize;
	}

	public int getHorisSize() {
		return boxSize * horisontallSize;
	}

	public int getBoxSize() {
		return boxSize;
	}

	public int getCordinate(int x) {
		int t = (x * boxSize) ;//+ (boxSize / 2);
		return t;
	}

	public int getVerBoxes() {
		return verticalSize;
	}

	public int getHorBoxes() {
		return horisontallSize;
	}

}
