<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Varda : Add Franchise Commission</title>
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
					<h2>Add Franchise Commission</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form name="franchiseCommissionSchFrm" id="franchiseCommissionSchFrm" class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/franchisee-commission/add-franchisee-commission" commandName="franchiseCommissionModel">

						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Franchisee *</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<select id="franchiseeId" name="franchiseeId" class=" selectpicker" data-live-search="true" title="Select Franchisee">
									<c:forEach items="${franchiseeModelList}" var="franchisee">
										<option
											value="${franchisee['franchiseeId']}_${franchisee['franchiseeName']}_${franchisee['commissionUnpaid']}_${franchisee['tds']}_${franchisee['pan']}">${franchisee['franchiseeName']}</option>
									</c:forEach>
								</select>
								<form:errors path="franchiseeId" class="errorMessage" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">PAN</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:hidden id="panValue" path="pan"/>
								<form:label id="pan" path="pan" style="background-color: #DCDCDC;" class="form-control" placeholder="Enter PAN" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Commission Due</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:label id="commissionAmount" style="background-color: #DCDCDC;" path="commissionAmount" class="form-control" placeholder="Enter Ammount" />
							</div>
						</div>
						

						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Payment Amount *</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:input id="paymentAmount" path="paymentAmount" class="form-control" placeholder="Enter Amount" />
								<form:errors path="paymentAmount" style="color: #ff0000;" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Cheque Amount</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:hidden id="chequeAmountValue" path="chequeAmount"/>
								<from:label id="chequeAmount" class="form-control" style="background-color: #DCDCDC;"/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">TDS</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:input id="tds" path="tds" class="form-control" placeholder="Enter TDS" />
								<form:errors path="tds" style="color: #ff0000;" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">TDS Amount</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<from:label id="tdsAmount" class="form-control" style="background-color: #DCDCDC;"/>
								<form:hidden id="tdsAmountValue" path="tdsAmount"/>
							</div>
						</div>

						<div class="ln_solid"></div>
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Payment Mode*</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<div class="radio">
									<label> <form:radiobutton id="cash" checked="checked" path="paymentMethod" value="Cash" placeholder="" /> Cash
									</label>
								</div>
								<div class="radio">
									<label> <form:radiobutton id="cheque" path="paymentMethod" value="Cheque" placeholder="" /> Cheque
									</label>
								</div>
								<div class="radio">
									<label> <form:radiobutton id="online" path="paymentMethod" value="Online" placeholder="" /> Online
										<form:errors path="paymentMethod" class="errorMessage" />
									</label>
								</div>
							</div>
						</div>
						
						<div class="ln_solid"></div>
						<div class="form-group" id="chequeNo" style="display: none;">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Cheque No./Transaction Id</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:input path="chequeNumber" class="form-control" placeholder="" />
								<form:errors path="chequeNumber" class="errorMessage" />
							</div>
						</div>
						
						<div class="form-group" id="issueDate" style="display: none;">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Issue Date</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:input id="chequeDate" path="chequeDateString" class="form-control" placeholder="dd/mm/yyyy" />
								<form:errors path="chequeDateString" class="errorMessage" />
							</div>
						</div>
						
						<div class="form-group" id="bank" style="display: none;">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Bank</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:input path="bank" class="form-control" placeholder="" />
								<form:errors path="bank" class="errorMessage" />
							</div>
						</div>
						
						<div class="form-group" id="accountHolder" style="display: none;">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Account Holder</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<form:input path="accountHolder" class="form-control" placeholder="" />
								<form:errors path="accountHolder" class="errorMessage" />
							</div>
						</div>
						
						<div class="form-group">
                        	<div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                          		<button id="submit" type="submit" class="btn btn-success">Save</button>
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
	$(function() {
		$("#chequeDate").datepicker({
			changeMonth : true,
			changeYear : true,
			format : "dd/mm/yyyy",
			yearRange : "-100:+0"
		});
		$("#nomineeDob").datepicker({
			changeMonth : true,
			changeYear : true,
			format : "dd/mm/yyyy",
			yearRange : "-100:+0"
		});
	});
	$('input:radio[name="paymentMethod"]').change(
			function(){
		        if ($(this).is(':checked') && $(this).val() == 'Cash') {
		        	$("#chequeNo").hide();	
		        	$("#issueDate").hide();
		        	$("#bank").hide();
		        	$("#accountHolder").hide();
		        }else  if ($(this).is(':checked') && $(this).val() == 'Cheque') {
		        	$("#chequeNo").show();
		        	$("#issueDate").show();
		        	$("#bank").show();
		        	$("#accountHolder").show();
				}else  if ($(this).is(':checked') && $(this).val() == 'Online') {
					$("#chequeNo").show();
		        	$("#issueDate").hide();
		        	$("#bank").show();
		        	$("#accountHolder").show();
				}
		    });
	$('input:radio[name="paymentMode"]').change(
			function(){
		        if ($(this).is(':checked') && $(this).val() == 'Cash') {
		        	$("#chequeNo").hide();	
		        	$("#issueDate").hide();
		        	$("#bank").hide();
		        	$("#accountHolder").hide();
		        }else  if ($(this).is(':checked') && $(this).val() == 'Cheque') {
		        	$("#chequeNo").show();
		        	$("#issueDate").show();
		        	$("#bank").show();
		        	$("#accountHolder").show();
				}else  if ($(this).is(':checked') && $(this).val() == 'Online') {
					$("#chequeNo").show();
		        	$("#issueDate").hide();
		        	$("#bank").show();
		        	$("#accountHolder").show();
				}
		    });
	$("#franchiseeId").change(function() {
    	var franchiseeDetail=$("#franchiseeId").val().split("_");
		$("#commissionAmount").html(franchiseeDetail[2]);
		$("#tds").val(franchiseeDetail[3]);
		$("#tdsAmount").html(((franchiseeDetail[2]*franchiseeDetail[3])/100).toFixed(0));
		$("#tdsAmountValue").val(((franchiseeDetail[2]*franchiseeDetail[3])/100).toFixed(0));
		$("#pan").html(franchiseeDetail[4]);
		$("#panValue").val(franchiseeDetail[4]);
		$("#chequeAmount").html('');
		$("#chequeAmountValue").val(0);
		$("#paymentAmount").val(0);
	});
	$("#paymentAmount").keyup(function() {
		var paymentAmount=$("#paymentAmount").val();
    	var tds=$("#tds").val();
    	var commissionAmount=$("#commissionAmount").html();
		$("#chequeAmount").html(paymentAmount-((paymentAmount*tds)/100).toFixed(0));
		$("#chequeAmountValue").val(paymentAmount-((paymentAmount*tds)/100).toFixed(0));
	});
	$("#tds").keyup(function() {
    	var tds=$("#tds").val();
    	var paymentAmount=$("#paymentAmount").val();
    	var commissionAmount=$("#commissionAmount").html();
		$("#chequeAmount").html(paymentAmount-((paymentAmount*tds)/100).toFixed(0));
		$("#chequeAmountValue").val(paymentAmount-((paymentAmount*tds)/100).toFixed(0));
		$("#tdsAmount").html(((commissionAmount*tds)/100).toFixed(0));
		$("#tdsAmountValue").html(((commissionAmount*tds)/100).toFixed(0));
	});
	$("#submit").click(function() {
		validateRequireFields = ["franchiseeId","paymentAmount"];	
			var isValid = validateForm();
			if (!isValid) {
				window.stop(); //should work in all major browsers
				document.execCommand("Stop"); //is necessary to support IE
				return false;
			}
		});
</script>
</html>