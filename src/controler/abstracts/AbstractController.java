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

	/**
	 * register model with controller
	 * 
	 * @param model
	 *            to register
	 */
	public void registerModel(AbstractModel model) {
		regModels.add(model);
		model.addPropertyChangeListener(this);
	}

	/**
	 * unregister model with controller
	 * 
	 * @param model
	 *            to register
	 */
	public void unregisterModel(AbstractModel model) {
		regModels.remove(model);
		model.removePropertyChangeListener(this);
	}

	/**
	 * register view to controller
	 * 
	 * @param view
	 */
	public void registerView(AbstractView view) {
		regViews.add(view);
	}

	/**
	 * unregister view with controller
	 * 
	 * @param view
	 */
	public void unregisterView(AbstractView view) {
		regViews.remove(view);
	}

	/**
	 * set new value for proprty in model
	 * 
	 * @param name
	 *            of property
	 * @param newValue
	 *            value to set
	 */
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

	/**
	 * acces the property of model from Contloller
	 * 
	 * @param name
	 *            name of property
	 * @return the property value
	 */
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

	/**
	 * run method from modle by name
	 * 
	 * @param name
	 *            of method to run in model
	 */
	protected void fireModelMethod(String name) {
		for (AbstractModel model : regModels) {
			try {
				Method met = model.getClass().getMethod(name, new Class[] {});
				met.invoke(model, null);
			} catch (Exception e) {

			}
		}
	}
}
