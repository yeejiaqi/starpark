/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starpark;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Asus
 */
public class Redemption {
    private String redeemID;
    private String custID;
    private LocalDate redeemDate;
    
    public Redemption(){
    }

    public Redemption(String redeemID, String custID, LocalDate redeemDate){
        this.redeemID = redeemID;
        this.custID = custID;
        this.redeemDate = redeemDate;
    }
    
    public String getRedeemID() {
        return redeemID;
    }

    public void setRedeemID(String redeemID) {
        this.redeemID = redeemID;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public LocalDate getRedeemDate() {
        return redeemDate;
    }

    public void setRedeemDate(LocalDate redeemDate) {
        this.redeemDate = redeemDate;
    }

    public String toString(){
        // Define the format for the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Format the LocalDate object using the defined formatter
        String formattedDate = redeemDate.format(formatter);
        
        String formattedRedeem = String.format("%-20s %-3s %-5s", "Redeemed Reward ID", ": ", redeemID) + "\n" +
                                    String.format("%-20s %-3s %-5s", "Customer ID", ": ", custID) + "\n" +
                                    String.format("%-20s %-3s %-10s", "Redeemed Date", ": ", formattedDate);
        return formattedRedeem;
    }
    
    public void saveRedeemFile(PrintWriter writer) {
        // Define the format for the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Format the LocalDate object using the defined formatter
        String formattedDate = redeemDate.format(formatter);
    
        String rewardDetails = String.format("(%s,%s,%s)", redeemID.trim(),custID.trim(),formattedDate);
        writer.println(rewardDetails);
    }
}
