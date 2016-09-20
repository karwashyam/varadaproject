<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<!-- 		<script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1.1','packages':['corechart']}]}"></script> -->
<script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1.1','packages':['line']}]}"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ page session="false"%>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<title>Reports</title>
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
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
            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                
           
                  <div class="x_title">
                    <h2>Franchisee Collection Report</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>

                    </ul>
                    <div class="clearfix"></div>
                      <div class="clearfix"></div>
                   
                                    
                             <div class="clearfix"></div>
                             
                              <div class="form-group">
							<label class="col-xs-3 control-label">Start Date</label>
							<div class="col-xs-3 date">
								<div class="input-group input-append date" id="datePicker">
								  <input id="startDate" name="startDate" class="form-control" placeholder="Select start date" />
									<span class="input-group-addon add-on"><span
										class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-xs-3 control-label">End Date</label>
							<div class="col-xs-3 date">
								<div class="input-group input-append date" id="datePicker">
								  <input id="endDate" name="endDate" class="form-control" placeholder="Select end date" />
									<span class="input-group-addon add-on"><span
										class="glyphicon glyphicon-calendar"></span>
								</span>
								</div>
							</div>
						</div>
  <div class="clearfix"></div>
						<div class="form-group">
							<div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
								<button type="button" name="btnReport" id="btnReport" class="btn btn-success">Search</button>
								<button  type="button" name="btnExport"  class="btn btn-success"  id="btnExport" > Export </button>
								
							</div>
						</div>
						  <div class="clearfix"></div>
                  </div>
                                   
                                   
                        <div class="x_content">
<!--                           		<div class="tab-content"> -->
<!-- 					  <div class="main-container"> -->
<!-- 						<div role="tabpanel" class="tab-pane" id="tapHist"> -->
								  <table id="franchisee-datatable" class="table table-striped table-bordered">
                      <thead>
                        <tr>
                          <th>Project</th>
                          <th>Booking Code</th>
<!--                           <th>Plot No.</th> -->
                          <th>Franchisee</th>
                          <th>Member Name</th>
                          <th>Plot Name</th>
                          <th>Payment Amount</th>
                          
<!--                           <th>Action</th> -->
                        </tr>
                      </thead>
						<tbody>
                		</tbody>

                    </table>		
                    </div>
<!-- 						</div> -->
					</div>
                </div>
           	</div>
            </div>
           </div>
           </div>
	
	
		<iframe id="txtArea1" style="display:none"></iframe>
	

<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</html>