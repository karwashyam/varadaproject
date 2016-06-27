<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<title>Edit Product Details</title>

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
	margin-top: 79PX;
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
				<form:form method="POST" action="${pageContext.request.contextPath}/products/edit.do" commandName="productModel" >
					<table>
						<tr>
							<td>Product Id : </td>
							<td>
								${productModel.productId}
								<input id="productId" type="hidden" class="form-control" name="productId" value="${productModel.productId}"/>
							</td>
						</tr>
						<tr>
							<td>Product Type : </td>
							<td>
								<select class="form-control" id="productTypeId" name="productTypeId">
									<c:forEach items="${productTypes}" var="productType">
										<c:choose>
											<c:when test="${productType.productTypeId == productModel.productTypeId}">
												<option value="${productType.productTypeId}" selected="selected">${productType.productTypeName}</option>
											</c:when>
											<c:otherwise>
												<option value="${productType.productTypeId}">${productType.productTypeName}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>Product Price : </td>
							<td>
								<input id="productPrice" type="text" class="form-control" name="productPrice" value="${productModel.productPrice}"/>
							</td>
						</tr>
						<tr>
							<td>Quantity : </td>
							<td>
								<input id="quantity" type="text" class="form-control" name="quantity" value="${productModel.quantity}"/>
							</td>
						</tr>
						<tr>
							<td>Weight : </td>
							<td>
								<input id="weight" type="text" class="form-control" name="weight" value="${productModel.weight}"/>
							</td>
						</tr>
						<tr>
							<td>Caption : </td>
							<td>
								<input id="caption" type="text" class="form-control" name="caption" value="${productModel.caption}"/>
							</td>
						</tr>
						<tr>
							<td>Tags : </td>
							<td>
								<c:set var="tagsCount" value="${fn:length(productModel.tagNames)}" />
								<textarea rows="4" cols="35" id="tagNames" name="tagNames"><c:forEach items="${productModel.tagNames}" var="tag" varStatus="i"><c:if test="${i.count == tagsCount}">${tag}</c:if><c:if test="${i.count != tagsCount}">${tag},</c:if></c:forEach></textarea>
							</td>
						</tr>
						<tr> 
							<td></td>
							<td><input type="submit" class="btn btn-lg btn-primary btn-block" name="submit" value="Update"></td>
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