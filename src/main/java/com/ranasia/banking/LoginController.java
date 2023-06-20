package com.ranasia.banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static com.ranasia.banking.RegistrationAccountController2.validateData;


public class LoginController implements Initializable {
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Label loginErrorLabel;
    static String username;
    static Boolean isValidLogin;

    @FXML
    public void loginButtonOnAction(ActionEvent actionEvent) {
        if(usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false){
            isValidLogin = validateLogin();
            if(isValidLogin){

            }
        }
        else{
            loginErrorLabel.setText("Please enter username and password");
        }
    }
    @FXML
    public void cancelButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public boolean validateLogin(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectionDb = connection.getConnection();

        String verifyLoginStatement = "SELECT password FROM banking.customer WHERE username = ?";
        String password = enterPasswordField.getText();
        try{
            PreparedStatement statement = connectionDb.prepareStatement(verifyLoginStatement);
            statement.setString(1,usernameTextField.getText());
            ResultSet queryResultSet = statement.executeQuery();

            while(queryResultSet.next()){
                String encodedPassword = queryResultSet.getString("password");
                if (validateData(encodedPassword,password)){
                   loginErrorLabel.setText("Congrats");
                   username = usernameTextField.getText();
                   return true;
                }
                else {
                    loginErrorLabel.setText("Invalid Login: Please Try Again");
                    return false;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return false;
    }

    public void registerButtonOnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("registerPersonalForms.fxml"));
            Stage registerStage1 = new Stage();
            registerStage1.initStyle(StageStyle.UNDECORATED);
            registerStage1.setScene(new Scene(root,700,500));
            registerStage1.show();
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void openAccount(){
        // First Check if there is an account with us
    }
}