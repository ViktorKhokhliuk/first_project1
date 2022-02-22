<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home page</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
   <body>
   <style>
   .reports {
      margin-left:100px;
   }
   .box{
       margin-left:400px;
   }
   form{
      display:inline-block;
   }
   </style>
         <header>
         		<nav class="navbar navbar-expand-md navbar-dark"
         			style="background-color: black">
         			<div>
         				 <form accept-charset="UTF-8" method="POST" action="/tax-office/service/logout">
                         <button type="submit" class="btn btn-primary ">Logout</button>
                         </form>
         			</div>
         		</nav>
         </header>
<p>
    <h1 align = "center">
      <jsp:text>
          Welcome, ${user.name}
      </jsp:text>
    </h1>
    <hr>
 <p>
          <div class = "box">
             <form method="POST" action="/tax-office/service/upload" enctype="multipart/form-data" class="form-horizontal" >
                   <h3>Upload a report</h3>
                   <div class="form-group">
                     <label for="uploadFile" class="col-xs-2 control-label">Choose a file:</label>
                       <input type="file" name="part" required/><br>
                   </div>
                   <div class="form-group">
                     <label for="selectType" class="col-xs-2 control-label">Choose a type:</label>
                        <select name="type" required>
                        <option value="">type</option>
                        <option value="income statement">income statement</option>
                        <option value="income tax">income tax</option>
                        <option value="single tax">single tax</option>
                        </select>
                   </div>
                   <div class="form-group">
                     <div class="col-xs-offset-2 col-xs-10">
                       <button type="submit" class="btn btn-outline-dark">Upload</button>
                     </div>
                   </div>
             </form>
             <form action = "/tax-office/service/allReportsByClient"  method="GET" class = reports>
                <input type="hidden" name="clientId" value="${user.id}"/><br><br>
                <button type="submit" class="btn btn-primary btn-lg">My Reports</button>
             </form>
          </div>
    </body>
</html>