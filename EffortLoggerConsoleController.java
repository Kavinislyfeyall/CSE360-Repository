import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.*;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.time.*;


public class EffortLoggerConsoleController {

    @FXML
    private ComboBox<String> CategoryChoiceBox;

    @FXML
    private Label ClockLabel;

    @FXML
    private Rectangle ClockRectColor;

    @FXML
    private ComboBox<String> EffortChoiceBox;

    @FXML
    private Label EnterOtherDetailsText;

    @FXML
    private TextField EnterOtherDetailsTextBox;

    @FXML
    private ComboBox<String> LifeCycleChoiceBox;

    @FXML
    private ComboBox<String> ProjectChoiceBox;

    @FXML
    private ComboBox<String> AdderList;

    @FXML
    private Label CategoryTextBox;

    @FXML
    private Label IncorrectAdd;

    @FXML
    private Label AddedText;

    @FXML
    private ComboBox<String> WhichBlankList;

    @FXML
    private Label WhichBlankText;

    @FXML
    private TextField namedTypeTextBox;
    DatabaseHandler DBH = new DatabaseHandler();
    LocalTime startTime;
    String projectName;
    String LCStep;
    String effortCat;
    String Deliverable;
    String Other;
    @FXML
    private void initialize() {
        EnterOtherDetailsText.setVisible(false);
        EnterOtherDetailsTextBox.setVisible(false);
        CategoryTextBox.setVisible(false);
        AddedText.setVisible(false);
        IncorrectAdd.setVisible(false);
        WhichBlankText.setVisible(false);
        WhichBlankList.setVisible(false);
        AdderList.getItems().add("Project");
        AdderList.getItems().add("Life Cycle Step");
        AdderList.getItems().add("Deliverable");
        EffortChoiceBox.getItems().add("Plans");
        EffortChoiceBox.getItems().add("Deliverables");
        EffortChoiceBox.getItems().add("Interruptions");
        EffortChoiceBox.getItems().add("Defects");
        EffortChoiceBox.getItems().add("Others");
        DBH.Create();
        DBH.addListEffort("Plans");
        DBH.addListEffort("Deliverables");
        DBH.addListEffort("Interruptions");
        DBH.addListEffort("Defects");
        DBH.addListEffort("Others");
        ArrayList<String> temp = DBH.findListProject();
        for(String temp1 : temp)
        {
            ProjectChoiceBox.getItems().add(temp1);
        }
        
    }

    @FXML
    void AdderSelected(ActionEvent event) {
        if(!AdderList.getSelectionModel().isEmpty()){
            if(AdderList.getValue().equals("Life Cycle Step")){
                WhichBlankText.setText("Which Project");
                WhichBlankText.setVisible(true);
                WhichBlankList.getItems().clear();
                WhichBlankList.getItems().addAll(ProjectChoiceBox.getItems());
                WhichBlankList.setVisible(true);
            }else if(AdderList.getValue().equals("Deliverable")){
                WhichBlankText.setText("Which Effort Category");
                WhichBlankText.setVisible(true);
                WhichBlankList.getItems().clear();
                WhichBlankList.getItems().addAll(EffortChoiceBox.getItems());
                WhichBlankList.setVisible(true);
            }else{
                WhichBlankList.getItems().clear();
                WhichBlankText.setVisible(false);
                WhichBlankList.setVisible(false);
            }
        }
    }

    @FXML
    void StartActivity(ActionEvent event) {
        ClockLabel.setText("Clock is running");
        ClockRectColor.setFill(Color.rgb(37, 255, 33));
        startTime = LocalTime.now();
        projectName = ProjectChoiceBox.getValue();
        LCStep = LifeCycleChoiceBox.getValue();
        effortCat = EffortChoiceBox.getValue();
        Deliverable = CategoryChoiceBox.getValue();
        Other = EnterOtherDetailsTextBox.getText();
    }

