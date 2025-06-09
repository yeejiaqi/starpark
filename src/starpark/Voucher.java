/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starpark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Asus
 */
public class Voucher extends Reward {
    private String vouID;
    private LocalDate expiryDate;

    public Voucher(String vouID, String rewardTitle, int pointNeed, String memberRequire, String rewardStatus, LocalDate expiryDate) {
        super(rewardTitle, pointNeed, memberRequire, rewardStatus);
        this.vouID = vouID;
        this.expiryDate = expiryDate;
    }
     
    public Voucher(String rewardTitle, int pointNeed, String memberRequire, LocalDate expiryDate) {
        super(rewardTitle, pointNeed, memberRequire, "Available");
        this.vouID = generateVouID();
        this.expiryDate = expiryDate;
    }

    public String getVouID() {
        return vouID;
    }
    
    public String generateVouID(){
        //change file path reminder
        String rewardText = "C:/Users/Asus/Desktop/OOP/Starpark/Voucher.txt";
        int lineCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(rewardText))) {
            while ((br.readLine()) != null) {
                lineCount++;
            }
        } catch (IOException e) {
        }
        
        return "V" + String.format("%03d", lineCount + 1);
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    @Override
    public String toString(){
        
        String formattedID =  String.format("%-18s %-3s %-5s", "Reward ID", ": ", vouID);
        String formattedVoucher = String.format("%-19s %-3s %-10s", "\nExpiry Date", ": ", expiryDate);
        
        return formattedID + "\n" + super.toString() + formattedVoucher;
    }
    
    @Override
    public String displayFullReward(){
        return super.displayFullReward();
    }
    
    @Override
    public void saveFile(PrintWriter writer) {
        // Define the format for the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Format the LocalDate object using the defined formatter
        String formattedDate = expiryDate.format(formatter);
        
        String rewardDetails = String.format("(%s, %s, %d, %s, %s, %s)",
            this.getVouID(),this.getRewardTitle(), this.getPointNeed(), this.getMemberRequire(), this.getRewardStatus(), formattedDate);
        writer.println(rewardDetails);
    }
}
