<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Coupons</title>

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
        <div id="sidebar">
            <%@ include file="/WEB-INF/views/includes/side-bar.jsp"%>
        </div>
        <div id="pagecontainer">
            <div class="page">
            <h2>Coupons</h2>
			<table id="couponsTable" class="display table-container mainbarpage" cellspacing="0" width="100%">
                <thead>
                    <tr>
                        <th>Coupon Id</th>
                        <th>Sr. No.</th>
                        <th>Coupon Code</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Discount In Percentage</th>
                        <th>Max times per user</th>
                        <th>Max Disocunt</th>
                        <th>Can Used With Referral</th>
                        <th>Disount on min payable price</th>
                        <th width="115">Action</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
			</div>
		</div>
	</div>
</div>

<div id="footer">
  ${jsFile}
</div>

</body>
</html>