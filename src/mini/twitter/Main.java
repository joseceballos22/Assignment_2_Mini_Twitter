package mini.twitter;
import javafx.application.Application;

/**
 * File: Main.java
 * Goal: Driver class method to trigger the Admin Control Panel
 * */

/**
 * --module-path D:\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib
 * --add-modules javafx.controls,javafx.fxml --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED
 * */

public class Main
{
    public static void main(String[] args)
    {
            //Launching the Admin Panel
            Application.launch(AdminControlPanel.class, args);
    }
}
