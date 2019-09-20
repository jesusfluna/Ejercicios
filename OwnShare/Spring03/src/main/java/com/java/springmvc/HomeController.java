package com.java.springmvc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

import beans.Alerts;
import beans.Downloads;
import beans.User;
import clases.DButilities;
//http://localhost:8080/springmvc/?lang=es

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	/**BBDD*/
	private String urlbd = "jdbc:postgresql://localhost:5432/XXXXX";
	private String usuario = "postgres";
	private String contrasena = "XXXXXX";
	/**Inerco Owncloud webdav*/
	private String webdav = "https://cloud.XXXXXX.com/remote.php/webdav/";
	private String owncloudu = "XXXXXX";
	private String owncloudp = "XXXXXX";
	
	/** Internacionalizacion*/
    @Autowired
    private MessageSource messageSource;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws SQLException 
	 * @throws NumberFormatException */
	@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
	public String home(Locale locale, Model model,HttpServletRequest request) throws NumberFormatException, SQLException {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		for (Cookie cookie : request.getCookies()) {
			if(cookie.getName().equals("MyCookie")) {
				User usuario = DButilities.getUserById(Integer.parseInt(cookie.getValue().split(":")[1]), this.urlbd, this.usuario, this.contrasena);
				List<Downloads> descargas = DButilities.getDownloadsByUserID(usuario.getId(),this.urlbd, this.usuario, this.contrasena);
				agregarAlModel(model,descargas,"0",null,locale,usuario, DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
			    return "login";
			}
		}
		return "home";
	}
	
	@RequestMapping(value = "/login" , method = RequestMethod.POST)
	public String test(Locale locale,Model model,@RequestParam("user") String user,@RequestParam("pass") String pass,@RequestParam("remember2") String remember,HttpServletResponse response) throws SQLException {
		Connection con = DButilities.conectarDB(this.urlbd, this.usuario, this.contrasena);
		
		if(con!=null) {
			
			ResultSet ress = DButilities.ejecutarConsulta(con, "SELECT * from users WHERE username LIKE '"+user+"' and password LIKE '"+pass+"';");
			if(ress!=null) {
				User usuario = null;
				while(ress.next()) {
					usuario = new User(Integer.parseInt(ress.getString(1)), ress.getString(2), ress.getString(3), ress.getString(4));
				}
				
				if(usuario!=null) {
					ress = DButilities.ejecutarConsulta(con, "SELECT * from downloads WHERE id_user="+usuario.getId()+";");
					List<Downloads> descargas = DButilities.getDownloadsByUserID(usuario.getId(),this.urlbd, this.usuario, this.contrasena);
					
					if(Integer.parseInt(remember)==1) {
						Cookie cookie = new Cookie("MyCookie", "u:"+usuario.getId());
						cookie.setMaxAge(60 * 60 * 24);
						response.addCookie(cookie);
					}
					agregarAlModel(model,descargas,"0",null,locale,usuario, DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
				}else {
					model.addAttribute("error", "1");
					messageSource.getMessage("message.error1",null, locale);
				    DButilities.desConectarDB(con);
					return "home";
				}
			}else {
				model.addAttribute("error", "1");
				messageSource.getMessage("message.error2",null, locale);
			    DButilities.desConectarDB(con);
				return "home";
			}
		}else {
			model.addAttribute("error", "1");
			messageSource.getMessage("message.error3",null, locale);
		    DButilities.desConectarDB(con);
			return "home";
		}
	    DButilities.desConectarDB(con);
	    return "login";
	}
	
	@RequestMapping(value = "/download" , method = RequestMethod.POST)
	public String download(Locale locale,Model model,@RequestParam("id_user") String id_user,@RequestParam("id_down") String id_down) throws NumberFormatException, SQLException{
		User usuario = DButilities.getUserById(Integer.parseInt(id_user), this.urlbd, this.usuario, this.contrasena);
		Downloads down = DButilities.getDownloadById(Integer.parseInt(id_down), this.urlbd, this.usuario, this.contrasena);
	
		Connection con = DButilities.conectarDB(this.urlbd, this.usuario, this.contrasena);
		List<Downloads> descargas = DButilities.getDownloadsByUserID(usuario.getId(),this.urlbd, this.usuario, this.contrasena);
		
		try {
			Sardine sardine = SardineFactory.begin();
			sardine.setCredentials(owncloudu, owncloudp);
			saveToDisk(System.getProperty("user.home") + "/Desktop"+"/"+down.getName(), sardine.get(down.getUrl()));
			
		} catch (Exception e) {
			agregarAlModel(model,descargas,"1" ,"message.error4",locale,usuario, DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
		    DButilities.desConectarDB(con);
			return "login";
		}
		agregarAlModel(model,descargas,"2" ,"message.success1",locale,usuario, DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
	    DButilities.desConectarDB(con);
		return "login";
	}
	
	@RequestMapping(value = "/delete" , method = RequestMethod.POST)
	public String delete(Locale locale,Model model,@RequestParam("id_user") String id_user,@RequestParam("id_down") String id_down) throws NumberFormatException, SQLException{
		User usuario = DButilities.getUserById(Integer.parseInt(id_user), this.urlbd, this.usuario, this.contrasena);
		Downloads down = DButilities.getDownloadById(Integer.parseInt(id_down), this.urlbd, this.usuario, this.contrasena);
		List<Downloads> descargas = DButilities.getDownloadsByUserID(usuario.getId(),this.urlbd, this.usuario, this.contrasena);
		
		try {
			Sardine sardine = SardineFactory.begin();
			sardine.setCredentials(owncloudu, owncloudp);
			sardine.delete(down.getUrl());
			DButilities.deleteDownloadById(down.getId(),this.urlbd, this.usuario, this.contrasena);
			
			descargas = DButilities.getDownloadsByUserID(usuario.getId(),this.urlbd, this.usuario, this.contrasena);
			
		} catch (Exception e) {
			agregarAlModel(model,descargas,"1" ,"message.error5",locale,usuario, DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
			return "login";
		}
		agregarAlModel(model,descargas,"2" ,"message.success2",locale,usuario, DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
		return "login";
	}
	
	@RequestMapping(value = "/new" , method = RequestMethod.POST)
	public String newFile(Locale locale,Model model, @RequestParam("file") MultipartFile file, @RequestParam("id_user") String id_user) throws NumberFormatException, SQLException{
		User usuario = DButilities.getUserById(Integer.parseInt(id_user), this.urlbd, this.usuario, this.contrasena);
		List<Downloads> descargas = DButilities.getDownloadsByUserID(usuario.getId(),this.urlbd, this.usuario, this.contrasena);
		
		try {
			Sardine sardine = SardineFactory.begin();
			sardine.setCredentials(owncloudu, owncloudp);

			Downloads down = new Downloads();
			down.setName(file.getOriginalFilename());
			down.setUrl(webdav+down.getName());
			down.setId_user(usuario.getId());
				
			sardine.put(down.getUrl(), file.getBytes());
			DButilities.saveNewDownload(down,  this.urlbd, this.usuario, this.contrasena);
			
			descargas = DButilities.getDownloadsByUserID(usuario.getId(),this.urlbd, this.usuario, this.contrasena);
			
		} catch (Exception e) {
			agregarAlModel(model,descargas,"1" ,"message.error6",locale,usuario, DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
			return "login";
		}
		agregarAlModel(model,null,"2" ,"message.succesf",locale,usuario, DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
		return "login";
	}
	
	@RequestMapping(value = "/changeView" , method = RequestMethod.POST)
	public String changeView(Model model, @RequestParam("btn") int opc, @RequestParam("id_user") String id_user) throws NumberFormatException, SQLException{
		User usuario = DButilities.getUserById(Integer.parseInt(id_user), this.urlbd, this.usuario, this.contrasena);
		Connection con = DButilities.conectarDB(this.urlbd, this.usuario, this.contrasena);
		
		ResultSet ress;
		
		if(opc==3) {
			ress = DButilities.ejecutarConsulta(con, "select * from alerts where id_descarga in (Select id from downloads where id_user ="+usuario.getId()+");");
			
			ArrayList<Alerts> alertas = new ArrayList<Alerts>();
			while(ress.next()) {
				alertas.add(new Alerts(Integer.parseInt(ress.getString(1)),ress.getString(2),Integer.parseInt(ress.getString(3)),Integer.parseInt(ress.getString(4)),
						DButilities.getUserById(Integer.parseInt(ress.getString(3)), this.urlbd, this.usuario, this.contrasena).getUserName(),
						DButilities.getDownloadById(Integer.parseInt(ress.getString(4)), this.urlbd, this.usuario, this.contrasena).getName(),Integer.parseInt(ress.getString(5))));
			}
			model.addAttribute("alertas", alertas);
			
		}else {
			if(opc==2) {
				ress = DButilities.ejecutarConsulta(con, "SELECT * from downloads;");
			}else {
				ress = DButilities.ejecutarConsulta(con, "SELECT * from downloads WHERE id_user="+usuario.getId()+";");
			}
			ArrayList<Downloads> descargas = new ArrayList<Downloads>();
			while(ress.next()) {
				descargas.add(new Downloads(Integer.parseInt(ress.getString(1)), ress.getString(2), DButilities.getUserById(Integer.parseInt(ress.getString(4)), this.urlbd, this.usuario, this.contrasena).getUserName(),
						Integer.parseInt(ress.getString(4)),ress.getString(3),DButilities.hasAlertByDownloadId(Integer.parseInt(ress.getString(1)),this.urlbd, this.usuario, this.contrasena)));
			}
			model.addAttribute("descargas", descargas);
		}
		
		model.addAttribute("user", usuario);
		model.addAttribute("numAlr", DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
	    DButilities.desConectarDB(con);
		return "login";
	}
	
	@RequestMapping(value = "/agree" , method = RequestMethod.POST)
	public String agree(Locale locale,Model model, @RequestParam("id_user") String id_user, @RequestParam("id_down") String id_down){
		User usuario = new User();
		try {
			usuario = DButilities.getUserById(Integer.parseInt(id_user), this.urlbd, this.usuario, this.contrasena);
			Downloads down = DButilities.getDownloadById(Integer.parseInt(id_down), this.urlbd, this.usuario, this.contrasena);
			Sardine sardine = SardineFactory.begin();
			sardine.setCredentials(owncloudu, owncloudp);
			sardine.delete(down.getUrl());
			
			Connection con = DButilities.conectarDB(this.urlbd, this.usuario, this.contrasena);
			DButilities.ejecutarConsulta(con, "DELETE from alerts WHERE id_descarga="+id_down+";");
			DButilities.deleteDownloadById(Integer.parseInt(id_down),this.urlbd, this.usuario, this.contrasena);
			
			ResultSet ress = DButilities.ejecutarConsulta(con, "select * from alerts where id_descarga in (Select id from downloads where id_user ="+usuario.getId()+");");
			
			ArrayList<Alerts> alertas = new ArrayList<Alerts>();
			while(ress.next()) {
				alertas.add(new Alerts(Integer.parseInt(ress.getString(1)),ress.getString(2),Integer.parseInt(ress.getString(3)),Integer.parseInt(ress.getString(4)),
						DButilities.getUserById(Integer.parseInt(ress.getString(3)), this.urlbd, this.usuario, this.contrasena).getUserName(),
						DButilities.getDownloadById(Integer.parseInt(ress.getString(4)), this.urlbd, this.usuario, this.contrasena).getName(),Integer.parseInt(ress.getString(5))));
			}
			
			model.addAttribute("alertas", alertas);
		} catch (Exception e) {
			model.addAttribute("error", "1");
			messageSource.getMessage("message.error7",null, locale);
		}
		agregarAlModel(model,null,"2" ,"message.success3",locale,usuario, DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
		return "login";
	}
	
	
	@RequestMapping(value = "/disagree" , method = RequestMethod.POST)
	public String disagree(Locale locale,Model model, @RequestParam("id_alr") String id_alr, @RequestParam("id_user") String id_user) {
		User usuario = new User();
		try {
			usuario = DButilities.getUserById(Integer.parseInt(id_user), this.urlbd, this.usuario, this.contrasena);
			Connection con = DButilities.conectarDB(this.urlbd, this.usuario, this.contrasena);
			DButilities.ejecutarConsulta(con, "DELETE from alerts WHERE id="+id_alr+";");
			
			ResultSet ress = DButilities.ejecutarConsulta(con, "select * from alerts where id_descarga in (Select id from downloads where id_user ="+usuario.getId()+");");
			
			ArrayList<Alerts> alertas = new ArrayList<Alerts>();
			while(ress.next()) {
				alertas.add(new Alerts(Integer.parseInt(ress.getString(1)),ress.getString(2),Integer.parseInt(ress.getString(3)),Integer.parseInt(ress.getString(4)),
						DButilities.getUserById(Integer.parseInt(ress.getString(3)), this.urlbd, this.usuario, this.contrasena).getUserName(),
						DButilities.getDownloadById(Integer.parseInt(ress.getString(4)), this.urlbd, this.usuario, this.contrasena).getName(),Integer.parseInt(ress.getString(5))));
			}
			
			model.addAttribute("alertas", alertas);
		} catch (Exception e) {
			agregarAlModel(model,null,"1" ,"message.error8",locale,usuario, DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
			return "login";
		}
		agregarAlModel(model,null,"2" ,"message.success4",locale,usuario, DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
		return "login";
	}
	
	@RequestMapping(value = "/alert" , method = RequestMethod.POST)
	public String newAlert(Locale locale,Model model, @RequestParam("id_down") String id_down, @RequestParam("id_user") String id_user, @RequestParam("comment") String comment) throws NumberFormatException, SQLException {
		User usuario = new User();
		List<Downloads> descargas = DButilities.getDownloads(this.urlbd, this.usuario, this.contrasena);
		
		try {
			usuario = DButilities.getUserById(Integer.parseInt(id_user), this.urlbd, this.usuario, this.contrasena);
			
			DButilities.saveNewAlert(Integer.parseInt(id_down), usuario.getId(), comment, this.urlbd, this.usuario, this.contrasena);
			descargas = DButilities.getDownloads(this.urlbd, this.usuario, this.contrasena);
			
		} catch (Exception e) {
			agregarAlModel(model,descargas,"1" ,"message.error9",locale,usuario, DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
			return "login";
		}

		agregarAlModel(model,descargas,"2" ,"message.success5",locale,usuario, DButilities.getNumAlertsNotRead(usuario.getId(),this.urlbd, this.usuario, this.contrasena));
		return "login";
	}
	
	private void saveToDisk(String ruta,InputStream file) {
		
		try {
			File targetFile = new File(ruta);
			FileUtils.copyInputStreamToFile(file, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/logout" , method = RequestMethod.POST)
	public String logout(Model model,HttpServletRequest request){
		for (Cookie cookie : request.getCookies()) {
			if(cookie.getName().equals("MyCookie")) {
				cookie.setMaxAge(0);
			    return "home";
			}
		}
		return "home";
	}	
	
	private Model agregarAlModel(Model m, List<Downloads> descargas,String error,String mensaje,Locale locale, User usuario, String nal) {
		if(descargas!=null) {
			m.addAttribute("descargas", descargas);
		}
		m.addAttribute("error", error);
		
		if(mensaje != null) {
			messageSource.getMessage(mensaje,null, locale);
		}

		m.addAttribute("user", usuario);
		m.addAttribute("numAlr",nal);
		
		return m;
	}
}
