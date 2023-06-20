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

public class RegistrationAccountController2 {
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

    static String firstNameUser;
    static String middleInitialUser;
    static String lastNameUser;
    static String dateOfBirthUser;
    static String ssnUser;
    static String streetAddressRUser;
    static String cityRUser;
    static String stateRUser;
    static String zipcodeRUser;
    static String streetAddressMUser;
    static String cityMUser;
    static String stateMUser;
    static String zipcodeMUser;
    static String userName;
    static String passwordUser;
    static String phoneNumberUser;
    static String emailUser;
    int cusIDUser;

    static Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);

    public void clickedDone(ActionEvent actionEvent) {
        boolean isValid = validateDataInformation();
        if(isValid) {
            registerUser();
            cusIDUser = getCusIdUser();
            if(cusIDUser == -1){
                System.out.println("ERROR");
            }
            else {
                nextPage();
            }
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
    private int getCusIdUser() {
        String sqlSelectIdNoStatement = "SELECT account_id FROM banking.customer WHERE username = ? and firstname = ?";
        try {
            DatabaseConnection connectionNow = new DatabaseConnection();
            Connection connectDB = connectionNow.getConnection();
            PreparedStatement preparedStatement= connectDB.prepareStatement(sqlSelectIdNoStatement); // get account_id to pass on
            preparedStatement.setString(1,userName.toLowerCase());
            preparedStatement.setString(2,firstNameUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("account_id");
        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return -1;
    }
    public static boolean validateData(String encodedPassword,String enteredPassword){
        // Needs to be used in login database. Will Add it there after reviewing
        return encoder.matches(enteredPassword,encodedPassword);
    }

    //Secure the SSN number and Password
    public static String secureData(String data){
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
            System.out.println(passwordUser);
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
    public void setPersonalInformation(String firstNameUser, String middleInitialUser, String lastNameUser,
                                       String dateOfBirthUser, String ssnUser, String streetAddressRUser,
                                       String cityRUser, String stateRUser, String zipcodeRUser,
                                       String streetAddressMUser, String cityMUser, String stateMUser,
                                       String zipcodeMUser) {

        RegistrationAccountController2.firstNameUser = firstNameUser;
        RegistrationAccountController2.middleInitialUser = middleInitialUser;
        RegistrationAccountController2.lastNameUser = lastNameUser;
        RegistrationAccountController2.dateOfBirthUser = dateOfBirthUser;
        RegistrationAccountController2.ssnUser = ssnUser;
        RegistrationAccountController2.streetAddressRUser = streetAddressRUser;
        RegistrationAccountController2.cityRUser = cityRUser;
        RegistrationAccountController2.stateRUser = stateRUser;
        RegistrationAccountController2.zipcodeRUser = zipcodeRUser;
        RegistrationAccountController2.streetAddressMUser = streetAddressMUser;
        RegistrationAccountController2.cityMUser = cityMUser;
        RegistrationAccountController2.stateMUser = stateMUser;
        RegistrationAccountController2.zipcodeMUser = zipcodeMUser;
    }
    public void registerUser(){

        String sqlInsertStatement = "INSERT INTO banking.customer(firstname,lastname,middle_Initial,birthday,ssn,mStreet_Address,mCity,mState,mZipcode,rStreet_Address,rCity,rState,rZipcode,email,phone,username,password) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"; //Inserts data into database

        String sqlSelectStatementEmail    = "SELECT COUNT(1) FROM banking.customer WHERE email = ?";
        String sqlSelectSSNStatement      = "SELECT COUNT(1) FROM banking.customer WHERE ssn = ?";
        String sqlSelectUsernameStatement = "SELECT COUNT(1) FROM banking.customer WHERE username = ?";
        try {
            DatabaseConnection connectionNow = new DatabaseConnection();
            Connection connectDB = connectionNow.getConnection();

            PreparedStatement preparedStatementEmail = connectDB.prepareStatement(sqlSelectStatementEmail); // Checks if email is already in uses
            preparedStatementEmail.setString(1,emailUser);
            ResultSet resultSet = preparedStatementEmail.executeQuery();
            resultSet.next();
            int emailCheck = resultSet.getInt(1);
            if(emailCheck == 1){ //If there is a match in the database then email can't be used
                errorLabel.setText("Error: Email already in use");
                preparedStatementEmail.close();
                resultSet.close();
                return;
            }
            PreparedStatement preparedStatementSSN = connectDB.prepareStatement(sqlSelectSSNStatement); // Checks if ssn already exist
            preparedStatementSSN.setString(1,ssnUser);
            ResultSet resultSetSSN = preparedStatementSSN.executeQuery();
            resultSetSSN.next();
            if(resultSetSSN.getInt(1) == 1){
                errorLabel.setText("Error: SSN already in use");
                preparedStatementSSN.close();
                resultSetSSN.close();
                return;
            }
            PreparedStatement preparedStatementUsername = connectDB.prepareStatement(sqlSelectUsernameStatement); // Checks if username already exist
            preparedStatementUsername.setString(1,userName.toLowerCase());
            ResultSet resultSetUsername = preparedStatementUsername.executeQuery();
            resultSetUsername.next();
            if(resultSetUsername.getInt(1) == 1){
                errorLabel.setText("Error: Username already in use");
                preparedStatementUsername.close();
                resultSetUsername.close();
                return;
            }
            // Input Information in database
            PreparedStatement preparedStatement = connectDB.prepareStatement(sqlInsertStatement);
            preparedStatement.setString(1, firstNameUser);
            preparedStatement.setString(2, lastNameUser);
            preparedStatement.setString(3, middleInitialUser);
            preparedStatement.setString(4, dateOfBirthUser);
            preparedStatement.setString(5, ssnUser); //UNIQUE
            preparedStatement.setString(6, streetAddressMUser);
            preparedStatement.setString(7, cityMUser);
            preparedStatement.setString(8,stateMUser);
            preparedStatement.setString(9, zipcodeMUser);
            preparedStatement.setString(10, streetAddressRUser);
            preparedStatement.setString(11, cityRUser);
            preparedStatement.setString(12, stateRUser);
            preparedStatement.setString(13, zipcodeRUser);
            preparedStatement.setString(14, emailUser); //UNIQUE
            preparedStatement.setString(15,phoneNumberUser);
            preparedStatement.setString(16,userName.toLowerCase()); // UNIQUE
            preparedStatement.setString(17,passwordUser);
            int row = preparedStatement.executeUpdate();
            System.out.println(row);
            if(row == 1){
                errorLabel.setText("Registration Successful");
            }

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
    public void nextPage(){
        cusIDUser = getCusIdUser();
        System.out.println("cusIDUSER:"+cusIDUser);
        OpenAccountController3 newAccountController = new OpenAccountController3();
        newAccountController.setCusID(cusIDUser);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("openAccountPage3.fxml"));
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
