/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starpark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Asus
 */
public class Gift extends Reward {
    private String giftID;
    private int giftLeft;

    public Gift(String giftID, String rewardTitle, int pointNeed, String memberRequire, String rewardStatus, int giftLeft) {
        super(rewardTitle, pointNeed, memberRequire, rewardStatus);
        this.giftID = giftID;
        this.giftLeft = giftLeft;
    }

    public Gift(String rewardTitle, int pointNeed, String memberRequire, int giftLeft) {
        super(rewardTitle, pointNeed, memberRequire, "Available");
        this.giftID = generateGiftID();
        this.giftLeft = giftLeft;
    }

    public String getGiftID() {
        return giftID;
    }
    
    // generate reward id
    protected String generateGiftID() {
        //change file path reminder
        String rewardText = "C:/Users/Asus/Desktop/OOP/Starpark/Gift.txt";
        int lineCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(rewardText))) {
            while ((br.readLine()) != null) {
                lineCount++;
            }
        } catch (IOException e) {
        }
        
        return "G" + String.format("%03d", lineCount + 1);
    }
    
    public int getGiftLeft() {
        return giftLeft;
    }

    public void setGiftLeft(int giftLeft) {
        this.giftLeft = giftLeft;
    }
        
    @Override
    public String toString(){
        
        String formattedID =  String.format("%-18s %-3s %-5s", "Reward ID", ": ", giftID);
        String formattedGift = String.format("\n%-18s %-3s %-5d", "Gift Left", ": ", giftLeft);
        
        return formattedID + "\n" + super.toString() + formattedGift;
    }
    
    @Override
    public String displayFullReward(){
        
        return super.displayFullReward();
    }
    
    @Override
    public void saveFile(PrintWriter writer) {
        String rewardDetails = String.format("(%s, %s, %d, %s, %s, %d)",
            this.getGiftID(), this.getRewardTitle(), this.getPointNeed(), this.getMemberRequire(),this.getRewardStatus(), this.getGiftLeft());
        writer.println(rewardDetails);
    }
    
}
