package SmartExpenseSplitter_Project;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

/*
=====================================
SMART EXPENSE SPLITTER
=====================================

CREATE DATABASE expensedb;
USE expensedb;

CREATE TABLE members(
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50)
);

CREATE TABLE expenses(
id INT PRIMARY KEY AUTO_INCREMENT,
payer VARCHAR(50),
description VARCHAR(100),
amount DOUBLE
);

INSERT INTO members(name) VALUES
('Alex'),('John'),('Sara');
*/

class DBConnection{

public static Connection getConnection(){
Connection con=null;
try{
Class.forName("com.mysql.cj.jdbc.Driver");
con=DriverManager.getConnection(
"jdbc:mysql://localhost:3306/expensedb",
"root",
"anvi@27"
);
}
catch(Exception e){
JOptionPane.showMessageDialog(null,e);
}
return con;
}
}

public class SmartExpenseSplitter_Project extends JFrame implements ActionListener{

JButton addMemberBtn;
JButton addExpenseBtn;
JButton viewBtn;
JButton splitBtn;
JButton summaryBtn;

SmartExpenseSplitter_Project(){

setTitle("Smart Expense Splitter");

JLabel title=new JLabel("Smart Expense Splitter");
title.setBounds(100,30,220,30);

addMemberBtn=new JButton("Add Member");
addExpenseBtn=new JButton("Add Expense");
viewBtn=new JButton("View Expenses");
splitBtn=new JButton("Split Bill");
summaryBtn=new JButton("Summary");

addMemberBtn.setBounds(100,80,180,35);
addExpenseBtn.setBounds(100,130,180,35);
viewBtn.setBounds(100,180,180,35);
splitBtn.setBounds(100,230,180,35);
summaryBtn.setBounds(100,280,180,35);

addMemberBtn.addActionListener(this);
addExpenseBtn.addActionListener(this);
viewBtn.addActionListener(this);
splitBtn.addActionListener(this);
summaryBtn.addActionListener(this);

add(title);
add(addMemberBtn);
add(addExpenseBtn);
add(viewBtn);
add(splitBtn);
add(summaryBtn);

setSize(400,430);
setLayout(null);
setVisible(true);
setLocationRelativeTo(null);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}

public void actionPerformed(ActionEvent e){

if(e.getSource()==addMemberBtn)
new AddMember();

if(e.getSource()==addExpenseBtn)
new AddExpense();

if(e.getSource()==viewBtn)
new ViewExpenses();

if(e.getSource()==splitBtn)
new SplitExpense();

if(e.getSource()==summaryBtn)
new SummaryReport();

}

public static void main(String args[]){
new SmartExpenseSplitter_Project();
}

}

class AddMember extends JFrame implements ActionListener{

JTextField nameField;
JButton saveBtn;

AddMember(){
setTitle("Add Member");

JLabel l1=new JLabel("Member Name");
l1.setBounds(50,70,100,30);

nameField=new JTextField();
nameField.setBounds(150,70,150,30);

saveBtn=new JButton("Save");
saveBtn.setBounds(120,170,100,40);
saveBtn.addActionListener(this);

add(l1);add(nameField);add(saveBtn);

setSize(400,280);
setLayout(null);
setVisible(true);
setLocationRelativeTo(null);
}

public void actionPerformed(ActionEvent e){
try{
Connection con=DBConnection.getConnection();
PreparedStatement ps=con.prepareStatement(
"insert into members(name) values(?)");
ps.setString(1,nameField.getText());
ps.executeUpdate();
JOptionPane.showMessageDialog(this,"Member Added");
nameField.setText("");
con.close();
}
catch(Exception ex){
JOptionPane.showMessageDialog(this,ex);
}
}

}

