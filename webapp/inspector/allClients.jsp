<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lan" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List of Clients</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>
   <body>
	<style>
	.logout {
      margin-right:10px;
    }
    .search{
        margin-right:100px;
    }
    .clients {
        margin-left:150px;
    }
    table {
       counter-reset: tableCount;
       table-layout: fixed;
        margin: auto;
    }
    td {
        word-wrap:break-word;
        text-align: center;
    }
    tr {
       text-align: center;
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
                <button type="submit" class="btn btn-primary"><lan:print message="logout"/></button>
                </form>
                <form action="/tax-office/service/toHome" method="GET">
                <button type="submit" class="btn btn-primary"><lan:print message="home"/></button>
                </form>
		</nav>
	</header>
 <br>
 	<h3 class="text-center"><lan:print message="list_clients"/></h3>
 	<hr>
 <form action = "/tax-office/service/searchClients"  method="GET" class = "search" align="right">
       <div class="form-group">
          <label for="name"><lan:print message="name"/>:</label>
          <input type="text" name="name" placeholder="<lan:print message="enter_name"/>"/>
          <label for="name"><lan:print message="surname"/>:</label>
          <input type="text" name="surname" placeholder="<lan:print message="enter_surname"/>"/>
          <label for="name"><lan:print message="itn"/>:</label>
          <input type="text" name="itn"= placeholder="<lan:print message="enter_itn"/>"/>
          <button type="submit" class="btn btn-outline-dark"><lan:print message="search"/></button>
       </div>
       </form>
       <form action = "/tax-office/service/getAllClients"  method="GET" class = "clients">
           <button type="submit" class="btn btn-primary"><lan:print message="all_clients"/></button>
       </form>
<br>
			<table class=" table-bordered " width="1200">
			<col style="width:4%">
    				<thead>
					<tr>
					    <th>№</th>
						<th><lan:print message="login"/></th>
						<th><lan:print message="name"/></th>
						<th><lan:print message="surname"/></th>
						<th><lan:print message="itn"/></th>
						<th><lan:print message="actions"/></th>
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
                               <form action = "/tax-office/service/allReportsByClient" method = "GET">
							     <input type="hidden" name="clientId" value="${client.id}"/>
							     <input type="hidden" name="clientLogin" value="<c:out value='${client.login}'/>"/>
							     <button type="submit" class="btn btn-outline-info"><lan:print message="reports"/></button>
							   </form>
							   <form action="/tax-office/service/deleteClientById" method="POST" onSubmit='return confirm("Are you sure?");'>
                                 <input type="hidden" name="clientId" value="${client.id}"/>
                                 <button type="submit" class="btn btn-outline-danger"><lan:print message="delete"/></button>
                               </form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br>
	</div>
</body>
</html>