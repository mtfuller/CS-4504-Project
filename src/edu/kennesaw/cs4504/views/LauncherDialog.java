package edu.kennesaw.cs4504.views;

import edu.kennesaw.cs4504.services.TCPClient;
import edu.kennesaw.cs4504.services.TCPPeer;
import edu.kennesaw.cs4504.services.TCPServer;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.UnknownHostException;

/**
 * Created by Thomas on 9/29/2017.
 */
public class LauncherDialog extends Dialog<TCPPeer> {
    private final static String[] APPLICATION_TYPES = {
            "Server",
            "Client"
    };

    public LauncherDialog() {
        // Configure display message for the user
        setTitle("Launcher");
        setHeaderText("Please select the following type of application to run.");

        // Set the button types.
        ButtonType runButton = new ButtonType("Run", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(runButton, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create choice box control elements
        ChoiceBox<String> appTypeBox = new ChoiceBox<>();
        appTypeBox.setItems(FXCollections.observableArrayList(APPLICATION_TYPES));
        appTypeBox.setValue(APPLICATION_TYPES[0]);

        TextField routerHostField = new TextField();
        TextField routerPortField = new TextField();

        // Add choice boxes to the dialog pane
        grid.add(new Label("Select Type:"), 0, 0);
        grid.add(appTypeBox, 1, 0);
        grid.add(new Label("Router Host:"), 0, 1);
        grid.add(routerHostField, 1, 1);
        grid.add(new Label("Router Port:"), 0, 2);
        grid.add(routerPortField, 1, 2);
        getDialogPane().setContent(grid);

        // Bind click event to the dialog run button
        setResultConverter(dialogButton -> {
            if (dialogButton == runButton) {
                String routerHost = routerHostField.getText();
                int routerPort = Integer.parseInt(routerPortField.getText());
                try {
                    if (appTypeBox.getValue() == "Server") return new TCPServer(routerHost, routerPort);
                    else if (appTypeBox.getValue() == "Client") return new TCPClient(routerHost, routerPort);
                    else return null;
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            System.out.println("END OF BUTTON PRESS");
            return null;
        });

    }
}
