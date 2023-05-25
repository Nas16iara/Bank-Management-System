package com.ranasia.banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

import static com.ranasia.banking.ValidateData.*;

public class RegistrationAccountController {
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

    public void clickedDone(ActionEvent actionEvent) {

        errorLabel.setText("");

        cusPassword.setBorder(Border.stroke(Color.rgb(113,171,238)));
        cusEmail.setBorder(Border.stroke(Color.rgb(113,171,238)));
        cusUsername.setBorder(Border.stroke(Color.rgb(113,171,238)));
        cusPassword.setBorder(Border.stroke(Color.rgb(113,171,238)));

        //Validate Username ----- Need TO REVIEW

        userName = cusUsername.getText();

        //Validate Password

        if(!isValidPassword(cusPassword.getText())){
            errorLabel.setText("Error: Password Must contain at least 1 special character, 1 numbers, and be at least 10 characters long");
            cusPassword.setBorder(Border.stroke(Color.rgb(255, 0, 0)));
            return;
        }
        else {
            errorLabel.setText("");
            passwordUser = cusPassword.getText();
        }

        //Validate Phone Number

        if(!isValidPhoneNumber(cusPhoneNumber.getText())){
            errorLabel.setText("Error: Phone Number Format is ###-###-####");
            cusPhoneNumber.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return;
        }
        else{
            errorLabel.setText("");
            phoneNumberUser = cusPhoneNumber.getText();
        }

        //Validate Email

        if(cusEmail.getText().length() > 255){
            errorLabel.setText("Error: Invalid Email");
            cusEmail.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return;
        }
        else if(!isValidEmail(cusEmail.getText())){
            errorLabel.setText("Error: Invalid Email");
            cusEmail.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return;
        }
        else{
            errorLabel.setText("");
            emailUser = cusEmail.getText();
        }

        System.out.println(userName + " | " + passwordUser +  " | " + emailUser +  " | " + phoneNumberUser);
        System.out.println(firstNameUser + "|" + middleInitialUser + "|" + lastNameUser + "|");
        System.out.println(ssnUser + "|" + dateOfBirthUser + "|");
        System.out.println(streetAddressRUser + "|" + cityRUser + "|" + stateRUser + "|" + zipcodeRUser);
        System.out.println(streetAddressMUser + "|" + cityMUser + "|" + stateMUser + "|" + zipcodeMUser);
    }

    public void clickedBack(ActionEvent actionEvent) {

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

}
