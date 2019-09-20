package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import utilidades.Herramienta;

public class Dia {
	private Timestamp entrada;
	private Timestamp salida;
	private int horasViaje;
	private float totalHoras;
	private int id;
	
	public Dia() {
		entrada = new Timestamp(System.currentTimeMillis());
		salida = null;
		horasViaje = 0;
		totalHoras = 0;
		id = 0;
	}
	
	public Dia(Timestamp entrada,Timestamp salida,int horasViaje,float totalHoras,int id) {
		this.entrada = entrada;
		this.salida = salida;
		this.horasViaje = horasViaje;
		this.totalHoras = totalHoras;
		this.id = id;
	}

	public Timestamp getEntrada() {return entrada;}
	public void setEntrada(Timestamp entrada) {this.entrada = entrada;}
	public Timestamp getSalida() {return salida;}
	public void setSalida(Timestamp salida) {this.salida = salida;}
	public int getHorasViaje() {return horasViaje;}
	public void setHorasViaje(int horasViaje) {this.horasViaje = horasViaje;}
	public float getTotalHoras() {return totalHoras;}
	public void setTotalHoras(float totalHoras) {this.totalHoras = totalHoras;}
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}

	@Override
	public String toString() {
		return "Dia [Id="+id+", entrada=" + entrada + ", salida=" + salida + ", horasViaje=" + horasViaje + ", totalHoras="
				+ totalHoras + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entrada == null) ? 0 : entrada.hashCode());
		result = prime * result + horasViaje;
		result = prime * result + ((salida == null) ? 0 : salida.hashCode());
		result = prime * result + Float.floatToIntBits(totalHoras);
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
		Dia other = (Dia) obj;
		if (entrada == null) {
			if (other.entrada != null)
				return false;
		} else if (!entrada.equals(other.entrada))
			return false;
		if (horasViaje != other.horasViaje)
			return false;
		if (salida == null) {
			if (other.salida != null)
				return false;
		} else if (!salida.equals(other.salida))
			return false;
		if (Float.floatToIntBits(totalHoras) != Float.floatToIntBits(other.totalHoras))
			return false;
		return true;
	}
	
	public static void newDia(String usuario) {		
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(Herramienta.datosConexionBD()[0], Herramienta.datosConexionBD()[1], Herramienta.datosConexionBD()[2]);
			Statement st = connection.createStatement();
			st.executeUpdate("insert into dia (entrada) values ('"+new Timestamp(System.currentTimeMillis())+"');");
			ResultSet res = st.executeQuery("select * from dia where id in (Select max(id) from dia);");
			
			String id = "";
			while(res.next()) {
				id = res.getString(5);
			}
			
			st.executeUpdate("insert into dia_empleado (id_dia,usuario) values ('"+id+"','"+usuario+"');");

			res.close();
			connection.close();
		} catch (SQLException error) {
			System.out.println("ERROR: "+error.getMessage());
		} catch (ClassNotFoundException error) {
			System.out.println("ERROR: "+error.getMessage());
		}
	}
	
	public static Dia ultimoDiaUsuario(String usuario) {
		Dia dia = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(Herramienta.datosConexionBD()[0], Herramienta.datosConexionBD()[1], Herramienta.datosConexionBD()[2]);
			Statement st = connection.createStatement();
			ResultSet res = st.executeQuery("select * from dia inner join dia_empleado on dia.id = dia_empleado.id_dia where id in (Select max(id) from dia) and dia_empleado.usuario like '"+usuario+"';");
			
			while(res.next()) {
				dia = new Dia(res.getTimestamp(1),res.getTimestamp(2),res.getInt(3),res.getFloat(4),res.getInt(5));
			}
			
			res.close();
			connection.close();
		} catch (SQLException error) {
			System.out.println("ERROR: "+error.getMessage());
		} catch (ClassNotFoundException error) {
			System.out.println("ERROR: "+error.getMessage());
		}
		
		return dia;
	}
	
	public static boolean actualizarDia(Dia dia) {
		boolean res = true;
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(Herramienta.datosConexionBD()[0], Herramienta.datosConexionBD()[1], Herramienta.datosConexionBD()[2]);
			Statement st = connection.createStatement();
			String consulta; 
			
			if(dia.getSalida()==null) {
				consulta = "update dia set entrada=to_timestamp('"+dia.getEntrada()+"','YYYY-MM-DD HH24:MI:SS.FF6'), salida=null, horasviaje="+dia.getHorasViaje()+", totalhoras="+dia.getTotalHoras()+" where id="+dia.getId()+";";
			}else {
				consulta = "update dia set entrada=to_timestamp('"+dia.getEntrada()+"','YYYY-MM-DD HH24:MI:SS.FF6'), salida=to_timestamp('"+dia.getSalida()+"','YYYY-MM-DD HH24:MI:SS.FF6'), horasviaje="+dia.getHorasViaje()+", totalhoras="+dia.getTotalHoras()+" where id="+dia.getId()+";";
			}
			st.executeUpdate(consulta);
			
			connection.close();
		} catch (SQLException error) {
			System.out.println("ERROR: "+error.getMessage());
			res=false;
		} catch (ClassNotFoundException error) {
			System.out.println("ERROR: "+error.getMessage());
			res=false;
		}
		
		return res;
	}

	public static List<Dia> diaActualUsuario(String usuario) {
		List<Dia> dias = new LinkedList<Dia>();
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(Herramienta.datosConexionBD()[0], Herramienta.datosConexionBD()[1], Herramienta.datosConexionBD()[2]);
			Statement st = connection.createStatement();
			
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.get(Calendar.YEAR);
			
			String fechaHoy = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
			
			ResultSet res = st.executeQuery("select * from dia inner join dia_empleado on dia.id = dia_empleado.id_dia where dia_empleado.usuario like '"+usuario+"' and entrada > to_timestamp('"+fechaHoy+" 00:00:00.00','YYYY-MM-DD HH24:MI:SS.FF6');");
			
			while(res.next()) {
				dias.add(new Dia(res.getTimestamp(1),res.getTimestamp(2),res.getInt(3),res.getFloat(4),res.getInt(5)));
			}
			
			res.close();
			connection.close();
		} catch (SQLException error) {
			System.out.println("ERROR: "+error.getMessage());
		} catch (ClassNotFoundException error) {
			System.out.println("ERROR: "+error.getMessage());
		}
		
		return dias;
	}
	
	public static Dia getDiaId(int id) {
		Dia dia = new Dia();
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(Herramienta.datosConexionBD()[0], Herramienta.datosConexionBD()[1], Herramienta.datosConexionBD()[2]);
			Statement st = connection.createStatement();
			ResultSet res = st.executeQuery("select * from dia where id="+id+";");
			
			while(res.next()) {
				dia.setEntrada(res.getTimestamp(1));
				dia.setSalida(res.getTimestamp(2));
				dia.setHorasViaje(res.getInt(3));
				dia.setTotalHoras(res.getFloat(4));
				dia.setId(id);
			}
			
			res.close();
			connection.close();
		} catch (SQLException error) {
			System.out.println("ERROR: "+error.getMessage());
		} catch (ClassNotFoundException error) {
			System.out.println("ERROR: "+error.getMessage());
		}
		
		return dia;
	}

	public static List<Dia> todosDiasMes(String user,int mes) {
		List<Dia> listaMes = new LinkedList<Dia>();
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(Herramienta.datosConexionBD()[0], Herramienta.datosConexionBD()[1], Herramienta.datosConexionBD()[2]);
			Statement st = connection.createStatement();
			
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
			int ultimoDia = Herramienta.diasDelMes(mes, calendar.get(Calendar.YEAR));;
			String fechaInicio = calendar.get(Calendar.YEAR)+"-"+mes+"-01";
			String fechaFin = calendar.get(Calendar.YEAR)+"-"+mes+"-"+ultimoDia;
			
			ResultSet res = st.executeQuery("select * from dia inner join dia_empleado on dia.id = dia_empleado.id_dia where dia_empleado.usuario like '"+user+"' and entrada > to_timestamp('"+fechaInicio+" 00:00:00.00','YYYY-MM-DD HH24:MI:SS.FF6') and entrada < to_timestamp('"+fechaFin+" 00:00:00.00','YYYY-MM-DD HH24:MI:SS.FF6');");
			
			while(res.next()) {
				listaMes.add(new Dia(res.getTimestamp(1),res.getTimestamp(2),res.getInt(3),res.getFloat(4),res.getInt(5)));
			}
			
			res.close();
			connection.close();
		} catch (SQLException error) {
			System.out.println("ERROR: "+error.getMessage());
		} catch (ClassNotFoundException error) {
			System.out.println("ERROR: "+error.getMessage());
		}
		
		return listaMes;
	}
	
	public static List<Dia> todosDiasDeUnDia(String user,int mes, int dia) {
		List<Dia> listaDias = new LinkedList<Dia>();
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(Herramienta.datosConexionBD()[0], Herramienta.datosConexionBD()[1], Herramienta.datosConexionBD()[2]);
			Statement st = connection.createStatement();
			
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
			String fechaInicio = calendar.get(Calendar.YEAR)+"-"+mes+"-"+dia;
			String fechaFin = calendar.get(Calendar.YEAR)+"-"+mes+"-"+dia;
			
			ResultSet res = st.executeQuery("select * from dia inner join dia_empleado on dia.id = dia_empleado.id_dia where dia_empleado.usuario like '"+user+"' and entrada > to_timestamp('"+fechaInicio+" 00:00:00.00','YYYY-MM-DD HH24:MI:SS.FF6') and entrada < to_timestamp('"+fechaFin+" 23:59:00.00','YYYY-MM-DD HH24:MI:SS.FF6');");
			
			while(res.next()) {
				listaDias.add(new Dia(res.getTimestamp(1),res.getTimestamp(2),res.getInt(3),res.getFloat(4),res.getInt(5)));
			}
			
			res.close();
			connection.close();
		} catch (SQLException error) {
			System.out.println("ERROR: "+error.getMessage());
		} catch (ClassNotFoundException error) {
			System.out.println("ERROR: "+error.getMessage());
		}
		
		return listaDias;
	}


}
