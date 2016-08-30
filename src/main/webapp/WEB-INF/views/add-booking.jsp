<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Booking</title>
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
                    <h2>Add Booking</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form:form class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/booking/add" commandName="bookingModel">
					  
					  <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Member</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select id="memberId" name="memberId" class="form-control selectpicker" data-live-search="true" title="Select Member">
                            <c:forEach items="${memberModelList}" var="memberModel" >
							<option value="${memberModel['memberId']}_${memberModel['memberName']}_${memberModel['fatherName']}">${memberModel['memberName']}</option></c:forEach>
                          </select>
                          <form:errors path="memberId" style="color: #ff0000;" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Select Project</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select id="projectId" name="projectId" class="form-control selectpicker" data-live-search="true" title="Select Project">
                            <c:forEach items="${projectModel}" var="project" >
							<option value="${project['projectId']}">${project['title']}</option></c:forEach>
                          </select>
                          <form:errors path="projectId" style="color: #ff0000;" />
                        </div>	
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Select Payment Scheme</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select id="paymentSchemeId" name="paymentSchemeId" class="form-control" data-live-search="true" title="Select Payment Scheme">
                          </select>
                          <form:errors path="paymentSchemeId" style="color: #ff0000;" />
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Select Plot</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select id="plotId" name="plotId" class="form-control" data-live-search="true" title="Select Payment Scheme">
                          </select>
                          <form:errors path="plotId" style="color: #ff0000;" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Plot Size (Yards)</label>
                        <div id="plotSize" class="col-md-3 col-sm-3 col-xs-12 display-data">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Rate Per Yard* (INR)</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="ratePerYard" class="form-control" placeholder="" />
                          <form:errors path="ratePerYard" style="color: #ff0000;" />
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Months</label>
                        <div id="months" class="col-md-3 col-sm-3 col-xs-12 display-data">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Interest Rate</label>
                        <div id="interestRate" class="col-md-3 col-sm-3 col-xs-12 display-data">
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Down Payment* (INR)</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input id="downPayment" path="downPayment" class="form-control" placeholder="" />
                          	<form:errors path="downPayment" style="color: #ff0000;" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">EMI</label>
                        <div id="emi" class="col-md-3 col-sm-3 col-xs-12 display-data">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Total Amount</label>
                        <div id="totalAmount" class="col-md-3 col-sm-3 col-xs-12 display-data">
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Member Name</label>
                        <div id="memberName" class="col-md-3 col-sm-3 col-xs-12 display-data">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Father/Husband Name</label>
                        <div id="fatherName" class="col-md-3 col-sm-3 col-xs-12 display-data">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Down Payment* (INR)</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input id="downPayment" path="downPayment" class="form-control" placeholder="" />
                          	<form:errors path="downPayment" style="color: #ff0000;" />
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Nominee Name</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input id="nomineeName" path="nomineeName" class="form-control" placeholder="" />
                          	<form:errors path="nomineeName" style="color: #ff0000;" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Nominee Father/Husband Name</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input id="nomineeFather" path="nomineeFather" class="form-control" placeholder="" />
                          	<form:errors path="nomineeFather" style="color: #ff0000;" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Nominee Address</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:textarea id="nomineeAddress" path="nomineeAddress" class="form-control" placeholder="" />
                          	<form:errors path="nomineeAddress" style="color: #ff0000;" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Nominee Relation</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input id="nomineeRelation" path="nomineeRelation" class="form-control" placeholder="" />
                          	<form:errors path="nomineeRelation" style="color: #ff0000;" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Nominee DOB</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input id="birthDate1" path="nomineeDob" class="form-control" placeholder="dd/mm/yyyy" />
                          	<form:errors path="nomineeDob" style="color: #ff0000;" />
                        </div>
                      </div>
                      <%-- <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">City Name</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="paymentSchemeId" class="form-control" placeholder="Enter City" />
                          <form:errors path="cityName" style="color: #ff0000;" />
                        </div>
                      </div> --%>

                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                        <a href="${pageContext.request.contextPath}/city" class="btn btn-primary" id="btnCancel" name= "btnCancel" >Cancel</a>
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
<script type="text/javascript">
$( function() {
    $("#birthDate1").datepicker({
			changeMonth: true,
			changeYear: true,
			format : "dd/mm/yyyy",
			startDate: new Date(),
			yearRange: "-100:+0"
    });
  } );
</script>
</html>