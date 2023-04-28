/*
 * By: Thomas Tung
 * 4/1/2023
 * This will be testing risk on security information, as well as ensuring access to the code is done through data.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PrototypeLoginController {

    @FXML
    private TextField Answer1;

    @FXML
    private TextField Answer2;

    @FXML
    private TextField Answer3;

    @FXML
    private Label Incorrect;

    @FXML
    private Label Question1;

    @FXML
    private Label Question2;

    @FXML
    private Label Question3;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField Username;

    @FXML
    private void initialize() {
        Incorrect.setVisible(false);
        Scanner myReader;
        try {
            File file = new File("UserData.txt");
            myReader = new Scanner(file);
            Question1.setText(myReader.nextLine());
            Question2.setText(myReader.nextLine());
            Question3.setText(myReader.nextLine());
            myReader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void LoginClick(ActionEvent event) {
        String User = Username.getText();
        String Pass = Password.getText();
        String Ans1 = Answer1.getText();
        String Ans2 = Answer2.getText();
        String Ans3 = Answer3.getText();
        HashPBKDF obj = new HashPBKDF();
        if(obj.hashDataRead(User+Pass+Ans1+Ans2+Ans3, Ans1+User+Ans3+Ans2+Pass)){
            Node  source = (Node)  event.getSource(); 
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        }else{
            Incorrect.setVisible(true);
        }
    }

}
