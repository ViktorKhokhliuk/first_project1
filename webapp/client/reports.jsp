<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Reports</title>
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
                <input type="submit" value='Logout'>
                </form></a>
			</div>
		</nav>
	</header>
	<br>

		<div class="container">
			<h3 class="text-center">List of Reports</h3>
			<hr>
			<div class="container text-left">
			</div>
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Name</th>
						<th>Status</th>
						<th>Date</th>
						<th>Type</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${reports}" var="report">

						<tr>
							<td><c:out value="${report.name}" /></td>
							<td><c:out value="${report.status}" /></td>
							<td><c:out value="${report.date}" /></td>
							<td><c:out value="${report.type}" /></td>
							<td><a href="delete?id=<c:out value='${report.id}' />">Update</a></td>
						</tr>
					</c:forEach>

				</tbody>

			</table>
		</div>
	</div>

	<form action = "/tax-office/service/filterReports"  method="GET">
                         <label for="name">Choose a date:</label><br>
                         <input type="date" name="date"
                          min="01-01-2010" max="12-31-2100"/><br><br>
                          <label for="name">Choose a status:</label><br>
                          <p><select  multiple name="status">
                          <option value="SUBMITTED">SUBMITTED</option>
                          <option value="ACCEPTED">ACCEPTED</option>
                          <option value="UNACCEPTED">UNACCEPTED</option>
                          </select></p>
                          <label for="name">Choose a type:</label><br>
                          <p><select size="3" multiple name="type">
                          <option value="income statement">income statement</option>
                          <option value="income tax">income tax</option>
                          <option value="single tax">single tax</option>
                          </select></p>
                         <input type="hidden" name="client_id" value="${user.id}"/>
                         <input type = "submit" value = "filter" />
    </form>

    <form action = "/tax-office/service/getAllReportsByClientId"  method="GET">
                      <input type="hidden" name="clientId" value="${user.id}"/>
                      <input type = "submit" value = "all your reports" />
    </form>
</body>
</html>
