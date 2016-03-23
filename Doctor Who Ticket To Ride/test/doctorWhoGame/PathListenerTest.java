package doctorWhoGame;

import static org.easymock.EasyMock.createMock;

import java.awt.event.MouseEvent;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class PathListenerTest {

	private PathSelectListener pathSelectListener;
	private PathComponent pathMock;
	private MouseEvent mouseEvent;
	private Game gameMock;

	@Before
	public void testSetup() throws NoSuchFieldException, SecurityException {
		this.pathMock = createMock(PathComponent.class);
		this.gameMock = createMock(Game.class);
		this.pathSelectListener = new PathSelectListener(this.pathMock, this.gameMock);
		this.mouseEvent = createMock(MouseEvent.class);
	}

	@Test
	public void testOfMouseClicked() {
		EasyMock.expect(this.mouseEvent.getX()).andReturn(0);
		EasyMock.expect(this.mouseEvent.getY()).andReturn(0);
		Game testGame = new Game(null, null, null, null);
		EasyMock.expect(testGame.checkIfCanBuyPath());
		pathMock.highlightCLicked(0, 0);
		EasyMock.expectLastCall();
		EasyMock.replay(this.mouseEvent);
		EasyMock.replay(pathMock);
		EasyMock.replay(gameMock);

		this.pathSelectListener.mouseClicked(this.mouseEvent);

		EasyMock.verify(pathMock);
		EasyMock.verify(gameMock);
		EasyMock.verify(this.mouseEvent);

	}
}
