<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>All Clients</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: black">
			<div>
				<a> <form accept-charset="UTF-8" method="POST" action="/tax-office/service/logout">
                <button type="submit" class="btn btn-primary">Logout</button>
                </form></a>
			</div>
		</nav>
	</header>
	<br>

		<div class="container">
			<h3 class="text-center">List of Clients</h3>
			<hr>
			<div class="container text-left">


			</div>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
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
							<td><c:out value="${client.login}" /></td>
							<td><c:out value="${client.name}" /></td>
							<td><c:out value="${client.surname}" /></td>
							<td><c:out value="${client.itn}" /></td>
							<td><a><form action="/tax-office/service/deleteClientById" method="POST">
                                   <input type="hidden" name="clientId" value="${client.id}"/>
                                   <button type="submit" class="btn-outline-dark">Delete</button>
							    <a href="getAllReportsByClientId?clientId=<c:out value='${client.id}'/>&clientLogin=<c:out value='${client.login}'/>">Reports</a></td>
						</tr>
					</c:forEach>

				</tbody>

			</table>
		</div>
	</div>
</body>
</html>