package ch3;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AssertTest {

    class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }

        private static final long serialVesionUID = 1L;
    }

    class Account {
        int balance;
        String name;

        public Account(String name) {
            this.name = name;
        }

        void deposit(int dollars) {
            balance += dollars;
        }

        void withdraw(int dollars) {
            if(balance < dollars) {
                throw new InsufficientFundsException("balance only " + balance);
            }
            balance -= dollars;
        }

        public int getBalance() {
            return balance;
        }

        public String getName() {
            return name;
        }

        public boolean hasPositiveBalance() {
            return balance > 0;
        }
    }

    class Customer {
        List<Account> accounts = new ArrayList<>();

        void add(Account account) {
            accounts.add(account);
        }

        Iterator<Account> getAccountIterator() {
            return accounts.iterator();
        }
    }

    private Account account;

    @BeforeEach
    public void createAccount() {
        account = new Account("an account name");
    }

    @Test
    public void hasPositiveBalance() {
        account.deposit(50);
        assertThat(account.hasPositiveBalance()).isTrue();
    }

    @Test
    public void depositIncreaseBalance() {
        int initialBalance = account.getBalance();
        account.deposit(100);
        assertThat(account.getBalance()).isGreaterThan(initialBalance);
        assertThat(account.getBalance()).isEqualTo(100);
    }

    // 부동소수점은 오차가 날수밖에 없어서 오차를 둬야함
    @Test
    public void floatNumberTest() {
        assertThat(2.32 * 3).isCloseTo(6.96, within(0.0005));
    }

    @Test()
    public void throwWhenWithdrawingTooMuch() {
        try {
            account.withdraw(100);
            fail("fail");
        } catch (InsufficientFundsException expected) {
            assertThat(expected.getMessage()).isEqualTo("balance only 0");
        }
    }
}
