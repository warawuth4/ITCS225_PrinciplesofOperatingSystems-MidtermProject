public class TransferTask implements Runnable {

    private BankAccount from;
    private BankAccount to;
    private float amount;

    public TransferTask(BankAccount from, BankAccount to, float amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public void run() {
    	System.out.println(from.getName() + " transfered " + this.amount + " to " + to.getName());
        boolean success = from.transfer(to, amount);
        System.out.println(Thread.currentThread().getName()
                + " transfer " + (success ? "SUCCESS" : "FAILED"));
    }
}