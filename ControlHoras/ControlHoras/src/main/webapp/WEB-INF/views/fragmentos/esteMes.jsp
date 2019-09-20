<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="col-8" align="center" style="margin-top: 2px; margin-right: 10%">
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
			
	<form:form method="post" action="generarDocumento">
		<input type="hidden" name="usuario" id="usuario" value="${usu}">
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
