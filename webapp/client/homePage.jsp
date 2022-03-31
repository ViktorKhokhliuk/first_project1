<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lan" %>
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
   .logout {
     margin-right:10px;
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
         				 <form accept-charset="UTF-8" method="POST" action="/tax-office/service/logout" class = "logout">
                         <button type="submit" class="btn btn-primary "><lan:print message="logout"/></button>
                         </form>
                         <form action="/tax-office/service/changeLocale" method="POST" class = "locale">
                            <input type="hidden" name="view" value="/client/homePage.jsp"/>
                               <select name="selectedLocale">
                                  <c:forEach var="locale" items="${sessionScope.locales}">
                                     <option value="${locale}">
                                        ${locale}
                                     </option>
                                  </c:forEach>
                               </select>
                            <button type="submit" class="btn btn-primary btn-sm"><lan:print message="update"/></button>
                         </form>
         			</div>
         		</nav>
         </header>
<p>
    <h1 align = "center">
          <lan:print message="welcome"/>, ${user.name}
    </h1>
    <hr>
 <p>
          <div class = "box">
             <form method="POST" action="/tax-office/service/upload" enctype="multipart/form-data" class="form-horizontal" >
                   <h3><lan:print message="upload_report"/></h3>
                   <div class="form-group">
                     <label for="uploadFile" class="col-xs-2 control-label"><lan:print message="choose_file"/>:</label>
                       <input type="file" name="part" required/><br>
                   </div>
                   <div class="form-group">
                     <label for="selectType" class="col-xs-2 control-label"><lan:print message="choose_type"/>:</label>
                        <select name="type" required>
                        <option value=""><lan:print message="type"/></option>
                        <option value="income statement">income statement(Справка о доходах)</option>
                        <option value="income tax">income tax(Подоходный налог)</option>
                        <option value="single tax">single tax(Единый налог)</option>
                        </select>
                   </div>
                   <div class="form-group">
                     <div class="col-xs-offset-2 col-xs-10">
                       <button type="submit" class="btn btn-outline-dark"><lan:print message="upload"/></button>
                     </div>
                   </div>
             </form>
             <form action = "/tax-office/service/allReportsByClient"  method="GET" class = reports>
                <input type="hidden" name="clientId" value="${user.id}"/><br><br>
                <button type="submit" class="btn btn-primary btn-lg"><lan:print message="my_reports"/></button>
             </form>
          </div>
    </body>
</html>