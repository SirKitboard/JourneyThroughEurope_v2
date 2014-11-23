package jte.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import jte.ui.JTEUI;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by Aditya on 11/22/2014.
 */
public class JTEAboutScreen {
	private Button home;
	BorderPane about1;
	BorderPane about2;

	public JTEAboutScreen() {
		try {
			JTEUI ui = JTEUI.getUI();
			about1 = new BorderPane();
			about1.setPadding(ui.getMarginlessInsets());
			HBox north = new HBox();
			north.setStyle("-fx-background-color:#202024 url('../../../data/images/black-thread.png') repeat left top;");
			home = new Button("Back");
			home.getStylesheets().add(new File("data/css/buttons.css").toURI().toURL().toExternalForm());
			home.setOnAction(e -> ui.switchPane(0));
			north.getChildren().addAll(home);
			about1.setTop(north);
			north.setMaxHeight(50);
			WebView aboutPane = new WebView();
			WebEngine engine = aboutPane.getEngine();
			engine.load(new File("data/" + "about.html").toURI().toURL().toExternalForm());
			about1.setCenter(aboutPane);
			initAboutScreen2();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void initAboutScreen2() {
		try {
			JTEUI ui = JTEUI.getUI();
			about2 = new BorderPane();
			about2.setPadding(ui.getMarginlessInsets());
			HBox north = new HBox();
			north.setStyle("-fx-background-color:#202024 url('../../../data/images/black-thread.png') repeat left top;");
			home = new Button("Back");
			home.getStylesheets().add(new File("data/css/buttons.css").toURI().toURL().toExternalForm());
			home.setOnAction(e -> ui.switchPane(5));
			north.getChildren().addAll(home);
			about2.setTop(north);
			north.setMaxHeight(50);
			WebView aboutPane = new WebView();
			WebEngine engine = aboutPane.getEngine();
			engine.load(new File("data/" + "about.html").toURI().toURL().toExternalForm());
			about2.setCenter(aboutPane);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public BorderPane getAbout1() {
		return about1;
	}

	public BorderPane getAbout2() {
		return about2;
	}
}
