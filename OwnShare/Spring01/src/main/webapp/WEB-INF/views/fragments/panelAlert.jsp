<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<table class="table table-hover">
<thead>
    <tr align="center">
      <th scope="col">#</th>
      <th scope="col">File name</th>
      <th scope="col">Issue</th> 
      <th scope="col">Sender</th>
      <th scope="col">Agree</th>
      <th scope="col">Disagree</th>
    </tr>
  </thead>
  <tbody>
    <c:set value="${requestScope.alertas}" var="alertas" />
	<c:set value="${requestScope.user}" var="user" />
  	<% int alm = 1; %>
  	  <c:forEach items="${alertas}" var="item">
	  	<tr align="center">
      		<th scope="row"><%=alm %></th>
      		<td>${item.download}</td>
      		<td>${item.text}</td>
      		<td>${item.user}</td>
      		<td>			      	
	      		<form:form action="agree" method="post" modelAttribute="agree">
		      		<input type="hidden" name="id_user" value="${user.id}">
		      		<input type="hidden" name="id_down" value="${item.id_download}">
		      		
		      		<div class="col-md-12 col-sm-12 text-section">
			      			<button type="button" class="btn btn-sm btn-success btn-block" data-toggle="modal" data-target="#exampleModalButton">Agree</button>
				  		
				  		<div class="modal fade" id="exampleModalButton" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true" style="display: none;">
				          <div class="modal-dialog" role="document">
				            <div class="modal-content">
				              <div class="modal-header">
				                <h5 class="modal-title">Agree</h5>
				                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				                  <span aria-hidden="false">×</span>
				                </button>
				              </div>
				              <div class="modal-body">
				                <p>You agree with the alert info and your download will be delete.<strong> Are you sure?</strong></p><p>
				              </p></div>
				              <div class="modal-footer">
				                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
				                <button class="btn btn-success" type="submit" name ="btn" value="${item.id}">Continue</button>
				              </div>
				            </div>
				          </div>
				        </div>
				    </div>

	      			<!-- <button class="btn btn-sm btn-success btn-block" type="submit">Agree</button> -->
	      		</form:form>
      		</td>
      		<td>			      	
	      		<form:form action="disagree" method="post" modelAttribute="disagree">
		      		<input type="hidden" name="id_user" value="${user.id}">
		      		<input type="hidden" name="id_alr" value="${item.id}">
	      			
	      				<div class="col-md-12 col-sm-12 text-section">
			      			<button type="button" class="btn btn-sm btn-danger btn-block" data-toggle="modal" data-target="#exampleModalButton2">Disagree</button>
				  		
				  		<div class="modal fade" id="exampleModalButton2" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true" style="display: none;">
				          <div class="modal-dialog" role="document">
				            <div class="modal-content">
				              <div class="modal-header">
				                <h5 class="modal-title">Disagree</h5>
				                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				                  <span aria-hidden="false">×</span>
				                </button>
				              </div>
				              <div class="modal-body">
				                <p>You disagree with the alert info and you are going to discard it.<strong> Are you sure?</strong></p><p>
				              </p></div>
				              <div class="modal-footer">
				                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
				                <button class="btn btn-success" type="submit" name ="btn" value="${item.id}">Continue</button>
				              </div>
				            </div>
				          </div>
				        </div>
				    </div>
	      			
	      			<!--  <button class="btn btn-sm btn-danger btn-block" type="submit">Disagree</button>-->
	      		</form:form>
      		</td>
      	</tr>
     	 	<% alm++; %>
		</c:forEach>
	  </tbody>
</table>

<script>
$('.custom-file-input').on('change', function() {
    let fileName = $(this).val().split('\\').pop();
    $(this).siblings('.custom-file-label').addClass('selected').html(fileName);
});

</script>