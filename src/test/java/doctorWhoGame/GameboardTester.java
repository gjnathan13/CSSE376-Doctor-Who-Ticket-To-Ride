package doctorWhoGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.createMock;

/**
 * Tests the non-GUI related functionality of Gameboard.
 *  
 * @author nathangj
 * 
 */
public class GameboardTester {

	private static boolean getNumberOfColorCalled = false;
	
	private Gameboard gameScreen;
	private File handAreaFile;
	private BufferedImage handAreaImage;
	private Integer imageWidth;
	private Integer imageHeight;
	private Hand handCheck;

	/**
	 * Grabs all private fields from a Gameboard instance and assigns them to
	 * fields for use in tests.
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Before
	public void InitializingVariables()
			throws Exception {
		this.gameScreen = new Gameboard();

		Field privateHandAreaImageFile = Gameboard.class.getDeclaredField("handAreaImageFile");

		privateHandAreaImageFile.setAccessible(true);
		this.handAreaFile = (File) privateHandAreaImageFile.get(gameScreen);

		Field privateHandAreaImage = Gameboard.class.getDeclaredField("handAreaImage");
		privateHandAreaImage.setAccessible(true);
		this.handAreaImage = (BufferedImage) privateHandAreaImage.get(gameScreen);

		Field privateHandImageWidth = Gameboard.class.getDeclaredField("handImageWidth");
		Field privateHandImageHeight = Gameboard.class.getDeclaredField("handImageHeight");

		privateHandImageWidth.setAccessible(true);
		privateHandImageHeight.setAccessible(true);

		this.imageWidth = (Integer) privateHandImageWidth.get(gameScreen);
		this.imageHeight = (Integer) privateHandImageHeight.get(gameScreen);

		Field privateHand = Gameboard.class.getDeclaredField("currentHand");
		privateHand.setAccessible(true);
		this.handCheck = (Hand) privateHand.get(gameScreen);
	}

	/**
	 * Makes sure a Gameboard can be initialized.
	 */
	@Test
	public void TestGameboardExists() {
		assertNotNull(new Gameboard());
	}

	/**
	 * Ensures that the Gameboard has an image file for the area to display the
	 * cards in the current hand.
	 */
	@Test
	public void TestGameboardHasHandAreaImageFile() {
		assertNotNull(handAreaFile);
	}

	/**
	 * Ensures the Gameboard has an actual image for the area to display the
	 * cards in the current hand.
	 */
	@Test
	public void TestGameboardHasHandAreaImage() {
		assertNotNull(handAreaImage);
	}

	/**
	 * Makes sure the height and the width of the hand area image are properly
	 * set.
	 */
	@Test
	public void TestImageHeightAndWidthAssignedProperlyForImage() {
		assertEquals((int) imageWidth, (int) handAreaImage.getWidth());
		assertEquals((int) imageHeight, (int) handAreaImage.getHeight());
	}

	/**
	 * Makes sure you can get the height and width of the hand area image.
	 */
	@Test
	public void TestThatImageHeightAndWidthCanBeProperlyObtainedWithGetter() {
		int[] testingHandImageDimensions = this.gameScreen.getHandImageDimensions();
		assertEquals((int) imageWidth, testingHandImageDimensions[0]);
		assertEquals((int) imageHeight, testingHandImageDimensions[1]);
	}

	/**
	 * Makes sure a gameboard does not start with any current hand.
	 */
	@Test
	public void TestThatAssociatedHandInitiallyNull() {
		assertNull(handCheck);
	}

	/**
	 * Ensures the current hand can be set as necessary.
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Test
	public void TestThatHandCanBeSet()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		FakeHand newHand = new FakeHand();
		gameScreen.setHand(newHand);

		Field privateHand = Gameboard.class.getDeclaredField("currentHand");
		privateHand.setAccessible(true);
		assertEquals(newHand, (Hand) privateHand.get(gameScreen));
	}

	/**
	 * Makes sure that the updateHandAreaImage method makes a call to the
	 * currentHand to get the number of each colored card in its hand.
	 */
	@Test
	public void TestThatUpdateHandImageCallsForCurrentCardAmounts() {
		FakeHand newHand = new FakeHand();
		gameScreen.setHand(newHand);
		gameScreen.updateHandAreaImage();
		assertTrue(getNumberOfColorCalled);
	}

	@SuppressWarnings("serial")
	private class FakeGameboardPurchaser extends Gameboard {

		private Color colorBeingBought;
		private Path purchasePath;
		private boolean purchasing;
		private PathComponent paths;
		private Color[] colorArray = new Color[]{ Color.RED, Color.PINK, Color.ORANGE, Color.YELLOW, Color.GREEN,
				Color.BLUE, Color.WHITE, Color.BLACK, Color.GRAY };

		@Override
		public void setPurchasing(Path p, PathComponent pathComponent) {
			this.colorBeingBought = p.getPathColor();
			this.purchasePath = p;
			this.purchasing = true;
			this.paths = pathComponent;
		}

		@Override
		public void purchaseGraphics(Color c){
			super.setColorArray(this.colorArray);
			super.purchaseGraphics(Color.GRAY);
		}
		
		public Color getColorBeingBought() {
			return colorBeingBought;
		}

		public Path getPurchasePath() {
			return purchasePath;
		}

		public boolean isPurchasing() {
			return purchasing;
		}

		public PathComponent getPaths() {
			return paths;
		}

		int counter1 = 0;
		public int getCounter1() {
			return counter1;
		}

		public int getCounter2() {
			return counter2;
		}

		public int getCounter3() {
			return counter3;
		}

		public int getCounter4() {
			return counter4;
		}

		@Override
		protected void addArrowButtons(int placement) {
			counter1++;
		}
		
		int counter2 = 0;
		@Override

		protected void addPurchaseButton() {
			counter2++;
		}

		int counter3 = 0;
		@Override
		protected void addCancelButton() {
			counter3++;
		}

		int counter4 = 0;
		@Override
		protected void addPurchasingInstructions() {
			counter4++;
		}

	}

	@Test
	public void TestPurchaseGraphics() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		FakeGameboardPurchaser testGameBoard = new FakeGameboardPurchaser();

		Method purchaseGraphics = FakeGameboardPurchaser.class.getDeclaredMethod("purchaseGraphics", Color.class);
		purchaseGraphics.setAccessible(true);
		purchaseGraphics.invoke(testGameBoard, Color.GRAY);

		assertEquals(testGameBoard.getCounter1(), 9);
		assertEquals(testGameBoard.getCounter2(), 1);
		assertEquals(testGameBoard.getCounter3(), 1);
		assertEquals(testGameBoard.getCounter4(), 1);

	}

	@Test
	public void TestSetPurchasing() {
		FakeGameboardPurchaser testGameboard = new FakeGameboardPurchaser();
		Path testPath = createMock(Path.class);
		PathComponent testPathComponent = createMock(PathComponent.class);

		testGameboard.setPurchasing(testPath, testPathComponent);

		assertTrue(testGameboard.getColorBeingBought() == null);
		assertEquals(testGameboard.getPurchasePath(), testPath);
		assertEquals(testGameboard.getPaths(), testPathComponent);
		assertTrue(testGameboard.isPurchasing());
	}

	/**
	 * Mock class of Hand for use in testing.
	 * 
	 * @author nathangj
	 * 
	 */
	private class FakeHand extends Hand {

		@Override
		public ArrayList<Integer> getNumberOfTrainCards() {
			GameboardTester.getNumberOfColorCalled = true;
			return null;
		}
	}
}
