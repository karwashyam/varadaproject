<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Sub Order Details</title>

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
        <div id="sidebar">
            <%@ include file="/WEB-INF/views/includes/side-bar.jsp"%>
        </div>
        <div id="pagecontainer">
            <div class="page">
            <h4>Order No. <b>${suborderModel['order_id']}</b></h4>
            <h4>Sub Order No. <b>${suborderModel['order_product_id']}</b></h4>
			<span style="color: #ff0000;">${msg}<br><br>${msg1}</span>
	<form:form method="POST" action="${pageContext.request.contextPath}/suborder/edit.do" commandName="suborderModel" >
		<table>
			<input type="hidden" id="order_product_id" name="order_product_id" value="${suborderModel['order_product_id']}" />
			<input type="hidden" id="order_id" name="order_id" value="${suborderModel['order_id']}" />
			<tr>
				<td>Product Id :</td>
				<td><input id="product_id" type="text" class="form-control" name="product_id" value="${suborderModel['product_id']}"/></td>
				<td><form:errors path="product_id" cssStyle="color: #ff0000;"/></td>
			</tr>
			<tr>
				<td>Product MRP : </td>
				<td><input id="price" type="text" class="form-control" name="price" value="${suborderModel['price']}"/></td>
				<td><form:errors path="price" cssStyle="color: #ff0000;"/></td>
			</tr>
			<tr>
				<td>Product Discounted Price : </td>
				<td><input id="discounted_price" type="text" class="form-control" name="discounted_price" value="${suborderModel['discounted_price']}"/></td>
				<td><form:errors path="discounted_price" cssStyle="color: #ff0000;"/></td>
			</tr>
			<tr>
				<td>Weight : </td>
				<td><input id="weight" type="text" class="form-control" name="weight" value="${suborderModel['weight']}"/></td>
				<td><form:errors path="weight" cssStyle="color: #ff0000;"/></td>
			</tr>
			<%-- <tr>
			<td>Order Status : </td>
			<td>
			<select id="order_status" name="order_status" class="form-control">
				<c:if test="${orderModel['order_status']=='open'}">				
					<option value="open" selected="selected">Open</option>
					<option value="processing">Processing</option>
					<option value="in transit">In Transit</option>
					<option value="partially in transit">Partially In Transit</option>
					<option value="completed">Completed</option>
					<option value="partially completed">Partially Completed</option>
				</c:if>
				<c:if test="${orderModel['order_status']=='processing'}">				
					<option value="open">Open</option>
					<option value="processing" selected="selected">Processing</option>
					<option value="in transit">In Transit</option>
					<option value="partially in transit">Partially In Transit</option>
					<option value="completed">Completed</option>
					<option value="partially completed">Partially Completed</option>
				</c:if>
				<c:if test="${orderModel['order_status']=='in transit'}">				
					<option value="open">Open</option>
					<option value="processing">Processing</option>
					<option value="in transit" selected="selected">In Transit</option>
					<option value="partially in transit">Partially In Transit</option>
					<option value="completed">Completed</option>
					<option value="partially completed">Partially Completed</option>
				</c:if>
				<c:if test="${orderModel['order_status']=='partially in transit'}">				
					<option value="open">Open</option>
					<option value="processing">Processing</option>
					<option value="in transit">In Transit</option>
					<option value="partially in transit" selected="selected">Partially In Transit</option>
					<option value="completed">Completed</option>
					<option value="partially completed">Partially Completed</option>
				</c:if>
				<c:if test="${orderModel['order_status']=='completed'}">				
					<option value="open" selected="selected">Open</option>
					<option value="processing">Processing</option>
					<option value="in transit">In Transit</option>
					<option value="partially in transit">Partially In Transit</option>
					<option value="completed" selected="selected">Completed</option>
					<option value="partially completed">Partially Completed</option>
				</c:if>
				<c:if test="${orderModel['order_status']=='partially completed'}">				
					<option value="open">Open</option>
					<option value="processing">Processing</option>
					<option value="in transit">In Transit</option>
					<option value="partially in transit">Partially In Transit</option>
					<option value="completed">Completed</option>
					<option value="partially completed" selected="selected">Partially Completed</option>
				</c:if>
				
			</select>
			</td>
			</tr>
			 --%>
			<tr>
			<td></td>
				<td><input type="submit"  class="btn btn-lg btn-primary btn-block" name="submit" value="Update"></td>
			</tr>
			
		
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