<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
 <div class="container">
  <h1>Registration form:</h1>
  <div class="card">
   <div class="card-body">
    <form action="/tax-office/service/registration" method="POST">

     <div class="form-group row">
      <label for="Name" class="col-sm-2 col-form-label">Name</label>
      <div class="col-sm-7">
       <input type="text" class="form-control" name="name"
        required pattern = "[A-Za-z]{2,30}|[А-Яа-яЁё]{2,30}"
        title="The name must contain of either Latin letters or Cyrillic and have size 2-30 letters"
        placeholder="Enter Name">
      </div>
     </div>

     <div class="form-group row">
      <label for="surname" class="col-sm-2 col-form-label">Surname</label>
      <div class="col-sm-7">
       <input type="text" class="form-control" name="surname"
        required pattern = "[A-Za-z]{2,30}|[А-Яа-яЁё]{2,30}"
        title="The surname must contain of either Latin letters or Cyrillic and have size 2-30 letters"
        placeholder="Enter Surname">
      </div>
     </div>

     <div class=" form-group row">
      <label for="itn" class="col-sm-2 col-form-label">ITN</label>
      <div class="col-sm-7">
       <input type="text" class="form-control" name="itn"
        required pattern = "[0-9]{12}"
        title="The ITN must contain 12 digits"
        placeholder="Enter ITN">
      </div>
     </div>

     <div class="form-group row">
      <label for="login" class="col-sm-2 col-form-label">Login</label>
      <div class="col-sm-7">
       <input type="text" class="form-control" name="login"
        required pattern = "[a-z_]{3,20}"
        title="The login must contain only lowercase letters and underscore, and have size 3-30 symbols"
        placeholder="Enter Login">
      </div>
     </div>

     <div class="form-group row">
      <label for="password" class="col-sm-2 col-form-label">Password</label>
      <div class="col-sm-7">
       <input type="password" class="form-control" name="password"
        required pattern = "[a-z0-9]{3,20}"
        title="The password must contain only lowercase letters or numbers, and have size 3-30 symbols"
        placeholder="Enter Password">
      </div>
     </div>

     <button type="submit" class="btn btn-primary">Submit</button>
    </form>
   </div>
  </div>
 </div>
</body>
</html>