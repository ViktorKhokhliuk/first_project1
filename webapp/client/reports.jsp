<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lan" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reports</title>
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
    .page {

    }
    .reports {
    margin-left:150px;
    }
    .filter{
        margin-right:100px;
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
			<div>
				<form accept-charset="UTF-8" method="POST" action="/tax-office/service/logout" class = "logout">
                <button type="submit" class="btn btn-primary"><lan:print message="logout"/></button>
                </form>
            </div>
            <div>
                <form action="/tax-office/service/toHome" method="GET">
                <button type="submit" class="btn btn-primary"><lan:print message="home"/></button>
                </form>
            </div>
		</nav>
	</header>
 <br>
	<h3 class="text-center"><lan:print message="list_reports"/></h3>
    <hr>
 <p>
      <form action = "/tax-office/service/filterClientReports" class = "filter" align="right">
      <div class="form-group">
         <label for="name"><lan:print message="choose_date"/>:</label>
           <input type="date" name="date"
            min="01-01-2010" max="12-31-2100"/>
         <label for="name"><lan:print message="choose_status"/>:</label>
            <select name="status">
              <option value=""><lan:print message="status"/></option>
              <option value="SUBMITTED"><lan:print message="submitted"/></option>
              <option value="ACCEPTED"><lan:print message="accepted"/></option>
              <option value="UNACCEPTED"><lan:print message="unaccepted"/></option>
              <option value="EDITED"><lan:print message="edited"/></option>
            </select>
         <label for="name"><lan:print message="choose_type"/>:</label>
            <select name="type">
              <option value=""><lan:print message="type"/></option>
              <option value="income statement"><lan:print message="income_statement"/></option>
              <option value="income tax"><lan:print message="income_tax"/></option>
              <option value="single tax"><lan:print message="single_tax"/></option>
            </select>
         <input type="hidden" name="clientId" value="${user.id}"/>
         <input type="hidden" name="page" value="1"/>
         <button type="submit" class="btn btn-outline-dark"><lan:print message="filter"/></button>
      </div>
      </form>
 <p>
      <form action = "/tax-office/service/allReportsByClient"  method="GET" align="left" class = "reports">
          <input type="hidden" name="clientId" value="${user.id}"/>
          <input type="hidden" name="page" value="1">
          <button type="submit" class="btn btn-primary"><lan:print message="all_my_reports"/></button>
      </form>
<br>
			<table class=" table-bordered " width="1200">
			<col style="width:4%">
				<thead>
					<tr>
					    <th>№</th>
                        <th><lan:print message="title"/></th>
                        <th><lan:print message="date"/></th>
                        <th><lan:print message="type_table"/></th>
                        <th><lan:print message="status_table"/></th>
                        <th><lan:print message="info"/></th>
                        <th><lan:print message="actions"/></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${reports}" var="report">
						<tr>
						    <td class="counterCell"></td>
							<td><a href="showReport?clientId=${user.id}&title=<c:out value='${report.title}'/>"target="_blank">${report.title}</a></td>
							<td><c:out value="${report.date}" /></td>
                            <td><c:out value="${report.type}" /></td>
                            <td><c:out value="${report.status}" /></td>
                            <td><c:out value="${report.info}" /></td>
							<td>
							   <form action="/tax-office/service/editReport" method="GET" >
                                  <input type="hidden" name="id" value="${report.id}"/>
                                  <input type="hidden" name="page" value="${page}"/>
                                  <input type="hidden" name="clientId" value="${user.id}"/>
                                  <input type="hidden" name="title" value="${report.title}"/>
                                  <input type="hidden" name="date" value="${date}"/>
                                  <input type="hidden" name="statusFilter" value="${status}"/>
                                  <input type="hidden" name="type" value="${type}"/>
                                  <button type="submit" class="btn btn-outline-primary" ><lan:print message="edit"/></button>
                               </form>
							   <a href="upload/id${user.id}/${report.title}" download >
							   <button  class="btn btn-outline-primary"><lan:print message="download"/></button>
							   </a>
							   <form action="/tax-office/service/deleteReportById" method="POST" onSubmit='return confirm("<lan:print message="are_you_sure"/>");'>
                                  <input type="hidden" name="id" value="${report.id}"/>
                                  <input type="hidden" name="page" value="${page}"/>
                                  <input type="hidden" name="clientId" value="${user.id}"/>
                                  <input type="hidden" name="title" value="${report.title}"/>
                                  <input type="hidden" name="date" value="${date}"/>
                                  <input type="hidden" name="statusFilter" value="${status}"/>
                                  <input type="hidden" name="type" value="${type}"/>
                                  <button type="submit" class="btn btn-outline-danger"><lan:print message="delete"/></button>
                               </form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br>
	</div>
	<c:if test = "${countOfPage != 0}">
       <c:forEach var = "i" begin = "1" end = "${countOfPage}">
       <form action = "/tax-office/service/filterClientReports"  method="GET" class = page style="float:left">
           <input type="hidden" name="clientId" value="${user.id}"/><br><br>
           <input type="hidden" name="page" value="${i}"/>
           <input type="hidden" name="date" value="${date}"/>
           <input type="hidden" name="status" value="${status}"/>
           <input type="hidden" name="type" value="${type}"/>
           <button type="submit" class="btn btn-link" >${i}</button>
       </form>
       </c:forEach>
    </c:if>
</body>
</html>
