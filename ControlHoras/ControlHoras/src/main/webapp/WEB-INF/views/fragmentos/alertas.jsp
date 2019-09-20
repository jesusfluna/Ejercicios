<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${error==1}">
	<div style="margin-top: 7%">
		<div class="alert alert-danger alert-dismissible fade show" role="alert" align="center">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
	    		<span aria-hidden="true">&times;</span>
	 	 	</button>
	 	 	<strong>ERROR: </strong>${message}
		</div>
	</div>
</c:if>
	
<c:if test="${error==0}">
	<div style="margin-top: 7%">
		<div class="alert alert-success alert-dismissible fade show" role="alert" align="center">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
	    		<span aria-hidden="true">&times;</span>
	 	 	</button>
	 	 	${message}
		</div>
	</div>
</c:if>