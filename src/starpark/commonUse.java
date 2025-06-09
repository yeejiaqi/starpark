/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starpark;

import java.util.Scanner;

/**
 *
 * @author Asus
 */
public class commonUse {
    public static boolean ansYN(String sentence) {
        Scanner scan = new Scanner(System.in);

        boolean ft = false;
        boolean repeat;
        do {
            repeat = false;
            System.out.print(sentence + "(Y/N) -> ");
            String ans = scan.nextLine();
            ans = ans.toUpperCase();

            if (ans.isEmpty()) {
                System.err.println("Please do not leave it empty.");
                repeat = true;
            } else if ("Y".equals(ans)) {
                ft = true;
            } else if ("N".equals(ans)) {
                ft = false;
            } else {
                System.err.println("Please enter only Y/N.");
                repeat = true;
            }
        } while (repeat);

        return ft;
    }
    
    public static boolean chkRewardIDValidity(Reward[] rewardArray, String rewardID){
        
        boolean rewardIDExists = false;
        
        if (rewardID.matches("G\\d{3}") || rewardID.matches("V\\d{3}")) {

            for (Reward reward : rewardArray){
                if (reward != null && "Available".equals(reward.getRewardStatus()) ){
                    if (reward instanceof Gift){
                        if(((Gift) reward).getGiftID().equals(rewardID)){
                            rewardIDExists = true;
                        }
                    } else if (reward instanceof Voucher){
                        if(((Voucher) reward).getVouID().equals(rewardID)){
                            rewardIDExists = true;
                        }
                    } else{
                        rewardIDExists = false;
                    }
                }
            }

            if (!rewardIDExists) {
                System.err.println("The entered reward ID does not exist.\n");
                return false;
            } else{
                return true;
            }

        } else {
            System.err.println("The reward ID is not in the correct format. EXP: G001\n");
            return false;
        }
        
    }
    
    public static boolean chkAllRewardIDValidity(Reward[] rewardArray, String rewardID){
        
        boolean rewardIDExists = false;
        
        if (rewardID.matches("G\\d{3}") || rewardID.matches("V\\d{3}")) {

            for (Reward reward : rewardArray){
                if (reward != null){
                    if (reward instanceof Gift){
                        if(((Gift) reward).getGiftID().equals(rewardID)){
                            rewardIDExists = true;
                        }
                    } else if (reward instanceof Voucher){
                        if(((Voucher) reward).getVouID().equals(rewardID)){
                            rewardIDExists = true;
                        }
                    } else{
                        rewardIDExists = false;
                    }
                }
            }

            if (!rewardIDExists) {
                System.err.println("The entered reward ID does not exist.\n");
                return false;
            } else{
                return true;
            }

        } else {
            System.err.println("The reward ID is not in the correct format. EXP: G001\n");
            return false;
        }
        
    }
}
