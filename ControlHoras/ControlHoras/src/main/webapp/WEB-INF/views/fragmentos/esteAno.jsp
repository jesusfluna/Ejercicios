<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="col-8" align="center" style="margin-top: 2px; margin-right: 10%">
	<h3 align="center"> Registro horario general de este año</h3>

	<table class="table table-bordered">
		<thead>
			<tr>
		    	<th scope="col">Fecha</th>
		    	<th scope="col">Horas totales</th>
		    	<th scope="col">Horas extra</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ano}" var="item">
				<c:choose>
					<c:when test="${item.horasTotal > 8}">
						<tr class="table-danger">
							<th scope="col">${item.dia}/${item.mes}</th>
					    	<th scope="col">${item.horasTotal}</th>
					    	
					    	<c:choose>
								<c:when test="${item.horasTotal == 0}">
									<th scope="col">${item.horasTotal}</th>
								</c:when>
								<c:otherwise>
									<th scope="col">${item.horasTotal - 8}</th>
								</c:otherwise>
							</c:choose>
					    	
						</tr>
					</c:when>
					<c:when test="${item.horasTotal == 0}">
						<tr>
							<th scope="col">${item.dia}/${item.mes}</th>
					    	<th scope="col">${item.horasTotal}</th>
							<th scope="col">${item.horasTotal}</th>
						</tr>
					</c:when>
					<c:otherwise>
						<tr class="table-success">
							<th scope="col">${item.dia}/${item.mes}</th>
					    	<th scope="col">${item.horasTotal}</th>
					    	
					    	<c:choose>
								<c:when test="${item.horasTotal == 0}">
									<th scope="col">${item.horasTotal}</th>
								</c:when>
								<c:otherwise>
									<th scope="col">${item.horasTotal - 8}</th>
								</c:otherwise>
							</c:choose>
							
						</tr>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tbody>
	</table>
</div>
