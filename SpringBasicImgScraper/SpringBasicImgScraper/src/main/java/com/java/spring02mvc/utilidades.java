package com.java.spring02mvc;

import org.apache.commons.validator.routines.UrlValidator;

public class utilidades {

	/**Comprueba si es valida la url introducida*/
	public static boolean esValidaUrl(String url) {
		UrlValidator defaultValidator = new UrlValidator();
		return defaultValidator.isValid(url);
	}
}
