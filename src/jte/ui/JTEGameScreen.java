package jte.ui;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.util.Duration;
import jte.game.City;
import jte.game.CityNotFoundException;
import jte.game.JTEGameData;
import jte.game.Player;
import jte.handlers.MouseHandler;

import java.util.ArrayList;

/**
 * Created by Aditya on 11/9/2014.
 */
public class JTEGameScreen {
	BorderPane mainPane;
	int humans;
	int ai;
	JTEGameData gameData;
	Button b1;
	Button b2;
	Button b3;
	Button b4;
	ArrayList<Player> player;
	ImageView board;
	Insets marginlessInsets;
	MouseHandler mouseHandler;
	double scaleRatio;
	ArrayList<String> names;
	Pane cardPane;
	ArrayList<ImageView> playerImages;
	Pane boardPane;
	Pane boardI;
	int counter;
	int activePlayer;
	ArrayList<Line> lines;
	ArrayList<Circle> circles;
	public JTEGameScreen(int humans, int ai,ArrayList<String> names) {
		JTEUI ui = JTEUI.getUI();
		this.names = names;
		mainPane = new BorderPane();
		this.humans = humans;
		this.ai = ai;
		gameData = ui.getGSM().getGameInProgress();
		player =gameData.getPlayer();
		mouseHandler = new MouseHandler();
		lines = new ArrayList<>();
		circles = new ArrayList<>();
		b1 = new Button();
		b2 = new Button();
		b3 = new Button();
		b4 = new Button();
		b1.setStyle("-fx-background-color: #666666;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b2.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b3.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b4.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b1.setMinWidth(100);b1.setMaxHeight(100);b1.setMaxWidth(100);b1.setMinHeight(100);
		b2.setMinWidth(100);b2.setMaxHeight(100);b2.setMaxWidth(100);b2.setMinHeight(100);
		b3.setMinWidth(100);b3.setMaxHeight(100);b3.setMaxWidth(100);b3.setMinHeight(100);
		b4.setMinWidth(100);b4.setMaxHeight(100);b4.setMaxWidth(100);b4.setMinHeight(100);
		b1.setOnAction(e -> topLeft());
		b2.setOnAction(e -> topRight());
		b3.setOnAction(e -> bottomLeft());
		b4.setOnAction(e -> bottomRight());
		playerImages = new ArrayList<>();
		playerImages.add(new ImageView(ui.loadImage("piece_black.png")));
		playerImages.add(new ImageView(ui.loadImage("piece_blue.png")));
		playerImages.add(new ImageView(ui.loadImage("piece_green.png")));
		playerImages.add(new ImageView(ui.loadImage("piece_red.png")));
		playerImages.add(new ImageView(ui.loadImage("piece_white.png")));
		playerImages.add(new ImageView(ui.loadImage("piece_yellow.png")));
		initGameScreen();
	}
	public void initGameScreen() {
		JTEUI ui = JTEUI.getUI();
		marginlessInsets = new Insets(5, 5, 5, 5);
		mainPane.getChildren().clear();
		BorderPane gameScreen = new BorderPane();
		VBox leftBar = new VBox();
		final ComboBox<String> label = new ComboBox<String>();
		label.getItems().addAll(names);
		label.setValue(names.get(0));
		label.setMinWidth(ui.getPaneWidth() * 0.20);
		label.setMinHeight(30);
		leftBar.getChildren().add(label);
		cardPane = new Pane();
		cardPane.setMinWidth(ui.getPaneWidth() * 0.20);
		label.valueProperty().addListener(e -> switchCards(names.indexOf(label.getValue())));
		leftBar.setStyle("-fx-background-color: #D1B48C;");
		leftBar.getChildren().add(cardPane);
		Image image = ui.loadImage("gameplay.jpg");
		boardI = new Pane();
		scaleRatio = ui.getPaneHeight()*3 / image.getHeight();
		board = new ImageView(image);
		board.setPreserveRatio(true);
		board.setFitHeight(ui.getPaneHeight() * 3);
		boardI.setMinHeight(ui.getPaneHeight() * 3);
		boardI.setMinWidth(ui.getPaneWidth() / scaleRatio);
		System.out.println("Image : " + board.getBoundsInParent().getHeight() + " " + board.getBoundsInParent().getWidth());
		System.out.println("Pane : " + boardI.getHeight() + " " + boardI.getWidth());
		boardPane = new Pane();
		boardI.setOnMouseReleased(e -> {
			mouseHandler.mouseReleased(e);
		});
		boardI.setOnMousePressed(e -> {
			mouseHandler.mousePressed(e);
		});
		boardI.setOnMouseDragged(e -> {
			mouseHandler.mouseDragged(e);
		});
		boardI.getChildren().addAll(board);
		boardPane.getChildren().addAll(boardI);
		gameScreen.setCenter(boardPane);
		cardPane.setPadding(marginlessInsets);
		System.out.print(boardPane.getWidth());
		Label turn = new Label("Player 1 Turn");
		turn.setStyle("-fx-font-size: 40px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#FF0000");
		Image icon = ui.loadImage("piece_red.png");
		boardPane.setMinWidth(ui.getPaneWidth() * 0.60);
		ImageView iconView = new ImageView(icon);
		iconView.setPreserveRatio(true);
		iconView.setFitHeight(50);
		HBox playerTurn = new HBox();
		playerTurn.getChildren().addAll(turn, iconView);

		int val = rollDie();
		Label roll = new Label("Rolled " + val);
		roll.setStyle("-fx-font-size: 30px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#FF0000");
		image = ui.loadImage("die_"+val+".jpg");
		ImageView rolImage = new ImageView(image);
		rolImage.setPreserveRatio(true);
		rolImage.setFitWidth(ui.getPaneWidth() * 0.100);
		GridPane nav = new GridPane();
		Pane padder = new Pane();
		Label ac = new Label("A-C");ac.setStyle("-fx-font-size: 20px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#000000");
		Label df = new Label("D-F");df.setStyle("-fx-font-size: 20px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#000000");
		Label p14 = new Label("1-4");p14.setStyle("-fx-font-size: 20px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#000000");
		Label p48 = new Label("5-8");p48.setStyle("-fx-font-size: 20px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#000000");

		nav.add(padder,0,0);nav.add(ac,1,0);nav.add(df,2,0);
		nav.add(p14,0,1);nav.add(b1,1,1);nav.add(b2,2,1);
		nav.add(p48,0,2);nav.add(b3,1,2);nav.add(b4, 2, 2);
		Button about = new Button("About");
		about.setStyle("-fx-font-size: 30px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill: #FF0000;-fx-background-color: #FFFFFF;-fx-border-color: #FF0000;-fx-border-radius: 3px;-fx-border-width: 5px");
		Button history = new Button("Game history");
		history.setStyle("-fx-font-size: 30px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill: #FF0000;-fx-background-color: #FFFFFF;-fx-border-color: #FF0000;-fx-border-radius: 3px;-fx-border-width: 5px");
		about.setOnAction(e -> ui.switchPane(4));
		history.setOnAction(e -> ui.switchPane(6));
		VBox rightPane = new VBox();
		rightPane.setStyle("-fx-background-color: #D1B48C;");
		rightPane.setSpacing(10);
		rightPane.setMinWidth(ui.getPaneWidth() * 0.2);
		rightPane.setMinHeight(ui.getPaneHeight());
		rightPane.setAlignment(Pos.CENTER);
		playerTurn.setAlignment(Pos.CENTER);
		nav.setAlignment(Pos.CENTER);
		rightPane.getChildren().addAll(playerTurn, roll, rolImage, nav, about, history);
		gameScreen.setRight(rightPane);
		gameScreen.setLeft(leftBar);
		initPlayerPositions();
		mainPane.getChildren().addAll(gameScreen);
		switchCards(0);
	}

	public void topLeft() {
		JTEUI ui = JTEUI.getUI();
		b1.setStyle("-fx-background-color: #666666;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b2.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b3.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b4.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
//		Image image = ui.loadImage("gameplay_AC14.jpg");
//		board.setImage(image);
//		board.setPreserveRatio(true);
//		board.setFitHeight(820);
//		selectedQuad =1;
//		scaleRatio = 820/image.getHeight();
	}

	public void topRight() {
		JTEUI ui = JTEUI.getUI();
		b1.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b2.setStyle("-fx-background-color: #666666;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b3.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b4.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
//		Image image = ui.loadImage("gameplay_DF14.jpg");
//		board.setImage(image);
//		board.setPreserveRatio(true);
//		board.setFitHeight(820);
//		selectedQuad = 2;
//		scaleRatio = 820/image.getHeight();
	}

	public void bottomRight() {
		JTEUI ui = JTEUI.getUI();
		b1.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b2.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b3.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b4.setStyle("-fx-background-color: #666666;-fx-border-width:2px;-fx-border-color:  #FF0000");
//		Image image = ui.loadImage("gameplay_DF58.jpg");
//		board.setImage(image);
//		board.setPreserveRatio(true);
//		board.setFitHeight(820);
//		selectedQuad = 4;
//		scaleRatio = 820/image.getHeight();
	}

	public void bottomLeft() {
		JTEUI ui = JTEUI.getUI();
		b1.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b2.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b3.setStyle("-fx-background-color: #666666;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b4.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		Image image = ui.loadImage("gameplay_AC58.jpg");
//		board.setImage(image);
//		board.setPreserveRatio(true);
//		board.setFitHeight(820);
//		selectedQuad = 3;
//		scaleRatio = 820/image.getHeight();
	}

	public int rollDie() {
		return (int)(Math.random()*6)+1;
	}

	public void mapClicked(MouseEvent me) {
		JTEUI ui = JTEUI.getUI();
		int x = (int)me.getX();
		int y = (int)me.getY();
		double scaledx = x/scaleRatio;
		double scaledy = y/scaleRatio;
		double scaledxO = (playerImages.get(activePlayer).getLayoutX()+10)/scaleRatio;
		double scaledyO = (playerImages.get(activePlayer).getLayoutY()+50)/scaleRatio;
		try {
			City clicked = gameData.getCity(scaledx, scaledy);
			City origin = gameData.getCity(scaledxO, scaledyO);
			ui.getErrorHandler().processError(origin.getName(), ui.getPrimaryStage());
			playerMoved(origin,clicked);
		} catch (CityNotFoundException e) {
			//System.out.print("No city at given coordinates");
		}
	}

	public BorderPane getMainPane() {
		return mainPane;
	}

	public ImageView getBoard() {
		return board;
	}

	public Pane getBoardI() {
		return boardI;
	}

	public void switchCards(int playerPos) {
		JTEUI ui = JTEUI.getUI();
		cardPane.getChildren().removeAll();
		activePlayer = playerPos;
		for(counter=0;counter<player.get(playerPos).getHand().size();counter++) {
			DropShadow ds1 = new DropShadow();
			ds1.setOffsetY(-2.0f);
			ds1.setOffsetX(4.0f);
			ds1.setColor(Color.GREY);
			Image image = ui.loadImage(player.get(playerPos).getHand().get(counter).toString());
			ImageView imageView = new ImageView(image);
			imageView.setPreserveRatio(true);
			imageView.setFitWidth(ui.getPaneWidth() * 0.19);
			cardPane.getChildren().add(imageView);
			imageView.setEffect(ds1);
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300),imageView);
			translateTransition.setFromX(ui.getPaneWidth()/2);
			translateTransition.setFromY(ui.getPaneHeight() / 2);
			translateTransition.setToX(5);
			translateTransition.setToY(5 + (counter * ui.getPaneHeight() * 0.10));
			translateTransition.play();
		}
		double scaledxO = (playerImages.get(activePlayer).getLayoutX()+10)/scaleRatio;
		double scaledyO = (playerImages.get(activePlayer).getLayoutY()+50)/scaleRatio;
		try {
			drawLines(gameData.getCity(scaledxO,scaledyO));
		} catch (CityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int getActivePlayer() {
		return activePlayer;
	}

	void initPlayerPositions() {
		JTEUI ui = JTEUI.getUI();
		for(int i=0;i<humans+ai;i++) {
			playerImages.get(i).setPreserveRatio(true);
			playerImages.get(i).setFitHeight(ui.getPaneHeight() * 0.05);
			System.out.println(player.get(i).getHome() + " " + player.get(i).getHome().getActualx() * scaleRatio + " " + player.get(i).getHome().getActualy() * scaleRatio);
			boardI.getChildren().add(playerImages.get(i));
			playerImages.get(i).setLayoutX(player.get(i).getHome().getActualx() * scaleRatio - 10 - playerImages.get(i).getBoundsInParent().getMinX());
			playerImages.get(i).setLayoutY(player.get(i).getHome().getActualy() * scaleRatio - 50 - playerImages.get(i).getBoundsInParent().getMinY());
			DropShadow ds1 = new DropShadow();
			ds1.setOffsetY(-2.0f);
			ds1.setOffsetX(4.0f);
			ds1.setColor(Color.BLACK);
			playerImages.get(i).setEffect(ds1);
		}
		drawLines(player.get(0).getHome());
	}

	public double getScaleRatio() {
		return scaleRatio;
	}

	public JTEGameData getGameData() {
		return gameData;
	}

	public void drawLines(City city) {
		for(int i=0;i<lines.size();i++) {
			boardI.getChildren().removeAll(lines.get(i), circles.get(i));
		}
		lines = new ArrayList<>();
		circles = new ArrayList<>();
		for(int i=0;i<city.getLandConnections().size();i++) {
			lines.add(LineBuilder.create()
					.startX(city.getActualx() * scaleRatio)
					.startY(city.getActualy() * scaleRatio)
					.endX(city.getLandConnections().get(i).getActualx() * scaleRatio)
					.endY(city.getLandConnections().get(i).getActualy() * scaleRatio)
					.fill(Color.RED)
					.strokeWidth(5f)
					.stroke(Color.RED)
					.build());
			circles.add(CircleBuilder.create()
					.centerX(city.getLandConnections().get(i).getActualx() * scaleRatio)
					.centerY(city.getLandConnections().get(i).getActualy() * scaleRatio)
					.radius(15f)
					.fill(Color.RED)
					.strokeWidth(2f)
					.stroke(Color.BLACK)
					.build());
			boardI.getChildren().addAll(lines.get(i), circles.get(i));
		}
		for(int i=0;i<city.getSeaConnections().size();i++) {
			lines.add(LineBuilder.create()
					.startX(city.getActualx() * scaleRatio)
					.startY(city.getActualy() * scaleRatio)
					.endX(city.getSeaConnections().get(i).getActualx() * scaleRatio)
					.endY(city.getSeaConnections().get(i).getActualy() * scaleRatio)
					.fill(Color.CYAN)
					.strokeWidth(5f)
					.stroke(Color.CYAN)
					.build());
			circles.add(CircleBuilder.create()
					.centerX(city.getSeaConnections().get(i).getActualx() * scaleRatio)
					.centerY(city.getSeaConnections().get(i).getActualy() * scaleRatio)
					.radius(15f)
					.fill(Color.CYAN)
					.strokeWidth(2f)
					.stroke(Color.BLACK)
					.build());
			boardI.getChildren().addAll(lines.get(i + city.getLandConnections().size()), circles.get(i + city.getLandConnections().size()));
		}
	}

	public void playerMoved(City origin, City clicked) {
		JTEUI ui = JTEUI.getUI();
		if(origin.getLandConnections().contains(clicked)) {
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000),playerImages.get(activePlayer));
			translateTransition.setByX((clicked.getActualx() - origin.getActualx()) * scaleRatio);
			translateTransition.setByY((clicked.getActualy() - origin.getActualy()) * scaleRatio);
			translateTransition.play();
			translateTransition.setOnFinished(e -> {
				translateTransition.stop();
				TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(1),playerImages.get(activePlayer));
				translateTransition1.setByX((origin.getActualx() - clicked.getActualx()) * scaleRatio);
				translateTransition1.setByY((origin.getActualy() - clicked.getActualy()) * scaleRatio);
				translateTransition1.play();
				playerImages.get(activePlayer).setLayoutX(playerImages.get(activePlayer).getLayoutX() + (clicked.getActualx() - origin.getActualx()) * scaleRatio);
				playerImages.get(activePlayer).setLayoutY(playerImages.get(activePlayer).getLayoutY() + (clicked.getActualy() - origin.getActualy()) * scaleRatio);
				drawLines(clicked);
			});
		}

		ui.getFileLoader().addToHistory(clicked.getName());
		ui.getHistoryScreen().refreshHistory();
	}
}
