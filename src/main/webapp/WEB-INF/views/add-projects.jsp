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
                    <h2>Add Project</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form:form name="projectFrm" id="projectFrm" class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/project/add" commandName="projectModel">

                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Project Name</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="title" class="form-control" placeholder="Enter Project Heading" />
                          <form:errors path="title" style="color: #ff0000;" />
                          <form:errors path="projectExists" style="color: #ff0000;" />
                          
                          
                        </div>
                      </div>
                      
                        <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Booking Prefix</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="bookingPrefix" class="form-control" placeholder="Enter Project Heading" />
                          <form:errors path="bookingPrefix" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      
                        <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Total No. Of Plots</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="totalPlots" class="form-control" placeholder="Enter number of plots" />
                          <form:errors path="totalPlots" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      
                        <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Overview</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="projectOverview" class="form-control" placeholder="Enter Overview" />
                          <form:errors path="projectOverview" style="color: #ff0000;" />
                        </div>
                      </div>

					<div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Plot Size</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="plotSize" class="form-control" placeholder="Enter Plot Size" />
                          <form:errors path="plotSize" style="color: #ff0000;" />
                        </div>
                      </div>
                      
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