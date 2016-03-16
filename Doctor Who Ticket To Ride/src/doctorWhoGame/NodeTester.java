package doctorWhoGame;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Test;

public class NodeTester {

	@Test
	public void testCoordinatesProperlyAssigned()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		int xCoordinate = 10;
		int yCoordinate = 10;
		Node n = new Node(0, xCoordinate, yCoordinate);

		Field privateXCoord = Node.class.getDeclaredField("xCoord");
		Field privateYCoord = Node.class.getDeclaredField("yCoord");

		privateXCoord.setAccessible(true);
		int testXCoord = (int) privateXCoord.get(n);

		privateYCoord.setAccessible(true);
		int testYCoord = (int) privateYCoord.get(n);

		assertEquals(xCoordinate, testXCoord);
		assertEquals(yCoordinate, testYCoord);
	}

}
