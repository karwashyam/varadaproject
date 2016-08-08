<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<%@ page session="false"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
	var basePath = '${pageContext.request.contextPath}';
</script> 

<%@ include file="/WEB-INF/views/includes/common-head.jsp"%>

</head>
<body>

	<%@ include file="/WEB-INF/views/includes/content-header.jsp"%>
	<!-- Login Container -->
	${jsFile}
	
	
		<!-- Page content -->
      <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>States <small></small></h3>
              </div>

              <div class="title_right">
                <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                  <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search for...">
                    <span class="input-group-btn">
                      <button class="btn btn-default" type="button">Go!</button>
                    </span>
                  </div>
                </div>
              </div>
            </div>

            <div class="clearfix"></div>
            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>States</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>

                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <p class="text-muted font-13 m-b-30">
                     
                    </p>

		<!-- START Content -->
		<div style="border: 1px solid; height: 100%;" class="block" id="page-content">
		
		<input type="hidden" name="isAddAccess" id="isAddAccess" value="${isAddAccess}" />
		<input type="hidden" name="isEditAccess" id="isEditAccess" value="${isEditAccess}" />
		<input type="hidden" name="isDeleteAccess" id="isDeleteAccess" value="${isDeleteAccess}" />
		
		
			            	<div style="float:left; width: 100%;">
			            	<%-- <form method="POST" name="addlessfrm" id="addlessfrm" action="${pageContext.request.contextPath}/secure/lesson.do"> --%>
			            
			            	<!-- </form> -->
			            	</div>
		
		
		
<%-- <div style="float:right; margin-bottom: 10px; margin-right: 60px;"><a href="${pageContext.request.contextPath}/secure/add-lesson.do" class="btn btn-primary" >Add Lesson</a></div> --%>
						<div style="float:left; width: 100%;" class="table-responsive">
								<table id="stateManageTable" class="table table-bordered table-hover"  style="width: 100%;" >
									<thead>
										<tr class="manage-users-list-header" style="width: 1058px;" >
											
											<th class="text-center">Sr. No</th>
											<th class="text-center">State Name</th>
											<th class="text-center">Action</th>
										
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
								 </div>
                </div>
              </div>
            </div>
           </div>
        </div>
	
							</div>
</div>


		<!-- END Content -->

		<!-- END Page Content -->

</body>
</html>