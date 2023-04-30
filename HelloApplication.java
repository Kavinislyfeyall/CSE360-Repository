 

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.FileChooser;


import java.util.logging.Logger;
import java.util.logging.Level;
import javafx.scene.control.TextArea;

import EasyXLS.*;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
       // Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Set Password");
        stage.setTitle("Set Password");

        TextField b = new TextField("Enter New Password");

        b.setAlignment(Pos.CENTER);
        // create a tile pane
        TilePane r = new TilePane();

        // create a label
        Label l = new Label("Awaiting new Password");
        l.setAlignment(Pos.CENTER_RIGHT);

        Label l2 = new Label("Name the password file: pw.txt");
        l.setAlignment(Pos.BOTTOM_RIGHT);

        // action event
      /*  EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
              // l.setText(b.getText());
                l.setText("Password Set");
            }
        };*/

        Button buttonSave = new Button("Save");

        buttonSave.setAlignment(Pos.CENTER_LEFT);

        // when enter is pressed
        //b.setOnAction(event);

        r.getChildren().add(b);
        r.getChildren().add(l);
        r.getChildren().add(l2);
        r.getChildren().add(buttonSave);

        Scene sc = new Scene(r, 320, 240);

        // set the scene


        //stage.setScene(scene);
        //stage.show();



        buttonSave.setOnAction((ActionEvent event2) ->{

            l.setText("Password Set");

            FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(stage);

            if (file != null){
                SaveFile(b.getText(), file); // get the text in the text field
            }

                }

                );



        stage.setScene(sc);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }

    private void SaveFile(String content, File file){

        try{
            FileWriter fileWriter;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();

        } catch(IOException ex){

            Logger.getLogger(HelloApplication.class.getName()).log(Level.SEVERE, null, ex);
        }


    }


}