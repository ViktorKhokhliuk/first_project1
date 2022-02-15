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
	.logout {
      margin-right:10px;
    }
    .clients {
        margin-left:185px;
    }
    table {
       counter-reset: tableCount;
    }
      .counterCell:before {
         content: counter(tableCount);
         counter-increment: tableCount;
    }
    </style>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: black">
				<form accept-charset="UTF-8" method="POST" action="/tax-office/service/logout" class = "logout">
                <button type="submit" class="btn btn-primary">Logout</button>
                </form>
                <form action="/tax-office/service/toHome" method="GET">
                <button type="submit" class="btn btn-primary">Home</button>
                </form>
		</nav>
	</header>
 <p>
 <form action = "/tax-office/service/searchClients"  method="GET" class="form-horizontal" align="center">
       <h3>Search</h3>
       <div class="form-group">
          <label for="name">Enter name:</label>
          <input type="text" name="name" placeholder="Name"/>
          <label for="name">Enter surname:</label>
          <input type="text" name="surname" placeholder="Surname"/>
          <label for="name">Enter ITN:</label>
          <input type="text" name="itn"= placeholder="ITN"/>
          <button type="submit" class="btn btn-outline-dark">Search</button>
       </div>
       </form>
  <p>
         <form action = "/tax-office/service/getAllClients"  method="GET" class = "clients">
             <button type="submit" class="btn btn-primary">All clients</button>
         </form>
		<div class="container">
			<h3 class="text-center">List of Clients</h3>
			<hr>
			<div class="container text-left">
			</div>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
					    <th>№</th>
						<th>Login</th>
						<th>Name</th>
						<th>Surname</th>
						<th>ITN</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${clients}" var="client">
						<tr>
						    <td class="counterCell"></td>
							<td><c:out value="${client.login}" /></td>
							<td><c:out value="${client.name}" /></td>
							<td><c:out value="${client.surname}" /></td>
							<td><c:out value="${client.itn}" /></td>
							<td>
                               <form action = "/tax-office/service/getAllReportsByClientId" method = "GET">
							     <input type="hidden" name="clientId" value="${client.id}"/>
							     <input type="hidden" name="clientLogin" value="<c:out value='${client.login}'/>"/>
							     <button type="submit" class="btn btn-outline-info">Reports</button>
							   </form>
							   <form action="/tax-office/service/deleteClientById" method="POST" onSubmit='return confirm("Are you sure?");'>
                                 <input type="hidden" name="clientId" value="${client.id}"/>
                                 <button type="submit" class="btn btn-outline-danger">Delete</button>
                               </form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
</body>
</html>