package informes;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import beans.Dia;
import utilidades.Herramienta;

public class RegistroInforme {
	private String dia;
	private String inicio;
	private String fin;
	private float horasTotales;
	private int horasViaje;
	
	public RegistroInforme() {
		this.dia = ""/*"00/00"*/;
		this.inicio = ""/*"00:00"*/;
		this.fin = ""/*"00:00"*/;
		this.horasTotales = 0F;
		this.horasViaje = 0;
	}
	
	@SuppressWarnings("deprecation")
	public RegistroInforme(List<Dia> dias) {
		if(dias.size()!=0) {
			this.dia = (Herramienta.formatearNumero(dias.get(0).getEntrada().getDate()))+"/"+(Herramienta.formatearNumero(dias.get(0).getEntrada().getMonth()+1));
			
			Timestamp menorFecha=null;
			Timestamp mayorFecha=null;
			
			this.horasTotales = 0F;
			this.horasViaje = 0;
			
			for (Dia dia : dias) {
				horasTotales+=dia.getTotalHoras();
				horasViaje+=dia.getHorasViaje();
				
				if(menorFecha==null) {
					menorFecha = dia.getEntrada();
				}else {
					if(menorFecha.after(dia.getEntrada())) {
						menorFecha = dia.getEntrada();
					}
				}
				
				if(mayorFecha==null) {
					mayorFecha = dia.getSalida();
				}else {
					if(mayorFecha.before(dia.getSalida())) {
						mayorFecha = dia.getSalida();
					}
				}
			}
			
			this.inicio = Herramienta.formatearNumero(menorFecha.getHours())+":"+Herramienta.formatearNumero(menorFecha.getMinutes());
			this.fin = Herramienta.formatearNumero(mayorFecha.getHours())+":"+Herramienta.formatearNumero(mayorFecha.getMinutes());
		}else {
			this.dia = ""/*"00/00"*/;;
			this.inicio = ""/*"00:00"*/;
			this.fin = ""/*"00:00"*/;
			this.horasTotales = 0F;
			this.horasViaje = 0;
		}
	}
	
	@SuppressWarnings("deprecation")
	public RegistroInforme(List<Dia> dias, int m, int d) {
		if(dias.size()!=0) {
			this.dia = (Herramienta.formatearNumero(dias.get(0).getEntrada().getDate()))+"/"+(Herramienta.formatearNumero(dias.get(0).getEntrada().getMonth()+1));
			
			Timestamp menorFecha=null;
			Timestamp mayorFecha=null;
			
			this.horasTotales = 0F;
			this.horasViaje = 0;
			
			for (Dia dia : dias) {
				horasTotales+=dia.getTotalHoras();
				horasViaje+=dia.getHorasViaje();
				
				if(menorFecha==null) {
					menorFecha = dia.getEntrada();
				}else {
					if(menorFecha.after(dia.getEntrada())) {
						menorFecha = dia.getEntrada();
					}
				}
				
				if(mayorFecha==null) {
					mayorFecha = dia.getSalida();
				}else {
					if(dia.getSalida()!=null) {
						if(mayorFecha.before(dia.getSalida())) {
							mayorFecha = dia.getSalida();
						}					
					}
				}
			}
			this.inicio = Herramienta.formatearNumero(menorFecha.getHours())+":"+Herramienta.formatearNumero(menorFecha.getMinutes());
			
			if(mayorFecha!=null) {
				this.fin = Herramienta.formatearNumero(mayorFecha.getHours())+":"+Herramienta.formatearNumero(mayorFecha.getMinutes());
			}else {
				this.fin = null;
			}
			
		}else {
			this.dia = Herramienta.formatearNumero(d)+"/"+Herramienta.formatearNumero(m);
			this.inicio = ""/*"00:00"*/;
			this.fin = ""/*"00:00"*/;
			this.horasTotales = 0F;
			this.horasViaje = 0;
		}
	}

	public String getDia() {return dia;}
	public void setDia(String dia) {this.dia = dia;}
	public String getInicio() {return inicio;}
	public void setInicio(String inicio) {this.inicio = inicio;}
	public String getFin() {return fin;}
	public void setFin(String fin) {this.fin = fin;}
	public float getHorasTotales() {return horasTotales;}
	public void setHorasTotales(float horasTotales) {this.horasTotales = horasTotales;}
	public int getHorasViaje() {return horasViaje;}
	public void setHorasViaje(int horasViaje) {this.horasViaje = horasViaje;}
	
	public static List<RegistroInforme> registroMes(String usuario, int mes){
		List<RegistroInforme> listaMes = new LinkedList<RegistroInforme>();
		
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int ultimoDia = 31;
		if(mes == 4 || mes == 6 || mes == 9 || mes == 11) {
			ultimoDia = 30;
		}else if(mes==2){
			if(calendar.get(Calendar.YEAR)%4==0 && calendar.get(Calendar.YEAR)%100!=0) {
				ultimoDia = 29;
			}else {
				ultimoDia = 28;
			}
		}
		
		try {
			for (int i = 1; i <= ultimoDia; i++) {
				listaMes.add(new RegistroInforme(Dia.todosDiasDeUnDia(usuario, mes, i),mes,i));
			}
		}catch(Exception e) {
			System.out.println("ERROR:"+e.getMessage());
		}

		
		return listaMes;
	}
}
