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
                    <h2>Edit Payment Scheme</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form:form name="editPaySchemeFrm" id="editPaySchemeFrm" class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/payment-scheme/edit" commandName="editPaySchemeFrm" >
			<form:hidden path="paymentSchemeId" name="paymentSchemeId" id="paymentSchemeId"/>

                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Payment Scheme Name</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="title" class="form-control" placeholder="Enter Payment scheme" />
                          <form:errors path="title" style="color: #ff0000;" />
<%--                           <form:errors path="projectExists" style="color: #ff0000;" /> --%>
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Down Payment</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="downPayment" class="form-control" placeholder="Enter down payment" />
                          <form:errors path="downPayment" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      
                        <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">No. Of Months</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="noOfMonths" class="form-control" placeholder="Enter number of months" />
                          <form:errors path="noOfMonths" style="color: #ff0000;" />
                        </div>
                      </div>
                      
                      
                        <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Rate Of Interest</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <form:input path="interestRate" class="form-control" placeholder="Enter rate of interest" />
                          <form:errors path="interestRate" style="color: #ff0000;" />
                        </div>
                      </div>

					
                        	<div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">pre payment Possible</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                        <form:radiobutton path="prepaymentPossible" class="form-control"  value="yes"/>Yes
						<form:radiobutton path="prepaymentPossible" class="form-control"  value="no"/>No
<%--                           <form:input path="prepaymentPossible" class="form-control" placeholder="Enter pre payment " /> --%>
                          <form:errors path="prepaymentPossible" style="color: #ff0000;" />
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