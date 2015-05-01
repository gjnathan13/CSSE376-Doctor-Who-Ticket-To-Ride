package doctorWhoGame;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class PathTester {

	@Test
	public void testColorsReturnedCorrectlyForInputTrainColors() {
		Node n = new Node(0, 0, 0);
		Path pRed = new Path(n, n, TrainColor.Red, 0);
		assertEquals(Color.RED, pRed.getPathColor());

		Path pPink = new Path(n, n, TrainColor.Pink, 0);
		assertEquals(Color.PINK, pPink.getPathColor());
		
		Path pOrange = new Path(n, n, TrainColor.Orange, 0);
		assertEquals(Color.ORANGE, pOrange.getPathColor());
		
		Path pBlue = new Path(n, n, TrainColor.Blue, 0);
		assertEquals(Color.BLUE, pBlue.getPathColor());
		
		Path pGreen = new Path(n, n, TrainColor.Green, 0);
		assertEquals(Color.GREEN, pGreen.getPathColor());
		
		Path pYellow = new Path(n, n, TrainColor.Yellow, 0);
		assertEquals(Color.YELLOW, pYellow.getPathColor());
		
		Path pWhite = new Path(n,n, TrainColor.White, 0);
		assertEquals(Color.WHITE, pWhite.getPathColor());
		
		Path pBlack = new Path(n,n, TrainColor.Black, 0);
		assertEquals(Color.BLACK, pBlack.getPathColor());
		
		Path pRainbow = new Path(n, n, TrainColor.Rainbow, 0);
		assertEquals(Color.GRAY, pRainbow.getPathColor());
		
		
	}

}
