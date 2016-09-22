<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Varda : Edit Member</title>
<!-- <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css" /> -->
<!-- <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" /> -->


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
                    <h2>Edit Member</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form:form name="memberSchFrm" id="memberSchFrm" class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/member/edit-member" commandName="memberModel" >
					  <form:hidden path="memberId" />
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Member Name *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="memberName" class="form-control" placeholder="Enter Member Name" />
                          <form:errors path="memberName" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Father Name *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="fatherName" class="form-control" placeholder="Enter Father's Name" />
                          <form:errors path="fatherName" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Email *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="email" class="form-control" placeholder="Enter Email" />
                          <form:errors path="email" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Date of Birth *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="dob" class="form-control" id="birthDate" placeholder="dd/mm/yyyy" />
                          <form:errors path="dob" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Gender *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<div class="radio">
	                        	<label>
		                        	<form:radiobutton id="male" checked="checked" path="gender" value="Male"  placeholder="" /> Male
	                          	</label>
                          	</div>
                          	<div class="radio">
	                        	<label>
		                        	<form:radiobutton id="female" path="gender" value="Female"  placeholder="" /> Female
		                        	<form:errors path="gender" style="color: #ff0000;" />
	                          	</label>
                          	</div>
                        </div>
                      </div>
                      
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Permanent Address</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:textarea path="address1" class="form-control" placeholder="Enter Address" />
                          <form:errors path="address1" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">City</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="city1" class="form-control" placeholder="Enter City" />
                        	<form:errors path="city1" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                      	<label class="control-label col-md-3 col-sm-3 col-xs-12">State</label>
                      	<div class="col-md-3 col-sm-3 col-xs-12">
                      		<form:input path="state1" class="form-control" placeholder="Enter State" />
                      		<form:errors path="state1" style="color: #ff0000;" />
                      	</div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Pincode</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="pincode1" class="form-control" placeholder="Enter Pincode" />
                        	<form:errors path="pincode1" style="color: #ff0000;" />
                        	<label><input type='checkbox' onclick='handleClick(this);'>Same Residance Address</label>
                        </div>
                      </div>
                      
                      <div class="ln_solid"></div>
                      
					  <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Residance Address</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:textarea path="address2" class="form-control" placeholder="Enter Address" />
                          <form:errors path="address2" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">City</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="city2" class="form-control" placeholder="Enter City" />
                        	<form:errors path="city2" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                      	<label class="control-label col-md-3 col-sm-3 col-xs-12">State</label>
                      	<div class="col-md-3 col-sm-3 col-xs-12">
                      		<form:input path="state2" class="form-control" placeholder="Enter State" />
                      		<form:errors path="state2" style="color: #ff0000;" />
                      	</div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Pincode</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="pincode2" class="form-control" placeholder="Enter Pincode" />
                        	<form:errors path="pincode2" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="ln_solid"></div>
                      
					  <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Phone *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="phone1" class="form-control" placeholder="91XXXXXXXX" />
                        	<form:errors path="phone1" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Alternate Phone</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="phone2" class="form-control" placeholder="91XXXXXXXX" />
                        	<form:errors path="phone2" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Fax no.</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="fax" class="form-control" placeholder="XXX XXX XXXX" />
                        	<form:errors path="fax" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Landline</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="landline1" class="form-control" placeholder="XXX XXX XXXX" />
                        	<form:errors path="landline1" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Alternate Landline</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="landline2" class="form-control" placeholder="XXX XXX XXXX" />
                        	<form:errors path="landline2" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">PAN</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="pancard" class="form-control" placeholder="Enter PAN Details" />
                        	<form:errors path="pancard" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Franchise Name</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<select name="franchiseeId" class="form-control">
                        	<c:forEach items="${franchiseList}" var="franchisee" >
                            <c:choose>
							  <c:when test="${franchisee['franchiseeId'] == franchiseeId}">
							  <option value="${franchisee['franchiseeId']}" selected ="selected">${franchisee['franchiseeName']}</option>
							  </c:when>
							  <c:otherwise>
							  <option value="${franchisee['franchiseeId']}">${franchisee['franchiseeName']}</option>
							  </c:otherwise>
							</c:choose>
							</c:forEach>
                          </select>
                          <form:errors path="franchiseeId" class="errorMessage" />
                        </div>
                      </div>
                      
					  <div class="ln_solid"></div>
                       <div class="form-group">
                       <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                       <a href="${pageContext.request.contextPath}/member" class="btn btn-primary" id="btnCancel" name= "btnCancel" >Cancel</a>
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

<script type="text/javascript">
function handleClick(cb) {
	  if(cb.checked){
		  $("#address2").val($("#address1").val());
		  $("#city2").val($("#city1").val());
		  $("#state2").val($("#state1").val());
		  $("#pincode2").val($("#pincode1").val());
	  }
	  else {
		  $("#address2").val("");
		  $("#city2").val("");
		  $("#state2").val("");
		  $("#pincode2").val("");
	  }
	}
$( function() {
	$("#birthDate").datepicker({
		changeMonth: true,
		changeYear: true,
		format : "dd/mm/yyyy",
		endDate: new Date(),
		yearRange: "-100:+0"
	});	
  } );
</script>
</html>