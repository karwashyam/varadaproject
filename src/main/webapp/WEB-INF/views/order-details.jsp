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
    size: auto;   /* auto is the initial value */
    margin: 0;  /* this affects the margin in the printer settings */
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
					
					<div style="float: left;">
						<a href="${pageContext.request.contextPath}/order/edit.do?order_id=${orderModel['order_id']}"><h4>Order Details</h4></a>
					</div>
					<div style="float: left; margin-left: 14px;">
						<a href="${pageContext.request.contextPath}/order/activity.do?order_id=${orderModel['order_id']}"><h4>Order Activity</h4></a>
					</div>
					
					<br/>
					<br/>
					<h4>
						Order No. <b>${orderModel['order_id']}</b>
					</h4>
					<input type="button" onclick="printDiv('printableArea')"
						value="print shipping label" style="float:right; margin-right: 60px;" /> <span style="color: #ff0000;">${msg}<br>
					<br>${msg1}</span>
					<form:form method="POST"
						action="${pageContext.request.contextPath}/order/edit.do"
						commandName="orderModel">
						<table>
							<input type="hidden" id="order_id" name="order_id"
								value="${orderModel['order_id']}" />
								<input type="hidden" id="coupon_id" name="coupon_id"
								value="${orderModel['coupon_id']}" />
							<input type="hidden" id="user_id" name="user_id"
								value="${orderModel['user_id']}" />
							<input type="hidden" id="previous_order_status"
								name="previous_order_status"
								value="${orderModel['order_status']}" />
							<tr>
								<td>User Id :</td>
								<td>${orderModel['user_id']}</td>
							</tr>
							<tr>
								<td>Full Name :</td>
								<td>${orderModel['user_full_name']}</td>
							</tr>
							<tr>
								<td>Email :</td>
								<td>${orderModel['email']}</td>
							</tr>
							<tr>
								<td>Mobile :</td>
								<td>${orderModel['phone']}</td>
							</tr>
							<tr>
								<td>Order Date :</td>
								<td>${orderModel['created_date']} <input type="hidden"
									id="created_at" name="created_at"
									value="${orderModel['created_at']}">
								</td>
							</tr>
							<tr>
								<td>Delivery Address :</td>
								<td><textarea id="address" class="form-control"
										name="address" rows="3" cols="50">${orderModel['address']}</textarea></td>
								<td><form:errors path="address" cssStyle="color: #ff0000;" /></td>
							</tr>
							<tr>
								<td>Delivery Mobile :</td>
								<td><input id="delivery_phone" type="text"
									class="form-control" name="delivery_phone"
									value="${orderModel['delivery_phone']}" /></td>
								<td><form:errors path="delivery_phone"
										cssStyle="color: #ff0000;" /></td>
							</tr>
							<tr>
								<td>Total MRP :</td>

								<td>${orderModel['total_price']}</td>
								<td><input id="total_price" type="hidden"
									class="form-control" name="total_price"
									value="${orderModel['total_price']}" /></td>
							</tr>
							<tr>
								<td>Total Discounted Price :</td>

								<td>${orderModel['total_discounted_price']}</td>
								<td><input id="total_discounted_price" type="hidden"
									class="form-control" name="total_discounted_price"
									value="${orderModel['total_discounted_price']}" /></td>
							</tr>
							<tr>
								<td>Total Courier :</td>
								<td><input id="total_courier" type="text"
									class="form-control" name="total_courier"
									value="${orderModel['total_courier']}" /></td>
								<td><form:errors path="total_courier"
										cssStyle="color: #ff0000;" /></td>
							</tr>
							<tr>
								<td>Courier Method :</td>
								<td>${orderModel['courier_method']}</td>
							</tr>
							<tr>
								<td>Coupon Discount :</td>
								<td>${orderModel['coupon_discount']}</td>
								<td><input id="coupon_discount" type="hidden"
									class="form-control" name="coupon_discount"
									value="${orderModel['coupon_discount']}" /></td>
							</tr>
							<tr>
								<td>Payment Method :</td>
								<td>${orderModel['payment_method']}</td>
							</tr>
							<tr>
								<td>Internet Handling Charge :</td>
								<td>${orderModel['internet_handling_charge']}</td>
							</tr>
							<tr>
								<td>COD charge :</td>
								<td>${orderModel['cod_charge']}</td>
							</tr>
							<tr>
								<td>Online Payment(10%) :</td>
								<td>${orderModel['online_payment']}</td>
							</tr>
							<tr>
								<td>COD Payment(90% + COD charge) :</td>
								<td>${orderModel['cod_payment']}</td>
							</tr>
							<tr>
								<td>Wallet Used :</td>
								<td>${orderModel['referral_wallet_discount']}</td>
								<td><input id="referral_wallet_discount" type="hidden"
									class="form-control" name="referral_wallet_discount"
									value="${orderModel['referral_wallet_discount']}" /></td>
							</tr>
							<tr>
								<td>Adjustment Amount :</td>
								<td><input id="adjustment_amount" type="text"
									class="form-control" name="adjustment_amount"
									value="${orderModel['adjustment_amount']}" /></td>
								<td>positive for addition(Ex: 100)<br>negative for
									discount(Ex: -100)
								</td>
								<td><form:errors path="adjustment_amount"
										cssStyle="color: #ff0000;" /></td>
							</tr>
							<tr>
								<td>Adjustment Note :</td>
								<td><input id="adjustment_note" type="text"
									class="form-control" name="adjustment_note"
									value="${orderModel['adjustment_note']}" /></td>
								<td><form:errors path="adjustment_note"
										cssStyle="color: #ff0000;" /></td>
							</tr>
							<tr>
								<td>Total Payable :</td>

								<td>${orderModel['total']}</td>
								<td><input id="total" type="hidden" class="form-control"
									name="total" value="${orderModel['total']}" /></td>
							</tr>
							<tr>
								<td>Order Note :</td>
								<td><textarea id="order_note" class="form-control"
										name="order_note" rows="3" cols="50">${orderModel['order_note']}</textarea></td>
								<td><form:errors path="order_note"
										cssStyle="color: #ff0000;" /></td>
							</tr>
							<tr>
								<td>Order Status :</td>
								<td><select id="order_status" name="order_status"
									class="form-control">
										<c:if test="${orderModel['order_status']=='open'}">
											<option value="open" selected="selected">Open</option>
											<option value="payment confirmation awaited">Payment
												confirmation awaited</option>
											<option value="on hold">On Hold</option>
											<option value="processing">Processing</option>
											<option value="in transit">In Transit</option>
											<option value="partially in transit">Partially In
												Transit</option>
											<option value="completed">Completed</option>
											<option value="partially completed">Partially
												Completed</option>
											<option value="cancel">Cancel</option>
											
											<option value="cancelled (not yet paid by customer)">Cancelled (not yet paid by customer)</option>
											<option value="returned">Returned</option>
											<option value="credit note against return">Credit note against return</option>
											<option value="credit note against payment (cancelled OOS)">Credit note against payment (cancelled OOS)</option>
											
										</c:if>
										<c:if test="${orderModel['order_status']=='processing'}">
											<option value="processing" selected="selected">Processing</option>
											<option value="in transit">In Transit</option>
											<option value="partially in transit">Partially In
												Transit</option>
											<option value="completed">Completed</option>
											<option value="partially completed">Partially
												Completed</option>
											<option value="cancel">Cancel</option>
											<option value="cancelled (not yet paid by customer)">Cancelled (not yet paid by customer)</option>
											<option value="returned">Returned</option>
											<option value="credit note against return">Credit note against return</option>
											<option value="credit note against payment (cancelled OOS)">Credit note against payment (cancelled OOS)</option>
											
										</c:if>
										<c:if test="${orderModel['order_status']=='in transit'}">
											<option value="in transit" selected="selected">In
												Transit</option>
											<option value="partially in transit">Partially In
												Transit</option>
											<option value="completed">Completed</option>
											<option value="partially completed">Partially
												Completed</option>
											<option value="cancel">Cancel</option>
											<option value="cancelled (not yet paid by customer)">Cancelled (not yet paid by customer)</option>
											<option value="returned">Returned</option>
											<option value="credit note against return">Credit note against return</option>
											<option value="credit note against payment (cancelled OOS)">Credit note against payment (cancelled OOS)</option>
											
										</c:if>
										<c:if
											test="${orderModel['order_status']=='partially in transit'}">
											<option value="in transit">In Transit</option>
											<option value="partially in transit" selected="selected">Partially
												In Transit</option>
											<option value="completed">Completed</option>
											<option value="partially completed">Partially
												Completed</option>
											<option value="cancel">Cancel</option>
											<option value="cancelled (not yet paid by customer)">Cancelled (not yet paid by customer)</option>
											<option value="returned">Returned</option>
											<option value="credit note against return">Credit note against return</option>
											<option value="credit note against payment (cancelled OOS)">Credit note against payment (cancelled OOS)</option>
											
										</c:if>
										<c:if test="${orderModel['order_status']=='completed'}">
											<option value="completed" selected="selected">Completed</option>
											<option value="partially completed">Partially
												Completed</option>
											<option value="returned">Returned</option>
											<option value="credit note against return">Credit note against return</option>
											
										</c:if>
										<c:if
											test="${orderModel['order_status']=='partially completed'}">
											<option value="completed">Completed</option>
											<option value="partially completed" selected="selected">Partially
												Completed</option>
											<option value="returned">Returned</option>
											<option value="credit note against return">Credit note against return</option>
											
										</c:if>
										<c:if test="${orderModel['order_status']=='cancel'}">
											<option value="cancel" selected="selected">Cancel</option>
											<option value="credit note against payment (cancelled OOS)">Credit note against payment (cancelled OOS)</option>
										</c:if>
										<c:if test="${orderModel['order_status']=='cancelled (not yet paid by customer)'}">
											<option value="cancelled (not yet paid by customer)" selected="selected">Cancelled (not yet paid by customer)</option>
										</c:if>
										<c:if test="${orderModel['order_status']=='credit note against payment (cancelled OOS)'}">
											<option value="credit note against payment (cancelled OOS)" selected="selected">Credit note against payment (cancelled OOS)</option>
										</c:if>
										<c:if
											test="${orderModel['order_status']=='payment confirmation awaited'}">
											<option value="payment confirmation awaited"
												selected="selected">Payment confirmation awaited</option>
											<option value="on hold">On Hold</option>
											<option value="processing">Processing</option>
											<option value="in transit">In Transit</option>
											<option value="partially in transit">Partially In
												Transit</option>
											<option value="completed">Completed</option>
											<option value="partially completed">Partially
												Completed</option>
											<option value="cancel">Cancel</option>
											<option value="cancelled (not yet paid by customer)">Cancelled (not yet paid by customer)</option>
											<option value="returned">Returned</option>
											<option value="credit note against return">Credit note against return</option>
											<option value="credit note against payment (cancelled OOS)">Credit note against payment (cancelled OOS)</option>
											
										</c:if>
										<c:if
											test="${orderModel['order_status']=='on hold'}">
											<option value="payment confirmation awaited">Payment confirmation awaited</option>
											<option value="on hold" selected="selected">On Hold</option>
											<option value="processing">Processing</option>
											<option value="in transit">In Transit</option>
											<option value="partially in transit">Partially In
												Transit</option>
											<option value="completed">Completed</option>
											<option value="partially completed">Partially
												Completed</option>
											<option value="cancel">Cancel</option>
											<option value="cancelled (not yet paid by customer)">Cancelled (not yet paid by customer)</option>
											<option value="returned">Returned</option>
											<option value="credit note against return">Credit note against return</option>
											<option value="credit note against payment (cancelled OOS)">Credit note against payment (cancelled OOS)</option>
											
										</c:if>
										<c:if test="${orderModel['order_status']=='credit note against return'}">
										    <option value="credit note against return" selected="selected">Credit note against return</option>
										</c:if>
										<c:if test="${orderModel['order_status']=='returned'}">
										    <option value="returned" selected="selected">Returned</option>
										    <option value="credit note against return">Credit note against return</option>
										</c:if>
								</select></td>
							</tr>
							<tr>
								<td><b>Customer Note: </b><br>(Only this note is<br>sent
									to customer)</td>
								<td><textarea id="customer_note" class="form-control"
										name="customer_note" rows="2" cols="50"></textarea></td>
								<c:if test="${orderModel['customer_note']!=null}">
									<td>Last Customer Note: <br>${orderModel['customer_note']}</td>
								</c:if>
								<td><form:errors path="customer_note"
										cssStyle="color: #ff0000;" /></td>
							</tr>
							<tr>
								<td></td>
								<td><input type="submit"
									class="btn btn-lg btn-primary btn-block" name="submit"
									value="Update"></td>
							</tr>

						</table>
						<table id="subordersTable"
							class="display table-container mainbarpage" cellspacing="0"
							width="100%">
							<thead>
								<tr>
									<th>Sub Order Id</th>
									<th>Product Id</th>
									<th>MRP</th>
									<th>Discounted Price</th>
									<th>Weight</th>
									<th>Product Type</th>
									<th width="115">Action</th>

								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>


					</form:form>
				</div>
			</div>
		</div>
		
		<div id="footer"></div>
	</div>
	<div id="printableArea" style="display:none;">
						<div
							style="max-width: 800px; padding: 15mm 15mm 15mm 15mm; border: 1px solid #eee; box-shadow: 0 0 10px rgba(0, 0, 0, .15); font-size: 16px; line-height: 24px; font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif; color: #555;">
							<table cellpadding="0" cellspacing="0"
								style="width: 100%; line-height: inherit; text-align: left;">

								<tr style="">
									<td colspan="2"
										style="padding: 5px; vertical-align: top; padding-bottom: 40px;">
										<table    style="width: 100%;">
											<tr>

												<td
													style="padding: 5px; vertical-align: top; line-height: 22px;"><b>Shipping
														Address:</b><br> <br>
													Phone:${orderModel['delivery_phone']}<br> ${orderModel['address']}</td>

												<td
													style="padding: 5px; vertical-align: top; text-align: right; line-height: 22px;">
													<c:if test="${orderModel['payment_method']=='90% Cash On Delivery'}">
													<b style="font-size:25px;">
													Cash On Delivery
													</b><br><br>
													Collect Rs <span id="cod-price">${orderModel['total']}</span>
													</c:if>
													<c:if test="${orderModel['payment_method']=='90% cash on delivery'}">
													<b style="font-size:25px;">
													Cash On Delivery
													</b><br><br>
													Collect Rs <span id="cod-price">${orderModel['total']}</span>
													</c:if>
													<c:if test="${orderModel['payment_method']=='Online Payment'}">
													<b style="font-size:25px;">
													Prepaid
													</b>
													</c:if>
													<c:if test="${orderModel['payment_method']=='Bank Transfer'}">
													<b style="font-size:25px;">
													Prepaid
													</b>
													</c:if>
													<c:if test="${orderModel['payment_method']=='online payment'}">
													<b style="font-size:25px;">
													Prepaid
													</b>
													</c:if>
													<c:if test="${orderModel['payment_method']=='bank transfer'}">
													<b style="font-size:25px;">
													Prepaid
													</b>
													</c:if>
													</td>
											</tr>
										</table>
									</td>
								</tr>

								<tr class="heading">
									<td
										style="padding: 5px; vertical-align: top; border-bottom: 2px solid #ddd; border-top: 2px solid #ddd; font-weight: bold;">Product</td>

									<td
										style="padding: 5px; vertical-align: top; text-align: right; border-bottom: 2px solid #ddd; border-top: 2px solid #ddd; font-weight: bold;">Price</td>
								</tr>
								<c:forEach items="${suborderModel}" var="suborder" >
								<tr style="border-bottom: 1px solid #eee;">
									<td style="padding: 5px; vertical-align: top;">${suborder['product_id']}</td>
									<td style="padding: 5px; vertical-align: top; text-align: right;">Rs ${suborder['price']}</td>
								</tr>
								</c:forEach>
								<tr class="total">
									<td style="padding: 5px; vertical-align: top;"></td>

									<td
										style="padding: 5px; vertical-align: top; text-align: right; font-weight: bold; padding-bottom: 20px;">
										Total: Rs ${orderModel['total_price']}<br>
									</td>

								</tr>

								<tr class="return" style="border-top: 1px solid #eee;">
									<td style="padding: 5px; vertical-align: top;">From <br>
										${orderModel['user_full_name']}<br> Phone: ${orderModel['phone']}
									</td>
									<td></td>

								</tr>

								<!-- <tr class="return" style="border-top: 1px solid #eee;">
									<td style="padding: 5px; vertical-align: top;"><b>Return
											Address: </b><br> 202 sagar plaza, Near Athwa Arcade Opp. T
										& TV High School, <br> Patel Wadi road, near jain temple,
										Athwa Gate, Surat</td>
									<td></td>
								</tr> -->
							</table>
						</div>
					</div>
	
	${jsFile}




</body>
</html>