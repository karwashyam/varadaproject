
<meta name="viewport" content="width=device-width; initial-scale=1.0;" />
<%@page import="java.util.HashMap"%>
<%@ page session="false" %>

	<!--<meta charset="utf-8">-->
     <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
     
     <meta name="description" content="">
     <meta name="keywords" content="">
     
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
     <link rel="icon" href="${pageContext.request.contextPath}/resources/assets/img/internal/fevicon.ico">
     
     <!-- Bootstrap -->
     <link href='http://fonts.googleapis.com/css?family=Open+Sans:300,700,400,600' rel='stylesheet' type='text/css'>
     <link href='http://fonts.googleapis.com/css?family=Roboto:300,400,500,700' rel='stylesheet' type='text/css'>
     <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

	
     ${cssFile}
     <script type="text/javascript">
			var basePath = '${pageContext.request.contextPath}';	
</script>
     <!-- 
     maxcdn for html5shiv and respond
         <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
         <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
     -->
     <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
     <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
     <!--[if lt IE 9]>
       <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
       <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
     <![endif]-->

     <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
     
