import org.junit.*;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class BankTester {

    @Mock
    private static Bank bank;

    @BeforeClass
    public static void setUpBeforeClass(){

    }

    @Before
    public void setUpBeforeTest(){

    }

    @After
    public void tearDownAfterTest(){

    }

    @AfterClass
    public static void TearDownAfterClass(){

    }

    @Test
    public void viewBalanceTest() throws InterruptedException {
        Bank bank = new Bank();
        Customer customer = new Customer(1,"johndoe","pass","john","doe");
        Account account = new Account(1,1,"testAccount",1000);
        int bal = bank.viewBalance(customer,account);
        assertEquals(1000,bal);
    }

}
