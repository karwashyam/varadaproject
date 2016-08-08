<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
	var basePath = '${pageContext.request.contextPath}';
</script> 

<%@ include file="/WEB-INF/views/includes/common-head.jsp"%>

</head>
<body>

	<%@ include file="/WEB-INF/views/includes/content-header.jsp"%>
	<!-- Login Container -->
	${jsFile}
	<script>
$(document).ready(function(){
	$("#master1").hide();
	$("#company1").hide();
    $("#project1").hide();
    $("#franchise1").hide();
    $("#booking1").hide();
    $("#member1").hide();
    $("#booking_login1").hide();
    $("#payment1").hide();
    
	$("#other1").hide();	
	$("#expenses1").hide();
	$("#commission1").hide();
	$("#upload_commission1").hide();
	$("#pay_commision1").hide();
	$("#ecs_remider1").hide();
	
    $("#reports1").hide();
    $("#inquiry1").hide();
	
  $("#master").hover(function(){
	$("#master1").show();
	$("#company1").hide();
    $("#project1").hide();
    $("#franchise1").hide();
    $("#booking1").hide();
    $("#member1").hide();
    $("#booking_login1").hide();
    $("#payment1").hide();
    $("#other1").hide();
    $("#reports1").hide();
    $("#inquiry1").hide();
  });  
    
  $("#company").hover(function(){
	$("#master1").hide();
	$("#company1").show();
    $("#project1").hide();
    $("#franchise1").hide();
    $("#booking1").hide();
    $("#member1").hide();
    $("#booking_login1").hide();
    $("#payment1").hide();
    $("#other1").hide();
    $("#reports1").hide();
    $("#inquiry1").hide();
  });  
  
  $("#project").hover(function(){
	$("#master1").hide();
	$("#company1").hide();
    $("#project1").show();
    $("#franchise1").hide();
    $("#booking1").hide();
    $("#member1").hide();
    $("#booking_login1").hide();
    $("#payment1").hide();
    $("#other1").hide();
    $("#reports1").hide();
    $("#inquiry1").hide();
  });  
  
  
  $("#franchise").hover(function(){
	$("#master1").hide();
	$("#company1").hide();
    $("#project1").hide();
    $("#franchise1").show();
    $("#booking1").hide();
    $("#member1").hide();
    $("#booking_login1").hide();
    $("#payment1").hide();
    $("#other1").hide();
    $("#reports1").hide();
    $("#inquiry1").hide();
  });  
  
  
  $("#booking1").hover(function(){
	$("#master1").hide();
	$("#company1").hide();
    $("#project1").hide();
    $("#franchise1").hide();
    $("#booking1").show();
    $("#member1").hide();
    $("#booking_login1").hide();
    $("#payment1").hide();
    $("#other1").hide();
    $("#reports1").hide();
    $("#inquiry1").hide();
  });  
  
  
  $("#member").hover(function(){
	$("#master1").hide();
	$("#company1").hide();
    $("#project1").hide();
    $("#franchise1").hide();
    $("#booking1").hide();
    $("#member1").show();
    $("#booking_login1").hide();
    $("#payment1").hide();
    $("#other1").hide();
    $("#reports1").hide();
    $("#inquiry1").hide();
  });  
  
  
  $("#booking_login").hover(function(){
	$("#master1").hide();
	$("#company1").hide();
    $("#project1").hide();
    $("#franchise1").hide();
    $("#booking1").hide();
    $("#member1").hide();
    $("#booking_login1").show();
    $("#payment1").hide();
    $("#other1").hide();
    $("#reports1").hide();
    $("#inquiry1").hide();
  });  
  
  $("#payment").hover(function(){
	$("#master1").hide();
	$("#company1").hide();
    $("#project1").hide();
    $("#franchise1").hide();
    $("#booking1").hide();
    $("#member1").hide();
    $("#booking_login1").hide();
    $("#payment1").show();
    $("#other1").hide();
    $("#reports1").hide();
    $("#inquiry1").hide();
  });  
<!-- Other Tab Start-->  
  $("#other").hover(function(){
	$("#master1").hide();
	$("#company1").hide();
    $("#project1").hide();
    $("#franchise1").hide();
    $("#booking1").hide();
    $("#member1").hide();
    $("#booking_login1").hide();
    $("#payment1").hide();
    $("#other1").show();
    $("#reports1").hide();
    $("#inquiry1").hide();
  });  
  
  <!-- Other Sub Tab Start-->
  $("#expenses").hover(function(){
    $("#expenses1").show(); 
	$("#commission1").hide();
	$("#upload_commission1").hide();
	$("#pay_commision1").hide();
	$("#ecs_remider1").hide();    
  }); 
  
  $("#commission").hover(function(){
    $("#expenses1").hide(); 
	$("#commission1").show();
	$("#upload_commission1").hide();
	$("#pay_commision1").hide();
	$("#ecs_remider1").hide();    
  }); 
  
  $("#upload_commission").hover(function(){
    $("#expenses1").hide(); 
	$("#commission1").hide();
	$("#upload_commission1").show();
	$("#pay_commision1").hide();
	$("#ecs_remider1").hide();    
  }); 
  
  $("#pay_commision").hover(function(){
    $("#expenses1").hide(); 
	$("#commission1").hide();
	$("#upload_commission1").hide();
	$("#pay_commision1").show();
	$("#ecs_remider1").hide();    
  }); 
  
  $("#ecs_remider").hover(function(){
    $("#expenses1").hide(); 
	$("#commission1").hide();
	$("#upload_commission1").hide();
	$("#pay_commision1").hide();
	$("#ecs_remider1").show();    
  });  
  
  $("#reports").hover(function(){
	$("#master1").hide();
	$("#company1").hide();
    $("#project1").hide();
    $("#franchise1").hide();
    $("#booking1").hide();
    $("#member1").hide();
    $("#booking_login1").hide();
    $("#payment1").hide();
    $("#other1").hide();
    $("#reports1").show();
    $("#inquiry1").hide();
  }); 
	<!-- Other Sub Tab End--> 	
<!-- Other Tab End--> 
 
  $("#inquiry").hover(function(){
	$("#master1").hide();
	$("#company1").hide();
    $("#project1").hide();
    $("#franchise1").hide();
    $("#booking1").hide();
    $("#member1").hide();
    $("#booking_login1").hide();
    $("#payment1").hide();
    $("#other1").hide();
    $("#reports1").hide();
    $("#inquiry1").show();
  });  
  
});

