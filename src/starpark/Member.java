/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starpark;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Asus
 */
public class Member extends User {

     private String userID;
    private String phoneNumber;
    private String email;
    private String tierLevel;
    private int memberPoint;
    private LocalDate expiryDate;
    private double renewalFee;
    private String userStatus;
    private LocalDate registerDate;

    //for generating userID purpose:
    private static final String ID_NUMBER = "0123456789";
    private static final int ID_LENGTH = 6;
    
    

    public Member() {
        this("", "", "","", "", LocalDate.now(),"Bronze", 0, LocalDate.now(),0.0, "Active" );
        //registerDate = LocalDate.now();

    }

    public Member(String userID, String name, String password, String phoneNumber, String email,LocalDate registerDate ,String tierLevel, int memberPoint, LocalDate expiryDate, double renewalFee, String userStatus) {
        super(name, password);
        this.userID = userID;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.tierLevel = tierLevel;
        this.memberPoint = memberPoint;
        this.expiryDate = expiryDate;
        this.renewalFee = renewalFee;
        this.userStatus = userStatus;
        this.registerDate = registerDate;
    }
    

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getTierLevel() {
        return tierLevel;
    }

    public void setTierLevel(String tierLevel) {
        this.tierLevel = tierLevel;
    }

    public int getMemberPoint() {
        return memberPoint;
    }

    public void setMemberPoint(int memberPoint) {
        this.memberPoint = memberPoint;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getRenewalFee() {
        return renewalFee;
    }

    public void setRenewalFee(double renewalFee) {
        this.renewalFee = renewalFee;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
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
        userID = "M" + strBuilder.toString();
    }

    @Override
    public String toString() {
            
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedRegisterDate = registerDate.format(dateFormatter);
        String formattedExpiryDate = expiryDate.format(dateFormatter);
        
        return "User ID : " + userID + "\n" + super.toString() + "Phone Number:  " + phoneNumber + "\n"
                + "Email: " + email + "\n" + "Tier Level: " + tierLevel + "\n" + "Member Point: " + memberPoint + "\n"
                + "Expiry Date: " + formattedExpiryDate + "\n" + "Renewal Fee: " + renewalFee + "\n"
                + "Status: " + userStatus + "\n" + "Register Date: " + formattedRegisterDate + "\n";

    }
    
    public String displayMemberInfo(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedExpiryDate = this.expiryDate.format(dateFormatter);
        
        String formattedMember =    String.format("%-18s %-3s %-30s", "User ID", ": ", this.userID) + "\n" +
                                    String.format("%-18s %-3s %-50s", "Name", ": ", super.getName()) + "\n" +
                                    String.format("%-18s %-3s %-10s", "Tier Level", ": ", this.tierLevel) + "\n" +
                                    String.format("%-18s %-3s %-6s", "Member Point", ": ", this.memberPoint)+ "\n" +
                                    String.format("%-18s %-3s %-6s", "Expiry Date", ": ", formattedExpiryDate)+ "\n" +
                                    String.format("%-18s %-3s %-6s", "Current Status", ": ", this.userStatus);
        
        return formattedMember;
    }
    
    public static void saveMember(Member memberInfo, BufferedWriter writer) throws IOException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedRegisterDate = memberInfo.getRegisterDate().format(dateFormatter);
        String formattedExpiryDate = memberInfo.getExpiryDate().format(dateFormatter);

        String memberDetails = String.format("(%s,%s,%s,%s,%s,%s,%s,%d,%s,%.2f,%s)",
                memberInfo.getUserID().trim(), memberInfo.getName().trim(), memberInfo.getPassword().trim(),
                memberInfo.getPhoneNumber().trim(), memberInfo.getEmail().trim(), formattedRegisterDate,
                memberInfo.getTierLevel().trim(), memberInfo.getMemberPoint(), formattedExpiryDate,
                memberInfo.getRenewalFee(), memberInfo.getUserStatus().trim());

        writer.write(memberDetails);
        writer.newLine();
    }
    
    public void saveWholeFile(PrintWriter writer) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String memberDetails = String.format("(%s,%s,%s,%s,%s,%s,%s,%d,%s,%.2f,%s)",
        userID, super.getName(), super.getPassword(),phoneNumber,email,registerDate.format(dateFormatter),tierLevel,memberPoint,expiryDate.format(dateFormatter),renewalFee,userStatus);

        writer.println(memberDetails);
    }

}