    @FXML
    void StopActivity(ActionEvent event) {
        ClockLabel.setText("Clock is stopped");
        ClockRectColor.setFill(Color.rgb(255, 31, 31));
        if(Other.isEmpty())
        {
            DBH.addData(projectName, LCStep, effortCat, startTime, LocalTime.now(), Deliverable);
        }
        else
        {
            DBH.addData(projectName, LCStep, effortCat, startTime, LocalTime.now(), Deliverable, Other);  
        }
    }

    @FXML
    void EncryptDataButton(ActionEvent event) {

    }
    
    @FXML
    void DecryptDataButton(ActionEvent event) {

    }

    @FXML
    void ShowDeliverable(ActionEvent event) {
        if(EffortChoiceBox.getValue().equals("Others")){
            CategoryTextBox.setVisible(false);
            EnterOtherDetailsText.setVisible(true);
            EnterOtherDetailsTextBox.setVisible(true);
        }else if(!EffortChoiceBox.getValue().isEmpty()){
            CategoryTextBox.setText( EffortChoiceBox.getValue() + ":");
            CategoryTextBox.setVisible(true);
            EnterOtherDetailsText.setVisible(false);
            EnterOtherDetailsTextBox.setVisible(false);
            CategoryChoiceBox.getItems().clear();
            ArrayList<String> temp = DBH.findListPlan(EffortChoiceBox.getValue());
            for(String temp1 : temp)
            {
                CategoryChoiceBox.getItems().add(temp1);
            }
        }
    }
    @FXML
    void ProjectChoiceBoxAct(ActionEvent event)
    {
        LifeCycleChoiceBox.getItems().clear();
        ArrayList<String> temp = DBH.findListLC(ProjectChoiceBox.getValue());
        for(String temp1 : temp)
        {
            LifeCycleChoiceBox.getItems().add(temp1);
        }
    }
    @FXML
    void AddButton(ActionEvent event) {
        String namedType = namedTypeTextBox.getText();
        if(addType(namedType)){
            IncorrectAdd.setVisible(false);
            AddedText.setVisible(true);
            if(AdderList.getValue().equals("Project"))
            {
                DBH.addListProject(namedType);
                ProjectChoiceBox.getItems().clear();
                ArrayList<String> temp = DBH.findListProject();
                for(String temp1 : temp)
                {
                    ProjectChoiceBox.getItems().add(temp1);
                }
            }
            if(AdderList.getValue().equals("Life Cycle Step"))
            {
                DBH.addListLC(WhichBlankList.getValue(),namedType);     
            }
            if(AdderList.getValue().equals("Deliverable"))
            {
                DBH.addListPlan(WhichBlankList.getValue(),namedType);                
            }
        }else{
            AddedText.setVisible(false);
            IncorrectAdd.setVisible(true);
        }       
    }

    boolean addType(String namedType){
        if(namedType.isEmpty() || AdderList.getSelectionModel().isEmpty()){
            return false;
        }
        if(AdderList.getValue().equals("Life Cycle Step") || AdderList.getValue().equals("Deliverable")){
            if(WhichBlankList.getSelectionModel().isEmpty()){
                return false;
            }
        }
        return true;
    }
    
    
    //** TAB 3 STARTS HERE ****************************************************************************************

    @FXML
    private Button SavePasswordButton; // Save button

    @FXML
    private TextField NewPasswordTextField;

    //Save contents of the text box into the file
    // SavePasswordButton.setOnMouseClicked((new EventHandler<MouseEvent>(){

    @FXML
    public void SavePasswordButton(ActionEvent event){

        //  l.setText("Password Set");

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //SAve the text of the textfield input
        String input = NewPasswordTextField.getText();

        //File file;

        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        //stage.close();

        if (file != null){
            SaveFile(input, file); // insert the input and file
        }



    }

    // }));



    //Save contents of the text field and open the directory to save
    @FXML
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

    /*
    @FXML
    private void initialize() {
        EnterOtherDetailsText.setVisible(false);
        EnterOtherDetailsTextBox.setVisible(false);

    }
*/

    //Tab 3 ends HERE*********************************************************************************************
    
}
