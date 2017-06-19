<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Акт <c:out value="${actDto.act.id}"/></title>
</head>
<body>

<c:url value="/act/${actDto.act.id}/edit" var="saveUrl"/>

<header>
    <jsp:include page="header.jsp"/>
</header>

<form:form commandName="actDto" method="post" action="${saveUrl}">

    <form:input path="act.id" cssStyle="display: none"/>

    Представитель <form:input path="act.department"/>, <form:input path="act.institute"/><br/>

    <c:out value="${actDto.act.coordinator.surname}"/> <c:out value="${actDto.act.coordinator.firstName}"/> <c:out value="${actDto.act.coordinator.secondName}"/>,
    <form:input path="act.position"/>

    <table class="table table-striped table-bordered" style="text-align: center">
        <thead>
        <tr>
            <th style="vertical-align: middle; text-align: center"><input type="checkbox"/></th>
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
            <c:url var="checkTicket" value="/ticket/${ticket.id}/check"/>
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

    Телефон координатора <c:out value="${actDto.act.coordinator.phoneNumber}"/>
    E-mail координатора <c:out value="${actDto.act.coordinator.email}"/>

    <h1><form:errors path="ticketsId" /></h1>
    <h1><form:errors path="act.department" /></h1>
    <h1><form:errors path="act.institute" /></h1>
    <h1><form:errors path="act.position" /></h1>
    <button name="button" value="save" type="submit">Сохранить</button>
    <button name="button" value="send" type="submit">Отправить на проверку</button>
</form:form>
</body>
</html>
