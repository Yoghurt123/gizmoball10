package GizmoballGUI;
import java.awt.Dimension;

public class Driver {
	public static void main(String[] args){
		ballModel ball = new ballModel(100,100);
		GUI frame = new GUI(ball);
		
		frame.setPreferredSize(new Dimension(680, 735));
		frame.pack();
		frame.setVisible(true);
	}

}