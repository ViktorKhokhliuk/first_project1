<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lan" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Report</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
    <body>
    <script>
    function close_window() {
      if (confirm("Close Window?")) {
        close();
      }
    }
    </script>
    <p>
<div class="container">
  <h1><lan:print message="report_info"/>:</h1>
  <div class="card">
   <div class="card-body">
    <form>

     <div class="form-group row">
      <label for="person" class="col-sm-2 col-form-label"><lan:print message="person"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.person}" class="form-control" name="person"
         readonly disabled>
      </div>
     </div>

     <div class="form-group row">
      <label for="nationality" class="col-sm-2 col-form-label"><lan:print message="nationality"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.nationality}" class="form-control" name="nationality"
         readonly disabled>
      </div>
     </div>

     <div class="form-group row">
      <label for="year" class="col-sm-2 col-form-label"><lan:print message="year"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.year}" class="form-control" name="year"
         readonly disabled>
      </div>
     </div>

     <div class="form-group row">
      <label for="quarter" class="col-sm-2 col-form-label"><lan:print message="quarter"/></label>
      <div class="col-sm-7">
       <input type="number" min = "1" max = "4" value = "${reportParameters.quarter}" class="form-control" name="quarter"
         readonly disabled>
      </div>
     </div>

     <div class="form-group row">
      <label for="month" class="col-sm-2 col-form-label"><lan:print message="month"/></label>
      <div class="col-sm-7">
       <input type="number" min = "1" max = "12" value = "${reportParameters.month}" class="form-control" name="month"
         readonly disabled>
      </div>
     </div>

     <div class="form-group row">
      <label for="group" class="col-sm-2 col-form-label"><lan:print message="group"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.group}" class="form-control" name="group"
         readonly disabled>
      </div>
     </div>

     <div class=" form-group row">
      <label for="activity" class="col-sm-2 col-form-label"><lan:print message="activity"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.activity}" class="form-control" name="activity"
         readonly disabled>
      </div>
     </div>

     <div class="form-group row">
      <label for="income" class="col-sm-2 col-form-label"><lan:print message="income"/></label>
      <div class="col-sm-7">
       <input type="text" value = "${reportParameters.income}" class="form-control" name="income"
         readonly disabled>
      </div>
     </div>
    </form>
    <br>
    <input type="button" class="btn btn-secondary" onclick="close_window();return false;" value=<lan:print message="back"/>>
   </div>
  </div>
 </div>
</body>
</html>