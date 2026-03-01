import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

abstract public class BankAccount implements Transferable {

    private float balance;
    private String name;
    private final int MAX_WITHDRAW = 10000;

    // ReentrantLock to prevent race condition
    protected final ReentrantLock lock = new ReentrantLock(true);

    // Constructor with initial balance and account name
	public BankAccount(float balance, String name)
	{
		this.balance = balance;
		this.name = name;
	}

    // Returns the current balance
    public float getBalance() {
        return balance;
    }

    // Returns the name of the account
    public String getName() {
        return name;
    }

    // Validates both deposit and withdraw
    public boolean validate(float amount, String type)
    {

        if (type.equalsIgnoreCase("withdraw")) 
        {
            // Check if the amount is less than balance
            if (amount > this.balance) 
            {
                System.out.println("Invalid amount: Bigger than current balance");
                return false;
            }

            // Check if the amount is negative
            else if (amount < 0) 
            {
                System.out.println("Invalid amount: Negative amount");
                return false;
            }

            // Check if the amount is outrageously large
            else if (amount >= MAX_WITHDRAW) 
            {
                System.out.println("Invalid amount: Too large");
                return false;
            }
            return true;
        } 
        else if (type.equalsIgnoreCase("deposit")) 
        {
            if (amount <= 0) 
            {
                System.out.println("Invalid amount: Negative amount");
                return false;
            } else {
                return true;
            }
        }
        // Invalid parameter
        return false;
    }

    // Increases the current balance by amount
    public boolean updateBalance(float amount) 
    {
        this.balance += amount;
        return true;
    }

    // Withdraw from current balance by amount
    // Only one thread may use this method at a time (synchronized)
    // Implements withdraw() from Transferable interface
    @Override
    public boolean withdraw(float amount) 
    {
        try 
        {

            // Timeout to prevent deadlocks
            if (lock.tryLock(2, TimeUnit.SECONDS)) 
            {
                try 
                {

                    // Validate if the withdrawal amount is valid
                    if (validate(amount, "withdraw")) 
                    {
                        // Update the balance by minus
                        updateBalance(-amount);

                        // Return withdraw success
                        System.out.println(this.name + " has successfully withdrawn: Current balance = " + this.balance);
                        return true;
                    } 
                    else 
                    {
                        // Return withdraw failed
                        System.out.println("Withdrawal failed");
                        return false;
                    }

                } 
                finally 
                {
                    lock.unlock();
                }

            // Lock failed
            } 
            else 
            {
                System.out.println("Withdraw timeout: Could not acquire lock");
                return false;
            }
        } 
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    // Deposit in current balance by amount
    // Only one thread may use this method at a time (synchronized)
    // Implements deposit() from Transferable interface
    @Override
    public boolean deposit(float amount) 
    {
        try 
        {

            // Timeout to prevent deadlocks
            if (lock.tryLock(2, TimeUnit.SECONDS)) 
            {
                try 
                {

                    // Validate if the deposit amount is valid
                    if (validate(amount, "deposit")) 
                    {
                        // Update the balance by increment
                        updateBalance(amount);

                        // Return withdraw success
                        System.out.println(this.name + " has successfully deposited: Current balance = " + this.balance);
                        return true;
                    } 
                    else 
                    {
                        // Return deposit failed
                        System.out.println("Deposit failed");
                        return false;
                    }

                } finally {
                    lock.unlock();
                }

            // Lock failed
            } else {
                System.out.println("Deposit timeout: Could not acquire lock");
                return false;
            }
        } 
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    // Place a Transfer amount to destination BankAccount
    // Only one thread may use this method at a time (synchronized)
    // Implements transfer() from Transferable interface
    @Override
    public boolean transfer(BankAccount destination, float amount) {

        // Validate inputs
        if (destination == null || amount <= 0) {
            return false;
        }

        // Deadlock prevention: lock ordering
        BankAccount first = this.hashCode() < destination.hashCode() ? this : destination;
        BankAccount second = this.hashCode() < destination.hashCode() ? destination : this;

        try 
        {

            // Timeout to prevent first deadlocks
            if (first.lock.tryLock(2, TimeUnit.SECONDS)) 
            {
                try 
                {

                    // Timeout to prevent second deadlocks
                    if (second.lock.tryLock(2, TimeUnit.SECONDS)) 
                    {
                        try 
                        {

                            // Attempt to withdraw from this account
                            if (this.validate(amount, "withdraw")) 
                            {

                                // Update balances atomically
                                this.updateBalance(-amount);
                                destination.updateBalance(amount);

                                return true;
                            }

                            // Withdrawal failed
                            return false;

                        } 
                        finally 
                        {
                            second.lock.unlock();
                        }
                    }
                } 
                finally 
                {
                    first.lock.unlock();
                }
            }

            // Lock failed
            System.out.println("Transfer timeout: Could not acquire locks");
            return false;

        } 
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    // Override toString() to provide custom useful information on printing BankAccount directly
    @Override
    public String toString() 
    {
        // Using StringBuilder optimizes performance
        StringBuilder sb = new StringBuilder();
        sb.append("BankAccount of ")
            .append(name)
            .append(": balance = ")
            .append(balance);
        return sb.toString();
    }
}