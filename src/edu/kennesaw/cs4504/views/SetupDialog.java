package edu.kennesaw.cs4504.views;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

/**
 * Created by Thomas on 9/30/2017.
 */
public class SetupDialog extends Dialog<String> {
    public SetupDialog(String title, String hostIP) {
        // Configure display message for the user
        setTitle(title);
        setHeaderText("Destination IP Setup for "+title);

        // Set the button types.
        ButtonType submitButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(submitButton, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create choice box control elements
        TextField destIPField = new TextField();
        TextField hostIPField = new TextField(hostIP);
        hostIPField.setEditable(false);

        // Add choice boxes to the dialog pane
        grid.add(new Label("Host IP:"), 0, 0);
        grid.add(hostIPField, 1, 0);
        grid.add(new Label("Destination IP:"), 0, 1);
        grid.add(destIPField, 1, 1);
        getDialogPane().setContent(grid);

        // Bind click event to the dialog run button
        setResultConverter(dialogButton -> {
            if (dialogButton == submitButton) {
                return destIPField.getText();
            }
            return null;
        });
    }
}
