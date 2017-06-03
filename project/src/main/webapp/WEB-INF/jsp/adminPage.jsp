<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<title>Spring MVC checkbox</title>
</head>
<body>
<h2>Subscribe to the gym</h2>
<form:form method="POST">
    <table>
        <tr>
            <td>Are you a new member?</td>
            <td><form:checkbox path="newMember"/>
            </td>
        </tr>
        <tr>
            <td>Choose the courses you like:</td>
            <td><form:checkboxes path="listPermission" items="${courses}"/>
            </td>
        </tr>
        <tr>
            <td><input type="submit" name="submit" value="Submit"></td>
        </tr>
        <tr>
    </table>
</form:form>
</body>
</html>
