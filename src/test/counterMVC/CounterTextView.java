package test.counterMVC;

import java.beans.PropertyChangeEvent;

import view.abstracts.AbstractView;

public class CounterTextView implements AbstractView {

	@Override
	public void modelPropertyChange(PropertyChangeEvent evnt) {
		System.out.println("FROM TEXTVIEW: ["+ evnt.getPropertyName()+"] updated from ["+evnt.getOldValue()+"] to "+evnt.getNewValue()+"] .");

	}

}
