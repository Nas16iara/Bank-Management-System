package com.ranasia.banking;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.net.URL;
import java.util.ResourceBundle;

import static com.ranasia.banking.ValidateData.*;


public class RegisterPersonalController implements Initializable {

    @FXML
    private TextField cusFirstName;
    @FXML
    private TextField cusMiddleInitial;
    @FXML
    private TextField cusLastName;
    @FXML
    private TextField cusDOB;
    @FXML
    private TextField cusSSN;
    @FXML
    private TextField cusRStreetAddress;
    @FXML
    private TextField cusRCity;
    @FXML
    private ComboBox<String> cusRState;
    @FXML
    private TextField cusRZipCode;
    @FXML
    public RadioButton yesButton;
    @FXML
    public RadioButton noButton;
    @FXML
    private TextField cusMStreetAddress;
    @FXML
    private TextField cusMCity;
    @FXML
    private ComboBox<String> cusMState;
    @FXML
    private TextField cusMZipcode;
    @FXML
    private Label errorLabel; //If the fields have the incorrect data type or size this error will be display
    @FXML
    public Label errorDateLabel;

    //String for the information

    private String firstName;
    private String middleInitial; //Optional
    private String lastName;
    private String dateOfBirth; //Date of Birth
    private String ssn;

    //R represents residential Address

    private String streetAddressR;
    private String cityR;
    private String stateR;
    private String zipcodeR;

    // M represents Mailing Address

    private String streetAddressM;
    private String cityM;
    private String stateM;
    private String zipcodeM;


