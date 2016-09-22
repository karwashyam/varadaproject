<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="en">
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
                          <select id="memberId" name="memberId" class=" selectpicker" data-live-search="true" title="Select Member">
                            <c:forEach items="${memberModelList}" var="memberModel" >
							<option value="${memberModel['memberId']}_${memberModel['memberName']}_${memberModel['fatherName']}">${memberModel['memberName']}</option></c:forEach>
                          </select>
                          <form:errors path="memberId" class="errorMessage" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Franchisee</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select id="franchiseeId" name="franchiseeId" class=" selectpicker" data-live-search="true" title="Select Franchisee">
                            <c:forEach items="${franchiseeModelList}" var="franchisee" >
							<option value="${franchisee['franchiseeId']}_${franchisee['franchiseeName']}">${franchisee['franchiseeName']}</option></c:forEach>
                          </select>
                          <form:errors path="franchiseeId" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Select Project</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select id="projectId" name="projectId" class=" selectpicker" data-live-search="true" title="Select Project">
                            <c:forEach items="${projectModel}" var="project" >
							<option value="${project['projectId']}">${project['title']}</option></c:forEach>
                          </select>
                          <form:errors path="projectId" class="errorMessage" />
                        </div>	
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Select Payment Scheme</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select id="paymentSchemeId" name="paymentSchemeId" class="selectpicker" data-live-search="true" title="Select Payment Scheme">
                          </select>
                          <form:errors path="paymentSchemeId" class="errorMessage" />
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Select Plot</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select id="plotId" name="plotId" class="selectpicker" data-live-search="true" title="Select Payment Scheme">
                          </select>
                          <form:errors path="plotId" class="errorMessage" />
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
                          <form:input path="ratePerYard" class="form-control" placeholder=""  onkeypress="return isNumber(event)"/>
                          <form:errors path="ratePerYard" class="errorMessage" />
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Months</label>
                        <div id="months" class="col-md-3 col-sm-3 col-xs-12 display-data">
                        
                        </div>
                        <input type="hidden" id="noOfEmi" name="noOfEmi" value="${bookingModel[noOfEmi]}"/>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Interest Rate</label>
                        <div id="interestRate" class="col-md-3 col-sm-3 col-xs-12 display-data">
                        
                        </div>
                        <input type="hidden" id="interestRate1" name="interestRate" value="${bookingModel[interestRate]}"/>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Down Payment* (INR)</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input id="downPayment" path="downPayment" class="form-control" placeholder=""  onkeypress="return isNumber(event)"/>
                          	<form:errors path="downPayment" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">EMI</label>
                        <input type="hidden" id="emi1" name="emi" value="${bookingModel[noOfEmi]}" />
                        <div id="emi" class="col-md-3 col-sm-3 col-xs-12 display-data">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Total Amount</label>
                        <input type="hidden" id="price" name="price" value="${price}" />
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
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Nominee Name</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input id="nomineeName" path="nomineeName" class="form-control" placeholder="" />
                          	<form:errors path="nomineeName" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Nominee Father/Husband Name</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input id="nomineeFather" path="nomineeFather" class="form-control" placeholder="" />
                          	<form:errors path="nomineeFather" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Nominee Address</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:textarea id="nomineeAddress" path="nomineeAddress" class="form-control" placeholder="" />
                          	<form:errors path="nomineeAddress" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Nominee Relation</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input id="nomineeRelation" path="nomineeRelation" class="form-control" placeholder="" />
                          	<form:errors path="nomineeRelation" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Nominee DOB</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input id="nomineeDob" path="nomineeDob" class="form-control" placeholder="dd/mm/yyyy" />
                          	<form:errors path="nomineeDob" class="errorMessage" />
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Payment Mode*</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<div class="radio">
	                        	<label>
		                        	<form:radiobutton id="cash" checked="checked" path="paymentMethod" value="Cash"  placeholder="" /> Cash
	                          	</label>
                          	</div>
                          	<div class="radio">
	                        	<label>
		                        	<form:radiobutton id="cheque" path="paymentMethod" value="Cheque"  placeholder="" /> Cheque
	                          	</label>
                          	</div>
                          	<div class="radio">
	                        	<label>
		                        	<form:radiobutton id="online" path="paymentMethod" value="Online"  placeholder="" /> Online
		                          	<form:errors path="paymentMethod" class="errorMessage" />
	                          	</label>
                          	</div>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Payment Date</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input id="paymentDate" path="paymentDate" class="form-control" placeholder="dd/mm/yyyy" />
                          	<form:errors path="paymentDate" class="errorMessage" />
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group" id="chequeNo" style="display: none;">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Cheque No./Transaction Id</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input  path="chequeNo" class="form-control" placeholder="" />
                          	<form:errors path="chequeNo" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group" id="issueDate" style="display: none;">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Issue Date</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input id="birthDate2" path="issueDate" class="form-control" placeholder="dd/mm/yyyy" />
                          	<form:errors path="issueDate" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group" id="bank" style="display: none;">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Bank</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="bank" class="form-control" placeholder="" />
                          	<form:errors path="bank" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group"  id="accountHolder" style="display: none;">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Account Holder</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="accountHolder" class="form-control" placeholder="" />
                          	<form:errors path="accountHolder" class="errorMessage" />
                        </div>
                      </div>
                      <%-- <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">City Name</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="paymentSchemeId" class="form-control" placeholder="Enter City" />
                          <form:errors path="cityName" class="errorMessage" />
                        </div>
                      </div> --%>

                      
                      <div class="form-group">
                        <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                        <a href="${pageContext.request.contextPath}/city" class="btn btn-primary" id="btnCancel" name= "btnCancel" >Cancel</a>
                          <button id="submit" type="submit" class="btn btn-success">Save</button>
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
		endDate: new Date(),
		yearRange: "-100:+0"
	});	
	$("#paymentDate").datepicker({
		changeMonth: true,
		changeYear: true,
		format : "dd/mm/yyyy",
		yearRange: "-100:+0"
	});
	$("#nomineeDob").datepicker({
		changeMonth: true,
		changeYear: true,
		format : "dd/mm/yyyy",
		yearRange: "-100:+0"
	});
  } );
</script>
</html>