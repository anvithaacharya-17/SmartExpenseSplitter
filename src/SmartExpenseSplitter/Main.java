package SmartExpenseSplitter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SplitService service = new SplitService();

        System.out.println("💸 WELCOME TO SMART EXPENSE SPLITTER 💸");

        while (true) {
            System.out.println("\n1. ➕ Add New Expense");
            System.out.println("2. 📊 View Group Balances");
            System.out.println("3. 📂 List All Groups");
            System.out.println("0. 🚪 Exit");
            System.out.print("➤ Selection: ");

            String choice = sc.nextLine();
            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("Group Name (e.g., GoaTrip): "); String g = sc.nextLine();
                        System.out.print("Expense Description: "); String d = sc.nextLine();
                        System.out.print("Amount: "); double a = Double.parseDouble(sc.nextLine());
                        System.out.print("Who Paid? "); String p = sc.nextLine();
                        service.processNewExpense(g, d, a, p);
                    }
                    case "2" -> {
                        System.out.print("Enter Group Name: ");
                        service.showGroupBalances(sc.nextLine());
                    }
                    case "3" -> service.listGroups();
                    case "0" -> {
                        System.out.println("Goodbye!");
                        System.exit(0);
                    }
                    default -> System.out.println("⚠️ Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: Invalid input data.");
            }
        }
    }
}