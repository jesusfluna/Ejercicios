package com.inerco.ControlHoras;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import beans.Dia;
import beans.Empleado;
import informes.InformeAnual;
import informes.RegistroInforme;
import informes.UExcel;
import informes.UPdf;

@Controller
public class HomeController {
	@Autowired
    ServletContext context;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	
	@RequestMapping(value = "/login" , method = RequestMethod.POST)
	public String login(Locale locale, Model model,@RequestParam("usuario") String user,@RequestParam("contrasena") String pass) {
		Empleado e = Empleado.buscarEmpleado(user);
		
		if(e!= null) {
			if(e.getContrasena().equals(pass) && e.getUsuario().equals(user)) {
				model.addAttribute("error", "0");
				model.addAttribute("message","Bienvenido "+user);
				
				model = accionesOpcion1(model,user);
				
				return "Principal";
			}else {
				model.addAttribute("error", "1");
				model.addAttribute("message","Usuario o contraseña incorrecta");
			}
		}else {
			model.addAttribute("error", "1");
			model.addAttribute("message","No se encontro el usuario solicitado");
		}
		return "home";
	}
	
	@RequestMapping(value = "/registrarEntrada" , method = RequestMethod.POST)
	public String registrarEntrada(Locale locale, Model model,@RequestParam("usuario") String user) {
		try {
			Dia.newDia(user);
			
			Date date = new Date();
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
			String formattedDate = dateFormat.format(date);
			
			model.addAttribute("tarjeta", "1");
			model.addAttribute("error", "0");
			model.addAttribute("message","Se ha registrado su entrada a las: "+ formattedDate);
		}catch(Exception e) {
			model.addAttribute("error", "1");
			model.addAttribute("message","Hemos tenido un error registrando su hora de entrada, por favor consulte con un tecnico o intentelo de nuevo mas tarde");
		}
		model.addAttribute("usu", user);
		return "Principal";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/registrarSalida" , method = RequestMethod.POST)
	public String registrarSalida(Locale locale, Model model,@RequestParam("usuario") String user) {
		try {
			Dia dia = Dia.ultimoDiaUsuario(user);
			dia.setSalida(new Timestamp(System.currentTimeMillis()));
			
			dia.setTotalHoras(dia.getSalida().getHours()-dia.getEntrada().getHours());
			if(!Dia.actualizarDia(dia)) {
				throw new Exception();
			}
			
			Date date = new Date();
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
			String formattedDate = dateFormat.format(date);
			
			model.addAttribute("tarjeta", "0");
			model.addAttribute("error", "0");
			model.addAttribute("message","Se ha registrado su salida a las: "+ formattedDate);
		}catch(Exception e) {
			model.addAttribute("tarjeta", "1");
			model.addAttribute("error", "1");
			model.addAttribute("message","Hemos tenido un error registrando su hora de salida, por favor consulte con un tecnico o intentelo de nuevo mas tarde");
		}
		
		return "Principal";
	}
	
	@RequestMapping(value = "/cambiarMes" , method = RequestMethod.POST)
	public String cambiarMes(Locale locale, Model model,@RequestParam("usuario") String user, @RequestParam("mesC") String mesC) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		List<RegistroInforme> ri = RegistroInforme.registroMes(user, Integer.parseInt(mesC));
		model.addAttribute("mes", ri);
		
		Empleado empl = Empleado.buscarEmpleado(user);
		model.addAttribute("empl",empl);
		
		float total = 0;
		for (RegistroInforme registroInforme : ri) {
			total+=registroInforme.getHorasTotales();
		}
		
		
		model.addAttribute("error", "0");
		model.addAttribute("message","Mes "+mesC+"/"+calendar.get(Calendar.YEAR)+" cargado con exito.");
		model.addAttribute("numMes", (calendar.get(Calendar.MONTH)+1));
		model.addAttribute("mesC",mesC);
		model.addAttribute("total",total);
		model.addAttribute("fecha",mesC+"/"+calendar.get(Calendar.YEAR));
		model.addAttribute("cualquierMes", 1);
		model.addAttribute("usu",user);
		
		
		return "Principal";
	}
	
