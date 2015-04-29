package doctorWhoGame;

import static org.easymock.EasyMock.createMock;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.awt.event.MouseEvent;

public class PathListenerTest {
	
	private PathSelectListener pathSelectListener;
	private PathComponent pathMock;
	private MouseEvent mouseEvent;

	@Before
	public void testSetup() throws NoSuchFieldException, SecurityException{
		this.pathMock=createMock(PathComponent.class);
		this.pathSelectListener=new PathSelectListener(this.pathMock);
		this.mouseEvent=createMock(MouseEvent.class);
		
	}
	
	@Test
	public void testOfMouseClicked(){
		pathMock.highlightCLicked();
		EasyMock.expectLastCall();
		EasyMock.replay(pathMock);
		
		this.pathSelectListener.mouseClicked(this.mouseEvent);
		
		EasyMock.verify(pathMock);
		
	}
	
	@Test
	public void testOfMouseEntered(){
		pathMock.highlightHover();
		EasyMock.expectLastCall();
		EasyMock.replay(pathMock);
		
		this.pathSelectListener.mouseEntered(this.mouseEvent);
		
		EasyMock.verify(pathMock);
	}
	
	@Test
	public void testOfMouseExit(){
		
	}

}
