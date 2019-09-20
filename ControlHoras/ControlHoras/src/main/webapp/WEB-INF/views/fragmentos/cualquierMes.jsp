<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="col-8" align="center" style="margin-top: 2px; margin-right: 10%">
	
	<form:form method="post" action="cambiarMes">
		<input type="hidden" name="usuario" id="usuario" value="${usu}">
		<div class="row">
			<div class="col-2">
				<select class="custom-select" size="1" id="mesC" name="mesC">
					<c:forEach var="i" begin="1" end="${numMes}" step="1">
					   	<option value="${i}" selected="selected">${i}</option>
					</c:forEach>  
				</select>
			</div>
			<div class="col-4">
				<button type="submit" name="boton" value="excel" class="btn btn-success">Cargar mes</button>
			</div>
		</div>
	</form:form>
	
	<br>
	<h3 align="center"> Registro horario RDL 08/2019</h3>
	<table class="table table-bordered">
		<tr>
	      <td colspan="2">${empl.nombre}</td>
	    </tr>
	    <tr>
	      <td>Mes</td>
	      <td>${fecha}</td>
	    </tr>
	</table>
	<br>
	
	<table class="table table-bordered">
		<thead>
			<tr>
		    	<th scope="col">Día</th>
		    	<th scope="col">Hora inicio</th>
		    	<th scope="col">Hora fin</th>
		    	<th scope="col">Horas totales</th>
		    	<th scope="col">Horas viaje</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${mes}" var="item">
				<tr>
			    	<th scope="col">${item.dia}</th>
			    	<th scope="col">${item.inicio}</th>
			    	<th scope="col">${item.fin}</th>
			    	<th scope="col">${item.horasTotales}</th>
			    	<th scope="col">${item.horasViaje}</th>
				</tr>
			</c:forEach>
				<tr>
			    	<th scope="col" colspan="3"><strong>Total de horas mensuales:</strong></th>
			    	<th scope="col" colspan="2">${total}</th>
				</tr>
		</tbody>
	</table>
			
	<form:form method="post" action="generarDocumento2">
		<input type="hidden" name="usuario" id="usuario" value="${usu}">
		<input type="hidden" name="mesC2" id="mesC2" value="${mesC}">
		<table>
			<tr>
				<td>
					<button type="submit" name="boton" value="excel" class="btn btn-success">Generar Excel</button>
				</td>
				<td>
					<button type="submit" name="boton" value="pdf" class="btn btn-danger">Generar PDF</button>
				</td>
			</tr>
		</table>
	</form:form>
</div>
