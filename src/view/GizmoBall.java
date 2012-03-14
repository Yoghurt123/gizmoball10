package view;

import java.beans.PropertyChangeEvent;

import view.abstracts.AbstractView;

public class GizmoBall implements AbstractView {

	@Override
	public void modelPropertyChange(PropertyChangeEvent evnt) {
		if (evnt.getPropertyName().equals("Xpos")) {
			int newxpos = (Integer)evnt.getNewValue();
			int newypos = (Integer)evnt.getNewValue();
			
			System.out.println("Ball moved to: ["+newxpos+","+newypos+"]!");
		}
	}

}
