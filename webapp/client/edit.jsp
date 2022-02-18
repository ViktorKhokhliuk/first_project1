<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
    <body>
          <form accept-charset="UTF-8" method="POST" action="/tax-office/service/saveReportChanges" align = "center">
            <p><textarea name="report" cols="70" rows="35">
${report}
            </textarea></p>
            <input type="hidden" name="id" value="${dto.id}"/>
            <input type="hidden" name="date" value="${dto.date}"/>
            <input type="hidden" name="statusFilter" value="${dto.statusFilter}"/>
            <input type="hidden" name="type" value="${dto.type}"/>
            <input type="hidden" name="clientId" value="${dto.clientId}"/>
            <input type="hidden" name="name" value="${dto.name}"/>
            <input type="hidden" name="status" value="EDITED"/>
            <input type="hidden" name="info" value="Report has been edited"/>
            <button type="submit" class="btn btn-outline-primary">Submit</button>
            </form>
          <form accept-charset="UTF-8" method="POST" action="/tax-office/service/logout">
            <input type="submit" value='Logout'>
          </form>
    </body>
</html>