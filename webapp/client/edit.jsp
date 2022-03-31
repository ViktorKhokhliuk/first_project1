<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lan" %>
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
  <h1><lan:print message="edit_form"/>:</h1>
  <div class="card">
   <div class="card-body">
    <form action="/tax-office/service/saveReportChanges" method="POST">

     <div class="form-group row">
      <label for="name" class="col-sm-2 col-form-label"><lan:print message="name"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${user.name}" class="form-control" name="name"
        readonly disabled>
      </div>
     </div>

     <div class="form-group row">
      <label for="surname" class="col-sm-2 col-form-label"><lan:print message="surname"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${user.surname}" class="form-control" name="surname"
        readonly disabled>
      </div>
     </div>

     <div class=" form-group row">
      <label for="itn" class="col-sm-2 col-form-label"><lan:print message="itn"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${user.itn}" class="form-control" name="itn"
        readonly disabled>
      </div>
     </div>

     <div class="form-group row">
      <label for="login" class="col-sm-2 col-form-label"><lan:print message="person"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.person}" class="form-control" name="person"
        required pattern = "(natural){1}|(legal){1}|(физическое){1}|(юридическое){1}"
        title="<lan:print message="person_pattern"/>">
      </div>
     </div>

     <div class="form-group row">
      <label for="nationality" class="col-sm-2 col-form-label"><lan:print message="nationality"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.nationality}" maxlength="20" class="form-control" name="nationality"
        required>
      </div>
     </div>

     <div class="form-group row">
      <label for="year" class="col-sm-2 col-form-label"><lan:print message="year"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.year}" class="form-control" name="year"
        required  required pattern = "^[1-9][0-9]{3}$"
        title="<lan:print message="year_pattern"/>">
      </div>
     </div>

     <div class="form-group row">
      <label for="quarter" class="col-sm-2 col-form-label"><lan:print message="quarter"/></label>
      <div class="col-sm-7">
       <input type="number" min = "1" max = "4" value = "${reportParameters.quarter}" class="form-control" name="quarter"
        required >
      </div>
     </div>

     <div class="form-group row">
      <label for="month" class="col-sm-2 col-form-label"><lan:print message="month"/></label>
      <div class="col-sm-7">
       <input type="number" min = "1" max = "12" value = "${reportParameters.month}" class="form-control" name="month"
        required >
      </div>
     </div>

     <div class="form-group row">
      <label for="group" class="col-sm-2 col-form-label"><lan:print message="group"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.group}" class="form-control" name="group"
        required pattern = "(I){1}|(II){1}|(III){1}|(IV){1}"
        title="<lan:print message="group_pattern"/>">
      </div>
     </div>

     <div class=" form-group row">
      <label for="activity" class="col-sm-2 col-form-label"><lan:print message="activity"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.activity}" maxlength="30" class="form-control" name="activity"
        required >
      </div>
     </div>

     <div class="form-group row">
      <label for="income" class="col-sm-2 col-form-label"><lan:print message="income"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.income}"  maxlength="20" class="form-control" name="income"
        required pattern = "[0-9]*"
        title="<lan:print message="income_pattern"/>">
      </div>
     </div>
     <input type="hidden" name="id" value="${dto.id}"/>
     <input type="hidden" name="clientId" value="${dto.clientId}"/>
     <input type="hidden" name="title" value="${dto.title}"/>
     <input type="hidden" name="date" value="${dto.date}"/>
     <input type="hidden" name="statusFilter" value="${dto.statusFilter}"/>
     <input type="hidden" name="type" value="${dto.type}"/>
     <button type="submit" class="btn btn-primary"><lan:print message="edit"/></button>
    </form>
    <br>
    <input type="button" class="btn btn-secondary" onclick="history.back();" value=<lan:print message="back"/>>
   </div>
  </div>
 </div>
</body>
</html>

