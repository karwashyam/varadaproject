<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pay Tds</title>
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
                    <h2>Pay TDS</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form:form class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/tds/add" commandName="tdsModel">
                      
						<div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Tds Amount* (INR)</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                        	<form:input id="tdsAmount" path="tdsAmount" class="form-control" placeholder="" />
                          	<form:errors path="tdsAmount" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group" id="chequeNo" >
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Cheque No.</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                        	<form:input id="chequeNumber" path="chequeNumber" class="form-control" placeholder="" />
                          	<form:errors path="chequeNumber" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group" id="issueDate" >
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Issue Date</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                        	<form:input id="chequeDate" path="chequeDateString" class="form-control" placeholder="dd/mm/yyyy" />
                          	<form:errors path="chequeDateString" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group" >
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Bank</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                        	<form:input id="bank" path="bank" class="form-control" placeholder="" />
                          	<form:errors path="bank" class="errorMessage" />
                        </div>
                      </div>
                      <div class="form-group"   >
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Account Holder</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                        	<form:input id="accountHolder" path="accountHolder" class="form-control" placeholder="" />
                          	<form:errors path="accountHolder" class="errorMessage" />
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-12 col-sm-12 col-xs-12 col-md-offset-3">
                        <a href="${pageContext.request.contextPath}/tds" class="btn btn-primary" id="btnCancel" name= "btnCancel" >Cancel</a>
                          <button type="submit" id="submit" class="btn btn-success">Save</button>
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
	$("#chequeDate").datepicker({
		changeMonth: true,
		changeYear: true,
		format : "dd/mm/yyyy",
		endDate: new Date(),
		yearRange: "-100:+0"
	});	
  } );
</script>
</html>