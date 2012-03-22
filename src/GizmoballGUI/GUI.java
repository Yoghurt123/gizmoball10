package GizmoballGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.*;

import GizmoballGUI.AnimateRunnable.Callback;

public class GUI extends JFrame{
	private static JFrame frame;
	//private gizmoBoard gb;
	//private ApplicationWindow aw;
	private Board b;
	private ballModel bm;
	private Timer timer;
	private boolean mode;
	private int c = 0;
	private int s = 0;
	private int t = 0;
	private int a = 0;
	private int fl = 0;
	private int fr = 0;
	private int bl = 0;
//	private boolean running = false;
//	private boolean MouseListenerActive;
	//private Grid grid;
	public GUI(ballModel bm){
		super("Flipper prototype");
		
		//gb = new gizmoBoard();
		b = new Board(bm);
			//grid = new Grid(22, 22);
		b.setPreferredSize(new Dimension(660, 660));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		JToolBar toolBar2 = new JToolBar();
		toolBar2.setFloatable(false);
		addButtons(toolBar);
		addGizmoButtons(toolBar2);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setPreferredSize(new Dimension(510,530));
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(scrollPane);
		contentPane.add(b);
		
		contentPane.add(toolBar2, BorderLayout.SOUTH);
		setContentPane(contentPane);
		
		//animator = new AnimateRunnable(this, (int)refreshtime);
	}
	
	
	public void addButtons(JToolBar toolBar) {
		
		final FileDialog loadDialog = new FileDialog(this, "Select GizmoBall file to load.", FileDialog.LOAD);
		final FileDialog saveDialog = new FileDialog(this, "Select GizmoBall file to save.", FileDialog.SAVE);
		
		JButton playButton = null;
		JButton pauseButton = null;
		JButton saveButton = null;
		JButton loadButton = null;
		JButton quit = null;
		final Random ran = new Random();
		
		
		
		playButton = new JButton(new ImageIcon("run_exc.gif","Run"));
		playButton.setToolTipText("Click this button to start playing Gizmoball");
		playButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				b.setMode(true);
//				if (!running) {
//					new Thread(animator).start();
//					running = true;
//				} else {
//					animator.stopSoon();
//					running = false;
//				}
				//new MainProgram();
				
			}
		});
		
		pauseButton = new JButton(new ImageIcon("pause.gif"));
		pauseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//new MainProgram();
				//b.setBackground(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
				b.setMode(false);
			}
		});
		pauseButton.setToolTipText("Click this button to pause the game");
		saveButton = new JButton(new ImageIcon("save_edit.gif"));
		saveButton.setToolTipText("Click this button to save the board layout");
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//saveDialog.setVisible(true);
				b.save(saveDialog.getDirectory() + saveDialog.getFile());
			}
		});
		loadButton = new JButton(new ImageIcon("fldr_obj.gif","Load"));
		loadButton.setToolTipText("Click this button to load a previously saved layout");
		loadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			loadDialog.setVisible(true);
			b.load(loadDialog.getDirectory() + loadDialog.getFile());
			}
		});
		
		quit = new JButton ("Quit");
		quit.setToolTipText("Click this button to quit Gizmoball");
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//b.save(saveDialog.getDirectory() + saveDialog.getFile());
				System.exit(0);
			}
		});
		
		toolBar.add(playButton);
		toolBar.addSeparator();
		toolBar.add(pauseButton);
		toolBar.addSeparator();
		toolBar.add(saveButton);
		toolBar.addSeparator();
		toolBar.add(loadButton);
		toolBar.add(quit);
		
	}
	
	
	private void addGizmoButtons(JToolBar toolBar2) {
		final JButton circle = new JButton ("Circle");
		JButton triangle = null;
		JButton square = null;
		JButton absorber = null;
		JButton flipperl = null;
		JButton flipperr = null;
		JButton delete = null;
		JButton ball = null;
		
		//MouseListenerActive = false;
		
		
//		circle = new JToggleButton("Circle");
		circle.setToolTipText("Click this button to add a Circle");
		circle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String sx = (String) JOptionPane.showInputDialog(frame,"Enter the X value for the Circle ","Adding new Circle",JOptionPane.PLAIN_MESSAGE,null, null, null);
				String sy = (String) JOptionPane.showInputDialog(frame,"Enter the Y value for the Circle ","Adding new Circle",JOptionPane.PLAIN_MESSAGE,null, null, null);
				
				int x = Integer.parseInt(sx);
				int y = Integer.parseInt(sy);
				b.addGizmo("Circle"+c, "Circle", x, y);
				c++;
				System.out.println("Circle added");
//				addMouseListener(new MouseAdapter(){
//				public void mouseClicked(MouseEvent mc){
//						b.addGizmo("Circle"+c, "Circle", mc.getX()/30, (mc.getY()/30)-2);
//						c++;
//				System.out.println("Circle added");
//				//circle.set;
//			}
//				});
		};
		
		});
		
		
		triangle = new JButton("Triangle");
		triangle.setToolTipText("Click this button to add a Triangle");
		triangle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String sx = (String) JOptionPane.showInputDialog(frame,"Enter the X value for the Triangle ","Adding new Triangle",JOptionPane.PLAIN_MESSAGE,null, null, null);
				String sy = (String) JOptionPane.showInputDialog(frame,"Enter the Y value for the Triangle ","Adding new Triangle",JOptionPane.PLAIN_MESSAGE,null, null, null);
				
				int x = Integer.parseInt(sx);
				int y = Integer.parseInt(sy);
				b.addGizmo("Triangle"+t, "Triangle", x, y);
				t++;
				System.out.println("Triangle added");
