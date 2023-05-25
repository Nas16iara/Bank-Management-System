package com.ranasia.banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.ranasia.banking.ValidateData.*;

public class RegistrationAccountController {
    public PasswordField confirmPassword;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField cusUsername;
    @FXML
    private PasswordField cusPassword;
    @FXML
    private TextField cusPhoneNumber;
    @FXML
    private TextField cusEmail;

    static String firstNameUser, middleInitialUser, lastNameUser,  dateOfBirthUser, ssnUser,
            streetAddressRUser, cityRUser, stateRUser, zipcodeRUser, streetAddressMUser,
            cityMUser,  stateMUser,  zipcodeMUser, userName, passwordUser,phoneNumberUser,emailUser;

    static Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);

    public void clickedDone(ActionEvent actionEvent) {
        boolean isValid = validateDataInformation();
        if(isValid) {
            registerUser();
        }
    }

    public void clickedBack(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("registerPersonalForms.fxml"));
            Stage registerStage2 = new Stage();
            registerStage2.initStyle(StageStyle.UNDECORATED);
            registerStage2.setScene(new Scene(root,700,500));
            registerStage2.show();
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    //Secure the SSN number and Password
    public String secureData(String data){
        return encoder.encode(data);
    }

    //Add information and then check if it fits the criteria
    public boolean validateDataInformation(){
        errorLabel.setText("");

        cusPassword.setBorder(Border.stroke(Color.rgb(113,171,238)));
        cusEmail.setBorder(Border.stroke(Color.rgb(113,171,238)));
        cusUsername.setBorder(Border.stroke(Color.rgb(113,171,238)));
        cusPassword.setBorder(Border.stroke(Color.rgb(113,171,238)));
        confirmPassword.setBorder(Border.stroke(Color.rgb(113,171,238)));

        //Validate Username ----- Need TO REVIEW

        userName = cusUsername.getText();

        //Validate Password

        if(!isValidPassword(cusPassword.getText())){
            errorLabel.setText("Error: Password Must contain at least 1 special character, 1 numbers, and be at least 10 characters long");
            cusPassword.setBorder(Border.stroke(Color.rgb(255, 0, 0)));
            return false;
        }
        else {
            errorLabel.setText("");
            passwordUser = cusPassword.getText();
        }

        //Confirming password

        if(!passwordUser.equals(confirmPassword.getText())){
            errorLabel.setText("Error: Password do not match");
            cusPassword.setBorder(Border.stroke(Color.rgb(255, 0, 0)));
            confirmPassword.setBorder(Border.stroke(Color.rgb(255,0,0)));
            passwordUser = "";
            return false;
        }
        else{
            errorLabel.setText("");
        }

        //Validate Phone Number

        if(!isValidPhoneNumber(cusPhoneNumber.getText())){
            errorLabel.setText("Error: Phone Number Format is ###-###-####");
            cusPhoneNumber.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return false;
        }
        else{
            errorLabel.setText("");
            phoneNumberUser = cusPhoneNumber.getText();
        }

        //Validate Email

        if(cusEmail.getText().length() > 255){
            errorLabel.setText("Error: Invalid Email");
            cusEmail.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return false;
        }
        else if(!isValidEmail(cusEmail.getText())){
            errorLabel.setText("Error: Invalid Email");
            cusEmail.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return false;
        }
        else{
            errorLabel.setText("");
            emailUser = cusEmail.getText();
        }
        ssnUser = secureData(ssnUser);
        passwordUser = secureData(passwordUser);
        return true;
    }

    // Gets the information from first fxml file, so it can be saved in database
    public void getPersonalInformation(String firstNameUser, String middleInitialUser, String lastNameUser,
                                       String dateOfBirthUser, String ssnUser, String streetAddressRUser,
                                       String cityRUser, String stateRUser, String zipcodeRUser,
                                       String streetAddressMUser, String cityMUser, String stateMUser,
                                       String zipcodeMUser) {

        RegistrationAccountController.firstNameUser = firstNameUser;
        RegistrationAccountController.middleInitialUser = middleInitialUser;
        RegistrationAccountController.lastNameUser = lastNameUser;
        RegistrationAccountController.dateOfBirthUser = dateOfBirthUser;
        RegistrationAccountController.ssnUser = ssnUser;
        RegistrationAccountController.streetAddressRUser = streetAddressRUser;
        RegistrationAccountController.cityRUser = cityRUser;
        RegistrationAccountController.stateRUser = stateRUser;
        RegistrationAccountController.zipcodeRUser = zipcodeRUser;
        RegistrationAccountController.streetAddressMUser = streetAddressMUser;
        RegistrationAccountController.cityMUser = cityMUser;
        RegistrationAccountController.stateMUser = stateMUser;
        RegistrationAccountController.zipcodeMUser = zipcodeMUser;
    }
    public void registerUser(){

        String sqlSelectStatement = "SELECT COUNT(1) FROM banking.user_information WHERE ssn = ?"; //Checks if ssn is already used
        String sqlSelectStatement2 = "SELECT COUNT(1) FROM banking.user_information WHERE email = ?"; //Checks if email is already used
        String sqlSelectStatement3 = "SELECT account_id FROM banking.user_information WHERE email = ?"; //Checks id assigned and add to second table
        String sqlSelectStatement4 = "SELECT COUNT(1) FROM banking.user_account WHERE username = ?";

        String sqlInsertStatement = "INSERT INTO banking.user_information(firstname,lastname,middle_Initial," +
                "birthday,ssn,mStreet_Address,mCity,mState,mZipcode,rStreet_Address,rCity,rState,rZipcode,email) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String sqlInsertStateDB2 = "INSERT INTO banking.user_account(userID,phone,email,Username,Password) " +
                "VALUES (?,?,?,?,?)";
        try {
            DatabaseConnection connectionNow = new DatabaseConnection();
            Connection connectDB = connectionNow.getConnection();
            int ssnFound = -1;
            int emailFound = -1;
            int usernameFound = -1;

            PreparedStatement preparedStatement1 = connectDB.prepareStatement(sqlSelectStatement); //SSN
            PreparedStatement preparedStatement2 = connectDB.prepareStatement(sqlSelectStatement2); //Email
            PreparedStatement preparedStatement = connectDB.prepareStatement(sqlInsertStatement); // Insert Personal Info in database
            PreparedStatement preparedStatement3 = connectDB.prepareStatement(sqlSelectStatement3); // Gets UserID back
            PreparedStatement preparedStatement4 = connectDB.prepareStatement(sqlInsertStateDB2); // Insert Login Info in database
            PreparedStatement preparedStatement5 = connectDB.prepareStatement(sqlSelectStatement4); //Checks username

            preparedStatement1.setString(1,ssnUser);
            preparedStatement2.setString(1,emailUser);
            preparedStatement5.setString(1,userName);

            ResultSet resultSet = preparedStatement1.executeQuery();
            ResultSet resultSet1 = preparedStatement2.executeQuery();
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            ResultSet resultSet3 = preparedStatement5.executeQuery();

            while(resultSet.next() && resultSet1.next()){
                ssnFound = resultSet.getInt(1);
                emailFound = resultSet1.getInt(1);
            }

            if(ssnFound == 0 && emailFound ==  0) {
                preparedStatement.setString(1, firstNameUser);
                preparedStatement.setString(2, lastNameUser);
                preparedStatement.setString(3, middleInitialUser);
                preparedStatement.setString(4, dateOfBirthUser);
                preparedStatement.setString(5, ssnUser); //UNIQUE
                preparedStatement.setString(6, streetAddressMUser);
                preparedStatement.setString(7, cityMUser);
                preparedStatement.setString(8, zipcodeMUser);
                preparedStatement.setString(9, streetAddressRUser);
                preparedStatement.setString(10, cityRUser);
                preparedStatement.setString(11, stateRUser);
                preparedStatement.setString(12, zipcodeRUser);
                preparedStatement.setString(13, emailUser); //UNIQUE
                int row = preparedStatement.executeUpdate();
                if (row == 1) {
                    preparedStatement3.setString(1,emailUser);
                    long id = resultSet2.getLong("account_id");

                    preparedStatement4.setString(1, String.valueOf(id));
                    preparedStatement4.setString(2,phoneNumberUser);
                    preparedStatement4.setString(3,emailUser);
                    preparedStatement4.setString(4,userName);
                    preparedStatement4.setString(5,passwordUser);

                    usernameFound = resultSet3.getInt(1);
                    if(usernameFound == 1){
                        errorLabel.setText("Username already in use");
                        return;
                    }
                    else {
                        errorLabel.setText("Registration Successful");
                    }
                }
                else{
                    errorLabel.setText("Registration Unsuccessful");
                }
            }
            else if(ssnFound == 1 && emailFound == 0){
                errorLabel.setText("SSN/ITIN is already in used");
                return;
            }
            else if(ssnFound == 0 && emailFound == 1){
                errorLabel.setText("Email address is already in used");
                return;
            }

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }


}
