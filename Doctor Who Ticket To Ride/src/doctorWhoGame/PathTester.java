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
		assertEquals(Color.PINK, pPink.getPathColor());
		
		Path pOrange = new Path(n, n, TrainColor.Orange);
		assertEquals(Color.ORANGE, pOrange.getPathColor());
		
		Path pBlue = new Path(n, n, TrainColor.Blue);
		assertEquals(Color.BLUE, pBlue.getPathColor());
		
		Path pGreen = new Path(n, n, TrainColor.Green);
		assertEquals(Color.GREEN, pGreen.getPathColor());
		
		Path pYellow = new Path(n, n, TrainColor.Yellow);
		assertEquals(Color.YELLOW, pYellow.getPathColor());
		
		Path pWhite = new Path(n,n, TrainColor.White);
		assertEquals(Color.WHITE, pWhite.getPathColor());
		
		Path pBlack = new Path(n,n, TrainColor.Black);
		assertEquals(Color.BLACK, pBlack.getPathColor());
		
		Path pRainbow = new Path(n, n, TrainColor.Rainbow);
		assertEquals(Color.GRAY, pRainbow.getPathColor());
		
		
	}

}
