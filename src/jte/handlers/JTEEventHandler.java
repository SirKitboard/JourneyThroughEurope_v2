package jte.handlers;

import application.Main;
import application.Main.JTEPropertyType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jte.game.JTEGameStateManager;
import jte.ui.JTEUI;
import properties_manager.PropertiesManager;

public class JTEEventHandler {

    /**
     * Constructor that simply saves the ui for later.
     */
    public JTEEventHandler(){}

    public void respondToWinRequest(Stage primaryStage) {
        JTEUI jteui = JTEUI.getUI();
        String options[] = new String[]{"OK"};
        // FIRST MAKE SURE THE USER REALLY WANTS TO EXIT
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        BorderPane exitPane = new BorderPane();
        HBox optionPane = new HBox();
        Button yesButton = new Button(options[0]);
        optionPane.setSpacing(10.0);
        optionPane.getChildren().addAll(yesButton);
        StackPane textPane = new StackPane();
        textPane.setPadding(jteui.getMarginlessInsets());
        Text exitLabel = new Text("YOU WIN!\nHead Back to the Main screen?");
        exitLabel.setFont(Font.font("Calibri"));
        exitLabel.setStyle("-fx-text-alignment:center");
        Image wini = jteui.loadImage("trophy.png");
        ImageView winv = new ImageView(wini);
        winv.setFitWidth(200);
        winv.setFitHeight(300);
        textPane.getChildren().add(exitLabel);
        exitPane.setTop(textPane);
        exitPane.setCenter(winv);
        exitPane.setBottom(yesButton);
        Scene scene = new Scene(exitPane, 300, 400);
        exitPane.setAlignment(yesButton, Pos.CENTER);
        dialogStage.setScene(scene);
        dialogStage.show();
        // WHAT'S THE USER'S DECISION?
        yesButton.setOnAction(e -> {
            // YES, LET'S EXIT
            jteui.switchPane(0);
            dialogStage.close();
        });
    }

    /**
     * This method responds to when the user presses the new game method.
     */
    public void respondToNewGameRequest(int players, int ai,int numCards) {
        JTEUI ui = JTEUI.getUI();
        JTEGameStateManager gsm = ui.getGSM();
        gsm.startNewGame(players, ai,numCards);
    }

    /**
     * This method responds to when the user requests to exit the application.
     *
     *
     */
    public void respondToExitRequest(Stage primaryStage) {
        // ENGLIS IS THE DEFAULT
        String options[] = new String[]{"Yes", "No"};
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        options[0] = props.getProperty(JTEPropertyType.DEFAULT_YES_TEXT);
        options[1] = props.getProperty(JTEPropertyType.DEFAULT_NO_TEXT);
        String verifyExit = props.getProperty(Main.JTEPropertyType.DEFAULT_EXIT_TEXT);

        // NOW WE'LL CHECK TO SEE IF LANGUAGE SPECIFIC VALUES HAVE BEEN SET
        if (props.getProperty(JTEPropertyType.YES_TEXT) != null) {
            options[0] = props.getProperty(JTEPropertyType.YES_TEXT);
            options[1] = props.getProperty(Main.JTEPropertyType.NO_TEXT);
            verifyExit = props.getProperty(Main.JTEPropertyType.EXIT_REQUEST_TEXT);
        }

        // FIRST MAKE SURE THE USER REALLY WANTS TO EXIT
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        BorderPane exitPane = new BorderPane();
        HBox optionPane = new HBox();
        Button yesButton = new Button(options[0]);
        Button noButton = new Button(options[1]);
        optionPane.setSpacing(10.0);
        optionPane.getChildren().addAll(yesButton, noButton);
        Label exitLabel = new Label(verifyExit);
        exitPane.setCenter(exitLabel);
        exitPane.setBottom(optionPane);
        Scene scene = new Scene(exitPane, 200, 100);
        dialogStage.setScene(scene);
        dialogStage.show();
        // WHAT'S THE USER'S DECISION?
        yesButton.setOnAction(e -> {
            // YES, LET'S EXIT
	        System.exit(0);
        });
        noButton.setOnAction(e -> {
            dialogStage.close();
        });
    }
}
