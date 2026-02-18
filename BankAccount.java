abstract public class BankAccount implements Transferable {

    private float balance;
    private String name;
    private final int MAX_WITHDRAW = 10000;

	public BankAccount(float balance, String name)
	{
		this.balance = balance;
		this.name = name;
	}

    public float getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

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

    public boolean updateBalance(float amount) 
    {
        this.balance += amount;
        return true;
    }

    public synchronized boolean withdraw(float amount) 
    {
        // Validate if the withdrawal amount is valid
        if (validate(amount, "withdraw")) 
        {
            // Update the balance by minus
            updateBalance(-amount);

            // Return withdraw success
            System.out.println(this.name + " has successfully withdrawed: Current balance = " + this.balance);
            return true;
        } 
        else 
        {
            // Return withdraw failed
            System.out.println("Withdraw failed");
            return false;
        }
    }

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

    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("BankAccount of ")
            .append(name)
            .append(": balance = ")
            .append(balance);
        return sb.toString();
    }
}

