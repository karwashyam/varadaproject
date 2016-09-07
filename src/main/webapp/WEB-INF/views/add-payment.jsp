<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Payment</title>
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
                    <h2>Add Payment</h2>
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
						<div class="row">
                        	<div class="col-md-6 col-sm-6 col-xs-6">
	                          	<form:form id="addPayment" style="border:1px solid;" class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/booking/add-payment" commandName="bookingModel">
	                          		<input type="hidden" id="bookingId" name="bookingId" value="${penaltyModel['bookingId']}" />	
	                          		<div class="form-group">
	                          			<b><u><center>Add Payment</center></u></b>
	                          		</div>
	                          		<div class="form-group">
				                        <label class="control-label col-md-6 col-sm-6 col-xs-12">Payment Mode*</label>
				                        <div class="col-md-6 col-sm-6 col-xs-12">
				                        	<div class="radio">
					                        	<label>
						                        	<form:radiobutton id="cash" checked="checked" path="paymentMethod" value="Cash"  placeholder="" /> Cash
					                          	</label>
				                          	</div>
				                          	<div class="radio">
					                        	<label>
						                        	<form:radiobutton id="cheque" path="paymentMethod" value="Cheque"  placeholder="" /> Cheque
					                          	</label>
				                          	</div>
				                          	<div class="radio">
					                        	<label>
						                        	<form:radiobutton id="online" path="paymentMethod" value="Online"  placeholder="" /> Online
						                          	<form:errors path="paymentMethod" class="errorMessage" />
					                          	</label>
				                          	</div>
				                        </div>
				                      </div>
				                      <div class="form-group">
				                        <label class="control-label col-md-6 col-sm-6 col-xs-12">Amount* (INR)</label>
				                        <div class="col-md-6 col-sm-6 col-xs-12">
				                        	<form:input id="amount" path="amount" class="form-control" placeholder="" />
				                          	<form:errors path="amount" class="errorMessage" />
				                        </div>
				                      </div>
				                      <div class="form-group">
				                        <label class="control-label col-md-6 col-sm-6 col-xs-12">Payment Date</label>
				                        <div class="col-md-6 col-sm-6 col-xs-12">
				                        	<form:input id="paymentDate" path="paymentDate" class="form-control" placeholder="dd/mm/yyyy" />
				                          	<form:errors path="paymentDate" class="errorMessage" />
				                        </div>
				                      </div>
				                      <div class="form-group" id="chequeNo" style="display: none;">
				                        <label class="control-label col-md-6 col-sm-6 col-xs-12">Cheque No./Transaction Id</label>
				                        <div class="col-md-6 col-sm-6 col-xs-12">
				                        	<form:input id="chequeNumber" path="chequeNo" class="form-control" placeholder="" />
				                          	<form:errors path="chequeNo" class="errorMessage" />
				                        </div>
				                      </div>
				                      <div class="form-group" id="issueDate" style="display: none;">
				                        <label class="control-label col-md-6 col-sm-6 col-xs-12">Issue Date</label>
				                        <div class="col-md-6 col-sm-6 col-xs-12">
				                        	<form:input id="chequeDate" path="issueDate" class="form-control" placeholder="dd/mm/yyyy" />
				                          	<form:errors path="issueDate" class="errorMessage" />
				                        </div>
				                      </div>
				                      <div class="form-group" id="bank" style="display: none;">
				                        <label class="control-label col-md-6 col-sm-6 col-xs-12">Bank</label>
				                        <div class="col-md-6 col-sm-6 col-xs-12">
				                        	<form:input id="bankName" path="bank" class="form-control" placeholder="" />
				                          	<form:errors path="bank" class="errorMessage" />
				                        </div>
				                      </div>
				                      <div class="form-group"  id="accountHolder" style="display: none;">
				                        <label class="control-label col-md-6 col-sm-6 col-xs-12">Account Holder</label>
				                        <div class="col-md-6 col-sm-6 col-xs-12">
				                        	<form:input id="accountHolderName" path="accountHolder" class="form-control" placeholder="" />
				                          	<form:errors path="accountHolder" class="errorMessage" />
				                        </div>
				                      </div>
				                      <div class="form-group">
				                      	<label class="control-label col-md-6 col-sm-6 col-xs-12"></label>
				                        <div class="col-md-6 col-sm-6 col-xs-12">
				                        	<button id="add-payment-button" type="submit" class="btn btn-success">Add Payment</button>
				                        </div>
				                      </div>
				                   </form:form>
                        		</div>
                        		<div class="col-md-6 col-sm-6 col-xs-6">
                        			<form:form style="border:1px solid;" class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/booking/add-penalty" commandName="penaltyModel">
                        				<input type="hidden" id="bookingId" name="bookingId" value="${penaltyModel['bookingId']}" />
	                        			<div class="form-group">
		                          			<b><u><center>Add Penalty/Discount</center></u></b>
		                          		</div>
		                          		<div class="form-group">
					                        <label class="control-label col-md-6 col-sm-6 col-xs-12">Select type*</label>
					                        <div class="col-md-6 col-sm-6 col-xs-12">
					                        	<div class="radio">
						                        	<label>
							                        	<form:radiobutton checked="checked" path="type" value="penalty"  placeholder="" /> Penalty/Late Fine
						                          	</label>
					                          	</div>
					                          	<div class="radio">
						                        	<label>
							                        	<form:radiobutton path="type" value="discount"  placeholder="" /> Discount
						                          	</label>
					                          	</div>
					                        </div>
					                      </div>
					                      
					                      <div class="form-group" >
					                        <label class="control-label col-md-6 col-sm-6 col-xs-12">Amount</label>
					                        <div class="col-md-6 col-sm-6 col-xs-12">
					                        	<form:input id="amount1" path="amount1" class="form-control" placeholder="" />
					                          	<form:errors path="amount1" class="errorMessage" />
					                        </div>
					                      </div>
					                      <div class="form-group" >
					                        <label class="control-label col-md-6 col-sm-6 col-xs-12">Description</label>
					                        <div class="col-md-6 col-sm-6 col-xs-12">
					                        	<form:textarea id="description" path="description" class="form-control" placeholder="" />
					                          	<form:errors path="description" class="errorMessage" />
					                        </div>
					                      </div> 
					                      <div class="form-group">
					                      	<label class="control-label col-md-6 col-sm-6 col-xs-12"></label>
					                        <div class="col-md-6 col-sm-6 col-xs-12">
					                        	<button id="add-penalty-button" type="submit" class="btn btn-success">Add Penalty/Discount</button>
					                        </div>
					                      </div>
					                 </form:form>
                        		</div>
                      	</div>
                      <!-- Table row -->
                      <div class="ln_solid"></div>
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
		                                <td>${paymentModel['receiptNo']}</td>
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
                    </section>
                  </div>
                </div>
              </div>
           </div>
        </div>
	
	
	

<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
<script type="text/javascript">
$( function() {
	$("#paymentDate").datepicker({
		changeMonth: true,
		changeYear: true,
		format : "dd/mm/yyyy",
		yearRange: "-100:+0"
	});
	$("#chequeDate").datepicker({
		changeMonth: true,
		changeYear: true,
		format : "dd/mm/yyyy",
		yearRange: "-100:+0"
	});
  } );
</script>
</html>