package doctorWhoGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PathSelectListener implements MouseListener {
	
	private PathComponent pathComponent;
	private Game game;

	public PathSelectListener(PathComponent givenPath, Game givenGame){
		this.pathComponent=givenPath;
		this.game=givenGame;
	}

	/*
	 * This should call the highlight function
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.pathComponent.highlightCLicked();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.pathComponent.highlightHover();
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.pathComponent.unhighlight();
		
	}

	//Not Implemented
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	//Not Implemented
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

}
