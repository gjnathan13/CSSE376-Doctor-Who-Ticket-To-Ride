package doctorWhoGame;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.easymock.EasyMock;
import org.junit.Test;

public class PathComponentTester {

	@Test
	public void testPathComponentConstructor() {
		Node n1 = new Node(1, 2, 5);
		Node n2 = new Node(2, 5, 2);
		Node n3 = new Node(3, 22, 5);
		Node n4 = new Node(4, 5, 22);
		Path p1 = new Path(n1, n2, Color.RED, 5, 3);
		Path p2 = new Path(n3, n4, Color.RED, 5, 0);
		Path[] pathArray = { p1, p2 };
		Node[] nodeArray = { n1, n2, n3, n4 };
		Gameboard gb = new Gameboard();
		PathComponent pathComponent = new PathComponent(pathArray, nodeArray, gb);
		Line2D.Double line = new Line2D.Double(p1.getNodes()[0].getNodePoint(), p1.getNodes()[1].getNodePoint());
		assertFalse(line.equals(p1.getLine()));
		line = new Line2D.Double(p2.getNodes()[0].getNodePoint(), p2.getNodes()[1].getNodePoint());
		assertEquals(line.getP1(), p2.getLine().getP1());
		assertEquals(line.getP2(), p2.getLine().getP2());
	}

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

		assertEquals(x1Expect, shiftedLine.getX1(), 0.01);
		assertEquals(x2Expect, shiftedLine.getX2(), 0.01);
		assertEquals(y1Expect, shiftedLine.getY1(), 0.01);
		assertEquals(y2Expect, shiftedLine.getY2(), 0.01);
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

		assertEquals(x1Expect, shiftedLine.getX1(), 0.01);
		assertEquals(x2Expect, shiftedLine.getX2(), 0.01);
		assertEquals(y1Expect, shiftedLine.getY1(), 0.01);
		assertEquals(y2Expect, shiftedLine.getY2(), 0.01);
	}
}