</script>
	
		<!-- Page content -->


		<!-- START Content -->
		<div style="border: 1px solid; height: 100%;" class="block" id="page-content">
		
		<input type="hidden" name="isAddAccess" id="isAddAccess" value="${isAddAccess}" />
		<input type="hidden" name="isEditAccess" id="isEditAccess" value="${isEditAccess}" />
		<input type="hidden" name="isDeleteAccess" id="isDeleteAccess" value="${isDeleteAccess}" />
		
		
			            	<div style="float:left; width: 100%;">
			            	<%-- <form method="POST" name="addlessfrm" id="addlessfrm" action="${pageContext.request.contextPath}/secure/lesson.do"> --%>
			            	<div style="float:left;  padding-bottom: 5px; padding-top: 15px;  width: 100%;">
			            	<div style="float:right;">
			            		<button type="button" id="addState" class="btn btn-lg btn-primary border-radius">Add State</button>
<%-- 			            		<a href="${pageContext.request.contextPath}/secure/add-lesson.do"  class="btn btn-primary border-radius"  >Add Lesson</a> --%>
			            	</div>
			            	<div style="float:right; margin-right: 5%;">
			            	
			            <!-- 	<select id="instrutype" name="instrutype" class="form-control"  onchange="loadTable();" style="width: 125px;" >
									<option value="2">Guitar</option>
									<option value="1">Piano</option>
						    </select> 
			            	 -->
			            	</div>
			            	
			            	</div>
			            	<!-- </form> -->
			            	</div>
		
		
		
<%-- <div style="float:right; margin-bottom: 10px; margin-right: 60px;"><a href="${pageContext.request.contextPath}/secure/add-lesson.do" class="btn btn-primary" >Add Lesson</a></div> --%>
						<div style="float:left; width: 100%;" class="table-responsive">
								<table id="stateManageTable" class="table table-bordered table-hover"  style="width: 100%;" >
									<thead>
										<tr class="manage-users-list-header" style="width: 1058px;" >
											
											<th class="text-center">Sr. No</th>
											<th class="text-center">State Name</th>
											<th class="text-center">Country</th>
											<th class="text-center">Action</th>
										
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
							</div>
</div>


		<!-- END Content -->

		<!-- END Page Content -->

</body>
</html>