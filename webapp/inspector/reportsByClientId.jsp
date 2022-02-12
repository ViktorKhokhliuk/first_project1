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
                    <input type="submit" value='Logout'>
                    </form></a>
			</div>
		</nav>
	</header>
	<br>

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
							<td><c:out value="${report.name}" /></td>
							<td><c:out value="${report.status}" /></td>
							<td><c:out value="${report.date}" /></td>
							<td><c:out value="${report.type}" /></td>
							<td><a> <form action="/tax-office/service/deleteReportById" method="POST">
							        <input type="hidden" name="id" value="${report.id}"/>
							        <input type="hidden" name="clientId" value="${clientId}"/>
							        <input type="hidden" name="clientLogin" value="${clientLogin}"/>
							        <input type="submit" value='delete'></form></a>
							    <a> <form action="/tax-office/service/updateStatusOfReport" method="POST">
							    <p><select multiple name="status">
                                <option value="ACCEPTED">ACCEPTED</option>
                                <option value="UNACCEPTED">UNACCEPTED</option>
                                </select></p>
                                    <input type="hidden" name="id" value="${report.id}"/>
                                    <input type="hidden" name="clientId" value="${clientId}"/>
                                    <input type="hidden" name="clientLogin" value="${clientLogin}"/>
                                    <input type="submit" value='submit'></form></a>
							</td>
						</tr>
					</c:forEach>

				</tbody>

			</table>
		</div>
	</div>