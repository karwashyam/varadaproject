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
				<td colspan="2" style="padding: 5px; vertical-align: top; align-self: center; /* padding-bottom: 40px; */">
						<center><b>Payment Receipt</b></center><br>
				</td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Receipt No: ${paymentModel.receiptNo}</td>
				<td colspan="1" style="width:50%;">Date: ${paymentModel.receiptNo}</td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Booking No : ${paymentModel.receiptNo}</td>
				<td colspan="1" style="width:50%;">Franchise Name : ${paymentModel.receiptNo}</td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Payment Date: ${paymentModel.receiptNo}</td>
				<td colspan="1" style="width:50%;">Reference Member: ${paymentModel.receiptNo}</td>
			</tr>
			<tr>	
				<td colspan="2" >Customer Name : ${paymentModel.receiptNo}</td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Contact No : ${paymentModel.receiptNo}</td>
				<td colspan="1" style="width:50%;">Pancard No : ${paymentModel.receiptNo}</td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Project : ${paymentModel.receiptNo}</td>
				<td colspan="1" style="width:50%;">Plot No : ${paymentModel.receiptNo}</td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Remaining Amount : ${paymentModel.receiptNo}</td>
				<td colspan="1" style="width:50%;">Payment Mode : ${paymentModel.receiptNo}</td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Bank Name : ${paymentModel.receiptNo}</td>
				<td colspan="1" style="width:50%;">Cheque No : ${paymentModel.receiptNo}</td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Cheque Date : ${paymentModel.receiptNo}</td>
			</tr>
			<tr>	
				<td colspan="2" >Amount: ${paymentModel.receiptNo}</td>
			</tr>
			<tr>	
				<td colspan="2" >Remarks : ${paymentModel.receiptNo}</td>
			</tr>
			<tr>	
				<td colspan="2" >Next Due Amount : ${paymentModel.receiptNo}</td>
			</tr>
			<tr>	
				<td colspan="2" >Note: Cheque payment is subject to realization.</td>
			</tr>
			<tr>	
				<td colspan="1" style="width:50%;">Customer's Signature __________________________</td>
				<td colspan="1" style="width:50%;">Authorized Signature __________________________</td>
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