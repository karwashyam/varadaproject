<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Print Receipt</title>

<style>
@media print {
  @page { margin: 0; }
  body { margin: 0cm; }
}
</style>
</head>
<body>
<div id="printableArea" >
<div style="max-width: 800px; padding: 15mm 15mm 15mm 15mm; border: 1px solid #eee; box-shadow: 0 0 10px rgba(0, 0, 0, .15); font-size: 16px; line-height: 24px; font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; color: #555;">
	<table cellpadding="0" cellspacing="0" style="width: 100%; line-height: inherit; text-align: left;">
		<tbody>
			<tr style="">
				<td colspan="2" style="padding: 5px; vertical-align: top; align-self: center;font-size:18px; /* padding-bottom: 40px; */">
						<center><b>Payment Receipt</b></center><br>
				</td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Receipt No:<b> ${paymentModel.receiptNo}</b></td>
				<td colspan="1" style="width:50%;">Date: <b>${bookingModel.todayDate}</b></td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Booking No : <b>${bookingModel.bookingCode}</b></td>
				<td colspan="1" style="width:50%;">Franchise Name : ${bookingModel.franchiseeName}</td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Payment Date: <b>${paymentModel.emiDateString}</b></td>
			</tr>
			<tr>	
				<td colspan="2" >Customer Name : <b>${bookingModel.memberName}</b></td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Contact No : <b>${bookingModel.phone1}</b></td>
				<td colspan="1" style="width:50%;">Pancard No : <b>${bookingModel.pancard}</b></td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Project : <b>${bookingModel.projectName}</b></td>
				<td colspan="1" style="width:50%;">Plot No :<b> ${bookingModel.plotName}</b></td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Remaining Amount : <b>${bookingModel.remainingPayment}</b></td>
				<td colspan="1" style="width:50%;">Payment Mode : <b>${paymentModel.paymentMode}</b></td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Bank Name : <b>${paymentModel.bank}</b></td>
				<td colspan="1" style="width:50%;">Cheque No : <b>${paymentModel.chequeNumber}</b></td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Cheque Date : <b>${paymentModel.chequeDateString}</b></td>
			</tr>
			<tr>	
				<td colspan="2" >Amount: <b>${paymentModel.paymentAmount}</b></td>
			</tr>
			<tr>	
				<td colspan="2" >Next Due Amount : <b>${bookingModel.emi}&nbsp;(${bookingModel.nextEmiOnString})</b></td>
			</tr>
			<tr>	
				<td colspan="2" >Note: Cheque payment is subject to realization.</td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;"><br><br>Customer's Signature</td>
				<td colspan="1" style="width:50%;"><br><br>Authorized Signature</td>
			</tr>
			
		</tbody>
	</table>
</div>
</div>
</body>
<script>
var printContents = document.getElementById("printableArea").innerHTML;
var originalContents = document.body.innerHTML;
document.body.innerHTML = printContents;
window.print();
</script>
</html>