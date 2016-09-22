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
<%@ include file="/WEB-INF/views/includes/content-header.jsp"%>
	<!-- Login Container -->
<div class="right_col" role="main">
<div class="clearfix"></div>
	 <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Edit State</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form:form name="editstate" modelAttribute="state" commandName="stateDto" id="editstateFrm" class="form-horizontal form-label-left" action="${pageContext.request.contextPath}/edit-state.do"  method="POST" >

                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">State Name <span class="required">*</span>
                        </label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                         <form:input path="stateName"  class="form-control border-radius" id="stateName" name="stateName"  style="font-size: 15px;"  maxlength="50"/>
                         <form:errors path="stateName" cssclass="errorMessage"/>
            	 		 <form:errors path="stateExists" class="errorMessage"  />
                        </div>
                      </div>
                    
           	<form:hidden path="stateId" id="stateId" />
                      <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
<!--                           <button type="submit" class="btn btn-primary">Cancel</button> -->
<!--                           <button type="submit" class="btn btn-success">Submit</button> -->
                           <a href="${pageContext.request.contextPath}/states.do" class="btn btn-primary" style="width: 90px;float: left;margin: 20px"  id="btnCancel" name= "btnCancel" >Cancel</a>
	              <input class="btn btn-success" style="width: 90px;float: left; margin: 20px" type="button"  id="btnUpdate" name="btnUpdate"   value="Update" >
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