package doctorWhoGame;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Test the non-gui related functionality of Routeboard
 * 
 * @author nathangj on 4/16/15
 *
 */
public class RouteboardTester {
	
	/**
	 * Makes sure a Routeboard can be initialized.
	 */
	@Test
	public void TestGameboardExists() {
		assertNotNull(new Routeboard());
	}
}
