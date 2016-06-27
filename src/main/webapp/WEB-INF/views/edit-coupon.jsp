<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Edit Coupon Details</title>

<%@include file="/WEB-INF/views/includes/common-head.jsp"%>
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
          
   margin-top: 79px;
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
			<span style="color: #ff0000;">${msg}<br><br>${msg1}</span>
				<form:form method="POST" action="${pageContext.request.contextPath}/coupons/edit.do" commandName="couponModel" >
					<input type="hidden" id="couponId" name="couponId" value="${couponModel.couponId}">
					<table>
						<%-- <tr>
							<td>Coupon Id : </td>
							<td>
								${couponModel.couponId}
								<input type="hidden" id="couponId" name="couponId" value="${couponModel.couponId}">
							</td>
						</tr> --%>
						<tr>
							<td>Coupon Code : </td>
							<td>
								<input id="couponCode" name="couponCode" type="text" class="form-control" value="${couponModel.couponCode}"/>
							</td>
						</tr>
						<tr>
							<td>Start Date : </td>
							<td>
								<input id="startDateDisplay" name="startDateDisplay" type="text" class="form-control" value="${couponModel.startDateDisplay}"/>
							</td>
						</tr>
						<tr>
							<td>End Date : </td>
							<td>
								<input id="endDateDisplay" name="endDateDisplay" type="text" class="form-control" value="${couponModel.endDateDisplay}"/>
							</td>
						</tr>
						<tr>
							<td>Discount ( In % ) : </td>
							<td>
								<input id="discountInPercentage" name="discountInPercentage" type="text" class="form-control" value="${couponModel.discountInPercentage}"/>
							</td>
						</tr>
						<tr>
							<td>Max times per user : </td>
							<td>
								<input id="maximumTimeUsed" name="maximumTimeUsed" type="text" class="form-control" value="${couponModel.maximumTimeUsed}"/>
							</td>
						</tr>
						<tr>
							<td>Max Discount : </td>
							<td>
								<input id="maxDisocunt" name="maxDisocunt" type="text" class="form-control" value="${couponModel.maxDisocunt}"/>
							</td>
						</tr>
						<tr>
							<td>Discount on Minimum Payable Price : </td>
							<td>
								<input id="discountOnMinimumPayablePrice" name="discountOnMinimumPayablePrice" type="text" class="form-control" value="${couponModel.discountOnMinimumPayablePrice}"/>
							</td>
						</tr>
						<tr>
							<td>Can Used With Referral : </td>
							<td>
								<c:choose>
									<c:when test="${couponModel.canUsedWithReferral}">
										<input id="canUsedWithReferral" name="canUsedWithReferral" type="checkbox" checked="checked" class="form-control" value="true"/>
									</c:when>
									<c:otherwise>
										<input id="canUsedWithReferral" name="canUsedWithReferral" type="checkbox" class="form-control" value="true"/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr> 
							<td></td>
							<td><input type="submit" class="btn btn-lg btn-primary btn-block" name="submit" value="Save"></td>
						</tr>
						
					</table>
				</form:form>
            </div>
        </div>
    </div>
</div>

<div id="footer">
	${jsFile}
</div>

</body>
</html>