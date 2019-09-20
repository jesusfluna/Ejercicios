<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<title>Home</title>
</head>
<body>
	<!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	
	<script type="text/javascript">
		function checkAll() {
		    var aa= document.getElementsByName("imagenes");
		    for (var i =0; i < aa.length; i++){
		    	aa[i].checked = true;
		    }
		}
	</script>
	
	<!-- Visualización de los mensajes de error -->
	<c:if test="${error==1}">
		<div class="alert alert-danger alert-dismissible fade show" role="alert"><strong style="margin-left: 40%; margin-right: 40%;">${message}</strong>
			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		</div>
	</c:if> 
	
	<!-- Visualización de los mensajes de exito -->
	<c:if test="${error==0}">
		<div class="alert alert-success alert-dismissible fade show" role="alert"><strong style="margin-left: 40%; margin-right: 40%;">${message}</strong>
			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		</div>
	</c:if> 
	
	<!-- Formulario principal de la web -->
	<div class="container" style="margin-left: 25%; margin-right: 25%; margin-top: 5%; padding: 0%;">
		<div class="row">
			<div class="col">
				<img src="resources/lupa.png" alt="icono.png" width="225;" height="225;" style="margin-left: 20%; margin-right: 20%;">
			</div>
		</div>
		<div class="row">
			<div class="col">
				<form method="post" action="getUrl">
					<input type="text" name="url" placeholder="www.exampleurl.com" style="width: 50%; margin-bottom: 0%; margin-top: 0%;">
				    <button type="submit" class="btn btn-dark" style="width: 15%; margin-bottom: 0%; margin-top: 0%;">Submit</button>
				    <br>
				    <div class="form-check form-check-inline">
					  <input class="form-check-input" type="radio" name="radio" id="radio1" value="option1" checked="checked">
					  <label class="form-check-label" for="inlineRadio1">General</label>
					</div>
					<div class="form-check form-check-inline">
					  <input class="form-check-input" type="radio" name="radio" id="radio2" value="option2">
					  <label class="form-check-label" for="inlineRadio2">Devianart</label>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- Contenedor de imagenes para la descarga -->
	<form method="post" action="descargarTodo">
		<div class="Galeria" align="center" style="height: auto; display: flex; flex-wrap: wrap; ">
			<% int cont = 1; %>
			<c:forEach var="entry" items="${listaImg}">
				<div class = "imagen" style="flex: 1 0 25%;">
						<img src="${entry}" alt="img" class="img-thumbnail" style="width: 225px; height: 175px;"/><br>
						<input class="form-check-input" name="imagenes" type="checkbox" value="${entry}" id="cb<%=cont %>">
						<label class="form-check-label">Descargar</label>
				</div>
			<% cont++; %>
			</c:forEach>
		</div>
		
		<c:if test="${listaImg[0]!=null}">
			<br>
			<div class="btGaleria" style="margin-left: 40%; margin-right: 35%;">
				<button type="button" class="btn btn-dark" style="width: 35%; margin-bottom: 0%; margin-top: 0%;" onclick="checkAll()">Todos</button>
				<button type="submit" class="btn btn-dark" style="width: 35%; margin-bottom: 0%; margin-top: 0%;">Descargar</button>
			</div>
		</c:if>
	</form>
	
	<!-- ./Footer -->
	<section id="footer" style="margin-bottom: 0%; margin-top:5%; height: 25%;">
		<div class="container">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-12 mt-2 mt-sm-2 text-center text-white">
					<P align="center"> The time on the server is ${serverTime}.</p>
					<p class="h6">&copy All right Reversed.<a class="text-green ml-2" href="https://github.com/jesusfluna" target="_blank">ChesC88</a></p>
				</div>
				<hr>
			</div>
		</div>
		
	</section>
	<style>
	
	@import url('https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css');
		section {
		    padding: 60px 0;
		}
		
		section .section-title {
		    text-align: center;
		    color: #007b5e;
		    margin-bottom: 50px;
		    text-transform: uppercase;
		}
		#footer {
		    background: #007b5e !important;
		}
		#footer h5{
			padding-left: 10px;
		    border-left: 3px solid #eeeeee;
		    padding-bottom: 6px;
		    margin-bottom: 20px;
		    color:#ffffff;
		}
		#footer a {
		    color: #ffffff;
		    text-decoration: none !important;
		    background-color: transparent;
		    -webkit-text-decoration-skip: objects;
		}
	}
	</style>
	<!-- ./Footer -->
</body>
</html>
