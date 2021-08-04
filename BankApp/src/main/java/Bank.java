
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DBConnection;

import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * This class holds the main method which will run our bank simulation.
 */
public class Bank {

    private static final Logger LOG = LogManager.getLogger(Bank.class);

    public static void main(String[] args) throws SQLException, InterruptedException {
        LOG.trace("displayStartScreen method called.");
        displayStartScreen();
    }

    private static void displayStartScreen() throws InterruptedException {
        Scanner scnr = new Scanner(System.in);

        System.out.println();
        System.out.println("Welcome to the Banking App.");
        System.out.println("-----------------------------------");
        System.out.println("Please select on option.");
        System.out.println("1. Customer Login");
        System.out.println("2. Employee Login");
        System.out.println("3. Register");

        int userInput = scnr.nextInt();

        switch (userInput) {
            case 1:
                //LOG.trace("displayCustomerLogin method was called");
                displayCustomerLogin();
                break;

            case 2:
                //LOG.trace("displayEmployeeLogin method was called");
                displayEmployeeLogin();
                break;

            case 3:
                displayRegister();
                System.out.println("User has been added");
                break;

            default:
                System.out.println("Incorrect input. Please try again");
                displayStartScreen();
                break;
        }
    }

    private static void displayRegister() throws InterruptedException {
        Scanner scnr = new Scanner(System.in);

        System.out.println();
        System.out.println("Please enter user information.");

        System.out.println("First name: ");
        String firstName = scnr.nextLine();

        System.out.println("Last name: ");
        String lastName = scnr.nextLine();

        System.out.println("Username: ");
        String username = scnr.nextLine();

        System.out.println("Password: ");
        String password = scnr.nextLine();

        addNewUser(username,password,firstName,lastName);

        System.out.println();
        System.out.println("Now please enter your account information.");

        System.out.println("Account name: ");
        String accountName = scnr.nextLine();

        System.out.println("Initial deposit");
        int deposit = scnr.nextInt();

        addFirstAccount(username,password,accountName,deposit);

        System.out.println();
        System.out.println("You have been registered and your account has been added.");
        System.out.println();
        displayStartScreen();
    }

    private static void addFirstAccount(String username, String password, String accountName, int deposit) throws InterruptedException {
        int id = 0;

        //First we need to get the customer id
        String sql = "select * from customers " +
                " where username = ? and password = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.execute();

            ResultSet rs = statement.getResultSet();
            rs.next();
            id = rs.getInt("customer_id");
        } catch (SQLException throwables) {
            System.out.println("Error with adding account.");
            displayStartScreen();
            throwables.printStackTrace();
        }

