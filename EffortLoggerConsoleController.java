import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EffortLoggerConsoleController {

    @FXML
    private ChoiceBox<?> CategoryChoiceBox;

    @FXML
    private Label ClockLabel;

    @FXML
    private ChoiceBox<?> EffortChoiceBox;

    @FXML
    private Label EffortLoggerConsoleTitle;

    @FXML
    private Label EnterOtherDetailsText;

    @FXML
    private TextField EnterOtherDetailsTextBox;

    @FXML
    private ChoiceBox<?> LifeCycleChoiceBox1;

    @FXML
    private ChoiceBox<?> ProjectChoiceBox;

    @FXML
    private Button StartActivity;

    @FXML
    private Button StopActivity;
    
    @FXML
    private void initialize() {
        EnterOtherDetailsText.setVisible(false);
        EnterOtherDetailsTextBox.setVisible(false);
        
    }
}
