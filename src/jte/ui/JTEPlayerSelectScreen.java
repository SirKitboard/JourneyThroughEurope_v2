package jte.ui;

import application.Main;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

import java.util.ArrayList;

/**
 * Created by Aditya on 11/22/2014.
 */
public class JTEPlayerSelectScreen extends BorderPane{
	HBox playerSelectNorthBar;
	GridPane playerSelectGridPane;
	ArrayList<BorderPane> players;
	ArrayList<ImageView> playerFlags;
	ArrayList<RadioButton> playerRadio;
	ArrayList<RadioButton> aiRadio;
	ArrayList<TextField> nameF = new ArrayList<>();
	public JTEPlayerSelectScreen() {
		JTEUI ui = JTEUI.getUI();
		this.setPadding(ui.getMarginlessInsets());
		playerSelectNorthBar = new HBox();
		playerSelectGridPane = new GridPane();
		players = new ArrayList<BorderPane>();
		playerFlags = new ArrayList<>();
		Button go = new Button("GO!");
		final ComboBox<Integer> comboBox = new ComboBox<Integer>();
		comboBox.getItems().addAll(2, 3, 4, 5, 6);
		comboBox.setValue(2);
		Label noPlayers = new Label("Select Number of Players : ");
		noPlayers.setMinWidth(150);
		playerSelectNorthBar.getChildren().addAll(noPlayers, comboBox, go);
		comboBox.valueProperty().addListener(e -> numberOfPlayers(comboBox.getValue()));
		go.setOnAction(e -> ui.startGame(comboBox.getValue()));
		playerFlags.add(new ImageView(ui.loadImage("flag_black.png")));
		playerFlags.add(new ImageView(ui.loadImage("flag_blue.png")));
		playerFlags.add(new ImageView(ui.loadImage("flag_green.png")));
		playerFlags.add(new ImageView(ui.loadImage("flag_red.png")));
		playerFlags.add(new ImageView(ui.loadImage("flag_white.png")));
		playerFlags.add(new ImageView(ui.loadImage("flag_yellow.png")));
		playerSelectGridPane.setVgap(4);
		playerRadio = new ArrayList<>();
		aiRadio = new ArrayList<>();
		PropertiesManager props = PropertiesManager.getPropertiesManager();
		for(int i=0;i<6;i++) {
			BorderPane playerSelect = new BorderPane();
			playerSelect.setMinHeight(ui.getPaneHeight() / 2.0 - 5);
			playerSelect.setMinWidth(ui.getPaneWidth() / 3.0 - 5);
			playerFlags.get(i).setPreserveRatio(true);
			playerFlags.get(i).setFitWidth(ui.getPaneWidth() / 9.0 - 5);

			playerSelect.setLeft(playerFlags.get(i));
			final ToggleGroup playerType = new ToggleGroup();
			VBox toggles = new VBox();
			toggles.setMinWidth(ui.getPaneWidth() / 9.0 - 5);
			VBox name = new VBox();
			name.setMaxWidth(ui.getPaneWidth()/ 9.0 - 5);
			Label nameL = new Label("Name : ");
			nameF.add(new TextField("Player "+(i+1)));
			name.getChildren().addAll(nameL,nameF.get(i));
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
		this.setTop(playerSelectNorthBar);
		this.setCenter(playerSelectGridPane);

	}
	public void numberOfPlayers(int n) {
		for(int i=0;i<6;i++) {
			players.get(i).setVisible(i<n);
		}
	}

	public ArrayList<RadioButton> getRadios() {
		return playerRadio;
	}

	public ArrayList<String> getNames(int n) {
		ArrayList<String> temp  = new ArrayList<>();
		for(int i=0;i<n;i++) {
			temp.add(nameF.get(i).getText());
		}
		return temp;
	}
}
