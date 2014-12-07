package jte.ui;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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
	Pane cardPane;
	ArrayList<ImageView> playerImages;
	Pane boardPane;
	Pane boardI;
	int counter;
	int activePlayer;
	int rolled;
	int movesLeft;
	ArrayList<Line> lines;
	ArrayList<Circle> circles;
	ComboBox<String> label;
	Label roll;
	ImageView rolImage;
	Label turn;
	Label left;
	boolean airDisplay;
	BorderPane gameScreen;
	public JTEGameScreen(int humans, int ai) {
		JTEUI ui = JTEUI.getUI();
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
		b1.setMinWidth(ui.getPaneWidth()*0.05);b1.setMaxHeight(ui.getPaneWidth()*0.05);b1.setMaxWidth(ui.getPaneWidth()*0.05);b1.setMinHeight(ui.getPaneWidth()*0.05);
		b2.setMinWidth(ui.getPaneWidth()*0.05);b2.setMaxHeight(ui.getPaneWidth()*0.05);b2.setMaxWidth(ui.getPaneWidth()*0.05);b2.setMinHeight(ui.getPaneWidth()*0.05);
		b3.setMinWidth(ui.getPaneWidth()*0.05);b3.setMaxHeight(ui.getPaneWidth()*0.05);b3.setMaxWidth(ui.getPaneWidth()*0.05);b3.setMinHeight(ui.getPaneWidth()*0.05);
		b4.setMinWidth(ui.getPaneWidth()*0.05);b4.setMaxHeight(ui.getPaneWidth()*0.05);b4.setMaxWidth(ui.getPaneWidth()*0.05);b4.setMinHeight(ui.getPaneWidth()*0.05);
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
		initGameScreen(0);
	}

	public JTEGameScreen(int humans, int ai, JTEGameData data,int activePlayer) {
		JTEUI ui = JTEUI.getUI();
		mainPane = new BorderPane();
		this.humans = humans;
		this.ai = ai;
		gameData = data;
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
		b1.setMinWidth(ui.getPaneWidth()*0.05);b1.setMaxHeight(ui.getPaneWidth()*0.05);b1.setMaxWidth(ui.getPaneWidth()*0.05);b1.setMinHeight(ui.getPaneWidth()*0.05);
		b2.setMinWidth(ui.getPaneWidth()*0.05);b2.setMaxHeight(ui.getPaneWidth()*0.05);b2.setMaxWidth(ui.getPaneWidth()*0.05);b2.setMinHeight(ui.getPaneWidth()*0.05);
		b3.setMinWidth(ui.getPaneWidth()*0.05);b3.setMaxHeight(ui.getPaneWidth()*0.05);b3.setMaxWidth(ui.getPaneWidth()*0.05);b3.setMinHeight(ui.getPaneWidth()*0.05);
		b4.setMinWidth(ui.getPaneWidth()*0.05);b4.setMaxHeight(ui.getPaneWidth()*0.05);b4.setMaxWidth(ui.getPaneWidth()*0.05);b4.setMinHeight(ui.getPaneWidth()*0.05);
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
		initGameScreen(activePlayer);
	}

	public void initGameScreen(int activePlayer) {
		JTEUI ui = JTEUI.getUI();
		marginlessInsets = new Insets(5, 5, 5, 5);
		mainPane.getChildren().clear();
		gameScreen = new BorderPane();
		VBox leftBar = new VBox();
		label = new ComboBox<String>();
		for(int i=0;i<humans+ai;i++) {
			label.getItems().addAll(player.get(i).getName());
		}
		label.setDisable(true);
		label.setValue(player.get(activePlayer).getName());
		label.setMinWidth(ui.getPaneWidth() * 0.20);
		label.setMinHeight(30);
		leftBar.getChildren().add(label);
		cardPane = new Pane();
		cardPane.setMinWidth(ui.getPaneWidth() * 0.20);
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
		turn = new Label(player.get(activePlayer).getName() + " Turn");
		turn.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.0370+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#FF0000");
		Image icon = ui.loadImage("piece_red.png");
		boardPane.setMinWidth(ui.getPaneWidth() * 0.60);
		ImageView iconView = new ImageView(icon);
		iconView.setPreserveRatio(true);
		iconView.setFitHeight(ui.getPaneHeight()*0.0462);
		HBox playerTurn = new HBox();
		playerTurn.getChildren().addAll(turn, iconView);
		rolled = rollDie();
		movesLeft = rolled;
		roll = new Label("Rolled " + rolled);
		left = new Label("Moves Left : " + movesLeft);
		roll.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.0277+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#FF0000");
		left.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.0277+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#FF0000");
		image = ui.loadImage("die_"+rolled+".jpg");
		rolImage = new ImageView(image);
		rolImage.setPreserveRatio(true);
		rolImage.setFitWidth(ui.getPaneWidth() * 0.100);
		GridPane nav = new GridPane();
		Pane padder = new Pane();
		Label ac = new Label("A-C");ac.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.01851+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#000000");
		Label df = new Label("D-F");df.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.01851+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#000000");
		Label p14 = new Label("1-4");p14.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.01851+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#000000");
		Label p48 = new Label("5-8");p48.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.01851+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#000000");

		nav.add(padder,0,0);nav.add(ac,1,0);nav.add(df,2,0);
		nav.add(p14,0,1);nav.add(b1,1,1);nav.add(b2,2,1);
		nav.add(p48,0,2);nav.add(b3,1,2);nav.add(b4, 2, 2);
		Button about = new Button("About");
		about.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.0277+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill: #FF0000;-fx-background-color: #FFFFFF;-fx-border-color: #FF0000;-fx-border-radius: 3px;-fx-border-width: 5px");
		Button history = new Button("Game history");
		history.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.0277+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill: #FF0000;-fx-background-color: #FFFFFF;-fx-border-color: #FF0000;-fx-border-radius: 3px;-fx-border-width: 5px");
		Button exit = new Button("Exit");
		exit.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.0277+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill: #FF0000;-fx-background-color: #FFFFFF;-fx-border-color: #FF0000;-fx-border-radius: 3px;-fx-border-width: 5px");
		Button airMap = new Button("Flight Plan");
		airMap.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.0277+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill: #FF0000;-fx-background-color: #FFFFFF;-fx-border-color: #FF0000;-fx-border-radius: 3px;-fx-border-width: 5px");
		Button cityInfo = new Button("City Information");
		cityInfo.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.0277+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill: #FF0000;-fx-background-color: #FFFFFF;-fx-border-color: #FF0000;-fx-border-radius: 3px;-fx-border-width: 5px");
		about.setOnAction(e -> ui.switchPane(4));
		history.setOnAction(e -> ui.switchPane(6));
		exit.setOnAction(e-> ui.getEventHandler().respondToSaveRequest(ui.getPrimaryStage()));
		cityInfo.setOnAction(e -> displayInfo(player.get(activePlayer).getPosition()));
		airMap.setOnAction(e -> {
			if(airDisplay) {
				board.setImage(ui.loadImage("gameplay.jpg"));
				airDisplay = false;
			}
			else {
				board.setImage(ui.loadImage("flightplan2.jpg"));
				airDisplay = true;
			}
		});
		VBox rightPane = new VBox();
		rightPane.setStyle("-fx-background-color: #D1B48C;");
		rightPane.setSpacing(10);
		rightPane.setMinWidth(ui.getPaneWidth() * 0.2);
		rightPane.setMinHeight(ui.getPaneHeight());
		rightPane.setAlignment(Pos.CENTER);
		playerTurn.setAlignment(Pos.CENTER);
		nav.setAlignment(Pos.CENTER);
		rightPane.getChildren().addAll(playerTurn, roll, left,rolImage, nav, about, history,exit,airMap,cityInfo);
		gameScreen.setRight(rightPane);
		gameScreen.setLeft(leftBar);
		initPlayerPositions();
		mainPane.getChildren().addAll(gameScreen);
		switchCards(activePlayer);
	}

	public void topLeft() {
		JTEUI ui = JTEUI.getUI();
		b1.setStyle("-fx-background-color: #666666;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b2.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b3.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b4.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		boardI.setLayoutX(0);
		boardI.setLayoutY(0);
	}

	public void topRight() {
		JTEUI ui = JTEUI.getUI();
		b1.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b2.setStyle("-fx-background-color: #666666;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b3.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b4.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		boardI.setLayoutX((ui.getPaneWidth() * 0.6) - ui.getJteGameScreen().getBoard().getBoundsInParent().getWidth());
		boardI.setLayoutY(0);
	}

	public void bottomRight() {
		JTEUI ui = JTEUI.getUI();
		b1.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b2.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b3.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b4.setStyle("-fx-background-color: #666666;-fx-border-width:2px;-fx-border-color:  #FF0000");
		boardI.setLayoutX((ui.getPaneWidth() * 0.6) - ui.getJteGameScreen().getBoard().getBoundsInParent().getWidth());
		boardI.setLayoutY((ui.getPaneHeight()) - ui.getJteGameScreen().getBoard().getBoundsInParent().getHeight());
	}

	public void bottomLeft() {
		JTEUI ui = JTEUI.getUI();
		b1.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b2.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b3.setStyle("-fx-background-color: #666666;-fx-border-width:2px;-fx-border-color:  #FF0000");
		b4.setStyle("-fx-background-color: #FFFFFF;-fx-border-width:2px;-fx-border-color:  #FF0000");
		boardI.setLayoutX(0);
		boardI.setLayoutY((ui.getPaneHeight()) - ui.getJteGameScreen().getBoard().getBoundsInParent().getHeight());
	}

	public int rollDie() {
		return (int)(Math.random()*6)+1;
	}

	public void mapClicked(City clicked) {
		JTEUI ui = JTEUI.getUI();
		double scaledxO = (playerImages.get(activePlayer).getLayoutX()+10)/scaleRatio;
		double scaledyO = (playerImages.get(activePlayer).getLayoutY()+50)/scaleRatio;
		if (!airDisplay) {
			try {
                City origin = gameData.getCity(scaledxO, scaledyO);
                //ui.getErrorHandler().processError(origin.getName(), ui.getPrimaryStage());
                if(origin.getLandConnections().contains(clicked)) {
                    playerMoved(origin, clicked);
                }
                else if (origin.getSeaConnections().contains(clicked)) {
                    playerMovedSea(origin, clicked);
                }
            } catch (CityNotFoundException e) {
                //System.out.print("No city at given coordinates");
            }
		}
		else {
			try {
				City origin = gameData.getCity(scaledxO, scaledyO);
				if(origin.getAirport()!=0) {
					if(clicked.getAirport()!=0) {
						if(clicked.getAirport()==origin.getAirport()) {
							playerMoved(origin, clicked);
						}
						else if (clicked.getAirport() == (origin.getAirport()-2) || clicked.getAirport() == (origin.getAirport()-2)) {
							if(movesLeft>=4) {
								movesLeft-=3;
								fly(origin, clicked);
							}
						}
						else if(origin.getAirport()%2 == 0) {
							if(clicked.getAirport()==origin.getAirport()-1) {
								if(movesLeft>=2) {
									movesLeft--;
									fly(origin, clicked);
								}
							}
						}
						else if (origin.getAirport()%2 != 0){
							if(clicked.getAirport() == (origin.getAirport()+1)) {
								if(movesLeft>=2) {
									fly(origin, clicked);
								}
							}
						}
					}
				}
			} catch (CityNotFoundException e) {

			}

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
			Image image = ui.loadImage(player.get(playerPos).getHand().get(counter).toString().replaceAll(" ", "_"));
			ImageView imageView = new ImageView(image);
			imageView.setPreserveRatio(true);
			imageView.setFitWidth(ui.getPaneWidth() * 0.19);
			cardPane.getChildren().add(imageView);
			imageView.setEffect(ds1);
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(700),imageView);
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
			System.out.println(player.get(i).getPosition() + " " + player.get(i).getPosition().getActualx() * scaleRatio + " " + player.get(i).getPosition().getActualy() * scaleRatio);
			boardI.getChildren().add(playerImages.get(i));
			playerImages.get(i).setLayoutX(player.get(i).getPosition().getActualx() * scaleRatio - 10 - playerImages.get(i).getBoundsInParent().getMinX());
			playerImages.get(i).setLayoutY(player.get(i).getPosition().getActualy() * scaleRatio - 50 - playerImages.get(i).getBoundsInParent().getMinY());
			DropShadow ds1 = new DropShadow();
			ds1.setOffsetY(-2.0f);
			ds1.setOffsetX(4.0f);
			ds1.setColor(Color.BLACK);
			playerImages.get(i).setEffect(ds1);
			Tooltip.install(playerImages.get(i), new Tooltip(player.get(i).getName()));
		}
		drawLines(player.get(0).getPosition());
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

		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), playerImages.get(activePlayer));
		translateTransition.setByX((clicked.getActualx() - origin.getActualx()) * scaleRatio);
		translateTransition.setByY((clicked.getActualy() - origin.getActualy()) * scaleRatio);
		translateTransition.play();
		translateTransition.setOnFinished(e -> {
			translateTransition.stop();
			TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(1), playerImages.get(activePlayer));
			translateTransition1.setByX((origin.getActualx() - clicked.getActualx()) * scaleRatio);
			translateTransition1.setByY((origin.getActualy() - clicked.getActualy()) * scaleRatio);
			translateTransition1.play();
			playerImages.get(activePlayer).setLayoutX(playerImages.get(activePlayer).getLayoutX() + (clicked.getActualx() - origin.getActualx()) * scaleRatio);
			playerImages.get(activePlayer).setLayoutY(playerImages.get(activePlayer).getLayoutY() + (clicked.getActualy() - origin.getActualy()) * scaleRatio);
			player.get(activePlayer).setPosition(clicked);
			performChecks(clicked);
		});

		ui.getFileLoader().addToHistory(clicked.getName());
		ui.getHistoryScreen().refreshHistory();
	}

	public void fly(City origin, City clicked) {
		JTEUI ui = JTEUI.getUI();
		double savedx, savedy;
		ImageView planeImage = new ImageView(ui.loadImage("plane.png"));
		savedx = playerImages.get(activePlayer).getLayoutX();
		savedy = playerImages.get(activePlayer).getLayoutY();
		planeImage.setLayoutY(savedy);
		planeImage.setLayoutX(savedx);
		planeImage.setPreserveRatio(true);
		planeImage.setFitHeight(playerImages.get(activePlayer).getFitHeight());
		boardI.getChildren().remove(playerImages.get(activePlayer));
		boardI.getChildren().addAll(planeImage);
		double xdisplacement = (clicked.getActualx() - origin.getActualx()) * scaleRatio;
		double ydisplacement = (clicked.getActualy() - origin.getActualy()) * scaleRatio;
		double rotate = Math.atan(ydisplacement/xdisplacement);
		planeImage.setRotate(rotate);
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(3000), planeImage);
		translateTransition.setByX(xdisplacement);
		translateTransition.setByY(ydisplacement);
		translateTransition.play();
		translateTransition.setOnFinished(e -> {
			translateTransition.stop();
			boardI.getChildren().remove(planeImage);
			boardI.getChildren().add(playerImages.get(activePlayer));
			playerImages.get(activePlayer).setLayoutX(savedx + (clicked.getActualx() - origin.getActualx()) * scaleRatio);
			playerImages.get(activePlayer).setLayoutY(savedy + (clicked.getActualy() - origin.getActualy()) * scaleRatio);
			player.get(activePlayer).setPosition(clicked);
			performChecks(clicked);
		});

		ui.getFileLoader().addToHistory(clicked.getName());
		ui.getHistoryScreen().refreshHistory();
	}

	public void moveNoAnimate(double x, double y, City origin, City clicked) {
		playerImages.get(activePlayer).setLayoutX(x + (clicked.getActualx() - origin.getActualx()) * scaleRatio);
		playerImages.get(activePlayer).setLayoutY(y + (clicked.getActualy() - origin.getActualy()) * scaleRatio);
		player.get(activePlayer).setPosition(clicked);
		performChecks(clicked);
	}

	public void performChecks(City clicked) {
		JTEUI ui = JTEUI.getUI();
		drawLines(clicked);
		movesLeft--;
		left.setText("Moves left : " + movesLeft);
		double scaledxO = (playerImages.get(activePlayer).getLayoutX() + 10) / scaleRatio;
		double scaledyO = (playerImages.get(activePlayer).getLayoutY() + 50) / scaleRatio;
		try {
			City city = gameData.getCity(scaledxO, scaledyO);
			for (int i = 0; i < player.get(activePlayer).getHand().size(); i++) {
				if (city == player.get(activePlayer).getHand().get(i)) {
					if(player.get(activePlayer).getHome() == city) {
						if(player.get(activePlayer).getHand().size() <=1 ){
							ui.getEventHandler().respondToWinRequest(ui.getPrimaryStage());
						}
					}
					else {
						player.get(activePlayer).getHand().remove(i);
						endTurn();
					}
				}
			}
		} catch (CityNotFoundException e1) {
			e1.printStackTrace();
		}

		if (movesLeft <= 0 && rolled == 6) {
			rolled = rollDie();
			movesLeft = rolled;
			turn.setText(player.get(activePlayer).getName() + " Turn");
			left.setText("Moves left : " + movesLeft);
			roll.setText("Rolled " + rolled);
			roll.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.02777+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#FF0000");
			Image image = ui.loadImage("die_"+rolled+".jpg");
			rolImage.setImage(image);
			rolImage.setPreserveRatio(true);
			rolImage.setFitWidth(ui.getPaneWidth() * 0.100);
		} else if (movesLeft <= 0) {
			endTurn();
		}
	}

	public void playerMovedSea(City origin, City clicked) {
		if(movesLeft == rolled) {
			movesLeft = 0;
			playerMoved(origin, clicked);
		}
	}

	public void movedSeaNoAnimate(double x, double y, City origin, City clicked) {
		if(movesLeft == rolled) {
			movesLeft = 0;
			moveNoAnimate(x,y,origin,clicked);
		}
	}

	public void endTurn() {
		JTEUI ui = JTEUI.getUI();
		activePlayer++;
		activePlayer%=(humans+ai);
		switchCards(activePlayer);
		label.setValue(player.get(activePlayer).getName());
		rolled = rollDie();
		movesLeft = rolled;
		turn.setText(player.get(activePlayer).getName() + " Turn");
		left.setText("Moves left : " + movesLeft);
		roll.setText("Rolled " + rolled);
		roll.setStyle("-fx-font-size: "+ui.getPaneHeight()*0.02777+"px;-fx-font-family: \"Bauhaus 93\";-fx-text-fill:#FF0000");
		Image image = ui.loadImage("die_"+rolled+".jpg");
		rolImage.setImage(image);
		rolImage.setPreserveRatio(true);
		rolImage.setFitWidth(ui.getPaneWidth() * 0.100);
	}

	public ImageView getActivePlayerImage() {
		return playerImages.get(activePlayer);
	}

	public Player getActive() {
		return player.get(activePlayer);
	}

	public void setPlayerPosition(double x, double y) {
		playerImages.get(activePlayer).setLayoutX(x);
		playerImages.get(activePlayer).setLayoutY(y);
	}

	public void displayInfo(City position){
		JTEUI ui = JTEUI.getUI();
		if(!position.getData().equals("")) {
			VBox overlay = new VBox();
			overlay.setMinWidth(ui.getPaneWidth()*0.6);
			overlay.setMinHeight(ui.getPaneHeight());
			overlay.setMaxWidth(ui.getPaneWidth() * 0.6);
			overlay.setMaxHeight(ui.getPaneHeight());
			overlay.setStyle("-fx-background-color:#000000;-fx-opacity:0.7;");
			Label data = new Label(position.getData());
			data.setStyle("-fx-text-fill:#FFFFFF;-fx-font-size:20px");
			data.setMaxWidth(ui.getPaneWidth() * 0.4);
			data.setWrapText(true);
			overlay.setAlignment(Pos.CENTER);
			Label close = new Label("Click to close");
			close.setStyle("-fx-text-fill:#FFFFFF;-fx-font-size:10px");
			System.out.println("Added");
			overlay.getChildren().addAll(data, close);
			boardPane.getChildren().add(overlay);
			overlay.setSpacing(10);
			overlay.setOnMouseClicked(e -> {
				boardPane.getChildren().remove(overlay);
				System.out.println("Removed");
			});
		}
	}

	public void saveGame() {
		JTEUI ui = JTEUI.getUI();
		ui.getFileLoader().saveGame(gameData,humans,ai,activePlayer);
	}
}
