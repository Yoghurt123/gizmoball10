package test.counterMVC;

import controler.abstracts.AbstractController;

public class CounterController extends AbstractController {

	public void changeCounterValue(int newVal) {
		setModelProperty("counter", newVal);
	}

	public void incrament() {
		int oldvalue = (Integer) getModelProperty("counter");
		oldvalue++;
		setModelProperty("counter", oldvalue);
	}

	public void decrament() {
		int oldvalue = (Integer) getModelProperty("counter");
		oldvalue--;
		setModelProperty("counter", oldvalue);
	}

	public void reset() {
		setModelProperty("counter", 0);
	}

}
