public class CheckingAccount extends BankAccount {

    private float currentOverdraft;
    private final float MAX_OVERDRAFT = 500.0f; // Over draft limit

    public CheckingAccount(float balance, String name) {
        super(balance, name);
        this.currentOverdraft = 0.0f;
    }
    public CheckingAccount(float balance, String name, float currentOverdraft) {
        super(balance, name);
        this.currentOverdraft = currentOverdraft;
    }

    public double getAvailableFunds() {
        return balance + (MAX_OVERDRAFT - currentOverdraft);
    }

    // Override withdraw for overdraft amount
    @Override
    public boolean withdraw(float amount) {

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
    if (amount <= balance) 
    {
        // Perform a normal withdrawal from the balance
        balance -= amount;
    } 
    else 
    {
        // Use overdraft for the remaining amount after balance is depleted
        float overdraftUsed = amount - balance;
        balance = 0;
        currentOverdraft += overdraftUsed;
    }

    // Withdrawal successful
    return true;

}
