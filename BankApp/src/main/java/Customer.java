
import java.util.Scanner;

public class Customer implements User {

    int customerID;
    String firstName;
    String lastName;
    String userName;
    String password;
    Boolean loggedIn = false;

    public Customer(String userName, String password, int customerID){
        this.userName = userName;
        this.password = password;
        this.customerID = customerID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
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

    public void applyForBankAccount(int balance){

    }

    public void withdraw(int accountID, int amount){

    }

    public void deposit(int accountID, int amount){

    }
}
