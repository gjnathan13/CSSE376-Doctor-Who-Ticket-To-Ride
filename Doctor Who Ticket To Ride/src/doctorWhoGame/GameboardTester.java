package doctorWhoGame;

import static org.junit.Assert.*;
import org.junit.*;

public class GameboardTester {
	
	@Test
	public void TestGameboardExists(){
		assertNotNull(new Gameboard());
	}

}
