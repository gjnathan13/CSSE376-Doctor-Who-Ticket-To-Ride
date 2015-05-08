package doctorWhoGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class RouteChoosingComponent extends JComponent {

	private Graphics2D pen;
	private ArrayList<RouteCard> routes;
	private RouteCard[] currentRoutesToPick = new RouteCard[3];

	private final int OFFSET_Y = 838;
	private static final int OFFSET_END_X = 400;
	private static final int ROUTE_BACK_HEIGHT = 100;
	private static final int ROUTE_BACK_WIDTH = 250;
	private static final int ROUTE_BACK_OFFSET_Y = 35;
	private static final int ROUTE_SPACING = 100;
	private static final int INITIAL_ROUTE_BACK_OFFSET_X = 250;

	public RouteChoosingComponent(ArrayList<RouteCard> routes) {
		this.routes = routes;
		for (int i = 0; i < currentRoutesToPick.length; i++) {
			currentRoutesToPick[i] = routes.remove(0);
		}
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.pen = (Graphics2D) g;
		this.pen.fillRect(0, OFFSET_Y, this.getWidth() - OFFSET_END_X,
				this.getHeight() - OFFSET_Y);

		JButton endRouteBuy = new JButton("Select Routes");
		endRouteBuy.setBounds(0, OFFSET_Y, 200, 25);
		this.add(endRouteBuy);

		endRouteBuy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.endRouteSelection();
			}

		});

		for (int i = 0; i < this.currentRoutesToPick.length; i++) {
			Rectangle routeCardBack = new Rectangle(INITIAL_ROUTE_BACK_OFFSET_X
					+ ROUTE_BACK_WIDTH * (i) + ROUTE_SPACING * (i), OFFSET_Y
					+ ROUTE_BACK_OFFSET_Y, ROUTE_BACK_WIDTH, ROUTE_BACK_HEIGHT);
			this.pen.setColor(Color.BLACK);
			this.pen.fill(routeCardBack);

			Node[] nodesToLabel = this.currentRoutesToPick[i].getNodes();
			String nodeName1 = nodesToLabel[0].getName();
			String nodeName2 = nodesToLabel[1].getName();
			String nodeAbbrv1 = nodesToLabel[0].getAbbreviation();
			String nodeAbbrv2 = nodesToLabel[1].getAbbreviation();

			String nodeInfo1 = "<html><div style=\"text-align: center;\">"
					+ nodeName1 + "<br>(" + nodeAbbrv1 + ")<br>V<br>"
					+ nodeName2 + "<br>(" + nodeAbbrv2 + ")</html>";

			JLabel node1Label = new JLabel(nodeInfo1, JLabel.CENTER);
			node1Label.setForeground(Color.WHITE);
			node1Label.setBounds((int) routeCardBack.getX(),
					(int) routeCardBack.getY(), (int) routeCardBack.getWidth(),
					(int) routeCardBack.getHeight());
			this.add(node1Label);

			JLabel routeScoreLabel = new JLabel(
					Integer.toString(this.currentRoutesToPick[i].getPoints()));
			routeScoreLabel.setForeground(Color.CYAN);
			routeScoreLabel.setBounds((int) (routeCardBack.getX() + routeCardBack.getWidth()*(7.0/8)) ,
					(int) (routeCardBack.getY() + routeCardBack.getHeight()*(2.0/3)), (int) (routeCardBack.getWidth()*(1.0/8)),
					(int) (routeCardBack.getHeight()*(1.0/3)));
			this.add(routeScoreLabel);
		}
	}

}
