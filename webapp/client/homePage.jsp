<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home page</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
    </head>
         <body align="center">
             <p>
                 <h1>Home page for client</h1>
             <p>
             <jsp:text>
               Welcome: ${user.name}
             </jsp:text>
             <p>
                <form action = "/tax-office/service/getAllReportsByClientId"  method="GET">
                <input type="hidden" name="clientId" value="${user.id}"/><br><br>
                <button type="submit" class="btn btn-primary">My Reports</button>
                </form>
             <p>

             <form accept-charset="UTF-8" method="POST" action="/tax-office/service/logout">
             <button type="submit" class="btn btn-primary">Logout</button>
             </form>
    </body>
</html>