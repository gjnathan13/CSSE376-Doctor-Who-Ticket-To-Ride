package doctorWhoGame;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Test;

public class PathTester {

	@Test
	public void testColorsReturnedCorrectlyForInputTrainColors() {
		Node n = new Node(0);
		Node n2 = new Node(1);
		Path pRed = new Path(n, n2, Color.RED, 0);
		assertEquals(Color.RED, pRed.getPathColor());

		Path pPink = new Path(n, n2, Color.PINK, 0);
		assertEquals(Color.PINK, pPink.getPathColor());

		Path pOrange = new Path(n, n2, Color.ORANGE, 0);
		assertEquals(Color.ORANGE, pOrange.getPathColor());

		Path pBlue = new Path(n, n2, Color.BLUE, 0);
		assertEquals(Color.BLUE, pBlue.getPathColor());

		Path pGreen = new Path(n, n2, Color.GREEN, 0);
		assertEquals(Color.GREEN, pGreen.getPathColor());

		Path pYellow = new Path(n, n2, Color.YELLOW, 0);
		assertEquals(Color.YELLOW, pYellow.getPathColor());

		Path pWhite = new Path(n, n2, Color.WHITE, 0);
		assertEquals(Color.WHITE, pWhite.getPathColor());

		Path pBlack = new Path(n, n2, Color.BLACK, 0);
		assertEquals(Color.BLACK, pBlack.getPathColor());

		Path pRainbow = new Path(n, n2, Color.GRAY, 0);
		assertEquals(Color.GRAY, pRainbow.getPathColor());

	}

}
