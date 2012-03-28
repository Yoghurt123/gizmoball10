package newGizmo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop.Action;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Point;
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
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import physics.Vect;

import GizmoballGUI.gizmoBoard;

import newGizmo.GizmoDriver.STATES;
import newGizmo.controller.EventListener;
import newGizmo.model.Absorber;
import newGizmo.model.AbstractGizmoModel;
import newGizmo.model.CircleGizmo;
import newGizmo.model.GizmoBall;
import newGizmo.model.GizmoBoard;
import newGizmo.model.GizmoWalls;
import newGizmo.model.LeftFlipper;
import newGizmo.model.RightFlipper;
import newGizmo.model.SquereGizmo;
import newGizmo.model.TriangleGizmo;
import newGizmo.Gizmo;

public class GizmoMainFrame extends JFrame implements MouseListener,
		MouseMotionListener, KeyListener {
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
	boolean[][] addedBoard = new boolean[20][20];
	Gizmo gizmo;
	RightFlipper rf;
	loadSave ls;
	EventListener eventListener;
	int newx, newy, rotate = 1, move = 1;
	AbstractGizmoModel movefrom = null;
	char keypressed;
	String bind;
	
	private JToolBar toolBar;
	private JToolBar GizmotoolBar;
	private JToolBar modeToolBar;

	public GizmoMainFrame() {
		board = new GizmoBoardView();
		KeyPress();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		JToolBar GizmotoolBar = new JToolBar();
		GizmotoolBar.setFloatable(false);
		// JToolBar

		addButtons(toolBar, GizmotoolBar);
		addGizmoButtons(GizmotoolBar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(this);
		scrollPane.addMouseMotionListener(this);
		scrollPane.addKeyListener(eventListener);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setPreferredSize(new Dimension(510, 530));
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(scrollPane);
		contentPane.add(board);
		contentPane.add(GizmotoolBar, BorderLayout.SOUTH);
		setContentPane(contentPane);

		// Absorber abs = new Absorber(0, 500);
		// GizmoBoard.getInstance().addGizmo(abs);
	}

	public void addButtons(JToolBar toolBar, JToolBar toolBar2) {

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
				gizmo = Gizmo.Nothing;
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
				saveDialog.setVisible(true);
				System.out.println(saveDialog.getFile());
				board.save(saveDialog.getDirectory() + saveDialog.getFile());
			}
		});
		loadButton = new JButton(new ImageIcon("fldr_obj.gif", "Load"));
		loadButton
				.setToolTipText("Click this button to load a previously saved layout");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDialog.setVisible(true);
				System.out.println(loadDialog.getFile());
				board.load(loadDialog.getDirectory() + loadDialog.getFile());
				board.update();
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
		// Create Jbuttons
		final JButton circle = new JButton("Circle");
		;
		JButton triangle = null;
		JButton square = null;
		JButton absorber = null;
		JButton flipperl = null;
		JButton flipperr = null;
		JButton delete = null;
		JButton ball = null;
		JButton move = null;
		JButton assignKey = null;

		assignKey = new JButton("AssignKey");
		assignKey.setToolTipText("assign key to activate gizmo");

		// Add labels and tips to buttons

		circle.setToolTipText("Click this button to add a Circle");
		triangle = new JButton("Triangle");
		triangle.setToolTipText("Click this button to add a Triangle");
		square = new JButton("Square");
		square.setToolTipText("Click this button to add a Square");
		flipperl = new JButton("Left Flipper");
		flipperl.setToolTipText("Click this button to add a Left Flipper");
		flipperr = new JButton("Right Flipper");
		flipperr.setToolTipText("Click this button to add a Right Flipper");
		delete = new JButton("Delete");
		delete.setToolTipText("Click this button to delete a Gizmo");
		move = new JButton("Move");
		move.setToolTipText("Click this button to mova a gizmo");

		circle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gizmo = Gizmo.Circle;
				addGizmo();
			};
		});

		triangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sw = (String) JOptionPane.showInputDialog(frame,
							"Please select rotation" + " angle ",
							"Adding new Absorber", JOptionPane.PLAIN_MESSAGE,
							null, null, null);
					rotate = Integer.parseInt(sw);
					rotate = (int) Math.ceil(rotate / 90);
					if (rotate < 1 && rotate > 4) {
						String message = "Select either 90, 180, 270 or 360";
						JOptionPane.showMessageDialog(new JFrame(), message,
								"Dialog", JOptionPane.ERROR_MESSAGE);
					}
					System.out.println("Triangle added");
					gizmo = Gizmo.Triangle;
					addGizmo();
				} catch (NumberFormatException e3) {
					System.out.println("No number entered");
				}
			};
		});

		square.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gizmo = Gizmo.Square;
				addGizmo();
			};
		});

		flipperl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gizmo = Gizmo.FlipperL;
				addGizmo();
			};
		});

		flipperr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gizmo = Gizmo.FlipperR;
				addGizmo();
			};
		});

		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gizmo = Gizmo.Delete;
				addGizmo();
			}
		});

		ball = new JButton("Ball");
		ball.setToolTipText("Click this button to add a Ball");
		ball.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// addMouseListener(new MouseAdapter() {
				// public void mousePressed(MouseEvent mb) {
				//
				// gizmo = Gizmo.Ball;
				// addGizmo();
				// GizmoBall b = new GizmoBall(mb.getX(), mb.getY(), new
				// Vect(0,-100));
				// GizmoBoard.getInstance().addGizmo(b);
				// System.out.println("Ball added");
				// }
				// });
			};
		});

		absorber = new JButton("Absorber");
		absorber.setToolTipText("Click this button to add a Absorber");
		absorber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gizmo = Gizmo.Absorber;
				addGizmo();
			}
		});

		move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gizmo = Gizmo.Move;
				addGizmo();
			}
		});

		assignKey.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				bind = (String) JOptionPane.showInputDialog(frame,
						"Enter the Key to bind" ,"Get Binding Key", JOptionPane.PLAIN_MESSAGE,
						null, null, null);
				gizmo = Gizmo.assignKey;
				addGizmo();
			}
		});

		GizmotoolBar.add(circle);
		GizmotoolBar.addSeparator();

		GizmotoolBar.add(circle);
		GizmotoolBar.addSeparator();

		GizmotoolBar.add(triangle);
		GizmotoolBar.addSeparator();

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

		GizmotoolBar.add(delete);
		GizmotoolBar.addSeparator();

		GizmotoolBar.add(move);
		GizmotoolBar.addSeparator();

		 GizmotoolBar.add(assignKey);

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
		// TriangleGizmo tr1 = new TriangleGizmo(70, 100);
		CircleGizmo ci1 = new CircleGizmo(90, 120);
		// Absorber absorb = new Absorber(0, 500);
		LeftFlipper lf = new LeftFlipper(100, 100);
		// sq1.linkGizmo(sq2);
		// GizmoBoard.getInstance().addGizmo(sq1);
		// GizmoBoard.getInstance().addGizmo(sq2);
		GizmoBoard.getInstance().addGizmo(walls);

	}

	public AbstractGizmoModel MoveFrom(int x, int y) {
		AbstractGizmoModel toMove = null;
		ArrayList<AbstractGizmoModel> temp = GizmoBoard.getInstance()
				.getGizmos();
		for (AbstractGizmoModel mo : temp) {
			if (mo.getX() == x && mo.getY() == y) {
				toMove = mo;
			}
		}

		move = 2;
		return toMove;
	}

	public void MoveTo(int toX, int toY, AbstractGizmoModel movefrom) {
		move = 1;
		AbstractGizmoModel toMove = null;

		if (movefrom != null) {
			System.out.println("name is: " + movefrom.getName());
			toMove = movefrom;
			movefrom.setX(toX);
			movefrom.setY(toY);
			GizmoBoard.getInstance().removeGizmo(movefrom);
			GizmoBoard.getInstance().addGizmo(toMove);
			board.update();
			toMove.setBoundaryBox();

		}
	}

	public void Delete(int x, int y, int tempx, int tempy) {
		AbstractGizmoModel toDelete = GizmoBoard.getInstance().getGizmoByCord(x, y);
		if (toDelete != null) {
			GizmoBoard.getInstance().removeGizmo(toDelete);
			board.update();

		}
	}

	public void addGizmo() {

		board.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mt) {
				int x = (Math.round(mt.getX() / L) * L);
				int y = (Math.round(mt.getY() / L) * L);
				int tempx = Math.round(mt.getX() / L);
				int tempy = Math.round(mt.getY() / L);
				try {
					if (gizmo == Gizmo.Delete) {
						addedBoard[tempx][tempy] = false;
					}
					if (gizmo == Gizmo.Move) {
						addedBoard[tempx][tempy] = false;
					}
					if(gizmo == Gizmo.assignKey)
					{
						addedBoard[tempx][tempy ]= false;
					}

					if (addedBoard[tempx][tempy] != true) {
						addedBoard[tempx][tempy] = true;
						if (x <= 630) {
							if (y <= 630) {
								try {
									switch (gizmo) {
									case Circle:
										CircleGizmo ci1 = new CircleGizmo(x, y);
										GizmoBoard.getInstance().addGizmo(ci1);
										System.out.println("Circle added");
										break;
									case Triangle:
										TriangleGizmo tr1 = new TriangleGizmo(
												x, y, rotate);
										GizmoBoard.getInstance().addGizmo(tr1);
										System.out.println("x is: " + x
												+ " y is: " + y);
										System.out.println("Triangle added");
										break;
									case Square:
										SquereGizmo sq1 = new SquereGizmo(x, y);
										GizmoBoard.getInstance().addGizmo(sq1);
										System.out.println("Square added");
										break;
									case FlipperL:
										LeftFlipper lf1 = new LeftFlipper(x, y);
										GizmoBoard.getInstance().addGizmo(lf1);
										System.out
												.println("Left Flipper added");
										break;
									case FlipperR:
										RightFlipper rf1 = new RightFlipper(x,
												y);
										GizmoBoard.getInstance().addGizmo(rf1);
										System.out
												.println("Right Flipper added");
										break;
									case Delete:
										addedBoard[tempx][tempy] = false;
										Delete(x, y, tempx, tempy);
										break;
									case Absorber:
										Absorber a = new Absorber(x, y);
										GizmoBoard.getInstance().addGizmo(a);
										break;
									case Move:
										if (move == 1) {
											movefrom = MoveFrom(x, y);
											System.out.println(movefrom);
											addedBoard[tempx][tempy] = false;
											gizmo = Gizmo.Nothing;
											break;
										}
										if (move == 2) {
											MoveTo(x, y, movefrom);
											gizmo = Gizmo.Nothing;

											break;
										}
									case Ball:
										// GizmoBall b = new GizmoBall(x, y, new
										// Vect(0,-100));
										// b.startBallMovement();
										// GizmoBoard.getInstance().addGizmo(b);

										System.out.println("Ball added");
										break;
									case assignKey:
										AbstractGizmoModel m = GizmoBoard.getInstance().getGizmoByCord(x, y);
										
										m.setKeyBind(bind.toCharArray()[0]);
										
									}
									
								} catch (NullPointerException n) {
									System.out
											.println("no value set for case statement");
								}
							}
							board.update();
						}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					String message = "Cannot place gizmo outside the frame!!";
					JOptionPane.showMessageDialog(new JFrame(), message,
							"Dialog", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
	}

	public void KeyPress() {
		board.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void keyReleased(KeyEvent arg0) {

			}

			public void keyPressed(KeyEvent ke) {
				keypressed = ke.getKeyChar();
				System.out.println(keypressed);
				if (keypressed == 'c') {
					gizmo = Gizmo.Circle;
					addGizmo();
				} else
				if (keypressed == 't') {
					try {
						String sw = (String) JOptionPane.showInputDialog(frame,
								"Please select rotation" + " angle ",
								"Adding new Absorber",
								JOptionPane.PLAIN_MESSAGE, null, null, null);
						rotate = Integer.parseInt(sw);
						rotate = (int) Math.ceil(rotate / 90);
						if (rotate < 1 && rotate > 4) {
							String message = "Select either 90, 180, 270 or 360";
							JOptionPane.showMessageDialog(new JFrame(),
									message, "Dialog",
									JOptionPane.ERROR_MESSAGE);
						}
						System.out.println("Triangle added");
						gizmo = Gizmo.Triangle;
						addGizmo();
					} catch (NumberFormatException e) {
						System.out.println("No number entered");
					}
				}
				if (keypressed == 's') {
					gizmo = Gizmo.Square;
					addGizmo();
				} else
				if (keypressed == 'd') {
					gizmo = Gizmo.Delete;
					addGizmo();
				} else
				if (keypressed == 'r') {
					gizmo = Gizmo.FlipperR;
					addGizmo();
				} else
				if (keypressed == 'l') {
					gizmo = Gizmo.FlipperL;
					addGizmo();
				} else
				if (keypressed == 'm') {
					gizmo = Gizmo.Move;
					addGizmo();
				} else
				if (keypressed == 'a') {
					gizmo = Gizmo.Absorber;
					addGizmo();
				} else
				if (keypressed == 'z') {
					gizmo = null;
				}else
				if (keypressed == 'b') {

				} 
				
				else 
					GizmoBoard.getInstance().acctivateGizmoByItsKey(keypressed);
				
				
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
	public void mouseDragged(MouseEvent me) {

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
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
