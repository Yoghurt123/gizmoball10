package GizmoballGUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.*;

public class GUI extends JFrame{
	private static JFrame frame;
	//private gizmoBoard gb;
	private Board b;
	private ball mb;
	private mouseListener ml;
	private Timer timer;
	private boolean mode;
	
	private int c = 0;
	private int s = 0;
	private int t = 0;
	private int a = 0;
	private int fl = 0;
	private int fr = 0;
	private int bl = 0;
	//private Grid grid;
	public GUI(){
		super("Flipper prototype");
		
		//gb = new gizmoBoard();
		b = new Board();
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
				mb.move();
				//b.setBackground(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
			}
		});
		
		pauseButton = new JButton(new ImageIcon("pause.gif"));
		pauseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				b.setBackground(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
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
		JButton circle = null;
		JButton triangle = null;
		JButton square = null;
		JButton absorber = null;
		JButton flipperl = null;
		JButton flipperr = null;
		JButton ball = null;
		
		circle = new JButton("Circle");
		circle.setToolTipText("Click this button to add a Circle");
		circle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent me){
					
//				String sx = (String) JOptionPane.showInputDialog(frame,"Enter the X value for the Circle ","Adding new Circle",JOptionPane.PLAIN_MESSAGE,null, null, null);
//				String sy = (String) JOptionPane.showInputDialog(frame,"Enter the Y value for the Circle ","Adding new Circle",JOptionPane.PLAIN_MESSAGE,null, null, null);
				
//				int x = Integer.parseInt(sx);
//				int y = Integer.parseInt(sy);
					System.out.println(me.getX()/30+ " "+ ((me.getY()/30)-2));
						b.addGizmo("Circle"+c, "Circle", me.getX()/30, (me.getY()/30)-2);
						c++;
				System.out.println("Circle added");
			}
				});
		};
		});
		
		triangle = new JButton("Triangle");
		triangle.setToolTipText("Click this button to add a Triangle");
		triangle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent me){
				b.addGizmo("Triangle"+t, "Triangle", me.getX()/30, (me.getY()/30)-2);
				t++;
				System.out.println("Triangle added");
					}
				});
		};
		});
		
		square = new JButton("Square");
		square.setToolTipText("Click this button to add a Square");
		square.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent me){
						
				b.addGizmo("Sqaure"+s, "Square", me.getX()/30, (me.getY()/30)-2);
				s++;
				System.out.println("Square added");
					}
				});
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
				addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent me){
						
				b.addFlipper("FlipperL"+fl, "FlipperL", me.getX()/30, (me.getY()/30)-2, true);
				fl++;
				System.out.println("FlipperL added");
					}
				});
		};
		});
		
		flipperr = new JButton("Right Flipper");
		flipperr.setToolTipText("Click this button to add a Right Flipper");
		flipperr.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent me){
						
				b.addFlipper("FlipperR"+fr, "FlipperR", me.getX()/30, (me.getY()/30)-2, false);
				fr++;
				System.out.println("FlipperR added");
				
					}
				});
				
		};
		});
		
		ball = new JButton("Ball");
		ball.setToolTipText("Click this button to add a Ball");
		ball.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent me){
						
				b.addGizmo("Ball"+bl, "Ball", me.getX()/30, (me.getY()/30)-2);
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
		
		toolBar2.add(ball);


		
	}
	
	  
}
