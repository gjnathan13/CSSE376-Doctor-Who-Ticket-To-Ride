package doctorWhoGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the non-gui related functionality of Routeboard
 * 
 * @author nathangj on 4/16/15
 *
 */
public class RouteboardTester {

	private Routeboard routeScreen;
	private File routeBackFile;
	private BufferedImage routeBackImage;
	private int imageWidth;
	private int imageHeight;

	/**
	 * Grabs all private fields from a Routeboard instance and assigns them to
	 * fields for use in tests.
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Before
	public void InitializingVariables()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		FakePathComponent mock = new FakePathComponent(null, null);

		this.routeScreen = new Routeboard(mock);

		Field privateRouteBackgroundFile = Routeboard.class.getDeclaredField("routeboardBackgroundFile");

		privateRouteBackgroundFile.setAccessible(true);
		this.routeBackFile = (File) privateRouteBackgroundFile.get(routeScreen);

		Field privateRouteBackgroundImage = Routeboard.class.getDeclaredField("routeboardBackgroundImage");

		privateRouteBackgroundImage.setAccessible(true);
		this.routeBackImage = (BufferedImage) privateRouteBackgroundImage.get(routeScreen);

		this.imageWidth = this.routeBackImage.getWidth();
		this.imageHeight = this.routeBackImage.getHeight();
	}

	/**
	 * Ensures that the Routeboard has an image file for the area to display the
	 * cards in the current hand.
	 */
	@Test
	public void TestRouteboardHasHandAreaImageFile() {
		assertNotNull(routeBackFile);
	}

	/**
	 * Tests that the dimensions of the image associated with Routeboard can be
	 * accessed with a getter.
	 */
	@Test
	public void TestThatImageDimensionsCanBeAccessedWithGetter() {
		int[] testingRouteImageDimensions = this.routeScreen.getRouteImageDimensions();
		assertEquals((int) imageWidth, testingRouteImageDimensions[0]);
		assertEquals((int) imageHeight, testingRouteImageDimensions[1]);
	}

	private class FakePathComponent extends PathComponent {

		public FakePathComponent(Path[] pArray, Gameboard gameboard) {
			super(pArray, gameboard);
		}

	}
}
