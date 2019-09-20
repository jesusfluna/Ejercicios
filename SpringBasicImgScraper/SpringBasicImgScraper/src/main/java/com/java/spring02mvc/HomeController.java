package com.java.spring02mvc;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	
	@RequestMapping(value = "/getUrl", method = RequestMethod.POST)
	public String getUrl(Locale locale, Model model,@RequestParam("url") String url,@RequestParam("radio") String radio) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		if(!utilidades.esValidaUrl(url)) {
			model.addAttribute("error", 1 );
			model.addAttribute("message", "La url introducida no es valida." );
		}else {
			
			if(radio.equals("option1")) {
				model.addAttribute("listaImg",crawlerGeneral(url));
			}else if(radio.equals("option2")) {
				model.addAttribute("listaImg",crawlerDevianart(url));
			}else{
				model.addAttribute("listaImg",crawlerExample(url));
			}

		}
		return "home";
	}
	
	private LinkedList<String> crawlerGeneral(String url) {
		LinkedList<String> lista = null;
		try {
			Document doc = Jsoup.connect(url).get();
			lista = new LinkedList<String>();
			Elements links = doc.select("img[src]");
			
			for (Element element : links) {
				lista.add(element.attr("abs:src"));
			}
		} catch (IOException e) {
			System.out.println("Error: "+e.getMessage());
		}
		
		return lista;
	}
	
	private LinkedList<String> crawlerDevianart(String url) {
		LinkedList<String> lista = null;
		try {
			Document doc = Jsoup.connect(url).get();
			lista = new LinkedList<String>();
			Elements links = doc.select("span[data-super-img]");
			
			for (Element element : links) {
				lista.add(element.attr("abs:data-super-img"));
			}	
		} catch (IOException e) {
			System.out.println("Error: "+e.getMessage());
		}
		
		return lista;
	}
	
	private LinkedList<String> crawlerExample(String url) {
		LinkedList<String> lista = null;
		
		lista = new LinkedList<String>();
		lista.add("https://cdn.icon-icons.com/icons2/836/PNG/512/Github_icon-icons.com_66788.png");
		lista.add("https://cdn.icon-icons.com/icons2/836/PNG/512/Android_icon-icons.com_66772.png");
		lista.add("https://cdn.icon-icons.com/icons2/836/PNG/512/Snapchat_icon-icons.com_66800.png");
		lista.add("https://cdn.icon-icons.com/icons2/836/PNG/512/Amazon_icon-icons.com_66787.png");
		lista.add("https://cdn.icon-icons.com/icons2/836/PNG/512/Mozilla_Firefox_icon-icons.com_66770.png");
		lista.add("https://cdn.icon-icons.com/icons2/836/PNG/512/Youtube_icon-icons.com_66802.png");
		lista.add("https://cdn.icon-icons.com/icons2/836/PNG/512/WhatsApp_icon-icons.com_66798.png");
		lista.add("https://cdn.icon-icons.com/icons2/836/PNG/512/Skype_icon-icons.com_66795.png");
		lista.add("https://cdn.icon-icons.com/icons2/836/PNG/512/Google_Chrome_icon-icons.com_66794.png");
		lista.add("https://cdn.icon-icons.com/icons2/836/PNG/512/Facebook_icon-icons.com_66805.png");
		
		return lista;
	}
}
