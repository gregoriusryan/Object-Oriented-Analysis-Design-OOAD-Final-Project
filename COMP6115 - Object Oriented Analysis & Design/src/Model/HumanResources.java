package Model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import Connector.Connector;

public class HumanResources extends Employee{
	
	private Connection con = Connector.connect();
	
	public HumanResources(int iD, int roleID, int salary, String fullname, String username, String status,
			String password) {
		super(iD, roleID, salary, fullname, username, status, password);
		// TODO Auto-generated constructor stub
	}
	
	public boolean addEmployee(int RoleID, String Fullname, String Username, int Salary) {
		try {
			Statement stat = con.createStatement();
			String query = String.format("INSERT INTO Employee(RoleID, EmployeeName, EmployeeUsername, Employeesalary, EmployeeStatus, EmployeePassword) VALUES (%d, '%s', '%s', %d, '%s', '%s')", RoleID, Fullname, Username, Salary, "Active", Username);
			if(stat.executeUpdate(query) == 0) return false;
			else return true;
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteEmployee(int ID) throws SQLException {
		try {
			Statement stat = con.createStatement();
			String query = String.format("UPDATE EMPLOYEE SET EmployeeStatus = 'Not Active' WHERE EmployeeID = %d", ID);
			if(stat.executeUpdate(query) == 0) return false;
			else return true;
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	

}
