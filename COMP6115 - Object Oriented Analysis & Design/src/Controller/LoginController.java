	package Controller;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Connector.Connector;
import Model.Cashier;
import Model.Employee;
import Model.HumanResources;
import Model.Manager;
import Model.ProductManagement;
import View.CashierView;
import View.HumanResourcesView;
import View.Login;
import View.ManagerView;
import View.ProductManagementView;

public class LoginController implements ActionListener{
	
	private JTextField TF_email, TF_password;
	private JButton btn_login;
	private JFrame frame;
	private Connection con = Connector.connect();
	private HumanResources hr;
	private Employee emp;
	
	
	
	public LoginController(JTextField TF_email, JTextField TF_password, JButton btn_login,JFrame frame) {
		super();
		this.TF_email = TF_email;
		this.TF_password = TF_password;
		this.btn_login = btn_login;
		this.frame = frame;
	}

	public static void LoginView() {
		new Login();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btn_login) {
			if(TF_email.getText().equals("") && TF_password.getText().equals("")) JOptionPane.showMessageDialog(frame, "Email dan Password Tidak dapat Kosong", "Login Message", JOptionPane.INFORMATION_MESSAGE);
			else if(TF_email.getText().equals("")) JOptionPane.showMessageDialog(frame, "Email Tidak dapat Kosong", "Login Message", JOptionPane.INFORMATION_MESSAGE);
			else if(TF_password.getText().equals("")) JOptionPane.showMessageDialog(frame    , "Password Tidak dapat Kosong", "Login Message", JOptionPane.INFORMATION_MESSAGE);
			else {
				try {
					Statement stat =  con.createStatement();
					int RoleID = 0;
					String username = TF_email.getText(), password = TF_password.getText(), Role = "";
					String query = String.format("select * from Employee WHERE EmployeeUsername = '%s' AND EmployeePassword = '%s'", username, password);
					ResultSet res = stat.executeQuery(query);
					
					if(res.isBeforeFirst() == false) {
						JOptionPane.showMessageDialog(frame, "Wrong Email or Password", "Failed Login", JOptionPane.ERROR_MESSAGE);
					}else {
						while(res.next()) {
							RoleID = res.getInt("RoleID");
							emp = new Employee(res.getInt("EmployeeID"), RoleID, res.getInt("EmployeeSalary"), res.getString("EmployeeName"), res.getString("EmployeeUsername"), res.getString("EmployeeStatus"), res.getString("EmployeePassword"));
						}
						
						String query1 = String.format("SELECT * FROM employeerole WHERE RoleID = %d", RoleID);
						ResultSet res1 = stat.executeQuery(query1);
						while(res1.next()) {
							Role = res1.getString("RoleName");
						}
						
						
						if(emp.getStatus().equals("Active")) {
							if(Role.equals("Manager")) {
								Manager manager = new Manager(emp.getID(), emp.getRoleID(), emp.getSalary(), emp.getFullname(), emp.getUsername(), emp.getStatus(), emp.getPassword());
								frame.setVisible(false);
								new ManagerView(manager);
								
							}else if(Role.equals("Human Resources")) {
								hr = new HumanResources(emp.getID(), emp.getRoleID(), emp.getSalary(), emp.getFullname(), emp.getUsername(), emp.getStatus(), emp.getPassword());
								frame.setVisible(false);
								new HumanResourcesView(hr);
								
							}else if(Role.equals("Product Management")) {
								ProductManagement pm = new ProductManagement(emp.getID(), emp.getRoleID(), emp.getSalary(), emp.getFullname(), emp.getUsername(), emp.getStatus(), emp.getPassword());
								frame.setVisible(false);
								new ProductManagementView(pm);
								
							}else if(Role.equals("Cashier")) {
								Cashier chr = new Cashier(emp.getID(), emp.getRoleID(), emp.getSalary(), emp.getFullname(), emp.getUsername(), emp.getStatus(), emp.getPassword());
								frame.setVisible(false);
								new CashierView(chr);
							}else {
								JOptionPane.showMessageDialog(frame, "Wrong Email or Password", "Failed Login", JOptionPane.ERROR_MESSAGE);
							}						
						}else {
							JOptionPane.showMessageDialog(frame, "Please Change To Active Role Account!!", "Failed Login", JOptionPane.ERROR_MESSAGE);
						}
					}
					
					
					
					
//					if(res.next() == false) {
//						JOptionPane.showMessageDialog(frame, "Wrong Email or Password", "Failed Login", JOptionPane.ERROR_MESSAGE);
//					}else {
//						
//						
//					}
					
				}catch(SQLException err) {
					err.printStackTrace();
				}
			}
		}
	}
	
}
