package starpark;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class Membership {
    private String membershipType;
    private double upgradeFee;
    private int pointRequired; 
    
    public Membership(String membershipType, double upgradeFee, int pointRequired){
        //super(currentDate,expiryDate,memberStatus);
        this.membershipType= membershipType;
        this.upgradeFee= upgradeFee;
        this.pointRequired= pointRequired;
        
    }
    
    public Membership(String membershipType){
        this.membershipType = membershipType;
        this.upgradeFee = 0;
        this.pointRequired = 0;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public double getUpgradeFee() {
        return upgradeFee;
    }

    public void setUpgradeFee(double upgradeFee) {
        this.upgradeFee = upgradeFee;
    }

    public int getpointRequired() {
        return pointRequired;
    }

    public void setpointRequired(int pointRequired) {
        this.pointRequired = pointRequired;
    }
    
    public String toString(){
        return "Membership Tier: " + membershipType + "\n" + super.toString();
    }
    
}

