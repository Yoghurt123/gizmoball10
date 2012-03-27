package newGizmo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop.Action;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import GizmoballGUI.gizmoBoard;

import newGizmo.GizmoDriver.STATES;
import newGizmo.model.Absorber;
import newGizmo.model.CircleGizmo;
import newGizmo.model.GizmoBoard;
import newGizmo.model.GizmoWalls;
import newGizmo.model.LeftFlipper;
import newGizmo.model.RightFlipper;
import newGizmo.model.SquereGizmo;
import newGizmo.model.TriangleGizmo;
import newGizmo.Gizmo;


public class GizmoMainFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener {
	private static final int L = GizmoSettings.getInstance().getGizmoL();
	protected static MouseEvent me;
	GizmoBoardView board;
	JButton playButton = null;
	JButton pauseButton = null;
	JButton saveButton = null;
	JButton loadButton = null;
	JButton quit = null;
	JButton play = null;
	JButton build = null;
	private static JFrame frame;
	boolean[][] addedBoard = new boolean[19][19];
	Gizmo gizmo;
	RightFlipper rf;
	
	private JToolBar toolBar;
	private JToolBar GizmotoolBar;
	private JToolBar modeToolBar;

	public GizmoMainFrame() {
		board = new GizmoBoardView();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		JToolBar GizmotoolBar = new JToolBar();
		GizmotoolBar.setFloatable(false);
//		JToolBar

		addButtons(toolBar, GizmotoolBar);
		addGizmoButtons(GizmotoolBar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(this);
		scrollPane.addMouseMotionListener(this);
		scrollPane.addKeyListener(this);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setPreferredSize(new Dimension(510, 530));
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(scrollPane);
		contentPane.add(board);
		contentPane.add(GizmotoolBar, BorderLayout.SOUTH);
		setContentPane(contentPane);

		Absorber abs = new Absorber(0, 500);
		GizmoBoard.getInstance().addGizmo(abs);
	}

	public void addButtons(JToolBar toolBar, JToolBar toolBar2)  {
		
		final JToolBar temp = toolBar2;

		final FileDialog loadDialog = new FileDialog(this,
				"Select GizmoBall file to load.", FileDialog.LOAD);
		final FileDialog saveDialog = new FileDialog(this,
				"Select GizmoBall file to save.", FileDialog.SAVE);

		final Random ran = new Random();
		playButton = new JButton(new ImageIcon("run_exc.gif", "Run"));
		playButton
		.setToolTipText("Click this button to start playing Gizmoball");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GizmoDriver.getInstance().setState(STATES.RUN_STATE);
				GizmoBoard.getInstance().run();
				temp.setVisible(false);
				playButton.setEnabled(false);
			}
		});

		pauseButton = new JButton(new ImageIcon("pause.gif"));
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GizmoDriver.getInstance().setState(STATES.POUSE_STATE);
				temp.setVisible(true);
				playButton.setEnabled(true);

			}
		});
		pauseButton.setToolTipText("Click this button to pause the game");
		saveButton = new JButton(new ImageIcon("save_edit.gif"));
		saveButton.setToolTipText("Click this button to save the board layout");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// saveDialog.setVisible(true);
				// b.save(saveDialog.getDirectory() + saveDialog.getFile());
			}
		});
		loadButton = new JButton(new ImageIcon("fldr_obj.gif", "Load"));
		loadButton
		.setToolTipText("Click this button to load a previously saved layout");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDialog.setVisible(true);
				// b.load(loadDialog.getDirectory() + loadDialog.getFile());
			}
		});

		quit = new JButton("Quit");
		quit.setToolTipText("Click this button to quit Gizmoball");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// b.save(saveDialog.getDirectory() + saveDialog.getFile());
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

	private void addGizmoButtons(JToolBar GizmotoolBar) {
		//Create Jbuttons
		final JButton circle = new JButton("Circle");;
		JButton triangle = null;
		JButton square = null;
		JButton absorber = null;
		JButton flipperl = null;
		JButton flipperr = null;
		JButton delete = null;
		JButton ball = null;

		//Add labels and tips to buttons

		circle.setToolTipText("Click this button to add a Circle");
		triangle = new JButton("Triangle");
		triangle.setToolTipText("Click this button to add a Triangle");
		square = new JButton("Square");
		square.setToolTipText("Click this button to add a Square");
		flipperl = new JButton("Left Flipper");
		flipperl.setToolTipText("Click this button to add a Left Flipper");
		flipperr = new JButton("Right Flipper");
		flipperr.setToolTipText("Click this button to add a Right Flipper");


		circle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
//				board.addMouseListener(new MouseAdapter(){
//					public void mouseClicked(MouseEvent mt){
//						int x = (Math.round(mt.getX()/30)*30);
//						int y = (Math.round(mt.getY()/30)*30);
//						int tempx = Math.round(mt.getX()/30);
//						int tempy = Math.round(mt.getY()/30);
//						if(addedBoard[tempx][tempy] != true ){
//							addedBoard[tempx][tempy] = true;
//							if(x<600){
//								if(y<600){
				gizmo = Gizmo.Circle;
				addGizmo();
//									CircleGizmo ci1 = new CircleGizmo(x, y);
//									GizmoBoard.getInstance().addGizmo(ci1);				 
									//System.out.println("Circle added");
//								}
//							}
//						}

//					}
//				});
			};

		});


		triangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Triangle added");
