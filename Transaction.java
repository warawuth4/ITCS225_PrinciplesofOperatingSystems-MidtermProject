public interface Transaction {
    boolean validate(float amount, String type);

    boolean updateBalance(float amount);
}
