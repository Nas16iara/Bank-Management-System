package com.ranasia.banking;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class SecureData {
    static Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);
    public static String secureData(String myPassword1) {

        var encodedPassword = encoder.encode(myPassword1);
        return encodedPassword;
    }
    public static boolean validateData(String encodedPassword,String enteredPassword){
        // Needs to be used in login database. Will Add it there after reviewing
        var validPassword = encoder.matches(enteredPassword,encodedPassword);
        return validPassword;
    }
    public static void main(String [] args){
        var myPassword = "123-33-2123";
        var notMyPassword = "233-23-2323";
        var encodedPassword = secureData(myPassword);
        System.out.println(validateData(encodedPassword,myPassword));
        System.out.println(validateData(encodedPassword,notMyPassword));
    }
}
