<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<%--    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />--%>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <title>Login Page</title>
</head>
<body>
    <div class="container">
        <div row>
            <div class="col-md-offset-4 col-md-4">
            <form method="POST" action="${contextPath}/login" class="form-signin">
                <h2 class="form-heading">Войти в систему</h2>

                <div class="form-group ${error != null ? 'has-error' : ''}">
                    <span>${message}</span>
                    <label class="sr-only" for="username">Имя пользователя</label>
                    <input name="username" id="username" type="text" class="form-control" placeholder="Username"
                           autofocus="true"/>
                </div>
                <div class="form-group">
                    <label class="sr-only" for="password">Пароль</label>
                    <input name="password" id="password" type="password" class="form-control" placeholder="Password"/>
                    <span>${error}</span>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </div>
                <div class="form-group">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="remember-me"> Remember me
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <button class="btn btn-primary" type="submit" style="width: 100px">Войти</button>
                </div>
            </form>
            </div>
        </div>
    </div>
</body>
</html>
