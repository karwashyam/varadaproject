<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Add Wallet Balance</title>

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
            <div style="float: left;">
				<a href="${pageContext.request.contextPath}/users/edit.do?user_id=${userReferralHistoryModel.userId}"><h4>User Details</h4></a>
			</div>
			<div style="float: left; margin-left: 14px;">
				<a href="${pageContext.request.contextPath}/users/edit/referral-history.do?userId=${userReferralHistoryModel.userId}"><h4>Referral History</h4></a>
			</div>
			<div style="float: left; margin-left: 14px;">
				<a href="${pageContext.request.contextPath}/order.do?userId=${userReferralHistoryModel.userId}"><h4>Order History</h4></a>
			</div>
            
			<span style="color: #ff0000;">${msg}<br><br>${msg1}</span>
			<br>
			
			
			<span style="color: #ff0000;">${msg}<br><br>${msg1}</span>
				<form:form method="POST" action="${pageContext.request.contextPath}/users/edit/referral-history/add.do" commandName="userReferralHistoryModel" >
					<input type="hidden" id="userId" name="userId" value="${userReferralHistoryModel.userId}">
					<table>
					<tr>
					<td>User Id :</td><td> ${userReferralHistoryModel.userId}</td>
					</tr>
						<tr>
							<td>Amount : </td>
							<td>
								<input id="amount" name="amount" type="text" class="form-control" value=""/>
							</td>
							<td>positive for addition(Ex: 100)<br>negative for deduction(Ex: -100)</td>
						</tr>
						<tr>
							<td>Wallet Note : </td>
							<td>
								<textarea id="historyNote" name="historyNote" class="form-control"></textarea>
							</td>
							<td>this note will be sent to customers</td>
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