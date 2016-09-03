<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Varada Home</title>
<!-- <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css" /> -->
<!-- <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" /> -->


<script type="text/javascript">
	var basePath = '${pageContext.request.contextPath}';
</script>

<%@ include file="/WEB-INF/views/includes/common-head.jsp"%>

</head>
<%@ include file="/WEB-INF/views/includes/content-header.jsp"%>

<!-- page content -->
<div class="right_col" role="main">
	<div class="">
		<div class="clearfix"></div>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>Edit Project Payment Scheme</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br />
					<form:form name="editprojPaymentSchFrm" id="editprojPaymentSchFrm"
						class="form-horizontal form-label-left" method="POST"
						action="${pageContext.request.contextPath}/proj-payment-scheme/edit"
						commandName="projectPaymentSchemeDto">

						<input type="hidden" value="${projId}" id="projId"
							name="projId" />
						<input type="hidden" value="${paymentSchId}"
							id="paymentSchId" name="paymentSchId" />
							<input type="hidden" value="${projPaymentSchemeId}" id="projectPaymentSchemeId"
							name="projectPaymentSchemeId" />
							
					
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Select
								Project Name</label>
							<div class="col-md-3 col-sm-3 col-xs-12">
								<%--                           <form:input path="projectId" class="form-control" placeholder="Select Project Heading" /> --%>
						
						
								<form:select path="projectId" name="projectId" id="projectId"
									class="form-control" data-live-search="true" title="Search">
									<option value="NONE">Select Project Title</option>
									<c:forEach items="${projectsList}" var="projType">

										<option value=<c:out value="${projType.projectId}"/>>
											<c:out value="${projType.title}" />
										</option>
									</c:forEach>
								</form:select>
								<form:errors path="projectId" class="errorMessage" />
								<%--                           <form:errors path="projectExists" class="errorMessage" /> --%>
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12">Select
								Payment Scheme</label>
														<div class="col-md-3 col-sm-3 col-xs-12">
							
							<form:select path="paymentSchemeId" name="paymentSchemeId"
								id="paymentSchemeId" class="form-control"
								data-live-search="true" title="Search">
								<option value="NONE">Select Payment Scheme</option>
								<c:forEach items="${paymentSchemeList}" var="payschemeType">

									<option value=<c:out value="${payschemeType.paymentSchemeId}"/>>
										<c:out value="${payschemeType.title}" />
									</option>
								</c:forEach>
							</form:select>
							</div>
							<form:errors path="paymentSchemeId" class="errorMessage" />

						</div>


						<div class="ln_solid"></div>
						<div class="form-group">
							<div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
								<button type="submit" class="btn btn-success">Update</button>
							</div>
						</div>

					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>




<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</html>