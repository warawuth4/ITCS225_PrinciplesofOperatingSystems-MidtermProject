public class TestBank {
    public static void main(String[] args) {

        // TODO Task 1: Stack pointer analysis
        System.out.println("Task 1: Stack pointer analysis");
        BankAccount Customer1 = new BankAccount(1000, "Bob");
        Customer1.withdraw(67);
        Customer1.deposit(167);
        System.out.println(Customer1);
        System.out.println();
        // StackOverflowSimulation(0);

        // TODO Task 2: Heap allocator
        System.out.println("Task 2: Heap allocator");
        BankAccount Obj1 = new BankAccount(1000, "Jane");
        System.out.println(Obj1);
        BankAccount Obj2 = new BankAccount(1000, "John");
        System.out.println(Obj2);
        Obj1 = new BankAccount(0, "Jane new");
        Obj2 = null;
        System.out.println();

        // TODO Task 3: The "Reference vs Primitive" Expert
        System.out.println("Task 3: The \"Reference vs Primitive\" Expert");
        // Test reference type
        BankAccount Customer2 = new BankAccount(1000, "Pop");
        testReference(Customer2);
        System.out.println(Customer2);

        // Test primitive type
        int Primitive = 1000;
        testPrimitive(Primitive);
        System.out.println("Primitive value (real): " + Primitive);

        // Test
        String customer1 = "Bob";
        customer1 += " VIP"; // Create a new StringBuilder every concatenation

        StringBuilder customer2 = new StringBuilder("Pop");
        customer2.append(" VIP"); // Append in-place - only one final string is created
        System.out.println(customer2);


    }

    public static void StackOverflowSimulation(int index) {
        System.out.println("Creating object " + index);
        BankAccount aaaa = new BankAccount(1000, "Bob");
        StackOverflowSimulation(index + 1);
    }

    public static void testReference(BankAccount person) {
        person.withdraw(67);
        person.deposit(167);
    }

    public static void testPrimitive(int number) {
        number -= 1676;
        System.out.println("Primitive value (copy): " + number);
    }
}
