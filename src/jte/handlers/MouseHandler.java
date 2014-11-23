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
	public void mouseClicked(MouseEvent me) {
		JTEUI ui = JTEUI.getUI();
		ui.mapClicked(me);
	}
	public void mouseDragged(MouseEvent me) {
		drag.add(me);
	}
}
