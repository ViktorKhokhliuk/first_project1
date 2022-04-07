<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lan" %>
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
    .logout {
      margin-right:10px;
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
				<form accept-charset="UTF-8" method="POST" action="/tax-office/service/logout" class = "logout">
                <button type="submit" class="btn btn-primary"><lan:print message="logout"/></button>
                </form>
                <form action="/tax-office/service/changeLocale" method="POST" class = "locale">
                   <input type="hidden" name="view" value="/inspector/homePage.jsp"/>
                      <select name="selectedLocale">
                         <c:forEach var="locale" items="${sessionScope.locales}">
                            <option value="${locale}">
                               ${locale}
                            </option>
                         </c:forEach>
                      </select>
                   <button type="submit" class="btn btn-primary btn-sm"><lan:print message="update"/></button>
                </form>
		</nav>
	</header>
 <br>
    <h1 align = "center">
          <lan:print message="welcome"/>, ${user.login}
    </h1>
    <hr>
    </hr>
 <p>
 <div class = "box">
     <form action = "/tax-office/service/allClients"  method="GET"  class = "clients">
        <input type="hidden" name="page" value="1">
        <button type="submit" class="btn btn-primary btn-lg"><lan:print message="all_clients"/></button>
     </form>
     <form action = "/tax-office/service/allReports"  method="GET" >
        <input type="hidden" name="page" value="1">
        <button type="submit" class="btn btn-primary btn-lg"><lan:print message="all_reports"/></button>
     </form>
 </div>
    </body>
</html>