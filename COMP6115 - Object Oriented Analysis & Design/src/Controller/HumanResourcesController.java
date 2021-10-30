package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Connector.Connector;
import Model.Employee;
import Model.HumanResources;
import View.Login;

public class HumanResourcesController implements ActionListener{
	
	private HumanResources hr;
	private JFrame frame;
	private JTextField TF_search, TF_FullName, TF_Username, TF_Role, TF_salary,  TF_employeeID;
	private JButton btn_search, btn_insert, btn_update, btn_delete, btn_logout;
	private DefaultTableModel model_table;
	private JTable table;
	private Employee emp;
	
	public HumanResourcesController(HumanResources hr, JFrame frame, JTextField tF_search, JTextField tF_FullName,
			JTextField tF_Username, JTextField tF_Role, JTextField tF_salary, JTextField tF_employeeID,
			JButton btn_search, JButton btn_insert, JButton btn_update, JButton btn_delete, DefaultTableModel model_table, JTable table, JButton btn_logout) {
		super();
		this.hr = hr;
		this.frame = frame;
		TF_search = tF_search;
		TF_FullName = tF_FullName;
		TF_Username = tF_Username;
		TF_Role = tF_Role;
		TF_salary = tF_salary;
		TF_employeeID = tF_employeeID;
		this.btn_search = btn_search;
		this.btn_insert = btn_insert;
		this.btn_update = btn_update;
		this.btn_delete = btn_delete;
		this.model_table = model_table;
		this.btn_logout = btn_logout;
		this.table = table;
		tF_salary.setText("0");
	}
	
