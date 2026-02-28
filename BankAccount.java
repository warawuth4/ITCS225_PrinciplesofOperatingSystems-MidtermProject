abstract public class BankAccount implements Transferable {

    private float balance;
    private String name;
    private final int MAX_WITHDRAW = 10000;

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
    public synchronized boolean withdraw(float amount) 
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

    // Deposit in current balance by amount
    // Only one thread may use this method at a time (synchronized)
    // Implements deposit() from Transferable interface
    @Override
    public synchronized boolean deposit(float amount) 
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
    }

    // Place a Transfer amount to destination BankAccount
    // Only one thread may use this method at a time (synchronized)
    // Implements transfer() from Transferable interface
    @Override
    public synchronized boolean transfer(BankAccount destination, float amount) {
    	
    	synchronized (this) {
	        // Validate inputs
	        if (destination == null || amount <= 0) {
	            return false;
	        }
	
	        // Attempt to withdraw from this account
	        if (this.withdraw(amount)) {
	        	
	            // Deposit into destination account
	            destination.deposit(amount);
	            return true;
	        }
	
	        // Withdrawal failed
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

