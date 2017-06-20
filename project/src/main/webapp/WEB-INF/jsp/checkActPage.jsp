<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: gagarkin
  Date: 13.06.17
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<header>
    <jsp:include page="header.jsp"/>
</header>

Представитель <c:out value="${dto.act.department}"/>, <c:out value="${dto.act.institute}"/><br/>
<c:out value="${dto.act.coordinator.surname}"/> <c:out value="${dto.act.coordinator.firstName}"/> <c:out value="${dto.act.coordinator.secondName}"/>, <c:out value="${act.position}"/>



<form:form commandName="dto" method="post" action="${saveUrl}">

    <form:input path="act.id" cssStyle="display: none"/>
<table class="table table-striped table-bordered" style="text-align: center">
    <thead>
    <tr>
        <th style="vertical-align: middle; text-align: center"><input type="checkbox"/></th>
        <th style="vertical-align: middle; text-align: center">№</th>
        <th style="vertical-align: middle; text-align: center">№ регистрационного листа</th>
        <th style="vertical-align: middle; text-align: center">ФИО</th>
        <th style="vertical-align: middle; text-align: center">Тема ВКР</th>
        <th style="vertical-align: middle; text-align: center">Дата и номер лицензионного договора</th>
    </tr>
    </thead>
    <tbody>
    <%int i = 0; %>
    <c:forEach items="${dto.act.tickets}" var="ticket">
        <% i++; %>
        <c:url var="checkTicket" value="/ticket/${ticket.id}/check"/>
        <tr>
            <td><form:checkbox path="ticketsId" value="${ticket.id}"/></td>
            <td><a href="${checkTicket}" class="editUrl" style="display: block"><%=i%>
            </a></td>
            <td><a href="${checkTicket}" class="editUrl" style="display: block"><c:out value="${ticket.id}"/></a></td>
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


    <button type="submit"  name="button" value="return" class="btn btn-default">Вернуть координатору</button>
    <button type="submit"  name="button" value="accept" class="btn btn-default">Принять акт</button>
    <button type="submit"  name="button" value="convert" class="btn btn-default">Конвертировать содержимое</button>

</form:form>
</body>
</html>
