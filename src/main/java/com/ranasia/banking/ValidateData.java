package com.ranasia.banking;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class ValidateData {
    //Comeback to check if SSN is unique
    private static final Pattern DATA_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static final Pattern DATA_PATTERN_SSN = Pattern.compile("^\\d{3}-\\d{2}-\\d{4}$");

    private static final Pattern DATA_PATTERN_Zipcode = Pattern.compile("^\\d{5}");
    private static final Pattern DATA_PATTERN_PHONE_NUMBER = Pattern.compile("^(\\d{3}-){2}\\d{4}$");
    private static final Pattern DATA_PATTERN_EMAIL = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    public static boolean isValidEmail(String dataStr) {
        return DATA_PATTERN_EMAIL.matcher(dataStr).matches();
    }
    public static boolean isValidPhoneNumber(String dataStr) {
        return DATA_PATTERN_PHONE_NUMBER.matcher(dataStr).matches();
    }
    public static boolean isValidZipCode(String dataStr){
        return DATA_PATTERN_Zipcode.matcher(dataStr).matches();
    }
    public static boolean isValidPassword(String password){
        char[] chars = password.toCharArray();
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;
        for(char c : chars){
            if(Character.isDigit(c)){
                hasDigit = true;
            }
            if(!Character.isDigit(c) && !Character.isLetter(c)){
                hasSpecialCharacter = true;
            }
            if(hasDigit & hasSpecialCharacter){
                return true;
            }
        }
        return false;
    }
    public static boolean isValidFormat(String dataStr,boolean isDate){
        if(isDate && DATA_PATTERN.matcher(dataStr).matches()){
            return true;
        }
        else return (!isDate) && DATA_PATTERN_SSN.matcher(dataStr).matches();
    }
    public static boolean isValidDate(String dateStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public static boolean isLetter(String dataStr){
        return Pattern.compile("^[A-Z]").matcher(dataStr).find();

    }
    public static boolean isOver18(String dateStr){
        return Integer.parseInt(dateStr.substring(0, 4)) <= 2005;
    }
    public static void main(String [] args){
        String str = "2002-08-19";
        String str1 = "000-00-0000";
        String str2 = "password!2";
        String str3 = "404-098-2345";
        String str4 = "Email@email.com";
        boolean isValidPattern = isValidFormat(str,true);
        System.out.println(isValidPattern);

        boolean isValidPattern2 = isValidFormat(str1,false);
        System.out.println(isValidPattern2);

        boolean isValidDate = isValidDate(str);
        System.out.println(isValidDate);

        boolean isOver18 = isOver18(str);
        System.out.println(isOver18);

        boolean isValidPassword = isValidPassword(str2);
        System.out.println(isValidPassword);

        boolean isValidPhoneNumber = isValidPhoneNumber(str3);
        System.out.println(isValidPhoneNumber);

        boolean isValidEmail = isValidEmail(str4);
        System.out.println(isValidEmail);
    }
}
