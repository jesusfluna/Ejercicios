package utilidades;


public class Herramienta {

		public static String formatearNumero(int n) {
			String res = ""+n;
			
			if(n<10) {
				res = 0+res;
			}
			
			return res;
		}
		
		
		public static int diasDelMes(int mes, int año) {
			int ultimoDia = 31;
			
			if(mes == 4 || mes == 6 || mes == 9 || mes == 11) {
				ultimoDia = 30;
			}else if(mes==2){
				if(año%4==0 && año%100!=0) {
					ultimoDia = 29;
				}else {
					ultimoDia = 28;
				}
			}
			
			return ultimoDia;
		}
		
		public static String[] datosConexionBD() {
			String [] res = {"jdbc:postgresql://localhost:5432/INERCOCONTROLHORAS","postgres","12345"};
			return res;
		}
}
