<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Акт <c:out value="${act.id}"/></title>
</head>
<body>

<c:url value="/act/edit" var="saveUrl"/>

<header>
    <jsp:include page="header.jsp"/>
</header>

<form:form commandName="act" method="post" action="${saveUrl}">

    <form:input path="id" cssStyle="display: none"/>

    Представитель <form:input path="department"/>, <form:input path="institute"/><br/>

    <c:out value="${act.coordinator.surname}"/> <c:out value="${act.coordinator.firstName}"/> <c:out value="${act.coordinator.secondName}"/>,
    <form:input path="position"/>

    <table class="table table-striped table-bordered" style="text-align: center">
        <thead>
        <tr>
            <th><input type="checkbox"/></th>
            <th style="vertical-align: middle; text-align: center">№</th>
            <th style="vertical-align: middle; text-align: center">ФИО</th>
            <th style="vertical-align: middle; text-align: center">Тема ВКР</th>
            <th style="vertical-align: middle; text-align: center">Дата и номер лицензионного договора</th>
        </tr>
        </thead>
        <tbody>
        <%int i = 0; %>
        <c:forEach items="${tickets}" var="ticket">
            <% i++; %>
            <c:url var="checkTicket" value="/act/edit?actId=${act.id}"/>
            <tr>
                <td><form:checkbox path="ticketsId" value="${ticket.id}"/></td>
                <td><a href="${checkTicket}" class="editUrl" style="display: block"><%=i%>
                </a></td>
                <td><a href="${checkTicket}" class="editUrl" style="display: block"><c:out
                        value="${ticket.user.surname}"/> <c:out
                        value="${ticket.user.firstName}"/> <c:out
                        value="${ticket.user.secondName}"/></a></td>
                <td><a href="${checkTicket}" class="editUrl" style="display: block"><c:out value="${ticket.title}"/></a>
                </td>
                <td><a href="${checkTicket}" class="editUrl" style="display: block">Лицензионный договор</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    Телефон координатора <c:out value="${act.coordinator.phoneNumber}"/>
    E-mail координатора <c:out value="${act.coordinator.email}"/>

    <button name="button" value="save" type="submit">Сохранить</button>
    <button name="button" value="send" type="submit">Отправить на проверку</button>
</form:form>
</body>
</html>
