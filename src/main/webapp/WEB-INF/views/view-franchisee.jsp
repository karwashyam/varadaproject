<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Varada : View Franchise</title>
<script type="text/javascript">
	var basePath = '${pageContext.request.contextPath}';
</script> 
<style>
@media print {
  @page { margin: 0; }
  body { margin: 0cm; }
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
                    <h2>Franchise Details</h2>
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
                          <h1>
                                          Franchise Name: ${franchiseModel.franchiseeName}
                                      </h1>
                        </div>
                        <!-- /.col -->
                      </div>
                      <div class="ln_solid"></div>
                      <!-- info row -->
                      <div class="row invoice-col">
                        <!-- /.col -->
                        <div class="col-sm-4 invoice-col">
                          <b>PAN Card:</b> ${franchiseModel.pan}
                          <br>
                          <b>Commission Paid:</b> ${franchiseModel.commissionPaid}
                          <br>
                          <b>Commission UnPaid:</b> ${franchiseModel.commissionUnpaid}
                          <br>
                          <b>TDS:</b> ${franchiseModel.tds}
                          <br>
                          <b>TDS Amount:</b> ${franchiseModel.tdsPaid}
                          <br>
                        </div>
                        <!-- /.col -->
                      
                      <div class="col-sm-4 invoice-col">
                          Primary Address
                          <address>
                                          <strong>${franchiseModel.franchiseeName}</strong>
                                          <br>${franchiseModel.address}
                                          <br>${franchiseModel.city}, ${franchiseModel.state} - ${franchiseModel.pincode}
                                          <br>Phone: ${franchiseModel.phone1}
                                          <br>Email: ${franchiseModel.email}
                                      </address>
                        </div>
                        <!-- /.col -->
                      <!-- /.row -->
						</div>
						<div class="ln_solid"></div>
                      <!-- Table row -->
                      <div class="row">
                        <div class="col-xs-12 table">
                          <table class="table table-striped">
                            <thead>
                              <tr>
                                <th>Sr. No.</th>
                                <th>Booking Code</th>
                                <th>Project Name</th>
                                <th>Cheque No/Transaction Id</th>
                                <th>Payment Date</th>
                                <th>Bank</th>
                                <th>Account Holder</th>
                                <th>Entry By</th>
                                <th>Amount</th>
                                <th>Commission Amount</th>
                              </tr>
                            </thead>
                            <tbody>
                              	<c:forEach items="${franchiseCommissionList}" var="paymentModel" >
									<tr>
		                                <td>${FranchiseCommissionModel['srNo']}</td>
		                                <td>${FranchiseCommissionModel['bookingCode']}</td>
		                                <td>${FranchiseCommissionModel['projectName']}</td>
		                                <td>${FranchiseCommissionModel['chequeNumber']}${paymentModel['transactionNumber']}</td>
		                                <td>${FranchiseCommissionModel['chequeDateString']}</td>
		                                <td>${FranchiseCommissionModel['bank']}</td>
		                                <td>${FranchiseCommissionModel['accountHolder']}</td>
		                                <td>${FranchiseCommissionModel['memberName']}</td>
		                                <td>${FranchiseCommissionModel['commissionAmount']}&nbsp;${FranchiseCommissionModel['status']}</td>
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
                          <button class="btn btn-default" onclick="printFunction()"><i class="fa fa-print"></i> Print</button>
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
function printFunction(){
	var printContents = document.getElementById("printableArea").innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;
};

</script>
</html>