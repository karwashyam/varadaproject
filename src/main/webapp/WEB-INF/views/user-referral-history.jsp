<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Users</title>

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
       
	margin-top: 79PX;
    margin-left: 15px;
    border-left:-8em;
    border-right:-8em;
}
li{
	padding-top:15px;
}
tr {
 
	height: 40px;
}
td{
	padding-right:5px;
	border: 1px solid; 
}
th{
	border: 1px solid;
}
.dataTables_paginate {
   float: right;
   padding: 0px 75px;
}
a, a:hover, a:focus, .text-primary, a.list-group-item.active > .badge, .nav-pills > .active > a > .badge, .btn-link, .btn-link:hover, .btn-link:focus, .btn-link.btn-icon:hover, .btn-link.btn-icon:focus, .themed-color {
   color: #4da2db;
   padding-right: 10px;
   cursor: pointer;
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
            <!-- <h2>User Referral History</h2> -->
            <div style="float: left;">
				<a href="${pageContext.request.contextPath}/users/edit.do?user_id=${userId}"><h4>User Details</h4></a>
			</div>
			<div style="float: left; margin-left: 14px;">
				<a href="${pageContext.request.contextPath}/users/edit/referral-history.do?userId=${userId}"><h4>Referral History</h4></a>
			</div>
			<div style="float: left; margin-left: 14px;">
				<a href="${pageContext.request.contextPath}/users/edit/order.do?userId=${userId}"><h4>Order History</h4></a>
			</div>
            
			<span style="color: #ff0000;">${msg}<br><br>${msg1}</span>
			<br>
            
			<table id="usersReferralHistoryTable" class="display table-container mainbarpage" cellspacing="0" width="100%">
                <thead>
                    <tr>
                    	<!-- <th>Sr. No.</th> -->
                        <th>User Id</th>
                        <th>User Name</th>
                        <th>Amount</th>
                        <th>History Note</th>
                        <!-- <th>Order Id</th>
                        <th>User Used</th> -->
                        <th>Used Date</th>
                        <!-- <th width="115">Action</th> -->
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
			
			<input type="hidden" id="userId" value="${userId}" />		
	
    <div id="footer">
   
    </div>
</div>
	${jsFile}

</body>
</html>