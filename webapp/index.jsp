<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lan" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Authorization</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: black">
                <form action="/tax-office/service/changeLocale" method="POST" class = "locale">
                <input type="hidden" name="view" value="/index.jsp"/>
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
 <div class="container">
  <h1><lan:print message="please_sign_in"/>:</h1>
  <div class="card">
   <div class="card-body">
    <form accept-charset="UTF-8" method="POST" action="/tax-office/service/login">

     <div class="form-group row">
      <label for="login" class="col-sm-2 col-form-label"><lan:print message="login"/>:</label>
      <div class="col-sm-7">
       <input type="text" class="form-control" name="login"
       placeholder = "<lan:print message="enter_login"/>"
        required>
      </div>
     </div>

      <div class="form-group row">
      <label for="password" class="col-sm-2 col-form-label"><lan:print message="password"/>:</label>
      <div class="col-sm-7">
       <input type="password" class="form-control" name="password"
       placeholder = "<lan:print message="enter_password"/>"
        required>
      </div>
     </div>

     <button type="submit" class="btn btn-primary"><lan:print message="sign_in"/></button>
     </form>
     <p>
     <form action = "user/registration.jsp">
     <button type="submit" class="btn btn-primary"><lan:print message="registration"/></button>
     </form>
    </div>
   </div>
  </div>
 </body>
</html>