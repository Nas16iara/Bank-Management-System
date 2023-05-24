package com.ranasia.banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

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

    String firstNameUser, middleInitialUser, lastNameUser,  dateOfBirthUser, ssnUser,
            streetAddressRUser, cityRUser, stateRUser, zipcodeRUser, streetAddressMUser,
            cityMUser,  stateMUser,  zipcodeMUser, userName, passwordUser,phoneNumberUser,emailUser;

    public void clickedDone(ActionEvent actionEvent) {
        // GOING TO GET DELETED  ***************
        RegisterPersonalController registerPersonalController = new RegisterPersonalController();
        firstNameUser = registerPersonalController.getFirstName();
        middleInitialUser = registerPersonalController.getMiddleInitial();
        lastNameUser = registerPersonalController.getLastName();
        dateOfBirthUser = registerPersonalController.getDateOfBirth();
        ssnUser = registerPersonalController.getSsn();
        streetAddressRUser = registerPersonalController.getStreetAddressR();
        cityRUser = registerPersonalController.getCityR();
        stateRUser = registerPersonalController.getStateR();

        //---------------------**********************
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

        //Validate Phone Number

        if(!isValidPhoneNumber(cusPassword.getText())){
            errorLabel.setText("Error: Phone Number Format is ###-###-####");
            cusPhoneNumber.setBorder(Border.stroke(Color.rgb(255,0,0)));
        }
        else{
            errorLabel.setText("");
            phoneNumberUser = cusPhoneNumber.getText();
        }

        System.out.println(userName + " | " + passwordUser +  " | " + emailUser +  " | " + phoneNumberUser);
        System.out.println(firstNameUser + "|" + middleInitialUser + "|" + lastNameUser + "|");
        System.out.println(ssnUser + "|" + dateOfBirthUser + "|");
        System.out.println(streetAddressRUser + "|" + cityRUser + "|" + stateRUser + "|" + zipcodeRUser);
        System.out.println(streetAddressMUser + "|" + cityMUser + "|" + stateMUser + "|" + zipcodeMUser);

    }
    public void clickedBack(ActionEvent actionEvent) {

    }
}
