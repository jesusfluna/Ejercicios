<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html lang="es">
	<head>
	    <meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<!--<title>Portal web </title> -->
		<title>Inerco: registro de horas</title>
		<!-- jQuery -->
		    <script type = "text/javascript" src = "https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
		<!-- jQueryUI -->
		    <script type = "text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" charset="utf-8"></script>
		<!-- Bootstrap -->
		    <script type = "text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js" charset="utf-8"></script>
		<!-- Font Awesome -->
		    <script src="https://use.fontawesome.com/7dd8dcb030.js"></script>
		<!-- custom script -->
		    <script type = "text/javascript" src="script.js" charset="utf-8"></script>
			<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
			<link rel="shortcut icon" type="image/x-icon" href="resources/favicon.ico" />
	</head>
	<body class="text-center">
		<div class="container-fluid row" style="margin-top: 10%; margin-left: 15%; margin-right: 10%; width: 80%;" align="center">
			<div class="icono col-10" align="center">
				<img class="mb-4" src="resources/inerco.jpeg" alt="" width="72" height="72">
			</div>
			<div class="texto col-10">
				<h1><strong>ERROR:</strong> En esta pagina estan desactivadas las peticiones GET por motivos de seguridad. Disculpe las molestias.
				<br><p>Para volver a la pagina principal pulse sobre el siguiente enlace: <a href="<c:url value="/" />" >Inicio</a></p></h1>
			</div>
		</div>
	</body>
</html> 