package jte.handlers;

import javafx.scene.input.MouseEvent;
import jte.game.City;
import jte.game.CityNotFoundException;
import jte.ui.JTEUI;

import java.util.ArrayList;

/**
 * Created by Aditya Balwani on 10/9/2014.
 * SBUID : 109353920
 */
public class MouseHandler {
	ArrayList<MouseEvent> drag = new ArrayList<>();
	boolean enabled;
	double temp1, temp2, distx, disty;
	boolean dragEnabled = true;
	City origin, clicked;
	public void mouseReleased(MouseEvent me) {
		JTEUI ui = JTEUI.getUI();
		if(dragEnabled);
		else {
			try {
				double scaleRatio = ui.getJteGameScreen().getScaleRatio();
				clicked = ui.getJteGameScreen().getGameData().getCity(me.getX()/scaleRatio, me.getY()/scaleRatio);
				if(origin != null) {
					if (clicked == origin)
						ui.mapClicked(clicked);
					else if (clicked.getLandConnections().contains(origin)) {
						ui.getJteGameScreen().playerMoved(origin, clicked);
					} else if (clicked.getSeaConnections().contains(origin)) {
						ui.getJteGameScreen().playerMovedSea(origin, clicked);
					}
				}
				dragEnabled = false;
				origin = null;
				clicked = null;
			} catch (CityNotFoundException e) {
				e.printStackTrace();
				origin = null;
				clicked=  null;
			}
		}
	}
	public void mouseDragged(MouseEvent me) {
		if(dragEnabled) {
			JTEUI ui = JTEUI.getUI();
			if (me.getX() - distx > 0) ;
			else if (me.getX() - distx < (ui.getPaneWidth() * 0.6) - ui.getJteGameScreen().getBoard().getBoundsInParent().getWidth());
			else {
				ui.getJteGameScreen().getBoardI().setLayoutX(me.getX() - distx);
			}

			if (me.getY() - disty > 0) ;
			else if (me.getY() - disty < (ui.getPaneHeight()) - ui.getJteGameScreen().getBoard().getBoundsInParent().getHeight());
			else {
				ui.getJteGameScreen().getBoardI().setLayoutY(me.getY() - disty);
			}
		}

	}
	public void mousePressed(MouseEvent me) {
		JTEUI ui = JTEUI.getUI();
		try {
			temp1 = me.getX();
			temp2 = me.getY();
			double scaleRatio = ui.getJteGameScreen().getScaleRatio();
			origin = ui.getJteGameScreen().getGameData().getCity(temp1/scaleRatio, temp2/scaleRatio);
			dragEnabled = false;
		} catch (CityNotFoundException e) {
			dragEnabled = true;
			distx = temp1 - ui.getJteGameScreen().getBoardI().getLayoutX();
			disty = temp2 - ui.getJteGameScreen().getBoardI().getLayoutY();
		}
	}
}
