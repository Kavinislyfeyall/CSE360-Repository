/*
 * By: Thomas Tung
 * 4/1/2023
 * This will be testing risk on security information, as well as ensuring access to the code is done through data.
 */
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

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

    @FXML
    private Label Incorrect;

    @FXML
    private void initialize() {
        Incorrect.setVisible(false);
    }
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
        ;
        File file1 = new File("psh.txt");
        try{
        file1.createNewFile();
    }
    catch(Exception e)
    {
    }
        //file1.delete();
        PrintWriter pw = null;
        //Encryption eh = new Encryption();
        try{
        FileWriter fw = new FileWriter(file1, true);
        pw = new PrintWriter(fw);
        pw.println(Pass);
        pw.close();
    }
    catch(Exception e)
    {
    }

        
        if(goodPassword(Pass)){
            if(ansqueEmpty(Ans1, Ans2, Ans3, Que1, Que2, Que3)){
                HashPBKDF obj = new HashPBKDF();
                obj.hashDataWrite(User+Pass+Ans1+Ans2+Ans3, Ans1+User+Ans3+Ans2+Pass, Que1, Que2, Que3);
                Node  source = (Node)  event.getSource(); 
                Stage stage  = (Stage) source.getScene().getWindow();
                stage.close();
                Parent root;
                Stage primaryStage = new Stage();
                try {
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
                Incorrect.setText("An Answer or Question isn't filled in");
                Incorrect.setVisible(true);
            }
        }else{
            Incorrect.setText("Password needs to be 8 or more with a number and special character");
            Incorrect.setVisible(true);
        }
    }

    boolean ansqueEmpty(String Ans1, String Ans2, String Ans3, String Que1, String Que2, String Que3){
        if(Ans1.isEmpty() || Ans2.isEmpty() || Ans3.isEmpty() || Que1.isEmpty() || Que2.isEmpty() || Que3.isEmpty()){
            return false;
        }
        return true;
    }

    boolean goodPassword(String password){
        if(password.length()>=8){
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();
        }
        else
            return false;
    }

}