    //Create the options for the comboBox with a list of States
    ObservableList<String> listStates = FXCollections.observableArrayList(
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
                 "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
                 "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
                 "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                 "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");



    public void clickedNext(ActionEvent actionEvent) {

        //Set the text to original state when next is entered then checks again

        errorLabel.setText("");
        errorDateLabel.setText("");

        cusFirstName.setBorder(Border.stroke(Color.rgb(113,171,238)));
        cusMiddleInitial.setBorder(Border.stroke(Color.rgb(113,171,238)));
        cusLastName.setBorder(Border.stroke(Color.rgb(113,171,238)));
        cusDOB.setBorder(Border.stroke(Color.rgb(113,171,238)));
        cusSSN.setBorder(Border.stroke(Color.rgb(113,171,238)));
        cusRStreetAddress.setBorder(Border.stroke(Color.rgb(113,171,238)));
        cusRZipCode.setBorder(Border.stroke(Color.rgb(113,171,238)));

        //Validate Name

        //First Name
        if(cusFirstName.getText().isBlank() || cusFirstName.getText().length() > 255){
            errorLabel.setText("Error: First Name is longer than 255 or Blank");
            cusFirstName.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return;
        }
        else{
            firstName = cusFirstName.getText();
            errorLabel.setText("");
        }
        //Middle Name
        if(!(cusMiddleInitial.getText().isBlank()) && (cusMiddleInitial.getText().length() != 1 ||!(isLetter(cusMiddleInitial.getText().toUpperCase())))){
            errorLabel.setText("Error: Middle Initial should be 1 letter");
            cusMiddleInitial.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return;
        }
        else if(!(cusMiddleInitial.getText().isBlank())){
            middleInitial = cusMiddleInitial.getText().toUpperCase();
            errorLabel.setText("");
        }
        //Last Name
        if(cusLastName.getText().isBlank() || cusLastName.getText().length() > 255){
            errorLabel.setText("Error: Last Name is longer than 255 or Blank");
            cusLastName.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return;
        }
        else{
            lastName = cusLastName.getText();
            errorLabel.setText("");
        }

        //Validate SSN/ITIN number

        if(cusSSN.getText().isBlank()){
            errorLabel.setText("SSN/ITIN is blank");
            cusSSN.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return;
        }
        else if(!isValidFormat(cusSSN.getText(),false)){
            errorLabel.setText("SSN/ITIN is enter incorrectly");
            errorDateLabel.setText("Format is ###-##-####");
            cusSSN.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return;
        }
        else{
            ssn = cusSSN.getText();
            errorLabel.setText("");
            errorDateLabel.setText("");
        }

        //Validate Birth Date

        if((!isValidFormat(cusDOB.getText(),true)) || cusDOB.getText().isBlank()){
            errorLabel.setText("Date is formatted incorrectly or Blank");
            errorDateLabel.setText("Format is YYYY-MM-DD");
            cusSSN.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return;
        }
        else if (!isValidDate(cusDOB.getText())) {
            errorLabel.setText("This is not a valid date");
            cusSSN.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return;
        }
        else if(!isOver18(cusDOB.getText())) {
            errorLabel.setText("Your not over 18");
            cusDOB.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return;
        }
        else{
            dateOfBirth = cusDOB.getText();
            errorLabel.setText("");
            errorDateLabel.setText("");
        }

        ///Validate Residential Address

        //Street Address
        if(cusRStreetAddress.getText().isBlank() || cusRStreetAddress.getText().length() > 255){
            errorLabel.setText("Error: Street Address is longer than 255 or Blank");
            cusRStreetAddress.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return;
        }
        else {
            streetAddressR = cusRStreetAddress.getText();
            errorLabel.setText("");
        }
        //City
        if(cusRCity.getText().isBlank() || cusRCity.getText().length() > 255){
            errorLabel.setText("Error: Residential City Address is longer than 255 or Blank");
            cusLastName.setBorder(Border.stroke(Color.rgb(255,0,0)));
            return;
        }
        else{
            cityR = cusRCity.getText();
            errorLabel.setText("");
        }
        //State
        stateR = cusRState.getSelectionModel().getSelectedItem();
        //Zipcode
        if(cusRZipCode.getText().isBlank() || !(isValidZipCode(cusRZipCode.getText()))){
            errorLabel.setText("Error: Residential Zipcode Address is blank or Incorrect");
            cusRZipCode.setBorder(Border.stroke(Color.rgb(225,0,0)));
            return;
        }
        else{
            zipcodeR = cusRZipCode.getText();
            errorLabel.setText("");
        }

        //Validate Mailing Address

        if(yesButton.isSelected()){
            errorLabel.setText("");
            streetAddressM = streetAddressR;
            cityM = cityR;
            stateM = stateR;
            zipcodeM = zipcodeR;
        }
        else if(!yesButton.isSelected() && !noButton.isSelected()){
            errorLabel.setText("Please select yes or no");
            return;
        }
        else if(noButton.isSelected()){
            errorLabel.setText("");
            //Street Address
            if (cusMStreetAddress.getText().isBlank() || cusMStreetAddress.getText().length() > 255) {
                errorLabel.setText("Error: Street Address is longer than 255 or Blank");
                cusMStreetAddress.setBorder(Border.stroke(Color.rgb(255, 0, 0)));
                return;
            } else {
                streetAddressM = cusMStreetAddress.getText();
                errorLabel.setText("");
            }
            //City
            if (cusMCity.getText().isBlank() || cusMCity.getText().length() > 255) {
                errorLabel.setText("Error: Mailing City Address is longer than 255 or Blank");
                cusMCity.setBorder(Border.stroke(Color.rgb(255, 0, 0)));
                return;
            } else {
                cityM = cusMCity.getText();
                errorLabel.setText("");
            }
            //State
            stateM = cusMState.getSelectionModel().getSelectedItem();
            //Zipcode
            if (cusMZipcode.getText().isBlank() || !(isValidZipCode(cusMZipcode.getText()))) {
                errorLabel.setText("Error: Mailing Zipcode Address is blank or Incorrect");
                cusMZipcode.setBorder(Border.stroke(Color.rgb(225, 0, 0)));
                return;
            } else {
                zipcodeM = cusMZipcode.getText();
                errorLabel.setText("");
            }
            errorLabel.setText("");
        }
        nextRegisterForm();
        System.out.println(firstName + "|" + middleInitial + "|" + lastName + "|");
        System.out.println(ssn + "|" + dateOfBirth + "|");
        System.out.println(streetAddressR + "|" + cityR + "|" + stateR + "|" + zipcodeR);
        System.out.println(streetAddressM + "|" + cityM + "|" + stateM + "|" + zipcodeM);
        System.out.println("_________________________________________________________________");
    }

    public void clickedCancel(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       cusMState.setItems(listStates);
       cusRState.setItems(listStates);
    }
    public void nextRegisterForm()  {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("registerAccountForms.fxml"));
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



    //Getters

    public String getFirstName() {
        return firstName;
    }
    public String getMiddleInitial() {
        return middleInitial;
    }
    public String getLastName() {
        return lastName;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public String getSsn() {
        return ssn;
    }
    public String getStreetAddressR() {
        return streetAddressR;
    }
    public String getCityR() {
        return cityR;
    }
    public String getStateR() {
        return stateR;
    }
    public String getZipcodeR() {
        return zipcodeR;
    }
    public String getStreetAddressM() {
        return streetAddressM;
    }
    public String getCityM() {
        return cityM;
    }

    public String getStateM() {
        return stateM;
    }

    public String getZipcodeM() {
        return zipcodeM;
    }
}
