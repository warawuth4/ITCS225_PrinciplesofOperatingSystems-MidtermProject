// This is a child of BankAccount (inheritance)
public class CheckingAccount extends BankAccount {

    private float currentOverdraft;
    private final float MAX_OVERDRAFT = 500.0f; // Over draft limit

    // Constructor with zero overdraft
    public CheckingAccount(float balance, String name) {
        super(balance, name);
        this.currentOverdraft = 0.0f; //
    }

    // Constructor with current balance, account name, and initial overdraft amount
    public CheckingAccount(float balance, String name, float currentOverdraft) {
        super(balance, name);
        this.currentOverdraft = currentOverdraft;
    }

    // Return current balance + available overdraft amount
    public double getAvailableFunds() {
        return super.getBalance() + (MAX_OVERDRAFT - currentOverdraft);
    }

    // Override withdraw that takes into account overdraft
    @Override
    public synchronized boolean withdraw(float amount) {

        // Check if the amount is negative
        if (amount <= 0) 
        {
            System.out.println("Invalid amount: Negative amount");
            return false;
        }

        // Get the total funds available for withdrawal, including overdraft
	    double available = getAvailableFunds();
	
	    // Check if the requested amount exceeds the available funds
	    if (amount > available) 
	    {
	        System.out.println("Invalid amount: exceeds available withdrawal limit");
	        return false;
	    }
	
	    // Check whether the withdrawal can be covered by the current balance
	    if (amount <= super.getBalance()) 
	    {
	        // Perform a normal withdrawal from the balance
	        super.updateBalance(-amount);
	    } 
	    else 
	    {
	        // Use overdraft for the remaining amount after balance is depleted
	        float overdraftUsed = amount - super.getBalance();
	        super.updateBalance(-(amount-overdraftUsed));
	        currentOverdraft += overdraftUsed;
	    }
	
	    // Withdrawal successful
	    return true;
	    }

}
