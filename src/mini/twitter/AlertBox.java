package mini.twitter;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * File: AlertBox.java
 * Goal: To Display a Alert On a Different Window to the User
 * */

public class AlertBox
{

    public static void display(String title, String message)
    {
        //Creating a Window
        Stage window = new Stage();

        //Block user interaction with other windows until this one is taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(250);

        Label label = new Label();
        label.setText(message);

        //Button closeButton = new Button("Close the Window");

        //Closing the Window when the user clicks the button
        //closeButton.setOnAction(e-> window.close());

        VBox layout = new VBox(10);
        //layout.getChildren().addAll(label,closeButton);
        layout.getChildren().add(label);
        layout.setAlignment(Pos.CENTER);

        //Creating a Scene
        Scene scene = new Scene(layout);

        //Linking the Scene to the Window
        window.setScene(scene);
        window.showAndWait(); //Shows the window and waits until its closed
    }
}
