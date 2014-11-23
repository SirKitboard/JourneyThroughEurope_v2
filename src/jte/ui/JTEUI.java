package jte.ui;

import application.Main;
import application.Main.JTEPropertyType;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import jte.file.JTEFileLoader;
import jte.game.JTEGameStateManager;
import properties_manager.PropertiesManager;
import jte.handlers.*;
import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Stack;

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

	private static JTEUI ui;

	// mainStage
	private Stage primaryStage;

	// mainPane
	private BorderPane mainPane;
	private BorderPane hmPane;
	private int xOffset;
	private int yOffset;
	// SplashScreen
	private ImageView splashScreenImageView;
	private StackPane splashScreenPane;
	private Label splashScreenImageLabel;
	private FlowPane levelSelectionPane;
	private ArrayList<Button> levelButtons;

	// NorthToolBar
	private HBox northToolbar;
	private Button backButton;
	private Button statsButton;
	private Button undoButton;
	private Button timeButton;
	Button home;
	// GamePane
	private Label SokobanLabel;
	private BorderPane gamePanel = new BorderPane();
	private BorderPane statsPanel = new BorderPane();

	//StatsPane
	private ScrollPane statsScrollPane;
	private WebView statsPane;
	WebView historyPane;
	//HelpPane
	private BorderPane helpPanel;
	private ScrollPane helpScrollPane;
	private JEditorPane helpPane;
	private Button homeButton;
	private Pane workspace;
	private StackPane gameStack;

	// Padding
	private Insets marginlessInsets;

	// Image path
	private String ImgPath = "file:images/";

	// mainPane weight && height
	private int paneWidth;
	private int paneHeigth;

	// THIS CLASS WILL HANDLE ALL ACTION EVENTS FOR THIS PROGRAM
	private JTEEventHandler eventHandler;
	private JTEErrorHandler errorHandler;
	private JTEFileLoader fileLoader;
	BorderPane playerSelect2;
	//Level Data;
	private int numCols;
	private int numRows;
	public double cellWidth;
	public double cellHeight;
	private int[][] levelData;
	private int[] charPosition = new int[2];
	private ArrayList<int[]> boxPositions = new ArrayList<int[]>();
	private ArrayList<int[]> destinations = new ArrayList<int[]>();
	//Game Renderer
	private GraphicsContext gc;
	private Stack<int[]> charMoves;
	private Stack<int[][]> boxPositionsStack;
	//Handlers
	ArrowKeyHandler arrowKeyHandler;
	MouseHandler mouseHandler;
	private Integer level;
	private static final Integer STARTTIMESECONDS = 0;
	private Timeline timeline;
	private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIMESECONDS);

	JTEGameStateManager gsm;
	JTEGameScreen jteGameScreen;

	//Player Selection stuff
	HBox playerSelectNorthBar;
	GridPane playerSelectGridPane;
	ArrayList<BorderPane> players = new ArrayList<BorderPane>();
	ArrayList<ImageView> playerFlags = new ArrayList<ImageView>();
	ArrayList<RadioButton> playerRadio;
	ArrayList<RadioButton> aiRadio;
	BorderPane about = new BorderPane();
	BorderPane about2 = new BorderPane();
	BorderPane history = new BorderPane();
	//AudioFiles
	AudioClip intro;
	AudioClip move;
	AudioClip win;
	AudioClip lose;
	AudioClip bump;
	public static JTEUI getUI() {
		if(ui == null) {
			ui = new JTEUI();
		}
		return ui;
	}
	private JTEUI() {
		gsm = new JTEGameStateManager(this);
		eventHandler = new JTEEventHandler();
		errorHandler = new JTEErrorHandler(primaryStage);
		fileLoader = new JTEFileLoader();
		arrowKeyHandler = new ArrowKeyHandler();
		mouseHandler = new MouseHandler();
		initMainPane();
		initSplashScreen();
		initAboutScreen();
		initPlayerSelectScreen();
		switchPane(0);
	}

	public void SetStage(Stage stage) {
		primaryStage = stage;
	}

	public BorderPane getMainPane() {
		return this.mainPane;
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

	public JEditorPane getHelpPane() {
		return helpPane;
	}

	public void initMainPane() {
		marginlessInsets = new Insets(5, 5, 5, 5);
		mainPane = new BorderPane();

		PropertiesManager props = PropertiesManager.getPropertiesManager();
		paneWidth = Integer.parseInt(props
				.getProperty(Main.JTEPropertyType.WINDOW_WIDTH));
		paneHeigth = Integer.parseInt(props
				.getProperty(Main.JTEPropertyType.WINDOW_HEIGHT));
		mainPane.resize(paneWidth, paneHeigth);
	}

	public void initSplashScreen() {
		try {
			initAboutScreen();
			// INIT THE SPLASH SCREEN CONTROLS
			PropertiesManager props = PropertiesManager.getPropertiesManager();
			String splashScreenImagePath = props
					.getProperty(JTEPropertyType.SPLASH_SCREEN_IMAGE_NAME);
			props.addProperty(JTEPropertyType.INSETS, "5");
			MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("videos/background.mp4").toURI().toURL().toExternalForm()));
			mediaPlayer.setMute(true);
			mainPane.setStyle("-fx-background-color: #D1B48C;");
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			splashScreenPane = new StackPane();
			MediaView mediaView = new MediaView(mediaPlayer);
			splashScreenPane.getChildren().add(mediaView);
			mediaView.setPreserveRatio(false);
			mediaView.setFitWidth(1250);
			mediaView.setFitHeight(820);

			mediaPlayer.play();
			VBox buttonList = new VBox();
			buttonList.setAlignment(Pos.TOP_CENTER);
			Image title = loadImage("title.png");
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
			start.setOnAction(e -> switchPane(3));
			about.setOnAction(e -> switchPane(2));
			exit.setOnAction(e -> eventHandler.respondToExitRequest(primaryStage));
			buttonList.getChildren().addAll(titleView, start, load, about, exit);
			splashScreenPane.getChildren().add(buttonList);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void initPlayerSelectScreen() {
		playerSelect2 = new BorderPane();
		playerSelectNorthBar = new HBox();
		playerSelectGridPane = new GridPane();
		players = new ArrayList<BorderPane>();
		playerFlags = new ArrayList<>();
		Button go = new Button("GO!");
		final ComboBox<Integer> comboBox = new ComboBox<Integer>();
		comboBox.getItems().addAll(2,3,4,5,6);
		comboBox.setValue(2);

		Label noPlayers = new Label("Select Number of Players : ");
		noPlayers.setMinWidth(150);
		playerSelectNorthBar.getChildren().addAll(noPlayers, comboBox, go);
		comboBox.valueProperty().addListener(e -> numberOfPlayers(comboBox.getValue()));
		go.setOnAction(e -> startGame(comboBox.getValue()));
		playerFlags.add(new ImageView(loadImage("flag_black.png")));
		playerFlags.add(new ImageView(loadImage("flag_blue.png")));
		playerFlags.add(new ImageView(loadImage("flag_green.png")));
		playerFlags.add(new ImageView(loadImage("flag_red.png")));
		playerFlags.add(new ImageView(loadImage("flag_white.png")));
		playerFlags.add(new ImageView(loadImage("flag_yellow.png")));
		playerSelectGridPane.setVgap(4);
		playerRadio = new ArrayList<>();
		aiRadio = new ArrayList<>();
		PropertiesManager props = PropertiesManager.getPropertiesManager();
		for(int i=0;i<6;i++) {
			BorderPane playerSelect = new BorderPane();
			playerSelect.setMinHeight(Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_HEIGHT)) / 2.0);
			playerSelect.setMinWidth(Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_WIDTH)) / 3.0);
			playerFlags.get(i).setPreserveRatio(true);
			playerFlags.get(i).setFitWidth(Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_WIDTH)) / 9.0);
			playerSelect.setLeft(playerFlags.get(i));
			final ToggleGroup playerType = new ToggleGroup();
			VBox toggles = new VBox();
			toggles.setMinWidth(Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_WIDTH)) / 9.0);
			VBox name = new VBox();
			name.setMaxWidth(Integer.parseInt(props.getProperty(JTEPropertyType.WINDOW_WIDTH)) / 9.0);
			Label nameL = new Label("Name : ");
			TextField nameF = new TextField();
			name.getChildren().addAll(nameL,nameF);
			RadioButton playerT = new RadioButton("Player");
			RadioButton cpu = new RadioButton("Computer");
			playerRadio.add(playerT);
			aiRadio.add(cpu);
			playerT.setToggleGroup(playerType);
			cpu.setToggleGroup(playerType);
			toggles.getChildren().addAll(playerT, cpu);
			playerSelect.setCenter(toggles);
			playerSelect.setRight(name);
			playerSelect.setStyle("-fx-border-color: #BB8800;\n" +
					"    -fx-padding: 15;\n" +
					"    -fx-spacing: 10;" +
					"-fx-border-width: 2px;");
			playerSelect.setVisible(false);
			players.add(playerSelect);
			playerSelectGridPane.add(players.get(i),i%3,i/ 3);
		}
		numberOfPlayers(comboBox.getValue());
		playerSelect2.setTop(playerSelectNorthBar);
		playerSelect2.setCenter(playerSelectGridPane);

	}
	public void numberOfPlayers(int n) {
		for(int i=0;i<6;i++) {
			players.get(i).setVisible(i<n);
		}
	}

	public void startGame(int no) {
		int humans=0;
		int ai=0;
		for(int i=0;i<no;i++) {
			if(playerRadio.get(i).isSelected()) {
				humans++;
			}
			else {
				ai++;
			}
		}
		eventHandler.respondToNewGameRequest();
		jteGameScreen = new JTEGameScreen(humans,ai);
		switchPane(5);
		initHistory();
	}
	public void initLoadGame() {

	}

	public void initAboutScreen() {
		try {
			about = new BorderPane();
			HBox north = new HBox();
			north.setStyle("-fx-background-color:#202024 url('../../../data/images/black-thread.png') repeat left top;");
			home = new Button("Back");
			home.getStylesheets().add(new File("data/css/buttons.css").toURI().toURL().toExternalForm());
			home.setOnAction(e -> switchPane(0));
			north.getChildren().addAll(home);
			about.setTop(north);
			north.setMaxHeight(50);
			WebView aboutPane = new WebView();
			WebEngine engine = aboutPane.getEngine();
			engine.load(new File("data/" + "about.html").toURI().toURL().toExternalForm());
			about.setCenter(aboutPane);
			initAboutScreen2();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void initAboutScreen2() {
		try {
			about2 = new BorderPane();
			HBox north = new HBox();
			north.setStyle("-fx-background-color:#202024 url('../../../data/images/black-thread.png') repeat left top;");
			home = new Button("Back");
			home.getStylesheets().add(new File("data/css/buttons.css").toURI().toURL().toExternalForm());
			home.setOnAction(e -> switchPane(5));
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

	public void initHistory() {
		fileLoader.createHistoryPage();
		try {
			history = new BorderPane();
			history.getChildren().removeAll();
			HBox north = new HBox();
			north.setStyle("-fx-background-color:#202024 url('../../../data/images/black-thread.png') repeat left top;");
			home = new Button("Back");
			home.getStylesheets().add(new File("data/css/buttons.css").toURI().toURL().toExternalForm());
			home.setOnAction(e -> switchPane(5));
			north.getChildren().addAll(home);
			history.setTop(north);
			north.setMaxHeight(50);
			historyPane = new WebView();
			WebEngine engine = historyPane.getEngine();
			ScrollPane aboutScrollPane = new ScrollPane();
			engine.load(new File("data/" + "statsHTML.html").toURI().toURL().toExternalForm());
			history.setCenter(historyPane);
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

	/**
	 * This method initializes the language-specific game controls, which
	 * includes the three primary game screens.
	 */


	/**
	 * This function initializes all the controls that go in the north toolbar.
	 */


	/**
	 * This method helps to initialize buttons for a simple toolbar.
	 *
	 * @param toolbar The toolbar for which to add the button.
	 * @param prop    The property for the button we are building. This will
	 *                dictate which image to use for the button.
	 * @return A constructed button initialized and added to the toolbar.
	 */
	private Button initToolbarButton(HBox toolbar, Main.JTEPropertyType prop) {
		// GET THE NAME OF THE IMAGE, WE DO THIS BECAUSE THE
		// IMAGES WILL BE NAMED DIFFERENT THINGS FOR DIFFERENT LANGUAGES
		PropertiesManager props = PropertiesManager.getPropertiesManager();
		String imageName = props.getProperty(prop);

		// LOAD THE IMAGE
		Image image = loadImage(imageName);
		ImageView imageIcon = new ImageView(image);

		// MAKE THE BUTTON
		Button button = new Button();
		button.setGraphic(imageIcon);
		button.setPadding(marginlessInsets);

		// PUT IT IN THE TOOLBAR
		toolbar.getChildren().add(button);

		// AND SEND BACK THE BUTTON
		return button;
	}

	/**
	 * The workspace is a panel that will show different screens depending on
	 * the user's requests.
	 */


	public void mapClicked(MouseEvent me) {
		jteGameScreen.mapClicked(me);
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

	public Image loadImage(String imageName) {
		Image img = new Image(ImgPath + imageName);
		return img;
	}

	public void switchPane(int num) {
		if(num==0) {
			mainPane.setCenter(splashScreenPane);
		}
		else if(num==1) {

		}
		else if (num==2) {
			mainPane.setCenter(about);
		}
		else if(num==3) {
			mainPane.setCenter(playerSelect2);
		}
		else if (num==4) {
			mainPane.setCenter(about2);
		}
		else if (num==5) {
			mainPane.setCenter(jteGameScreen.getMainPane());
		}
		else if(num==6) {
			mainPane.setCenter(history);
		}
	}
}
