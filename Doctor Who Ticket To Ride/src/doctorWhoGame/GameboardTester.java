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
	
	public static boolean getNumberOfColorCalled = false;
	private Gameboard gameScreen;
	private File handAreaFile;
	private BufferedImage handAreaImage;
	private Integer imageWidth;
	private Integer imageHeight;
	private Hand handCheck;
	
	@Before
	public void InitializingVariables() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		this.gameScreen = new Gameboard();
		
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
		
		Field privateHand = Gameboard.class.getDeclaredField("currentHand");
		privateHand.setAccessible(true);
		this.handCheck = (Hand) privateHand.get(gameScreen);
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
		assertEquals((int) imageWidth, (int) handAreaImage.getWidth());
		assertEquals((int) imageHeight, (int) handAreaImage.getHeight());
	}
	
	@Test
	public void TestThatImageHeightAndWidthCanBeProperlyObtainedWithGetter(){
		int[] testingHandImageDimensions = this.gameScreen.getHandImageDimensions();
		assertEquals((int) imageWidth, testingHandImageDimensions[0]);
		assertEquals((int) imageHeight, testingHandImageDimensions[1]);
	}
	
	@Test
	public void TestThatAssociatedHandInitiallyNull(){
		assertNull(handCheck);
	}
	
	@Test
	public void TestThatHandCanBeSet() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		FakeHand newHand = new FakeHand();
		gameScreen.setHand(newHand);
		
		Field privateHand = Gameboard.class.getDeclaredField("currentHand");
		privateHand.setAccessible(true);
		assertEquals(newHand, (Hand) privateHand.get(gameScreen));
	}
	
	@Test
	public void TestThatUpdateHandImageCallsForCurrentCardAmounts(){
		FakeHand newHand = new FakeHand();
		gameScreen.setHand(newHand);
		gameScreen.updateHandAreaImage();
		assertTrue(this.getNumberOfColorCalled);
	}
	
	private class FakeHand extends Hand{

		public void getNumberOfEachColor(){
			GameboardTester.getNumberOfColorCalled  = true;
		}
	}

}
