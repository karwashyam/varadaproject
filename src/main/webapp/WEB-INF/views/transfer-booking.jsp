<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Transfer Booking</title>
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
                    <h2>Transfer Amount and Cancel this booking</h2>
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
                          <b>Amount Paid:</b>Rs ${bookingModel.paymentMadeTillNow}
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
					<form:form class="form-horizontal form-label-left" method="POST" action="${pageContext.request.contextPath}/booking/transfer" commandName="transferModel">
					  
					  <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Select Booking</label>
                        <div class="col-md-3 col-sm-3 col-xs-12">
                          <select id="bookingId" name="bookingId" class=" selectpicker" data-live-search="true" title="Select Booking to Transfer">
                            <c:forEach items="${bookingIdList}" var="bookingIds" >
							<option value="${bookingIds['id']}">${bookingIds['code']}</option></c:forEach>
                          </select>
                          <form:errors path="bookingId" class="errorMessage" />
                        </div>
                      </div>
                      <input type="hidden" id="memberId" name="memberId" value="${memberId}" />
                      <input type="hidden" id="memberBookingId" name="memberBookingId" value="${transferModel['memberBookingId']}" />
                       <div class="form-group">
                        <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                          <button id="transfer-button" type="submit" class="btn btn-success">Transfer Amount And Cancel Booking</button>
                        </div>
                      </div>
                       
                       
                      </form:form>
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
</script>
</html>