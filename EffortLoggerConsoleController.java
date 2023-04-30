import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EffortLoggerConsoleController {

    @FXML
    private ComboBox<String> CategoryChoiceBox;

    @FXML
    private Label ClockLabel;

    @FXML
    private ComboBox<String> EffortChoiceBox;

    @FXML
    private Label EffortLoggerConsoleTitle;

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
    private Label EffortLoggerAdderTitle;

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

    }

    @FXML
    void StopActivity(ActionEvent event) {

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
        }
    }

    @FXML
    void AddButton(ActionEvent event) {
        String namedType = namedTypeTextBox.getText();
        if(addType(namedType)){
            IncorrectAdd.setVisible(false);
            AddedText.setVisible(true);
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


}
