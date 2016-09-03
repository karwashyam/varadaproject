<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Varada Home</title>
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
                    <h2>Add Project</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form:form name="projectFrm" id="projectFrm" class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/project/add" commandName="projectModel" enctype="multipart/form-data">

                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Project Name</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="title" class="form-control" placeholder="Enter Project Heading" />
                          <form:errors path="title" class="errorMessage" />
                          <form:errors path="projectExists" class="errorMessage" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Booking Prefix</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="bookingPrefix" class="form-control" placeholder="Enter Project Heading" />
                          <form:errors path="bookingPrefix" class="errorMessage" />
                        </div>
                      </div>
                      
                      
                        <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Total No. Of Plots</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="totalPlots" class="form-control" placeholder="Enter number of plots" />
                          <form:errors path="totalPlots" class="errorMessage" />
                        </div>
                      </div>
                      
                      
                        <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Overview</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="projectOverview" class="form-control" placeholder="Enter Overview" />
                          <form:errors path="projectOverview" class="errorMessage" />
                        </div>
                      </div>

					
                        	<div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Build Up Percentage</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="superBuildupPercentage" class="form-control" placeholder="Enter Build up percentage" />
                          <form:errors path="superBuildupPercentage" class="errorMessage" />
                        </div>
                      </div>
						<div class="form-group">
							<label class="col-xs-3 control-label">Completion Date</label>
							<div class="col-xs-3 date">
								<div class="input-group input-append date" id="datePicker">
								  <form:input path="completionDate" id="completionDate" name="completionDate" class="form-control" placeholder="Select date" />
									<span class="input-group-addon add-on"><span
										class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-xs-3 control-label">Select File</label>
							<div class="col-xs-4">
								<form:input path="excelFile" name="excelFile" id="excelFile"
									type="file" class="filestyle" data-icon="false" />
							</div>
						</div>
						<%-- 	<div class="form-group">
							<div class="fileinput fileinput-new" data-provides="fileinput">
								<span class="btn btn-default btn-file"> <span>
										Choose file</span> <form:input type="file" path="excelFile"
										name="excelFile" id="excelFile" /></span> <span
									class="fileinput-filename"></span> <span class="fileinput-new">No
									file chosen</span>
							</div>

						</div> --%>


						<div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
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