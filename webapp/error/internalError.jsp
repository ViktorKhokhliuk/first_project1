<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lan" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
        <title>Error page.</title>
        <meta charset="UTF-8">
    </head>
    <body>
    <h3 style="color:red">
     <center>
            <lan:print message="error"/>: ${message}
     </center>
    </h3>
         <form action = "/tax-office/index.jsp"  align = "center">
         <input type="button" class="btn btn-secondary" onclick="history.back();" value=<lan:print message="back"/>>
         </form>
    </body>
</html>