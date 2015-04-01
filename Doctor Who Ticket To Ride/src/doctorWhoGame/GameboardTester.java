package doctorWhoGame;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.*;

import org.junit.*;

public class GameboardTester {
	
	@Test
	public void TestGameboardExists(){
		assertNotNull(new Gameboard());
	}
	
	@Test
	public void TestGameboardHasHandAreaImageFile() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Gameboard gameScreen = new Gameboard();
		Field privateHandAreaImageFile = Gameboard.class.
	            getDeclaredField("handAreaFile");
		
		privateHandAreaImageFile.setAccessible(true);
		
		File handAreaFile = (File) privateHandAreaImageFile.get(gameScreen);
		assertNotNull(handAreaFile);

	}

}
