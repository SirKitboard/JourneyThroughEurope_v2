package jte.ui;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import properties_manager.PropertiesManager;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by Aditya on 11/22/2014.
 */
public class JTEHistoryScreen extends BorderPane{
	Button home;
	WebView historyPane;
	public JTEHistoryScreen() {
		JTEUI ui = JTEUI.getUI();
		ui.getFileLoader().createHistoryPage();
		try {
			this.getChildren().removeAll();
			HBox north = new HBox();
			north.setStyle("-fx-background-color:#202024 url('../../../data/images/black-thread.png') repeat left top;");
			home = new Button("Back");
			home.getStylesheets().add(new File("data/css/buttons.css").toURI().toURL().toExternalForm());
			home.setOnAction(e -> ui.switchPane(5));
			north.getChildren().addAll(home);
			this.setTop(north);
			north.setMaxHeight(50);
			historyPane = new WebView();
			WebEngine engine = historyPane.getEngine();
			ScrollPane aboutScrollPane = new ScrollPane();
			engine.load(new File("data/" + "statsHTML.html").toURI().toURL().toExternalForm());
			this.setCenter(historyPane);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void refreshHistory() {
		try {
			WebEngine engine = historyPane.getEngine();
			engine.load(new File("data/" + "statsHTML.html").toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void loadPage(WebEngine jep, String fileName) {
		// GET THE FILE NAME
		PropertiesManager props = PropertiesManager.getPropertiesManager();
		System.out.println(fileName);
		try {
			jep.load(new File("data/"+fileName).toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
