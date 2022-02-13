<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
 <div class="container">
  <h1>Authorization:</h1>
  <div class="card">
   <div class="card-body">
    <form accept-charset="UTF-8" method="POST" action="/tax-office/service/login">

     <div class="form-group row">
      <label for="login" class="col-sm-2 col-form-label">Login</label>
      <div class="col-sm-7">
       <input type="text" class="form-control" name="login"
        required
        placeholder="Enter Login">
      </div>
     </div>

      <div class="form-group row">
      <label for="password" class="col-sm-2 col-form-label">Password</label>
      <div class="col-sm-7">
       <input type="password" class="form-control" name="password"
        required
        placeholder="Enter Password">
      </div>
     </div>
     <button type="submit" class="btn btn-primary">Sign in</button>
     </form>
     <p>
     <form action = "user/registration.jsp">
     <button type="submit" class="btn btn-primary">Registration</button>
     </form>
    </div>
   </div>
  </div>
 </body>
</html>