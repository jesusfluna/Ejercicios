package beans;

public class Alerts {
	private int id;
	private int id_user;
	private int id_download;
	private String text;
	private String user;
	private String download;
	private int status; //0= no leido , 1= leido

	public Alerts() {
		this.id = 0;
		this.id_user = 0;
		this.id_download = 0;
		this.user = "";
		this.download = "";
		this.text = "";
		this.status = 0;
	}
	public Alerts(int id, String text , int id_user, int id_download) {
		this.id = id;
		this.id_user = id_user;
		this.id_download = id_download;
		this.text = text;
		this.user = "";
		this.download = "";
		this.status = 0;
	}
	public Alerts(int id, String text , int id_user, int id_download,String user, String download, int status) {
		this.id = id;
		this.id_user = id_user;
		this.id_download = id_download;
		this.text = text;
		this.user = user;
		this.download = download;
		this.status = status;
	}
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public int getId_download() {
		return id_download;
	}
	public void setId_download(int id_download) {
		this.id_download = id_download;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
