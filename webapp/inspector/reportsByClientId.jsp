<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
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
                    <button type="submit" class="btn btn-primary">Logout</button>
                    </form></a>
			</div>
		</nav>
	</header>
	<br>
	<form action = "/tax-office/service/filterReports"  method="GET">
                             <label for="name">Choose a date:</label><br>
                             <input type="date" name="date"
                              min="01-01-2010" max="12-31-2100"/><br><br>
                              <label for="name">Choose a status:</label><br>
                              <p><select name="status">
                              <option value="">Choose status</option>
                              <option value="SUBMITTED">SUBMITTED</option>
                              <option value="ACCEPTED">ACCEPTED</option>
                              <option value="UNACCEPTED">UNACCEPTED</option>
                              </select></p>
                              <label for="name">Choose a type:</label><br>
                              <p><select name="type">
                              <option value="">Choose type</option>
                              <option value="income statement">income statement</option>
                              <option value="income tax">income tax</option>
                              <option value="single tax">single tax</option>
                              </select></p>
                             <input type="hidden" name="client_id" value="${clientId}"/>
                             <input type="hidden" name="clientLogin" value="${clientLogin}"/>
                             <button type="submit" class="btn-outline-dark">Filter</button>
    </form>

        <form action = "/tax-office/service/getAllReportsByClientId"  method="GET">
                          <input type="hidden" name="clientId" value="${clientId}"/>
                          <input type="hidden" name="clientLogin" value="${clientLogin}"/>
                          <button type="submit" class="btn btn-primary">All reports</button>
        </form>
		<div class="container">
			<h3 class="text-center">List of Reports by : '${clientLogin}'</h3>
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
							<td><a href="showReport?clientId=${clientId}&name=<c:out value='${report.name}'/>"target="_blank">${report.name}</a></td>
							<td><c:out value="${report.status}" /></td>
							<td><c:out value="${report.date}" /></td>
							<td><c:out value="${report.type}" /></td>
							<td><a> <form action="/tax-office/service/deleteReportById" method="POST">
							        <input type="hidden" name="id" value="${report.id}"/>
							        <input type="hidden" name="clientId" value="${clientId}"/>
							        <input type="hidden" name="clientLogin" value="${clientLogin}"/>
							        <<button type="submit" class="btn-outline-dark">Delete</button>
							    <a> <form action="/tax-office/service/updateStatusOfReport" method="POST">
							    <p><select name="status">
							    <option value="">Choose status</option>
                                <option value="ACCEPTED">ACCEPTED</option>
                                <option value="UNACCEPTED">UNACCEPTED</option>
                                </select></p>
                                    <input type="hidden" name="id" value="${report.id}"/>
                                    <input type="hidden" name="clientId" value="${clientId}"/>
                                    <input type="hidden" name="clientLogin" value="${clientLogin}"/>
                                    <button type="submit" class="btn-outline-dark">Update</button>
							</td>
						</tr>
					</c:forEach>

				</tbody>

			</table>
		</div>
	</div>

    </body>
    </html>