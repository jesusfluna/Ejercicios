<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="col-8" align="center" style="margin-top: 2px; margin-right: 10%">
	<c:choose>
	    <c:when test="${tarjeta==1}">
	    	<div class="card" style="width: 65%; padding-top: 2%;" align="center">
			  <img class="card-img-top" src="resources/salir.jpeg" alt="imagen entrada" style="width: 10rem; height: 10rem;">
			  <div class="card-body">
			    <h5 class="card-title">Hora de salida</h5>
			    <p class="card-text" align="center">Registre su hora de salida de su puesto laboral. Hora actual del servidor: <br><strong> ${serverTime} </strong></p>
			   	<form method="post" action="registrarSalida">
			    	<input type="hidden" name="usuario" id="usuario" value="${usu}">
			    	<button type="submit" class="btn btn-primary" name="entrada">Registrar salida</button>
			    </form>
			  </div>
			</div>
	    </c:when>    
	    <c:when test="${tarjeta==0}">
			<div class="card" style="width: 45%; padding-top: 2%;" align="center">
			  <img class="card-img-top" src="resources/entrar.jpeg" alt="imagen entrada" style="width: 8rem; height: 8rem;">
			  <div class="card-body">
			    <h5 class="card-title">Hora de entrada</h5>
			    <p class="card-text" align="center">Registre su hora de entrada a su puesto laboral.<br><strong> ${serverTime} </strong></p>
			   	<form method="post" action="registrarEntrada">
			    	<input type="hidden" name="usuario" id="usuario" value="${usu}">
			    	<button type="submit" class="btn btn-primary" name="entrada">Registrar entrada</button>
			    </form>
			  </div>
			</div>
	    </c:when>
	</c:choose>
</div>
