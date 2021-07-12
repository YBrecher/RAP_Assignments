import java.util.Scanner;

/**
 * This class holds the main method which will run our bank simulation.
 */
public class Bank {

    public static void main(String[] args){

        Scanner scnr = new Scanner(System.in);

        System.out.println("Welcome to the Banking App.");
        System.out.println("-----------------------------------");
        System.out.println("Please select on option.");
        System.out.println("1. Customer Login");
        System.out.println("2. Employee Login");
        System.out.println("3. Create Customer Account");

        int userInput = scnr.nextInt();

        switch (userInput){
            case 1:
                Scanner customerScnr = new Scanner(System.in);

                String customerUsername;
                String customerPassword;
                System.out.println("Customer Login: ");
                System.out.println("Please enter your username: ");
                customerUsername = customerScnr.nextLine();
                System.out.println("Please enter your password: ");
                customerPassword = customerScnr.nextLine();

                //At this point we will use jdbc to check the username and password with the db
                System.out.println("Welcome " + customerUsername);

                System.out.println("Please select an option");
                System.out.println("1. Apply for bank account");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");

                int customerInput = scnr.nextInt();

                switch (customerInput){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
                break;

            case 2:
                Scanner employeeScnr = new Scanner(System.in);

                String employeeUsername;
                String employeePassword;
                System.out.println("Customer Login: ");
                System.out.println("Please enter your username: ");
                employeeUsername = employeeScnr.nextLine();
                System.out.println("Please enter your password: ");
                employeePassword = employeeScnr.nextLine();

                //At this point we will use jdbc to check the username and password with the db

                System.out.println("Welcome "+ employeeUsername);

                System.out.println("Please select an option");
                System.out.println("1. View customer account");
                System.out.println("2. View transactions");
                System.out.println("3. Approve or reject account");

                int employeeInput = scnr.nextInt();

                switch (employeeInput){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }

                break;

            case 3:
                //This option requires jdbc to implement
                break;
            default:
                System.out.println("Incorrect input");
                System.exit(0);
                break;
        }


    }
}
