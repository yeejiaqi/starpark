/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starpark;

import java.io.BufferedWriter;
import java.io.IOException;
import java.security.SecureRandom;

/**
 *
 * @author Asus
 */
public class Admin extends User {
   private String userID;
    private String phoneNumber;
    private String email;
    
    //for generating userID purpose:
    private static final String ID_NUMBER = "0123456789";
    private static final int ID_LENGTH = 6;

    public Admin() {
        this("", "", "","","");
    }
    
    public Admin(String userID, String name, String password , String phoneNumber, String email) {
        super(name, password);
        this.userID = userID;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    
    @Override
    public String toString() {
        return "User ID : " + userID + "\n" + super.toString() + "Phone Number:  " + phoneNumber + "\n"
                + "Email: " + email + "\n" ;
    } 
    
    
    //generating userID:
    public void generateID() {
        SecureRandom random = new SecureRandom();
        StringBuilder strBuilder = new StringBuilder();

        for (int i = 0; i < ID_LENGTH; i++) {
            int randomIndex = random.nextInt(ID_NUMBER.length());
            char randomChar = ID_NUMBER.charAt(randomIndex);
            strBuilder.append(randomChar);
        }
        userID = "A" + strBuilder.toString();

    }
    
    
    public static void saveAdmin(Admin adminInfo, BufferedWriter writer) throws IOException {

        String adminDetails = String.format("(%s,%s,%s,%s,%s)",
                adminInfo.getUserID().trim(), adminInfo.getName().trim(), adminInfo.getPassword().trim(),
                adminInfo.getPhoneNumber().trim(), adminInfo.getEmail().trim());

        writer.write(adminDetails);
        writer.newLine();
    }
    
}
