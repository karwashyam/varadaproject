<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Varda : Add Member</title>
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
                    <h2>Add Member</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form:form name="memberSchFrm" id="memberSchFrm" class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/member/add" commandName="memberModel" >

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
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">State</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select name="state1" id="stateId1" class="selectpicker" data-live-search="true" title="Select State">
                            <c:forEach items="${stateModel}" var="state" >
							<option value="${state['stateId']}">${state['stateName']}</option></c:forEach>
                          </select>
                          <form:errors path="state1" class="errorMessage" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">City</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select id="cityId1" name="city1" class="selectpicker" data-live-search="true" title="Select City">
                          </select>
                          <form:errors path="city1" class="errorMessage" />
                        </div>
                      </div>
                      
                      <%-- <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">City</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="city1" class="form-control" placeholder="Enter City" />
                        	<form:errors path="city1" style="color: #ff0000;" />
                        </div>
                      </div> --%>
                      
                      <%-- <div class="form-group">
                      	<label class="control-label col-md-3 col-sm-3 col-xs-12">State</label>
                      	<div class="col-md-3 col-sm-3 col-xs-12">
                      		<form:input path="state1" class="form-control" placeholder="Enter State" />
                      		<form:errors path="state1" style="color: #ff0000;" />
                      	</div>
                      </div> --%>
                      
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
                      
                      <%-- <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">City</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="city2" class="form-control" placeholder="Enter City" />
                        	<form:errors path="city2" style="color: #ff0000;" />
                        </div>
                      </div> --%>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">State</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select name="state2" id="stateId2" class="selectpicker" data-live-search="true" title="Select State">
                            <c:forEach items="${stateModel}" var="state" >
							<option value="${state['stateId']}">${state['stateName']}</option></c:forEach>
                          </select>
                          <form:errors path="state2" class="errorMessage" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">City</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select id="cityId2" name="city2" class="selectpicker" data-live-search="true" title="Select City">
                          </select>
                          <form:errors path="city2" class="errorMessage" />
                        </div>
                      </div>
                      
                      <%-- <div class="form-group">
                      	<label class="control-label col-md-3 col-sm-3 col-xs-12">State</label>
                      	<div class="col-md-3 col-sm-3 col-xs-12">
                      		<form:input path="state2" class="form-control" placeholder="Enter State" />
                      		<form:errors path="state2" style="color: #ff0000;" />
                      	</div>
                      </div> --%>
                      
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
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">PAN *</label>
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
							<option value="${franchisee['franchiseeId']}">${franchisee['franchiseeName']}</option></c:forEach>
                          </select>
                          <form:errors path="franchiseeId" class="errorMessage" />
                        </div>
                      </div>
                      
					  <div class="ln_solid"></div>
                       <div class="form-group">
                       <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                       <a href="${pageContext.request.contextPath}/member" class="btn btn-primary" id="btnCancel" name= "btnCancel" >Cancel</a>
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
function handleClick(cb) {
	  if(cb.checked){
		  $("#address2").val($("#address1").val());
		  $("#stateId2").val($("#stateId1").val());
		  $('#stateId2').selectpicker('refresh');
		  $.ajax({
	            type: "GET",
	            url:basePath+"/franchisee/fetch/"+$("#stateId1").val()+".json",
	            async: false,
	            success: function (data) {
	            	$('#cityId2').selectpicker('refresh');
	            	   $("#cityId2").empty();
	            	   $("#cityId2").append($("<option> "+                                                 
	                "</option>").val("NONE").html("Select City"));
	            	for ( var i in data.cityList) {
	            		var id = data.cityList[i].cityId;
	            		var name = data.cityList[i].cityName;
	            		  $("#cityId2").append($("<option> "+                                                 
	                       "</option>").val(id).html(name));
	            	}
	            	$('#cityId2').selectpicker('refresh');
	            }
		  });
		  $("#cityId2").val($("#cityId1").val());
		  $('#cityId2').selectpicker('refresh');
		  $("#pincode2").val($("#pincode1").val());
	  }
	  else {
		  $("#address2").val("");
		  $("#stateId2").val("");
		  $('#stateId2').selectpicker('refresh');
		  $("#cityId2").empty();
		  $('#cityId2').selectpicker('refresh');
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
$("#stateId1").change(function() {
	var stateId=$("#stateId1").val();
	  $.ajax({
            type: "GET",
            url:basePath+"/franchisee/fetch/"+stateId+".json",
            async: false,
            success: function (data) {
            	$('#cityId1').selectpicker('refresh');
            	   $("#cityId1").empty();
            	   $("#cityId1").append($("<option> "+                                                 
                "</option>").val("NONE").html("Select City"));
            	for ( var i in data.cityList) {
            		var id = data.cityList[i].cityId;
            		var name = data.cityList[i].cityName;
            		  $("#cityId1").append($("<option> "+                                                 
                       "</option>").val(id).html(name));
            	}
            	$('#cityId1').selectpicker('refresh');
            }
	  });
  });
$("#stateId2").change(function() {
	var stateId=$("#stateId2").val();
	  $.ajax({
            type: "GET",
            url:basePath+"/franchisee/fetch/"+stateId+".json",
            async: false,
            success: function (data) {
            	$('#cityId2').selectpicker('refresh');
            	   $("#cityId2").empty();
            	   $("#cityId2").append($("<option> "+                                                 
                "</option>").val("NONE").html("Select City"));
            	for ( var i in data.cityList) {
            		var id = data.cityList[i].cityId;
            		var name = data.cityList[i].cityName;
            		  $("#cityId2").append($("<option> "+                                                 
                       "</option>").val(id).html(name));
            	}
            	$('#cityId2').selectpicker('refresh');
            }
	  });
  });
</script>
</html>