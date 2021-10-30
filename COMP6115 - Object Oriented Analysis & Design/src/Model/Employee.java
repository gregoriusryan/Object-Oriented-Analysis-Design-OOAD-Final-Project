package Model;

public class Employee {
	private int ID, RoleID, Salary;
	private String Fullname, Username, Status, Password;
	private String RoleName;
	
	public Employee(int iD, int roleID, int salary, String fullname, String username, String status, String password) {
		super();
		ID = iD;
		RoleID = roleID;
		Salary = salary;
		Fullname = fullname;
		Username = username;
		Status = status;
		Password = password;
	}
	
	
	
	public String getRoleName() {
		return RoleName;
	}



	public void setRoleName(String roleName) {
		RoleName = roleName;
	}



	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getRoleID() {
		return RoleID;
	}
	public void setRoleID(int roleID) {
		RoleID = roleID;
	}
	public int getSalary() {
		return Salary;
	}
	public void setSalary(int salary) {
		Salary = salary;
	}
	public String getFullname() {
		return Fullname;
	}
	public void setFullname(String fullname) {
		Fullname = fullname;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
	
	
}
