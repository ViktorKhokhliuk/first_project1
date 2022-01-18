<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Index page.</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <p>
            This is a simple JSP file.
        </p>
          <form accept-charset="UTF-8" method="POST" action="/tax-office/service/login">
                                   <label for="name">Login:</label><br>
                                   <input type="text" name="login"><br><br>
                                   <label for="pass">Password:</label><br>
                                   <input type="password" name="password"><br><br>
                                   <input type="submit" value='Sign in'>
                     </form>
    </body>
</html>