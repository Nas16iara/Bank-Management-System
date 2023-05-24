package com.ranasia.banking;

public class Customer {
    private int idIncrementer = 1;
    private String cusFirstName;
    private char cusMiddleName; //Optional
    private String cusLastName;
    private String DOB; //Date of Birth
    private String SSN;
    private String email;
    private String phone;
    private String residentialAddress;
    private String mailingAddress;
    private String username;
    private String userPassword;
    private int cusBankIDNo = 0; //Customer ID number which goes up by 1 per new customer

    public Customer(String cusFirstName, char cusMiddleName, String cusLastName, String DOB,
                    String SSN, String email, String phone, String residentialAddress,
                    String mailingAddress, String username, String userPassword) {
        this.cusFirstName = cusFirstName;
        this.cusMiddleName = cusMiddleName;
        this.cusLastName = cusLastName;
        this.DOB = DOB;
        this.SSN = SSN;
        this.email = email;
        this.phone = phone;
        this.residentialAddress = residentialAddress;
        this.mailingAddress = mailingAddress;
        this.username = username;
        this.userPassword = userPassword;
        this.cusBankIDNo =  idIncrementer;
        idIncrementer++;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cusFirstName='" + cusFirstName + '\'' +
                ", cusMiddleName=" + cusMiddleName +
                ", cusLastName='" + cusLastName + '\'' +
                ", DOB='" + DOB + '\'' +
                ", SSN='" + SSN + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", residentialAddress='" + residentialAddress + '\'' +
                ", mailingAddress='" + mailingAddress + '\'' +
                ", username='" + username + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", cusBankIDNo=" + cusBankIDNo +
                '}';
    }
}