        //Once we have the customer id we can add the account
        String insertSql = "insert into accounts (customer_id, name, balance) " +
                "values (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSql);) {
            statement.setInt(1, id);
            statement.setString(2, accountName);
            statement.setInt(3, deposit);
            statement.execute();

        } catch (SQLException throwables) {
            System.out.println("Error with adding account.");
            displayStartScreen();
            throwables.printStackTrace();
        }
    }

        private static void addNewUser(String username, String password, String firstName, String lastName) {

        String sql = "insert into customers (username, password, first_name, last_name) " +
                "values ( ?, ?, ?, ?)";


        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

        private static void displayCustomerLogin() throws InterruptedException {
        Scanner customerScnr = new Scanner(System.in);

        String customerUsername;
        String customerPassword;
        System.out.println("Customer Login: ");
        System.out.println("Please enter your username: ");
        customerUsername = customerScnr.nextLine();
        System.out.println("Please enter your password: ");
        customerPassword = customerScnr.nextLine();

        String sql = "select * from customers " +
                " where username = ? and password = ?";


        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, customerUsername);
            statement.setString(2, customerPassword);
            statement.execute();

            ResultSet rs = statement.getResultSet();

            while (rs.next()) {
                int customerID = rs.getInt("customer_id");
                String userName = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                Customer customer = new Customer(customerID, userName, password, firstName, lastName);

                System.out.println();
                System.out.println("Welcome " + firstName + " " + lastName);
                System.out.println();

                displayAccountSelection(customer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Incorrect username or password. Please try again.");
        System.out.println();
        displayCustomerLogin();

    }

    private static void displayAccountSelection(Customer customer) {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Please enter the name of the account you would like to access: ");
        String accountName = scnr.nextLine();

        String sql = "select * from accounts " +
                " where customer_id = ? and name = ?";


        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, customer.customerID);
            statement.setString(2, accountName);
            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                int accountID = rs.getInt("account_id");
                String name = rs.getString("name");
                int balance = rs.getInt("balance");

                Account account = new Account(accountID, customer.customerID, name, balance);

                displayCustomerActions(customer, account);
            }


        } catch (SQLException | InterruptedException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Incorrect account name. Please try again.");
        System.out.println();
        displayAccountSelection(customer);
    }

    private static void displayCustomerActions(Customer customer, Account account) throws InterruptedException, SQLException {
        Scanner scnr = new Scanner(System.in);
        Scanner scnr2 = new Scanner(System.in);

        System.out.println();
        System.out.println("Please select an option:");
        System.out.println("1. View balance");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer between accounts");
        System.out.println("5. Switch to a different account");
        System.out.println("6. Apply for a new account");
        System.out.println("7. Logout");

        int customerInput = scnr.nextInt();

        switch (customerInput) {
            case 1:
                viewBalance(customer, account);
                break;
            case 2:
                System.out.println("Please enter the amount to withdraw: ");
                int amount = scnr.nextInt();
                if (amount > account.getBalance()){
                    System.out.println();
                    System.out.println("Error: The most you can withdraw is $" + account.getBalance());
                    System.out.println();
                    System.out.println("Returning to customer options screen...");
                    displayCustomerActions(customer,account);
                }
                withdraw(customer, account, amount);
                displayCustomerActions(customer, account);
                break;
            case 3:
                System.out.println("Please enter the amount to deposit: ");
                amount = scnr.nextInt();
                deposit(customer, account, amount);
                displayCustomerActions(customer, account);
                break;
            case 4:
                System.out.println("Please enter the name of the account you would like to transfer funds to: ");
                String newAccount = scnr2.nextLine();

                System.out.println("Please enter the amount you would like to transfer: ");
                amount = scnr.nextInt();

                if (amount > account.getBalance()){
                    System.out.println("Sorry the most you can withdraw is $" + account.getBalance());
                    System.out.println("Returning to customer options screen...");
                    displayCustomerActions(customer,account);
                }


                String sql = "select * from accounts " +
                        " where customer_id = ? and name = ?";


                try (Connection connection = DBConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(sql);) {
                    statement.setInt(1, customer.customerID);
                    statement.setString(2, newAccount);
                    statement.execute();

                    ResultSet rs = statement.getResultSet();
                    while (rs.next()) {
                        int accountID = rs.getInt("account_id");
                        String name = rs.getString("name");
                        int balance = rs.getInt("balance");

                        Account receivingAccount = new Account(accountID, customer.customerID, name, balance);
                        transfer(customer, account, receivingAccount, amount);
                    }
                }
                displayCustomerActions(customer, account);
                break;
            case 5:
                displayAccountSelection(customer);
                break;
            case 6:
                System.out.println("What will the name of your new account be? ");
                String newAccountName = scnr2.nextLine();

                System.out.println("How much would you like the initial deposit to be? ");
                int initialDeposit = scnr.nextInt();

                applyForNewAccount(customer,newAccountName,initialDeposit);

                System.out.println("Application complete. An employee will look over this application soon.");
                System.out.println();
                System.out.println("What else would you like to do?");
                displayCustomerActions(customer,account);

                break;
            case 7:
                System.out.println("Logging out...");
                System.out.println();
                TimeUnit.SECONDS.sleep(2);
                displayStartScreen();
                break;
            default:
                System.out.println("Incorrect input. Please try again");
                displayCustomerActions(customer, account);
                break;
        }
    }

    public static boolean applyForNewAccount(Customer customer, String newAccountName, int initialDeposit) {

        String sql = "insert into requests (customer_id, name, deposit) " +
                " values (?, ?, ?);";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, customer.customerID);
            statement.setString(2, newAccountName);
            statement.setInt(3, initialDeposit);
            statement.execute();

            return true;

        } catch (SQLException throwables) {
            System.out.println("There was an error with the application.");
            throwables.printStackTrace();
        }
        return false;
    }

        public static void transfer(Customer customer, Account account, Account receivingAccount, int amount) throws SQLException, InterruptedException {
        account.setBalance(account.getBalance() - amount);
        receivingAccount.setBalance(receivingAccount.getBalance() + amount);

        String sql = "update accounts" +
                " set balance = ? where account_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, account.getBalance());
            statement.setInt(2, account.getAccountID());
            statement.execute();

            System.out.println();
            System.out.println("Your new account balance for your " + account.getName() + " account is $" + account.getBalance());
            System.out.println();


        } catch (SQLException throwables) {
            System.out.println("An error has occurred please try again");
            System.out.println();
            displayCustomerActions(customer, account);
            throwables.printStackTrace();
        }

        sql = "update accounts" +
                " set balance = ? where account_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, receivingAccount.getBalance());
            statement.setInt(2, receivingAccount.getAccountID());
            statement.execute();

            System.out.println();
            System.out.println("Your new account balance for your " + receivingAccount.getName() + " account is $" + receivingAccount.getBalance());
            System.out.println();

            System.out.println();
            System.out.println("What else would you like to do?");


        } catch (SQLException throwables) {
            System.out.println("An error has occurred please try again");
            System.out.println();
            displayCustomerActions(customer, account);
            throwables.printStackTrace();
        }

    }

    public static void deposit(Customer customer, Account account, int amount) throws InterruptedException, SQLException {

        account.setBalance(account.getBalance() + amount);

        String sql = "update accounts" +
                " set balance = ? where account_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, account.getBalance());
            statement.setInt(2, account.getAccountID());
            statement.execute();

            System.out.println();
            System.out.println("Your new account balance is $" + account.getBalance());
            System.out.println();
            System.out.println("What else would you like to do?");

        } catch (SQLException throwables) {
            System.out.println("An error has occurred please try again");
            System.out.println();
            displayCustomerActions(customer, account);
            throwables.printStackTrace();
        }
    }

    public static void withdraw(Customer customer, Account account, int amount) throws InterruptedException, SQLException {

        account.setBalance(account.getBalance() - amount);

        String sql = "update accounts" +
                " set balance = ? where account_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, account.getBalance());
            statement.setInt(2, account.getAccountID());
            statement.execute();

            System.out.println();
            System.out.println("Your new account balance is $" + account.getBalance());
            System.out.println();
            System.out.println("What else would you like to do?");


        } catch (SQLException throwables) {
            System.out.println("An error has occurred please try again");
            System.out.println();
            displayCustomerActions(customer, account);
            throwables.printStackTrace();
        }
    }

    public static void viewBalance(Customer customer, Account account) throws InterruptedException, SQLException {
        System.out.println();
        System.out.println("Your balance for your " + account.getName()
                + " account is $" + account.getBalance() + ".");

        System.out.println();
        System.out.println("What else would you like to do?");
        displayCustomerActions(customer, account);

    }

    private static void displayEmployeeLogin() throws InterruptedException {
        Scanner employeeScnr = new Scanner(System.in);

        String employeeUsername;
        String employeePassword;
        System.out.println("Employee Login: ");
        System.out.println("Please enter your username: ");
        employeeUsername = employeeScnr.nextLine();
        System.out.println("Please enter your password: ");
        employeePassword = employeeScnr.nextLine();

        String sql = "select * from employees " +
                " where username = ? and password = ?";


        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, employeeUsername);
            statement.setString(2, employeePassword);
            statement.execute();

            ResultSet rs = statement.getResultSet();

            while (rs.next()) {
                int employeeID = rs.getInt("employee_id");
                String userName = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                Employee employee = new Employee(employeeID, userName, password, firstName, lastName);

                System.out.println();
                System.out.println("Welcome " + firstName + " " + lastName);

                displayEmployeeActions(employee);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Incorrect username or password. Please try again.");
        System.out.println();
        displayEmployeeLogin();

    }

    private static void displayEmployeeActions(Employee employee) throws InterruptedException {
        Scanner scnr = new Scanner(System.in);

        System.out.println();
        System.out.println("Please select an option:");
        System.out.println("1. View customer account");
        System.out.println("2. Review account application");
        System.out.println("3. Logout");

        int employeeInput = scnr.nextInt();

        switch (employeeInput) {
            case 1:
                System.out.println("Please enter the ID number of the customer you would like to view: ");
                int id = scnr.nextInt();
                viewCustomerAccount(id, employee);
                break;
            case 2:
                reviewApplication(employee);
                break;
            case 3:
                System.out.println("Logging out...");
                System.out.println();
                TimeUnit.SECONDS.sleep(2);
                displayStartScreen();
                break;
            default:
                System.out.println("Incorrect input. Please try again");
                displayEmployeeActions(employee);
                break;
        }
    }

    private static void reviewApplication(Employee employee) throws InterruptedException {
        Scanner scnr = new Scanner(System.in);
        int custID;
        String accountName;
        int initialDeposit;

        String sql = "select * from requests limit 1;";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.execute();

            ResultSet rs = statement.getResultSet();
            rs.next();

            custID = rs.getInt("customer_id");
            accountName = rs.getString("name");
            initialDeposit = rs.getInt("deposit");

            System.out.println();
            System.out.println("Account application: ");
            System.out.println("Customer id: " + custID);
            System.out.println("Account name: "+ accountName);
            System.out.println("Initial deposit: " + initialDeposit);

            System.out.println();
            System.out.println("Please enter 1 to approve this application or 2 to reject it.");
            int employeeDecision = scnr.nextInt();

            if (employeeDecision == 1){
                createAccount(custID,accountName,initialDeposit);
                System.out.println("Account has been successfully created.");
                deleteRequest();
                System.out.println();
                System.out.println("What else would you like to do?");
                displayEmployeeActions(employee);
            }
            else if (employeeDecision == 2){
                System.out.println("Application has been rejected. New account will not be created.");
                deleteRequest();
                System.out.println();
                System.out.println("What else would you like to do?");
                displayEmployeeActions(employee);
            }

            else{
                System.out.println("Not a valid input please try again.");
                reviewApplication(employee);
            }

        } catch (SQLException | InterruptedException throwables) {
            System.out.println("There are currently no account applications to view.");
            displayEmployeeActions(employee);
            throwables.printStackTrace();
        }
    }

    private static void createAccount(int custID, String accountName, int initialDeposit) {

        String sql = "insert into accounts (customer_id, name, balance) values (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, custID);
            statement.setString(2, accountName);
            statement.setInt(3, initialDeposit);
            statement.execute();

        } catch (SQLException throwables) {
            System.out.println("Error. Could not create new account.");
            throwables.printStackTrace();
        }
    }

        private static void deleteRequest() {

        String sql = "delete from requests where request_id in (select request_id from requests limit 1)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.execute();

        } catch (SQLException throwables) {
            System.out.println("Error could not delete request.");
            throwables.printStackTrace();
        }
    }

        private static void viewCustomerAccount(int id, Employee employee) throws InterruptedException {

        String sql = "select * from customers" +
                " where customer_id = ?";

        String sql2 = "select * from accounts where customer_id = ?";

        //we run the first sql statement to get the information from the customer table
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            statement.execute();

            ResultSet rs = statement.getResultSet();

            rs.next();

            System.out.println();
            System.out.println("Customer name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
            System.out.println("Customer username: " + rs.getString("username"));
            System.out.println("Customer password: " + rs.getString("password"));

        } catch (SQLException throwables) {
            System.out.println("There was an error with viewing that customer's account.");
            throwables.printStackTrace();
        }

        //we run the second sql statement to get the information from the account table
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql2);) {
            statement.setInt(1, id);
            statement.execute();

            ResultSet rs = statement.getResultSet();

            while(rs.next()){
                System.out.println();
                System.out.println("Account name: " + rs.getString("name"));
                System.out.println("Account balance: $" + rs.getInt("balance"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println();
        System.out.println("What else would you like to do?");
        displayEmployeeActions(employee);
    }
}
