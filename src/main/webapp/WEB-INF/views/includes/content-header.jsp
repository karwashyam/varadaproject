        <nav class="navbar navbar-default menu" style="">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav toggleicon">
                        <li class="active brdr-rt"> <a href="javascript:void(0)" class="" id="menu-toggle"></a></li>
                        <li class="settings brdr-rt"><a href="#"></a></li>
                        <li class="qstn-mark brdr-rt"><a href="mailto:support@beerengineapp.com "></a></li>
                       <%--  <li class="qstn-mark brdr-rt"><a href="${pageContext.request.contextPath}/logout.do"></a></li> --%>
                    </ul>
                    
                    <div id="head-up" class="col-xs-10">
                    	<div class="col-xs-3">
                    		<!-- <li><span class="lft-arw">&nbsp;</span>Add Bar</li> -->
                    	</div>
                    	
                    	<div class="col-xs-9">
	                        <ul class="nav navbar-nav navbar-right">
	                            <li>${requestScope.userFullName}, you are logged in to your account</li>
	                        </ul>
	                    </div>
                    
                    </div>
                    
<%--                     
                    <ul class="nav navbar-nav navbar-right">
                        <li>${requestScope.userFullName}, you are logged in to your account</li>
                    </ul>
 --%> 
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
        <div id="wrapper">
            <!-- Sidebar -->
            <div id="sidebar-wrapper">
                <ul class="sidebar-nav">
                    	<a href="#"> 
                    		<div id="logo-top">
                       			<img src="${pageContext.request.contextPath}/resources/assets/img/internal/beer-engine-logo.png"/>
                    		</div>
                    	</a>
                    ${requestScope.parentMenuHtml}
                   <%--  <li><a href="#" class="bottom-logo"> 
                    	<div>
                                <img src="${pageContext.request.contextPath}/resources/assets/img/internal/beer-engine-logo.png"/>
                                <p class="text-center ">Build 021.25.07<br/>
                                    Date 08/19/2015</p>
                        </div></a>
                     </li>  --%>
                </ul>
                <div class="clear"></div>
            </div>
            <!-- /#sidebar-wrapper -->

			${requestScope.childMenuHtml}
			
            <!-- Page Content -->
            <div id="page-content-wrapper" >
            <!-- <div id="page-content-wrapper"> -->