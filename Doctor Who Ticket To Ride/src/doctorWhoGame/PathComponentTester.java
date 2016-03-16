package doctorWhoGame;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

public class PathComponentTester {

	@Test
	public void TestPositiveShiftOnPathWithPositiveSlope() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class[] parameters = { Point2D.class, Point2D.class, int.class };
		PathComponent mockPathComponent = EasyMock.createMock(PathComponent.class);
		Method shiftLineMethod = PathComponent.class.getDeclaredMethod("getShiftedLine", parameters);
		shiftLineMethod.setAccessible(true);
		Point2D pointOne = new Point2D.Double(10, 10);
		Point2D pointTwo = new Point2D.Double(12, 13);
		Object[] inputs = { pointOne, pointTwo, 1 };
		Line2D.Double shiftedLine = (Line2D.Double) shiftLineMethod.invoke(mockPathComponent, inputs);
		double x1Expect = 18.320;
		double x2Expect = 20.320;
		double y1Expect = 4.45;
		double y2Expect = 7.45;

		Assert.assertEquals(x1Expect, shiftedLine.getX1(), 0.01);
		Assert.assertEquals(x2Expect, shiftedLine.getX2(), 0.01);
		Assert.assertEquals(y1Expect, shiftedLine.getY1(), 0.01);
		Assert.assertEquals(y2Expect, shiftedLine.getY2(), 0.01);
	}

	@Test
	public void TestPositiveShiftOnPathWithPNegativeSlope() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class[] parameters = { Point2D.class, Point2D.class, int.class };
		PathComponent mockPathComponent = EasyMock.createMock(PathComponent.class);
		Method shiftLineMethod = PathComponent.class.getDeclaredMethod("getShiftedLine", parameters);
		shiftLineMethod.setAccessible(true);
		Point2D pointOne = new Point2D.Double(12, 13);
		Point2D pointTwo = new Point2D.Double(10, 10);
		Object[] inputs = { pointOne, pointTwo, 1 };
		Line2D.Double shiftedLine = (Line2D.Double) shiftLineMethod.invoke(mockPathComponent, inputs);
		double x1Expect = 18.320;
		double x2Expect = 20.320;
		double y1Expect = 4.45;
		double y2Expect = 7.45;

		Assert.assertEquals(x1Expect, shiftedLine.getX1(), 0.01);
		Assert.assertEquals(x2Expect, shiftedLine.getX2(), 0.01);
		Assert.assertEquals(y1Expect, shiftedLine.getY1(), 0.01);
		Assert.assertEquals(y2Expect, shiftedLine.getY2(), 0.01);
	}
}