	@RequestMapping(value = "/accionMenu" , method = RequestMethod.POST)
	public String accionMenu(Locale locale, Model model,@RequestParam("usuario") String user,@RequestParam("boton") String boton) {
		
		if(boton.equals("boton1")) {
			model = accionesOpcion1(model,user);
		}else if(boton.equals("boton2")) {
			model = accionesOpcion2(model,user);
		}else if(boton.equals("boton3")) {
			model = accionesOpcion3(model,user);
		}else if(boton.equals("boton4")) {
			model = accionesOpcion4(model,user);
		}else if(boton.equals("boton5")){
			model = accionesOpcion5(model,user);
		}else {
			model = accionesOpcion1(model,user);//TODO cambiar por una pagina de error
		}
		
		return "Principal";
	}

	private Model accionesOpcion1(Model model, String user) {
		model.addAttribute("usu",user);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		Dia dia = Dia.ultimoDiaUsuario(user);
		if(dia.getSalida()==null && dia.getEntrada()!=null) {
			model.addAttribute("tarjeta", "1");
		}else{
			model.addAttribute("tarjeta", "0");
		}
		
		return model;
	}

	private Model accionesOpcion2(Model model, String user) {
		model.addAttribute("usu",user);
		model.addAttribute("tablaDiaria",1);
		
		List<Dia> dias= Dia.diaActualUsuario(user);
		
		model.addAttribute("dias", dias);
		return model;
	}
	
	private Model accionesOpcion3(Model model, String user) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		List<RegistroInforme> ri = RegistroInforme.registroMes(user, calendar.get(Calendar.MONTH)+1);
		model.addAttribute("mes", ri);
		
		Empleado empl = Empleado.buscarEmpleado(user);
		model.addAttribute("empl",empl);
		
		float total = 0;
		for (RegistroInforme registroInforme : ri) {
			total+=registroInforme.getHorasTotales();
		}
		model.addAttribute("total",total);
		model.addAttribute("fecha",(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR));
		model.addAttribute("esteMes", 1);
		model.addAttribute("usu",user);
		
