package SmartExpenseSplitter;
import java.util.List;

public class SplitService {
    private ExpenseDAO dao = new ExpenseDAOIMPL();

    public void processNewExpense(String group, String desc, double amount, String payer) {
        dao.createGroup(group); // Ensure group exists
        dao.addExpense(group, desc, amount, payer);
        System.out.println("✅ Expense recorded: $" + amount + " for " + desc);
    }

    public void showGroupBalances(String groupName) {
        List<ExpenseDTO> expenses = dao.getExpensesByGroup(groupName);
        if (expenses.isEmpty()) {
            System.out.println("🔍 No records found for " + groupName);
            return;
        }

        System.out.println("\n--- 📊 " + groupName.toUpperCase() + " SUMMARY ---");
        double total = 0;
        for (ExpenseDTO e : expenses) {
            System.out.printf("%-12s paid $%-8.2f for %s\n", e.getPayer(), e.getAmount(), e.getDescription());
            total += e.getAmount();
        }
        
        System.out.println("---------------------------------");
        System.out.println("💰 Total Group Spending: $" + total);
        System.out.println("⚖️ Each person's share (assuming 2 people): $" + (total / 2));
    }

    public void listGroups() {
        System.out.println("📂 Active Groups: " + dao.getAllGroups());
    }
}