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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

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

    @FXML
    public void loginButtonOnAction(ActionEvent actionEvent) {
        if(usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false){
            validateLogin();
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
    public void validateLogin(){
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectionDb = connection.getConnection();


        String verifyLogin = "SELECT count(1) FROM banking.user_account WHERE username = '"+ usernameTextField.getText() +"' AND password = '"+enterPasswordField.getText()+"'";
        try{
            Statement statement = connectionDb.createStatement();
            ResultSet queryResultSet = statement.executeQuery(verifyLogin);

            while(queryResultSet.next()){
                if (queryResultSet.getInt(1) == 1){
                   loginErrorLabel.setText("Congrats");
                }
                else {
                    loginErrorLabel.setText("Invalid Login: Please Try Again");
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createAccountForm()  {
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
}