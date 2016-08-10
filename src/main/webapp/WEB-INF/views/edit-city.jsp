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
                    <h2>Add City</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form:form class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/city/edit-city" commandName="cityModel">

                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Select State</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select name="stateId" class="form-control">
                            <c:forEach items="${stateModel}" var="state" >
                            <c:choose>
							  <c:when test="${state['stateId'] == stateId}">
							  <option value="${state['stateId']}" selected ="selected">${state['stateName']}</option>
							  </c:when>
							  <c:otherwise>
							  <option value="${state['stateId']}">${state['stateName']}</option>
							  </c:otherwise>
							</c:choose>
							</c:forEach>
                          </select>
                          <form:errors path="stateId" style="color: #ff0000;" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">City Name</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="cityName" class="form-control" placeholder="Enter City" />
                          <form:errors path="cityName" style="color: #ff0000;" />
                          <form:errors path="validCity" style="color: #ff0000;" />
                        </div>
                      </div>
						<form:hidden path="cityId"  />
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                        <a href="${pageContext.request.contextPath}/city" class="btn btn-primary"  id="btnCancel" name= "btnCancel" >Cancel</a>
                          <button type="submit" class="btn btn-success">Update</button>
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