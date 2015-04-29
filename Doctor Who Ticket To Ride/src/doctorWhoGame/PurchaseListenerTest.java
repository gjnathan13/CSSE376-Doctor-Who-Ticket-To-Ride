package doctorWhoGame;

import static org.easymock.EasyMock.*;

import org.junit.Before;

public class PurchaseListenerTest {
	

	private Player playerMock;

	@Before
	public void testSetup(){
		this.playerMock=createMock(Player.class);
	}

}
