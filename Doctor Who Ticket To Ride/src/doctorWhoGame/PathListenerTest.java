package doctorWhoGame;

import static org.easymock.EasyMock.createMock;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.MouseEvent;

public class PathListenerTest {
	
	private PathSelectListener pathSelectListener;
	private PathComponent pathMock;
	private MouseEvent mouseEvent;

	@Before
	public void testSetup(){
		this.pathMock=createMock(PathComponent.class);
		this.pathSelectListener=new PathSelectListener(this.pathMock);
		this.mouseEvent=createMock(MouseEvent.class);
	}
	
	@Test
	public void testOfMouseClicked(){
		EasyMock.expect(this.pathSelectListener.highlightPath());
		EasyMock.replay(pathMock);
		EasyMock.replay(mouseEvent);
		
		this.pathSelectListener.mouseClicked(this.mouseEvent);
		
		EasyMock.verify(pathMock);
		
	}

}
