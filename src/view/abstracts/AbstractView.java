package view.abstracts;

import java.beans.PropertyChangeEvent;

public interface AbstractView {
	public abstract void modelPropertyChange(PropertyChangeEvent evnt);
}
