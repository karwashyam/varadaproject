<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Order Details</title>

<%@ include file="/WEB-INF/views/includes/common-head.jsp"%>
</head>
<style>
html, body {
    background-color:#ffffff;
    margin:0 0 0 0;
    height:100%;
}

#container {
    width: 100%;
    height: 100%;

}

.errorMessage {
  color: #ff0000;

}

#subcontainer {
display:table;
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
        border-left:-8em;
        border-right:-8em;
    }
    li{
    padding-top:15px;
    }
    td, th {
    padding: 6px;
	}

#pagecontainer {
    width:80%;
  display:table-cell;
    background-color:#FFFFFF;
  color:black;
      padding-left: 25px;
      }
</style>
<body>
<div style="top:20px;right:20px;position:absolute;"><a href="${pageContext.request.contextPath}/logout.do">logout</a></div>
<div id="container">
    <div id="subcontainer">
       	<%@ include file="/WEB-INF/views/includes/side-bar.jsp"%>
       
        
        <div id="pagecontainer">
            <div class="page">
            <h4>New Order</h4>
			<span style="color: #ff0000;">${msg}</span>
	<form:form method="POST" action="${pageContext.request.contextPath}/order/add.do" commandName="orderModel" >
		<table>
			<tr>
				<td>User Id : </td>
				<td><input id="user_id" type="text" class="form-control" name="user_id" value="${orderModel['user_id']}"/></td>
				<td><form:errors path="user_id" style="color: #ff0000;"/></td>
			</tr>
			<tr>
				<td>Delivery Address : </td>
				<td><input id="address" type="text" class="form-control" name="address" value="${orderModel['address']}"/></td>
				<td><form:errors path="address" style="color: #ff0000;"/></td>
			</tr>
			<tr>
				<td>Delivery Mobile : </td>
				<td><input id="delivery_phone" type="text" class="form-control" name="delivery_phone" value="${orderModel['delivery_phone']}"/></td>
				<td><form:errors path="delivery_phone" style="color: #ff0000;"/></td>
			</tr>
			<tr>
				<td>Product No. : <br>(seperated by comma)</td>
				<td><input id="product_no" type="text" class="form-control" name="product_no" value="${orderModel['product_no']}"/></td>
				<td><form:errors path="product_no" style="color: #ff0000;"/></td>
			</tr>
			<%-- <tr>
				<td>Total Courier : </td>
				<td><input id="total_courier" type="text" class="form-control" name="total_courier"/></td>
				<td><form:errors path="total_courier" style="color: #ff0000;"/></td>
			</tr> --%>
			<tr>
				<td>Coupon Discount : </td>
				<td><input id="coupon_discount" type="text" class="form-control" name="coupon_discount" value="${orderModel['coupon_discount']}"/></td>
				<td><form:errors path="coupon_discount" style="color: #ff0000;"/></td>
			</tr>
			<tr>
				<td>Order Note : </td>
				<td><textarea id="order_note" class="form-control" name="order_note" rows="3" cols="80">${orderModel['order_note']}</textarea></td>
				<td><form:errors path="order_note" style="color: #ff0000;"/></td>
			</tr>
			<tr>
			<td>Payment Method: </td>
			<td>
			<select id="payment_method" name="payment_method" class="form-control">
					<option value="bank transfer" selected="selected">Bank Transfer</option>
					<option value="online payment">Online Payment</option>
					<option value="90% cash on delivery">90% Cash On Delivery</option>
			</select>
			</td>
			</tr>
			<tr>
			<td>Courier Method: </td>
			<td>
			<select id="courier_method" name="courier_method" class="form-control">
					<option value="fast" selected="selected">Fast</option>
					<option value="local">Local</option>
			</select>
			</td>
			</tr>
			<tr>
			<td>Order Status : </td>
			<td>
			<select id="order_status" name="order_status" class="form-control">
							
					<option value="open" selected="selected">Open</option>
					<option value="payment confirmation awaited">Payment confirmation awaited</option>
					<option value="processing">Processing</option>
					<option value="in transit">In Transit</option>
					<option value="partially in transit">Partially In Transit</option>
					<option value="completed">Completed</option>
					<option value="partially completed">Partially Completed</option>
					<option value="cancel">Cancel</option>
			</select>
			</td>
			</tr>
			<tr>
				<td>Customer Note: </td>
				<td><textarea id="customer_note" class="form-control" name="customer_note" rows="2" cols="80">${orderModel['customer_note']}</textarea></td>
				<td><form:errors path="customer_note" style="color: #ff0000;"/></td>
			</tr>
			<tr>
			<td></td>
				<td><input type="submit" id="submit" class="btn btn-lg btn-primary btn-block" name="submit" value="Add"></td>
			</tr>
			
		
            </table>
		
		
	</form:form>
            </div>
        </div>
    </div>
    <div id="footer">
   
    </div>
</div>
	${jsFile}

</body>
</html>