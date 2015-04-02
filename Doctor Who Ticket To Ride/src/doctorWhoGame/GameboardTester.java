package doctorWhoGame;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

public class GameboardTester {
	
	private File handAreaFile;
	private BufferedImage handAreaImage;
	private Integer imageWidth;
	private Integer imageHeight;
	
	@Before
	public void InitializingVariables() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Gameboard gameScreen = new Gameboard();
		
		Field privateHandAreaImageFile = Gameboard.class.
	            getDeclaredField("handAreaFile");
		
		privateHandAreaImageFile.setAccessible(true);
		this.handAreaFile = (File) privateHandAreaImageFile.get(gameScreen);
		
		Field privateHandAreaImage = Gameboard.class.
	            getDeclaredField("handAreaImage");
		privateHandAreaImage.setAccessible(true);
		this.handAreaImage = (BufferedImage) privateHandAreaImage.get(gameScreen);
		
		Field privateHandImageWidth = Gameboard.class.
	            getDeclaredField("handImageWidth");
		Field privateHandImageHeight = Gameboard.class.
				getDeclaredField("handImageHeight");
		
		privateHandImageWidth.setAccessible(true);
		privateHandImageHeight.setAccessible(true);
		
		this.imageWidth = (Integer) privateHandImageWidth.get(gameScreen);
		this.imageHeight = (Integer) privateHandImageHeight.get(gameScreen);
	}
	
	@Test
	public void TestGameboardExists(){
		assertNotNull(new Gameboard());
	}
	
	@Test
	public void TestGameboardHasHandAreaImageFile(){
		
		assertNotNull(handAreaFile);

	}
	
	@Test
	public void TestGameboardHasHandAreaImage(){
		
		assertNotNull(handAreaImage);
	}
	
	@Test
	public void TestImageHeightAndWidthAssignedProperlyForImage(){
		Gameboard gameScreen = new Gameboard();
		
		assertEquals((int) imageWidth, (int) handAreaImage.getWidth());
		assertEquals((int) imageHeight, (int) handAreaImage.getHeight());
	}
	

}
