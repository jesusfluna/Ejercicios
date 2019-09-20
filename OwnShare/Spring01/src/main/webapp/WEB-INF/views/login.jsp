<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<html>
<head>
	<!-- titulo -->
	<title>Panel de usuario</title>
	<!-- Include header -->
	<jsp:include page="fragments/header.jsp" />
</head>
<body>
	<!-- Include CSS and javascrip -->
	<jsp:include page="fragments/extras.jsp" />
	
	<div class="top-bar">
	        <div class="container">
	          <div class="row d-flex align-items-center">
	            <div class="col-md-6 d-md-block d-none">
	              <p> Our employee's platform for share files</p>
	            </div>
	            <div class="col-md-6">
	              <div class="d-flex justify-content-md-end justify-content-between">
	                <ul class="list-inline contact-info d-block d-md-none">
	                  <li class="list-inline-item"><a href="#"><i class="fa fa-phone"></i></a></li>
	                  <li class="list-inline-item"><a href="#"><i class="fa fa-envelope"></i></a></li>
	                </ul>
	                <ul class="social-custom list-inline">
	                  <li class="list-inline-item"><a href="#"><i class="fa fa-facebook"></i></a></li>
	                  <li class="list-inline-item"><a href="#"><i class="fa fa-google-plus"></i></a></li>
	                  <li class="list-inline-item"><a href="#"><i class="fa fa-twitter"></i></a></li>
	                  <li class="list-inline-item"><a href="#"><i class="fa fa-envelope"></i></a></li>
	                </ul>
	              </div>
	            </div>
	          </div>
	        </div>
	      </div>

	<c:choose>
		<c:when test="${error==2}">
			<div class="alert alert-success alert-dismissible fade show" role="alert" align="center">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    		<span aria-hidden="true">&times;</span>
		 	 	</button>
		 	 	<strong>Exito!!</strong> ${message}
			</div>
		</c:when>
		<c:when test="${error==1}">
			<div class="alert alert-danger alert-dismissible fade show" role="alert" align="center">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    		<span aria-hidden="true">&times;</span>
		 	 	</button>
		 	 	<strong>Error</strong> ${message}
			</div>
		</c:when>
	</c:choose>

	<div class="container-fluid" style="margin: 5%;" >
		<div class="row">
			<div class="col-md-auto">
	      		<div class="card" style="width: 18rem;">
				  <h5 class="card-title" align="center">${user.privileges}</h5>
				  	<c:choose>
						<c:when test="${user.privileges=='admin'}">
					  		<img src="resources/admin.png" class="card-img-top" alt="admin" width="100" height="300">
						</c:when> 
						<c:otherwise>
							<img src="resources/user.jpg" class="card-img-top" alt="user" width="100" height="300">
						</c:otherwise>
					</c:choose>
				  <div class="card-body">
				    <h5 class="card-title" align="center">${user.userName}</h5>
				    <form:form action="logout" method="post">
				    	<button type="submit" class="btn btn-danger btn-block" name="btn">Logout</button>
				    </form:form>
				  </div>
				</div>
				
				<br>
				<form:form action="changeView" method="post" modelAttribute="changeView">
					<input type="hidden" name="id_user" value="${user.id}"> 
					<div class="btn-group-vertical" style="width: 18rem;">
					  <button type="submit" class="btn btn-outline-success btn-block" name="btn" value="1">My files</button>
					  <button type="submit" class="btn btn-outline-success btn-block" name="btn" value="2">All Files</button>
					  <button type="submit" class="btn btn-outline-success btn-block" name="btn" value="3">Alerts</button>
					</div>
				</form:form>
	    	</div>
	    	
	    	<div class="col col-8">
	    	
	    	<c:set var="user" value="${user}" scope="request"/>	<!-- Envio del objeto por scope para los jsp importados -->
	    	<c:choose>
				<c:when test="${alertas != null}">
					
						<!-- Import alert panel -->
						<c:set var="alertas" value="${alertas}" scope="request"/>
						<c:import url="fragments/panelAlert.jsp"/>
												
				</c:when> 
				<c:otherwise>
				
						<!-- Import downloads panel -->
						<c:set var="descargas" value="${descargas}" scope="request"/>
						<c:import url="fragments/panelDownloads.jsp"/>
					
				</c:otherwise>
			</c:choose>
	    	</div>
  		</div>
	</div>	
</body>

<!-- Include footer -->
<jsp:include page="fragments/footer.jsp" />

</html>