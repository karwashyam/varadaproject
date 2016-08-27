<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Employee</title>
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
                    <h2>Add Employee</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form:form class="form-horizontal form-label-left" onsubmit="return validateForm()" method="POST" action="${pageContext.request.contextPath}/employee/add" commandName="employeeModel">

                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Full Name *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="fullName" class="form-control" placeholder="Enter Full Name" />
                          	<form:errors path="fullName" style="color: #ff0000;" />
                          	<p id="fullNameError" style="color:#ff0000;"></p>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Password *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:password path="password" class="form-control" placeholder="Enter Password" />
                          	<form:errors path="password" style="color: #ff0000;" />
                          	<p id="passwordError" style="color:#ff0000;"></p>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Confirm Password *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:password path="confirmPassword" class="form-control" placeholder="Enter Confirm Password" />
                          	<form:errors path="confirmPassword" style="color: #ff0000;" />
                          	<p id="confirmPasswordError" style="color:#ff0000;"></p>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Email *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="email" class="form-control" placeholder="Enter Email" />
                          	<form:errors path="email" style="color: #ff0000;" />
                          	<p id="emailError" style="color:#ff0000;"></p>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Date of Birth</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="birthDateForModel" class="form-control" id="birthDate" placeholder="dd/mm/yyyy" />
                          	<form:errors path="birthDateForModel" style="color: #ff0000;" />
                          	<p id="birthDateError" style="color:#ff0000;"></p>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Address</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:textarea path="address" class="form-control" placeholder="Enter Address" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Phone *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="phone" class="form-control" placeholder="91XXXXXXXX" />
                          	<form:errors path="phone" style="color: #ff0000;" />
                          	<p id="phoneError" style="color:#ff0000;"></p>
                        </div>
                      </div>

                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                        <a href="${pageContext.request.contextPath}/employee" class="btn btn-primary" id="btnCancel" name= "btnCancel" >Cancel</a>
                          <button type="submit" class="btn btn-success">Save</button>
                        </div>
                      </div>

                    </form:form>
                  </div>
                </div>
              </div>
           </div>
        </div>
<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
<style>
.ui-datepicker {
   background: ghostwhite;
   border: 1px solid #555;
   width: 17em;
 }
 .ui-datepicker-calendar{
 	width: 100%;
 }
</style>
<script type="text/javascript">
$( function() {
    $( "#birthDate" ).datepicker({
			changeMonth: true,
			changeYear: true,
			dateFormat : 'dd/mm/yy',
			maxDate: 0,
			yearRange: "-100:+0"
    });
  } );
</script>
</html>