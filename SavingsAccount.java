public class SavingsAccount extends BankAccount {

    private float interestRate;

    public SavingsAccount(float balance, String name) 
    {
        super(balance, amount);
        this.interestRate = 0.02f; // Default interest rate
    }

    public SavingsAccount(float balance, String name, float interestRate) 
    {
        super(balance, amount);
        this.interestRate = interestRate;
    }

    public double getInterestRate() 
    {
        return interestRate;
    }

    public boolean addInterest() {
        if ( 0 <= interestRate <= 1)
        {
            // Add interest rate to balance
            balance += balance * interestRate();

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
