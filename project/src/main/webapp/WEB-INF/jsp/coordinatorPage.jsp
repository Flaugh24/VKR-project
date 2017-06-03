<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/studentPage.css" />"/>

    <title>Личный кабинет координатора</title>
</head>
<body>
<c:url value="/ticket/addLazy" var="addLazy"/>
<c:url value="/createAct" var="createAct"/>
<div class="container-fluid">
    <header>
        <jsp:include page="header.jsp"/>
    </header>
    <main>
        <div>

            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <c:if test="${not empty ticketsNew}">
                    <li role="presentation"><a href="#ticketsNew" aria-controls="ticketsNew" role="tab"
                                               data-toggle="tab">Новые (<c:out value="${countTicketsNew}"/>)</a></li>
                </c:if>
                <c:if test="${not empty ticketsInCheck}">
                    <li role="presentation"><a href="#ticketsInCheck" aria-controls="ticketsInCheck" role="tab"
                                               data-toggle="tab">Просмотренные (<c:out
                            value="${countTicketsInCheck}"/>)</a></li>
                </c:if>
                <c:if test="${not empty ticketsReady}">
                    <li role="presentation"><a href="#ticketsReady" aria-controls="ticketsReady" role="tab"
                                               data-toggle="tab">Проверенные (<c:out value="${countTicketsReady}"/>)</a>
                    </li>
                </c:if>
                <li role="presentation"><a href="#ticketsInvalid" aria-controls="ticketsInvalid" role="tab"
                                           data-toggle="tab">На доработку</a></li>
                <c:if test="${not empty lazyStudents}">
                    <li role="presentation"><a href="#lazyStudents" aria-controls="lazyStudents" role="tab"
                                               data-toggle="tab">Студенты (<c:out value="${countLazyStudents}"/>)</a>
                    </li>
                </c:if>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <c:if test="${not empty ticketsNew}">
                    <div role="tabpanel" class="tab-pane" id="ticketsNew">
                        <div class="panel panel-default" style="margin-top: 10px">
                            <div class="panel-heading">
                                <h2 class="panel-title">Заявки доступные для проверки</h2>
                            </div>
                            <table class="table table-striped table-bordered" style="text-align: center">
                                <thead>
                                <tr>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">№</th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">№ Заявки</th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">Номер группы</th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">ФИО соискателя
                                    </th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">Заглавие работы
                                    </th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">Тип документа
                                    </th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">Вид
                                        использования
                                    </th>
                                    <th colspan="2" style="vertical-align: middle; text-align: center">Лицензионный
                                        договор
                                    </th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">Статус</th>
                                </tr>
                                <tr>
                                    <th style="vertical-align: middle; text-align: center">Дата</th>
                                    <th style="vertical-align: middle; text-align: center">Номер</th>
                                </tr>
                                </thead>
                                <tbody>
                                <% Integer i = 0; %>
                                <c:forEach items="${ticketsNew}" var="ticketNew">
                                    <% i++; %>
                                    <c:url var="checkUrl" value="/ticket/check?ticketId=${ticketNew.id}"/>
                                    <a href="${checkUrl}">
                                        <tr>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><%=i%>
                                            </a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketNew.id}"/></a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketNew.groupNum}"/> </a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketNew.surname}"/> <c:out
                                                    value="${ticketNew.firstName}"/> <c:out
                                                    value="${ticketNew.secondName}"/></a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketNew.title}"/> </a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketNew.documentType}"/></a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketNew.typeOfUse}"/></a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block">Data</a>
                                            </td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block">Data</a>
                                            </td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketNew.status}"/> </a></td>
                                        </tr>
                                    </a>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty ticketsInCheck}">
                    <div role="tabpanel" class="tab-pane" id="ticketsInCheck">
                        <div class="panel panel-default" style="margin-top: 10px">
                            <div class="panel-heading">
                                <h2 class="panel-title">Заявки доступные для проверки</h2>
                            </div>
                            <table class="table table-striped table-bordered" style="text-align: center">
                                <thead>
                                <tr>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">№</th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">№ Заявки</th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">Номер группы</th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">ФИО соискателя
                                    </th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">Заглавие работы
                                    </th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">Тип документа
                                    </th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">Вид
                                        использования
                                    </th>
                                    <th colspan="2" style="vertical-align: middle; text-align: center">Лицензионный
                                        договор
                                    </th>
                                    <th rowspan="2" style="vertical-align: middle; text-align: center">Статус</th>
                                </tr>
                                <tr>
                                    <th style="vertical-align: middle; text-align: center">Дата</th>
                                    <th style="vertical-align: middle; text-align: center">Номер</th>
                                </tr>
                                </thead>
                                <tbody>
                                <% int i = 0; %>
                                <c:forEach items="${ticketsInCheck}" var="ticketInCheck">
                                    <% i++; %>
                                    <c:url var="checkUrl" value="/ticket/check?ticketId=${ticketInCheck.id}"/>
                                    <a href="${checkUrl}">
                                        <tr>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><%=i%>
                                            </a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketInCheck.id}"/></a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketInCheck.groupNum}"/></a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketInCheck.surname}"/> <c:out
                                                    value="${ticketInCheck.firstName}"/> <c:out
                                                    value="${ticketInCheck.secondName}"/></a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketInCheck.title}"/></a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketInCheck.documentType}"/></a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketInCheck.typeOfUse}"/></a></td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block">Data</a>
                                            </td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block">Data</a>
                                            </td>
                                            <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                    value="${ticketInCheck.status}"/></a></td>
                                        </tr>
                                    </a>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty ticketsReady}">
                    <div role="tabpanel" class="tab-pane" id="ticketsReady">
                        <form:form method="post" commandName="checkBox" action="${createAct}">
                            <div class="panel panel-default" style="margin-top: 10px">
                                <div class="panel-heading">
                                    <h2 class="panel-title">Заявки доступные для проверки</h2>
                                </div>
                                <table class="table table-striped table-bordered" style="text-align: center"
                                       id="tableReady">
                                    <thead>
                                    <tr>
                                        <th rowspan="2" style="vertical-align: middle; text-align: center"><input
                                                type="checkbox" id="all"/></th>
                                        <th rowspan="2" style="vertical-align: middle; text-align: center">№</th>
                                        <th rowspan="2" style="vertical-align: middle; text-align: center">№ Заявки</th>
                                        <th rowspan="2" style="vertical-align: middle; text-align: center">Номер
                                            группы
                                        </th>
                                        <th rowspan="2" style="vertical-align: middle; text-align: center">ФИО
                                            соискателя
                                        </th>
                                        <th rowspan="2" style="vertical-align: middle; text-align: center">Заглавие
                                            работы
                                        </th>
                                        <th rowspan="2" style="vertical-align: middle; text-align: center">Тип
                                            документа
                                        </th>
                                        <th rowspan="2" style="vertical-align: middle; text-align: center">Вид
                                            использования
                                        </th>
                                        <th colspan="2" style="vertical-align: middle; text-align: center">Лицензионный
                                            договор
                                        </th>
                                        <th rowspan="2" style="vertical-align: middle; text-align: center">Статус</th>
                                    </tr>
                                    <tr>
                                        <th style="vertical-align: middle; text-align: center">Дата</th>
                                        <th style="vertical-align: middle; text-align: center">Номер</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <% int i = 0; %>
                                    <c:forEach items="${ticketsReady}" var="ticketReady">
                                        <% i++; %>
                                        <c:url var="checkUrl" value="/ticket/check?ticketId=${ticketReady.id}"/>
                                        <a href="${checkUrl}">
                                            <tr>
                                                <td><form:checkbox value="${ticketReady.id}" path="id"/></td>
                                                <td><a href="${checkUrl}" class="editUrl" style="display: block"><%=i%>
                                                </a></td>
                                                <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                        value="${ticketReady.id}"/></a></td>
                                                <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                        value="${ticketReady.groupNum}"/></a></td>
                                                <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                        value="${ticketReady.surname}"/> <c:out
                                                        value="${ticketReady.firstName}"/> <c:out
                                                        value="${ticketReady.secondName}"/></a></td>
                                                <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                        value="${ticketReady.title}"/></a></td>
                                                <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                        value="${ticketReady.documentType}"/></a></td>
                                                <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                        value="${ticketReady.typeOfUse}"/></a></td>
                                                <td><a href="${checkUrl}" class="editUrl"
                                                       style="display: block">Data</a></td>
                                                <td><a href="${checkUrl}" class="editUrl"
                                                       style="display: block">Data</a></td>
                                                <td><a href="${checkUrl}" class="editUrl" style="display: block"><c:out
                                                        value="${ticketReady.status}"/></a></td>
                                            </tr>

                                        </a>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <button>Создать акт</button>
                        </form:form>
                    </div>
                </c:if>
                <div role="tabpanel" class="tab-pane" id="ticketsInvalid">...</div>
                <c:if test="${not empty lazyStudents}">
                    <div role="tabpanel" class="tab-pane" id="lazyStudents">
                        <div class="panel panel-default" style="margin-top: 10px">
                            <div class="panel-heading">
                                <h2 class="panel-title">Создать заявку за студента</h2>
                            </div>
                            <table class="table table-striped table-bordered" style="text-align: center">
                                <thead>
                                <tr>
                                    <th style="vertical-align: middle; text-align: center">№</th>
                                    <th style="vertical-align: middle; text-align: center">Номер группы</th>
                                    <th style="vertical-align: middle; text-align: center">ФИО соискателя</th>
                                </tr>
                                </thead>
                                <tbody>
                                <% int i = 0; %>
                                <c:forEach items="${lazyStudents}" var="lazyStudent">
                                    <% i++; %>
                                    <a href="${checkUrl}">
                                        <tr>
                                            <td><a href="#"
                                                   onclick="document.getElementById('addticket${lazyStudent.studentCopy.username}').submit()"
                                                   class="editUrl" style="display: block"><%=i%>
                                            </a></td>
                                            <td><a href="#"
                                                   onclick="document.getElementById('addticket${lazyStudent.studentCopy.username}').submit()"
                                                   class="editUrl" style="display: block"><c:out
                                                    value="${lazyStudent.educProgram.groupNum}"/></a></td>
                                            <td><a href="#"
                                                   onclick="document.getElementById('addticket${lazyStudent.studentCopy.username}').submit()"
                                                   class="editUrl" style="display: block"><c:out
                                                    value="${lazyStudent.studentCopy.surname}"/> <c:out
                                                    value="${lazyStudent.studentCopy.firstName}"/> <c:out
                                                    value="${lazyStudent.studentCopy.secondName}"/></a></td>
                                        </tr>
                                    </a>
                                    <form method="post" id="addticket${lazyStudent.studentCopy.username}"
                                          action="${addLazy}" enctype="application/x-www-form-urlencoded">
                                        <input name="lazyStudentId" value="${lazyStudent.studentCopy.username}"
                                               style="display: none"/>
                                        <input name="educId" value="${lazyStudent.educProgram.id}"
                                               style="display: none"/>
                                    </form>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>

    </main>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="<c:url value="/resources/js/checkAll.js"/> "></script>
<script src="<c:url value="/resources/js/coordinator.js"/>"></script>

</body>
</html>
