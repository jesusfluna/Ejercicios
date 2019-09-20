<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html lang="es">
	<head id="header">
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
		<!-- Header from https://bootsnipp.com/snippets/aMG53 -->
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	</head>
	<body class="text-center">
	
	<!-- Header -->
	<div id="header">
		<div class="container">	
			<table>
				<tr>
					<td>
						<img src="resources/inerco.jpeg" alt="logo" title="InercoLogo" width="45px"height="45px"/>
					</td>
					<td>
						<div id="logo" class="pull-left">
							<h1><a href="https://www.inerco.com/" class="scrollto">INERCO</a></h1>
						</div>
					</td>
					<td style="width: 100%;">
						<div class="usuario" align="right" style="color: green;">
							Bienvenido <strong>${usu}</strong>
						</div>
					</td>
				</tr>
			</table>
			

		</div>
	</div>
	<!-- Header:FIN -->
	
		<!-- Include alertas -->
		<jsp:include page="fragmentos/alertas.jsp" />
				
	<!-- Panel principal -->
	<div class="container row" style="margin: 7%; width: 90%; padding-top: 10%;">
		<!-- Include botonera -->
		<jsp:include page="fragmentos/botonera.jsp" />
		
		<c:if test="${tarjeta==1 || tarjeta==0}">
			<!-- Include tarjetaES -->
			<jsp:include page="fragmentos/tarjetaES.jsp" />
		</c:if>

		<c:if test="${tablaDiaria==1}">
			<!-- Include tablaDiaria -->
			<jsp:include page="fragmentos/tablaDiaria.jsp" />
		</c:if>
		
		<c:if test="${esteMes==1}">
			<!-- Include esteMes -->
			<jsp:include page="fragmentos/esteMes.jsp" />
		</c:if>
		
		<c:if test="${cualquierMes==1}">
			<!-- Include esteMes -->
			<jsp:include page="fragmentos/cualquierMes.jsp" />
		</c:if>
		
		<c:if test="${esteAno==1}">
			<!-- Include esteAño -->
			<jsp:include page="fragmentos/esteAno.jsp" />
		</c:if>
		
	</div>
	<!-- Panel principal:END -->
	</body>
	
<!-- Styles -->
<style>
	#header.header-scrolled {
	    background: #fff;
	    padding: 20px 0;
	    height: 72px;
	    transition: all 0.5s;
	}
	#header {
	    padding: 30px 0;
	    height: 92px;
	    position: fixed;
	    left: 0;
	    top: 0;
	    right: 0;
	    transition: all 0.5s;
	    z-index: 997;
	    background-color: #fff;
	    box-shadow: 5px 0px 15px #c3c3c3;
	}
	#header #logo h1 {
	    font-size: 34px;
	    margin: 0;
	    padding: 0;
	    line-height: 1;
	    font-family: "Montserrat", sans-serif;
	    font-weight: 700;
	    letter-spacing: 3px;
	}
	#header #logo h1 a, #header #logo h1 a:hover {
	    color: #000;
	    padding-left: 10px;
	    border-left: 4px solid grey;
	}
</style>
<!-- Styles:END -->

</html> 