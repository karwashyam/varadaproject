<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Varada : All Franchisee Commission</title>
<script type="text/javascript">
	var basePath = '${pageContext.request.contextPath}';
</script>
<style>
@media print {
	@page {
		margin: 0;
	}
	body {
		margin: 0cm;
	}
}
</style>
<%@ include file="/WEB-INF/views/includes/common-head.jsp"%>

</head>
<%@ include file="/WEB-INF/views/includes/content-header.jsp"%>

<!-- page content -->
<div id="printableArea" class="right_col" role="main">
	<div class="">
		<div class="clearfix"></div>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>Franchisee Commission List</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">

					<section class="content invoice">
						<!-- title row -->
						<div class="row">
							<div class="col-xs-12 invoice-header">
								<h1>Franchisee Commission List</h1>
							</div>
						</div>
						<!-- Table row -->
						<div class="row">
							<div class="col-xs-12 table">
								<table class="table table-striped">
									<thead>
										<tr>
											<th>Sr. No.</th>
											<th>Franchisee Name</th>
											<th>PAN</th>
											<th>Commission</th>
											<th>TDS</th>
											<th>TDS Amount</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${franchiseCommissionList}" var="FranchiseCommissionModel">
											<tr>
												<td>${FranchiseCommissionModel['srNo']}</td>
												<td>${FranchiseCommissionModel['franchiseeName']}</td>
												<td>${FranchiseCommissionModel['pan']}</td>
												<td><label id="commission${FranchiseCommissionModel['srNo']}">${FranchiseCommissionModel['commissionUnpaid']}</label></td>
												<td><label id="tds${FranchiseCommissionModel['srNo']}" title="Click for change TDS percentage" onclick="changeTdsAmount(${FranchiseCommissionModel['srNo']},${FranchiseCommissionModel['commissionUnpaid']})">${FranchiseCommissionModel['tds']}</label></td>
												<td><label id="tdsAmount${FranchiseCommissionModel['srNo']}">${FranchiseCommissionModel['tdsAmount']}</label></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!-- /.col -->
						</div>
						<!-- /.row -->


						<!-- /.row -->

						<!-- this row will not appear when printing -->
						<div class="row no-print">
							<div class="col-xs-12">
								<button class="btn btn-default" onclick="printFunction()">
									<i class="fa fa-print"></i> Print
								</button>
							</div>
						</div>
						<div class="row no-print">
							<div style="float:right;">
								TDS : <input id="allTdsInput" onkeyup="changeAllTds()"/>
							</div>
						</div>
					</section>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
<script type="text/javascript">
var clickPerformed=false;
	function printFunction() {
		var printContents = document.getElementById("printableArea").innerHTML;
		var originalContents = document.body.innerHTML;

		document.body.innerHTML = printContents;

		window.print();

		document.body.innerHTML = originalContents;
	};
	function changeTdsAmount(srNo,commissionAmount){
		if(!clickPerformed){
			clickPerformed = true;
			document.getElementById("tds"+srNo).innerHTML="<input id='tdsInput"+srNo+"' value='"+document.getElementById("tds"+srNo).innerHTML+"' onkeyup='changeTds("+srNo+","+commissionAmount+")' onfocusout='returnLabel("+srNo+")'/>";
		}else{
			return;
		}
	}
	function changeTds(srNo,commissionAmount){
		console.log(document.getElementById("tdsInput"+srNo).value);
		if(isNaN(document.getElementById("tdsInput"+srNo).value)){
			alert("Enter the Valid Number");
			document.getElementById("tdsInput"+srNo).value=20;
		}
		else if(document.getElementById("tdsInput"+srNo).value>100 || document.getElementById("tdsInput"+srNo).value<1){
			alert("Enter the Valid Percentage");
			document.getElementById("tdsInput"+srNo).value=20;
		}
		document.getElementById("tdsAmount"+srNo).innerHTML = ((commissionAmount * document.getElementById("tdsInput"+srNo).value)/100).toFixed(2);
	}
	function returnLabel(srNo){
		clickPerformed=false;
		document.getElementById("tds"+srNo).innerHTML = document.getElementById("tdsInput"+srNo).value;
	}
	function changeAllTds(){
		if(isNaN(document.getElementById("allTdsInput").value)){
			alert("Enter the Valid Number");
			document.getElementById("allTdsInput"+srNo).value='';
			return;
		}
		else if(document.getElementById("allTdsInput").value>100 || document.getElementById("allTdsInput").value<1){
			alert("Enter the Valid Percentage");
			document.getElementById("allTdsInput"+srNo).value='';
			return;
		}
		var checkForTds=true;
		var srNo=1;
		var value=document.getElementById("allTdsInput").value;
		while(checkForTds){
			if(document.getElementById("tds"+srNo)!=undefined){
				document.getElementById("tds"+srNo).innerHTML = value;
				document.getElementById("tdsAmount"+srNo).innerHTML=((document.getElementById("commission"+srNo).innerHTML*value)/100).toFixed(2);
			}else{
				checkForTds=false;
			}
			srNo++;
		}
	}
</script>
</html>