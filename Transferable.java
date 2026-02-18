public interface Transaction {

    boolean deposit(float amount);
    boolean withdraw(float amount);
    boolean transfer(BankAccount destination, float amount);
}
