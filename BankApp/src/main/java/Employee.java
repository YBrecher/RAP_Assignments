

import java.util.Scanner;

public class Employee implements User{

    int employeeID;
    String firstName;
    String lastName;
    String userName;
    String password;
    Boolean loggedIn = false;

    public Employee(String userName, String password, int employeeID){
        this.userName = userName;
        this.password = password;
        this.employeeID = employeeID;
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

    @Override
    public void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your user name: ");
        String nameInput = scanner.nextLine();

        System.out.println("Enter your user password: ");
        String passwordInput = scanner.nextLine();

        if (nameInput.equals(userName) && passwordInput.equals(password)){
            System.out.println("Welcome " + userName);
            loggedIn = true;
        }

        else{
            System.out.println("Sorry that user name or password is incorrect.");
        }
    }

    public void viewCustomersAccounts(int customerID){

    }

    public void viewTransactions(int customerID){

    }

    public void approveOrRejectAccount(int customerID, int accountID){

    }


}
