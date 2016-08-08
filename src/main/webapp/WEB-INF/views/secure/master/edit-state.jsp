<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
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
	
	
			<!-- START Content -->
		<div class="block" style="height: 585px;">
		
		<form:form name="editstateFrm" modelAttribute="state" commandName="editstateFrm" id="editstateFrm" class="form-horizontal" action="${pageContext.request.contextPath}/secure/edit-state.do"  method="POST"  >
		
		<div class="form-group"  >
				<span class="col-sm-3 " style="padding-right: 30px;font-size: 18px" ><span class="error">*</span>Country </span>
				<div class="col-sm-4">
				
				
           		<form:select path="countryId"  id="countryId" name="countryId" class="form-control border-radius">
           		<form:option value="">Select Country</form:option>
		          <form:option value="2">USA</form:option>
		          <form:option value="1">India</form:option>
		          
		        </form:select> 
		        <form:errors path="countryId" cssStyle="color: #ff0000;"/>
            	 </div>
           	</div>
		
			<div class="form-group"  >
            	<span class="col-sm-3 "   style="padding-right: 30px;font-size: 18px" ><span class="error">*</span>State Name</span>
            	 <div class="col-sm-4">
            		<form:input path="stateName"  class="form-control border-radius" id="stateName" name="stateName"  style="font-size: 15px;"  maxlength="50"/>
            	 	<form:errors path="stateName" cssStyle="color: #ff0000;"/>
            	 </div>
           	</div>

		
           	<form:hidden path="stateId" id="stateId" />
           	 <div style=" width: 331px;float: left;height: 100px;padding-left: 45px; ">
                     <a href="${pageContext.request.contextPath}/secure/states.do" class="btn btn-lg btn-primary btn-block border-radius button-size" style="width: 90px;float: left;margin: 20px"  id="btnCancel" name= "btnCancel" >Cancel</a>
	              <input class="btn btn-lg btn-primary btn-block border-radius button-size" style="width: 90px;float: left; margin: 20px" type="button"  id="btnUpdate" name="btnUpdate"   value="Update" >
	              <img src="${pageContext.request.contextPath}/resources/assets/img/ajax-loader.gif" id="ajaxLoader" style="padding-top: 32px; display: none;" />
           	</div>
		
		</form:form>
		</div>
		<!-- END Content -->
	
</body>
</html>