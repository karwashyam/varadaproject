<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Payment Scheme</title>
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
                    <h2>Payment Scheme</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>

                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <table id="paymentSchemeDatatable" class="table table-striped table-bordered">
                      <thead>
                        <tr>
                        <th>Payment Scheme  Id</th>
                          <th>Payment Scheme Name</th>
                             <th>Down Payment</th>
                          <th>No of Months</th>
                           <th>Rate of Interest</th>
                          <th>Pre payment possible</th>
                          <th>Action</th>
                        </tr>
                      </thead>
						<tbody>
                </tbody>


                    </table>
                  </div>
                </div>
              </div>
            </div>
           </div>
        </div>
	
	
	

<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</html>