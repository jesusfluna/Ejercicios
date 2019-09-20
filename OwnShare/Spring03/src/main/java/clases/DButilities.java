package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Downloads;
import beans.User;

public class DButilities {
	
	/**Crea una conexion con una base de datos*/
	public static Connection conectarDB(String url,String user, String password) {
		Connection connection = null;
        try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
            System.out.println("No se cargaron los driver jdbc: ");
            e.printStackTrace();
		} catch (SQLException e) {
            System.out.println("No se pudo cargar la base de datos: ");
            e.printStackTrace();
		}
        return connection;
	}

	/**Cierra una conexion con una base de datos*/
	public static void desConectarDB(Connection con) {
			try {
				con.close();
			} catch (SQLException e) {
	            System.out.println("No se pudo cerrar correctamente la base de datos: ");
	            e.printStackTrace();
			}
	}
	
	/**Laza la consulta*/
	public static ResultSet ejecutarConsulta(Connection con, String consulta) {
		ResultSet res = null;
		try {
			Statement st = con.createStatement();
			res = st.executeQuery(consulta);
		} catch (SQLException e) {
            System.out.println("No se pudo realizar la consulta introducida en la base de datos: ");
            e.printStackTrace();
		}
		return res;
	}
	
	/**develve el usuario de la base de datos con el ID introducido
	 * @throws SQLException */
	public static User getUserById(int id,String url,String user, String pass) throws SQLException {
		User usuario = new User();
		
		Connection con = conectarDB(url, user, pass);
		ResultSet ress = DButilities.ejecutarConsulta(con, "SELECT * from users WHERE id= "+id+";");
		try {
			while(ress.next()) {
				usuario = new User(Integer.parseInt(ress.getString(1)), ress.getString(2), ress.getString(3), ress.getString(4));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ress.close();
		desConectarDB(con);
		return usuario;
	}
	
	/**devuelve el usuario de la base de datos con el ID introducido
	 * @throws SQLException */
	public static Downloads getDownloadById(int id,String url,String user, String pass) throws SQLException {
		Downloads down = new Downloads();
		User usuario = new User();
		
		Connection con = conectarDB(url, user, pass);
		ResultSet ress = DButilities.ejecutarConsulta(con, "SELECT * from downloads WHERE id= "+id+";");
		try {
			while(ress.next()) {
				usuario = getUserById(Integer.parseInt(ress.getString(4)),url,user, pass);
				down = new Downloads(Integer.parseInt(ress.getString(1)), ress.getString(2), usuario.getUserName(),Integer.parseInt(ress.getString(4)),ress.getString(3) );
			}
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ress.close();
		desConectarDB(con);
		return down;
	}
	
	public static void deleteDownloadById(int id,String url,String user, String pass) throws SQLException {
		Connection con = conectarDB(url, user, pass);
		ejecutarConsulta(con, "DELETE from downloads WHERE id= "+id+";");
		desConectarDB(con);
	}
	
	/**Comprueba si la descarga tiene alguna alarma asociada*/
	public static boolean hasAlertByDownloadId(int id,String url,String user, String pass) throws SQLException {
		Connection con = conectarDB(url, user, pass);
		ResultSet ress = ejecutarConsulta(con, "SELECT * from alerts WHERE id_descarga= "+id+";");
		
		int total = 0;
		try {
			while (ress.next()){
			   total++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ress.close();
		desConectarDB(con);
		return total>0;
	}
	
	public static void saveNewDownload(Downloads down,String url,String user, String pass) throws SQLException{
		Connection con = conectarDB(url, user, pass);
		ejecutarConsulta(con, "INSERT into downloads (url,name,id_user) values ('"+down.getUrl()+"','"+down.getName()+"','"+down.getId_user()+"');");
		con.close();
	}
	
	public static void saveNewAlert(int id_down,int id_user, String txt,String url,String user, String pass) throws SQLException{
		Connection con = conectarDB(url, user, pass);
		ejecutarConsulta(con, "INSERT into alerts (text,id_user,id_descarga,status) values ('"+txt+"','"+id_user+"','"+id_down+"',0);");
		con.close();
	}
	
	/**Listado de downloads
	 * @throws SQLException */
	public static List<Downloads> getDownloads(String url,String user, String pass) throws SQLException {
		List<Downloads> descargas = new ArrayList<Downloads>();
		
		Connection con = conectarDB(url, user, pass);
		ResultSet ress = DButilities.ejecutarConsulta(con, "SELECT * from downloads;");
		User usuario;
		try {
			while(ress.next()) {
				usuario = getUserById(Integer.parseInt(ress.getString(4)),url,user, pass);
				descargas.add(new Downloads(Integer.parseInt(ress.getString(1)), ress.getString(2), usuario.getUserName(),Integer.parseInt(ress.getString(4)),ress.getString(3)));
			}
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ress.close();
		desConectarDB(con);
		return descargas;
	}
	
	/**Listado de downloads de un usuario
	 * @throws SQLException */
	public static List<Downloads> getDownloadsByUserID(int id_user,String url,String user, String pass) {
		List<Downloads> descargas = new ArrayList<Downloads>();
		
		Connection con = conectarDB(url, user, pass);
		ResultSet ress = DButilities.ejecutarConsulta(con, "SELECT * from downloads WHERE id_user="+id_user+";");
		User usuario;
		try {
			while(ress.next()) {
				usuario = getUserById(Integer.parseInt(ress.getString(4)),url,user, pass);
				descargas.add(new Downloads(Integer.parseInt(ress.getString(1)), ress.getString(2), usuario.getUserName(),Integer.parseInt(ress.getString(4)),ress.getString(3)));
			}
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			ress.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		desConectarDB(con);
		return descargas;
	}
	
	
	/**Numero de alarmas no leidas del usuario
	 * @throws SQLException */
	public static String getNumAlertsNotRead(int id_user,String url,String user, String pass){
		Connection con = conectarDB(url, user, pass);
		ResultSet ress = DButilities.ejecutarConsulta(con, "select * from alerts where status = 0 and id_descarga in (select id from downloads where id_user = "+id_user+");");
		int res = 0;
		
		try {
			while(ress.next()) {
				res++;
			}
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			ress.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		desConectarDB(con);
		
		if(res==0) {
			return "";
		}else {
			
		}	return ""+res;

	}
}
