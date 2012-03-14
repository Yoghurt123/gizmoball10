import java.awt.Dimension;

public class Driver {
	public static void main(String[] args){
		GUI frame = new GUI();
		
		frame.setPreferredSize(new Dimension(680, 735));
		frame.pack();
		frame.setVisible(true);
	}

}