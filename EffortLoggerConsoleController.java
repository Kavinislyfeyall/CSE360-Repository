import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.time.*;
import javafx.scene.control.Alert;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Keeps the same general layout as the first effort logger as per the users request
//This file in conjunction with the console controller FXML manages all buttons, event handlers, textfields, labels, and other GUI elements

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
    private Label RemovedText;
    
    @FXML
    private Label IncorrectRemove;
    
    @FXML
    private ComboBox<String> RemoverList;

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
    String Other = "";
    String pass = "";
    @FXML
    private void initialize() {
        EnterOtherDetailsText.setVisible(false);
        EnterOtherDetailsTextBox.setVisible(false);
        CategoryTextBox.setVisible(false);
        AddedText.setVisible(false);
        IncorrectAdd.setVisible(false);
        WhichBlankText.setVisible(false);
        WhichBlankList.setVisible(false);
        IncorrectRemove.setVisible(false);
        RemovedText.setVisible(false);
        AdderList.getItems().add("Project");
        AdderList.getItems().add("Life Cycle Step");
        AdderList.getItems().add("Deliverable");
        EffortChoiceBox.getItems().add("Plans");
        EffortChoiceBox.getItems().add("Deliverables");
        EffortChoiceBox.getItems().add("Interruptions");
        EffortChoiceBox.getItems().add("Defects");
        EffortChoiceBox.getItems().add("Others");    
        String file = "UserData.txt";
        try{
            File file1 = new File("psh.txt");
            Scanner myReader;
            myReader = new Scanner(file1);
            pass = myReader.nextLine();
            myReader.close();
            System.out.println(pass);
        }
        catch(Exception e)
        {
            
        }
        DBH.pass = pass;
        DBH.Create(pass);
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
        temp = DBH.printWholeList();
        for(String temp1 : temp)
        {
            RemoverList.getItems().add(temp1);
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
                AddedText.setVisible(false);
            }else if(AdderList.getValue().equals("Deliverable")){
                WhichBlankText.setText("Which Effort Category");
                WhichBlankText.setVisible(true);
                WhichBlankList.getItems().clear();
                WhichBlankList.getItems().addAll(EffortChoiceBox.getItems());
                WhichBlankList.setVisible(true);
                AddedText.setVisible(false);
            }else{
                WhichBlankList.getItems().clear();
                WhichBlankText.setVisible(false);
                WhichBlankList.setVisible(false);
                AddedText.setVisible(false);
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
        
       if(DBH.isEnc() == 0)
       {
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
        RemoverList.getItems().clear();
        ArrayList<String> temp = DBH.printWholeList();
        for(String temp1 : temp)
        {
            RemoverList.getItems().add(temp1);
        }
        }
        else
        {
            showAlert();
        }
    }
    
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Encrypted Data");
        alert.setHeaderText("Please decrypt the database to add new entries!");
        alert.setContentText("You cannot add new data if the database is already encrypted.");

        alert.showAndWait();
    }
    
    @FXML
    void EncryptDataButton(ActionEvent event) {
        DBH.encryptSheet();
        
    }
    
    @FXML
    void DecryptDataButton(ActionEvent event) {
        DBH.decryptSheet();   
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
    
    @FXML
    void RemoveButton(ActionEvent event) {
        String namedType = RemoverList.getValue();
        if(!namedType.isEmpty() || namedType != null){
            int temp = (RemoverList.getSelectionModel().getSelectedIndex()+1);
            DBH.Remove(temp + "");
            RemoverList.getItems().clear();
            ArrayList<String> tempL = DBH.printWholeList();
            for(String temp1 : tempL)
            {   
            RemoverList.getItems().add(temp1);
            }
            IncorrectRemove.setVisible(false);
            RemovedText.setVisible(true);             
        }else{
            RemovedText.setVisible(false);
            IncorrectRemove.setVisible(true);
        } 
        ProjectChoiceBox.getItems().clear();
        ArrayList<String> tempP = DBH.findListProject();
        for(String temp1 : tempP)
        {
                    ProjectChoiceBox.getItems().add(temp1);
        }
    }
}
