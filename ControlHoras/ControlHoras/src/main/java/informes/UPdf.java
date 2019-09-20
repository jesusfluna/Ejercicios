package informes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class UPdf {

	public static boolean generarPDF(String nombre,List<RegistroInforme> ri,int mes, int año,String ruta) {
		boolean res = true;
		
		try {
			Document iTextpdf = new Document();
			PdfWriter.getInstance(iTextpdf, new FileOutputStream(ruta+"//WEB-INF//descargas//Horas_"+mes+"-"+año+".pdf"));
			iTextpdf.open();
			
			//Encabezado
			iTextpdf.add(new Paragraph("REGISTRO HORARIO RDL 08/2019",FontFactory.getFont("arial",22, Font.BOLD,BaseColor.BLACK)));
			iTextpdf.add(new Paragraph(" "));
			
			//Primera tabla
			PdfPTable tabla1 = new PdfPTable(3);
			PdfPCell celdaN = new PdfPCell(new Paragraph(nombre));
			celdaN.setColspan(3);
			tabla1.addCell(celdaN);
			
			tabla1.addCell("MES");
			PdfPCell celdaF = new PdfPCell(new Paragraph(mes+"/"+año));
			celdaF.setColspan(2);
			tabla1.addCell(celdaF);
			
			iTextpdf.add(tabla1);
			iTextpdf.add(new Paragraph(" "));
			
			PdfPTable tabla2 = new PdfPTable(6);
			tabla2.addCell("DÍA");
			tabla2.addCell("HORA INICIO");
			tabla2.addCell("HORA FIN");
			tabla2.addCell("TOTALES TRABAJO");
			tabla2.addCell("HORAS VIAJE");
			tabla2.addCell("FIRMA TRABAJADOR");
			
			int total = 0;
			for (RegistroInforme registroInforme : ri) {
				for (int i=0;i<6;i++) {
					switch (i) {
					case 0:
						tabla2.addCell(registroInforme.getDia());
						break;
					case 1:
						tabla2.addCell(registroInforme.getInicio());
						break;
					case 2:
						tabla2.addCell(registroInforme.getFin());
						break;
					case 3:
						tabla2.addCell(""+registroInforme.getHorasTotales());
						total+=registroInforme.getHorasTotales();
						break;
					case 4:
						tabla2.addCell(""+registroInforme.getHorasViaje());
						break;
					case 5:
						tabla2.addCell("");
						break;
					default:
						break;
					}
				}
			}
			
			PdfPCell celdat = new PdfPCell(new Paragraph("TOTAL HORAS MENSUALES",FontFactory.getFont("arial",10, Font.BOLD,BaseColor.BLACK)));
			celdat.setColspan(2);
			tabla2.addCell(celdat);
			tabla2.addCell("");
			tabla2.addCell(""+total);
			tabla2.addCell("");
			tabla2.addCell("");
			
			iTextpdf.add(tabla2);

			iTextpdf.add(new Paragraph("NOTA 1: En las casillas que se indica \"referencia estandar\", con la firma del trabajador se entiende que éste ha empleado su "
	        		+ "horario de referencia de acuerdo al sistema de autoorganización de la jornada laboral mediante flexibilidad horaria que se dispone en "
	        		+ "la empresa para permitir la conciliación de vida privada con vida laboral"));
			
			iTextpdf.add(new Paragraph("NOTA 2: Horario de referencia del empleado: 09:00 a 14:30 - 15:30 a 18:00"));
			
			iTextpdf.add(new Paragraph("NOTA 3: Este registro se dispone con la única finalidad de dar cumplimiento al Art. 31.7 del Estatuto de los Trabajadores, y al citado RDL 08/2019."));
			
			iTextpdf.add(new Paragraph("Firma de la empresa",FontFactory.getFont("arial",16, Font.BOLD,BaseColor.BLACK)));
                    
			
			iTextpdf.close(); 
		} catch (FileNotFoundException e) {
			res = false;
			System.out.println("ERROR: "+e.getMessage());
		} catch (DocumentException e) {
			res = false;
			System.out.println("ERROR: "+e.getMessage());
		}
		
		return res;
	}
}
