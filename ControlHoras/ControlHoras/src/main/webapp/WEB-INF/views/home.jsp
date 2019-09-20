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
	<c:if test="${error==1}">
		<div class="alert alert-danger alert-dismissible fade show" role="alert" align="center">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
	    		<span aria-hidden="true">&times;</span>
	 	 	</button>
	 	 	<strong>ERROR: </strong>${message}
		</div>
	</c:if>
	
	<c:if test="${error==0}">
		<div class="alert alert-success alert-dismissible fade show" role="alert" align="center">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
	    		<span aria-hidden="true">&times;</span>
	 	 	</button>
	 	 	${message}
		</div>
	</c:if>
	
		<div style="margin-top: 10%; margin-left: 40%; margin-right: 40%;">
			<form:form class="form-signin" action="login" method="post" modelAttribute="login">
			  <img class="mb-4" src="resources/inerco.jpeg" alt="" width="72" height="72">
			  <h1 class="h3 mb-3 font-weight-normal">Identificación de usuario</h1>
			  
			  <label for="inputText" class="sr-only">Email</label>
			  <input type="text" name="usuario" id="inputText"  class="form-control" placeholder="Nombre de usuario" required autofocus>
			  <label for="inputPassword" class="sr-only">Contraseña</label>
			  <input type="password" name="contrasena" id="inputPassword" class="form-control" placeholder="Contraseña" required>
			  <!-- <div class="checkbox mb-3">
			    <label>
			    	<input type="hidden" id="remember2" name="remember2" value="0">
			      	<input type="checkbox" name="remember" id="remember" class="my-checkbox"> Recordarme
			    </label>
			  </div> -->
			  <br>
			  <button class="btn btn-lg btn-success btn-block" type="submit">Entrar</button>
			  <p class="mt-5 mb-3 text-muted">${serverTime} &copy;Inerco-2019</p>
			</form:form>
		</div>
	</body>
	
<!-- <script type="text/javascript">
$(".my-checkbox").change(function() {
	if($("#remember2").val()==0){
		$("#remember2").val(1);
	}else{
		$("#remember2").val(0);
	}
  });
</script> -->
</html> 