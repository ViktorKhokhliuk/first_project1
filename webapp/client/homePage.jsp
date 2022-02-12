<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome page.</title>
        <meta charset="UTF-8">
    </head>
         <body align="center">
             <p>
                 <h1>Home page for client</h1>
             <p>
             <jsp:text>
               Welcome: ${user.name}
             </jsp:text>
             <p>
                 <h1>Create report</h1>
             <p>

            <br><br>
                     <form method="POST" action="/tax-office/service/upload" enctype="multipart/form-data">
                     Choose a file: <input type="file" name="multiPartServlet" />
                     Enter a type: <input type="text" name="type"><br><br>
                     <input type="submit" value="Upload" />
                      </form>
            <br>
            <br>

                <form action = "/tax-office/service/getAllReportsByClientId"  method="GET">
                <input type="hidden" name="clientId" value="${user.id}"/><br><br>
                <input type = "submit" value = "your reports" />
                </form>


             <form accept-charset="UTF-8" method="POST" action="/tax-office/service/logout">
             <input type="submit" value='Logout'>
             </form>
    </body>
</html>