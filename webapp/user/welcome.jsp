<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome page.</title>
        <meta charset="UTF-8">
    </head>
    <body>
         <jsp:text>
            Welcome: ${user.login}
         </jsp:text>
          <form accept-charset="UTF-8" method="POST" action="/tax-office/service/logout">
            <input type="submit" value='Logout'>
          </form>
    </body>
</html>