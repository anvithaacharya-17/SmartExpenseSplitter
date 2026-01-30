package SmartExpenseSplitter;
import java.util.List;

public interface ExpenseDAO {
    void addExpense(String group, String desc, double amount, String payer);
    List<ExpenseDTO> getExpensesByGroup(String groupName);
    List<String> getAllGroups();
    void createGroup(String groupName);
}