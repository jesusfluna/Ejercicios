package informes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import beans.Dia;
import utilidades.Herramienta;

public class InformeAnual {
	private List<RegistrosResumen> ano;

	public InformeAnual() {
		this.ano = new LinkedList<RegistrosResumen>();
	}
	
	public InformeAnual(List<RegistrosResumen> año) {
		this.ano = año;
	}
	
	public InformeAnual(int año,String user) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		List<Dia> dias = new LinkedList<Dia>();
		
		for (int i=1 ; i<= (calendar.get(Calendar.MONTH)+1); i++) {
			dias.addAll(Dia.todosDiasMes(user, i));
		}
		
		this.ano = new LinkedList<RegistrosResumen>();
		
		for (int i=1 ; i<= (calendar.get(Calendar.MONTH)+1); i++) {
			for(int j=1; j <= Herramienta.diasDelMes(i, año); j++) {
				this.getAño().add(trasfDiaARegistroResumen(dias,j,i,año));
			}
		}
		
		System.out.println("Finalizacion del metodo");
	}

	
	
	public List<RegistrosResumen> getAño() { return ano; }
	public void setAño(List<RegistrosResumen> año) { this.ano = año; }

	@Override
	public String toString() {
		return "InformeAnual [año=" + ano + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
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
		InformeAnual other = (InformeAnual) obj;
		if (ano == null) {
			if (other.ano != null)
				return false;
		} else if (!ano.equals(other.ano))
			return false;
		return true;
	}
	
	private RegistrosResumen trasfDiaARegistroResumen(List<Dia> dias,int dia,int mes, int año) {
		RegistrosResumen res = new RegistrosResumen();
		res.setDia(dia);
		res.setMes(mes);
		
		if(mes == 5 && dia>15) {
			System.out.println("HOLA");
		}
		
		try {
			Date dateIni = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(año+"-"+Herramienta.formatearNumero(mes)+"-"+Herramienta.formatearNumero(dia)+" 00:00:00.00");
			Date dateFin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(año+"-"+Herramienta.formatearNumero(mes)+"-"+Herramienta.formatearNumero(dia)+" 23:59:00.00");

			int cont = 0;
			for (Dia d : dias) {
				if(d.getEntrada().after(dateIni) && d.getEntrada().before(dateFin)) {
					cont+=d.getTotalHoras();
				}
			}
			
			
			res.setHorasTotal(cont);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return res;
	}
}
