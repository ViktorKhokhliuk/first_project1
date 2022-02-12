<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<body>
    <h1>Регистрация</h1>

    <form action="/tax-office/service/registration" method="POST">
        <table style="with: 50%">
        				<tr>
        					<td>Name</td>
        					<td><input type="text" name="name" /></td>
        				</tr>
        				<tr>
        					<td>Surname</td>
        					<td><input type="text" name="surname" /></td>
        				</tr>
        				<tr>
                             <td>ITN</td>
                             <td><input type="text" name="itn" /></td>
                        </tr>
        				<tr>
        					<td>Login</td>
        					<td><input type="text" name="login" /></td>
        				</tr>
        					<tr>
        					<td>Password</td>
        					<td><input type="password" name="password" /></td>
        				</tr>
        			<input type="submit" value="Submit" /></form>
</body>
</html>