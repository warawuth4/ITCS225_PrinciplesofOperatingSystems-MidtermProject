import java.util.*;
import java.util.concurrent.Semaphore;

public class TestBank {
    public static void main(String[] args) throws InterruptedException {

        // TODO Task 1: Stack pointer analysis
        System.out.println("Task 1: Stack pointer analysis");
        BankAccount Customer1 = new SavingsAccount(1000, "Bob");
        Customer1.withdraw(67);
        Customer1.deposit(167);
        System.out.println(Customer1);
        System.out.println();
        // StackOverflowSimulation(0);

        // TODO Task 2: Heap allocator
        System.out.println("Task 2: Heap allocator");
        BankAccount Obj1 = new SavingsAccount(1000, "Jane");
        System.out.println(Obj1);
        BankAccount Obj2 = new SavingsAccount(1100, "John");
        System.out.println(Obj2);
        Obj1 = new SavingsAccount(0, "Jane new");
        Obj2 = null;
        System.out.println();

        // TODO Task 3: The "Reference vs Primitive" Expert
        System.out.println("Task 3: The \"Reference vs Primitive\" Expert");
        // Test reference type
        BankAccount Customer2 = new SavingsAccount(1000, "Pop");
        testReference(Customer2);
        System.out.println(Customer2);

        // Test primitive type
        int Primitive = 1000;
        System.out.println("Initial primitive value: " + Primitive);
        testPrimitive(Primitive);
        System.out.println("Primitive value (original): " + Primitive);

        // StringBuilder explained
        String string1 = "Bob";
        string1 += " VIP"; // Create a new StringBuilder every concatenation
        System.out.println(string1);

        StringBuilder string2 = new StringBuilder("Pop");
        string2.append(" VIP"); // Append in-place - only one final string is created
        System.out.println(string2);
        System.out.println();
     
        // TODO Additional: Scheduling Method: Priority Scheduling
        testPriorityScheduling(Customer1, Customer2);
        System.out.println();

        
        // TODO Additional: Concurrency
        testConcurrency(Customer1, Customer2);
       

    }

    public static void StackOverflowSimulation(int index) {
        System.out.println("Creating object " + index);
        BankAccount aaaa = new SavingsAccount(1000, "Bob");
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
    
    public static void testConcurrency(BankAccount acc1, BankAccount acc2) 
            throws InterruptedException {

        System.out.println("=== Concurrency Test ===");

        // Only allows 2 threads at a time
        Semaphore semaphore = new Semaphore(2); 

        Thread t1 = new Thread(new TransferTask(acc1, acc2, 200f, semaphore), "T1");
        Thread t2 = new Thread(new TransferTask(acc2, acc1, 150f, semaphore), "T2");


        // Start both threads
        t1.start();
        t2.start();

        // Wait for both threads to finish
        t1.join();
        t2.join();

        System.out.println("=== Concurrency Test Finished ===");
    }
    
    public static void testPriorityScheduling(BankAccount acc1, BankAccount acc2) throws InterruptedException {

        System.out.println("=== Priority Scheduling Test ===");

        Thread highPriority = new Thread(
                new TransferTask(acc1, acc2, 300f)
        );

        Thread lowPriority = new Thread(
                new TransferTask(acc2, acc1, 100f)
        );

        // Apply priority scheduling
        highPriority.setPriority(Thread.MAX_PRIORITY);
        lowPriority.setPriority(Thread.MIN_PRIORITY);

        // Start concurrently
        highPriority.start();
        lowPriority.start();

        // Wait for both threads to finish
        highPriority.join();
        lowPriority.join();

        // NOTE: Thread priority is only a scheduling hint.
        // The JVM/OS may ignore it.
        // Execution order is NOT guaranteed.

        System.out.println("=== Priority Scheduling Test Finished ===");
    }
}
