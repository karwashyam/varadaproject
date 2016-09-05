<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<!-- 		<script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1.1','packages':['corechart']}]}"></script> -->
<script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1.1','packages':['line']}]}"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ page session="false"%>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<title>Projects</title>
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
                    <h2>Report Booking</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>

                    </ul>
                    <div class="col-lg-5 form-fielddata">
									<select id="year" class="size form-control" style="width: 150px">
							<c:forEach var="i" begin="${startYear }" end="${endYear }">
								<option value="${i}">${i}</option>
							</c:forEach>
									</select>
								</div>
                    <div class="clearfix"></div>
                    
                    <div id="line_top_x"></div>
                  </div>
  				 <div id="graphDiv" class="col-lg-10">
                    		<div class="box box-info">
                                       
                                        <div class="box-body">
                                            <div id="chart" class="chart">
                                                <canvas id="lineChart" style="width: 90%; height: 20%;"></canvas>
                                            </div>
                                        </div><!-- /.box-body -->
                                    </div><!-- /.box -->
                    	</div>
<!-- 						<div id="columnchart_values" style="width: 900px; height: 300px;"></div> -->
                  </div>
                </div>
              </div>
            </div>
           </div>
        </div>
	
	
	

<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</html>