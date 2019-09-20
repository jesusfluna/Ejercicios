package beans;

public class Downloads {
	private int id;
	private String url;
	private String name;
	private String owner;
	private int id_user;
	private boolean alert;
	
	public Downloads() {
		this.id = 0;
		this.url = "";
		this.owner = "";
		this.name = "";
		this.id_user = 0;
		this.alert = false;
	}

	public Downloads(int id, String url, String owner, int id_user ,String name) {
		this.id = id;
		this.url = url;
		this.owner = owner;
		this.name = name;
		this.id_user = id_user;
		this.alert = false;
	}

	public Downloads(int id, String url, String owner, int id_user ,String name,boolean alert) {
		this.id = id;
		this.url = url;
		this.owner = owner;
		this.name = name;
		this.id_user = id_user;
		this.alert = alert;
	}
	
	
	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public java.lang.String toString() {
		return this.name+":"+this.owner+" => "+this.url+"";
	}
}
