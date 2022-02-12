<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome page.</title>
        <meta charset="UTF-8">
    </head>
         <body align="center">
             <p>
                 <h1>Home page for inspector</h1>
             <p>
             <jsp:text>
               Welcome: ${user.login}
             </jsp:text>
             <form accept-charset="UTF-8" method="POST" action="/tax-office/service/logout">
             <input type="submit" value='Logout'>
             </form>

             <form action = "/tax-office/service/getAllClients"  method="GET">
             <input type = "submit" value = "List of clients" />
             </form>
    </body>
</html>