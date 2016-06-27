<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Orders</title>

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
            <h2>Orders</h2>
			<table id="ordersTable"  class="display table-container mainbarpage" cellspacing="0" width="100%">
                <thead>
                    <tr>
                    	<th>Sr.No</th>
                        <th>Order Id</th>
                        <th>User Id</th>
                        <th>User Name</th>
                        <th>Phone</th>
                        <th>Email</th>
                        <th>Products</th>
                        <th>Order Total</th>
                        <th>Order Date</th>
                        <th>Payment</th>
                        <th>Courier</th>
                        <th>Order Status</th>
                        <th width="115">Action</th>
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