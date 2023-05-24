package com.ranasia.banking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Get customer Information
        String cusFirstName = "Jake";
        char cusMiddleName = 'D';
        String cusLastName = "Car";
        String DOB = "09-12-1999";
        String SSN = "098-345-2342";
        String residentialAddress = "089 Street Name, City State 00000";
        char sameAddress = 'y';
        String mailingAddress = "029 Street Name, City State 00100";
        String email = "This is a real email hehehehe";
        String username = "UserName9";
        String phone = "+1 000-000-0000";
        String userPassword = "userPassword89*";
        Customer newCustomer;
        if(sameAddress == 'y'){
            mailingAddress = residentialAddress;
        }
        newCustomer = new Customer(cusFirstName,cusMiddleName,cusLastName,DOB,SSN,email,phone,residentialAddress,
                mailingAddress,username,userPassword);
        Scanner sc = new Scanner(System.in);
        System.out.println(newCustomer);
        System.out.println("---Welcome to registration---");

        System.out.println("First Name: ");
        cusFirstName = sc.next();

        System.out.println("Middle Initial: ");
        cusMiddleName = sc.next().charAt(0);

        System.out.println("Last Name: ");
        cusLastName = sc.next();

        System.out.println("Date of Birth (MM-DD-YYYY): ");
        DOB = sc.next();

        System.out.println("SSN/ITIN: ");
        SSN = sc.next();

        System.out.println("Residential Address: ");
        residentialAddress = sc.next();
        sc.nextLine();

        System.out.println("Is your residential address the same as your mailing address?:(y/n)");
        sameAddress = sc.next().charAt(0);

        if(sameAddress == 'y'){
            mailingAddress = residentialAddress;
        }
        else {
            System.out.println("Mailing Address: ");
            mailingAddress = sc.next();
            sc.nextLine();
        }
        System.out.println("Email: ");
        email = sc.next();
        System.out.println("Phone: ");
        phone = sc.next();


        System.out.println("Username: ");
        username = sc.next();

        System.out.println("Password: ");
        userPassword = sc.next();
        newCustomer = new Customer(cusFirstName,cusMiddleName,cusLastName,DOB,SSN,email,phone,residentialAddress,
               mailingAddress,username,userPassword);
        System.out.println(newCustomer);
    }
}