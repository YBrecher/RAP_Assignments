import org.junit.*;
import org.mockito.Mock;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class BankTester {

    Bank bank = new Bank();
    Customer customer = new Customer(1,"johndoe","pass","john","doe");
    Account account = new Account(1,1,"personal",1000);

    @Test
    public void withdrawTest() throws InterruptedException, SQLException {
        bank.withdraw(customer, account, 100);
        assertEquals(900, account.getBalance());
    }

    @Test
    public void depositTest() throws InterruptedException, SQLException {
        bank.deposit(customer, account, 100);
        assertEquals(1100, account.getBalance());
    }

    @Test
    public void transferTest() throws InterruptedException, SQLException {
        Account account2 = new Account(2,1,"business",2000);
        bank.transfer(customer, account, account2, 500);
        assertEquals(500, account.getBalance());
        assertEquals(2500, account2.getBalance());
    }

    @Test
    public void applicationTest() throws InterruptedException, SQLException {
        String accountName = "account";
        assertTrue(bank.applyForNewAccount(customer, accountName, 500));
    }

}
