package test.counterMVC;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class CounterMain {

	public CounterMain() {
	CounterModel cmodel = new CounterModel();
	CounterController cont = new  CounterController();
	CounterDefoultView defView = new CounterDefoultView(cont);
	CounterTextView textView = new CounterTextView();
	cont.registerModel(cmodel);
	cont.registerView(defView);
	cont.registerView(textView);
	
	JFrame frame = new JFrame("Counter");
	frame.getContentPane().add(defView,BorderLayout.CENTER);
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.setVisible(true);
	
	
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CounterMain main = new CounterMain();
	}

}
