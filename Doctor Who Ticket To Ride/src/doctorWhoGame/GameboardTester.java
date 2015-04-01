package doctorWhoGame;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;

import javax.imageio.ImageIO;

import org.jmock.Mockery;
import org.jmock.Expectations;
import org.junit.*;

public class GameboardTester {
	
	Mockery context = new Mockery();
	
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
	
	@Test
	public void TestGameboardHasHandAreaImage() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		Gameboard gameScreen = new Gameboard();
		Field privateHandAreaImage = Gameboard.class.
	            getDeclaredField("handAreaImage");
		
		privateHandAreaImage.setAccessible(true);
		
		BufferedImage handAreaImage = (BufferedImage) privateHandAreaImage.get(gameScreen);
		assertNotNull(handAreaImage);
	}
	
	@Test
	public void TestThatGameboardIsPerformingFileIOToGetHandAreaImage() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException{
		final ImageIO imageInputOutputClass = context.mock(ImageIO.class);
		Gameboard gameScreen = new Gameboard();
		Field privateHandAreaImageFile = Gameboard.class.
	            getDeclaredField("handAreaFile");
		
		privateHandAreaImageFile.setAccessible(true);
		
		final File handAreaFile = (File) privateHandAreaImageFile.get(gameScreen);
		
		context.checking(new Expectations() {{ oneOf (imageInputOutputClass).read(handAreaFile); }} );
		
		context.assertIsSatisfied();
	}
	
	
	
	

}
