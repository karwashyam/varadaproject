<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Varada Developers Login</title>
<script type="text/javascript">
	var basePath = '${pageContext.request.contextPath}';
</script> 

<%@ include file="/WEB-INF/views/includes/common-head.jsp"%>

</head>
	
	<!-- Login Container -->
	<%-- <div class="container">
		<div class="row">
			<div class="col-sm-6 col-md-4 col-md-offset-4">
				<h1 class="text-center login-title">Varda Developers Login</h1>
				<div class="account-wall">
					
					<form:form class="form-signin" method="POST"  action="${pageContext.request.contextPath}/login.do" commandName="login">
					
					<form:input path="userName" class="form-control" placeholder="username" />
					<form:errors path="userName" class="errorMessage" />
							
					<form:password path="password" class="form-control" placeholder="Password"/>
					
					<form:errors path="password" class="errorMessage"  />
					<form:errors path="validLogin" class="errorMessage"  /> 			
					
					<input class="btn btn-lg btn-primary btn-block" type="submit" name="Sign in" value="Sign in">
						
					</form:form>
				</div>
				
			</div>
		</div>
	</div> --%>
	
	<body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form:form  method="POST"  action="${pageContext.request.contextPath}/login.do" commandName="login">
              <h1>Varda Login</h1>
              <div>
              	<form:input path="userName" class="form-control" placeholder="username" />
					<form:errors path="userName" class="errorMessage" />
              </div>
              <div>
                <form:password path="password" class="form-control" placeholder="Password"/>
					
					<form:errors path="password" class="errorMessage"  />
					<form:errors path="validLogin" class="errorMessage"  />
              </div>
              <div style="text-align:center">
              	<input class="btn btn-default submit" style="float: initial;" type="submit" name="Sign in" value="Log in">
              </div>

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">Forgot Your Password?
                  <a href="#signup" class="to_register"> Click Here </a>
                </p>

                <div class="clearfix"></div>
                <br />

              </div>
            </form:form>
          </section>
        </div>

        <%-- <div id="register" class="animate form registration_form">
          <section class="login_content">
            <form>
              <h1>Forgot Password</h1>
              <!-- <div>
                <input type="text" class="form-control" placeholder="Username" required="" />
              </div>
              <div>
                <input type="email" class="form-control" placeholder="Email" required="" />
              </div>
              <div>
                <input type="password" class="form-control" placeholder="Password" required="" />
              </div>
              <div>
                <a class="btn btn-default submit" href="index.html">Submit</a>
              </div> -->

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">Already a member ?
                  <a href="#signin" class="to_register"> Log in </a>
                </p>

                <div class="clearfix"></div>
                <br />

              </div>
            </form>
          </section>
        </div> --%>
      </div>
    </div>
    ${jsFile}
  </body>
	
	
	
</html>