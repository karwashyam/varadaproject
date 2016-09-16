<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Varada Home</title>
<script type="text/javascript">
	var basePath = '${pageContext.request.contextPath}';
</script> 

<%@ include file="/WEB-INF/views/includes/common-head.jsp"%>

</head>
<%@ include file="/WEB-INF/views/includes/content-header.jsp"%>

	<!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="clearfix"></div>
            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                	<div class="x_title">
                    <h2>Change Password</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form:form class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/change-password" commandName="changePasswordApiDto">
						 <form:hidden path="userId" class="form-control" />
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Old Password</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:password path="oldPassword" class="form-control" placeholder="Password" />
                          <form:errors path="oldPassword" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">New Password</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:password path="newPassword" class="form-control" placeholder="Password" />
                          <form:errors path="newPassword" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Confirm Password</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:password path="confirmNewPassword" class="form-control" placeholder="Password" />
                          <form:errors path="confirmNewPassword" class="errorMessage" />
                        </div>
                      </div>

                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                          <button type="submit" class="btn btn-success">Update Password</button>
                        </div>
                      </div>

                    </form:form>
                  </div>
                </div>
              </div>
           </div>
        </div>
	
	
	

<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</html>