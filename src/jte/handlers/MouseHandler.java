package jte.handlers;

import javafx.scene.image.ImageView;
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
	double temp1, temp2, distx, disty, origx, origy;
	boolean dragEnabled = true;
	City origin, clicked;
	public void mouseReleased(MouseEvent me) {
		JTEUI ui = JTEUI.getUI();
		if(dragEnabled) {
			if(origin != null) {
				try {
					double scaleRatio = ui.getJteGameScreen().getScaleRatio();
					clicked = ui.getJteGameScreen().getGameData().getCity(me.getX()/scaleRatio, me.getY()/scaleRatio);
					if (clicked == origin)
                        ui.mapClicked(clicked);
				} catch (CityNotFoundException e) {
					//Do nothing
				}
			}
		}
		else {
			try {
				double scaleRatio = ui.getJteGameScreen().getScaleRatio();
				clicked = ui.getJteGameScreen().getGameData().getCity(me.getX()/scaleRatio, me.getY()/scaleRatio);
				if(origin != null) {
					if (clicked == origin)
						ui.mapClicked(clicked);
					else if (clicked.getLandConnections().contains(origin)) {
						ui.getJteGameScreen().moveNoAnimate(origx,origy,origin, clicked);
					} else if (clicked.getSeaConnections().contains(origin)) {
						ui.getJteGameScreen().movedSeaNoAnimate(origx,origy,origin, clicked);
					}
				}
				dragEnabled = false;
				origin = null;
				clicked = null;
			} catch (CityNotFoundException e) {
				ui.getJteGameScreen().setPlayerPosition(origx,origy);

				origin = null;
				clicked=  null;
			}
		}
	}
	public void mouseDragged(MouseEvent me) {
		JTEUI ui = JTEUI.getUI();
		if(dragEnabled) {
			if (me.getX() - distx > 0);
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

		else{
			ui.getJteGameScreen().setPlayerPosition(me.getX(), me.getY());
		}

	}
	public void mousePressed(MouseEvent me) {
		JTEUI ui = JTEUI.getUI();
		try {
			temp1 = me.getX();
			temp2 = me.getY();
			double scaleRatio = ui.getJteGameScreen().getScaleRatio();
			origin = ui.getJteGameScreen().getGameData().getCity(temp1/scaleRatio, temp2/scaleRatio);
			if(origin.getName().equals(ui.getJteGameScreen().getActive().getPosition().getName())) {
				dragEnabled = false;
				ImageView player = ui.getJteGameScreen().getActivePlayerImage();
				origx = player.getX();
				origy = player.getY();
			}
			else {
				dragEnabled = true;
				distx = temp1 - ui.getJteGameScreen().getBoardI().getLayoutX();
				disty = temp2 - ui.getJteGameScreen().getBoardI().getLayoutY();
			}
		} catch (CityNotFoundException e) {
			dragEnabled = true;
			distx = temp1 - ui.getJteGameScreen().getBoardI().getLayoutX();
			disty = temp2 - ui.getJteGameScreen().getBoardI().getLayoutY();
		}
	}
}
