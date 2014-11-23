package jte.ui;

import application.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import jte.ui.JTEUI;
import properties_manager.PropertiesManager;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by Aditya on 11/22/2014.
 */
public class JTESplashScreen extends StackPane{

	public JTESplashScreen() {
		try {
			JTEUI ui = JTEUI.getUI();
			// INIT THE SPLASH SCREEN CONTROLS
			PropertiesManager props = PropertiesManager.getPropertiesManager();
			String splashScreenImagePath = props
					.getProperty(Main.JTEPropertyType.SPLASH_SCREEN_IMAGE_NAME);
			props.addProperty(Main.JTEPropertyType.INSETS, "5");
			MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("videos/background.mp4").toURI().toURL().toExternalForm()));
			mediaPlayer.setMute(true);

			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			MediaView mediaView = new MediaView(mediaPlayer);
			this.getChildren().add(mediaView);
			mediaView.setPreserveRatio(false);
			mediaView.setFitWidth(ui.getPaneWidth());
			mediaView.setFitHeight(ui.getPaneHeight());

			mediaPlayer.play();
			VBox buttonList = new VBox();
			buttonList.setAlignment(Pos.TOP_CENTER);
			Image title = ui.loadImage("title.png");
			ImageView titleView = new ImageView(title);
			Button start = new Button("New Game");start.setMinHeight(70);start.setMinWidth(200);
			Button load = new Button("Load Game");load.setMinHeight(70);load.setMinWidth(200);
			Button about = new Button("About");about.setMinHeight(70);about.setMinWidth(200);
			Button exit = new Button("Exit");exit.setMinHeight(70);exit.setMinWidth(200);
			buttonList.setSpacing(10);
			start.getStylesheets().add(new File("data/css/buttons.css").toURI().toURL().toExternalForm());
			load.getStylesheets().add(new File("data/css/buttons.css").toURI().toURL().toExternalForm());
			about.getStylesheets().add(new File("data/css/buttons.css").toURI().toURL().toExternalForm());
			exit.getStylesheets().add(new File("data/css/buttons.css").toURI().toURL().toExternalForm());
			start.setOnAction(e -> ui.switchPane(3));
			about.setOnAction(e -> ui.switchPane(2));
			exit.setOnAction(e -> ui.getEventHandler().respondToExitRequest(ui.getPrimaryStage()));
			buttonList.getChildren().addAll(titleView, start, load, about, exit);
			this.getChildren().add(buttonList);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
