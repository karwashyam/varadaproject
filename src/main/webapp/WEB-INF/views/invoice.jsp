<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Order Details</title>

<%@ include file="/WEB-INF/views/includes/common-head.jsp"%>
</head>
<style>
html, body {
	background-color: #ffffff;
	margin: 0 0 0 0;
	height: 100%;
}

#container {
	width: 100%;
	height: 100%;
}

#subcontainer {
	display: table;
	width: 100%;
	height: 100%;
}

#sidebar {
	width: 20%;
	background-color: grey;
	color: black;
	display: table-cell;
	height: 100%;
}

.logo {
	MARGIN-TOP: 79PX;
	margin-left: 15px;
	border-left: -8em;
	border-right: -8em;
}

li {
	padding-top: 15px;
}

td, th {
	padding: 6px;
}

#pagecontainer {
	width: 80%;
	display: table-cell;
	background-color: #FFFFFF;
	color: black;
	padding-left: 25px;
}
</style>
<style type="text/css" media="print">
@page {
	size: auto; /* auto is the initial value */
	margin: 0; /* this affects the margin in the printer settings */
}
</style>
<body>
	<div style="top: 20px; right: 20px; position: absolute;">
		<a href="${pageContext.request.contextPath}/logout.do">logout</a>
	</div>
	<div id="container">
		<div id="subcontainer">
			<div id="sidebar">
				<%@ include file="/WEB-INF/views/includes/side-bar.jsp"%>
			</div>
			<div id="pagecontainer">
				<div class="page">
					<input type="button" onclick="printDiv('printableArea')"
						value="print shipping label" />



					<div id="printableArea">
						<div
							style="max-width: 800px; padding: 15mm 15mm 15mm 15mm; border: 1px solid #eee; box-shadow: 0 0 10px rgba(0, 0, 0, .15); font-size: 16px; line-height: 24px; font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; color: #555;">
							<table cellpadding="0" cellspacing="0"
								style="width: 100%; line-height: inherit; text-align: left;">

								<tr style="">
									<td colspan="2"
										style="padding: 5px; vertical-align: top; padding-bottom: 40px;">
										<table>
											<tr>

												<td
													style="padding: 5px; vertical-align: top; line-height: 22px;"><b>Shipping
														Address:</b><br> RAMNARAYAN KESHRI<br>
													Phone:8388830470<br> Near­ Rohit Ferro Tech Ltd.
													WBIIDC Area, Dwarika Bishnupur, BANKURA NSZ PIN:722122</td>

												<td
													style="padding: 5px; vertical-align: top; text-align: right; line-height: 22px;"><b>COD/Prepaid</b><br>
													Rs 1000</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr class="heading">
									<td
										style="padding: 5px; vertical-align: top; border-bottom: 2px solid #ddd; border-top: 2px solid #ddd; font-weight: bold;">Item</td>

									<td
										style="padding: 5px; vertical-align: top; text-align: right; border-bottom: 2px solid #ddd; border-top: 2px solid #ddd; font-weight: bold;">Price</td>
								</tr>

								<tr style="border-bottom: 1px solid #eee;">
									<td style="padding: 5px; vertical-align: top;">Website
										design</td>

									<td
										style="padding: 5px; vertical-align: top; text-align: right;">$300.00</td>
								</tr>

								<tr class="total">
									<td style="padding: 5px; vertical-align: top;"></td>

									<td
										style="padding: 5px; vertical-align: top; text-align: right; font-weight: bold; padding-bottom: 20px;">
										Total: $385.00<br>
									</td>

								</tr>

								<tr class="return" style="border-top: 1px solid #eee;">
									<td style="padding: 5px; vertical-align: top;">From <br>
										ABC Franchisee<br> Phone: 98798797989
									</td>
									<td></td>

								</tr>

								<tr class="return" style="border-top: 1px solid #eee;">
									<td style="padding: 5px; vertical-align: top;"><b>Return
											Address: </b><br> 202 sagar plaza, Near Athwa Arcade Opp. T
										& TV High School, <br> Patel Wadi road, near jain temple,
										Athwa Gate, Surat</td>
									<td></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="footer"></div>
	</div>
	${jsFile}
</body>
</html>