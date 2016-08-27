<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Booking List</title>
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
                    <h2>Bookings</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                	<input type="hidden" name="isAddAccess" id="isAddAccess" value="${isAddAccess}" />
					<input type="hidden" name="isEditAccess" id="isEditAccess" value="${isEditAccess}" />
					<input type="hidden" name="isDeleteAccess" id="isDeleteAccess" value="${isDeleteAccess}" />
                  <div class="x_content">
                    <table id="booking-datatable" class="table table-striped table-bordered">
                      <thead>
                        <tr>
                          <th>Project</th>
                          <th>Booking No.</th>
                          <th>Plot No.</th>
                          <th>Franchisee</th>
                          <th>Customer</th>
                          <th>Member Code</th>
                          <th>Phone</th>
                          <th>Email</th>
                          <th>Add Payment</th>
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