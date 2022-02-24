<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
        <title>Forbidden page.</title>
        <meta charset="UTF-8">
    </head>
    <body>
    <h3 style="color:red">
     <center>
            ${message}
     </center>
    </h3>
         <form action = "/tax-office/index.jsp"  align = "center">
         <input type="button" class="btn btn-secondary" onclick="history.back();" value="Back"/>
         </form>
    </body>
</html>