package newGizmo.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;

import newGizmo.model.RightFlipper;

public class EventListener extends MouseAdapter implements
MouseMotionListener, KeyListener, ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keynum = e.getKeyCode();
		
		if((keynum >= 65) && (keynum <= 91)){
			RightFlipper.triggerEvent()
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keynum = e.getKeyCode();
		
		if((keynum >= 65) && (keynum <=91)){
			
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
