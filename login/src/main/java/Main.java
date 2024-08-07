// GUI implementation
package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class Main {

    private static LinkedList<Credentials> userList;
    private static MyHashTable hashMap = new MyHashTable();

    public static void main(String[] args) {

        userList = new LinkedList<>();
        userList.add(new Credentials("Kevin", "Mendez", "00/00/2000", "123-45-6789", "mevin", "kendez"));
        userList.add(new Credentials("xxxx", "yyyyy", "xx/yy/zzzz", "987-65-4321", "ttttt", "rrrrrr"));
        userList.add(new Credentials("xxxx", "yyyyy", "xx/yy/zzzz", "987-65-4377", "iiiiiii", "nnnnn"));

        for (Credentials user : userList) {
            hashMap.set(user.getUsername(), user.getPassword());
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("User Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(90, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(170, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(90, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(170, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 80, 80, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(270, 80, 100, 25);
        panel.add(registerButton);

        JButton showUsersButton = new JButton("Show Users");
        showUsersButton.setBounds(70, 110, 120, 25);
        panel.add(showUsersButton);

        JButton searchUserButton = new JButton("Search User");
        searchUserButton.setBounds(200, 110, 120, 25);
        panel.add(searchUserButton);

        JButton deleteUserButton = new JButton("Delete User");
        deleteUserButton.setBounds(330, 110, 100, 25);
        panel.add(deleteUserButton);

        JTextArea resultArea = new JTextArea();
        resultArea.setBounds(30, 140, 500, 300);
        panel.add(resultArea);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                for (int i = 0; i < userList.size(); i++) {
                    Credentials user = userList.get(i);
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        resultArea.setText("Login successful.\n" + "User Information:\n" + "First Name: " + user.getFName() + "\n" + "Last Name: " + user.getLName() + "\n" + "Date of Birth: " + user.getDoB() + "\n" + "SSN: " + user.getSSN());
                        return;
                    }
                }
                resultArea.setText("Invalid username or password. Please try again.");
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerU();
            }
        });

        showUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayUsers(resultArea);
            }
        });

        searchUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Enter Username");
                searchforUser(resultArea, username);
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = JOptionPane.showInputDialog("Enter User's First Name");
                String lastName = JOptionPane.showInputDialog("Enter User's Last Name");
                deleteUser(resultArea, firstName, lastName);
            }
        });
    }

    private static void registerU() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField ssnField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Date of Birth (00/00/0000):"));
        panel.add(dobField);
        panel.add(new JLabel("SSN (000-00-0000):"));
        panel.add(ssnField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Register User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String DoB = dobField.getText();
            String SSN = ssnField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (!DoB.matches("[0-1][0-9]/[0-3][0-9]/[0-9][0-9][0-9][0-9]") || !SSN.matches("[0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9][0-9]")) {
                JOptionPane.showMessageDialog(null, "Invalid DoB or SSN format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (hashMap.get(username) != null) {
                JOptionPane.showMessageDialog(null, "Username is taken, please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Credentials.PasswordStrength(password)) {
                JOptionPane.showMessageDialog(null, "Password does not meet requirements.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            hashMap.set(username, password);
            userList.add(new Credentials(firstName, lastName, DoB, SSN, username, password));
            JOptionPane.showMessageDialog(null, "User has been registered.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void displayUsers(JTextArea resultArea) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------------------------------------------------------------------------\n");
        sb.append("Users:\n");
        sb.append(String.format("%-15s | %-15s | %-15s | %-15s | %-15s | %-15s \n", "First Name", "Last Name", "Date of Birth", "SSN", "Username", "Password"));
        for (Credentials user : userList) {
            sb.append(String.format("%-15s | %-15s | %-15s | %-15s | %-15s | %-15s \n", user.getFName(), user.getLName(), user.getDoB(), user.getSSN(), user.getUsername(), user.getPassword()));
        }
        sb.append("-----------------------------------------------------------------------------------------------\n");
        resultArea.setText(sb.toString());
    }

    private static void searchforUser(JTextArea resultArea, String username) {
        String password = hashMap.get(username);
        if (password != null) {
            resultArea.setText("Password for " + username + " is: " + password);
        } else {
            resultArea.setText("User Not Found");
        }
    }

    private static void deleteUser(JTextArea resultArea, String fName, String lName) {
        int i = 0;
        for (Credentials user : userList) {
            if (user.getFName().equals(fName) && user.getLName().equals(lName)) {
                userList.remove(i);
                hashMap.remove(user.getUsername());
                resultArea.setText("User Removed");
                return;
            }
            i++;
        }
        resultArea.setText("User Not Found");
    }

}



// Base original

/* 
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
*/