//				addMouseListener(new MouseAdapter(){
//					public void mouseClicked(MouseEvent mt){
//				b.addGizmo("Triangle"+t, "Triangle", mt.getX()/30, (mt.getY()/30)-2);
//				t++;
//				System.out.println("Triangle added");
//					}
//				});
		};
		});
		
		square = new JButton("Square");
		square.setToolTipText("Click this button to add a Square");
		square.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String sx = (String) JOptionPane.showInputDialog(frame,"Enter the X value for the Square ","Adding new Square",JOptionPane.PLAIN_MESSAGE,null, null, null);
				String sy = (String) JOptionPane.showInputDialog(frame,"Enter the Y value for the Square ","Adding new Square",JOptionPane.PLAIN_MESSAGE,null, null, null);
				
				int x = Integer.parseInt(sx);
				int y = Integer.parseInt(sy);
				b.addGizmo("Square"+s, "Square", x, y);
				s++;
				System.out.println("Square added");
//				addMouseListener(new MouseAdapter(){
//					public void mousePressed(MouseEvent ms){
//						
//				b.addGizmo("Square"+s, "Square", ms.getX()/30, (ms.getY()/30)-2);
//				s++;
//				//System.out.println("Square added");
//					}
//				});
				
		};
		});
		
		absorber = new JButton("Absorber");
		absorber.setToolTipText("Click this button to add a Absorber");
		absorber.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String sx = (String) JOptionPane.showInputDialog(frame,"Enter the X value for the Absorber ","Adding new Absorber",JOptionPane.PLAIN_MESSAGE,null, null, null);
				String sy = (String) JOptionPane.showInputDialog(frame,"Enter the Y value for the Absorber ","Adding new Absorber",JOptionPane.PLAIN_MESSAGE,null, null, null);
				String sw = (String) JOptionPane.showInputDialog(frame,"Enter the value for the length of the Absorber ","Adding new Absorber",JOptionPane.PLAIN_MESSAGE,null, null, null);
				String sh = (String) JOptionPane.showInputDialog(frame,"Enter the value for the height of the Absorber ","Adding new Absorber",JOptionPane.PLAIN_MESSAGE,null, null, null);
				
				int x = Integer.parseInt(sx);
				int y = Integer.parseInt(sy);
				int w = Integer.parseInt(sw);
				int h = Integer.parseInt(sh);
				b.addAbsorber("Absorber"+a, "Absorber", x, y, w, h);
				a++;
				System.out.println("Absorber added");
			}
		});
		
		flipperl = new JButton("Left Flipper");
		flipperl.setToolTipText("Click this button to add a Left Flipper");
		flipperl.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String sx = (String) JOptionPane.showInputDialog(frame,"Enter the X value for the FlipperL ","Adding new FlipperL",JOptionPane.PLAIN_MESSAGE,null, null, null);
				String sy = (String) JOptionPane.showInputDialog(frame,"Enter the Y value for the FlipperL ","Adding new FlipperL",JOptionPane.PLAIN_MESSAGE,null, null, null);
				
				int x = Integer.parseInt(sx);
				int y = Integer.parseInt(sy);
				b.addFlipper("FlipperL"+fl, "FlipperL", x, y, true);
				fl++;
				System.out.println("FlipperL added");