class AddExpense extends JFrame implements ActionListener{

JTextField payerField,descField,amountField;
JButton addBtn;

AddExpense(){
setTitle("Add Expense");

JLabel l1=new JLabel("Paid By");
JLabel l2=new JLabel("Description");
JLabel l3=new JLabel("Amount");

payerField=new JTextField();
descField=new JTextField();
amountField=new JTextField();

l1.setBounds(50,50,100,30);
payerField.setBounds(150,50,150,30);

l2.setBounds(50,100,100,30);
descField.setBounds(150,100,150,30);

l3.setBounds(50,150,100,30);
amountField.setBounds(150,150,150,30);

addBtn=new JButton("Add Expense");
addBtn.setBounds(110,230,120,40);
addBtn.addActionListener(this);

add(l1);add(payerField);
add(l2);add(descField);
add(l3);add(amountField);
add(addBtn);

setSize(400,350);
setLayout(null);
setVisible(true);
setLocationRelativeTo(null);
}

public void actionPerformed(ActionEvent e){
try{
Connection con=DBConnection.getConnection();
PreparedStatement ps=con.prepareStatement(
"insert into expenses(payer,description,amount) values(?,?,?)"
);
ps.setString(1,payerField.getText());
ps.setString(2,descField.getText());
ps.setDouble(3,Double.parseDouble(amountField.getText()));
ps.executeUpdate();
JOptionPane.showMessageDialog(this,"Expense Added");
payerField.setText("");
descField.setText("");
amountField.setText("");

con.close();
}
catch(Exception ex){
JOptionPane.showMessageDialog(this,ex);
}
}

}

class ViewExpenses extends JFrame{

JTextArea area;

ViewExpenses(){
setTitle("Expense Ledger");

area=new JTextArea();
JScrollPane sp=new JScrollPane(area);
sp.setBounds(20,20,480,300);
add(sp);

loadExpenses();

setSize(550,400);
setLayout(null);
setVisible(true);
setLocationRelativeTo(null);
}

void loadExpenses(){
try{
Connection con=DBConnection.getConnection();
Statement st=con.createStatement();
ResultSet rs=st.executeQuery("select * from expenses");

area.append("ID\tPayer\tDescription\tAmount\n");

while(rs.next()){
area.append(
rs.getInt(1)+"\t"+
rs.getString(2)+"\t"+
rs.getString(3)+"\t"+
rs.getDouble(4)+"\n"
);
}
con.close();
}
catch(Exception e){
JOptionPane.showMessageDialog(this,e);
}
}

}

class SplitExpense extends JFrame{

SplitExpense(){
setTitle("Split Calculation");

JTextArea area=new JTextArea();
area.setBounds(30,30,320,180);
add(area);

try{
Connection con=DBConnection.getConnection();
Statement st=con.createStatement();

ResultSet rs1=st.executeQuery(
"select sum(amount) from expenses");
rs1.next();
double total=rs1.getDouble(1);

ResultSet rs2=st.executeQuery(
"select count(*) from members");
rs2.next();
int members=rs2.getInt(1);

double share=total/members;

area.append("Total Expense : "+total+"\n\n");
area.append("Members : "+members+"\n\n");
area.append("Each Pays : "+share);

con.close();
}
catch(Exception e){
area.setText(e.toString());
}

setSize(420,300);
setLayout(null);
setVisible(true);
setLocationRelativeTo(null);
}

}

class SummaryReport extends JFrame{

SummaryReport(){
setTitle("Member Settlement Summary");

JTextArea area=new JTextArea();
JScrollPane sp=new JScrollPane(area);
sp.setBounds(20,20,450,300);
add(sp);

try{
Connection con=DBConnection.getConnection();
Statement st=con.createStatement();

ResultSet t=st.executeQuery("select sum(amount) from expenses");
t.next();
double total=t.getDouble(1);

ResultSet c=st.executeQuery("select count(*) from members");
c.next();
int n=c.getInt(1);

double equalShare=total/n;

ResultSet members=st.executeQuery("select name from members");

area.append("Member\tPaid\tBalance\n");
area.append("-----------------------------\n");

while(members.next()){
String name=members.getString(1);

PreparedStatement ps=con.prepareStatement(
"select ifnull(sum(amount),0) from expenses where payer=?"
);
ps.setString(1,name);
ResultSet paid=ps.executeQuery();
paid.next();

double contributed=paid.getDouble(1);
double balance=contributed-equalShare;

area.append(
name+"\t"+
contributed+"\t"+
balance+"\n"
);
}

con.close();
}
catch(Exception e){
area.setText(e.toString());
}

setSize(530,400);
setLayout(null);
setVisible(true);
setLocationRelativeTo(null);
}

}