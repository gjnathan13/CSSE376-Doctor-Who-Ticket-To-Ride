package doctorWhoGame;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class PathTester {

	@Test
	public void testColorsReturnedCorrectlyForInputTrainColors() {
		Node n = new Node(0, 0, 0);
		Path pRed = new Path(n, n, TrainColor.Red);
		assertEquals(Color.RED, pRed.getPathColor());

		Path pPink = new Path(n, n, TrainColor.Pink);
		assertEquals(Color.PINK, pRed.getPathColor());
		
		Path pOrange = new Path(n, n, TrainColor.Orange);
		assertEquals(Color.ORANGE, pRed.getPathColor());
		
		Path pBlue = new Path(n, n, TrainColor.Blue);
		assertEquals(Color.BLUE, pRed.getPathColor());
		
		Path pGreen = new Path(n, n, TrainColor.Green);
		assertEquals(Color.GREEN, pRed.getPathColor());
		
		Path pYellow = new Path(n, n, TrainColor.Yellow);
		assertEquals(Color.YELLOW, pRed.getPathColor());
		
		Path pRainbow = new Path(n, n, TrainColor.Rainbow);
		assertEquals(Color.GRAY, pRed.getPathColor());
		
		
	}

}
