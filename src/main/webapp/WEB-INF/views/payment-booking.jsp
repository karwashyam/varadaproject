<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="en">
<title>Select Booking for Payment</title>
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
                    <h2>Select Booking for Payment</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form:form class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/booking/add" commandName="bookingModel">
					  
					  <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Select Booking</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select id="bookingCode" name="bookingCode" class=" selectpicker" data-live-search="true" title="Select Booking">
                            <c:forEach items="${bookingModelList}" var="booking" >
							<option value="${booking['bookingId']}">${booking['bookingCode']}</option></c:forEach>
                          </select>
                          <form:errors path="bookingCode" class="errorMessage" />
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                          <button id="submit" type="button" class="btn btn-success">Add Payment</button>
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
$( function() {
	$("#birthDate1").datepicker({
		changeMonth: true,
		changeYear: true,
		format : "dd/mm/yyyy",
		endDate: new Date(),
		yearRange: "-100:+0"
	});	
	$("#paymentDate").datepicker({
		changeMonth: true,
		changeYear: true,
		format : "dd/mm/yyyy",
		yearRange: "-100:+0"
	});
	$("#nomineeDob").datepicker({
		changeMonth: true,
		changeYear: true,
		format : "dd/mm/yyyy",
		yearRange: "-100:+0"
	});
  } );
</script>
</html>