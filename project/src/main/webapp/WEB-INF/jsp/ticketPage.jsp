<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Заявка <c:out value="${ticket.id}"/></title>
</head>
<body>

<c:url var="editUrl" value="/ticket/${ticket.id}/edit"/>

<header>
    <jsp:include page="header.jsp"/>
</header>
<main>
    № заявки: <c:out value="${ticket.id}"/>
    Дата создания: <fmt:formatDate pattern="dd.MM.yyyy" value="${ticket.dateCreationStart}"/>"
    Статус: <c:out value="${ticket.status.name}"/>
    Автор: <c:out value="${ticket.user.surname}"/> <c:out value="${ticket.user.firstName}"/> <c:out
        value="${ticket.user.secondName}"/>
    Телефон: <c:out value="${ticket.user.phoneNumber}"/> Email: <c:out value="${ticket.user.email}"/>
    Заглавие работы: <c:out value="${ticket.title}"/>
    Специальность: <c:out value="${ticket.direction}"/> <c:out value="${ticket.directionCode}"/>

    <a href="${editUrl}" type="button" class="btn btn-default">Редактирвоать</a>

</main>
</body>
</html>
