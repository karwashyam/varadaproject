<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>User Details</title>

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
            
            <!-- <h4>User Details</h4> -->
            <div style="float: left;">
				<a href="${pageContext.request.contextPath}/users/edit.do?user_id=${userModel['user_id']}"><h4>User Details</h4></a>
			</div>
			<div style="float: left; margin-left: 14px;">
				<a href="${pageContext.request.contextPath}/users/edit/referral-history.do?userId=${userModel['user_id']}"><h4>Referral History</h4></a>
			</div>
			<div style="float: left; margin-left: 14px;">
				<a href="${pageContext.request.contextPath}/users/edit/order.do?userId=${userModel['user_id']}"><h4>Order History</h4></a>
			</div>
            
            
			<span style="color: #ff0000;">${msg}<br><br>${msg1}</span>
			<br>
		<form:form method="POST" action="${pageContext.request.contextPath}/users/edit.do" commandName="userModel" >
		<table>
			<input type="hidden" id="user_id" name="user_id" value="${userModel['user_id']}" />
			<tr>
				<td>User Id : </td>
				<td>${userModel['user_id']}</td>
			</tr>
			<tr>
				<td>User Name : </td>
				<td>${userModel['user_name']}</td>
			</tr>
			<tr>
				<td>Joining Date : </td>
				<td>${userModel['created_date']}</td>
			</tr>
			<tr>
				<td>Referral Balance : </td>
				<td>${userModel['referralBalance']}</td>
			</tr>
			<tr>
				<td>User Full Name : </td>
				<td><input id="user_full_name" type="text" class="form-control" name="user_full_name" value="${userModel['user_full_name']}"/></td>
				<td><form:errors path="user_full_name" cssStyle="color: #ff0000;"/></td>
			</tr>
			<tr>
				<td>Phone :</td>
				<td><input id="phone" type="text" class="form-control" name="phone" value="${userModel['phone']}"/></td>
				<td><form:errors path="phone" cssStyle="color: #ff0000;"/></td>
			</tr>
			<tr>
				<td>Email : </td>
				<td><input id="email" type="text" class="form-control" name="email" value="${userModel['email']}"/></td>
				<td><form:errors path="email" cssStyle="color: #ff0000;"/></td>
			</tr>
			<tr>
				<td>City : </td>
				<td><input id="user_city" type="text" class="form-control" name="user_city" value="${userModel['user_city']}"/></td>
				<td><form:errors path="user_city" cssStyle="color: #ff0000;"/></td>
			</tr>
			<tr>
				<td>Saree : </td>
				<td>
				<select id="saree" name="saree" class="form-control">
				<c:if test="${userModel['saree']!=false}">				
					<option value=true selected="selected">True</option>
					<option value=false>False</option>
				</c:if>
				<c:if test="${userModel['saree']==false}">				
					<option value=true>True</option>
					<option value=false selected="selected">False</option>
				</c:if>
				</select>
				</td>
			</tr>
			<tr>
				<td>Kurti : </td>
				<td>
				<select id="kurti" name="kurti" class="form-control">
				<c:if test="${userModel['kurti']!=false}">				
					<option value=true selected="selected">True</option>
					<option value=false>False</option>
				</c:if>
				<c:if test="${userModel['kurti']==false}">				
					<option value=true>True</option>
					<option value=false selected="selected">False</option>
				</c:if>
				</select>
				</td>
			</tr>
			<tr>
				<td>Gown : </td>
				<td>
				<select id="gown" name="gown" class="form-control">
				<c:if test="${userModel['gown']!=false}">				
					<option value=true selected="selected">True</option>
					<option value=false>False</option>
				</c:if>
				<c:if test="${userModel['gown']==false}">				
					<option value=true>True</option>
					<option value=false selected="selected">False</option>
				</c:if>
				</select>
				</td>
			</tr>
			<tr>
				<td>Lehenga : </td>
				<td>
				<select id="lehenga" name="lehenga" class="form-control">
				<c:if test="${userModel['lehenga']!=false}">				
					<option value=true selected="selected">True</option>
					<option value=false>False</option>
				</c:if>
				<c:if test="${userModel['lehenga']==false}">				
					<option value=true>True</option>
					<option value=false selected="selected">False</option>
				</c:if>
				</select>
				</td>
			</tr>
			<tr>
				<td>Dress Material : </td>
				<td>
				<select id="dress_material" name="dress_material" class="form-control">
				<c:if test="${userModel['dress_material']!=false}">				
					<option value=true selected="selected">True</option>
					<option value=false>False</option>
				</c:if>
				<c:if test="${userModel['dress_material']==false}">				
					<option value=true>True</option>
					<option value=false selected="selected">False</option>
				</c:if>
				</select>
				</td>
			</tr>
			
			<tr>
			<td>Update Frequency : </td>
			<td>
			<select id="update_frequency" name="update_frequency" class="form-control">
				<c:if test="${userModel['update_frequency']!='on demand'}">				
					<option value="daily" selected="selected">Daily</option>
					<option value="on demand">On Demand</option>
				</c:if>
				<c:if test="${userModel['update_frequency']=='on demand'}">				
					<option value="daily">Daily</option>
					<option value="on demand" selected="selected">On Demand</option>
				</c:if>
				
			</select>
			</td>
			</tr>
			<tr>
			<td>Catalogue Delivery : </td>
			<td>
			<select id="catalogue_delivery" name="catalogue_delivery" class="form-control">
				<c:if test="${userModel['catalogue_delivery']=='app'}">				
					<option value="app" selected="selected">App - Telegram</option>
					<option value="email">Email</option>
					<option value="both">Both</option>
				</c:if>
				<c:if test="${userModel['catalogue_delivery']=='email'}">				
					<option value="app">App - Telegram</option>
					<option value="email" selected="selected">Email</option>
					<option value="both">Both</option>
				</c:if>
				<c:if test="${userModel['catalogue_delivery']=='both'}">				
					<option value="app">App - Telegram</option>
					<option value="email">Email</option>
					<option value="both" selected="selected">Both</option>
				</c:if>
				
			</select>
			</td>
			</tr>
			
			<tr>
			<td></td>
				<td><input type="submit"  class="btn btn-lg btn-primary btn-block" name="submit" value="Update"></td>
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