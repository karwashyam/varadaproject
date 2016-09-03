<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Varada Home</title>
<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.2.1/css/buttons.dataTables.min.css" />
<link rel="stylesheet" href="https://cdn.datatables.net/select/1.2.0/css/select.dataTables.min.css" />
<script type="text/javascript"></script>

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
                    <h2>Edit Project</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
				<div class="x_content">
					<br />
					<form:form name="editProjectFrm" id="editProjectFrm"
						class="form-horizontal form-label-left" method="POST"
						action="${pageContext.request.contextPath}/project/edit"
						commandName="editProjectFrm" enctype="multipart/form-data">

<form:hidden path="projectId" name="projectId" id="projectId"/>
<form:hidden path="projPlotsList" name="projPlotsList" id="projPlotsList"/>

						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Project
								Name</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:input path="title" class="form-control"
									placeholder="Enter Project Heading" />
								<form:errors path="title" class="errorMessage" />
								<form:errors path="projectExists" class="errorMessage" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Booking
								Prefix</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:input path="bookingPrefix" class="form-control"
									placeholder="Enter Project Heading" />
								<form:errors path="bookingPrefix" class="errorMessage" />
							</div>
						</div>


						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Total
								No. Of Plots</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:input path="totalPlots" class="form-control"
									placeholder="Enter number of plots" />
								<form:errors path="totalPlots" class="errorMessage" />
							</div>
						</div>


						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Overview</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:input path="projectOverview" class="form-control"
									placeholder="Enter Overview" />
								<form:errors path="projectOverview" class="errorMessage" />
							</div>
						</div>


						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Build
								Up Percentage</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:input path="superBuildupPercentage" class="form-control"
									placeholder="Enter Build up percentage" />
								<form:errors path="superBuildupPercentage"
									class="errorMessage" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">Completion Date</label>
							<div class="col-xs-3 date">
								<div class="input-group input-append date" id="datePicker">
									<form:input path="completionDate" id="completionDate"
										name="completionDate" class="form-control"
										placeholder="Select date" />
									<span class="input-group-addon add-on"><span
										class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</div>
						</div>

						<%-- 	<div class="form-group">
							<label class="col-xs-3 control-label">Select File</label>
							<div class="col-xs-4">
								<form:input path="excelFile" name="excelFile" id="excelFile"
									type="file" class="filestyle" data-icon="false" />
							</div>
						</div> --%>


						<div class="ln_solid"></div>
						<div class="form-group">
							<div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
								<button type="submit" class="btn btn-success">Update</button>
							</div>
						</div>


						<div class="clearfix"></div>
				
				</div>
				<div class="x_content">
					<table id="projectPlotsDatatable"
						class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Project Plt id</th>
								<th>Plot Name</th>
								<th>Plot Size</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
						</tbody>


					</table>
					</form:form>
				</div>
			</div>
              </div>
           </div>
        </div>
	
	
	

<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</html>