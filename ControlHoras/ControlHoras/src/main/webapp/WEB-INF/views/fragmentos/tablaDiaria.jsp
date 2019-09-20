<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<c:choose>
	<c:when test="${not empty dias}">
		<% int cont=1; %>
	
		<c:forEach items="${dias}" var="item">
		<% if(cont>1) {%>
			<div class="container row" style="margin: 7%; width: 90%; padding-top: 2%;">
			<div class="btn-group-vertical col-2 ml-auto" align="left" style="margin-top: 0%;"></div>
		<% } %>
		<div class="col-8" align="center" style="margin-top: 2px; margin-right: 10%">
		   	<div style="border-color: gray; border-radius: 5px; border-width: 2px; border-style: solid; padding: 2%;">
			   	<form:form method="post" action="modificarDia"> 
			   	<input type="hidden" name="usuario" id="usuario" value="${usu}">
			   	
			   		<div class="form-row" align="left">
						<div class="form-group col-md-6">
							<label for="entrada">Entrada</label>
			     			<input type="text" class="form-control" id="entrada" name="entrada" value="${item.entrada}">
						</div>
						<div class="form-group col-md-6">
							<label for="salida">Salida</label>
			     			<input type="text" class="form-control" id="salida" name="salida" value="${item.salida}">
						</div>
			   		</div>
			   		<div class="form-row" align="left">
						<div class="form-group col-md-4">
							<label for="hviaje">Horas viaje</label>
			     			<select class="custom-select" size="1" id="hviaje" name="hviaje">
							   <c:forEach var="i" begin="0" end="${item.totalHoras}" step="1">
							   		<c:if test="${item.horasViaje==i}">
							   			<option value="${i}" selected="selected">${i}</option>
							   		</c:if>
							   		<c:if test="${item.horasViaje!=i}">
							   			<option value="${i}">${i}</option>
							   		</c:if>
								</c:forEach>  
							</select>
						</div>
						<div class="form-group col-md-4">
							<label for="htotal">Horas total</label>
							<input type="text" class="form-control" id="htotal" name="htotal" value="${item.totalHoras}" disabled>
						</div>
						<div class="form-group col-md-4" align="center">
							<button type="submit" class="btn btn-primary" name="boton" value="botonD-${item.id}" style="width: 50%; margin-top: 13%;">Guardar</button>
						</div>
			   		</div>
			   	</form:form>
		   	</div>
		</div>
		<% 
		cont = cont+1;
		if(cont>1) { %>
			</div>
		<% } %>
	</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="col-8" align="center" style="margin-top: 2px; margin-right: 10%">
			<h3 align="center"> Sección temporalmente vacia</h3>
			<p> En estos momentos la sección a la que intenta acceder no tiene ningun contenido a mostrar, esto puede deberse a que primero 
			necesita registrar un movimiento en la aplicación. Si piensa que es un error del sistema, por favor pongase en contacto con el administrador
			de la aplicación informando de la incidendia para que sea solucionada al menor tiempo posible. <strong>Disculpe las molestias.</strong></p>
		</div>
	</c:otherwise>
</c:choose>
