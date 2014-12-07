package jte.ui;

import application.Main;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import jte.file.JTEFileLoader;
import jte.game.City;
import jte.game.JTEGameStateManager;
import jte.game.Player;
import properties_manager.PropertiesManager;
import jte.handlers.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class JTEUI extends Pane {

	/**
	 * The JTEUIState represents the four screen states that are possible
	 * for the JTE game application. Depending on which state is in current
	 * use, different controls will be visible.
	 */
	public enum JTEUIState {

		SPLASH_SCREEN_STATE, PLAY_GAME_STATE, VIEW_STATS_STATE, VIEW_HELP_STATE,
		HANG1_STATE, HANG2_STATE, HANG3_STATE, HANG4_STATE, HANG5_STATE, HANG6_STATE,
	}

	private static JTEUI ui = new JTEUI();

	// mainStage
	private Stage primaryStage;

	// mainPane
	private BorderPane mainPane;

	// aboutScreen
	JTEAboutScreen aboutScreen;

	// SplashScreen
	private JTESplashScreen splashScreen;

	// PLayerSelectScreen
	JTEPlayerSelectScreen playerSelectScreen;

	//StatsPane
	JTEHistoryScreen historyScreen;

	// Padding
	private Insets marginlessInsets;

	// Image path
	private String ImgPath = "file:images/";

	// mainPane weight && height
	private int paneWidth;
	private int paneHeight;

	// THIS CLASS WILL HANDLE ALL ACTION EVENTS FOR THIS PROGRAM
	private JTEEventHandler eventHandler;
	private JTEErrorHandler errorHandler;
	private JTEFileLoader fileLoader;

	JTEGameStateManager gsm;
	JTEGameScreen jteGameScreen;

	public static JTEUI getUI() {
		if(ui == null) {
			ui = new JTEUI();
		}
		return ui;
	}

	public void initUI() {
		marginlessInsets = new Insets(5, 5, 5, 5);
		gsm = new JTEGameStateManager();
		eventHandler = new JTEEventHandler();
		errorHandler = new JTEErrorHandler(primaryStage);
		fileLoader = new JTEFileLoader();
		initMainPane();
		splashScreen = new JTESplashScreen();
		aboutScreen = new JTEAboutScreen();
		playerSelectScreen = new JTEPlayerSelectScreen();
		switchPane(0);
	}

	public void SetStage(Stage stage) {
		primaryStage = stage;
	}

	public BorderPane getMainPane() {
		return this.mainPane;
	}

	public JTEGameScreen getJteGameScreen() {
		return jteGameScreen;
	}

	public void setJteGameScreen(JTEGameScreen jteGameScreen) {
		this.jteGameScreen = jteGameScreen;
	}

	public JTEHistoryScreen getHistoryScreen() {
		return historyScreen;
	}

	public void setHistoryScreen(JTEHistoryScreen historyScreen) {
		this.historyScreen = historyScreen;
	}

	public JTEPlayerSelectScreen getPlayerSelectScreen() {
		return playerSelectScreen;
	}

	public void setPlayerSelectScreen(JTEPlayerSelectScreen playerSelectScreen) {
		this.playerSelectScreen = playerSelectScreen;
	}

	public JTEAboutScreen getAboutScreen() {
		return aboutScreen;
	}

	public void setAboutScreen(JTEAboutScreen aboutScreen) {
		this.aboutScreen = aboutScreen;
	}

	public JTESplashScreen getSplashScreen() {
		return splashScreen;
	}

	public void setSplashScreen(JTESplashScreen splashScreen) {
		this.splashScreen = splashScreen;
	}

	public Insets getMarginlessInsets() {
		return marginlessInsets;
	}

	public JTEGameStateManager getGSM() {
		return gsm;
	}

	public JTEFileLoader getFileLoader() {
		return fileLoader;
	}

	public JTEErrorHandler getErrorHandler() {
		return errorHandler;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public JTEEventHandler getEventHandler() { return eventHandler; }

	public void initMainPane() {
		mainPane = new BorderPane();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		PropertiesManager props = PropertiesManager.getPropertiesManager();
		paneWidth = (int)screen.getWidth();
		paneHeight = (int)screen.getHeight();
		mainPane.resize(paneWidth, paneHeight);
		mainPane.setStyle("-fx-background-color: #D1B48C;");
	}

	public int getPaneWidth() {
		return paneWidth;
	}

	public int getPaneHeight() {
		return paneHeight;
	}

	public void startGame(int no, int numCards) {
		int humans=0;
		int ai=0;
		for(int i=0;i<no;i++) {
			if(playerSelectScreen.getRadios().get(i).isSelected()) {
				humans++;
			}
			else {
				ai++;
			}
		}
		ArrayList<String> names = playerSelectScreen.getNames(humans+ai);
		eventHandler.respondToNewGameRequest(humans, ai, numCards,names);
		jteGameScreen = new JTEGameScreen(humans,ai);
		switchPane(5);
		historyScreen = new JTEHistoryScreen();
	}

	public void loadGame() {
		try {
			int temp[] = new int[3];
			ArrayList<Player> players = getFileLoader().loadGame(temp);
			int humans = temp[0];
			int ai = temp[1];
			int activeplayer = temp[2];
			gsm.loadGame(humans,ai,players);
			jteGameScreen = new JTEGameScreen(humans, ai,gsm.getGameInProgress(),activeplayer);
			switchPane(5);
			historyScreen = new JTEHistoryScreen();
		} catch (IOException e) {
			ui.getErrorHandler().processError(Main.JTEPropertyType.ERROR_INVALID_FILE, ui.getPrimaryStage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			ui.getErrorHandler().processError("Invalid FIle", ui.getPrimaryStage());
			e.printStackTrace();
		}

	}


	public void mapClicked(City me) {
		jteGameScreen.mapClicked(me);
	}


	public Image loadImage(String imageName) {
		Image img = new Image(ImgPath + imageName);
		return img;
	}

	public void switchPane(int num) {
		if(num==0) {
			mainPane.setCenter(splashScreen);
		}
		else if(num==1) {

		}
		else if (num==2) {
			mainPane.setCenter(aboutScreen.getAbout1());
		}
		else if(num==3) {
			mainPane.setCenter(playerSelectScreen);
		}
		else if (num==4) {
			mainPane.setCenter(aboutScreen.getAbout2());
		}
		else if (num==5) {
			mainPane.setCenter(jteGameScreen.getMainPane());
		}
		else if(num==6) {
			mainPane.setCenter(historyScreen);
		}
	}
}
