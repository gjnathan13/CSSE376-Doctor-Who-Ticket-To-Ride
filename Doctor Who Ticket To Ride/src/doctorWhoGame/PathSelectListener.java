package doctorWhoGame;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public class PathSelectListener implements MouseInputListener {

	private PathComponent pathComponent;
	private Game game;

	public PathSelectListener(PathComponent givenPath, Game givenGame) {
		this.pathComponent = givenPath;
		this.game = givenGame;
	}

	/*
	 * This should call the highlight function
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (game.checkIfCanBuyPath()) {
			int xMouse = arg0.getX();
			int yMouse = arg0.getY();
			this.pathComponent.highlightCLicked(xMouse, yMouse);
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// not used
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// not used
	}

	// Not Implemented
	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	// Not Implemented
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		int xMouse = arg0.getX();
		int yMouse = arg0.getY();
		this.pathComponent.checkHighlight(xMouse, yMouse);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// not used
	}

}
