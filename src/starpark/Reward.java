/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starpark;

import java.io.PrintWriter;

/**
 *
 * @author Asus
 */
public class Reward {
    private String rewardTitle;
    private int pointNeed;
    private String memberRequire;
    private String rewardStatus;
    
    public Reward (String rewardTitle, int pointNeed, String memberRequire){
        this.rewardTitle = rewardTitle;
        this.pointNeed = pointNeed;
        this.memberRequire = memberRequire;
        this.rewardStatus= "Available";
    }
    
    public Reward (String rewardTitle, int pointNeed, String memberRequire, String rewardStatus){
        this.rewardTitle = rewardTitle;
        this.pointNeed = pointNeed;
        this.memberRequire = memberRequire;
        this.rewardStatus = rewardStatus;
    }

    public String getRewardTitle() {
        return rewardTitle;
    }

    public void setRewardTitle(String rewardTitle) {
        this.rewardTitle = rewardTitle;
    }

    public int getPointNeed() {
        return pointNeed;
    }

    public void setPointNeed(int pointNeed) {
        this.pointNeed = pointNeed;
    }

    public String getMemberRequire() {
        return memberRequire;
    }

    public void setMemberRequire(String memberRequire) {
        this.memberRequire = memberRequire;
    }

    public String getRewardStatus() {
        return rewardStatus;
    }

    public void setRewardStatus(String rewardStatus) {
        this.rewardStatus = rewardStatus;
    }
    
    //for customer view
    public String toString() {
        String formattedReward = String.format("%-18s %-3s %-30s", "Title", ": ", rewardTitle) + "\n" +
                                    String.format("%-18s %-3s %-5d", "Point(s) Needed", ": ", pointNeed) + "\n" +
                                    String.format("%-18s %-3s %-6s", "Membership require", ": ", memberRequire);
        return formattedReward;
    }
    
    //for admin use
    public String displayFullReward() {
        String formattedReward = toString()+ String.format("\n%-18s %-3s %-6s", "Reward Status", ": ", rewardStatus);
        return formattedReward;
    }
    
    public void saveFile(PrintWriter writer) {
        String rewardDetails = String.format("%s,%d,%s,%s", rewardTitle.trim(), pointNeed, memberRequire.trim(), rewardStatus.trim());
        writer.println(rewardDetails);
    }
}
