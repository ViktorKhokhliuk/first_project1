<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lan" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>
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
                <input type="hidden" name="view" value="/user/registration.jsp"/>
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
  <h1><lan:print message="registration_form"/>:</h1>
  <div class="card">
   <div class="card-body">
    <form action="/tax-office/service/registration" method="POST">

     <div class="form-group row">
      <label for="name" class="col-sm-2 col-form-label"><lan:print message="name"/></label>
      <div class="col-sm-7">
       <input type="text" class="form-control" name="name"
        required pattern = "[A-Za-z]{2,30}|[А-Яа-яЁё]{2,30}"
        placeholder = "<lan:print message="enter_name"/>"
        title="<lan:print message="name_pattern"/>">
      </div>
     </div>

     <div class="form-group row">
      <label for="surname" class="col-sm-2 col-form-label"><lan:print message="surname"/></label>
      <div class="col-sm-7">
       <input type="text" class="form-control" name="surname"
        required pattern = "[A-Za-z]{2,30}|[А-Яа-яЁё]{2,30}"
        placeholder = "<lan:print message="enter_surname"/>"
        title="<lan:print message="surname_pattern"/>">
      </div>
     </div>

     <div class=" form-group row">
      <label for="itn" class="col-sm-2 col-form-label"><lan:print message="itn"/></label>
      <div class="col-sm-7">
       <input type="text" class="form-control" name="itn"
        required pattern = "[0-9]{12}"
        placeholder = "<lan:print message="enter_itn"/>"
        title="<lan:print message="itn_pattern"/>">
      </div>
     </div>

     <div class="form-group row">
      <label for="login" class="col-sm-2 col-form-label"><lan:print message="login"/></label>
      <div class="col-sm-7">
       <input type="text" class="form-control" name="login"
        required pattern = "[a-z_]{3,20}"
        placeholder = "<lan:print message="enter_login"/>"
        title="<lan:print message="login_pattern"/>">
      </div>
     </div>

     <div class="form-group row">
      <label for="password" class="col-sm-2 col-form-label"><lan:print message="password"/></label>
      <div class="col-sm-7">
       <input type="password" class="form-control" name="password"
        required pattern = "[a-z0-9]{3,20}"
        placeholder = "<lan:print message="enter_password"/>"
        title="<lan:print message="password_pattern"/>">
      </div>
     </div>

     <button type="submit" class="btn btn-primary"><lan:print message="register"/></button>
    </form>
    <br>
    <form action = "/tax-office/index.jsp">
    <button type="submit" class="btn btn-secondary"><lan:print message="back"/></button>
    </form>
   </div>
  </div>
 </div>
</body>
</html>