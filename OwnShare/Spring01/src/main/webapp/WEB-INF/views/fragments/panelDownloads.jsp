<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<table class="table table-hover">
  <thead>
    <tr align="center">
      <th scope="col">#</th>
      <th scope="col">File name</th>
      <th scope="col">Owner</th> 
      <th scope="col">Download</th>
      <th scope="col">Delete</th>
      <th scope="col">Alert</th>
      <th scope="col">Status</th>
    </tr>
  </thead>
  <tbody>
  
    <c:set value="${requestScope.descargas}" var="descargas" />
	<c:set value="${requestScope.user}" var="user" />
  	<% int cont = 1; %>
  	
  	<c:forEach items="${descargas}" var="item">
  		<tr align="center">
      		<th scope="row"><%=cont %></th>
      		<td>${item.name}</td>
      		<td>${item.owner}</td>
      		<td>			      	
	      		<form:form action="download" method="post" modelAttribute="download">
		      		<input type="hidden" name="id_user" value="${user.id}">
		      		<input type="hidden" name="id_down" value="${item.id}">
	      			<button class="btn btn-sm btn-success btn-block" type="submit">Download</button>
	      		</form:form>
	      	</td>
      		<td>
				<c:if test="${user.id==item.id_user}">
					<form:form action="delete" method="post" modelAttribute="delete">
			      		<input type="hidden" name="id_user" value="${user.id}">
			      		<input type="hidden" name="id_down" value="${item.id}">
			      		
			      		<div class="col-md-12 col-sm-12 text-section">
			      			<button type="button" class="btn btn-sm btn-danger btn-block" data-toggle="modal" data-target="#exampleModalButton">Delete</button>
				  		
				  		<div class="modal fade" id="exampleModalButton" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true" style="display: none;">
				          <div class="modal-dialog" role="document">
				            <div class="modal-content">
				              <div class="modal-header">
				                <h5 class="modal-title">Delete</h5>
				                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				                  <span aria-hidden="false">×</span>
				                </button>
				              </div>
				              <div class="modal-body">
				                <p>Your are going to delete the download link <strong>Are you sure?</strong></p><p>
				              </p></div>
				              <div class="modal-footer">
				                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
				                <button class="btn btn-danger" type="submit">Continue</button>
				              </div>
				            </div>
				          </div>
				        </div>
				    </div>
				  		
				  		<!-- <button class="btn btn-sm btn-danger btn-block" type="submit">Delete</button> 	-->
				  	</form:form>
				</c:if>
      		</td>
      		<td>
				<c:if test="${user.id!=item.id_user}">
					<form:form action="alert" method="post" modelAttribute="alert">
			      		<input type="hidden" name="id_user" value="${user.id}">
			      		<input type="hidden" name="id_down" value="${item.id}">
			      		
			      		<div class="col-md-12 col-sm-12 text-section">
			      			<button type="button" class="btn btn-sm btn-warning btn-block" data-toggle="modal" data-target="#exampleModalButton3">Alert</button>
				  		
				  		<div class="modal fade" id="exampleModalButton3" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true" style="display: none;">
				          <div class="modal-dialog" role="document">
				            <div class="modal-content">
				              <div class="modal-header">
				                <h5 class="modal-title">New Alert</h5>
				                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				                  <span aria-hidden="false">×</span>
				                </button>
				              </div>
				              <div class="modal-body">
				                <p>Your are going to create an alert for this download <strong>Are you sure?</strong></p>
				               </div>
				               <div class="form-group">
								  <label for="comment">Comment:</label>
								  <textarea class="form-control" rows="5" id="comment" name="comment"></textarea>
								</div>
				              <div class="modal-footer">
				                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
				                <button class="btn btn-danger" type="submit">Continue</button>
				              </div>
				            </div>
				          </div>
				        </div>
				    </div>
			      		
				  		<!--  <button class="btn btn-sm btn-warning btn-block" type="submit">Alert</button> -->
				  	</form:form>
				</c:if>
      		</td>
      		<td>
	      		<c:choose>
				<c:when test="${item.alert==true}">
			  		<img class="mb-4" src="resources/alert.png" alt="" width="36" height="36">
				</c:when> 
				<c:otherwise>
					<img class="mb-4" src="resources/right.png" alt="" width="36" height="36">
				</c:otherwise>
				</c:choose>
      		</td>
	    </tr>
	   <% cont++; %>
	</c:forEach>
  </tbody>
</table>
					
<form:form action="new" method="post" modelAttribute="new" enctype="multipart/form-data">
  <input type="hidden" name="id_user" value="${user.id}"> 
  <div class="custom-file">
	  <input type="file" class="custom-file-input" name="file" id="file">
	  <label class="custom-file-label" for="customFileLang">Upload new file</label>
  </div>
  <button class="btn btn-sm btn-success btn-block" type="submit" style="margin-top: 1%;">Send</button> 
</form:form>

<script>
$('.custom-file-input').on('change', function() {
    let fileName = $(this).val().split('\\').pop();
    $(this).siblings('.custom-file-label').addClass('selected').html(fileName);
});
</script>
