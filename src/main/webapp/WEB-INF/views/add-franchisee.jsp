<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Franchise</title>
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
                    <h2>Add Franchise</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form:form name="franchiseSchFrm" id="franchiseSchFrm" class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/franchisee/add" commandName="franchiseModel" >

                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Franchise Name *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="franchiseeName" class="form-control" placeholder="Enter Franchise Name" />
                          <form:errors path="franchiseeName" style="color: #ff0000;" />
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
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Address</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:textarea path="address" class="form-control" placeholder="Enter Address" />
                          <form:errors path="address" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">City</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="city" class="form-control" placeholder="Enter City" />
                        	<form:errors path="city" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                      	<label class="control-label col-md-3 col-sm-3 col-xs-12">State</label>
                      	<div class="col-md-3 col-sm-3 col-xs-12">
                      		<form:input path="state" class="form-control" placeholder="Enter State" />
                      		<form:errors path="state" style="color: #ff0000;" />
                      	</div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Pincode</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="pincode" class="form-control" placeholder="Enter Pincode" />
                        	<form:errors path="pincode" style="color: #ff0000;" />
                        </div>
                      </div>
						
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
                        	<form:input path="landLine1" class="form-control" placeholder="XXX XXX XXXX" />
                        	<form:errors path="landLine1" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Alternate Landline</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="landLine2" class="form-control" placeholder="XXX XXX XXXX" />
                        	<form:errors path="landLine2" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">PAN</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="pan" class="form-control" placeholder="Enter PAN Details" />
                        	<form:errors path="pan" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">TDS *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="tds" class="form-control" placeholder="Enter TDS" />
                        	<form:errors path="tds" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Commission % *</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        	<form:input path="commissionPercentage" class="form-control" placeholder="Enter Commission (In Percentage)" />
                        	<form:errors path="commissionPercentage" style="color: #ff0000;" />
                        </div>
                      </div>
                      
					  <div class="ln_solid"></div>
                       <div class="form-group">
                       <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                       <a href="${pageContext.request.contextPath}/franchisee" class="btn btn-primary" id="btnCancel" name= "btnCancel" >Cancel</a>
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
</html>