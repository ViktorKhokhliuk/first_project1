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
    .reports {
    margin-left:195px;
    }
    .filter{
        margin-right:100px;
    }
    .clients {
     margin-right:10px;
      margin-left:10px;
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
                <form action = "/tax-office/service/getAllClients"  method="GET"  class = "clients">
                   <button type="submit" class="btn btn-primary">All clients</button>
                </form>
                <form action = "/tax-office/service/getAllReports"  method="GET" >
                   <button type="submit" class="btn btn-primary">All reports</button>
                </form>
		</nav>
	</header>
 <br>
    <h3 class="text-center">List of Reports by ${clientLogin}</h3>
 <hr>
      <form action = "/tax-office/service/filterClientReports"  method="GET" class = "filter" align = "right">
            <div class="form-group">
               <label for="name">Choose a date:</label>
                 <input type="date" name="date"
                  min="01-01-2010" max="12-31-2100"/>
               <label for="name">Choose a status:</label>
                  <select name="status">
                    <option value="">status</option>
                    <option value="SUBMITTED">SUBMITTED</option>
                    <option value="ACCEPTED">ACCEPTED</option>
                    <option value="UNACCEPTED">UNACCEPTED</option>
                  </select>
               <label for="name">Choose a type:</label>
                  <select name="type">
                    <option value="">type</option>
                    <option value="income statement">income statement</option>
                    <option value="income tax">income tax</option>
                    <option value="single tax">single tax</option>
                  </select>
               <input type="hidden" name="clientId" value="${clientId}"/>
               <input type="hidden" name="clientLogin" value="${clientLogin}"/>
               <button type="submit" class="btn btn-outline-dark">Filter</button>
            </div>
     </form>
     <form action = "/tax-office/service/getAllReportsByClientId"  method="GET" class = "reports">
        <input type="hidden" name="clientId" value="${clientId}"/>
         <input type="hidden" name="clientLogin" value="${clientLogin}"/>
         <button type="submit" class="btn btn-primary">All reports</button>
     </form>
            <p>
    <div class="container">
		<div class="container text-left">
		</div>
			<table class="table table-bordered">
				<thead>
					<tr>
					    <th>№</th>
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
						    <td class="counterCell"></td>
							<td><a href="showReport?clientId=${clientId}&name=<c:out value='${report.name}'/>"target="_blank">${report.name}</a></td>
							<td><c:out value="${report.status}" /></td>
							<td><c:out value="${report.date}" /></td>
							<td><c:out value="${report.type}" /></td>
							<td> <form action="/tax-office/service/updateStatusOfReport" method="POST">
							        <select name="status" required>
							          <option value="">status</option>
                                      <option value="ACCEPTED">ACCEPTED</option>
                                      <option value="UNACCEPTED">UNACCEPTED</option>
                                    </select>
                                    <input type="hidden" name="id" value="${report.id}"/>
                                    <input type="hidden" name="clientId" value="${clientId}"/>
                                    <input type="hidden" name="clientLogin" value="${clientLogin}"/>
                                    <input type="hidden" name="date" value="${date}"/>
                                    <input type="hidden" name="statusFilter" value="${status}"/>
                                    <input type="hidden" name="type" value="${type}"/>
                                    <button type="submit" class="btn btn-outline-dark">Update</button>
                                  </form>
                                  <a href="showReport?clientId=${clientId}&name=<c:out value='${report.name}'/>" download >
                                      <button  class="btn btn-outline-primary">Download</button>
                                  </a>
                                  <form action="/tax-office/service/deleteReportById" method="POST" onSubmit='return confirm("Are you sure?");'>
                                      <input type="hidden" name="id" value="${report.id}"/>
                                      <input type="hidden" name="clientId" value="${clientId}"/>
                                      <input type="hidden" name="clientLogin" value="${clientLogin}"/>
                                      <input type="hidden" name="name" value="${report.name}"/>
                                      <input type="hidden" name="date" value="${date}"/>
                                      <input type="hidden" name="statusFilter" value="${status}"/>
                                      <input type="hidden" name="type" value="${type}"/>
                                      <button type="submit" class="btn btn-outline-danger">Delete</button>
                                  </form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
    </div>
  </body>
</html>