//				board.addMouseListener(new MouseAdapter(){		 
//					public void mouseClicked(MouseEvent mt){
//						int x = (Math.round(mt.getX()/30)*30);
//						int y = (Math.round(mt.getY()/30)*30);
//						int tempx = Math.round(mt.getX()/30);
//						int tempy = Math.round(mt.getY()/30);
//						if(addedBoard[tempx][tempy] != true ){
//							addedBoard[tempx][tempy] = true;
//							if(x<600){
//								if(y<600){
				gizmo = Gizmo.Triangle;
				addGizmo();
									//addGizmo(gizmo.Triangle);
//									TriangleGizmo tr1 = new TriangleGizmo(x, y);
//									GizmoBoard.getInstance().addGizmo(tr1);
//									System.out.println("Triangle added");
//								}
//							}
//						}
//					}
//				});
			};
		});


		square.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gizmo = Gizmo.Square;
				addGizmo();
//				board.addMouseListener(new MouseAdapter(){				 
//					public void mouseClicked(MouseEvent mt){	
//						int x = (Math.round(mt.getX()/30)*30);
//						int y = (Math.round(mt.getY()/30)*30);
//						int tempx = Math.round(mt.getX()/30);
//						int tempy = Math.round(mt.getY()/30);
//						if(addedBoard[tempx][tempy] != true ){
//							addedBoard[tempx][tempy] = true;
//							if(x<600){
//								if(y<600){
//									SquereGizmo sq1 = new SquereGizmo(x, y);
//									GizmoBoard.getInstance().addGizmo(sq1);
//									System.out.println("Square added");
//								}
//							}
//						}
//					}
//				});
			};
		});


		flipperl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gizmo = Gizmo.FlipperL;
				addGizmo();
//				board.addMouseListener(new MouseAdapter(){					 
//					public void mouseClicked(MouseEvent mt){
//						int x = (Math.round(mt.getX()/30)*30);
//						int y = (Math.round(mt.getY()/30)*30);
//						int tempx = Math.round(mt.getX()/30);
//						int tempy = Math.round(mt.getY()/30);
//						if(addedBoard[tempx][tempy] != true ){
//							addedBoard[tempx][tempy] = true;
//							if(x<600){
//								if(y<600){
//									LeftFlipper lf1 = new LeftFlipper(x, y);
//									GizmoBoard.getInstance().addGizmo(lf1);
//								}
//							}
//						}
//
//						System.out.println("Left Flipper Added");
//					}
//				});
			};
		});


		flipperr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				gizmo = Gizmo.FlipperR;
				addGizmo();
