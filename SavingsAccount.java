// This is a child of BankAccount (inheritance)
public class SavingsAccount extends BankAccount {

    private float interestRate;

    // Constructor with default interest rate
    public SavingsAccount(float balance, String name) 
    {
        super(balance, name);
        this.interestRate = 0.02f; // Default interest rate
    }

    // Constructor with initial balance, account name, and interest rate
    public SavingsAccount(float balance, String name, float interestRate) 
    {
        super(balance, name);
        this.interestRate = interestRate;
    }

    // Return the account's interest rate
    public double getInterestRate() 
    {
        return this.interestRate;
    }

    // Add the interest earned to current balance
    public boolean addInterest() {
        if (this.interestRate >= 0 && this.interestRate <= 1)
        {
            // Add interest rate to balance
            super.updateBalance(super.getBalance() * this.interestRate);

            // Return add interest successful
            return true;
        }
        else
        {
            // Return add interest failed
            System.out.println("Invalid interest: Invalid range");
            return false;
        }
        
    }
}
