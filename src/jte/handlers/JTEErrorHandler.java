package jte.handlers;

import application.Main;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

public class JTEErrorHandler {

    private Stage ui;

    public JTEErrorHandler(Stage initUI) {
        ui = initUI;
    }

    /**
     * This method provides all error feedback. It gets the feedback text, which
     * changes depending on the type of error, and presents it to the user in a
     * dialog box.
     *
     * @param errorType Identifies the type of error that happened, which allows
     * us to get and display different text for different errors.
     */
    public void processError(Main.JTEPropertyType errorType, Stage primaryStage) {
        // GET THE FEEDBACK TEXT
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String errorFeedbackText = props.getProperty(errorType);

        // NOTE THAT WE'LL USE THE SAME DIALOG TITLE FOR ALL ERROR TYPES
        String errorTitle = props.getProperty(Main.JTEPropertyType.ERROR_DIALOG_TITLE_TEXT);

        // POP OPEN A DIALOG TO DISPLAY TO THE USER
        //JOptionPane.showMessageDialog(window, errorFeedbackText, errorTitle, JOptionPane.ERROR_MESSAGE);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setTitle(errorTitle);
        VBox vbox = new VBox();
        vbox.setSpacing(10.0);
        Label errLabel = new Label(errorFeedbackText);
        Button errButton = new Button("OK");
        vbox.getChildren().addAll(errLabel, errButton);
        Scene scene = new Scene(vbox, 200, 100);
        dialogStage.setScene(scene);
        dialogStage.show();
		errButton.setOnAction(e -> {
			dialogStage.close();
		});
    }
    public void processError(String name, Stage primaryStage) {
        // GET THE FEEDBACK TEXT
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // NOTE THAT WE'LL USE THE SAME DIALOG TITLE FOR ALL ERROR TYPES
        // POP OPEN A DIALOG TO DISPLAY TO THE USER
        //JOptionPane.showMessageDialog(window, errorFeedbackText, errorTitle, JOptionPane.ERROR_MESSAGE);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setTitle("City Clicked");
        VBox vbox = new VBox();
        vbox.setSpacing(10.0);
        Label errLabel = new Label(name);
        Button errButton = new Button("OK");
        vbox.getChildren().addAll(errLabel, errButton);
        Scene scene = new Scene(vbox, 200, 100);
        dialogStage.setScene(scene);
        dialogStage.show();
        errButton.setOnAction(e -> {
            dialogStage.close();
        });
    }
}
