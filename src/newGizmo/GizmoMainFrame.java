package newGizmo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import GizmoballGUI.gizmoBoard;

import newGizmo.GizmoDriver.STATES;
import newGizmo.model.CircleGizmo;
import newGizmo.model.GizmoBoard;
import newGizmo.model.GizmoWalls;
import newGizmo.model.SquereGizmo;

public class GizmoMainFrame extends JFrame {
	GizmoBoardView board;

	public GizmoMainFrame() {
		board = new GizmoBoardView();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		JToolBar toolBar2 = new JToolBar();
		toolBar2.setFloatable(false);

		addButtons(toolBar);
		// addGizmoButtons(toolBar2);

		JScrollPane scrollPane = new JScrollPane();

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setPreferredSize(new Dimension(510, 530));
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(scrollPane);
		contentPane.add(board);
		contentPane.add(toolBar2, BorderLayout.SOUTH);
		setContentPane(contentPane);
	}

	public void addButtons(JToolBar toolBar) {

		final FileDialog loadDialog = new FileDialog(this,
				"Select GizmoBall file to load.", FileDialog.LOAD);
		final FileDialog saveDialog = new FileDialog(this,
				"Select GizmoBall file to save.", FileDialog.SAVE);

		JButton playButton = null;
		JButton pauseButton = null;
		JButton saveButton = null;
		JButton loadButton = null;
		JButton quit = null;
		final Random ran = new Random();
		playButton = new JButton(new ImageIcon("run_exc.gif", "Run"));
		playButton
				.setToolTipText("Click this button to start playing Gizmoball");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GizmoDriver.getInstance().setState(STATES.RUN_STATE);
				GizmoBoard.getInstance().run();
			}
		});

		pauseButton = new JButton(new ImageIcon("pause.gif"));
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GizmoDriver.getInstance().setState(STATES.POUSE_STATE);

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

	public static void main(String[] args) {
		GizmoMainFrame frame = new GizmoMainFrame();
		frame.setPreferredSize(new Dimension(680, 735));
		frame.pack();
		frame.setVisible(true);
		// SquereGizmo sq1 = new SquereGizmo(30,30);
		 SquereGizmo sq2 = new SquereGizmo(60,30);
		//CircleGizmo ci1 = new CircleGizmo(60, 30);
		GizmoWalls walls = new GizmoWalls(0, 0, 600, 600);
		// sq1.linkGizmo(sq2);
		// GizmoBoard.getInstance().addGizmo(sq1);
		 GizmoBoard.getInstance().addGizmo(sq2);
		GizmoBoard.getInstance().addGizmo(walls);
		//GizmoBoard.getInstance().addGizmo(ci1);

	}
}
