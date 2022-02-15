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
   <body>
	<style>
    form{
      display:inline-block;
    }
    .box{
    margin-left:500px;
    }
    .clients {
    margin-right:260px;
    }
    </style>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: black">
				<form accept-charset="UTF-8" method="POST" action="/tax-office/service/logout">
                <button type="submit" class="btn btn-primary">Logout</button>
                </form>
		</nav>
	</header>
 <p>
    <h1 align = "center">
      <jsp:text>
          Welcome, ${user.login}
      </jsp:text>
    </h1>
    <hr>
    </hr>
 <p>
 <div class = "box">
     <form action = "/tax-office/service/getAllClients"  method="GET"  class = "clients">
        <button type="submit" class="btn btn-primary btn-lg">All clients</button>
     </form>
     <form action = "/tax-office/service/getAllReports"  method="GET" >
        <button type="submit" class="btn btn-primary btn-lg">All reports</button>
     </form>
 </div>
    </body>
</html>