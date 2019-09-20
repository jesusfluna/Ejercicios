package com.java.spring02mvc;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.validator.routines.UrlValidator;

public class utilidades {

	/**Comprueba si es valida la url introducida*/
	public static boolean esValidaUrl(String url) {
		UrlValidator defaultValidator = new UrlValidator();
		return defaultValidator.isValid(url);
	}
	
	/**descarga una imagen*/
	public static boolean descargar(String ruta,String rutaLocal,String nombre) {
		boolean res = true;
		
		try {
			URL url = new URL(ruta);
			URLConnection urlCon = url.openConnection();
			InputStream is = urlCon.getInputStream();
			FileOutputStream fos = new FileOutputStream(rutaLocal+nombre+"."+getExtension(urlCon.getContentType()));
			
			byte[] array = new byte[1000];
			int leido = is.read(array);
			while (leido > 0) {
				fos.write(array, 0, leido);
				leido = is.read(array);
			}
			
			is.close();
			fos.close();
		} catch (Exception e) {
			res = false;
		}
		return res;
	}

	/**Devuelve una cadena con la extension del tipo de fichero*/
	private static String getExtension(String contentType) {
		String ext;
		
		if(contentType.equals("image/png")) {
			ext="png";
		}else if(contentType.equals("image/jpeg")) {
			ext="jpeg";
		}else if(contentType.equals("image/gif")) {
			ext="gif";
		}else {
			ext="unk";
		}
		
		return ext;
	}
	
	/**descarga una imagen*/
	public static boolean generarHtml(String rutaLocal) {
		boolean res = true;
		
		try {
			int cont = 0;
			File f = new File(rutaLocal);
			
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(rutaLocal+"index.html"));
			bw.write("<html>");
			bw.newLine();
			bw.write("	<head><title>Galeria</title></head>");
			bw.newLine();
			bw.write("	<body>");
			bw.newLine();
			bw.write("			<header><h1>Galeria de imagenes</h1></header><div class=\"row\"><div class=\"col-md-12\"><div id=\"mdb-lightbox-ui\"></div><div class=\"mdb-lightbox\">");
			bw.newLine();
			bw.write("		<table><tr>");
			bw.newLine();
			
			for (File img : f.listFiles()) {
				if(cont%5==0) {
					bw.write("		</tr><tr>");
					bw.newLine();
				}
				bw.write("		<td>");
				bw.newLine();
				bw.write("		<td><a href=\""+img.getName()+"\" data-size=\"1600x1067\">");
				bw.newLine();
				bw.write("		<img alt=\"picture\" src=\""+img.getName()+"\" class=\"img-fluid\" / style=\"width: 250px; height: 250px;\"></a></td>");
				bw.newLine();
				
				cont++;
			}
			
			bw.write("		</tr></table>");
			bw.newLine();
			bw.write("	<script>");
			bw.newLine();
			bw.write("		$(function () {");
			bw.newLine();
			bw.write("			$(\"#mdb-lightbox-ui\").load(\"mdb-addons/mdb-lightbox-ui.html\");");
			bw.newLine();
			bw.write("			});");
			bw.newLine();
			bw.write("	</script>");
			bw.newLine();
			bw.write("	</body>");
			bw.newLine();
			bw.write("</html>");
			
			bw.close();
		} catch (IOException e) {
			res = false;
			e.printStackTrace();
		}
		return res;
	}
	
	   /**Compresion de un fichero*/
	   public static void makeZip(String fileName){
		      File file = new File(fileName);
		      try {
		      ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file + ".zip"));
		      recurseFiles(file,zos);
		      zos.close();
		      }catch(Exception e) {
		    	  System.out.println("Error: "+e.getMessage());
		      }
		      
		  }
	
	   private static void recurseFiles(File file,ZipOutputStream zos)throws IOException, FileNotFoundException{
		      if (file.isDirectory()) {
		    	 String[] fileNames = file.list();
		         if (fileNames != null) {
		            for (int i=0; i<fileNames.length; i++){ 
		            	recurseFiles(new File(file, fileNames[i]),zos);
		            }
		         }
		      }
		      else {
		         byte[] buf = new byte[1024];
		         int len;
		         ZipEntry zipEntry = new ZipEntry(file.toString());
		         FileInputStream fin = new FileInputStream(file);
		         BufferedInputStream in = new BufferedInputStream(fin);
		         zos.putNextEntry(zipEntry);
				 while ((len = in.read(buf)) >= 0) {
					zos.write(buf, 0, len);
				 }
		         in.close();
		         zos.closeEntry();
		      }
		   }
	   
	   /**Borrar un directorio y su contenido*/
	    public static void deleteFolder(File fileDel) {
	        if(fileDel.isDirectory()){            
	            if(fileDel.list().length == 0)
	                fileDel.delete();
	            else{
	               for (String temp : fileDel.list()) {
	                   File fileDelete = new File(fileDel, temp);
	                   deleteFolder(fileDelete);
	               }
	               if(fileDel.list().length==0)
	                   fileDel.delete();
	            }

	        }else{
	            fileDel.delete();            
	        }
	    }
}
