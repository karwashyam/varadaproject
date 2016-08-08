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
	
	
	
			<!-- START Content -->
		<div class="block" style="height: 585px;">
		
		<form:form name="stateFrm" modelAttribute="state" commandName="stateFrm" id="stateFrm" class="form-horizontal" action="${pageContext.request.contextPath}/secure/add-state.do"  method="POST"  >
				
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
	              <input class="btn btn-lg btn-primary btn-block border-radius button-size" style="width: 90px;float: left; margin: 20px" type="button"  id="btnSave" name="btnSave"   value="Save" >
	              <img src="${pageContext.request.contextPath}/resources/assets/img/ajax-loader.gif" id="ajaxLoader" style="padding-top: 32px; display: none;" />
           	</div>
		
		</form:form>
		</div>
		<!-- END Content -->
	
</body>
</html>