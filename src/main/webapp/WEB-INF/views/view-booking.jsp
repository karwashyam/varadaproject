<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Varada Home</title>
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
                    <h2>Booking Details</h2>
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
                                          Booking No. ${bookingModel.bookingCode}
                                          <small class="pull-right">Date: ${bookingModel.todayDate}</small>
                                      </h1>
                        </div>
                        <!-- /.col -->
                      </div>
                      <div class="ln_solid"></div>
                      <!-- info row -->
                      <div class="row invoice-col">
                        <!-- /.col -->
                        <div class="col-sm-4 invoice-col">
                          <b>Project:</b> ${bookingModel.projectName}
                          <br>
                          <b>Member Name:</b> ${bookingModel.memberName}
                          <br>
                          <b>Email:</b> ${bookingModel.email}
                          <br>
                          <b>Phone:</b> ${bookingModel.phone1}
                          <br>
                          <b>Plot No.:</b> ${bookingModel.plotName}
                          <br>
                          <b>Rate Per Yard:</b> ${bookingModel.ratePerYard}
                          <br>
                          <b>Plot Size:</b> ${bookingModel.plotSize}
                          <br>
                          <b>Booking Date:</b> ${bookingModel.bookingDate}
                          <br>
                          <b>Payment Scheme:</b> ${bookingModel.title}
                          <br>
                          <b>Allotment Letter Given:</b> ${bookingModel.allotmentLetterGiven}
                          <br>
                          <br>
                        </div>
                        <!-- /.col -->
                      
                      <div class="col-sm-4 invoice-col">
                          Primary Address
                          <address>
                                          <strong>${bookingModel.memberName}</strong>
                                          <br>${bookingModel.address1}
                                          <br>${bookingModel.city1}, ${bookingModel.state1} - ${bookingModel.pincode1}
                                          <br>Phone: ${bookingModel.phone1}
                                          <br>Email: ${bookingModel.email}
                                      </address>
                        </div>
                        <!-- /.col -->
                        <div class="col-sm-4 invoice-info">
                          <b>Nominee Details</b>
                          <br>
                          <br>
                          <b>Nominee Name:</b> ${bookingModel.nomineeName}
                          <br>
                          <b>Nominee Father/Husband Name:</b> ${bookingModel.nomineeFather}
                          <br>
                          <b>Nominee Address:</b> ${bookingModel.nomineeAddress}
                          <br>
                          <b>Nominee Relation:</b> ${bookingModel.nomineeRelation}
                          <br>
                          <b>Nominee DOB:</b> ${bookingModel.nomineeDob}
                          <br>
                        </div>
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
                                <th style="width: 20%">Description</th>
                                <th>Receipt No</th>
                                <th>Payment Date</th>
                                <th>Cheque No/Transaction Id</th>
                                <th>Cheque Date	</th>
                                <th>Bank</th>
                                <th>Account Holder</th>
                                <th>Entry By</th>
                                <th>Amount</th>
                              </tr>
                            </thead>
                            <tbody>
                              	<c:forEach items="${paymentModelList}" var="paymentModel" >
									<tr>
		                                <td>${paymentModel['srNo']}</td>
		                                <c:choose>
			                                <c:when test="${paymentModel['paymentMode'] == NULL}">
											  <td>${paymentModel['description']}  </td>
											</c:when>
											<c:otherwise>
											  <td>${paymentModel['paymentMode']} Payment
											  <c:if test="${paymentModel['description'] != NULL}">
											  &nbsp;(${paymentModel['description']})
											  </c:if>
											  </td>
											</c:otherwise>
										</c:choose>
		                                <td style="cursor:pointer;" onclick="printReceipt('${paymentModel['paymentId']}')">${paymentModel['receiptNo']}</td>
		                                <td>${paymentModel['emiDateString']}</td>
		                                <td>${paymentModel['chequeNumber']}${paymentModel['transactionNumber']}</td>
		                                <td>${paymentModel['chequeDateString']}</td>
		                                <td>${paymentModel['bank']}</td>
		                                <td>${paymentModel['accountHolder']}</td>
		                                <td>${paymentModel['fullName']}</td>
		                                <td>${paymentModel['paymentAmount']}&nbsp;${paymentModel['type']}</td>
		                            </tr>
								</c:forEach>
                              
                            </tbody>
                          </table>
                        </div>
                        <!-- /.col -->
                      </div>
                      <!-- /.row -->

                      <div class="row">
                        <!-- accepted payments column -->
                        <!-- /.col -->
                        <div class="col-xs-4 pull-right">
<!--                           <p class="lead">Amount PA 2/22/2014</p> -->
                          <div class="table-responsive">
                            <table class="table">
                              <tbody>
                                <tr>
                                  <th style="width:50%">Booking Price:</th>
                                  <td>Rs ${bookingModel.price}</td>
                                </tr>
                                <tr>
                                  <th>Remaining Installments:</th>
                                  <td>${bookingModel.noOfEmi}</td>
                                </tr>
                                <tr>
                                  <th>EMI:</th>
                                  <td>Rs ${bookingModel.emi}</td>
                                </tr>
                                <tr>
                                  <th>Amount Paid:</th>
                                  <td>Rs ${bookingModel.paymentMadeTillNow}</td>
                                </tr>
                                <tr>
                                	<th>Payment Due:</th>
                                	<td>Rs ${bookingModel.remainingPayment}</td>
                                </tr>
                              </tbody>
                            </table>
                          </div>
                        </div>
                        <!-- /.col -->
                      </div>
                      <!-- /.row -->

                      <!-- this row will not appear when printing -->
                      <div class="row no-print">
                        <div class="col-xs-12">
                          <button class="btn btn-default" onclick="printFunction()"><i class="fa fa-print"></i> Print</button>
                          <c:if test="${bookingModel['recordStatus'] == 'A'}">
                          <button onclick="allotmentLetter('${bookingModel.bookingId}')" class="btn btn-default"><i class="fa fa-print"></i> Print Allotment Letter</button>
                          <button id="transferBooking" onclick="transferBooking('${bookingModel.bookingId}','${bookingModel.memberId}')"  class="btn btn-primary pull-right" style="margin-right: 5px;"> Transfer Booking</button>
                          <button id="cancelBooking" onclick="cancelBooking('${bookingModel.bookingId}')" class="btn btn-primary pull-right" style="margin-right: 5px;"> Cancel Booking</button>
                          <button id="addPayment" onclick="addPayment('${bookingModel.bookingId}')" class="btn btn-success pull-right"><i class="fa fa-credit-card"></i> Add Payment</button>
                          </c:if>
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
function allotmentLetter(bookingId){
	window.open(
			  basePath+'/booking/allotment-letter/'+bookingId,
			  '_blank' // <- This is what makes it open in a new window.
			);
};

function printReceipt(paymentId){
	window.open(
			  basePath+'/payment/print-receipt/'+paymentId,
			  '_blank' // <- This is what makes it open in a new window.
			);
};


</script>
</html>