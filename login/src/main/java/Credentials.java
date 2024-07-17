package main.java;

public class Credentials extends People {
    String username;
    String password;

    public Credentials(String FName, String LName, String DoB, String SSN, String username, String password) {
        super(FName, LName, DoB, SSN);
        this.username = username;
        this.password = password;

    }

    public String getPassword() {//Retrieve Password
        return password;
    }

    public String getUsername() {//Retrieve Username
        return username;
    }

    public void changePassword(String password) {//Change Password
        this.password = password;
    }

    public void changeUsername(String username) {//Change Username
        this.username = username;
    }

    public static boolean PasswordStrength(String password) {//Used for password authentication
        char c;
        boolean CapFlag = false;//Password must include capital letter
        boolean NumFlag = false; //Password must include Number
        boolean length = false;//Password Must be 8 or more characters
        if (password.length() >= 8) {
            length = true;
        }
        for (int i = 0; i < password.length(); i++) {
            c = password.charAt(i);
            if (Character.isDigit(c)) {
                NumFlag = true;
            }
            if (Character.isUpperCase(c)) {
                CapFlag = true;
            }

            if (CapFlag && NumFlag && length) {

                return true;
            }

        }
        System.out.println("Password does not Meet the Requirments");
        if (!CapFlag) {
            System.out.println("Password Need At least One Upper Case letter");
        }
        if (!length) {
            System.out.println("Password Need to be At least 8 Characters long");
        }
        if (!NumFlag) {
            System.out.println("Password Need At least One Number");
        }
        return false;
    }

}
