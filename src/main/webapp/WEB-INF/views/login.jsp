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
	
	<!-- Login Container -->
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-md-4 col-md-offset-4">
				<h1 class="text-center login-title">Sign In</h1>
				<div class="account-wall">
					
					<form:form class="form-signin" method="POST"  action="${pageContext.request.contextPath}/login.do" commandName="login">
					
					<form:input path="userName" class="form-control" placeholder="username" />
					<form:errors path="userName" cssStyle="color: #ff0000;" />
							
					<form:password path="password" class="form-control" placeholder="Password"/>
					
					<form:errors path="password" cssStyle="color: #ff0000;"  />
					<form:errors path="validLogin" cssStyle="color: #ff0000;"  /> 			
					
					<input class="btn btn-lg btn-primary btn-block" type="submit" name="Sign in" value="Sign in">
						
					</form:form>
				</div>
				
			</div>
		</div>
	</div>
	
	${jsFile}
	
</body>
</html>