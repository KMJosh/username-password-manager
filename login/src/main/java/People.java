package main.java;

public class People {
    String firstName;
    String lastName;
    String DoB;
    String SSN;

    public People(String firstName, String lastName, String DoB, String SSN){
        this.firstName = firstName;
        this.lastName = lastName;
        this.DoB = DoB;
        this.SSN = SSN;
    }

    public String getFName(){//Retrieve First Name
        return firstName;
    }

    public String getLName(){//Retrieve Last Name
        return lastName;
    }

    public String getDoB(){//Retrieve DoB
        return DoB;
    }

    public String getSSN(){//Retrieve SSN
        return SSN;
    }

    public String toString(){//Retrieve all info in a String
        return "Name: " + firstName + " " + lastName + " Date of Birth: " + DoB + " SSN: " + SSN ;
    }
    



}
