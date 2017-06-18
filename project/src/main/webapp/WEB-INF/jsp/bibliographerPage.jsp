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
<title>Заявка <c:out value="${user.surname}"/></title>
<head>
    <title>Личный кабинет библиографа</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"/>
</header>
</main>

    <div role="tabpanel" class="tab-pane" id="acts">
        <ul class="nav nav-tabs" role="tablist">
            <c:if test="${not empty actAllCoordinators}">
                <li role="presentation"><a href="#actAllCoordinators" aria-controls="actAllCoordinators" role="tab"
                                           data-toggle="tab">Новые (<c:out value="${countActsNew}"/>)</a></li>
            </c:if>
            <c:if test="${not empty actListGet}">
                <li role="presentation"><a href="#actListGet" aria-controls="actListGet" role="tab"
                                           data-toggle="tab">Просмотренные(<c:out value="${countActsInCheck}"/>)</a></li>
            </c:if>

            <c:if test="${not empty actListReadyConvert}">
                <li role="presentation"><a href="#actListReadyConvert" aria-controls="actListReadyConvert" role="tab"
                                           data-toggle="tab">Конвертация(<c:out value="${countActsConvert}"/>)</a></li>
            </c:if>
            <c:if test="${not empty actListReadyLibrary}">
                <li role="presentation"><a href="#actListReadyLibrary" aria-controls="actListReadyLibrary" role="tab"
                                           data-toggle="tab">Готовы к передаче(<c:out value="${countActsLibrary}"/>)</a></li>
            </c:if>

        </ul>
        <c:if test="${not empty actListGet}">
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
            <c:forEach items="${actListGet}" var="act">
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

<c:if test="${not empty actAllCoordinators}">
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
        <c:forEach items="${actAllCoordinators}" var="act">
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

<c:if test="${not empty actListReadyConvert}">
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
        <c:forEach items="${actListReadyConvert}" var="act">
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


<c:if test="${not empty actListReadyLibrary}">
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
        <c:forEach items="${actListReadyLibrary}" var="act">
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
