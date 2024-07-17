
package main.java;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    
    private static LinkedList<Credentials> userList;
    private static MyHashTable hashMap = new MyHashTable();

    public static void main(String[] args) {

        userList = new LinkedList<>(); // create a list of users

        // add groups info as users
        userList.add(new Credentials("Kevin", "Mendez", "00/00/2000", "123-45-6789", "mevin", "kendez"));
        userList.add(new Credentials("xxxx", "yyyyy", "xx/yy/zzzz", "987-65-4321", "ttttt", "rrrrrr"));
        userList.add(new Credentials("xxxx", "yyyyy", "xx/yy/zzzz", "987-65-4377", "iiiiiii", "nnnnn"));


        // add users to hash table
        for (Credentials user : userList) {
            hashMap.set(user.getUsername(), user.getPassword());
        }

        Scanner scanner = new Scanner(System.in);

        // display menu options
        while (true) {
            System.out.println("Please choose one of the following options (choose a number): ");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.println("4. Show all users");
            System.out.println("5. Search For User by Username");
            System.out.println("6. Delete User");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {// Switch case for menu options
                case 1:
                    loginU(scanner);//Login
                    break;
                case 2:
                    registerU(scanner);//Register
                    break;
                case 3:
                    System.out.println("Exiting...");//Exit
                    scanner.close();
                    System.exit(0);
                    break;
                case 4:
                    displayUsers();//Display all users
                    break;
                case 5:
                    searchforUser(scanner);//Search for user
                    break;
                case 6:
                    deleteUser(scanner);//Delete user
                    break;
                default:
                    System.out.println("Invalid choice. Please choose one of the above.");
            }
        }

    }

    private static void registerU(Scanner scanner) {//Register User
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter your date of birth (00/00/0000): ");
        String DoB = scanner.nextLine();
        while (!DoB.matches("[0-1][0-9]/[0-3][0-9]/[0-9][0-9][0-9][0-9]")) {//Check if input matches 00/00/0000 Format
            System.out.println("DoB is Invalid Try Again");
            DoB = scanner.nextLine();
        }
        System.out.print("Enter your SSN (000-00-0000): ");
        String SSN = scanner.nextLine();
        while (!SSN.matches("[0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9][0-9]")) {//Check if input matches 000-00-0000 Format
            System.out.println("SSN is Invalid Try Again");
            SSN = scanner.nextLine();
        }
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        while(hashMap.get(username) != null){//Check if Username is taken
            System.out.println("Username is taken, please try again. ");
            System.out.println("Enter your username: ");
            username = scanner.nextLine();
        }
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        while (Credentials.PasswordStrength(password) == false) {//check if password meets requirements
            password = scanner.nextLine();
        }
        hashMap.set(username, password);
        userList.add(new Credentials(firstName, lastName, DoB, SSN, username, password));
        System.out.println("User has been registered.");

    }

    private static void loginU(Scanner scanner) {//For logging in
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        for (int i = 0; i < userList.size(); i++) {
            Credentials user = userList.get(i);
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful.");
                displayInfo(i);
                return;
            }
        }

        System.out.println("Invalid username or password. Please try again.");

    }

    private static void displayInfo(int i) { //Displays all info about user
        System.out.println("User Information:");
        System.out.println("First Name: " + userList.get(i).getFName());
        System.out.println("Last Name: " + userList.get(i).getLName());
        System.out.println("Date of Birth: " + userList.get(i).getDoB());
        System.out.println("SSN: " + userList.get(i).getSSN());

    }

    private static void displayUsers() { //Displays all users
        System.out.println(
                "-----------------------------------------------------------------------------------------------");
        System.out.println("Users:");
        System.out.printf("%-15s | %-15s | %-13s | %-11s | %-15s | %-15s \n", "First Name", "Last Name",
                "Date of Birth", "SSN", "Username", "Password");
        for (Credentials user : userList) {
            System.out.printf("%-15s | %-15s | %-13s | %-11s | %-15s | %-15s \n", user.getFName(), user.getLName(),
                    user.getDoB(), user.getSSN(), user.getUsername(), user.getPassword());
        }
        System.out.println(
                "-----------------------------------------------------------------------------------------------");
    }

    private static void searchforUser(Scanner scanner) {//Searches by username using hashset
        System.out.println("Enter Username");
        String username = scanner.nextLine();
        String password = hashMap.get(username);
        if (password != null) {
            System.out.println("Password for " + username + " is: " + password);
        } else {
            System.out.println("User Not Found");
        }

    }

    private static void deleteUser(Scanner scanner) {//deletes user
        System.out.println("Enter Users First Name");
        String fName = scanner.nextLine();
        System.out.println("Enter Users Last Name");
        String lName = scanner.nextLine();
        int i = 0;
        for (Credentials user : userList) {
            if (user.getFName().equals(fName) && user.getLName().equals(lName)) {
                userList.remove(i);
                System.out.println("User Removed");
                hashMap.remove(user.getUsername());
                return;
            }
            i++;

        }
        System.out.println("User Not Found");
    }

}
