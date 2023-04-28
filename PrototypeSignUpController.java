/*
 * By: Thomas Tung
 * 4/1/2023
 * This will be testing risk on security information, as well as ensuring access to the code is done through data.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PrototypeSignUpController {

    //Set Variables to be used later

    @FXML
    private TextField Answer1;

    @FXML
    private TextField Answer2;

    @FXML
    private TextField Answer3;

    @FXML
    private TextField Password;

    @FXML
    private TextField Question1;

    @FXML
    private TextField Question2;

    @FXML
    private TextField Question3;

    @FXML
    private TextField Username;

    //When the Sign up button is clicked it will get all the inputs and hash them
    @FXML
    void SignUpClick(ActionEvent event) {
        String User = Username.getText();
        String Pass = Password.getText();
        String Ans1 = Answer1.getText();
        String Ans2 = Answer2.getText();
        String Ans3 = Answer3.getText();
        String Que1 = Question1.getText();
        String Que2 = Question2.getText();
        String Que3 = Question3.getText();
        HashPBKDF obj = new HashPBKDF();
        obj.hashDataWrite(User+Pass+Ans1+Ans2+Ans3, Ans1+User+Ans3+Ans2+Pass, Que1, Que2, Que3);
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
