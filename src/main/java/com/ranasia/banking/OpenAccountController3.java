package com.ranasia.banking;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class OpenAccountController3 {
    //Account Type
    Label accountTypeLabel = new Label(); //Creates a new label for next question
    ToggleGroup accountType = new ToggleGroup();
    RadioButton checkingRB = new RadioButton("Checking"); //Creates new Buttons for the next question
    RadioButton savingsRB = new RadioButton("Savings");
    RadioButton creditRB = new RadioButton("Credit Card");
    //Checking
    Label accountOptionLabel = new Label(); //Creates a new label for next question
    ToggleGroup whichCheckingAccount = new ToggleGroup();
    RadioButton checkingBasicRB = new RadioButton("Checking Basic"); //Creates new Buttons for the next question
    RadioButton checkingAdvancedRB = new RadioButton("Checking Advanced");
    //Savings
    ToggleGroup whichSavingsAccount = new ToggleGroup();
    RadioButton savingsBasicRB = new RadioButton("Savings Basic"); //Creates new Buttons for the next question
    RadioButton savingsAdvancedRB = new RadioButton("Savings Advanced");
    //Credit Card
    ToggleGroup whichCreditCardAccount = new ToggleGroup();
    RadioButton creditBasicRB = new RadioButton("Credit Basic"); //Creates new Buttons for the next question
    RadioButton creditAdvancedRB = new RadioButton("Credit Advanced");
    //Income
    Label incomeLabel = new Label(); //Creates a new label for next question
    TextField incomeTF = new TextField();

    @FXML
    private RadioButton yesButton;
    @FXML
    private RadioButton noButton;
    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane accountTypePane;
    @FXML
    private AnchorPane accountOptionsPane;
    @FXML
    private AnchorPane pinkBoxPane;
    @FXML
    private Label errorLabel;


    // Non Components
    private Font labelFont = Font.font("DUBAI", FontWeight.NORMAL, 15);
    private String typeOfAccount;
    private String income;
    private static int accountNO = 1000;
    private int uniqueAccountId = 0;
    private String userAccount;
    static int cusID; //Will be used as a foreign key in account database

    private boolean isAccountTypeSelected = false;
    private boolean isAccountOptionSelected = false;
    private boolean isAnnualIncomeSelected = false;
    @FXML
    private void pressedNext(ActionEvent actionEvent) {
        if(yesButton.isSelected() && !isAccountTypeSelected && !isAccountOptionSelected && !isAnnualIncomeSelected){
            // If the user wants to open up a new account, additional questions are asked
            System.out.println("Yes is Selected");
            newAccountType();
            yesButton.setDisable(true);
            noButton.setDisable(true);
        }
        else if(yesButton.isSelected() && isAccountTypeSelected){
            System.out.println("Account Type Selected");
            typeOfAccount = getAccountType(checkingRB,savingsRB,creditRB);
            getAccountType(typeOfAccount);
            System.out.println(typeOfAccount);
            checkingRB.setDisable(true);
            savingsRB.setDisable(true);
            creditRB.setDisable(true);
            isAccountTypeSelected = false;
        }
        else if(isAccountOptionSelected){
            System.out.println("Account option is selected");
            if(typeOfAccount.equals("Checking")) {
                typeOfAccount = getCheckingOption(checkingBasicRB, checkingAdvancedRB);
                getCheckingOption(typeOfAccount);
                System.out.println(typeOfAccount);
                checkingBasicRB.setDisable(true);
                checkingAdvancedRB.setDisable(true);
            }
            else if(typeOfAccount.equals("Savings")){
                typeOfAccount = getSavingsOption(savingsBasicRB, savingsAdvancedRB);
                getSavingsOption(typeOfAccount);
                System.out.println(typeOfAccount);
                savingsBasicRB.setDisable(true);
                savingsAdvancedRB.setDisable(true);
            }
            else if(typeOfAccount.equals("Credit Card")){
                typeOfAccount = getCreditCardOption(creditBasicRB,creditAdvancedRB);
                getCreditCardOption(typeOfAccount);
                System.out.println(typeOfAccount);
                creditBasicRB.setDisable(true);
                creditAdvancedRB.setDisable(true);
            }
            getAnnualIncome();
            isAccountOptionSelected = false;
        }
        else if(isAnnualIncomeSelected){
            income = incomeTF.getText();
            System.out.println(income);
            createNewAccount(cusID,1,typeOfAccount);
        }
        else {
            System.out.println("Not Selected");
        }
    }
    @FXML
    private void pressedCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    private void newAccountType(){

        // What account would you like to open?
        accountTypeLabel.setTranslateX(3);
        accountTypeLabel.setTranslateY(0);
        accountTypeLabel.setText("Which Account would you like to open?");
        accountTypeLabel.setFont(labelFont);
        accountTypeLabel.setTextFill(Color.rgb(113, 171, 238));

        checkingRB.setToggleGroup(accountType);
        savingsRB.setToggleGroup(accountType);
        creditRB.setToggleGroup(accountType);

        checkingRB.setTranslateX(3);
        checkingRB.setTranslateY(29);

        savingsRB.setTranslateX(114);
        savingsRB.setTranslateY(29);

        creditRB.setTranslateX(209);
        creditRB.setTranslateY(29);

        accountTypePane.getChildren().add(accountTypeLabel);
        accountTypePane.getChildren().add(checkingRB);
        accountTypePane.getChildren().add(savingsRB);
        accountTypePane.getChildren().add(creditRB);
        isAccountTypeSelected = true;
    }
    private String getAccountType(RadioButton checkingRB, RadioButton savingsRB, RadioButton creditCardRB){
        if(checkingRB.isSelected()){
            return "Checking";
        }
        else if (savingsRB.isSelected()){
            return "Savings";
        }
        else if(creditCardRB.isSelected()){
            return "Credit Card";
        }
        return null;
    }
    private void getAccountType(String accountType){
        if(Objects.equals(accountType, "Checking")){
            openChecking();
        }
        else if(Objects.equals(accountType, "Savings")){
            openSavings();
        }
        else if(Objects.equals(accountType, "Credit Card")){
            openCreditCard();
        }
    }
    // Picked Checking
    private void openChecking(){

        accountOptionLabel.setTranslateX(6);
        accountOptionLabel.setTranslateY(0);
        accountOptionLabel.setText("Which Checking account would you like to open up?");
        accountOptionLabel.setFont(labelFont);
        accountOptionLabel.setTextFill(Color.rgb(113, 171, 238));

        checkingBasicRB.setToggleGroup(whichCheckingAccount);
        checkingAdvancedRB.setToggleGroup(whichCheckingAccount);

        checkingBasicRB.setTranslateX(26);
        checkingBasicRB.setTranslateY(34);

        checkingAdvancedRB.setTranslateX(188);
        checkingAdvancedRB.setTranslateY(34);


        accountOptionsPane.getChildren().add(accountOptionLabel);
        accountOptionsPane.getChildren().add(checkingBasicRB);
        accountOptionsPane.getChildren().add(checkingAdvancedRB);
        isAccountOptionSelected = true;
    }
    private String getCheckingOption(RadioButton checkingBasicRB, RadioButton checkingAdvancedRB){
        if(checkingBasicRB.isSelected()){
            return "Checking Basic";
        }
        else if (checkingAdvancedRB.isSelected()){
            return "Checking Advanced";
        }
        return null;
    }
    private void getCheckingOption(String checkingOption){
        if(Objects.equals(checkingOption, "Checking Basic")){
            userAccount = "Checking Basic";
        }
        else if(Objects.equals(checkingOption, "Checking Advanced")){
            userAccount = "Checking Advanced";
        }
    }
    //Picked Savings
    private void openSavings(){
        accountOptionLabel.setTranslateX(6);
        accountOptionLabel.setTranslateY(0);
        accountOptionLabel.setText("Which Savings account would you like to open up?");
        accountOptionLabel.setFont(labelFont);
        accountOptionLabel.setTextFill(Color.rgb(113, 171, 238));

        savingsBasicRB.setToggleGroup(whichSavingsAccount);
        savingsAdvancedRB.setToggleGroup(whichSavingsAccount);

        savingsBasicRB.setTranslateX(26);
        savingsBasicRB.setTranslateY(34);

        savingsAdvancedRB.setTranslateX(188);
        savingsAdvancedRB.setTranslateY(34);


        accountOptionsPane.getChildren().add(accountOptionLabel);
        accountOptionsPane.getChildren().add(savingsBasicRB);
        accountOptionsPane.getChildren().add(savingsAdvancedRB);
        isAccountOptionSelected = true;
    }
    private String getSavingsOption(RadioButton savingsBasicRB, RadioButton savingsAdvancedRB) {
        if(savingsBasicRB.isSelected()){
            return "Savings Basic";
        }
        else if (savingsAdvancedRB.isSelected()){
            return "Savings Advanced";
        }
        return null;
    }
    private void getSavingsOption(String savingOption){
        if(Objects.equals(savingOption, "Savings Basic")){
            userAccount = "Savings Basic";
        }
        else if(Objects.equals(savingOption, "Savings Advanced")){
            userAccount = "Savings Advanced";
        }
    }
    //Picked Credit Card
    private void openCreditCard(){
        accountOptionLabel.setTranslateX(6);
        accountOptionLabel.setTranslateY(0);
        accountOptionLabel.setText("Which Checking account would you like to open up?");
        accountOptionLabel.setFont(labelFont);
        accountOptionLabel.setTextFill(Color.rgb(113, 171, 238));

        creditBasicRB.setToggleGroup(whichCreditCardAccount);
        creditAdvancedRB.setToggleGroup(whichCreditCardAccount);

        creditBasicRB.setTranslateX(26);
        creditBasicRB.setTranslateY(34);

        creditAdvancedRB.setTranslateX(188);
        creditAdvancedRB.setTranslateY(34);


        accountOptionsPane.getChildren().add(accountOptionLabel);
        accountOptionsPane.getChildren().add(creditBasicRB);
        accountOptionsPane.getChildren().add(creditAdvancedRB);
        isAccountOptionSelected = true;
    }
    private String getCreditCardOption(RadioButton creditBasicRB, RadioButton creditAdvancedRB){
        if(creditBasicRB.isSelected()){
            return "Credit Basic";
        }
        else if (creditAdvancedRB.isSelected()){
            return "Credit Advanced";
        }
        return null;
    }
    private void getCreditCardOption(String creditOption){
        if(Objects.equals(creditOption, "Credit Basic")){
            userAccount = "Credit Basic";
        }
        else if(Objects.equals(creditOption, "Credit Advanced")){
            userAccount = "Credit Advanced";
        }
    }
    private void getAnnualIncome(){
        //Create a label and Text Field for income
        incomeLabel.setTranslateX(24);
        incomeLabel.setTranslateY(293);
        incomeLabel.setText("What is your annual income (in usd) ?");
        incomeLabel.setFont(labelFont);
        incomeLabel.setTextFill(Color.rgb(113, 171, 238));

        incomeTF.setBorder(Border.stroke(Color.rgb(113, 171, 238)));
        incomeTF.setTranslateX(286);
        incomeTF.setTranslateY(295);


        pinkBoxPane.getChildren().add(incomeLabel);
        pinkBoxPane.getChildren().add(incomeTF);
        isAnnualIncomeSelected = true;
    }
    public void setCusID(int cusIdEntered){
        // Set the customer id
        cusID = cusIdEntered;
        System.out.println(cusID);
    }
    private void createNewAccount(int customer_id, int employee_id, String account_type){
        System.out.println(customer_id);
        BigDecimal current_Balance = BigDecimal.valueOf(0.00);
        int accountNo =  accountNO + 1;
        String updateIncomeLevel = "UPDATE banking.customer SET income = ? WHERE account_id = ?";
        String newAccountInsert = "INSERT INTO banking.account" +
                "(employee_ID,customer_ID,account_Type,current_Balance,created_date,account_no) " +
                "VALUES (?,?,?,?,?,?)";
        try {
            DatabaseConnection connectionNow = new DatabaseConnection();
            Connection connectDB = connectionNow.getConnection();

            PreparedStatement preparedStatementUpdate= connectDB.prepareStatement(updateIncomeLevel); // Checks if email is already in uses
            preparedStatementUpdate.setString(1,income);
            preparedStatementUpdate.setInt(2,customer_id);
            int row = preparedStatementUpdate.executeUpdate();
            System.out.println(row); //1 means it updated successfully

            // Input Information in database
            PreparedStatement preparedStatementInsert = connectDB.prepareStatement(newAccountInsert);
            preparedStatementInsert.setInt(1,employee_id);
            preparedStatementInsert.setInt(2, customer_id);
            preparedStatementInsert.setString(3, account_type);
            preparedStatementInsert.setBigDecimal(4, current_Balance);
            preparedStatementInsert.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now())); //UNIQUE
            preparedStatementInsert.setInt(6, accountNo);
            int row2 = preparedStatementInsert.executeUpdate();
            System.out.println(row2); //1
            if(row2 == 1 && row == 1){
                errorLabel.setText("INSERT AND UPDATE SUCCESSFUL");
            }
        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
