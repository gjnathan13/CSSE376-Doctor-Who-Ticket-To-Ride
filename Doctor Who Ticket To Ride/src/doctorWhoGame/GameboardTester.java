package doctorWhoGame;

public class GameboardTester {
	
	@Test
	public void TestGameboardExists(){
		assertNotEqual(new Gameboard(), null);
	}

}
