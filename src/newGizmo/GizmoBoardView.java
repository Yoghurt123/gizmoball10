package newGizmo;

import java.awt.Canvas;
import java.awt.Graphics;

import newGizmo.model.AbstractGizmoModel;
import newGizmo.model.GizmoBall;
import newGizmo.model.GizmoBoard;

public class GizmoBoardView extends Canvas {

	public GizmoBoardView() {
		GizmoDriver.getInstance().runShudledTask(new GizmoUpdateViewTask(),
				1000, 50);
	}

	private class GizmoUpdateViewTask extends GizmoDriver.GizmoTask {
		public GizmoUpdateViewTask() {
			GizmoDriver.getInstance().super();
		}

		@Override
		public void onRun(GizmoBoard board) {
			switch (getCurentDriverState())
			{
			case RUN_STATE:
				board.checkColisions();
				break;
			}
			paint(getGraphics());

		}

	}

	@Override
	public void paint(Graphics g) {

		GizmoBall ball = GizmoBoard.getInstance().getBall();
		if (ball != null)
			ball.paint(g);

		for (AbstractGizmoModel giz : GizmoBoard.getInstance().getGizmos()) {
			giz.paint(g);
		}

	}

}
