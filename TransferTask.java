import java.util.concurrent.Semaphore;

public class TransferTask implements Runnable {

    private BankAccount from;
    private BankAccount to;
    private float amount;
    private Semaphore semaphore;

    // Constructor without semaphore
    public TransferTask(BankAccount from, BankAccount to, float amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.semaphore = null;
    }

    // Constructor with semaphore
    public TransferTask(BankAccount from, BankAccount to, float amount, Semaphore semaphore) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try 
        {
            // Acquire a permit before executing transfer
            // If no permit is available, the thread will BLOCK here
            // This limits how many transfers run concurrently
            if (semaphore != null) 
            {
                semaphore.acquire();
            }

            System.out.println(from.getName() + " transfered " + this.amount + " to " + to.getName());

            boolean success = from.transfer(to, amount);

            System.out.println(Thread.currentThread().getName() + " transfer " + (success ? "SUCCESS" : "FAILED"));

        } 
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
        } 
        finally 
        {
            // Always release permit after execution
            // This allows another waiting thread to proceed
            if (semaphore != null) 
            {
                semaphore.release();
            }
        }
    }
}