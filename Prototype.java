/*
 * By: Thomas Tung
 * 4/1/2023
 * This will be testing risk on security information, as well as ensuring access to the code is done through data.
 */
import java.io.IOException;

import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;

public class Prototype extends Application{
    @Override
    public void start(Stage primaryStage) {
        
        Parent root;
        File file = new File("UserData.txt");
        if(file.length() == 0){
            try {
                //Create a scene that'll be shown using the fxml file
                root = FXMLLoader.load(getClass().getResource("EffortLoggerConsole.fxml"));
                Scene scene = new Scene(root);
                //primaryStage.setTitle("Hello World!");
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            try {
                //Create a scene that'll be shown using the fxml file
                root = FXMLLoader.load(getClass().getResource("PrototypeLogin.fxml"));
                Scene scene = new Scene(root);
                //primaryStage.setTitle("Hello World!");
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
 
 public static void main(String[] args) {
        //Launch the Stage
        launch(args);
    }
}
