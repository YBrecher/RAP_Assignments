
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

        /**
        String sql = "select balance from accounts " +
                "inner join customers on accounts.customer_id = customers.customer_id" +
                " where customers.customer_id = ?";


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);)
        {
            statement.setInt(1, 1);
            statement.execute();

            ResultSet rs = statement.getResultSet();

            while (rs.next()){
                System.out.println(rs.getString("balance"));
            }
        }
         **/


        LOG.trace("displayStartScreen method called.");
        displayStartScreen();



    }

    private static void displayStartScreen() throws InterruptedException {
        Scanner scnr = new Scanner(System.in);

        System.out.println("Welcome to the Banking App.");
        System.out.println("-----------------------------------");
        System.out.println("Please select on option.");
        System.out.println("1. Customer Login");
        System.out.println("2. Employee Login");
        System.out.println("3. Register");

        int userInput = scnr.nextInt();

        switch (userInput){
            case 1:
                LOG.trace("displayCustomerLogin method was called");
                displayCustomerLogin();

                break;

            case 2:
                LOG.trace("displayEmployeeLogin method was called");
                displayEmployeeLogin();

                break;

            case 3:
                //This option requires jdbc to implement
                break;

            default:
                System.out.println("Incorrect input. Please try again");
                displayStartScreen();
                break;
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


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);)
        {
           statement.setString(1,customerUsername);
           statement.setString(2,customerPassword);
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


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, customer.customerID);
            statement.setString(2, accountName);
            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                int accountID = rs.getInt("account_id");
                String name = rs.getString("name");
                int balance = rs.getInt("balance");

                Account account = new Account(accountID, customer.customerID, name,balance);

                displayCustomerActions(customer, account);
            }


        } catch (SQLException | InterruptedException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Incorrect account name. Please try again.");
        System.out.println();
        displayAccountSelection(customer);
    }

    private static void displayCustomerActions(Customer customer, Account account) throws InterruptedException {
        Scanner scnr = new Scanner(System.in);

        System.out.println("Please select an option:");
        System.out.println("1. View balance");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer between accounts");
        System.out.println("5. Switch to different account");
        System.out.println("6. Apply for an account");
        System.out.println("7. Logout");

        int customerInput = scnr.nextInt();

        switch (customerInput){
            case 1:
                viewBalance(customer,account);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                displayAccountSelection(customer);
                break;
            case 6:
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

    private static void viewBalance(Customer customer, Account account) throws InterruptedException {
        System.out.println();
        System.out.println("Your balance for your " + account.getName()
                + " account is $" + account.getBalance() +".");

        System.out.println("What else would you like to do?");
        System.out.println();
        displayCustomerActions(customer,account);
    }

    private static void displayEmployeeLogin() throws InterruptedException {
        Scanner employeeScnr = new Scanner(System.in);

        String employeeUsername;
        String employeePassword;
        System.out.println("Customer Login: ");
        System.out.println("Please enter your username: ");
        employeeUsername = employeeScnr.nextLine();
        System.out.println("Please enter your password: ");
        employeePassword = employeeScnr.nextLine();

        String sql = "select * from employees " +
                " where username = ? and password = ?";


        try(Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);)
        {
            statement.setString(1,employeeUsername);
            statement.setString(2,employeePassword);
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

        System.out.println("Please select an option:");
        System.out.println("1. View customer account");
        System.out.println("2. View transactions");
        System.out.println("3. Approve or reject account");
        System.out.println("4. Logout");

        int employeeInput = scnr.nextInt();

        switch (employeeInput){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
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
}