		return model;
	}
	
	private Model accionesOpcion4(Model model, String user) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		List<RegistroInforme> ri = RegistroInforme.registroMes(user, calendar.get(Calendar.MONTH)+1);
		model.addAttribute("mes", ri);
		
		Empleado empl = Empleado.buscarEmpleado(user);
		model.addAttribute("empl",empl);
		
		float total = 0;
		for (RegistroInforme registroInforme : ri) {
			total+=registroInforme.getHorasTotales();
		}
		model.addAttribute("total",total);
		model.addAttribute("fecha",(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR));
		model.addAttribute("cualquierMes", 1);
		model.addAttribute("numMes", calendar.get(Calendar.MONTH)+1);
		model.addAttribute("usu",user);
		
		return model;
	}
	
	private Model accionesOpcion4(Model model, String user,int mes) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		List<RegistroInforme> ri = RegistroInforme.registroMes(user, mes);
		model.addAttribute("mes", ri);
		
		Empleado empl = Empleado.buscarEmpleado(user);
		model.addAttribute("empl",empl);
		
		float total = 0;
		for (RegistroInforme registroInforme : ri) {
			total+=registroInforme.getHorasTotales();
		}
		model.addAttribute("total",total);
		model.addAttribute("fecha",mes+"/"+calendar.get(Calendar.YEAR));
		model.addAttribute("cualquierMes", 1);
		model.addAttribute("numMes", mes);
		model.addAttribute("usu",user);
		
		return model;
	}
	
	private Model accionesOpcion5(Model model, String user) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		model.addAttribute("usu",user);
		model.addAttribute("esteAno", 1);
		InformeAnual ano = new InformeAnual(calendar.get(Calendar.YEAR), user);
		model.addAttribute("ano", ano.getAño());
		
		return model;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/modificarDia" , method = RequestMethod.POST)
	public String modificarDia(Locale locale, Model model,@RequestParam("usuario") String user,@RequestParam("boton") String boton,
			@RequestParam("entrada") String entrada, @RequestParam("salida") String salida, @RequestParam("hviaje") String hviaje) {
		int id = Integer.parseInt(boton.split("-")[1]);
		
		try {
			Dia dia = Dia.getDiaId(id);
			dia.setEntrada(stringToTimestamp(entrada));
			dia.setSalida(stringToTimestamp(salida));
			dia.setHorasViaje(Integer.parseInt(hviaje));
			
			if(dia.getSalida()==null)
			{
				dia.setTotalHoras(0);
			}else {
				dia.setTotalHoras(dia.getSalida().getHours()-dia.getEntrada().getHours());
			}
			
			
			if(!Dia.actualizarDia(dia)) {
				throw new Exception();
			}
			
			model.addAttribute("error", "0");
			model.addAttribute("message","Se ha actualizado el registro horario con exito");
		}catch(Exception e){
			model.addAttribute("error", "1");
			model.addAttribute("message","ocurrio un error actualizando el registro horario, por favor intentelo de nuevo mas adelante"
					+ " o consulte con el servicio de soporte");
		}

		model = accionesOpcion2(model,user);
		return "Principal";
	}
	
	@RequestMapping(value = "/generarDocumento" , method = RequestMethod.POST)
	public String generarDocumento(HttpServletResponse response,Locale locale, Model model,@RequestParam("usuario") String user,@RequestParam("boton") String boton) {
		Empleado empl = Empleado.buscarEmpleado(user);
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String error = "0";
		String message = "El documento Excel con los datos del "+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)+" generado";
		
		
		List<RegistroInforme> ri = RegistroInforme.registroMes(user, calendar.get(Calendar.MONTH)+1);	
		
		String res = UExcel.generarExcel(empl.getNombre(),ri,calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.YEAR),getPath());
		
		if(res.equals("")) {
			error="1";
			message = "El documento Excel con los datos del "+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)+" no pudo ser generado";
		}else {
			try {
				if(boton.equals("excel")) {
					response.setContentType("application/xls");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + "Horas_"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR)+".xls"+ "\"");
				}else {
					if(UPdf.generarPDF(empl.getNombre(),ri,calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.YEAR),getPath())) {
						res = res.replaceAll("xls", "pdf");
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition", "attachment; filename=\"" + "Horas_"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR)+".pdf"+ "\"");
					}else {
						error="1";
						message = "El documento PDF con los datos del "+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)+" no pudo ser generado";
					}
				}
				
				if(!error.equals("1")) {
					InputStream is = new FileInputStream(res);
		            IOUtils.copy(is, response.getOutputStream());
		            response.getOutputStream().flush();//Opcion 1 para que no de el error en la descarga del fichero
		            response.getOutputStream().close();//Opcion 1 para que no de el error en la descarga del fichero
		            response.flushBuffer();
				}
			}
			catch(Exception e) {
				error="1";
				message = "El documento con los datos del "+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)+" no pudo ser generado";
				System.out.println("ERROR: "+e.getMessage());
			}
		}
		
		model = accionesOpcion3(model,user);
		model.addAttribute("mes", ri);
		model.addAttribute("error", error);
		model.addAttribute("message",message);

		return null;//Opcion 2 para que no de el error en la descarga del fichero /*return "Principal";*/
		//Vamos a dejar las dos opciones puestas por seguridad
	}
	
	@RequestMapping(value = "/generarDocumento2" , method = RequestMethod.POST)
	public String generarDocumento2(HttpServletResponse response,Locale locale, Model model,@RequestParam("usuario") String user,@RequestParam("boton") String boton,@RequestParam("mesC2") String mesC2) {
		
		Empleado empl = Empleado.buscarEmpleado(user);
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String error = "0";
		String message = "El documento Excel con los datos del "+mesC2+"/"+calendar.get(Calendar.YEAR)+" generado";
		
		
		List<RegistroInforme> ri = RegistroInforme.registroMes(user, Integer.parseInt(mesC2));	
		
		String res = UExcel.generarExcel(empl.getNombre(),ri,Integer.parseInt(mesC2),calendar.get(Calendar.YEAR),getPath());
		
		if(res.equals("")) {
			error="1";
			message = "El documento Excel con los datos del "+mesC2+"/"+calendar.get(Calendar.YEAR)+" no pudo ser generado";
		}else {
			try {
				if(boton.equals("excel")) {
					response.setContentType("application/xls");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + "Horas_"+mesC2+"-"+calendar.get(Calendar.YEAR)+".xls"+ "\"");
				}else {
					if(UPdf.generarPDF(empl.getNombre(),ri,Integer.parseInt(mesC2),calendar.get(Calendar.YEAR),getPath())) {
						res = res.replaceAll("xls", "pdf");
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition", "attachment; filename=\"" + "Horas_"+mesC2+"-"+calendar.get(Calendar.YEAR)+".pdf"+ "\"");
					}else {
						error="1";
						message = "El documento PDF con los datos del "+mesC2+"/"+calendar.get(Calendar.YEAR)+" no pudo ser generado";
					}
				}
				
				if(!error.equals("1")) {
					InputStream is = new FileInputStream(res);
		            IOUtils.copy(is, response.getOutputStream());
		            response.getOutputStream().flush();//Opcion 1 para que no de el error en la descarga del fichero
		            response.getOutputStream().close();//Opcion 1 para que no de el error en la descarga del fichero
		            response.flushBuffer();
				}
			}
			catch(Exception e) {
				error="1";
				message = "El documento con los datos del "+mesC2+"/"+calendar.get(Calendar.YEAR)+" no pudo ser generado";
				System.out.println("ERROR: "+e.getMessage());
			}
		}
		
		model = accionesOpcion4(model,user, Integer.parseInt(mesC2));
		model.addAttribute("mes", ri);
		model.addAttribute("error", error);
		model.addAttribute("message",message);

		return null;//Opcion 2 para que no de el error en la descarga del fichero /*return "Principal";*/
		//Vamos a dejar las dos opciones puestas por seguridad
	}
	
	private Timestamp stringToTimestamp(String fecha) {
		Timestamp timestamp = null;
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    Date parsedDate = dateFormat.parse(fecha);
		    timestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch(Exception e) {  System.out.println("ERROR: "+e.getMessage()); }
		return timestamp;
	}

	public String getPath(){
	    String path = this.getClass().getClassLoader().getResource("").getPath();
	    String fullPath = "pufff";
		try {
			fullPath = URLDecoder.decode(path, "UTF-8");
		    String pathArr[] = fullPath.split("/WEB-INF/classes/");
		    System.out.println(fullPath);
		    System.out.println(pathArr[0]);
		    fullPath = pathArr[0];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	    return fullPath;
	}
	
	/** Todas las peticiones GET dentro de la plataforma se redirigiran por defecto a la misma pagina de error*/
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {		
		return "error";
	}
	
	@RequestMapping(value = "/registrarEntrada", method = RequestMethod.GET)
	public String registrarEntrada() {		
		return "error";
	}
	
	@RequestMapping(value = "/registrarSalida", method = RequestMethod.GET)
	public String registrarSalida() {		
		return "error";
	}
	
	@RequestMapping(value = "/accionMenu", method = RequestMethod.GET)
	public String accionMenu() {		
		return "error";
	}
	
	@RequestMapping(value = "/modificarDia" , method = RequestMethod.GET)
	public String modificarDia() {
		return "error";
	}
	
	@RequestMapping(value = "/generarDocumento" , method = RequestMethod.GET)
	public String generarDocumento() {
		return "error";
	}
	
	@RequestMapping(value = "/generarDocumento2" , method = RequestMethod.GET)
	public String generarDocumento2() {
		return "error";
	}
	
	@RequestMapping(value = "/cambiarMes" , method = RequestMethod.GET)
	public String cambiarMes() {
		return "error";
	}
}
