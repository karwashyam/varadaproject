<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TDS History</title>
<script type="text/javascript">
	var basePath = '${pageContext.request.contextPath}';
</script> 

<%@ include file="/WEB-INF/views/includes/common-head.jsp"%>

</head>
<%@ include file="/WEB-INF/views/includes/content-header.jsp"%>

	<!-- page content -->
        <div class="right_col" role="main">
          <div class="">
          <!-- <div class="page-title">
              <div class="title_left">
                <h3>TDS </h3>
              </div>
           </div> -->
            <div class="clearfix"></div>
            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>TDS Due: Rs <span id="tdsDue"></span></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
<!--                       <li class="dropdown"> -->
<!--                         <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a> -->
<!--                         <ul class="dropdown-menu" role="menu"> -->
<!--                           <li><a href="#">Settings 1</a> -->
<!--                           </li> -->
<!--                           <li><a href="#">Settings 2</a> -->
<!--                           </li> -->
<!--                         </ul> -->
<!--                       </li> -->
<!--                       <li><a class="close-link"><i class="fa fa-close"></i></a> -->
<!--                       </li> -->
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <table id="tds-datatable" class="table table-striped table-bordered">
                      <thead>
                        <tr>
                        	<th>Sr. No</th>
                          	<th>Franchisee</th>
                          	<th>Tds</th>
                          	<th>Cheque Number</th>
                          	<th>Bank</th>
                          	<th>Account Holder</th>
                          	<th>Cheque Date</th>
                          	<th>Added Date</th>
                          	<th>TDS Amount</th>
                        </tr>
                      </thead>
						<tbody>
                </tbody>

<!--                       <tbody> -->
<!--                         <tr> -->
<!--                           <td>Tiger Nixon</td> -->
<!--                           <td>System Architect</td> -->
<!--                           <td>Edinburgh</td> -->
<!--                         </tr> -->
<!--                       </tbody> -->
                    </table>
                  </div>
                </div>
              </div>
            </div>
           </div>
        </div>
	
	
	

<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</html>