public class Account {
    private String name;
    private String type;
    private double interest;

    public Account() {
        this.name = null;
        this.type = null;
        this.interest = 0;
    }

    public Account(String name, String type, double interest) {
        this.name = name;
        this.type = type;
        this.interest = interest;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getInterest() {
        return interest;
    }
}
