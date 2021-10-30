package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Connector.Connector;
import Controller.ManagerEmployeeController;
import Model.Employee;
import Model.Manager;

public class ManagerEmployeeView {

	private JFrame frame = new JFrame("Manager");
	private Manager mng;
	private JTextField TF_search, TF_FullName, TF_Username, TF_Role, TF_salary,  TF_employeeID;
	private JLabel fullName_Label, EmployeeID_label , TitleCRUD_label, Fullname_TFLabel, username_Label, role_Label, salary_label;
	private JTable table;
	private DefaultTableModel model_table;
	private JButton btn_search, btn_insert, btn_update, btn_delete, btn_logout;
	
	public ManagerEmployeeView(Manager mng) {
		super();
		this.mng = mng;
		FramePlay();
	}
	
	public void FramePlay() {
		setFrame();
		setPanel();
		setTableMode();
		RefreshData();
	}
	
	private void setPanel() {
		
		
		
		// SET PANEL
		JPanel content = new JPanel();
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		content.setLayout(new BorderLayout());
		
		
		// Top Panel
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new GridLayout(2, 2));
		fullName_Label = new JLabel("Nama Manager: "+mng.getFullname());
		titlePanel.add(fullName_Label);
		btn_logout = new JButton("Back To Main");
		titlePanel.add(btn_logout);
		
		
		TF_search = new JTextField();
		btn_search = new JButton("Search By ID");
		titlePanel.add(TF_search);
		titlePanel.add(btn_search);
		content.add(titlePanel, BorderLayout.NORTH);
		
		// CENTER PANEL
		JScrollPane jspane = new JScrollPane();
		table = new JTable();
		jspane.setViewportView(table);
		content.add(jspane, BorderLayout.CENTER);
				
		
		// Bottom TextField
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(12, 1));
		
		// TITLE CRUD
		TitleCRUD_label = new JLabel("Employee [ INSERT | DELETE | UPDATE ]");
		bottomPanel.add(TitleCRUD_label);
		
		// ID
		EmployeeID_label = new JLabel("Employee ID [ Only Delete and Update ]");
		TF_employeeID = new JTextField();
		bottomPanel.add(EmployeeID_label);
		bottomPanel.add(TF_employeeID);
		
		// Fullname TextField
		Fullname_TFLabel = new JLabel("Full Name");
		TF_FullName = new JTextField();
		bottomPanel.add(Fullname_TFLabel);
		bottomPanel.add(TF_FullName);
		
		// Username TextField
		username_Label = new JLabel("Username");
		TF_Username = new JTextField();
		bottomPanel.add(username_Label);
		bottomPanel.add(TF_Username);
		
		// Role TextField
		role_Label = new JLabel("Role [ Manager | Human Resources | Product Management | Cashier ]");
		TF_Role = new JTextField();
		bottomPanel.add(role_Label);
		bottomPanel.add(TF_Role);
		
		// Salary Text Field
		salary_label = new JLabel("Salary");
		TF_salary = new JTextField();
		bottomPanel.add(salary_label);
		bottomPanel.add(TF_salary);
		
		// Panel Button
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 4));
		buttonPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
		btn_insert = new JButton("Insert Employee");
		buttonPanel.add(btn_insert);
		btn_delete = new JButton("Fire Employee");
		buttonPanel.add(btn_delete);
		btn_update = new JButton("Update Employee");
		buttonPanel.add(btn_update);
		
		bottomPanel.add(buttonPanel);
		content.add(bottomPanel, BorderLayout.SOUTH);
		frame.setContentPane(content);
		
//		
		ManagerEmployeeController mngec= new ManagerEmployeeController(mng, frame, TF_search, TF_FullName, TF_Username, TF_Role, TF_salary, TF_employeeID, btn_search, btn_insert, btn_update, btn_delete, model_table, table, btn_logout); 
		btn_search.addActionListener(mngec);
		btn_delete.addActionListener(mngec);
		btn_insert.addActionListener(mngec);
		btn_update.addActionListener(mngec);
		btn_logout.addActionListener(mngec);
		
	}
	
	private void setFrame() {
		frame.setSize(900, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
	private void setTableMode() {
		model_table = new DefaultTableModel(new String[] {"ID", "FullName", "Status", "Username", "Password", "Salary", "Role Name"}, 0);
		table.setModel(model_table);
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
	
	
	
}
