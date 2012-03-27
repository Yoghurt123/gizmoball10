package newGizmo.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;

import GizmoballGUI.Board; 

import newGizmo.model.RightFlipper;

public class EventListener extends MouseAdapter implements
MouseMotionListener, KeyListener, ActionListener {
	
	RightFlipper rf;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keynum = e.getKeyCode();
		
		if(e.getKeyCode()==KeyEvent.VK_C){
			rf.rotate();
			System.out.println("TRIGGER PRESSED!!!!!!!!!!!!");
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
