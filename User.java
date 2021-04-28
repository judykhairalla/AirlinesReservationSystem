
package AirlinesReservationSystem;

import java.io.*;
import java.util.regex.*;

public class User implements Serializable {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private char gender;
    private String phoneNumber;
    private String email;
    private boolean loggedIn;

    public User() {
        this(" ", " ", " ", " ", 0, ' ', " ", " ");
    }

    public User(String userName, String password, String firstName, String lastName, int age, char gender, String phoneNumber, String email) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean setPassword(String password) {
        if (isPasswordValid(password) == true) {
            this.password = password;
            return true;
        } else {
//            System.out.println("Invalid. Your password must be minimum of 8 chars, contains at least one digit, one upper alpha char, special char, does not contain space or tab.");
            return false;
        }
    }

    public boolean isPasswordValid(String attempt) {
        String regExpn = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=~<>])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regExpn);
        Matcher password = pattern.matcher(attempt);
        return password.matches();
    }

    public void setFullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setFIrstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Boolean setPhoneNumber(String phoneNumber) {
        if (isValidPhoneNumber(phoneNumber) == true) {
        this.phoneNumber = phoneNumber;
        return true;
        } else {
//            System.out.println("Phone Number is invalid.");
              return false;
        }
    }
    
    public boolean isValidPhoneNumber(String attempt) {
        String ePattern = "^^(\\+\\d{1,2}\\s?)?0?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
        Pattern pattern = Pattern.compile(ePattern);
        Matcher phoneNumber = pattern.matcher(attempt);
        return phoneNumber.matches();
    }

    public Boolean setEmail(String email) {
        if (isValidEmailAddress(email) == true) {
            this.email = email;
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidEmailAddress(String attempt) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(ePattern);
        Matcher email = pattern.matcher(attempt);
        return email.matches();
    }

    public void setLoggedIn(boolean val) {
        this.loggedIn = val;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public char getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }


}