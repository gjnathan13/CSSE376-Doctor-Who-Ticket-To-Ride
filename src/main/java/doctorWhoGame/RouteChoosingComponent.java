package doctorWhoGame;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//import java.util.ArrayDeque;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class RouteChoosingComponent extends GameComponent {

//	private ArrayDeque<RouteCard> routes;
	private RouteCard[] currentRoutesToPick = new RouteCard[3];
	private Rectangle[] currentRouteRectangles;

	private final int OFFSET_Y = (int)(GameStarter.getHeightModifier() *838);
	private static final int OFFSET_END_X = (int)(GameStarter.getWidthModifier()*400);
	private static final int ROUTE_BACK_HEIGHT = (int)(GameStarter.getHeightModifier() * 100);
	private static final int ROUTE_BACK_WIDTH = (int)(GameStarter.getWidthModifier() * 250);
	private static final int ROUTE_BACK_OFFSET_Y = (int)(GameStarter.getHeightModifier() * 35);
	private static final int ROUTE_SPACING = (int)(GameStarter.getDiagonalModifier() *100);
	private static final int INITIAL_ROUTE_BACK_OFFSET_X = (int)(GameStarter.getWidthModifier() * 250);

	private boolean purchasingRoutes;

	public RouteChoosingComponent() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int mouseX = arg0.getX();
				int mouseY = arg0.getY();
				for (int i = 0; i < currentRoutesToPick.length; i++) {
					if (currentRouteRectangles[i] != null) {
						if (currentRouteRectangles[i].intersects(mouseX - 5, mouseY - 5, 10, 10)) {
							if (currentRoutesToPick != null) {
								if (!currentRoutesToPick[i].getSelected()) {
									currentRoutesToPick[i].setSelected(true);
								} else {
									currentRoutesToPick[i].setSelected(false);

								}
							}
							removeAll();
							revalidate();
							repaint();
						} else {
						}
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});

	}

	@Override
	protected void showGraphics(){
		removeAll();
		this.pen.fillRect(0, OFFSET_Y, this.getWidth() - OFFSET_END_X, this.getHeight() - OFFSET_Y);
		
		String endRouteBuyButtonText = "Select at least one route";
		if(Game.getIsItFirstTurn()){
			endRouteBuyButtonText = "Select at least two routes";
		}

		JButton endRouteBuy = new JButton(endRouteBuyButtonText);
		endRouteBuy.setBounds(0, OFFSET_Y, (int)(GameStarter.getWidthModifier()*200), (int)(GameStarter.getHeightModifier()*25));
		this.add(endRouteBuy);

		endRouteBuy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// size verification
				int numberOfSelectedRoutes = 0;
				for (RouteCard r : currentRoutesToPick) {
					if (r != null) {
						if (r.getSelected()) {
							numberOfSelectedRoutes++;
						}
					}
				}

				boolean correctNumberOfRoutesSelected = false;
				if (Game.getIsItFirstTurn()) {
					if (numberOfSelectedRoutes >= 2) {
						correctNumberOfRoutesSelected = true;
					}
				} else {
					if (numberOfSelectedRoutes >= 1) {
						correctNumberOfRoutesSelected = true;
					}
				}

				if (correctNumberOfRoutesSelected) {
					purchasingRoutes = false;
					ArrayList<RouteCard> selectedCards = new ArrayList<RouteCard>();
					for (RouteCard r : currentRoutesToPick) {
						if (r != null) {
							if (r.getSelected()) {
								selectedCards.add(r);
							} else {
								RouteCardDeck.reinsertRouteCard(r);
							}
						}
					}
					Game.addRouteCardsToHand(selectedCards);
					Game.endRouteSelection();
				}
			}

		});
		// Was purchasing = true, side effects may have affected other things
		if (purchasingRoutes) {
			if (this.currentRoutesToPick[0] == null) {
				for (int i = 0; i < 3; i++) {
					currentRoutesToPick[i] = RouteCardDeck.drawRouteCard();
				}
			}
			if (this.currentRoutesToPick[0] != null) {
				this.currentRouteRectangles = new Rectangle[3];

				for (int i = 0; i < this.currentRoutesToPick.length; i++) {
					if (currentRoutesToPick[i] != null) {
						if (currentRoutesToPick[i].getSelected()) {
							Rectangle routeCardBackHighlight = new Rectangle(
									INITIAL_ROUTE_BACK_OFFSET_X + ROUTE_BACK_WIDTH * (i) + ROUTE_SPACING * (i) - 10,
									OFFSET_Y + ROUTE_BACK_OFFSET_Y - 10, ROUTE_BACK_WIDTH + 20, ROUTE_BACK_HEIGHT + 20);
							this.pen.setColor(Color.CYAN);
							this.pen.fill(routeCardBackHighlight);

						}

						Rectangle routeCardBack = new Rectangle(
								INITIAL_ROUTE_BACK_OFFSET_X + ROUTE_BACK_WIDTH * (i) + ROUTE_SPACING * (i),
								OFFSET_Y + ROUTE_BACK_OFFSET_Y, ROUTE_BACK_WIDTH, ROUTE_BACK_HEIGHT);
						this.currentRouteRectangles[i] = routeCardBack;
						this.pen.setColor(Color.BLACK);
						this.pen.fill(routeCardBack);

						Node[] nodesToLabel = this.currentRoutesToPick[i].getNodes();
						String nodeName1 = nodesToLabel[0].getName();
						String nodeName2 = nodesToLabel[1].getName();
						String nodeAbbrv1 = nodesToLabel[0].getAbbreviation();
						String nodeAbbrv2 = nodesToLabel[1].getAbbreviation();

						String nodeInfo1 = "<html><div style=\"text-align: center;\">" + nodeName1 + "<br>("
								+ nodeAbbrv1 + ")<br>V<br>" + nodeName2 + "<br>(" + nodeAbbrv2 + ")</html>";

						JLabel node1Label = new JLabel(nodeInfo1, JLabel.CENTER);
						node1Label.setForeground(Color.WHITE);
						node1Label.setBounds((int) routeCardBack.getX(), (int) routeCardBack.getY(),
								(int) routeCardBack.getWidth(), (int) routeCardBack.getHeight());
						this.add(node1Label);

						JLabel routeScoreLabel = new JLabel(Integer.toString(this.currentRoutesToPick[i].getPoints()));
						routeScoreLabel.setForeground(Color.CYAN);
						routeScoreLabel.setBounds((int) (routeCardBack.getX() + routeCardBack.getWidth() * (7.0 / 8)),
								(int) (routeCardBack.getY() + routeCardBack.getHeight() * (2.0 / 3)),
								(int) (routeCardBack.getWidth() * (1.0 / 8)),
								(int) (routeCardBack.getHeight() * (1.0 / 3)));
						this.add(routeScoreLabel);
					}
				}
			}
		}
	}

	public void setPurchasing(boolean purchasing) {
		this.purchasingRoutes = purchasing;
		if (purchasing) {
			this.currentRoutesToPick = new RouteCard[3];
		}
	}

}
