package jte.handlers;

import application.Main;
import application.Main.JTEPropertyType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

    /**
     * This method responds to when the user wishes to switch between the Game,
     * Stats, and Help screens.
     *
     * @param uiState The ui state, or screen, that the user wishes to switch
     * to.
     */
    /**
     * This method responds to when the user presses the new game method.
     */
    public void respondToNewGameRequest() {
        JTEUI ui = JTEUI.getUI();
        JTEGameStateManager gsm = ui.getGSM();
        gsm.startNewGame();
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
