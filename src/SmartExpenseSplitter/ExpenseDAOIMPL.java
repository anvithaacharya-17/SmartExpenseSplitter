package SmartExpenseSplitter;
import java.sql.*;
import java.util.*;

public class ExpenseDAOIMPL implements ExpenseDAO {
    private final String URL = "jdbc:mysql://localhost:3306/expense_splitter";
    private final String USER = "root";
    private final String PASS = "your_password"; //Change password

    @Override
    public void createGroup(String groupName) {
        String sql = "INSERT IGNORE INTO `groups` (group_name) VALUES (?)";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, groupName);
            p.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void addExpense(String group, String desc, double amount, String payer) {
        String sql = "INSERT INTO expenses (group_id, description, amount, payer) " +
                     "VALUES ((SELECT group_id FROM `groups` WHERE group_name=?), ?, ?, ?)";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, group);
            p.setString(2, desc);
            p.setDouble(3, amount);
            p.setString(4, payer);
            p.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<ExpenseDTO> getExpensesByGroup(String groupName) {
        List<ExpenseDTO> list = new ArrayList<>();
        String sql = "SELECT e.id, g.group_name, e.description, e.amount, e.payer " +
                     "FROM expenses e JOIN `groups` g ON e.group_id = g.group_id WHERE g.group_name = ?";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, groupName);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                list.add(new ExpenseDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5)));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public List<String> getAllGroups() {
        List<String> groups = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT group_name FROM `groups`")) {
            while (rs.next()) groups.add(rs.getString(1));
        } catch (SQLException e) { e.printStackTrace(); }
        return groups;
    }
}