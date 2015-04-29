package doctorWhoGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PathSelectListener implements MouseListener {
	
	private PathComponent pathComponent;

	public PathSelectListener(PathComponent givenPath){
		this.pathComponent=givenPath;
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

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