	private void RefreshData() {
		model_table.setRowCount(0);
		Connection con = Connector.connect();
		try {
			Vector<Employee> empList = new Vector<Employee>();
			String query = String.format("SELECT * FROM Employee");
			
			Statement stat = con.createStatement();
			ResultSet res = stat.executeQuery(query);
							
			while(res.next()) {
				empList.add(new Employee(res.getInt("EmployeeID"), res.getInt("RoleID"), res.getInt("EmployeeSalary"), res.getString("EmployeeName"), res.getString("EmployeeUsername"), res.getString("EmployeeStatus"), res.getString("EmployeePassword")));
				
			}
			
			for (Employee employee : empList) {
				String query1 = String.format("SELECT * FROM employeerole WHERE RoleID = %d", employee.getRoleID()), Role = "";
				ResultSet res1 = stat.executeQuery(query1);
				while(res1.next()) {
					Role = res1.getString("RoleName");
				}
				
				employee.setRoleName(Role);
			}
						
			
			for (Employee employee : empList) {
				model_table.addRow(new Object[] {
						employee.getID(),
						employee.getFullname(),
						employee.getStatus(),
						employee.getUsername(), 
						employee.getPassword(),
						employee.getSalary(),
						employee.getRoleName()
				});
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void setTableMode() {
		model_table = new DefaultTableModel(new String[] {"ID", "FullName", "Status", "Username", "Password", "Salary", "Role Name"}, 0);
		table.setModel(model_table);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btn_search) {
			
			setTableMode();
			if(TF_search.getText().equals("")) {				
				RefreshData();
				TF_employeeID.setText("");
				TF_FullName.setText("");
				TF_Username.setText("");
				TF_Role.setText("");
				TF_salary.setText("");
			}else {
				Connection con = Connector.connect();
				try {
					Vector<Employee> empList = new Vector<Employee>();
					String query = String.format("SELECT * FROM Employee WHERE EmployeeID = %d", Integer.valueOf(TF_search.getText()));
					
					Statement stat = con.createStatement();
					ResultSet res = stat.executeQuery(query);
									
					while(res.next()) {
						empList.add(new Employee(res.getInt("EmployeeID"), res.getInt("RoleID"), res.getInt("EmployeeSalary"), res.getString("EmployeeName"), res.getString("EmployeeUsername"), res.getString("EmployeeStatus"), res.getString("EmployeePassword")));
						
					}
					
					for (Employee employee : empList) {
						String query1 = String.format("SELECT * FROM employeerole WHERE RoleID = %d", employee.getRoleID()), Role = "";
						ResultSet res1 = stat.executeQuery(query1);
						while(res1.next()) {
							Role = res1.getString("RoleName");
						}
						
						employee.setRoleName(Role);
					}
								
					String Name = "", Username = "", Role = "";
					int Salary = 0, ID = 0;
					for (Employee employee : empList) {
						model_table.addRow(new Object[] {
								employee.getID(),
								employee.getFullname(),
								employee.getStatus(),
								employee.getUsername(), 
								employee.getPassword(),
								employee.getSalary(),
								employee.getRoleName()
						});
						ID = employee.getID();
						Name = employee.getFullname();
						Username = employee.getUsername();
						Role = employee.getRoleName();
						Salary = employee.getSalary();
					}
					
					TF_employeeID.setText(String.valueOf(ID));
					TF_FullName.setText(Name);
					TF_Username.setText(Username);
					TF_Role.setText(Role);
					TF_salary.setText(String.valueOf(Salary));
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
			
			
		}else if(e.getSource() == btn_insert) {
			
			setTableMode();
			
			try {
				
				String Fullname = TF_FullName.getText(), Username = TF_Username.getText(), Role = TF_Role.getText(), SalaryText = TF_salary.getText();
				int Salary = 0, RoleID = 0;
								
				setTableMode();
				String error = "";
				
				if(Fullname.equals("")) {
					error += "Field Nama Tidak Boleh Kosong!";
				}
				
				if(Username.equals("")) {
					error += "\nField Username Tidak Boleh Kosong!";
				}
				
				if(Role.equals("")) {
					error += "\nField Role Tidak Boleh Kosong!";
				}else {
					if(Role.equals("Manager")) RoleID = 1;
					else if(Role.equals("Human Resources")) RoleID = 2;
					else if(Role.equals("Product Management")) RoleID = 3;
					else if(Role.equals("Cashier")) RoleID = 4;
					
					if(RoleID == 0) {
						error += "\nRole Yang Dipilih Harus Sesuai!";
					}
				}
				
				if(SalaryText.equals("")) {
					error += "\nField Salary Harus Lebih Dari 0";
				}
				Salary = Integer.parseInt(SalaryText);
				
				if(Salary <= 0) {
					error += "Salary Harus lebih Besar Dari 0";
				}
				
				if(error.equals("")) {
					if(hr.addEmployee(RoleID, Fullname, Username, Salary)) {
						RefreshData();
						TF_employeeID.setText("");
						TF_FullName.setText("");
						TF_Username.setText("");
						TF_Role.setText("");
						TF_salary.setText("");
						TF_search.setText("");
						JOptionPane.showMessageDialog(frame, "Success Insert Employee");
					}else {
						JOptionPane.showMessageDialog(frame, "Failed Registered Employee");
					}
				}else {
					JOptionPane.showMessageDialog(frame, error);
				}
			}catch(Exception e2) {
				e2.printStackTrace();
			}
			RefreshData();
			
		}else if(e.getSource() == btn_update) {
			String EmpIDText = TF_employeeID.getText();
			int EmpID = 0;
			String Fullname = TF_FullName.getText(), Username = TF_Username.getText(), Role = TF_Role.getText(), SalaryText = TF_salary.getText();
			int Salary = 0, RoleID = 0;
							
			setTableMode();
			String error = "";
			
			if(EmpIDText.equals("")) {
				error += "Field ID Harus Diisi";
			}else {
				EmpID = Integer.valueOf(TF_employeeID.getText());
			}
			if(Fullname.equals("")) {
				error += "Field Nama Tidak Boleh Kosong!";
			}
			
			if(Username.equals("")) {
				error += "\nField Username Tidak Boleh Kosong!";
			}
			
			if(Role.equals("")) {
				error += "\nField Role Tidak Boleh Kosong!";
			}else {
				if(Role.equals("Manager")) RoleID = 1;
				else if(Role.equals("Human Resources")) RoleID = 2;
				else if(Role.equals("Product Management")) RoleID = 3;
				else if(Role.equals("Cashier")) RoleID = 4;
				
				if(RoleID == 0) {
					error += "\nRole Yang Dipilih Harus Sesuai!";
				}
			}
			
			if(SalaryText.equals("")) {
				error += "\nField Salary Harus Lebih Dari 0";
			}
			Salary = Integer.parseInt(SalaryText);
			
			if(Salary <= 0) {
				error += "Salary Harus lebih Besar Dari 0";
			}
			Connection con = Connector.connect();
			setTableMode();
			
			try {
				Statement stat = con.createStatement();
				String query = String.format("UPDATE Employee SET EmployeeName = '%s', EmployeeUsername = '%s', EmployeeSalary = %d, RoleID = %d WHERE EmployeeID = %d", Fullname, Username, Salary, RoleID, EmpID);
				if(stat.executeUpdate(query) == 0) {
					JOptionPane.showMessageDialog(frame, "Failed Update Employee");
				}else {
					RefreshData();					
					TF_employeeID.setText("");
					TF_FullName.setText("");
					TF_Username.setText("");
					TF_Role.setText("");
					TF_salary.setText("");
					TF_search.setText("");
					JOptionPane.showMessageDialog(frame, "Success Update Employee");
				}
			}catch(SQLException e2) {
				e2.printStackTrace();
			}
			
			
			
			
		}else if(e.getSource() == btn_delete) {
			String EmpIDText = TF_employeeID.getText();
			int EmpID = 0;
			setTableMode();
			String error = "";
			
			if(EmpIDText.equals("")) {
				error += "Field ID Harus Diisi";
			}else {
				EmpID = Integer.valueOf(TF_employeeID.getText());
			}
			
			if(error.equals("")) {
				try {
					if(hr.deleteEmployee(EmpID)) {
						JOptionPane.showMessageDialog(frame, "Employee Has Been Fired");
						RefreshData();
						TF_employeeID.setText("");
						TF_FullName.setText("");
						TF_Username.setText("");
						TF_Role.setText("");
						TF_salary.setText("");
						TF_search.setText("");
					}else {
						JOptionPane.showMessageDialog(frame, "There's Error! Can't Fired Employee");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}else {
				JOptionPane.showMessageDialog(frame, error);
			}
			
			RefreshData();
		}else if(e.getSource() == btn_logout) {
			new Login();
			frame.setVisible(false);
		}
	}
	
	
	
}
