

import java.util.Scanner;

public class Employee {

    int employeeID;
    String userName;
    String password;
    String firstName;
    String lastName;


    public Employee(int employeeID, String userName, String password, String firstName, String lastName){
        this.userName = userName;
        this.password = password;
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int customerID) {
        this.employeeID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
