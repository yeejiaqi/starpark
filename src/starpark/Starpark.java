/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package starpark;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Starpark {
    
    private static final String ADMIN_FILE_PATH = "C:/Users/Asus/Desktop/OOP/Starpark/Admin.txt";
    private static final String MEMBER_FILE_PATH = "C:/Users/Asus/Desktop/OOP/Starpark/Member.txt";
    private static final String TOP_MANAGE_USERID = "T123456";
    private static final String TOP_MANAGE_PHONENUMBER = "0185201314";
    private static final String TOP_MANAGE_PASSWORD = "snowman";
    
    private static final ArrayList<Admin> adminList = new ArrayList<>();
    private static ArrayList<Member> memberList = new ArrayList<>();
    
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        // Define all the file and array
        // file: to store gift
        String giftText = "C:/Users/Asus/Desktop/OOP/Starpark/Gift.txt";
        //if file does not exist, create a file
        chkFileExist(giftText);
        
        // file: to store voucher
        String voucherText = "C:/Users/Asus/Desktop/OOP/Starpark/Voucher.txt";
        //if file does not exist, create a file
        chkFileExist(voucherText);
        
        // file: to store admin
        chkFileExist(ADMIN_FILE_PATH);
        
        //file: to store member
        chkFileExist(MEMBER_FILE_PATH);
        
        // file: to store redeem infor
        String redeemText = "C:/Users/Asus/Desktop/OOP/Starpark/Redemption.txt";
        File redemptionFile = new File(redeemText);
        chkFileExist(redeemText);
        
        // Read gift from file
        Gift[] giftArray = new Gift[999];
        giftArray = readGiftFile(giftText);
        
        //Read voucher from file
        Voucher[] vouArray = new Voucher[999];
        vouArray = readVouFile(voucherText);
        
        // rewardArray created to store gift and voucher
        Reward[] rewardArray = new Reward[999];
        //Merge giftArray and vouArray
        rewardArray = mergeGiftVoucher(giftArray, vouArray);

        // Read redeemed reward from file
        Redemption[] redeemArray = new Redemption[999];
        redeemArray = readRedeemFile(redeemText);
        
        int option = 0;
        do{

            System.out.println(ANSI_BLUE + " ____  ____   __   ____  ____   __   ____  __ _        __     __    ___   __    __   __ _ " + ANSI_RESET);
            System.out.println(ANSI_BLUE + "/ ___)(_  _) / _\\ (  _ \\(  _ \\ / _\\ (  _ \\(  / )      (  )   / _\\  / __) /  \\  /  \\ (  ( \\" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "\\___ \\  )(  /    \\ )   / ) __//    \\ )   / )  (       / (_/\\/    \\( (_ \\(  O )(  O )/    /" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "(____/ (__) \\_/\\_/(__\\_)(__)  \\_/\\_/(__\\_)(__\\_)      \\____/\\_/\\_/ \\___/ \\__/  \\__/ \\_)__)" + ANSI_RESET);
            System.out.println("\n------------Welcome to Starpark Lagoon------------");

            System.out.println("\n(1)Sign In \n(2)Sign Up as Member \n(3)Terminate");

            boolean validOption = false;
            do {
                try{
                    System.out.print("Enter your choice: ");
                    option = scan.nextInt();
                    switch (option) {
                        case 1:
                            signIn(rewardArray, redeemArray);
                            validOption = true;
                            break;
                        case 2:   
                            System.out.println(ANSI_BLUE + "\n\n--- Starpark Lagoon Sign Up page ---" + ANSI_RESET);
                            System.out.println("Sign up as a member now!\n");
                            signUp();
                            validOption = true;
                            break;
                        case 3:
                            //Terminate
                            thankMsg();
                            validOption = true;
                            return;
                        default:
                            System.out.println(ANSI_RED + "\nInvalid input! please enter again." + ANSI_RESET);
                            validOption = false;
                    }
                }catch (InputMismatchException e) {
                    System.out.println(ANSI_RED + "\nInvalid input! Please enter an integer." + ANSI_RESET);
                    validOption = false;
                    scan.next();
                }
            } while (validOption == false);
        }while(true);
        

    }
    
    public static void thankMsg(){

        System.out.println(ANSI_RED + "  _____     _   _       _        _   _        _  __          __   __    U  ___ u    _   _  " + ANSI_RESET);
        System.out.println(ANSI_GREEN + " |_ \" _|   |'| |'|  U  /\"\\  u   | \\ |\"|      |\"|/ /          \\ \\ / /     \\/\"_ \\/ U |\"|u| | " + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "   | |    /| |_| |\\  \\/ _ \\/   <|  \\| |>     | ' /            \\ V /      | | | |  \\| |\\| | " + ANSI_RESET);
        System.out.println(ANSI_BLUE + "  /| |\\   U|  _  |u  / ___ \\   U| |\\  |u   U/| . \\\\u         U_|\"|_u .-,_| |_| |   | |_| | " + ANSI_RESET);
        System.out.println(ANSI_PURPLE + " u |_|U    |_| |_|  /_/   \\_\\   |_| \\_|      |_|\\_\\            |_|    \\_)-\\___/   <<\\___/  " + ANSI_RESET);
        System.out.println(ANSI_CYAN + " _// \\\\_   //   \\\\   \\\\    >>   ||   \\\\,-. ,-,>> \\\\,-.     .-,//|(_        \\\\    (__) )(   " + ANSI_RESET);
        System.out.println(ANSI_RED + "(__) (__) (_\") (\"_) (__)  (__)  (_\")  (_/   \\.)   (_/       \\_) (__)      (__)       (__)  " + ANSI_RESET);

    }
    
    //for files
    // Create file if file not exist
    public static void chkFileExist(String filePath) {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
                //System.out.println("File created: " + filePath);
            }
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error creating file: " + e.getMessage() + ANSI_RESET);
        }
    }
    
    //Read data from file
    //Read Gift
    public static Gift[] readGiftFile(String giftFile){
        Gift[] giftArray = new Gift[999];
        
        int index = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(giftFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    line = line.substring(1, line.length() - 1);
                    String[] parts = line.split(",");
                    giftArray[index++] = parseGift(parts);
                }
            }
            //System.out.println("The file is empty");
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error reading from gift file: " + e.getMessage() + ANSI_RESET);
        }
        
        return giftArray;
    }
    
    // Seperate each of the part in gift file
    public static Gift parseGift(String[] parts){
        try {
            String giftID = parts[0].trim();
            String rewardTitle = parts[1].trim();
            int pointNeed = Integer.parseInt(parts[2].trim());
            String memberRequire = parts[3].trim();
            String rewardStatus = parts[4].trim(); 
            int giftLeft = Integer.parseInt(parts[5].trim());
            
            return new Gift(giftID, rewardTitle, pointNeed, memberRequire, rewardStatus, giftLeft);
            
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println(ANSI_RED + "Error parsing gift: " + e.getMessage() + ANSI_RESET);
            
            return null;
        }
    }
    
    //Read Voucher
    public static Voucher[] readVouFile(String vouFile){
        Voucher[] vouArray = new Voucher[999];
        int index = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(vouFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    line = line.substring(1, line.length() - 1);
                    String[] parts = line.split(",");
                    vouArray[index++] = parseVou(parts);
                }
            }
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error reading from voucher file: " + e.getMessage() + ANSI_RESET);
        }
        
        return vouArray;
    }
    
    // Seperate each of the part in voucher file
    public static Voucher parseVou(String[] parts){
        try {
            String vouID = parts[0].trim();
            String rewardTitle = parts[1].trim();
            int pointNeed = Integer.parseInt(parts[2].trim());
            String memberRequire = parts[3].trim();
            String rewardStatus = parts[4].trim(); 
            LocalDate expDate = LocalDate.parse(parts[5].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            return new Voucher(vouID, rewardTitle, pointNeed, memberRequire, rewardStatus, expDate);
            
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println(ANSI_RED + "Error parshing voucher: " + e.getMessage() + ANSI_RESET);
            
            return null;
        }
    }
    
    //Read Redemption
    public static Redemption[] readRedeemFile(String redeemFile){
        Redemption[] redeemArray = new Redemption[999];
        int index = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(redeemFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    line = line.substring(1, line.length() - 1);
                    String[] parts = line.split(",");
                    redeemArray[index++] = parseRedeem(parts);
                }
            }
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error reading from redeem file: " + e.getMessage() + ANSI_RESET);
        }
        
        return redeemArray;
    }
    
    // Seperate each of the part in redeem file
    public static Redemption parseRedeem(String[] parts){
        try {
            String redeemID = parts[0].trim();
            String custID = parts[1].trim();
            LocalDate redeemDate = LocalDate.parse(parts[2].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            return new Redemption(redeemID, custID, redeemDate);
            
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println(ANSI_RED + "Error parsing redeem: " + e.getMessage() + ANSI_RESET);
            
            return null;
        }
    }
    
    //To merge gift and voucher
    public static Reward[] mergeGiftVoucher(Gift[] giftArray, Voucher[] vouArray){
        int index = 0;

        Reward[] rewardArray = new Reward[999];
        
        // Add all Gift objects to the merged array
        for (Gift gift : giftArray) {
            if (gift != null) {
                rewardArray[index++] = gift;
            }
        }

        // Add all Voucher objects to the merged array
        for (Voucher voucher : vouArray) {
            if (voucher != null) {
                rewardArray[index++] = voucher;
            }
        }
        
        return rewardArray;

    }
    
    // ********************************************************************
    // YEE JIQ QI : user security module and report module
    // ********************************************************************
    // User security module
    public static void signIn(Reward[] rewardArray, Redemption[] redeemArray){
        //your code here
        Scanner scan = new Scanner(System.in);
        
        System.out.println(ANSI_BLUE + "\n\n--- Starpark Lagoon Sign In page ---" + ANSI_RESET);
        
        boolean continueLoop = true;
        do {
            // Prompt the user to enter their userID or phoneNumber and password
            System.out.print("Enter your userID or phone number: ");
            String userInput = scan.nextLine().toUpperCase();

            System.out.print("Enter your password: ");
            String password = scan.nextLine();

            // Check if the user is an admin
            boolean isAdmin = authenticateAdmin(userInput, password);

            // Check if the user is a member
            boolean isMember = authenticateMember(userInput, password);
            
            boolean isTopManage = authenticateTopManage(userInput,password);

            if (isAdmin) {
                System.out.println("");
                System.out.println(ANSI_GREEN + "Authentication successful for admin. You are now signed in as admin." + ANSI_RESET);
                System.out.println("Your information: ");
                Admin adminInfo = getAdminInfo(userInput, password);
                if (adminInfo != null) {
                    System.out.println(adminInfo.toString());
                    adminPage(rewardArray, redeemArray);
                }
                // Add code for admin actions
                break;
            } else if (isMember) {
                System.out.println("");
                System.out.println(ANSI_GREEN + "Authentication successful for member. You are now signed in." + ANSI_RESET);
                System.out.println("Your information: ");
                Member memberInfo = getMemberInfo(userInput,password);
                if (memberInfo != null) {
                    System.out.println(memberInfo.displayMemberInfo());
                    System.out.println("Welcome, " + memberInfo.getName());
                    int memberOption = 0;
                    boolean validMemberOption = false;
                    do{
                        System.out.println(ANSI_PURPLE + "\n--- Member Menu ---"  + ANSI_RESET);
                        System.out.println(
                                "(1)Renew Membership" + 
                                "\n(2)Reward Redemption" + 
                                "\n(3)View Reward History" + 
                                "\n(4)View Policy" + 
                                "\n(5)Log Out"
                        );

                        do{
                            try{
                                System.out.print("Enter your choice: ");
                                memberOption = scan.nextInt();

                                switch (memberOption) {
                                    case 1:
                                        RenewMembership(memberInfo);
                                        validMemberOption = true;
                                        break;
                                    case 2:
                                        //Reward Redemption;
                                        redeemReward(rewardArray, memberInfo, redeemArray);
                                        validMemberOption = true;
                                        break;
                                    case 3:
                                        // My Reward();
                                        disRewardHistory(rewardArray, redeemArray);
                                        validMemberOption = true;
                                        break;
                                    case 4:
                                        viewPolicy();
                                        System.out.print("Please enter any key to continue...");
                                        String any = scan.nextLine();
                                        scan.nextLine();
                                        validMemberOption = true;
                                        break;
                                    case 5:
                                        // LogOut();
                                        System.out.println("\n");
                                        validMemberOption = true;
                                        return;
                                    default:
                                        System.out.println(ANSI_RED + "\nPlease enter 1 to 5 only." + ANSI_RESET);
                                        validMemberOption = false;
                                        break;
                                }
                            }catch (InputMismatchException e) {
                                System.out.println(ANSI_RED + "\nInvalid input! Please enter an integer." + ANSI_RESET);
                                validMemberOption = false;
                                scan.next();
                            }
                        }while(validMemberOption == false);
                    }while(memberOption != 5);

                }
                // Add code for member actions
                
            } else if (isTopManage){
                System.out.println("");
                System.out.println(ANSI_GREEN + "Authentication successful for top management. You are now signed in." + ANSI_RESET);
                TopManagement(rewardArray, redeemArray);
                break;
            }
            else {
                // Prompt for retry, reset password, or exit
                while (true) {
                    System.out.print(ANSI_RED + "\nInvalid input! please enter again." + ANSI_RESET);
                    System.out.print("\nDo you want to: \n(1) Try again \n(2) Reset password \n(3) Exit \nEnter the option number: ");
                    String choice = scan.nextLine();
                    System.out.println("");

                    if (choice.equals("1")) {
                        break;
                    } else if (choice.equals("2")) {
                        resetPassword(scan); // Pass the Scanner object
                        break;
                    } else if (choice.equals("3")) {
                        continueLoop = false;
                        break;
                    } else {
                        
                        System.out.println(ANSI_RED + "Invalid input. Please enter 1, 2, or 3."  + ANSI_RESET);
                    }
                }

                if (!continueLoop) {
                    break;
                }
            }
        } while (continueLoop);
          
    }
    
    public static boolean authenticateAdmin(String userInput, String password) {
        return authenticateFromFile(userInput, password, ADMIN_FILE_PATH);
    }

    public static boolean authenticateMember(String userInput, String password) {
        return authenticateFromFile(userInput, password, MEMBER_FILE_PATH);
    }
    
    public static boolean authenticateTopManage(String userInput, String password) {
        return (userInput.equals(TOP_MANAGE_USERID) || userInput.equals(TOP_MANAGE_PHONENUMBER) ) && password.equals(TOP_MANAGE_PASSWORD);
    }
    
    private static boolean authenticateFromFile(String userInput, String password, String filePath) {

       
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println(ANSI_RED + "Error: File not found."  + ANSI_RESET);
            return false; // File not found
        }

        try (Scanner testfile = new Scanner(file)) {
            while (testfile.hasNextLine()) {
                String line = testfile.nextLine().trim(); 

                if (line.isEmpty()) {
                    continue; 
                }

                // Check if the file is for admin or member based on line length
                if (filePath.equals(ADMIN_FILE_PATH)) {
                    // Admin file format
                    if (line.startsWith("(") && line.endsWith(")")) {
                        line = line.substring(1, line.length() - 1);
                        String[] parts = line.split(",");

                        if (parts.length >= 5) {
                            String storedUserID = parts[0].trim();
                            String storedPhoneNumber = parts[3].trim();
                            String storedPassword = parts[2].trim();
                            if ((storedUserID.equals(userInput) || storedPhoneNumber.equals(userInput)) && storedPassword.equals(password)) {
                                return true; // Found a match
                            }
                        }
                    }

                } else if (filePath.equals(MEMBER_FILE_PATH)) {
                    // Member file format
                    if (line.startsWith("(") && line.endsWith(")")) {
                        line = line.substring(1, line.length() - 1); // Remove parentheses
                        String[] parts = line.split(",");

                        // Ensure correct number of fields
                        if (parts.length >= 11) {
                            String storedUserID = parts[0].trim();
                            String storedPhoneNumber = parts[3].trim();
                            String storedPassword = parts[2].trim();

                            if ((storedUserID.equals(userInput) || storedPhoneNumber.equals(userInput)) && storedPassword.equals(password)) {
                                return true; // Found a match
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            
            System.out.println(ANSI_RED + "Error: File not found."  + ANSI_RESET);
        }

        return false; // No match found or file not found
    }
    
    public static void resetPassword(Scanner input) {
        
        System.out.println("Reset Password");
        // Check if the user exists
        boolean userExists = true;
        String userInput;
        do{
            System.out.print("Enter userID or phone number: ");
            userInput = input.nextLine().toUpperCase();
            userExists = checkUserExistence(userInput);

            if (userExists) {
                // Code to reset the password
                System.out.print("Enter your new password: ");
                String newPassword = input.nextLine();
                updatePasswordInFile(userInput, newPassword);
                System.out.println(ANSI_GREEN + "Password update successfully. Please sign-in again.\n"  + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "User not found. Please enter a valid userID or phone number.\n"  + ANSI_RESET);
            }
        
        }while(!userExists);
    }
    
    public static boolean checkUserExistence(String userInput) {
        try (Scanner scanner = new Scanner(new File(MEMBER_FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim(); 

                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
                
                
                if (line.startsWith("(") && line.endsWith(")")) {
                    line = line.substring(1, line.length() - 1); // Remove parentheses
                    String[] parts = line.split(",");

                    // Ensure correct number of fields
                    if (parts.length >= 11) {
                        String storedUserID = parts[0].trim();
                        String storedPhoneNumber = parts[3].trim();
                        String storedPassword = parts[2].trim();

                        if (storedUserID.equals(userInput) || storedPhoneNumber.equals(userInput)) {
                            return true; // Found a match
                        }
                    }
                }  
            }
        } catch (FileNotFoundException e) {
            
            System.out.println(ANSI_RED + "Error: File not found."  + ANSI_RESET);
        }

        return false; // User not found
    }
    
    public static void updatePasswordInFile(String userInput, String newPassword) {
        File inputFile = new File(MEMBER_FILE_PATH);
        List<String> lines = new ArrayList<>();

        // Read the contents of the file into memory
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("(") && line.endsWith(")")) {
                    String[] parts = line.substring(1, line.length() - 1).split(",");
                    if (parts.length >= 11) {
                        String storedUserID = parts[0].trim();
                        String storedPhoneNumber = parts[3].trim();
                        if (storedUserID.equals(userInput) || storedPhoneNumber.equals(userInput)) {
                            parts[2] = newPassword; // Update the password
                            line = "(" + String.join(",", parts) + ")";
                        }
                    }
                }
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
                        
            System.out.println(ANSI_RED + "Error: File not found." + ANSI_RESET);
            return;
        }

        // Write the modified contents back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println(ANSI_GREEN + "Password reset successfully."  + ANSI_RESET);
        } catch (IOException e) {
            System.out.print(ANSI_RED + "Error updating password: " + e.getMessage() + ANSI_RESET);
        }
    }
    
    private static Admin parseAdminData(String[] parts) {
        try {
            String userID = parts[0].trim();
            String name = parts[1].trim();
            String password = parts[2].trim();
            String phoneNumber = parts[3].trim();
            String email = parts[4].trim();
            return new Admin(userID, name, password, phoneNumber, email);
        } catch (Exception ex) {
               System.out.print(ANSI_RED + "Error parsing admin data: " + ex.getMessage() + ANSI_RESET);
            return null;
        }
    }
    
    private static Admin getAdminInfo(String userInput, String password) {
        Admin AdminInfo = null;
        try (BufferedReader br = new BufferedReader(new FileReader(ADMIN_FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("(") && line.endsWith(")")) {
                    line = line.substring(1, line.length() - 1);
                }
                String[] parts = line.split(",");
                if (parts.length == 5 && (parts[0].equalsIgnoreCase(userInput) || parts[3].equalsIgnoreCase(userInput)) && parts[2].equals(password)) {
                    AdminInfo = parseAdminData(parts);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AdminInfo;
    }
    
    private static Member parseMemberData(String[] parts) {
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            String userID = parts[0].trim();
            String name = parts[1].trim();
            String password = parts[2].trim();
            String phoneNumber = parts[3].trim();
            String email = parts[4].trim();
            LocalDate registerDate = LocalDate.parse(parts[5].trim(), format);
            String tierLevel = parts[6].trim();
            int memberPoint = Integer.parseInt(parts[7].trim());
            LocalDate expiryDate = LocalDate.parse(parts[8].trim(), format);
            double renewalFee = Double.parseDouble(parts[9].trim());
            String userStatus = parts[10].trim();
            
            return new Member(userID, name, password, phoneNumber, email, registerDate, tierLevel, memberPoint, expiryDate, renewalFee, userStatus);
        } catch (Exception ex) {
                System.out.print(ANSI_RED + "Error parsing admin data: " + ex.getMessage() + ANSI_RESET);
            return null;
        }
    }
    
    private static Member getMemberInfo(String userInput, String password) {
        Member memberInfo = null;
        try (BufferedReader br = new BufferedReader(new FileReader(MEMBER_FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("(") && line.endsWith(")")) {
                    line = line.substring(1, line.length() - 1);
                }
                String[] parts = line.split(",");
                if (parts.length == 11 && (parts[0].equalsIgnoreCase(userInput) || parts[3].equalsIgnoreCase(userInput)) && parts[2].equals(password)) {
                    memberInfo = parseMemberData(parts);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return memberInfo;
    }
    
    public static void adminPage(Reward[] rewardArray, Redemption[] redeemArray){
        Scanner scan = new Scanner(System.in);
        int selectAddEdit = 0;
        do {
            // Display the reward control panel options
            System.out.println(ANSI_PURPLE + "\n\n--- Administrator Menu ---" + ANSI_RESET);
            System.out.println(
                    "1. Add Member\n" +
                    "2. Edit Member\n" +
                    "3. Add Reward\n" +
                    "4. Edit Reward\n" +
                    "5. Log Out\n"
            );

            boolean validAddEdit = false;
            while (!validAddEdit) {
                try {
                    System.out.print("Please enter your selection: ");
                    selectAddEdit = scan.nextInt();
                    switch(selectAddEdit){
                        case 1:
                            // add member
                            System.out.println("\n");
                            System.out.println("Sign Up a member");
                            signUp();
                            validAddEdit = true;
                            break;
                        case 2:
                            // edit member
                            System.out.println("\n");
                            editMemberInfo();
                            validAddEdit = true;
                            break;
                        case 3:
                            // add reward
                            System.out.println("\n");
                            displayAllReward(rewardArray);
                            addReward(rewardArray);
                            validAddEdit = true;
                            break;
                        case 4:
                            // edit reward
                            System.out.println("\n");
                            displayAllReward(rewardArray);
                            editReward(rewardArray);
                            validAddEdit = true;
                            break;
                        case 5:
                            // Exit the inner loop if the selection is valid
                            validAddEdit = true;
                            return;
                        default:
                            System.out.println(ANSI_RED + "\nPlease enter 1 to 7 only." + ANSI_RESET);
                            validAddEdit = false;
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println(ANSI_RED + "\nInvalid input! Please enter an integer." + ANSI_RESET);
                    scan.next();
                }
            }

            System.out.println("\n");
        } while(selectAddEdit != 5); 
        
    }
    
    public static List<Member> loadMemberListFromFile() {
        List<Member> memberList = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(MEMBER_FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("(") && line.endsWith(")")) {
                    line = line.substring(1, line.length() - 1);
                }
                String[] parts = line.split(",");
                if (parts.length == 11) {
                    Member member = parseMemberData(parts);
                    if (member != null) {
                        memberList.add(member);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return memberList;
    }
 
    public static void editMemberInfo() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Edit Member Information Page");
        System.out.print("Enter the userID or phone number you want to edit: ");
        String input = scan.nextLine().toUpperCase();

        // Load members from file
        Member[] memberList = loadMemberListFromFile().toArray(new Member[999]);

        boolean found = false;
        for (Member member : memberList) {
            if (member != null && (member.getUserID().equalsIgnoreCase(input) || member.getPhoneNumber().equalsIgnoreCase(input))) {
                found = true;
                System.out.println(member.displayMemberInfo());

                System.out.print("\nEnter the data type you want to modify  \n(1)Name \n(2)Phone Number \n(3)Email "
                        + "\n(4)Add member point");
                
                boolean validDataType = false;
                do{
                
                    try{
                        System.out.print("\nEnter your choice: ");
                        int dataType = scan.nextInt();
                        scan.nextLine();

                        switch (dataType) {
                            case 1:
                                System.out.print("\nEnter a valid name: ");
                                String newName = scan.nextLine().trim();
                                member.setName(newName);
                                validDataType = true;
                                break;
                            case 2:
                                System.out.print("\nEnter a valid phone number: ");
                                String newPhoneNumber = scan.nextLine().trim();
                                member.setPhoneNumber(newPhoneNumber);
                                validDataType = true;
                                break;
                            case 3:
                                System.out.print("\nEnter a valid email: ");
                                String newEmail = scan.nextLine().trim();
                                member.setEmail(newEmail);
                                validDataType = true;
                                break;
                            case 4:
                                System.out.print("\nEnter a valid member point: ");
                                int pointAdd = Integer.parseInt(scan.nextLine().trim());
                                int newMemberPoint = pointAdd + member.getMemberPoint();
                                member.setMemberPoint(newMemberPoint);
                                validDataType = true;
                                break;
                            default:
                                System.out.println(ANSI_RED + "Invalid data type." + ANSI_RESET);
                                validDataType = false;
                                break;
                        }
                    }catch (InputMismatchException e) {
                        System.out.println(ANSI_RED + "Invalid input! Please enter an integer." + ANSI_RESET);
                        scan.next();
                    }
                    
                }while(validDataType == false);
                

                // Print updated member information
                System.out.println("Updated member information:");
                System.out.println(member.toString());

                // Update filewrite
                updateMemberFile(memberList);

                break;
            }
        }

        if (!found) {
            System.out.print(ANSI_RED + "Member not found." + ANSI_RESET);
        }
    }
    
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.trim().length() >= 3 && name.trim().matches("[a-zA-Z/ ]*");
    }

    public static boolean isValidPassword(String password) {
        return password != null && !password.trim().isEmpty() && password.trim().length() >= 4;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("01\\d{8,9}");

    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return email != null && matcher.matches();
    }
    
    public static boolean isValidMemberPoint(String newValue) {
        try {
            int points = Integer.parseInt(newValue);
            if (points < 0) {
                System.out.print(ANSI_RED + "Member points cannot be negative." + ANSI_RESET);
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.print(ANSI_RED + "Invalid member points. Please enter a valid integer." + ANSI_RESET);
            return false;
        }
    }
    
    public static void updateMemberFile(Member[] memberList){
        try (PrintWriter writer = new PrintWriter(new FileWriter(MEMBER_FILE_PATH))) {
            for (Member m : memberList) {
                if(m!= null){
                    m.saveWholeFile(writer);
                }
            }
            
            System.out.println(ANSI_GREEN + "\nMember file updated successfully."  + ANSI_RESET);
        } catch (IOException e) {
            System.out.print(ANSI_RED + "\nError updating member file: " + e.getMessage() + ANSI_RESET);
        }
    }
    
    public static void writeToFile(Member memberInfo) {
        memberList.add(memberInfo); // Add member to the list

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBER_FILE_PATH, true))) {
            // Loop through the list and write each member's information to the file
            for (Member m : memberList) {
                Member.saveMember(m, writer); // Call saveMember method
            }
            
            System.out.println(ANSI_GREEN + "User data saved successfully."  + ANSI_RESET);
        } catch (IOException ex) {
            System.out.println("Error writing to file: " + ex.getMessage());
        }
    }
    
    public static void writeToFile(Admin adminInfo) {
        adminList.add(adminInfo); // Add member to the list

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE_PATH, true))) {
            // Loop through the list and write each member's information to the file
            for (Admin a : adminList) {
                Admin.saveAdmin(a, writer); // Call saveMember method
            }
            
            System.out.println(ANSI_GREEN + "User data saved successfully."  + ANSI_RESET);
        } catch (IOException ex) {
            System.out.println("Error writing to file: " + ex.getMessage());
        }
    }
      
    public static void signUp(){
        //your code here
        Member memberInfo = new Member();
        Scanner scan = new Scanner(System.in);

        String[] inputfield = {"name", "password", "phone number", "email"};
        int fieldIndex = 0;
        String value;

        while (fieldIndex < inputfield.length) {
            System.out.print("Enter your " + inputfield[fieldIndex] + ": ");
            value = scan.nextLine();

            switch (inputfield[fieldIndex]) {
                case "name":
                    if (!isValidName(value)) {
                        System.out.println(ANSI_RED + "Invalid input. Please enter name with at least 3 characters and do not leave it as blank only!\n" + ANSI_RESET);
                    } else {
                        memberInfo.setName(value);
                        fieldIndex++;
                    }
                    break;
                case "password":
                    if (!isValidPassword(value)) {
                        System.out.println(ANSI_RED + "Invalid input. Please enter stronger password with at least 4 characters and do not leave it as blank only!\n" + ANSI_RESET);
                    } else {
                        memberInfo.setPassword(value);
                        fieldIndex++;
                    }
                    break;
                case "phone number":
                    if (!isValidPhoneNumber(value)) {
                        System.out.println(ANSI_RED + "Invalid phone number format. Please enter on 10 to 11 digits only and do not leave it as blank!\n" + ANSI_RESET);
                    } else {
                        memberInfo.setPhoneNumber(value);
                        fieldIndex++;
                    }
                    break;
                case "email":
                    if (!isValidEmail(value)) {
                        System.out.println(ANSI_RED + "Invalid email format.Please enter a valid email by referring to the sample format given\n" + ANSI_RESET);
                    } else {
                        memberInfo.setEmail(value);
                        fieldIndex++;
                    }
                    break;
            }
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        LocalDate today = LocalDate.now();
        LocalDate exp = today.plusYears(1);
        memberInfo.setExpiryDate(exp);
        
        //----------------------DISPLAY WHEN SUCCESSFUL-------------------------------------
        //Successful registration prompt
        System.out.println("\nSuccessful registration, hereby attached are your information: ");

        //display generated ID:
        memberInfo.generateID();
        
        memberInfo.displayMemberInfo();

        writeToFile(memberInfo);
        
        System.out.print("\nPress any key to Continue ...");
        scan.nextLine();
        
        
    }
    
    public static void TopManagement(Reward[] rewardArray, Redemption[] redeemArray){
        Scanner scan = new Scanner(System.in);
        int selectAddEdit = 0;
            do {
                // Display the reward control panel options
                System.out.println(ANSI_PURPLE + "\n\n--- Top Management Menu ---" + ANSI_RESET);
                System.out.println(
                        "1. Add Admin\n" +
                        "2. Add Reward\n" +
                        "3. Edit Reward\n" +
                        "4. Add Policy\n" +
                        "5. Edit Policy\n" +
                        "6. Delete Policy\n" +
                        "7. View Report\n" +
                        "8. Log Out\n"
                );

                boolean validTMSelection = false;
                while (!validTMSelection) {
                    try {
                        System.out.print("Please enter your selection: ");
                        selectAddEdit = scan.nextInt();
                        switch(selectAddEdit){
                            case 1:
                                // add admin
                                System.out.println("\n");
                                signUpAdmin();
                                validTMSelection = true;
                                break;
                            case 2:
                                // add reward
                                System.out.println("\n");
                                displayAllReward(rewardArray);
                                addReward(rewardArray);
                                validTMSelection = true;
                                break;
                            case 3:
                                // edit reward
                                System.out.println("\n");
                                displayAllReward(rewardArray);
                                editReward(rewardArray);
                                validTMSelection = true;
                                break;
                            case 4:
                                // add policy
                                System.out.println("\n");
                                viewPolicy();
                                addPolicy();
                                validTMSelection = true;
                                break;
                            case 5:
                                // edit policy
                                System.out.println("\n");
                                viewPolicy();
                                editPolicy();
                                validTMSelection = true;
                                break;
                            case 6:
                                // delete policy
                                System.out.println("\n");
                                viewPolicy();
                                deletePolicy();
                                validTMSelection = true;
                                break;
                            case 7:
                                // view report
                                System.out.println("\n");
                                viewReport(rewardArray, redeemArray);
                                validTMSelection = true;
                                break;
                            case 8:
                                // Exit the inner loop if the selection is valid
                                validTMSelection = true;
                                return;
                            default:
                                System.out.println(ANSI_RED + "\nPlease enter 1 to 9 only." + ANSI_RESET);
                                validTMSelection = false;
                                break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println(ANSI_RED + "\nInvalid input! Please enter an integer." + ANSI_RESET);
                        scan.next();
                    }
                }
                
                System.out.println("\n");
            } while(selectAddEdit != 8); 
        
    }
    
    public static void signUpAdmin() {
        Admin adminInfo = new Admin();
        Scanner scan = new Scanner(System.in);

        System.out.println("Sign Up Account for Admin");

        String[] inputfield = {"name", "password", "phone number", "email"};
        int fieldIndex = 0;
        String value;

        while (fieldIndex < inputfield.length) {
            System.out.print("Enter your " + inputfield[fieldIndex] + ": ");
            value = scan.nextLine();

            switch (inputfield[fieldIndex]) {
                case "name":
                    if (!isValidName(value)) {
                        System.out.print(ANSI_RED + "Invalid input. Please enter name with at least 3 characters and do not leave it as blank only!\n" + ANSI_RESET);
                    } else {
                        adminInfo.setName(value);
                        fieldIndex++;
                    }
                    break;
                case "password":
                    if (!isValidPassword(value)) {
                        System.out.print(ANSI_RED + "Invalid input. Please enter stronger password with at least 4 characters and do not leave it as blank only!\n" + ANSI_RESET);
                    } else {
                        adminInfo.setPassword(value);
                        fieldIndex++;
                    }
                    break;
                case "phone number":
                    if (!isValidPhoneNumber(value)) {
                        System.out.print(ANSI_RED + "Invalid phone number format. Please enter on 10 to 11 digits only and do not leave it as blank!\n" + ANSI_RESET);
                    } else {
                        adminInfo.setPhoneNumber(value);
                        fieldIndex++;
                    }
                    break;
                case "email":
                    if (!isValidEmail(value)) {
                        
                        System.out.print(ANSI_RED + "Invalid email format.Please enter a valid email by referring to the sample format given\n" + ANSI_RESET);
                    } else {
                        adminInfo.setEmail(value);
                        fieldIndex++;
                    }
                    break;
            }
        }

        //----------------------DISPLAY WHEN SUCCESSFUL-------------------------------------
        //Successful registration prompt
        System.out.println("\nSuccessful registration, hereby attached are your information: ");

        //display generated ID:
        adminInfo.generateID();
        System.out.println("Your ID: " + adminInfo.getUserID());

        //display name:
        System.out.println("Your name: " + adminInfo.getName());

        //display phone number:
        System.out.println("Your phone number: " + adminInfo.getPhoneNumber());

        //display email:
        System.out.println("Your email: " + adminInfo.getEmail());

        //---------------------------SAVE USER INFO INTO A TEXT FILE--------------------------------
        // Save members' data to the text file
        writeToFile(adminInfo);
        
        System.out.print("\nPlease enter any key to continue...");
        scan.nextLine();
    }
    
    // Report Module
    public static void viewReport(Reward[] rewardArray, Redemption[] redeemArray){
        Scanner scan = new Scanner(System.in);
        
        int selectReport = 0;
        do {
            // Display the reward control panel options
            System.out.println("\n\nReport Type");
            System.out.println(
                    "1. Monthly Report on Tier Level\n" +
                    "2. Gift Redeemed Analysis\n" +
                    "3. Voucher Redeemed Analysis\n" +
                    "4. Reward redeemed Analysis\n" +
                    "5. Go Back\n"
            );

            boolean validReport = false;
            while (!validReport) {
                try {
                    System.out.print("Please enter your selection: ");
                    selectReport = scan.nextInt();
                    switch(selectReport){
                        case 1:
                            System.out.println("");
                            // monthly member analysis
                            membershipReport();
                            validReport = true;
                            break;
                        case 2:
                            // gift analysis
                            System.out.println("");
                            giftAnalysis(rewardArray, redeemArray);
                            validReport = true;
                            break;
                        case 3:
                            // voucher analysis
                            System.out.println("");
                            voucherAnalysis(rewardArray, redeemArray);
                            validReport = true;
                            break;
                        case 4:
                            // reward analysis
                            System.out.println("");
                            rewardAnalysis(rewardArray, redeemArray);
                            validReport = true;
                            break;
                        case 5:
                            return;
                        default:
                            System.out.println(ANSI_RED + "\nPlease enter 1 to 4 only." + ANSI_RESET);
                            validReport = false;
                    }
                } catch (InputMismatchException e) {
                    System.out.println(ANSI_RED + "\nInvalid input! Please enter an integer." + ANSI_RESET);
                    scan.next();
                }
            }

            System.out.println("\n");
        } while(selectReport != 5);
    }
    
    public static void membershipReport() {
        Scanner scan = new Scanner(System.in);
        // Prompt user for the month
        System.out.println("Monthly Report of Distribution of Member Registered for Different Tier Level");
        System.out.println("\nEnter the month for the report \n(01)January \n(02)February \n(03)March \n(04)April \n(05)May \n(06)June "
                + "\n(07)July \n(08)August \n(09)September \n(10)October \n(11)November \n(12)December : ");

        String printMonthByWords = "";
        String choice;
        do {
            System.out.print("Enter the month (01-12): ");
            choice = scan.nextLine().trim();
            switch (choice) {
                case "01":
                    printMonthByWords = "January";
                    break;
                case "02":
                    printMonthByWords = "February";
                    break;
                case "03":
                    printMonthByWords = "March";
                    break;
                case "04":
                    printMonthByWords = "April";
                    break;
                case "05":
                    printMonthByWords = "May";
                    break;
                case "06":
                    printMonthByWords = "June";
                    break;
                case "07":
                    printMonthByWords = "July";
                    break;
                case "08":
                    printMonthByWords = "August";
                    break;
                case "09":
                    printMonthByWords = "September";
                    break;
                case "10":
                    printMonthByWords = "October";
                    break;
                case "11":
                    printMonthByWords = "November";
                    break;
                case "12":
                    printMonthByWords = "December";
                    break;
                default:
                    
                    System.out.print(ANSI_RED + "\nInvalid input. Please enter again (01-12): " + ANSI_RESET);
                    break;
            }
        } while (printMonthByWords.isEmpty());

        // Get from file
        Member[] memberList = new Member[999];
        int index = 0;        
        try (BufferedReader br = new BufferedReader(new FileReader(MEMBER_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    // Check if line starts with parenthesis (optional)
                    if (line.startsWith("(")) {
                        line = line.substring(1, line.length() - 1); // Remove parenthesis if present
                    }
                    String[] parts = line.split(",");
                    memberList[index] = parseMemberData(parts);
                    index++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Process memberList to calculate member class distribution for selected month
        int selectedMonthIndex = Integer.parseInt(choice) - 1; // Months are 0-based
        int bronzeCount = 0;
        int silverCount = 0;
        int goldenCount = 0;
        
        for (Member m : memberList) {
            if (m != null) { // Check for null entries
                LocalDate registerDate = m.getRegisterDate();
                int registeredMonth = registerDate.getMonthValue() - 1; // Months are 0-based
                if (registeredMonth == selectedMonthIndex) {
                    String tierLevel = m.getTierLevel().toLowerCase();
                    switch (tierLevel) {
                        case "bronze":
                            bronzeCount++;
                            break;
                        case "silver":
                            silverCount++;
                            break;
                        case "golden":
                            goldenCount++;
                            break;
                        default:
                            
                            System.out.print(ANSI_RED + "Missing information!" + ANSI_RESET);
                            break;
                    }
                }
            }
        }

        // Calculate number of "x" symbols for each class
        String bronzeReport = calculateXs(bronzeCount) + " (" + bronzeCount + ")";
        String silverReport = calculateXs(silverCount) + " (" + silverCount + ")";
        String goldenReport = calculateXs(goldenCount) + " (" + goldenCount + ")";

        // Print the report header
        System.out.println("\nMember Class Registration Report for " + printMonthByWords);

        // Print the report for each class
        System.out.println("Bronze: " + bronzeReport);
        System.out.println("Silver: " + silverReport);
        System.out.println("Golden: " + goldenReport);
        System.out.println("\nTotal number of member registered for the month: " + (bronzeCount + silverCount + goldenCount));

        System.out.print("\nPlease enter any key to continue...");
        scan.nextLine();
    }
    
    public static String calculateXs(int memberCount) {
        StringBuilder reportString = new StringBuilder();
        for (int i = 0; i < memberCount; i++) {
            reportString.append(ANSI_BLUE + "x " + ANSI_RESET);
        }
        return reportString.toString();
    }
    
    public static void giftAnalysis(Reward[] rewardArray, Redemption[] redeemArray) {
        Scanner scan = new Scanner(System.in);
        int giftCount = 0;

        System.out.println("\n\nReport of Gift Redeemed\n");

        for (Reward reward : rewardArray) {
            if (reward != null && reward instanceof Gift) {
                giftCount = 0;
                System.out.println("Reward ID: " + ((Gift) reward).getGiftID());
                System.out.println("Reward title: " + reward.getRewardTitle());
                
                for (Redemption redeem : redeemArray) {
                    if (redeem != null && ((Gift) reward).getGiftID().equals(redeem.getRedeemID())) {
                        giftCount++;
                    }
                }
                
                System.out.println("Total of gift redeemed: " + giftCount + "\n");
            }
        }
        
        System.out.println("Summary Chart");
        for (Reward reward : rewardArray) {
            if (reward != null && reward instanceof Gift) {
                giftCount = 0;
                System.out.print(((Gift) reward).getGiftID() + ": ");
                
                for (Redemption redeem : redeemArray) {
                    if (redeem != null && ((Gift) reward).getGiftID().equals(redeem.getRedeemID())) {
                        giftCount++;
                    }
                }
                
                for(int i = 0; i < giftCount; i++){
                    System.out.print(ANSI_BLUE + "*" + ANSI_RESET);
                }
                
                System.out.println("");
            }
        }
        
        System.out.print("\nPlease enter any key to continue...");
        scan.nextLine();
    }
    
    public static void voucherAnalysis(Reward[] rewardArray, Redemption[] redeemArray){
        Scanner scan = new Scanner(System.in);
        int voucherCount = 0;
        
        System.out.println("\n\nReport of Voucher Redeemed\n");
        
        for (Reward reward : rewardArray){
            if(reward != null  && reward instanceof Voucher){
                voucherCount = 0;
                System.out.println("Reward ID: " + ((Voucher)reward).getVouID());
                System.out.println("Reward title: " + reward.getRewardTitle());
                
                for (Redemption redeem : redeemArray){
                    if (redeem != null && ((Voucher)reward).getVouID().equals(redeem.getRedeemID())){
                        voucherCount++;
                    }
                }
                
                System.out.println("Total of voucher redeemed: " + voucherCount + "\n");
            }
        }
        
        System.out.println("Summary Chart");
        for (Reward reward : rewardArray) {
            if (reward != null && reward instanceof Voucher) {
                voucherCount = 0;
                System.out.print(((Voucher)reward).getVouID() + ": ");
                
                for (Redemption redeem : redeemArray) {
                    if (redeem != null && ((Voucher)reward).getVouID().equals(redeem.getRedeemID())) {
                        voucherCount++;
                    }
                }
                
                for(int i = 0; i < voucherCount; i++){
                    System.out.print(ANSI_BLUE + "*" + ANSI_RESET);
                }
                
                System.out.println("");
            }
        }
        
        System.out.print("\nPlease enter any key to continue...");
        scan.nextLine();
    }
    
    public static void rewardAnalysis(Reward[] rewardArray, Redemption[] redeemArray) {
        Scanner scan = new Scanner(System.in);
        int giftCount = 0;
        int voucherCount = 0;

        for(Reward reward : rewardArray){
            if(reward != null){
                if (reward instanceof Gift){
                    for(Redemption redeem : redeemArray){
                        if (redeem != null){
                            if(((Gift)reward).getGiftID().equals(redeem.getRedeemID())){
                                giftCount++;
                            }
                        }
                    }
                } else if (reward instanceof Voucher){
                    for(Redemption redeem : redeemArray){
                        if (redeem != null){
                            if(((Voucher)reward).getVouID().equals(redeem.getRedeemID())){
                                voucherCount++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("\n\nSummary Report of Total Gift and Voucher Redeemed\n");
        System.out.println("Total of gift redeemed: " + giftCount);
        System.out.println("Total of voucher redeemed: " + voucherCount);
        
        System.out.println("\nSummary Chart");
        System.out.print("Gift\t: ");
        for(int i = 0; i < giftCount; i++){
            System.out.print(ANSI_BLUE + "*" + ANSI_RESET);
        }
        System.out.println("");
        System.out.print("Voucher\t: ");
        for(int i = 0; i < voucherCount; i++){
            System.out.print(ANSI_BLUE + "*" + ANSI_RESET);
        }
        System.out.println("");
        
        System.out.print("\nPlease enter any key to continue...");
        scan.nextLine();
    }
  
    
    // ********************************************************************
    // Heng Pei Lin : Reward and redemption module
    // ********************************************************************
    // --- Reward Module ---
    // Method to display reward for Admin
    public static void displayAllReward(Reward[] rewardArray){
        boolean isEmpty = true;

        // Check if the array is empty
        for (Reward reward : rewardArray) {
            if (reward != null) {
                isEmpty = false;
                break;
            }
        }

        // If the array is empty, print the message
        if (isEmpty) {
            
            System.out.println("There are no rewards exist.");
            return;
        } else{
            //check is there any expiry
            for(Reward reward : rewardArray){
                if (reward != null){
                    if(reward instanceof Voucher){
                        if (((Voucher) reward).getExpiryDate().isBefore(LocalDate.now())){
                            reward.setRewardStatus("Unavailable");
                            updateVoucherFile(rewardArray);
                        }
                    }
                }
            }
        }

        int rewardCount = 1;
        for (Reward reward : rewardArray) {
            if (reward != null) {
                System.out.println(rewardCount + ".\n" + reward.displayFullReward());
                System.out.println("");
                rewardCount++;
            }
        }
        
    }
    
    public static void addReward(Reward[] rewardArray){
       Scanner scan = new Scanner(System.in);
       
        boolean contAddReward = false;
        int decisionAdd = 0;
        int again = 0;
        do {
            
            if (again > 0){
                displayAllReward(rewardArray);
            }
        
            boolean validDecisionAdd = false;
            do{
                System.out.println("\nWhich type of reward do you wish to add?\n" +
                                             "1. Gift\n" +
                                             "2. Voucher");
                try{
                    
                    System.out.print("Enter your decision (1/2): ");
                    decisionAdd = scan.nextInt();
                
                    switch (decisionAdd) {
                        case 1:
                            validDecisionAdd = true;
                            break;
                        case 2:
                            validDecisionAdd = true;
                            break;
                        default:
                            System.out.println(ANSI_RED + "Please enter only 1 to 2." + ANSI_RESET);
                            break;
                    }
                    
                    scan.nextLine();
                        
                }catch(InputMismatchException e){
                    System.out.println(ANSI_RED + "Invalid number entered!\n" + ANSI_RESET);
                    scan.nextLine();
                }
            
            }while(validDecisionAdd == false);
                 
            //System.out.print(decisionAdd);

            Membership[] memList = {
                new Membership("Bronze"),   
                new Membership("Silver"),   
                new Membership("Golden")        
            };

            boolean reTitle = false;
            boolean rePoint = false;
            boolean reGiftAmt = false;
            boolean reExpiry = false;
            boolean reMember = false;

            String rewardTitle = "";
            int rewardPoint = 0;
            int rewardGiftAmt = 0;
            LocalDate rewardExpiry = LocalDate.now(); // chg from null 
            String memberType = "";

            System.out.println("\n--- Creating new reward ---");

            while(!reTitle){
                System.out.print("Enter Reward title: ");
                String title = scan.nextLine();
                
                if (title.trim().isEmpty()){
                    System.out.println(ANSI_RED + "Reward's title cannot be empty.\n" + ANSI_RESET);
                }else {
                    rewardTitle = title;
                    reTitle = true;
                }
            }

            while(!rePoint) {
                try {
                    System.out.print("Enter point require to purchase Reward: ");
                    int point = scan.nextInt();
                    
                    if (point <= 0){
                        System.out.println(ANSI_RED + "Reward's point could not be less or equal to 0.\n" + ANSI_RESET);
                    }else {
                        rewardPoint = point;
                        rePoint = true;
                    }
                //InputMismatchException
                } catch (InputMismatchException e) {  
                    System.out.println(ANSI_RED + "Invalid point entered!" + ANSI_RESET);
                    scan.nextLine();
                }
            }

            scan.nextLine();
            
            if (decisionAdd == 1){
                while(!reGiftAmt){
                    
                    try {
                        System.out.print("Enter amount of gift available: ");
                        rewardGiftAmt = scan.nextInt();

                        if (rewardGiftAmt < 0){
                            System.out.println(ANSI_RED + "Gift's amount could not be less than 0.\n" + ANSI_RESET);
                        }else {
                            reGiftAmt = true;
                        }
                        
                    //InputMismatchException
                    } catch (InputMismatchException e) {  
                        System.out.println(ANSI_RED + "Invalid amount entered!" + ANSI_RESET);
                        scan.nextLine();
                    }
                }
            }else{
                while (!reExpiry) {
                    System.out.print("Enter expiry date for the Reward (EXP: dd/MM/yyyy): ");
                    String expiry = scan.nextLine();

                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    try {
                        rewardExpiry = LocalDate.parse(expiry, format);

                        if (rewardExpiry.isBefore(LocalDate.now())) {
                            System.out.println(ANSI_RED + "Expiry date cannot be before today's date!" + ANSI_RESET);
                        } else {
                            reExpiry = true;
                        }

                    } catch (DateTimeParseException e) {
                        System.out.println(ANSI_RED + "Invalid date format!" + ANSI_RESET);
                    }
                }
            }

            System.out.println("");
            System.out.printf("%-20s", "Membership Type");
            System.out.println("");
            for (int i = 0; i < memList.length; i++) {
                System.out.println((i+1) + "." + memList[i].getMembershipType() );
            }
            
            while (!reMember) {
                try {
                    System.out.print("Enter Selection (1/2/3): ");
                    int selection = scan.nextInt();
                    scan.nextLine();

                    if (selection < 1 || selection > 3) {
                        System.out.println(ANSI_RED + "Please enter 1 to 3 only." + ANSI_RESET);
                    } else {
                        memberType = memList[selection - 1].getMembershipType();
                        reMember = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println(ANSI_RED + "Invalid input! Please enter a number between 1 and 3." + ANSI_RESET);
                    scan.nextLine(); 
                }
            }

            // to clean screen
            System.out.println("");
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            if(decisionAdd == 1){
                //update array
                Reward gift = new Gift(rewardTitle, rewardPoint, memberType, rewardGiftAmt);
                System.out.println("\n1 Gift created.\n" + gift.toString());
                for (int i = 0; i < rewardArray.length; i++) {
                    if (rewardArray[i] == null) {
                        rewardArray[i] = gift;
                        break;
                    }
                }
                
                //update gift file
                updateGiftFile(rewardArray);
                
            } else{
                //update array
                Reward vou = new Voucher(rewardTitle, rewardPoint, memberType, rewardExpiry);
                System.out.println("\n1 Voucher created.\n" + vou.displayFullReward());
                for (int i = 0; i < rewardArray.length; i++) {
                    if (rewardArray[i] == null) {
                        rewardArray[i] = vou;
                        break;
                    }
                }
                
                //update voucher file
                updateVoucherFile(rewardArray);
            }
            
            
            String sentence = "Do you want to add another reward? ";
            contAddReward = commonUse.ansYN(sentence);
            
            if(contAddReward == false){
                return;
            }else {
                again++;
            }
            
        }while(contAddReward == true);
   }
   
    // update gift added successfully into file
    public static void updateGiftFile(Reward[] rewardArray){
        try (PrintWriter writer = new PrintWriter(new FileWriter("Gift.txt"))) {
            for (Reward reward : rewardArray) {
                //System.out.println("Object is of type: " + reward.getClass().getSimpleName());
                if (reward instanceof Gift){
                    //System.out.println(reward.toString());
                    reward.saveFile(writer);
                }
            }
            
            System.out.println(ANSI_GREEN + "\nGift file updated successfully." + ANSI_RESET);
        } catch (IOException e) {
            
            System.out.println(ANSI_RED + "\nError updating rewards file: " + e.getMessage() + ANSI_RESET);
        }
    }
    
    // update voucher added successfully into file
    public static void updateVoucherFile(Reward[] rewardArray){
        try (PrintWriter writer = new PrintWriter(new FileWriter("Voucher.txt"))) {
            for (Reward reward : rewardArray) {
                if (reward instanceof Voucher){
                    reward.saveFile(writer);
                }
            }
            
            System.out.println(ANSI_GREEN + "\nVoucher file updated successfully." + ANSI_RESET);
        } catch (IOException e) {
            System.out.println(ANSI_RED + "\nError updating voucher file: " + e.getMessage() + ANSI_RESET);
        }
    }
    
    public static void editReward(Reward[] rewardArray){
        
        Scanner scan = new Scanner(System.in);
        String editID;
        
        
        // ask if want to cont on other reward ID
        boolean contOtherReward = true;
        int again = 0;
        do{
        
            if (again > 0){
                displayAllReward(rewardArray);
            }
            
            //chk is it a correct reward id
            boolean validEditID = false;
            do{
            
                System.out.print("Enter the reward ID to edit (EXP: G001): ");
                editID = scan.nextLine();
                
                //editID = editID.substring(0, 1).toUpperCase() + editID.substring(1,4);
                if (editID.isEmpty()) {
                    System.out.println("Please do not leave blank.");
                    validEditID = false;
                    continue;
                } else {
                    editID = editID.isEmpty() ? "" : editID.substring(0, 1).toUpperCase() + (editID.length() >= 4 ? editID.substring(1, 4) : "");
                }


                
                validEditID = commonUse.chkAllRewardIDValidity(rewardArray, editID);
                
            }while(validEditID == false);
            
            // apply selection base on specific editID
            boolean editDecisionRepeat = true;
            do{
                System.out.println("\n\n--- Edit Panel for " + editID + " ---");

                // Display selected Reward
                for(Reward reward : rewardArray){
                    if (reward != null){
                        if(reward instanceof Gift){
                            if(((Gift) reward).getGiftID().equals(editID)){
                                reward.displayFullReward();
                            }
                        }else if(reward instanceof Voucher){
                            if(((Voucher) reward).getVouID().equals(editID)){
                                reward.displayFullReward();
                            }
                        }
                    }
                }

                System.out.println(
                                "Which part do you wish to edit?" + 
                                "\n1. Reward Title" +
                                "\n2. Reward Point Needed"
                                ); 
                
                if ("G".equals(editID.substring(0, 1))){
                    System.out.print("3. Amount Left");
                } else{
                    System.out.print("3. Expiry Date");
                }
                
                System.out.println(
                                "\n4. Reward Member Required" + 
                                "\n5. Reward Status (Available/Disable)"
                                );

                boolean validDecision = true;
                do{
                    try{

                        System.out.print("Enter your decision(1/2/3/4/5,  0 to go back) : ");
                        int editRewardDecision = scan.nextInt();
                        switch(editRewardDecision){
                            case 0:
                                return;
                            case 1:
                                editRewardTitle(rewardArray, editID);
                                validDecision = true;
                                break;
                            case 2:
                                editRewardPointNeed(rewardArray, editID);
                                validDecision = true;
                                break;
                            case 3:
                                if("G".equals(editID.substring(0, 1))){
                                    //edit item left
                                    editRewardGiftLeft(rewardArray, editID);
                                    break;
                                }else{
                                    // edit expiry date
                                    editRewardExpiryDate(rewardArray, editID);
                                    break;
                                }
                            case 4:
                                editRewardMemberRequire(rewardArray, editID);
                                validDecision = true;
                                break;
                            case 5:
                                editRewardStatus(rewardArray, editID);
                                validDecision = true;
                                break;
                            default:
                                System.out.println(ANSI_RED + "Please enter only 0 - 4" + ANSI_RESET);
                                validDecision = false;
                                break;
                        }

                    }catch(InputMismatchException e){
                        System.out.println(ANSI_RED + "Invalid number entered!\n" + ANSI_RESET);
                        scan.nextLine();

                    }
                }while(validDecision = false);

                String sentence = "Do you want to continue edit " + editID + "? ";
                editDecisionRepeat = commonUse.ansYN(sentence);

            }while(editDecisionRepeat == true);
            
            String sentence = "Do you want to select another reward ID to edit? ";
            contOtherReward = commonUse.ansYN(sentence);
            scan.nextLine();
            
            System.out.println(" ");
            
        } while(contOtherReward == true);
        
    }
    
    // Edit reward title
    public static void editRewardTitle(Reward[] rewardArray,String editID){
        Scanner scan = new Scanner(System.in);
        
        for(Reward reward : rewardArray){
            if (reward != null){
                if(reward instanceof Gift){
                    if(((Gift) reward).getGiftID().equals(editID)){
                        System.out.println("\nCurrent title: " + reward.getRewardTitle());
                
                        boolean validTitle = false;
                        while(validTitle == false) {
                            System.out.print("Enter new title: ");
                            String newTitle = scan.nextLine();

                            if (newTitle.trim().isEmpty()){
                                System.out.println(ANSI_RED + "Reward's title cannot be empty.\n" + ANSI_RESET);
                            }else {
                                reward.setRewardTitle(newTitle);
                                System.out.println("New Title: " + reward.getRewardTitle());
                                System.out.println("\n--- Overview on edited reward ---");
                                System.out.println(reward.displayFullReward());
                                updateGiftFile(rewardArray);
                                validTitle = true;
                            }    
                        }
                    }
                }else if(reward instanceof Voucher){
                    if(((Voucher) reward).getVouID().equals(editID)){
                        System.out.println("\nCurrent title: " + reward.getRewardTitle());
                
                        boolean validTitle = false;
                        while(validTitle == false) {
                            System.out.print("Enter new title: ");
                            String newTitle = scan.nextLine();

                            if (newTitle.trim().isEmpty()){
                                System.out.println(ANSI_RED + "Reward's title cannot be empty.\n" + ANSI_RESET);
                            }else {
                                reward.setRewardTitle(newTitle);
                                System.out.println("New Title: " + reward.getRewardTitle());
                                System.out.println("\n--- Overview on edited reward ---");
                                System.out.println(reward.displayFullReward());
                                updateVoucherFile(rewardArray);
                                validTitle = true;
                            }    
                        }
                    }
                }
            }
        }
    }
    
    // Edit point need
    public static void editRewardPointNeed(Reward[] rewardArray,String editID){
        Scanner scan = new Scanner(System.in);
        
        for(Reward reward : rewardArray){
            if (reward != null){
                if(reward instanceof Gift){
                    if(((Gift) reward).getGiftID().equals(editID)){
                        System.out.println("\nCurrent point needed: " + reward.getPointNeed());
                
                        boolean validPoint = false;
                        while(validPoint == false) {
                            try {
                                System.out.print("Enter new point require: ");
                                int newPoint = scan.nextInt();

                                if (newPoint < 0){
                                    System.out.println(ANSI_RED + "Reward's point could not be less than 0.\n" + ANSI_RESET);
                                }else {
                                    reward.setPointNeed(newPoint);
                                    System.out.println("New point Needed: " + reward.getPointNeed());
                                    System.out.println("\n--- Overview on edited reward ---");
                                    System.out.println(reward.displayFullReward());
                                    updateGiftFile(rewardArray);
                                    validPoint = true;
                                }
                            //InputMismatchException
                            } catch (InputMismatchException e) {  
                                System.out.println(ANSI_RED + "Invalid point entered!" + ANSI_RESET);
                                scan.nextLine();
                            }
                        }
                    }
                }else if(reward instanceof Voucher){
                    if(((Voucher) reward).getVouID().equals(editID)){
                        System.out.println("\nCurrent point needed: " + reward.getPointNeed());
                
                        boolean validPoint = false;
                        while(validPoint == false) {
                            try {
                                System.out.print("Enter new point require: ");
                                int newPoint = scan.nextInt();

                                if (newPoint < 0){
                                    System.out.println(ANSI_RED + "Reward's point could not be less than 0.\n" + ANSI_RESET);
                                }else {
                                    reward.setPointNeed(newPoint);
                                    System.out.println("New point Needed: " + reward.getPointNeed());
                                    System.out.println("\n--- Overview on edited reward ---");
                                    System.out.println(reward.displayFullReward());
                                    updateVoucherFile(rewardArray);
                                    validPoint = true;
                                }
                            //InputMismatchException
                            } catch (InputMismatchException e) {  
                                System.out.println(ANSI_RED + "Invalid point entered!" + ANSI_RESET);
                                scan.nextLine();
                            }
                        }
                    }
                }
            }
        }
    }
    
    // edit gift left
    public static void editRewardGiftLeft(Reward[] rewardArray, String editID){
    Scanner scan = new Scanner(System.in);
        
        for(Reward reward : rewardArray){
            if (reward != null){
                if(reward instanceof Gift){
                    if(((Gift) reward).getGiftID().equals(editID)){
                        System.out.println("\nCurrent gift left: " + ((Gift) reward).getGiftLeft());
                
                        boolean validPoint = false;
                        while(validPoint == false) {
                            try {
                                System.out.print("Enter new gift amount: ");
                                int newAmt = scan.nextInt();

                                if (newAmt <= 0){
                                    System.out.println(ANSI_RED + "Gift amount could not be less than or equal to 0.\n" + ANSI_RESET);
                                }else {
                                    ((Gift) reward).setGiftLeft(newAmt);
                                    System.out.println("New gift amount: " + ((Gift) reward).getGiftLeft());
                                    System.out.println("\n--- Overview on edited reward ---");
                                    System.out.println(reward.displayFullReward());
                                    updateGiftFile(rewardArray);
                                    validPoint = true;
                                }
                            //InputMismatchException
                            } catch (InputMismatchException e) {  
                                System.out.println(ANSI_RED + "Invalid point entered!" + ANSI_RESET);
                                scan.nextLine();
                            }
                        }
                    }
                }
            }
        }
    }
    
    // edit expiry date
    public static void editRewardExpiryDate(Reward[] rewardArray, String editID){
        Scanner scan = new Scanner(System.in);
        
        for(Reward reward : rewardArray){
            if (reward != null){
                if(reward instanceof Voucher){
                    if(((Voucher) reward).getVouID().equals(editID)){
                        LocalDate currentExpDate = ((Voucher) reward).getExpiryDate();
                        System.out.println("Current Expiry Date: " + currentExpDate);

                        boolean validDate = false;
                        do {
                            System.out.print("Enter another expiry date (dd/mm/yyyy): ");
                            String newExpDateStr = scan.nextLine();

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                Date expiryDate = sdf.parse(newExpDateStr);
                                if (expiryDate.before(new Date())) {
                                    System.out.println(ANSI_RED + "Expiry date cannot be before today's date!" + ANSI_RESET);
                                } else {
                                    LocalDate newExpDate = LocalDate.parse(newExpDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                    ((Voucher) reward).setExpiryDate(newExpDate);
                                    validDate = true;
                                }

                            } catch (ParseException e) {
                                System.out.println(ANSI_RED + "Invalid date format!" + ANSI_RESET);
                            }
                        } while (!validDate);

                        System.out.println("\n--- Overview on edited reward ---");
                        System.out.println(reward.displayFullReward());
                        updateVoucherFile(rewardArray);
                        return;
                    }
                }
            }
        }
    }
    
    //edit reward member require
    public static void editRewardMemberRequire(Reward[] rewardArray, String editID){
        Scanner scan = new Scanner(System.in);
        
        for(Reward reward : rewardArray){
            if (reward != null){
                int noMemberChg;
                String currentMember = "";

                if (reward instanceof Gift){
                    if(((Gift) reward).getGiftID().equals(editID)){
                        currentMember = reward.getMemberRequire();
                        System.out.print("\nCurrent Membership Require: " + currentMember);
                    
                        // Bronze
                        if (currentMember.equals("Bronze")){
                            System.out.print(
                                    "\nWhich membership require do you want to change to?" + 
                                    "\n1. Silver" + 
                                    "\n2. Golden" 
                            );

                            boolean repeat = false;
                            do{
                                try{

                                    System.out.print("\nEnter your selection (1/2, 0 to go back to previous): ");
                                    noMemberChg = scan.nextInt();

                                    switch(noMemberChg){
                                        case 0: 
                                            return;
                                        case 1: 
                                            reward.setMemberRequire("Silver");
                                            repeat = false;
                                            break;
                                        case 2:
                                            reward.setMemberRequire("Golden");
                                            repeat = false;
                                            break;
                                        default:
                                            System.out.println(ANSI_RED + "Please enter only 1 or 2" + ANSI_RESET);
                                            repeat = true;
                                            break;
                                    }

                                }catch(InputMismatchException e){
                                    System.out.println(ANSI_RED + "Invalid number entered!\n" + ANSI_RESET);
                                    scan.nextLine();

                                }
                            } while(repeat == true);  
                        }

                        // Silver
                        if (currentMember.equals("Silver")){
                            System.out.print(
                                    "\nWhich membership require do you want to change to?" + 
                                    "\n1. Bronze" + 
                                    "\n2. Golden" 
                            );

                            boolean repeat = false;
                            do{
                                try{

                                    System.out.print("\nEnter your selection (1/2, 0 to go back to previous): ");
                                    noMemberChg = scan.nextInt();

                                    switch(noMemberChg){
                                        case 0: 
                                            return;
                                        case 1: 
                                            reward.setMemberRequire("Bronze");
                                            repeat = false;
                                            break;
                                        case 2:
                                            reward.setMemberRequire("Golden");
                                            repeat = false;
                                            break;
                                        default:
                                            System.out.println(ANSI_RED + "Please enter only 1 or 2" + ANSI_RESET);
                                            repeat = true;
                                            break;
                                    }

                                }catch(InputMismatchException e){
                                    System.out.println(ANSI_RED + "Invalid number entered!\n" + ANSI_RESET); 
                                    scan.nextLine();

                                }
                            } while(repeat == true);  
                        }

                        // Golden
                        if (currentMember.equals("Golden")){
                            System.out.print(
                                    "\nWhich membership require do you want to change to?" + 
                                    "\n1. Bronze" + 
                                    "\n2. Silver" 
                            );

                            boolean repeat = false;
                            do{
                                try{

                                    System.out.print("\nEnter your selection (1/2, 0 to go back to previous): ");
                                    noMemberChg = scan.nextInt();

                                    switch(noMemberChg){
                                        case 0: 
                                            return;
                                        case 1: 
                                            reward.setMemberRequire("Bronze");
                                            repeat = false;
                                            break;
                                        case 2:
                                            reward.setMemberRequire("Silver");
                                            repeat = false;
                                            break;
                                        default:
                                            System.out.println(ANSI_RED + "Please enter only 1 or 2" + ANSI_RESET); 
                                            repeat = true;
                                            break;
                                    }

                                }catch(InputMismatchException e){
                                    System.out.println(ANSI_RED + "Invalid number entered!\n" + ANSI_RESET); 
                                    scan.nextLine();

                                }
                            } while(repeat == true);  

                        }
                        
                        System.out.println("\n--- Overview on edited reward ---");
                        System.out.println(reward.displayFullReward());
                        
                        updateGiftFile(rewardArray);
                    }
                }else if (reward instanceof Voucher){
                    if(((Voucher) reward).getVouID().equals(editID)){
                        currentMember = reward.getMemberRequire();
                        System.out.print("\nCurrent Membership Require: " + currentMember);
                                            
                        // Bronze
                        if (currentMember.equals("Bronze")){
                            System.out.print(
                                    "\nWhich membership require do you want to change to?" + 
                                    "\n1. Silver" + 
                                    "\n2. Golden" 
                            );

                            boolean repeat = false;
                            do{
                                try{

                                    System.out.print("\nEnter your selection (1/2, 0 to go back to previous): ");
                                    noMemberChg = scan.nextInt();

                                    switch(noMemberChg){
                                        case 0: 
                                            return;
                                        case 1: 
                                            reward.setMemberRequire("Silver");
                                            repeat = false;
                                            break;
                                        case 2:
                                            reward.setMemberRequire("Golden");
                                            repeat = false;
                                            break;
                                        default:
                                            System.out.println(ANSI_RED + "Please enter only 1 or 2" + ANSI_RESET);
                                            repeat = true;
                                            break;
                                    }

                                }catch(InputMismatchException e){
                                    System.out.println(ANSI_RED + "Invalid number entered!\n" + ANSI_RESET);
                                    scan.nextLine();

                                }
                            } while(repeat == true);  
                        }

                        // Silver
                        if (currentMember.equals("Silver")){
                            System.out.print(
                                    "\nWhich membership require do you want to change to?" + 
                                    "\n1. Bronze" + 
                                    "\n2. Golden" 
                            );

                            boolean repeat = false;
                            do{
                                try{

                                    System.out.print("\nEnter your selection (1/2, 0 to go back to previous): ");
                                    noMemberChg = scan.nextInt();

                                    switch(noMemberChg){
                                        case 0: 
                                            return;
                                        case 1: 
                                            reward.setMemberRequire("Bronze");
                                            repeat = false;
                                            break;
                                        case 2:
                                            reward.setMemberRequire("Golden");
                                            repeat = false;
                                            break;
                                        default:
                                            System.out.println(ANSI_RED + "Please enter only 1 or 2" + ANSI_RESET);
                                            repeat = true;
                                            break;
                                    }

                                }catch(InputMismatchException e){
                                    System.out.println(ANSI_RED + "Invalid number entered!\n" + ANSI_RESET); 
                                    scan.nextLine();

                                }
                            } while(repeat == true);  
                        }

                        // Golden
                        if (currentMember.equals("Golden")){
                            System.out.print(
                                    "\nWhich membership require do you want to change to?" + 
                                    "\n1. Bronze" + 
                                    "\n2. Silver" 
                            );

                            boolean repeat = false;
                            do{
                                try{

                                    System.out.print("\nEnter your selection (1/2, 0 to go back to previous): ");
                                    noMemberChg = scan.nextInt();

                                    switch(noMemberChg){
                                        case 0: 
                                            return;
                                        case 1: 
                                            reward.setMemberRequire("Bronze");
                                            repeat = false;
                                            break;
                                        case 2:
                                            reward.setMemberRequire("Silver");
                                            repeat = false;
                                            break;
                                        default:
                                            System.out.println(ANSI_RED + "Please enter only 1 or 2" + ANSI_RESET); 
                                            repeat = true;
                                            break;
                                    }

                                }catch(InputMismatchException e){
                                    System.out.println(ANSI_RED + "Invalid number entered!\n" + ANSI_RESET); 
                                    scan.nextLine();

                                }
                            } while(repeat == true);  
                        }
                        
                        System.out.println("\n--- Overview on edited reward ---");
                        System.out.println(reward.displayFullReward());
                        
                        updateVoucherFile(rewardArray);
                    }
                }
            }
        }
    }
    
    // Edit reward status
    public static void editRewardStatus(Reward[] rewardArray, String editID){
        
        for(Reward reward : rewardArray){
            if (reward != null){
                String currentStatus = "";
                String newStatus = "";
                if (reward instanceof Gift){
                    if(((Gift) reward).getGiftID().equals(editID)){
                        
                        currentStatus = reward.getRewardStatus();
                        newStatus = "Available".equals(currentStatus) ? "Unavailable" : "Available";
                        String sentence = "Do you want to change the status from " + currentStatus + " to " + newStatus ;
                        boolean agreeChg = commonUse.ansYN(sentence);

                        if (agreeChg) {
                            reward.setRewardStatus(newStatus);
                            System.out.println("Status changed to: " + newStatus);
                        } else {
                            System.out.println("Status \"" + currentStatus + "\" remain.");
                        }

                        System.out.println("\n--- Overview on edited reward ---");
                        System.out.println(reward.displayFullReward());
                        
                        updateGiftFile(rewardArray);
                    }
                }else if (reward instanceof Voucher){
                    if(((Voucher) reward).getVouID().equals(editID)){
                        
                        currentStatus = reward.getRewardStatus();
                        newStatus = "Available".equals(currentStatus) ? "Unavailable" : "Available";
                        String sentence = "Do you want to change the status from " + currentStatus + " to " + newStatus ;
                        boolean agreeChg = commonUse.ansYN(sentence);

                        if (agreeChg) {
                            reward.setRewardStatus(newStatus);
                            System.out.println("Status changed to: " + newStatus);
                        } else {
                            System.out.println("Status \"" + currentStatus + "\" remain.");
                        }

                        System.out.println("\n--- Overview on edited reward ---");
                        System.out.println(reward.displayFullReward());
                        
                        updateVoucherFile(rewardArray);
                    }
                }
            }
        }
    }
    
    // --- Redeem Module ---
    public static void redeemReward(Reward[] rewardArray, Member member, Redemption[] redeemArray) {
        Scanner scan = new Scanner(System.in);

        boolean contRedeem = true;
        while (contRedeem) {
            displayReward(rewardArray, redeemArray);
            System.out.println("***Enter 0 to go back");

            String rewardEnter;
            boolean validRewardEnter = false;

            
            int pointUse = 0;
            do {
                
                System.out.print("Enter reward ID you want to redeem (EXP: G001): ");
                rewardEnter = scan.nextLine().trim();

                if ("0".equals(rewardEnter)) {
                    return;
                }

                rewardEnter = rewardEnter.substring(0, 1).toUpperCase() + rewardEnter.substring(1);

                boolean existReward = commonUse.chkRewardIDValidity(rewardArray, rewardEnter);
                if (!existReward) {
                    System.out.println("Invalid reward ID entered. Please try again.");
                    continue;
                }

                boolean noRedeem = chkRedeemBefore(redeemArray, rewardEnter);
                if (!noRedeem) {
                    System.out.println(ANSI_RED + "This reward has already been redeemed before." + ANSI_RESET);
                    continue;
                }

                String membershipNeeded = "";
                for (Reward reward : rewardArray) {
                    if (reward != null) {
                        if (reward instanceof Gift && ((Gift) reward).getGiftID().equals(rewardEnter)) {
                            membershipNeeded = reward.getMemberRequire();
                            pointUse = reward.getPointNeed();
                        } else if (reward instanceof Voucher && ((Voucher) reward).getVouID().equals(rewardEnter)) {
                            membershipNeeded = reward.getMemberRequire();
                            pointUse = reward.getPointNeed();
                        }
                    }
                }

                String currentMembership = member.getTierLevel();
                if (!isValidMembership(currentMembership, membershipNeeded)) {
                    System.out.println(ANSI_RED + "Membership requirement not achieved." + ANSI_RESET);
                    continue;
                }

                int currentPoint = member.getMemberPoint();
                if (currentPoint < pointUse) {
                    System.out.println(ANSI_RED + "Insufficient points. Please select another reward." + ANSI_RESET);
                    continue;
                }

                validRewardEnter = true;
            } while (!validRewardEnter);

            int newPoint = member.getMemberPoint() - pointUse;
            updatePointToFile(member, newPoint);
            member.setMemberPoint(newPoint);

            System.out.println("Your points have been deducted.");
            System.out.println("Current points: " + member.getMemberPoint());

            Random random = new Random();
            int code = random.nextInt(9000) + 1000; 

            System.out.println("Reward code: " + code);

            Redemption redeem = new Redemption(rewardEnter, member.getUserID(), LocalDate.now());
            System.out.println("\nReward Redeemed\n" + redeem.toString());

            for (int i = 0; i < redeemArray.length; i++) {
                if (redeemArray[i] == null) {
                    redeemArray[i] = redeem;
                    break;
                }
            }

            updateRedeemFile(redeemArray);

            if ("G".equals(rewardEnter.substring(0, 1))) {
                for (Reward reward : rewardArray) {
                    if (reward != null && reward instanceof Gift && ((Gift) reward).getGiftID().equals(rewardEnter)) {
                        ((Gift) reward).setGiftLeft(((Gift) reward).getGiftLeft() - 1);
                        updateGiftFile(rewardArray);
                        break;
                    }
                }
            }

            contRedeem = commonUse.ansYN("Do you want to redeem another reward?");
        }
    }

    private static boolean isValidMembership(String currentMembership, String membershipNeeded) {
        switch (membershipNeeded) {
            case "Bronze":
                return true;
            case "Silver":
                if("Bronze".equalsIgnoreCase(currentMembership)){
                    return false;
                }else{
                    return true;
                }
            case "Gold":
                if ("Gold".equalsIgnoreCase(currentMembership)){
                    return true;
                }else{
                    return false;
                }
            default:
                return false;
        }
    }

    public static void updatePointToFile(Member member, int newPoint) {
        File inputFile = new File(MEMBER_FILE_PATH);
        List<String> lines = new ArrayList<>();

        String point = Integer.toString(newPoint);
        
        // Read the contents of the file into memory
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("(") && line.endsWith(")")) {
                    String[] parts = line.substring(1, line.length() - 1).split(",");
                    if (parts.length >= 11) {
                        String userID = parts[0].trim();
                        if (userID.equals(member.getUserID())) {
                            parts[7] = point; // Update the point
                            line = "(" + String.join(",", parts) + ")";
                        }
                    }
                }
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            return;
        }

        // Write the modified contents back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println(ANSI_GREEN + "\nPoint update successfully\n" + ANSI_RESET);
        } catch (IOException e) {
            System.out.println("Error updating point: " + e.getMessage());
        }
    }
      
    // Display reward for Customer
    public static void displayReward(Reward[] rewardArray, Redemption[] redeemArray){
        
        boolean isRewardEmpty = true;

        // Check if the array is empty
        for (Reward reward : rewardArray) {
            if (reward != null) {
                isRewardEmpty = false;
                break;
            }
        }

        // If the array is empty, print the message
        if (isRewardEmpty) {
            System.out.println("There are no rewards exist.");
            return;
        }else{
                    
            //check is there any expiry
            for(Reward reward : rewardArray){
                if (reward != null){
                    if(reward instanceof Voucher){
                        if (((Voucher) reward).getExpiryDate().isBefore(LocalDate.now())){
                            reward.setRewardStatus("Unavailable");
                            updateVoucherFile(rewardArray);
                        }
                    }
                }
            }
        }
        
        boolean isRedeemEmpty = true;

        // Check if the array is empty
        for (Redemption redeem : redeemArray) {
            if (redeem != null) {
                isRedeemEmpty = false;
            }
        }

        // If the array is empty, print the message
        if (isRedeemEmpty) {
            
            int giftCount = 1;
            System.out.println("\n\n*** Gift ***");
            for(Reward reward : rewardArray){
                if(reward != null && "Available".equals(reward.getRewardStatus())){
                    if(reward instanceof Gift){
                        System.out.println(giftCount + ".\n" + reward.toString());
                        giftCount++;
                        System.out.println("");
                    }
                }
            }
            
            int vouCount = 1;
            System.out.println("\n*** Voucher ***");
            for(Reward reward : rewardArray){
                if(reward != null && "Available".equals(reward.getRewardStatus())){
                    if(reward instanceof Voucher){
                        System.out.println(vouCount + ".\n" + reward.toString());
                        vouCount++;
                        System.out.println("");
                    }
                }
            }
            
        }else{
            // chk if the reward had been redeem before
            int giftCount = 1;
            boolean redeemed=false;
            System.out.println("\n\n*** Gift ***");
            for(Reward reward : rewardArray){
                if(reward != null && "Available".equals(reward.getRewardStatus())){
                    if(reward instanceof Gift){
                        for(Redemption redeem : redeemArray){
                            if(redeem != null){
                                if ( ( ( ( (Gift) reward ).getGiftID() ).equals( redeem.getRedeemID() ) ) ){
                                    redeemed = true;
                                }
                            }
                        }
                        if(redeemed == false){
                            System.out.println(giftCount + ".\n" + reward.toString());
                            giftCount++;
                            System.out.println("");
                        }
                        redeemed = false;
                    }
                }
            }
            
            int vouCount = 1;
            System.out.println("\n\n*** Voucher ***");
            for(Reward reward : rewardArray){
                if(reward != null && "Available".equals(reward.getRewardStatus())){
                    if(reward instanceof Voucher){
                        for(Redemption redeem : redeemArray){
                            if(redeem != null){
                                if ( ( ( ( (Voucher) reward ).getVouID() ).equals( redeem.getRedeemID() ) ) ){
                                    redeemed = true;
                                }
                            }
                        }
                        if(redeemed == false){
                            System.out.println(vouCount + ".\n" + reward.toString());
                            vouCount++;
                            System.out.println("");
                        }
                        redeemed = false;
                    }
                }
            }
        }
        
        System.out.println("Please be aware that each gift and voucher can only be redeem once.");
    }
    
    public static boolean chkRedeemBefore(Redemption[] redeemArray, String rewardEnter){
        
        boolean isRedeemEmpty = true;

        for(Redemption redeem : redeemArray){
            if(redeem != null){
                isRedeemEmpty = false;
            }
        }
        
        if(isRedeemEmpty){
            return true;
        }
        
        boolean isExist = false;
        if(!isRedeemEmpty){
            for(Redemption redeem : redeemArray){
                if(redeem != null){
                    if (redeem.getRedeemID().equals(rewardEnter)){
                        isExist = true;
                        break;
                    }
                }
            }
        }
        
        if(isExist == true){
            return false;
        }else{
            return true;
        }
    }
    
    // update redeemed reward into file
    public static void updateRedeemFile(Redemption[] redeemArray){
        try (PrintWriter writer = new PrintWriter(new FileWriter("Redemption.txt"))) {
            for (Redemption redeem : redeemArray) {
                if(redeem != null){
                    redeem.saveRedeemFile(writer);
                }
            }
            
            System.out.println(ANSI_GREEN + "\nRedemption file updated successfully." + ANSI_RESET);
        } catch (IOException e) {
            System.out.println(ANSI_RED + "\nError updating redemption file: " + e.getMessage() + ANSI_RESET); 
        }
    }
    
    public static void disRewardHistory(Reward[] rewardArray, Redemption[] redeemArray){
        Scanner scan = new Scanner(System.in);
        System.out.println("\nRedeem History");
        
        boolean isRedeemEmpty = true;

        for(Redemption redeem : redeemArray){
            if(redeem != null){
                isRedeemEmpty = false;
            }
        }
        
        if(isRedeemEmpty){
            System.out.println("You does not have any reward redeemed yet.");
        }else{
            int redeemCount = 1;
            for(Redemption redeem : redeemArray){
                if (redeem != null){
                    for(Reward reward : rewardArray){
                        if(reward instanceof Gift){
                            if ((redeem.getRedeemID()).equals(((Gift) reward).getGiftID())){
                                System.out.println(redeemCount + ".");
                                System.out.println(String.format("%-20s %-3s %-5s", "Reward Title", ":", reward.getRewardTitle()));
                                System.out.println(redeem.toString());
                                System.out.println("");
                                redeemCount ++;
                            }
                        }else if(reward instanceof Voucher){
                            if ((redeem.getRedeemID()).equals(((Voucher) reward).getVouID())){
                                System.out.println(redeemCount + ".");
                                System.out.println(String.format("%-20s %-3s %-5s", "Reward Title", ":", reward.getRewardTitle()));
                                System.out.println(redeem.toString());
                                System.out.println("");
                                redeemCount ++;
                            }
                        }
                    }
                }
            }
        }
        
        System.out.print("\nPlease enter any key to continue:");
        scan.nextLine();
    }
    
    
    // ********************************************************************
    // Clarist : Renew membership and policy module
    // ********************************************************************
    // --- Membership Maintainance Module ---
    public static void RenewMembership(Member member){  

        Scanner scan = new Scanner(System.in);
        
        String cMemberType = member.getTierLevel();
        String cMemberStatus = member.getUserStatus();
        LocalDate expDate = member.getExpiryDate();
        LocalDate currentDate = LocalDate.now();
        long daysBetween;

        System.out.println("\n\nCurrent Tier Level :"+ cMemberType);
        System.out.println("Expiry Date :"+ expDate);
        System.out.println("Member Status :"+ cMemberStatus);
        
        // check date is within 30 days
        daysBetween = ChronoUnit.DAYS.between(currentDate, expDate);
        //System.out.println("Days between :" + daysBetween);
        System.out.println("");
        
        boolean ableRenew = false;
        if (daysBetween < 0){
            System.out.println(ANSI_RED + "Your membership had expired." + ANSI_RESET);
            member.setTierLevel("Bronze");
            member.setUserStatus("Inactive");
            member.setMemberPoint(0);
            ableRenew = true;
        }else if (daysBetween <= 30){
            System.out.println("You need to renew your membership as soon.");
            ableRenew = true;
        }else {
            System.out.println("You may renew after " + (daysBetween - 30) + " days.");
            System.out.print("Please enter any key to continue...");
            scan.nextLine();
        }

        if(ableRenew == true){
            String membershipType = member.getTierLevel();
            int memberPoint = member.getMemberPoint();
            checkSelection(membershipType, memberPoint, member);
        }else{
            return;
        }
    }
    
    public static void checkSelection(String membershipType, int memberPoint, Member member) {
        Scanner scanner = new Scanner(System.in);
        boolean validSelection = false;
        String[] options = checkMembershipType(membershipType, memberPoint);

        printOptions(options);

        // Continuously prompt for a valid selection
        do {
            try {
                System.out.print("Enter your choice : ");
                int selection = Integer.parseInt(scanner.nextLine()); // Read and parse the input

                // Check if the selection is within the valid range
                if (selection < 1 || selection > options.length) {
                    System.out.println(ANSI_RED + "\nInvalid selection. Please choose a valid option between 1 and " + options.length + "." + ANSI_RESET); 
                } else {

                    executeMembershipOption(selection, member);
                    validSelection = true; // Set validSelection to true to break the loop
                }
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "\nInvalid input. Please enter a number." + ANSI_RESET); 
            }
        } while (validSelection == false); // Continue the loop if the selection was not valid
    }
    
    // Retrieve options based on membership type and points
    public static String[] checkMembershipType(String membershipType, int memberPoint) {
        if ("Silver".equalsIgnoreCase(membershipType)) {
            if (memberPoint > 1000) {
                return new String[]{"Renew Membership", "Upgrade to Higher Membership (Golden tier)", "Downgrade Membership (Bronze Tier)"};
            } else {
                return new String[]{"Renew Membership", "Downgrade Membership (Bronze Tier)"};
            }
        }else if ("Bronze".equalsIgnoreCase(membershipType)) {
            if (memberPoint >= 301 && memberPoint <= 1000) {
                return new String[]{"Renew membership", "Upgrade to Higher Membership (Silver Tier)"};
            } else {
                return new String[]{"Renew Membership"};
            } 
        }else {
            return new String[]{"Renew Membership", "Downgrade Membership (Silver Tier)"};  //Golden tier option
        }
    }
    
    // Print options based on  different tier
    public static void printOptions(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }
    
    // Execute user selection
    public static void executeMembershipOption(int selection, Member member) {
        String tier = member.getTierLevel();
        int point = member.getMemberPoint();
        switch (tier) {
            case "Bronze":
                if(point >= 301){
                    if (selection == 1) {
                        renewMembership(member);
                    } else if (selection == 2){
                        upgradeMembership(member);
                    }
                }else{
                    if (selection == 1) {
                    renewMembership(member);
                    }
                }   
                return;
            case "Silver":
                if (point > 1000 ){
                    if (selection == 1) {
                        renewMembership(member);
                    } else if (selection == 2) {
                        upgradeMembership(member);
                    } else if (selection == 3) {
                        downgradeMembership(member);
                    }
                }else {
                    if (selection == 1) {
                        renewMembership(member);
                    } else if (selection == 2) {
                        downgradeMembership(member);
                    }
                } 
                return;
            case "Golden":
                if (selection == 1) {
                    renewMembership(member);
                } else if (selection == 2) {
                    downgradeMembership(member);
                }
                return;
            default:
                System.out.println(ANSI_RED + "Invalid tier level. No actions can be performed." + ANSI_RESET); 
                break;
        }
    }
    
    public static void renewMembership(Member member) {
        
        Scanner scan = new Scanner(System.in);
        
        // calculate latest expiry date
        LocalDate currentExpiryDate = member.getExpiryDate();
        LocalDate renewExpDate = currentExpiryDate.plusYears(1);

        String currentTL = member.getTierLevel();
        
        double fee = 0;
        switch (currentTL) {
            case "Bronze":
                fee = getMembershipByTier(currentTL).getUpgradeFee();
                break;
            case "Silver":
                fee = getMembershipByTier(currentTL).getUpgradeFee();
                break;
            case "Golden":
                System.out.println(ANSI_RED + "Highest tier reached. Cannot upgrade further." + ANSI_RESET); 
                return;
            default:
                System.out.println(ANSI_RED + "Invalid tier level." + ANSI_RESET); 
                return;
        }

        if (payment(fee, currentTL)) {
            member.setTierLevel(currentTL);
            member.setExpiryDate(renewExpDate);
            member.setUserStatus("Active");
            System.out.println(" ");
            System.out.println(ANSI_GREEN + "Renew successfully." + ANSI_RESET); 
            System.out.println("Current Tier Level: " + currentTL);
            System.out.println("Expiry Date: " + member.getExpiryDate());        
            System.out.println("Member Status: " + member.getUserStatus());
            System.out.println("");
            
            updateMembershipToFile(member, member.getTierLevel(), renewExpDate, fee, "Active");

        } else {
            System.out.println(" ");
            System.out.println(ANSI_RED + "Renew cancelled.\n" + ANSI_RESET);
            System.out.println("Current Tier Level: " +currentTL );
            System.out.println("Expiry Date: " +currentExpiryDate );
            System.out.println("Member Status: " + member.getUserStatus());
            System.out.println("\n");

        }
        
        System.out.print("Please enter any key to continue...");
        scan.nextLine();
        
    }
    
    public static void upgradeMembership(Member member) {
        
        Scanner scan = new Scanner(System.in); // Create a single Scanner instance for reuse
        
        LocalDate currentExpiryDate = member.getExpiryDate();
        LocalDate renewExpDate = currentExpiryDate.plusYears(1);
        
        String currentTL = member.getTierLevel();

        String newTL;
        double fee = 0;

        switch (currentTL) {
            case "Bronze":
                newTL = "Silver";
                fee = getMembershipByTier(newTL).getUpgradeFee();
                break;
            case "Silver":
                newTL = "Golden";
                fee = getMembershipByTier(newTL).getUpgradeFee();
                break;
            case "Golden":
                System.out.println(ANSI_RED + "Highest tier reached. Cannot upgrade further." + ANSI_RESET);
                return;
            default:
                System.out.println(ANSI_RED + "Invalid tier level." + ANSI_RESET);
                return;
        }

        if (payment(fee, newTL)) {
            member.setTierLevel(newTL);
            member.setExpiryDate(renewExpDate);
            member.setUserStatus("Active");
            System.out.println(" ");
            System.out.println(ANSI_GREEN + "Upgrade Successfully." + ANSI_RESET); 
            System.out.println("Current Tier Level: " + member.getTierLevel());
            System.out.println("Expiry Date: " + member.getExpiryDate());
            System.out.println("Member Status: " + member.getUserStatus());
            System.out.println("");
            
            updateMembershipToFile(member, member.getTierLevel(), renewExpDate, fee, "Active");
            
        } else {
            System.out.println(" ");
            System.out.println(ANSI_RED + "Upgrade Cancelled.\n" + ANSI_RESET);
            System.out.println("Current Tier Level: " +currentTL );
            System.out.println("Expiry Date: " +currentExpiryDate );
            System.out.println("Member Status: " + member.getUserStatus());
            System.out.println("\n");

        }
        
        System.out.print("Please enter any key to continue...");
        scan.nextLine();
          
    }
    
    public static void downgradeMembership(Member member) {
        
        Scanner scan = new Scanner(System.in);
        
        LocalDate currentExpiryDate = member.getExpiryDate();
        LocalDate renewExpDate = currentExpiryDate.plusYears(1);
        
        String currentTL = member.getTierLevel();

        String newTL;
        double fee = 0.00;
        switch (currentTL) {
            case "Golden":
                newTL = "Silver";
                break;
            case "Silver":
                newTL = "Bronze";
                break;
            case "Bronze":
                System.out.println(ANSI_RED + "Lowest tier reached. Cannot downgrade further." + ANSI_RESET);
                return;
            default:
                System.out.println(ANSI_RED + "Invalid tier level." + ANSI_RESET);
                return;
        }

        if (payment(fee, newTL)) {
            member.setTierLevel(newTL);
            member.setExpiryDate(renewExpDate);
            member.setUserStatus("Active");
            System.out.println(" ");
            System.out.println(ANSI_GREEN + "Downgrade Successfully." + ANSI_RESET); 
            System.out.println("Current Tier Level: " + member.getTierLevel());
            System.out.println("Expiry Date: " + member.getExpiryDate());
            System.out.println("Member Status: " + member.getUserStatus());
            System.out.println("");
            
            updateMembershipToFile(member, member.getTierLevel(), renewExpDate, fee, "Active");

        } else {
            System.out.println(" ");
            System.out.println(ANSI_RED + "Downgrade Cancelled.\n" + ANSI_RESET); 
            System.out.println("Current Tier Level: " +currentTL );
            System.out.println("Expiry Date: " + currentExpiryDate );
            System.out.println("Member Status: " + member.getUserStatus());
            System.out.println("\n");
        }
        
        System.out.print("Please enter any key to continue...");
        scan.nextLine();
       
    }
    
    public static Membership getMembershipByTier(String membershipType) {
        
        Membership[] memList = {
            new Membership("Bronze", 0.00, 0), 
            new Membership("Silver", 50.00, 301),
            new Membership("Golden", 100.00, 1001)
        };
        
        for (Membership m : memList) {
            if (m.getMembershipType().equalsIgnoreCase(membershipType)) {
                return m;
            }
        }
        return null; // Return null if tier not found
    }
    
    public static boolean payment(double fee, String tierLevel) {
        Scanner scan = new Scanner(System.in);
        String confirmPay;
        do {
            System.out.println("\nYou are required to pay RM" + String.format("%.2f", fee) + " to renew as a " + tierLevel + " member.");
            System.out.print("Do you want to proceed? (Y/N) : ");
            confirmPay = scan.nextLine();
            confirmPay = confirmPay.toUpperCase();
            if (!"Y".equals(confirmPay) && !"N".equals(confirmPay)) {
                System.out.println(ANSI_RED + "Please enter only \"Y\" or \"N\"!" + ANSI_RESET);
            }
        } while (!"Y".equals(confirmPay) && !"N".equals(confirmPay));

        //scan.next();
        return "Y".equals(confirmPay);
    }
    
    public static void updateMembershipToFile(Member member, String membership, LocalDate expiryDate, double renewFee, String status) {
        File inputFile = new File(MEMBER_FILE_PATH);
        List<String> lines = new ArrayList<>();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String exp = expiryDate.format(formatter);
        
        String fee = String.format("%.2f", renewFee);
        
        // Read the contents of the file into memory
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("(") && line.endsWith(")")) {
                    String[] parts = line.substring(1, line.length() - 1).split(",");
                    if (parts.length >= 11) {
                        String userID = parts[0].trim();
                        if (userID.equals(member.getUserID())) {
                            parts[6] = membership;
                            parts[8] = exp;
                            parts[9] = fee;
                            parts[10] = status;
                            line = "(" + String.join(",", parts) + ")";
                        }
                    }
                }
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            
            System.out.println(ANSI_RED + "Error: File not found." + ANSI_RESET);
            return;
        }

        // Write the modified contents back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.print(ANSI_GREEN + "Membership update successfully\n" + ANSI_RESET);
        } catch (IOException e) {
            
            System.out.println(ANSI_RED + "Error reading membership: " + e.getMessage() + ANSI_RESET);
        }
    }

    
    // --- Policy Module ---
    public static void addPolicy(){
        Scanner scan = new Scanner(System.in);  // Create a Scanner object for input
        
        // Specify the path to the file
        // change path reminder
        String policyText = "C:/Users/Asus/Desktop/OOP/Starpark/Policy.txt";
        File file = new File(policyText);
        //try catch
        int numberOfPolicies = 0;
        try{
            System.out.print("Enter the number of policies you want to store: ");
             // Read user input for the number of policies
            numberOfPolicies = scan.nextInt();

        }catch (InputMismatchException e) {
                System.err.println(ANSI_RED +"\nInvalid input! Please enter an integer."+ ANSI_RESET); 
        }
        scan.nextLine();
        
        String[] policyArray = new String[numberOfPolicies];  // Declare the String array
        
        //try and catch cannot be empty
        System.out.println("Enter " + numberOfPolicies + " policy content(s):");
        for (int i = 0; i < numberOfPolicies; i++) {
            System.out.print((i + 1) + ". ");
            
            try{
                
                policyArray[i] = scan.nextLine();  // Store each user input in the array
            
            }catch (IllegalArgumentException e) {
                
                System.err.println(ANSI_RED +"\nInput cannot be empty. Please try again."+ ANSI_RESET); 
                
            }
        
        }

        // Display the stored policies
        System.out.println("\nYou entered these policies:");
        for (String policy : policyArray) {
            System.out.println(policy);
        }

        // Try to append to the file instead of overwriting it
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {  // Set true for append mode
            for (String policy : policyArray) {
                if (policy != null) {
                    writer.println(policy);  // Write each policy to the file followed by a newline
                }
            }
            
            System.out.println(ANSI_GREEN + "Policy content updated successfully." + ANSI_RESET);
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error updating policy file: " + e.getMessage() + ANSI_RESET);
        }
        
        System.out.println("");
        System.out.print("Enter anything to go back: ");
        scan.nextLine();
        
    }
    
    public static void viewPolicy(){
        Scanner scan = new Scanner(System.in);
        
        //change file path reminder
        String policyText = "C:/Users/Asus/Desktop/OOP/Starpark/Policy.txt";
        File file = new File(policyText);

        if (!file.exists()) {
            System.out.println(ANSI_RED + "The file does not exist." + ANSI_RESET);
            return;
        }

        System.out.println();
        System.out.println(" --- Starpark Lagoon Membership Policy ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String policy;
            int lineNumber = 1; // Initialize a counter for the line number

            // Read file line by line
            while ((policy = reader.readLine()) != null) {
                System.out.println(lineNumber + ". " + policy); // Print each line prefixed by its line number
                lineNumber++; // Increment the line number with each loop iteration
            }
            System.out.println("\nFor any inquiries or further assistance, please contact our customer support team through the following channels:");
            System.out.println("Email: support@starparklagoon.com");
            System.out.println("Phone Number: +60-3-1234-5678");
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error reading file: " + e.getMessage() + ANSI_RESET);
        }
        
        System.out.println("");

    }
    
    public static void editPolicy() {
        Scanner scan = new Scanner(System.in);
        // Specify the path to the file
        String policyText = "C:/Users/Asus/Desktop/OOP/Starpark/Policy.txt";
        File file = new File(policyText);

        if (!file.exists()) {
            
            System.out.println(ANSI_RED + "The file does not exist." + ANSI_RESET);
            return;
        }

        List<String> policyArray = new ArrayList<>();

        // Reading policies from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String policy;
            while ((policy = reader.readLine()) != null) {
                policyArray.add(policy);
            }
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error reading file: " + e.getMessage() + ANSI_RESET);
            return;
        }

        // Display current policies
        System.out.println("Current Policies:");
        int index = 1;
        for (String policy : policyArray) {
            System.out.println(index++ + ". " + policy);
        }

        // Ask user which policy to edit
        int lineToEdit = 0;
        boolean validLine = false;
        do {
            try {
                System.out.print("Enter the line number of the policy you wish to edit: ");
                lineToEdit = scan.nextInt();

                // Check if line is valid
                if (lineToEdit < 1 || lineToEdit > policyArray.size()) {
                    System.out.println(ANSI_RED + "Invalid line number" + ANSI_RESET);
                    validLine = false;
                } else {
                    validLine = true;
                }
            } catch (InputMismatchException e) {
                
                System.out.println(ANSI_RED + "Invalid input! Please enter an integer." + ANSI_RESET);
                scan.next();  // Clear the invalid input
                validLine = false;
            }
        } while (!validLine);

        // Clear the buffer
        scan.nextLine();

        // Get new policy from user
        String newPolicy = "";
        do {
            System.out.print("Enter the new policy: ");
            newPolicy = scan.nextLine();

            if (newPolicy.isEmpty()) {
                
                System.out.println(ANSI_RED + "Input cannot be empty. Please try again." + ANSI_RESET);
            }
        } while (newPolicy.isEmpty());

        // Update the policy in the list
        policyArray.set(lineToEdit - 1, newPolicy);

        // Write changes back to the file
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
            for (String policy : policyArray) {
                writer.println(policy);
            }
            
            System.out.println(ANSI_GREEN + "Policy updated successfully."  + ANSI_RESET);
        } catch (IOException e) {
            
            System.out.println(ANSI_RED + "Error writting to file: " + e.getMessage() + ANSI_RESET);
        }

        // Display updated policies
        System.out.println("\nUpdated Policies:");
        int count = 1;
        for (String policy : policyArray) {
            System.out.println(count + ". " + policy);
            count++;
        }


        System.out.println("");
        System.out.print("Please enter any key to continue...");
        String any = scan.nextLine();
    }
    
    public static void deletePolicy(){
        Scanner scan = new Scanner(System.in);
        // Specify the path to the file
        // change file path reminder
        String policyText = "C:/Users/Asus/Desktop/OOP/Starpark/Policy.txt";
        File file = new File(policyText);

        if (!file.exists()) {
            System.out.println(ANSI_RED + "The file does not exist." + ANSI_RESET);
            return;
        }

        List<String> policyArray = new ArrayList<>();

        // Reading policies from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String policy;
            while ((policy = reader.readLine()) != null) {
                policyArray.add(policy);
            }
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error reading file: " + e.getMessage() + ANSI_RESET);
            return;
        }

        // Display current policies
        System.out.println("Current Policies:");
        int index = 1;
        for (String policy : policyArray) {
            System.out.println(index++ + ". " + policy);
        }
        
        //try and catch
        // Ask user which policy to delete
        int lineToDelete = 0;
        boolean validLine = false;
        do{
            try{
                System.out.print("Enter the line number of the policy you wish to delete: ");
                lineToDelete = scan.nextInt();
                scan.nextLine();  // Consume the leftover newline

                // Check if line is valid
                if (lineToDelete < 1 || lineToDelete > policyArray.size()) {
                    System.out.println(ANSI_RED + "Invalid line number" + ANSI_RESET);
                    validLine = false;
                }else{
                    validLine = true;
                }
            }catch (Exception e) {
                System.out.println(ANSI_RED +"\nInvalid input! Please enter an integer."+ ANSI_RESET);
                scan.nextLine();
            }
        }while(validLine == false);

        
        // Remove the policy from the list
        policyArray.remove(lineToDelete - 1);
        
        // Write changes back to the file
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
            for (String policy : policyArray) {
                writer.println(policy);
            }
            System.out.println(ANSI_GREEN + "Policy deleted successfully." + ANSI_RESET);
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error writting to file: " + e.getMessage() + ANSI_RESET);
        }

        System.out.println("\nUpdated Policies:");
        int count = 1;
        for (String policy : policyArray) {
            System.out.println(count + ". " + policy);
            count++;
        }
        
        System.out.println("");
        System.out.print("Please enter any key to continue...");
        scan.nextLine();
    }
    
}
