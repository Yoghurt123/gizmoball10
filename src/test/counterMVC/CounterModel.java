package test.counterMVC;

import models.abstracts.AbstractModel;

public class CounterModel extends AbstractModel {
	int counter = 0;

	public int getcounter() {
		return counter;
	}

	public void setcounter(Integer c) {
		int oldcounter = counter;
		this.counter = c;
		firePropertyChange("counter", oldcounter, c);
	}

}
