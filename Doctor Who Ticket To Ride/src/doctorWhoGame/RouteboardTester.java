package doctorWhoGame;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

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
	public void InitializingVariables() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		this.routeScreen = new Routeboard();

		Field privateRouteBack = Routeboard.class
				.getDeclaredField("routeBackFile");

		privateRouteBack.setAccessible(true);
		this.routeBackFile = (File) privateRouteBack.get(routeScreen);

		Field privateRouteBackImage = Routeboard.class
				.getDeclaredField("routeBackImage");

		privateRouteBackImage.setAccessible(true);
		this.routeBackImage = (BufferedImage) privateRouteBackImage
				.get(routeScreen);

		this.imageWidth = this.routeBackImage.getWidth();
		this.imageHeight = this.routeBackImage.getHeight();
	}

	/**
	 * Makes sure a Routeboard can be initialized.
	 */
	@Test
	public void TestRouteboardExists() {
		assertNotNull(new Routeboard());
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
	 * Ensures the Routeboard has an actual image for displaying routes on.
	 */
	@Test
	public void TestRouteboardHasHandAreaImage() {
		assertNotNull(routeBackImage);
	}

	/**
	 * Tests that Routeboard has a component on it
	 */
	@Test
	public void TestRouteboardHasComponent() {
		Component[] componentList = routeScreen.getComponents();
		assertTrue(componentList.length > 0);
	}

	/**
	 * Tests that Routeboard has a component on it with the correct image.
	 */
	@Test
	public void TestRouteboardComponentIsJLabelWithRouteImage() {
		Component[] componentList = routeScreen.getComponents();
		assertTrue(componentList[0].getClass().equals(JLabel.class));
		ImageIcon testIcon = new ImageIcon(routeBackImage);

		JLabel routeComponent = (JLabel) componentList[0];
		ImageIcon routeImageIcon = (ImageIcon) routeComponent.getIcon();

		assertEquals(testIcon.getImage(), routeImageIcon.getImage());
	}

	/**
	 * Tests that the dimensions of the image associated with Routeboard can be
	 * accessed with a getter.
	 */
	@Test
	public void TestThatImageDimensionsCanBeAccessedWithGetter() {
		int[] testingRouteImageDimensions = this.routeScreen
				.getRouteImageDimensions();
		assertEquals((int) imageWidth, testingRouteImageDimensions[0]);
		assertEquals((int) imageHeight, testingRouteImageDimensions[1]);
	}
}
