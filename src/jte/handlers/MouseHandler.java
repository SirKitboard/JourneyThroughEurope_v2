package jte.handlers;

import javafx.scene.input.MouseEvent;
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
	public void mouseClicked(MouseEvent me) {
		JTEUI ui = JTEUI.getUI();
		ui.mapClicked(me);
	}
	public void mouseDragged(MouseEvent me) {
		JTEUI ui = JTEUI.getUI();
		System.out.println(me.getX() - distx);
		if(me.getX() - distx > 0);
		else if(me.getX() - distx <  (ui.getPaneWidth()*0.6) - ui.getJteGameScreen().getBoard().getBoundsInParent().getWidth());
		else {
			ui.getJteGameScreen().getBoardI().setLayoutX(me.getX() - distx);
		}

		if(me.getY() - disty > 0);
		else if(me.getY() - disty <  (ui.getPaneHeight()) - ui.getJteGameScreen().getBoard().getBoundsInParent().getHeight());
		else {
			ui.getJteGameScreen().getBoardI().setLayoutY(me.getY() - disty);
		}


	}
	public void mousePressed(MouseEvent me) {
		JTEUI ui = JTEUI.getUI();
		temp1 = me.getX();
		temp2 = me.getY();
		distx = temp1 - ui.getJteGameScreen().getBoardI().getLayoutX();
		disty = temp2 - ui.getJteGameScreen().getBoardI().getLayoutY();
	}
}
