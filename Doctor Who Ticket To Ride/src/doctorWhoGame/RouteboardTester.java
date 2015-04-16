package doctorWhoGame;

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

	}

	
	/**
	 * Makes sure a Routeboard can be initialized.
	 */
	@Test
	public void TestRouteboardExists() {
		assertNotNull(new Routeboard());
	}
	
	/**
	 * Ensures that the Gameboard has an image file for the area to display the
	 * cards in the current hand.
	 */
	@Test
	public void TestRouteboardHasHandAreaImageFile() {
		assertNotNull(routeBackFile);

	}
}

