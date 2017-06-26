<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <title>Назначение прав <c:out value="${dto.name}"/></title>

</head>
<body>
<c:url value="/admin/groups/${dto.pathVariable}" var="saveUrl"/>
<header>
    <jsp:include page="header.jsp"/>
</header>
<div class="container">
    <main>
        <form:form commandName="dto" action="${saveUrl}" method="post">
            <table class="table table-striped table-bordered" style="width: auto">
                <thead>
                <tr>
                    <th style="vertical-align: middle; text-align: center"><input type="checkbox"/></th>
                    <th style="vertical-align: middle; text-align: center">Группы</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${groupsNum}" var="groupNum">
                    <tr>
                        <td style="vertical-align: middle; text-align: center"><form:checkbox path="checkedValsStr"
                                                                                              value="${groupNum}"/></td>
                        <td><c:out value="${groupNum}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <button type="submit" class="btn btn-default">Сохранить</button>
        </form:form>
    </main>
</div>
</body>
</html>
