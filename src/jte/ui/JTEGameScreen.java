package jte.ui;

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
	Player player;
	ImageView board;
	Insets marginlessInsets;
	int selectedQuad;
	MouseHandler mouseHandler;
	double scaleRatio;
	ArrayList<String> names;
	public JTEGameScreen(int humans, int ai,ArrayList<String> names) {
		JTEUI ui = JTEUI.getUI();
		this.names = names;
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
		final ComboBox<String> label = new ComboBox<String>();
		label.getItems().addAll(names);
		label.setValue(names.get(0));
		label.setMinWidth(ui.getPaneWidth() * 0.20);
		label.setMinHeight(30);
		leftBar.getChildren().add(label);
		Pane pane = new Pane();
		pane.setMinWidth(ui.getPaneWidth() * 0.20);

		for(int i=0;i<player.getHand().size();i++) {
			DropShadow ds1 = new DropShadow();
			ds1.setOffsetY(-2.0f);
			ds1.setOffsetX(4.0f);
			ds1.setColor(Color.GREY);
			Image image = ui.loadImage(player.getHand().get(i).toString());
			ImageView imageView = new ImageView(image);
			imageView.setPreserveRatio(true);
			imageView.setX(5);
			imageView.setFitWidth(ui.getPaneWidth() * 0.19);
			imageView.setY(5+(i*ui.getPaneHeight()*0.10));
			pane.getChildren().add(imageView);
			imageView.setEffect(ds1);
		}
		leftBar.setStyle("-fx-background-color: #D1B48C;");
		leftBar.getChildren().add(pane);
		Image image = ui.loadImage("gameplay.jpg");
		scaleRatio = ui.getPaneHeight()/(image.getHeight()/2);
		board = new ImageView(image);
		board.setPreserveRatio(true);
		board.setFitHeight(ui.getPaneHeight()*2);
		board.setOnMouseClicked(e -> mouseHandler.mouseClicked(e));
		board.setOnMousePressed(e -> {
			mouseHandler.mousePressed(e);
		});
		board.setOnMouseDragged(e -> {
			mouseHandler.mouseDragged(e);
		});
		Pane boardPane = new Pane();
		boardPane.getChildren().addAll(board);
		gameScreen.setCenter(boardPane);
		pane.setPadding(marginlessInsets);
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
		mainPane.getChildren().addAll(gameScreen);
		selectedQuad = 1;
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
		try {
			City clicked = gameData.getCity(scaledx, scaledy, selectedQuad);
			System.out.printf("Name : %s\nX Coord : %.2f\nY Coord : %.2f", clicked.getName(), clicked.getX() * scaleRatio, clicked.getY() * scaleRatio);
			ui.getFileLoader().addToHistory(clicked.getName());
			ui.getErrorHandler().processError(clicked.getName(), ui.getPrimaryStage());
			ui.getHistoryScreen().refreshHistory();
		} catch (CityNotFoundException e) {
			System.out.print("No city at given coordinates");
		}
	}

	public BorderPane getMainPane() {
		return mainPane;
	}

	public ImageView getBoard() {
		return board;
	}

}
