<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Send Sms</title>

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
td, th {
    padding: 6px;
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
            <h2>Send Sms</h2>
            <p>Enter "all" in Mobile No. for sending sms to every user 

	<form:form method="POST" action="${pageContext.request.contextPath}/send-sms.do" commandName="sendMessageDto" >
		<table>
			<tr>
				<td>Mobile No. </td>
				<td><input id="phone" type="text" class="form-control" name="phone" /></td>
				<td><form:errors path="phone" cssStyle="color: #ff0000;"/></td>
			</tr>
			<tr>
				<td>Message</td>
				<td><textarea id="message" class="form-control" name="message" rows="6" cols="100"></textarea></td>
				<td><form:errors path="message" cssStyle="color: #ff0000;"/></td>
			</tr>
			
			<tr>
			</tr>
			
			<tr>
			<td></td>
				<td><input type="submit"  class="btn btn-lg btn-primary btn-block" name="submit" value="Submit"></td>
			</tr>
			
		</table>
		<span style="color: #ff0000;">${msg}<br><br>${msg1}</span>
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