//				addMouseListener(new MouseAdapter(){
//					public void mousePressed(MouseEvent ml){
//						
//				b.addFlipper("FlipperL"+fl, "FlipperL", ml.getX()/30, (ml.getY()/30)-2, true);
//				fl++;
//				System.out.println("FlipperL added");
//					}
//				});
		};
		});
		
		flipperr = new JButton("Right Flipper");
		flipperr.setToolTipText("Click this button to add a Right Flipper");
		flipperr.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String sx = (String) JOptionPane.showInputDialog(frame,"Enter the X value for the FlipperR ","Adding new FlipperR",JOptionPane.PLAIN_MESSAGE,null, null, null);
				String sy = (String) JOptionPane.showInputDialog(frame,"Enter the Y value for the FlipperR ","Adding new FlipperR",JOptionPane.PLAIN_MESSAGE,null, null, null);
				
				int x = Integer.parseInt(sx);
				int y = Integer.parseInt(sy);
				b.addFlipper("FlipperR"+fr, "FlipperR", x, y, true);
				fr++;
				System.out.println("FlipperR added");
//				addMouseListener(new MouseAdapter(){
//					public void mousePressed(MouseEvent mr){
//						
//				b.addFlipper("FlipperR"+fr, "FlipperR", mr.getX()/30, (mr.getY()/30)-2, false);
//				fr++;
//				System.out.println("FlipperR added");
//				
//					}
//				});
				
		};
		});
		
		delete = new JButton("Delete");
		delete.setToolTipText("Click this button to delete a Gizmo");
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String sx = (String) JOptionPane.showInputDialog(frame,"Enter the X value for the Gizmo you want to delete ","Deleting a Gizmo",JOptionPane.PLAIN_MESSAGE,null, null, null);
				String sy = (String) JOptionPane.showInputDialog(frame,"Enter the Y value for the Gizmo you want to delete ","Deleting a Gizmo",JOptionPane.PLAIN_MESSAGE,null, null, null);
				
				int x = Integer.parseInt(sx);
				int y = Integer.parseInt(sy);
			}
		});
		
		ball = new JButton("Ball");
		ball.setToolTipText("Click this button to add a Ball");
		ball.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent mb){
						
				b.addGizmo("Ball"+bl, "Ball", mb.getX()/30, (mb.getY()/30)-2);
				bl++;
				System.out.println("Ball added");
					}
				});
		};
		});
		
		toolBar2.add(circle);
		toolBar2.addSeparator();
		
		toolBar2.add(circle);
		toolBar2.addSeparator();

		toolBar2.add(triangle);
		toolBar2.addSeparator();

		toolBar2.add(square);
		toolBar2.addSeparator();

		toolBar2.add(absorber);
		toolBar2.addSeparator();
		
		toolBar2.add(flipperl);
		toolBar2.addSeparator();
		
		toolBar2.add(flipperr);
		toolBar2.addSeparator();
		
		//toolBar2.add(ball);


		
	}


	//@Override
//	public void animate() {
//		Board.getInstance().update((double)1/refreshtime);
//		paint(this.getGraphics());
//		
//	}
	
	  
}
