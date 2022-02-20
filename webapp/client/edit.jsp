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
    <p>
<div class="container">
  <h1>Edit form:</h1>
  <div class="card">
   <div class="card-body">
    <form action="/tax-office/service/saveReportChanges" method="POST">

     <div class="form-group row">
      <label for="Name" class="col-sm-2 col-form-label">Name</label>
      <div class="col-sm-7">
       <input type="text" value = "${user.name}" class="form-control" name="name"
        readonly disabled>
      </div>
     </div>

     <div class="form-group row">
      <label for="surname" class="col-sm-2 col-form-label">Surname</label>
      <div class="col-sm-7">
       <input type="text" value = "${user.surname}" class="form-control" name="surname"
        readonly disabled>
      </div>
     </div>

     <div class=" form-group row">
      <label for="itn" class="col-sm-2 col-form-label">ITN</label>
      <div class="col-sm-7">
       <input type="text" value = "${user.itn}" class="form-control" name="itn"
        readonly disabled>
      </div>
     </div>

     <div class="form-group row">
      <label for="login" class="col-sm-2 col-form-label">Person(natural,legal)</label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.person}" class="form-control" name="person"
        required pattern = "(natural){1}|(legal){1}"
        title="natural or legal">
      </div>
     </div>

     <div class="form-group row">
      <label for="password" class="col-sm-2 col-form-label">Nationality</label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.nationality}" class="form-control" name="nationality"
        required>
      </div>
     </div>

     <div class="form-group row">
      <label for="password" class="col-sm-2 col-form-label">Year</label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.year}" class="form-control" name="year"
        required  required pattern = "^[1-9][0-9]{3}$"
        title="example: 2022">
      </div>
     </div>

     <div class="form-group row">
      <label for="password" class="col-sm-2 col-form-label">Quarter(1,2,3,4)</label>
      <div class="col-sm-7">
       <input type="number" min = "1" max = "4" value = "${reportParameters.quarter}" class="form-control" name="quarter"
        required >
      </div>
     </div>

     <div class="form-group row">
      <label for="password" class="col-sm-2 col-form-label">Number of Month</label>
      <div class="col-sm-7">
       <input type="number" min = "1" max = "12" value = "${reportParameters.month}" class="form-control" name="month"
        required >
      </div>
     </div>

     <div class="form-group row">
      <label for="surname" class="col-sm-2 col-form-label">Group(I,II,III,IV)</label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.group}" class="form-control" name="group"
        required pattern = "(I){1}|(II){1}|(III){1}|(IV){1}"
        title="I or II or III or IV group">
      </div>
     </div>

     <div class=" form-group row">
      <label for="itn" class="col-sm-2 col-form-label">Activity</label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.activity}" class="form-control" name="activity"
        required >
      </div>
     </div>

     <div class="form-group row">
      <label for="login" class="col-sm-2 col-form-label">Income</label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.income}" class="form-control" name="income"
        required pattern = "\d+"
        title="The income must contain only numbers">
      </div>
     </div>
     <input type="hidden" name="id" value="${dto.id}"/>
     <input type="hidden" name="clientId" value="${dto.clientId}"/>
     <input type="hidden" name="title" value="${dto.title}"/>
     <input type="hidden" name="date" value="${dto.date}"/>
     <input type="hidden" name="statusFilter" value="${dto.statusFilter}"/>
     <input type="hidden" name="type" value="${dto.type}"/>
     <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    <br>
    <input type="button" class="btn btn-secondary" onclick="history.back();" value="Back"/>
   </div>
  </div>
 </div>
</body>
</html>

