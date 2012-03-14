package GizmoballGUI;

import java.awt.event.*;

public class mouseListener extends MouseAdapter implements MouseMotionListener,KeyListener,ActionListener {
	ball b;
	public mouseListener(ball b){
		super();
	}
	
	public void mouseClicked(MouseEvent e) {
		  b.move();
		}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		b.move();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
