package beans;

public class User {
	private int id;
	private String userName;
	private String password;
	private String privileges;//admin,user
	
	public User(int id,String userName, String password, String privileges) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.privileges = privileges;
	}
	
	public User() {
		this.id = 0;
		this.userName = "";
		this.password = "";
		this.privileges = "";
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPrivileges() {
		return privileges;
	}
	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}
	public String toString() {
		return this.getPrivileges()+"-"+id+" => User :"+this.getUserName()+":"+this.getPassword();
	}
}