//				board.addMouseListener(new MouseAdapter(){				 
//					public void mouseClicked(MouseEvent mt){
//						int x = (Math.round(mt.getX()/30)*30);
//						int y = (Math.round(mt.getY()/30)*30);
//						int tempx = Math.round(mt.getX()/30);
//						int tempy = Math.round(mt.getY()/30);
//						if(addedBoard[tempx][tempy] != true ){
//							addedBoard[tempx][tempy] = true;
//							if(x<600){
//								if(y<600){
//									RightFlipper rf1 = new RightFlipper(x, y);
//									GizmoBoard.getInstance().addGizmo(rf1);
//									System.out.println("Right Flipper added");
//								}
//							}
//						}
//					}
//				});
			};
		});

		delete = new JButton("Delete");
		delete.setToolTipText("Click this button to delete a Gizmo");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sx = (String) JOptionPane.showInputDialog(frame,
						"Enter the X value for the Gizmo you want to delete ",
						"Deleting a Gizmo", JOptionPane.PLAIN_MESSAGE, null,
						null, null);
				String sy = (String) JOptionPane.showInputDialog(frame,
						"Enter the Y value for the Gizmo you want to delete ",
						"Deleting a Gizmo", JOptionPane.PLAIN_MESSAGE, null,
						null, null);

				int x = Integer.parseInt(sx);
				int y = Integer.parseInt(sy);
			}
		});

		ball = new JButton("Ball");
		ball.setToolTipText("Click this button to add a Ball");
		ball.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent mb) {

						// b.addGizmo("Ball"+bl, "Ball", mb.getX()/30,
						// (mb.getY()/30)-2);
						// bl++;
						System.out.println("Ball added");
					}
				});
			};
		});

		absorber = new JButton("Absorber");
		absorber.setToolTipText("Click this button to add a Absorber");
		absorber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sx = (String) JOptionPane.showInputDialog(frame,
						"Enter the X value for the Absorber ",
						"Adding new Absorber", JOptionPane.PLAIN_MESSAGE, null,
						null, null);
				String sy = (String) JOptionPane.showInputDialog(frame,
						"Enter the Y value for the Absorber ",
						"Adding new Absorber", JOptionPane.PLAIN_MESSAGE, null,
						null, null);
				// String sw = (String)
				// JOptionPane.showInputDialog(frame,"Enter the value for the length of the Absorber ","Adding new Absorber",JOptionPane.PLAIN_MESSAGE,null,
				// null, null);
				// String sh = (String)
				// JOptionPane.showInputDialog(frame,"Enter the value for the height of the Absorber ","Adding new Absorber",JOptionPane.PLAIN_MESSAGE,null,
				// null, null);

				int x = Integer.parseInt(sx);
				int y = Integer.parseInt(sy);

				Absorber ab1 = new Absorber(x * L, y * L);
				GizmoBoard.getInstance().addGizmo(ab1);
				// int w = Integer.parseInt(sw);
				// int h = Integer.parseInt(sh);
				// b.addAbsorber("Absorber"+a, "Absorber", x, y, w, h);
				// a++;
				System.out.println("Absorber added");
			}
		});

		GizmotoolBar.add(circle);
		GizmotoolBar.addSeparator();

		GizmotoolBar.add(circle);
		GizmotoolBar.addSeparator();

		GizmotoolBar.add(triangle);
		GizmotoolBar.addSeparator();

		//
		GizmotoolBar.add(square);
		GizmotoolBar.addSeparator();

		GizmotoolBar.add(absorber);
		GizmotoolBar.addSeparator();
		//
		GizmotoolBar.add(flipperl);
		GizmotoolBar.addSeparator();
		//
		GizmotoolBar.add(flipperr);
		GizmotoolBar.addSeparator();

		// toolBar2.add(ball);

	}

	public static void main(String[] args) {
		GizmoMainFrame frame = new GizmoMainFrame();
		frame.setPreferredSize(new Dimension(680, 735));
		frame.pack();
		frame.setVisible(true);
		// SquereGizmo sq1 = new SquereGizmo(30,30);
		SquereGizmo sq2 = new SquereGizmo(60, 30);
		// CircleGizmo ci1 = new CircleGizmo(60, 30);
		GizmoWalls walls = new GizmoWalls(0, 0, 600, 600);
		// SquereGizmo sq2 = new SquereGizmo(60,30);
		TriangleGizmo tr1 = new TriangleGizmo(70, 100);
		CircleGizmo ci1 = new CircleGizmo(90, 120);
		// Absorber absorb = new Absorber(0, 500);
		LeftFlipper lf = new LeftFlipper(100, 100);
		// sq1.linkGizmo(sq2);
		// GizmoBoard.getInstance().addGizmo(sq1);
		//GizmoBoard.getInstance().addGizmo(sq2);
		GizmoBoard.getInstance().addGizmo(walls);
		// GizmoBoard.getInstance().addGizmo(ci1);
		// GizmoBoard.getInstance().addGizmo(sq2);
		// GizmoBoard.getInstance().addGizmo(ci1);
		// GizmoBoard.getInstance().addGizmo(tr1);
		//GizmoBoard.getInstance().addGizmo(lf);
		// GizmoBoard.getInstance().addGizmo(absorb);

	}
	
	public void addGizmo(){
//		
//		System.out.println(gizmo);
//		int x = 0;
//		int y = 0;
//		
		board.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent mt){
				int x = (Math.round(mt.getX()/30)*30);
				int y = (Math.round(mt.getY()/30)*30);
				int tempx = Math.round(mt.getX()/30);
				int tempy = Math.round(mt.getY()/30);
				if(addedBoard[tempx][tempy] != true ){
					addedBoard[tempx][tempy] = true;
					if(x<600){
						if(y<600){
							switch(gizmo){
							case Circle: CircleGizmo ci1 = new CircleGizmo(x,y);
											GizmoBoard.getInstance().addGizmo(ci1);
											System.out.println("Circle added");
											break;
							case Triangle: TriangleGizmo tr1 = new TriangleGizmo(x,y);
											GizmoBoard.getInstance().addGizmo(tr1);
											System.out.println("Triangle added");
											break;
							case Square: SquereGizmo sq1 = new SquereGizmo(x,y);
											GizmoBoard.getInstance().addGizmo(sq1);
											System.out.println("Square added");
											break;
							case FlipperL: LeftFlipper lf1 = new LeftFlipper(x,y);
											GizmoBoard.getInstance().addGizmo(lf1);
											System.out.println("Left Flipper added");
											break;
							case FlipperR: RightFlipper rf1 = new RightFlipper(x,y);
											GizmoBoard.getInstance().addGizmo(rf1);
											System.out.println("Right Flipper added");
											break;
							
							}
						}
					}
				}
			}
		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keynum = e.getKeyCode();
		
		if(e.getKeyCode()==KeyEvent.VK_C){
			rf.rotate();
			System.out.println("TRIGGER PRESSED!!!!!!!!!!!!");
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
