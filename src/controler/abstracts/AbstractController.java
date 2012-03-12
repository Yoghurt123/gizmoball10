package controler.abstracts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;

import view.abstracts.AbstractView;
import models.abstracts.AbstractModel;

public abstract class AbstractController implements PropertyChangeListener {
	private ArrayList<AbstractView> regViews;
	private ArrayList<AbstractModel> regModels;

	public AbstractController() {
		regViews = new ArrayList<AbstractView>();
		regModels = new ArrayList<AbstractModel>();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		for (AbstractView view : regViews) {
			view.modelPropertyChange(evt);
		}

	}

	public void registerModel(AbstractModel model) {
		regModels.add(model);
		model.addPropertyChangeListener(this);
	}

	public void unregisterModel(AbstractModel model) {
		regModels.remove(model);
		model.removePropertyChangeListener(this);
	}

	public void registerView(AbstractView view) {
		regViews.add(view);
	}

	public void unregisterView(AbstractView view) {
		regViews.remove(view);
	}

	protected void setModelProperty(String name, Object newValue) {
		for (AbstractModel model : regModels) {
			try {

				Method setter = model.getClass().getMethod("set" + name,
						new Class[] { newValue.getClass() });
				setter.invoke(model, newValue);
			} catch (Exception e) {

			}
		}
	}

	protected Object getModelProperty(String name) {
		for (AbstractModel model : regModels) {
			try {

				Method setter = model.getClass().getMethod("get" + name,
						new Class[] {});
				return setter.invoke(model, null);

			} catch (Exception e) {

			}
		}
		return null;
	}
}
