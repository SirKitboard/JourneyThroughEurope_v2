package jte.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import jte.game.City;
import jte.game.CityNotFoundException;
import jte.game.JTEGameData;
import jte.game.Player;
import jte.handlers.MouseHandler;

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
	Player player;
	ImageView board;
	Insets marginlessInsets;
	int selectedQuad;
	MouseHandler mouseHandler;
	double scaleRatio;
	public JTEGameScreen(int humans, int ai) {
		JTEUI ui = JTEUI.getUI();
		mainPane = new BorderPane();
		this.humans = humans;
		this.ai = ai;
		gameData = ui.getGSM().getGameInProgress();
		player =gameData.getPlayer();
		for(int i=0;i<3;i++) {
			player.getHand().remove(0);
		}
		mouseHandler = new MouseHandler();
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
		initGameScreen();
	}

	public void initGameScreen() {
		JTEUI ui = JTEUI.getUI();
		marginlessInsets = new Insets(5, 5, 5, 5);
		mainPane.getChildren().clear();
		BorderPane gameScreen = new BorderPane();
		VBox leftBar = new VBox();

		Button label = new Button("Player 1");
		label.setMinWidth(300);
		label.setMinHeight(30);
		leftBar.getChildren().add(label);
		Pane pane = new Pane();
		pane.setMinHeight(700);
		pane.setMinWidth(300);

		for(int i=0;i<player.getHand().size();i++) {
			DropShadow ds1 = new DropShadow();
			ds1.setOffsetY(-2.0f);
			ds1.setOffsetX(4.0f);
			ds1.setColor(Color.GREY);
			Image image = ui.loadImage(player.getHand().get(i).toString());
			ImageView imageView = new ImageView(image);
			imageView.setPreserveRatio(true);
			imageView.setX(5);
			imageView.setFitWidth(290);
			imageView.setY(5+(i*100));
			pane.getChildren().add(imageView);
			imageView.setEffect(ds1);
		}
		leftBar.getChildren().add(pane);
		Image image = ui.loadImage("gameplay_AC14.jpg");
		scaleRatio = 820/image.getHeight();
		board = new ImageView(image);
		board.setPreserveRatio(true);
		board.setFitHeight(820);
		board.setOnMouseClicked(e -> mouseHandler.mouseClicked(e));
		Pane boardPane = new Pane();
		gameScreen.setLeft(leftBar);
		boardPane.getChildren().addAll(board);
		gameScreen.setCenter(boardPane);
		pane.setPadding(marginlessInsets);
		System.out.print(boardPane.getWidth());
		Label turn = new Label("Player 1 Turn");
		turn.setStyle("-fx-font-size: 40px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#FF0000");
		Image icon = ui.loadImage("piece_red.png");
		boardPane.setMinWidth(660);
		ImageView iconView = new ImageView(icon);
		iconView.setPreserveRatio(true);
		iconView.setFitHeight(50);
		HBox playerTurn = new HBox();
		playerTurn.getChildren().addAll(turn, iconView);
		VBox rightPane = new VBox();
		gameScreen.setRight(rightPane);
		int val = rollDie();
		Label roll = new Label("Rolled " + val);
		roll.setStyle("-fx-font-size: 30px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#FF0000");
		image = ui.loadImage("die_"+val+".jpg");
		ImageView rolImage = new ImageView(image);
		rolImage.setPreserveRatio(true);
		rolImage.setFitWidth(200);
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
		rightPane.setSpacing(10);
		rightPane.getChildren().addAll(playerTurn, roll, rolImage, nav, about, history);
		mainPane.getChildren().addAll(gameScreen);
		selectedQuad = 1;
	}

	public void topLeft() {
		JTEUI ui = JTEUI.getUI();
		b1.setStyle("-fx-background-color: #666666;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b2.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b3.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b4.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		Image image = ui.loadImage("gameplay_AC14.jpg");
		board.setImage(image);
		board.setPreserveRatio(true);
		board.setFitHeight(820);
		selectedQuad =1;
		scaleRatio = 820/image.getHeight();
	}

	public void topRight() {
		JTEUI ui = JTEUI.getUI();
		b1.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b2.setStyle("-fx-background-color: #666666;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b3.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b4.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		Image image = ui.loadImage("gameplay_DF14.jpg");
		board.setImage(image);
		board.setPreserveRatio(true);
		board.setFitHeight(820);
		selectedQuad = 2;
		scaleRatio = 820/image.getHeight();
	}

	public void bottomRight() {
		JTEUI ui = JTEUI.getUI();
		b1.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b2.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b3.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b4.setStyle("-fx-background-color: #666666;-fx-border-width:2px;-fx-border-color:  #FF0000");
		Image image = ui.loadImage("gameplay_DF58.jpg");
		board.setImage(image);
		board.setPreserveRatio(true);
		board.setFitHeight(820);
		selectedQuad = 4;
		scaleRatio = 820/image.getHeight();
	}

	public void bottomLeft() {
		JTEUI ui = JTEUI.getUI();
		b1.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b2.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b3.setStyle("-fx-background-color: #666666;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b4.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		Image image = ui.loadImage("gameplay_AC58.jpg");
		board.setImage(image);
		board.setPreserveRatio(true);
		board.setFitHeight(820);
		selectedQuad = 3;
		scaleRatio = 820/image.getHeight();
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
		try {
			City clicked = gameData.getCity(scaledx, scaledy, selectedQuad);
			System.out.printf("Name : %s\nX Coord : %.2f\nY Coord : %.2f", clicked.getName(), clicked.getX() * scaleRatio, clicked.getY() * scaleRatio);
			ui.getFileLoader().addToHistory(clicked.getName());
			ui.getErrorHandler().processError(clicked.getName());
			ui.refreshHistory();
		} catch (CityNotFoundException e) {
			System.out.print("No city at given coordinates");
		}
	}

	public BorderPane getMainPane() {
		return mainPane;
	}

}
