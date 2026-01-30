package SmartExpenseSplitter;
public class ExpenseDTO {
    private int id;
    private String groupName;
    private String description;
    private double amount;
    private String payer;

    public ExpenseDTO(int id, String groupName, String description, double amount, String payer) {
        this.id = id;
        this.groupName = groupName;
        this.description = description;
        this.amount = amount;
        this.payer = payer;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getGroupName() { return groupName; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getPayer() { return payer; }
}