package informes;

public class RegistrosResumen {
	private int dia;
	private int mes;
	private int horasTotal;
	
	public RegistrosResumen() {
		this.dia = 0;
		this.mes = 0;
		this.horasTotal = 0;
	}
	
	public RegistrosResumen(int dia,int mes,int horasTotal) {
		this.dia = dia;
		this.mes = mes;
		this.horasTotal = horasTotal;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getHorasTotal() {
		return horasTotal;
	}

	public void setHorasTotal(int horasTotal) {
		this.horasTotal = horasTotal;
	}

	@Override
	public String toString() {
		return "RegistrosResumen [dia=" + dia + ", mes=" + mes + ", horasTotal=" + horasTotal + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dia;
		result = prime * result + horasTotal;
		result = prime * result + mes;
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
		RegistrosResumen other = (RegistrosResumen) obj;
		if (dia != other.dia)
			return false;
		if (horasTotal != other.horasTotal)
			return false;
		if (mes != other.mes)
			return false;
		return true;
	}
	
	
}
