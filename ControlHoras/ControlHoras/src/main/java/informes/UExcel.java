package informes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

public class UExcel {

	public static String generarExcel(String nombre,List<RegistroInforme> ri,int mes,int ano,String ruta) {
		String res = "";
		
		HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, "REGISTRO JORNADA");
        
        CellStyle cabecera = workbook.createCellStyle();
        Font cellFont = workbook.createFont();
        cellFont.setBold(true);
        cabecera.setFont(cellFont);
        cabecera.setAlignment(HorizontalAlignment.CENTER);
        HSSFCellStyle style=workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        
        CellStyle negrita = workbook.createCellStyle();
        Font cellFontAux = workbook.createFont();
        cellFontAux.setBold(true);
        negrita.setWrapText(true); 
        negrita.setFont(cellFontAux);
        negrita.setBorderBottom(BorderStyle.THIN);
        negrita.setBorderTop(BorderStyle.THIN);
        negrita.setBorderRight(BorderStyle.THIN);
        negrita.setBorderLeft(BorderStyle.THIN);
        
        Row fila = sheet.createRow(0);
        Cell celda = fila.createCell(0);
        celda.setCellValue("REGISTRO HORARIO RDL 08/2019");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
        celda.setCellStyle(cabecera);
        
        fila = sheet.createRow(1);
        celda = fila.createCell(0);
        celda.setCellValue(nombre);
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,1));
        celda.setCellStyle(negrita);
        celda = fila.createCell(1);
        celda.setCellStyle(negrita);
        
        fila = sheet.createRow(2);
        celda = fila.createCell(0);
        celda.setCellStyle(negrita);
        celda.setCellValue("MES");
        celda = fila.createCell(1);
        celda.setCellValue(mes+"/"+ano);
        celda.setCellStyle(negrita);
        
        fila = sheet.createRow(3);//Fila en blanco
        fila = sheet.createRow(4);
        
        celda = fila.createCell(0);
        negrita.setAlignment(HorizontalAlignment.CENTER);
		celda.setCellStyle(negrita);
		celda.setCellValue("DÍA");
		
    	celda = fila.createCell(1);
		celda.setCellStyle(negrita);
		celda.setCellValue("HORA INICIO TRABAJO");
		
    	celda = fila.createCell(2);
		celda.setCellStyle(negrita);
		celda.setCellValue("HORA FIN TRABAJO");
		
    	celda = fila.createCell(3);
		celda.setCellStyle(negrita);
		celda.setCellValue("HORAS TOTALES");
		
    	celda = fila.createCell(4);
		celda.setCellStyle(negrita);
		celda.setCellValue("HORAS VIAJES");
		
    	celda = fila.createCell(5);
		celda.setCellStyle(negrita);
		celda.setCellValue("FIRMA TRABAJADOR");
        
		CellStyle alDerc = workbook.createCellStyle();
		alDerc.setAlignment(HorizontalAlignment.RIGHT);
		alDerc.setBorderBottom(BorderStyle.THIN);
		alDerc.setBorderTop(BorderStyle.THIN);
		alDerc.setBorderRight(BorderStyle.THIN);
		alDerc.setBorderLeft(BorderStyle.THIN);
        
        int cont = 5;
        for (RegistroInforme registro : ri) {
        	fila = sheet.createRow(cont);
        	
        	celda = fila.createCell(0);
    		celda.setCellStyle(style);
    		celda.setCellValue(registro.getDia());
    		celda.setCellStyle(alDerc);
    		
        	celda = fila.createCell(1);
    		celda.setCellStyle(style);
    		celda.setCellValue(registro.getInicio());
    		
        	celda = fila.createCell(2);
    		celda.setCellStyle(style);
    		celda.setCellValue(registro.getFin());
    		
        	celda = fila.createCell(3);
    		celda.setCellStyle(style);
    		celda.setCellValue(registro.getHorasTotales());
    		
        	celda = fila.createCell(4);
    		celda.setCellStyle(style);
    		celda.setCellValue(registro.getHorasViaje());
    		
        	celda = fila.createCell(5);
    		celda.setCellStyle(style);
    		celda.setCellValue("");
        	
        	cont++;
		}
        
        sheet.setColumnWidth(0, 256 * 10);
        sheet.setColumnWidth(1, 256 * 20);
        sheet.setColumnWidth(2, 256 * 20);
        sheet.setColumnWidth(3, 256 * 10);
        sheet.setColumnWidth(4, 256 * 10);
        sheet.setColumnWidth(5, 256 * 20);

        for(int i=0;i<cont;i++) {
        	if(i==4) {
            	sheet.getRow(i).setHeight((short)800);
        	}else {
            	sheet.getRow(i).setHeight((short)400);
        	}
        }
        
        CellStyle cs = workbook.createCellStyle();
        cs.setWrapText(true);   

        
        fila = sheet.createRow(cont);
        celda = fila.createCell(0);
        celda.setCellValue("NOTA 1: En las casillas que se indica \"referencia estandar\", con la firma del trabajador se entiende que éste ha empleado su "
        		+ "horario de referencia de acuerdo al sistema de autoorganización de la jornada laboral mediante flexibilidad horaria que se dispone en "
        		+ "la empresa para permitir la conciliación de vida privada con vida laboral");
        celda.setCellStyle(cs);
        sheet.getRow(cont).setHeight((short)1600);
        sheet.addMergedRegion(new CellRangeAddress(cont,cont,0,5));
        cont++;
        
        fila = sheet.createRow(cont);
        celda = fila.createCell(0);
        celda.setCellValue("NOTA 2: Horario de referencia del empleado: 09:00 a 14:30 - 15:30 a 18:00");
        celda.setCellStyle(cs);
        sheet.getRow(cont).setHeight((short)400);
        sheet.addMergedRegion(new CellRangeAddress(cont,cont,0,5));
        cont++;
        
        fila = sheet.createRow(cont);
        celda = fila.createCell(0);
        celda.setCellValue("NOTA 3: Este registro se dispone con la única finalidad de dar cumplimiento al Art. 31.7 del Estatuto de los Trabajadores, y al citado RDL 08/2019.");
        celda.setCellStyle(cs);
        sheet.getRow(cont).setHeight((short)800);
        sheet.addMergedRegion(new CellRangeAddress(cont,cont,0,5));
        cont++;
        
        sheet.createRow(cont);//Fila en blanco
        cont++;
        
        fila = sheet.createRow(cont);
        celda = fila.createCell(0);
        celda.setCellValue("Firma de la empresa");
        sheet.getRow(cont).setHeight((short)400);
        sheet.addMergedRegion(new CellRangeAddress(cont,cont,0,1));
        
        try {
        	System.out.println(ruta+"//WEB-INF//descargas//Horas...");
			FileOutputStream salida = new FileOutputStream(ruta+"//WEB-INF//descargas//Horas_"+mes+"-"+ano+".xls");
			res = ruta+"//WEB-INF//descargas//Horas_"+mes+"-"+ano+".xls";
			
			workbook.write(salida);
			workbook.close();
			salida.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return res;
	}
}
