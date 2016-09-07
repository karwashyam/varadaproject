<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Allotment Letter</title>

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

								<tbody><tr style="">
									<td colspan="2" style="padding: 5px; vertical-align: top; align-self: center; font-size: 32px;/* padding-bottom: 40px; */">
												<center><b>Allotment Letter</b></center><br>
										</td>
										</tr>
										<tr>	
									<td colspan="2"><div style="float: right; font-size: 18px;">Date: ${bookingModel.bookingDate}</div></td><br><br>
									</tr>
									<tr>

										<td colspan="2">
										<center><b>SUBJECT : </b> ALLOTMENT LETTER  FOR PLOT NO. <b><u>${bookingModel.plotName}</u></b></center>
										</td>
									</tr>
									<tr>
										<td colspan="2">
										<center>IN <b><u>${bookingModel.projectName}</u></b></center><br> 
										</td>
									</tr>
								<tr >
												<td style="width:25%;padding:5px;vertical-align: top;">
												ALLOTEE NAME:  
												</td>
												<td ><b><u>${bookingModel.memberName}</u></b></td>
								</tr>
								<tr>
												<td style="width:25%;padding:5px;vertical-align: top;">
												ADDRESS:  
												</td>
												<td >${bookingModel.address1}<br>
												${bookingModel.city1}&nbsp;${bookingModel.pincode1}<br>
												${bookingModel.state1}<br>
												(M) ${bookingModel.phone1}<br>
												${bookingModel.email}<br></td>
								</tr>
								
								<tr >
									<td colspan="2" style="padding: 5px; vertical-align: top;width:100%">
										<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;THIS IS TO CONFIRM THAT THE ABOVE NAMED ALLOTTEE HAS BEEN ALLOTTED PROPOSED PLOT
										NO. : ${bookingModel.plotName}, IN OUR SCHEME ${bookingModel.projectName} AS PER THE TERMS AND CONDITIONS OF AND WITHIN THE
										SCOPE OF BOOKING TERM FORM APPLICATION NO. : ${bookingModel.bookingCode} & BOOKING DATE ${bookingModel.bookingDate}.
										</p>
									</td>
								</tr>
								<tr>
									<td colspan="2">
									<b><u>PLOT & PAYMENT CONDITIONS OF ALLOTMENT</u></b>
									</td>
								</tr>
								<tr >
									<td>
									<br>
									PLOT NO :  
									</td>
									<td ><br><b>${bookingModel.plotName}</b></td>
								</tr>
								<tr >
									<td>
									PLOT AREA :  
									</td>
									<td ><b>${bookingModel.plotSize} SQ. YD. SUPER BUILT UP.</b></td>
								</tr>
								<tr >
									<td>
									RATE :
									</td>
									<td >Rs ${bookingModel.ratePerYard}/- PER YARD</td>
								</tr>
								<tr >
									<td>
									TOTAL PRICE :  
									</td>
									<td >Rs ${bookingModel.price}/-</td>
								</tr>
								<tr >
									<td>
									DOWN PAYMENT :  
									</td>
									<td>Rs ${bookingModel.downPayment}/-</td>
								</tr>
								<tr >
									<td>
									INSTALLMENT :  
									</td>
									<td >Rs ${bookingModel.emi}/-</td>
								</tr>
								<tr >
									<td>
									PAYMENT TERM :  
									</td>
									<td >${bookingModel.title}</td>
								</tr>
								<tr>
									<td colspan="2">
										<br>
										<p>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;THIS ALLOTMENT LETTER DOES NOT CREATE ANY KIND OF RIGHT OR TITLE TO THE SAID PLOT
IN FAVOR OF THE PROPOSED PURCHASER OF THE PLOT NOR DOES IT GIVE RIGHT TO CREATE A CHARGE,
LIEN MORTGAGE, ON THE SAID PLOT OF LAND TILL THE EXECUTION OF FINAL DEED OF ALLOTMENT. THIS
RESERVATION LETTER DOES NOT PASS THE TITLE IN FAVOR OF THE PROPOSED PURCHASER NOR IS
HE/SHE LEGALLY ENTITLED TO THE POSSESSION OF THE SAID PLOT OF LAND OR ANY PART THERE OF.
										</p>
									</td>
								</tr>

							</tbody></table>
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