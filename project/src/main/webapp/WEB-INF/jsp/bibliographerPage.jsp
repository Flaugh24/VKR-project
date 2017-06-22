<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/b.."
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!— Optional theme —>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/b.."
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/studentPage.css" />"/>

    <title>Личный кабинет координатора</title>
</head>
<body>
<div class="container-fluid">
    <header>
        <jsp:include page="header.jsp"/>
    </header>
    <main>
        <div>
            <ul class="nav nav-tabs" role="tablist">
                <c:if test="${not empty actAllCoordinators}">
                    <li role="presentation"><a href="#actAllCoordinators" aria-controls="actAllCoordinators" role="tab"
                                               data-toggle="tab">Новые (<c:out value="${countActsNew}"/>)</a></li>
                </c:if>
                <c:if test="${not empty actListGet}">
                    <li role="presentation"><a href="#actListGet" aria-controls="actListGet" role="tab"
                                               data-toggle="tab">Просмотренные(<c:out value="${countActsInCheck}"/>)</a>
                    </li>
                </c:if>

                <c:if test="${not empty actListReadyConvert}">
                    <li role="presentation"><a href="#actListReadyConvert" aria-controls="actListReadyConvert"
                                               role="tab"
                                               data-toggle="tab">Конвертация(<c:out value="${countActsConvert}"/>)</a>
                    </li>
                </c:if>
                <c:if test="${not empty actListReadyLibrary}">
                    <li role="presentation"><a href="#actListReadyLibrary" aria-controls="actListReadyLibrary"
                                               role="tab"
                                               data-toggle="tab">Готовы к передаче(<c:out
                            value="${countActsLibrary}"/>)</a></li>
                </c:if>
            </ul>
            <div class="tab-content">
                <c:if test="${not empty actAllCoordinators}">
                    <div role="tabpanel" class="tab-pane" id="actAllCoordinators">
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

                <c:if test="${not empty actListGet}">
                    <div role="tabpanel" class="tab-pane" id="actListGet">
                        <table class="table table-striped table-bordered" style="text-align: center">
                            <thead>
                            <tr>
                                <th style="vertical-align: middle; text-align: center">№</th>
                                <th
                                        style="vertical-align: middle; text-align: center">№ Акта
                                </th>
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

                <c:if test="${not empty actListReadyConvert}">
                    <div role="tabpanel" class="tab-pane" id="actListReadyConvert">
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
                    <div role="tabpanel" class="tab-pane" id="actListReadyLibrary">
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
                                    <td><a href="${checkTicket}" class="editUrl" style="display:
block"><c:out
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

            </div>

        </div>

    </main>
</div>
</body>
</html>