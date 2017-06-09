<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: gagarkin
  Date: 08.06.17
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Личный кабинет библиографа</title>
</head>
<body>
<header>
<jsp:include page="header.jsp"/>
</header>

<c:forEach items="${actList}" var="act">
    <button class="btn btn-primary btn-block" type="button" data-toggle="collapse" data-target="#collapseExample${act.id}" aria-expanded="false" aria-controls="collapseExample">
        <c:out value="${act.id}"/>
    </button>
    <div class="collapse" id="collapseExample${act.id}">
        <div class="well">
            Представитель <c:out value="${act.department}"/>, <c:out value="${act.institute}"/><br/>
            <c:out value="${act.user.surname}"/> <c:out value="${act.user.firstName}"/> <c:out value="${act.user.secondName}"/>
            <table class="table table-striped table-bordered" style="text-align: center">
                <thead>
                <tr>
                    <th style="vertical-align: middle; text-align: center">№</th>
                    <th style="vertical-align: middle; text-align: center">ФИО</th>
                    <th style="vertical-align: middle; text-align: center">Тема ВКР</th>
                    <th style="vertical-align: middle; text-align: center">Дата и номер лицензионного договора</th>
                </tr>
                </thead>
                <tbody>
                <%int i = 0; %>
                <c:forEach items="${act.tickets}" var="ticket">
                    <% i++; %>
                    <c:url var="editAct" value="/act/edit?actId=${act.id}"/>
                    <tr>
                        <td><a href="${editAct}" class="editUrl" style="display: block"><%=i%>
                        </a></td>
                        <td><a href="${editAct}" class="editUrl" style="display: block"><c:out
                                value="${ticket.user.surname}"/> <c:out
                                value="${ticket.user.firstName}"/> <c:out
                                value="${ticket.user.secondName}"/></a></td>
                        <td><a href="${editAct}" class="editUrl" style="display: block"><c:out value="${ticket.title}"/></a>
                        </td>
                        <td><a href="${editAct}" class="editUrl" style="display: block">Лицензионный договор</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</c:forEach>

</body>
</html>
