package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utilidades.Herramienta;

public class Empleado {
	private String nombre;
	private String usuario;
	private String contrasena;
	private String email;
	
	public Empleado() {
		nombre = "";
		usuario = "";
		contrasena = "";
		email = "";
	}
	
	public Empleado(String nombre,String usuario, String contrasena, String email) {
		this.nombre = nombre;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.email = email;
	}

	public Empleado(String nombre,String usuario, String contrasena) {
		this.nombre = nombre;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.email = usuario+"@inerco.com";
	}
	
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public String getUsuario() {return usuario;}
	public void setUsuario(String usuario) {this.usuario = usuario;}
	public String getContrasena() {return contrasena;}
	public void setContrasena(String contrasena) {this.contrasena = contrasena;}
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}

	@Override
	public String toString() {
		return "Empleado [nombre=" + nombre + ", usuario=" + usuario + ", contrasena=" + contrasena + ", email=" + email+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contrasena == null) ? 0 : contrasena.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		if (contrasena == null) {
			if (other.contrasena != null)
				return false;
		} else if (!contrasena.equals(other.contrasena))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	public static Empleado buscarEmpleado(String usuario) {
		Empleado e = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(Herramienta.datosConexionBD()[0], Herramienta.datosConexionBD()[1], Herramienta.datosConexionBD()[2]);
			Statement st = connection.createStatement();
			ResultSet res = st.executeQuery("SELECT * from empleado WHERE usuario like '"+usuario+"';");
			
			while(res.next()) {
				e = new Empleado(res.getString(1),res.getString(2),res.getString(3),res.getString(4));
			
			}
			res.close();
			connection.close();
		} catch (SQLException error) {
			System.out.println("ERROR: "+error.getMessage());
		} catch (ClassNotFoundException error) {
			System.out.println("ERROR: "+error.getMessage());
		}
		return e;
	}
}
