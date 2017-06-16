<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<c:if test="${not empty actList}">
    <div role="tabpanel" class="tab-pane" id="acts">
        <table class="table table-striped table-bordered" style="text-align: center">
            <thead>
            <tr>
                <th style="vertical-align: middle; text-align: center">№</th>
                <th style="vertical-align: middle; text-align: center">№ Акта</th>
                <th style="vertical-align: middle; text-align: center">Дата создания</th>
                <th style="vertical-align: middle; text-align: center">Институт</th>
                <th style="vertical-align: middle; text-align: center">Кафедра</th>
                <th style="vertical-align: middle; text-align: center">Координатор</th>
            </tr>
            </thead>
            <tbody>
            <% Integer i = 0; %>
            <c:forEach items="${actList}" var="act">
                <% i++; %>
                <c:url var="checkTicket" value="/act/check?actId=${act.id}"/>
                <tr>
                    <td><a href="${checkTicket}" class="editUrl" style="display: block"><%=i%>
                    </a></td>
                    <td><a href="${checkTicket}" class="editUrl" style="display: block"><c:out
                            value="${act.id}"/></a></td>
                    <td><a href="${checkTicket}" class="editUrl" style="display: block"><fmt:formatDate
                            pattern="dd.MM.yyyy" value="${act.dateOfCreate}"/></a></td>
                    <td><a href="${checkTicket}" class="editUrl" style="display: block"><c:out
                            value="${act.institute}"/></a></td>
                    <td><a href="${checkTicket}" class="editUrl" style="display: block"><c:out
                            value="${act.department}"/></a></td>
                    <td><a href="${checkTicket}" class="editUrl" style="display: block"><c:out
                            value="${act.coordinator.surname}"/> <c:out
                            value="${act.coordinator.firstName}"/> <c:out
                            value="${act.coordinator.secondName}"/></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>

</body>
</html>
