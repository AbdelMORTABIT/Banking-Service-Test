import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Interface
public interface AccountService {
    void deposit(int amount);
    void withdraw(int amount);
    void printStatement();
}

class Account implements AccountService {

    private int balance = 0;

    private class Transaction {

        String date;
        int amount;
        int balanceAfter;

        Transaction(String date, int amount, int balanceAfter) {
            this.date = date;
            this.amount = amount;
            this.balanceAfter = balanceAfter;
        }
    }

    private List<Transaction> transactions = new ArrayList<>();

    private String today() {
        return java.time.LocalDate.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );
    }

    @Override
    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Please enter a deposit amount greater than zero."
            );
        }
        balance += amount;
        transactions.add(new Transaction(today(), amount, balance));
    }

    @Override
    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Please enter a withdrawal amount greater than zero."
            );
        }
        if (amount > balance) {
            throw new IllegalArgumentException(
                "Please enter an amount within your available balance"
            );
        }
        balance -= amount;
        transactions.add(new Transaction(today(), -amount, balance));
    }

    @Override
    public void printStatement() {
        System.out.println("Date\t\t|| Amount\t|| Balance");
        List<Transaction> reversed = new ArrayList<>(transactions);
        Collections.reverse(reversed);
        for (Transaction t : reversed) {
            System.out.printf(
                "%s\t|| %d\t\t|| %d%n",
                t.date,
                t.amount,
                t.balanceAfter
            );
        }
    }

    public static void main(String[] args) {
        AccountService account = new Account();
        account.deposit(7000);
        account.deposit(300);
        account.withdraw(1000);
        account.printStatement();
    }
}
