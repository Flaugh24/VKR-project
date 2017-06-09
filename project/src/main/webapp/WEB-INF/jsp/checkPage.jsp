<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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

    <!-- Latest compiled and minified JavaScript -->
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/liveSearch.css"/>"/>

    <title>Заявка <c:out value="${ticketAttribute.id}"/></title>

</head>
<body>
<c:url var="uploadUrl" value="/ticket/fileupload"/>
<c:url var="deleteUrl" value="/ticket/filedelete"/>
<c:url var="saveUrl" value="/ticket/check?ticketId=${ticketAttribute.id}"/>
<c:url var="pdfDocument" value="/pdfDocument?ticketId=${ticketAttribute.id}"/>

<c:choose>
    <c:when test="${!(ticketAttribute.status.id == 3 || ticketAttribute.status.id == 4)}">
        <c:set var="varclass" value="disabledEdit"/>
    </c:when>
</c:choose>

<div class="container-fluid">
    <header>
        <jsp:include page="header.jsp"/>
    </header>
    <div class="main">
        <div class="row">
            <div class="col-md-5" style="max-height: 100vh; overflow-y: auto; padding-bottom: 100px">
                <h2>Номер заявки <c:out value="${ticketAttribute.id}"/></h2>

                <c:if test="${ticketAttribute.status.id == 3 || ticketAttribute.status.id == 4}">
                        <c:if test="${(ticketAttribute.filePdf == null || ticketAttribute.fileZip == null)&&
                                  (ticketAttribute.filePdfSecret == null || ticketAttribute.fileZipSecret == null)&&
                                  (ticketAttribute.filePdf == null || ticketAttribute.fileZipSecret == null)&&
                                  (ticketAttribute.filePdfSecret == null || ticketAttribute.fileZip == null)}">
                            <div>
                                <form method="POST" action="${uploadUrl}" enctype="multipart/form-data">
                                    <label for="ticketId">
                                        <input name="ticketId" id="ticketId" value="${ticketAttribute.id}"
                                               style="display: none"/>
                                    </label>
                                    <div class="form-group">
                                        <label>Загрузите файлы Вашей ВКР в формате PDF или ZIP<br/>
                                            <input name="uploadFile" id="uploadFile" type="file"/>
                                        </label>
                                        <input type="checkbox" name="tradeSecret" value="${true}"/>
                                    </div>

                                    <div class="form-group">
                                        <input type="submit" name="submit" value="Загрузить"
                                               class="btn btn-default uploadButton" disabled/>
                                    </div>
                                </form>
                            </div>
                        </c:if>
                        <div>
                            <form method="POST" action="${deleteUrl}" enctype="multipart/form-data">
                                <label>
                                    <input name="ticketId" value="${ticketAttribute.id}" style="display: none"/>
                                </label>
                                <c:if test="${ticketAttribute.filePdf != null }">
                                    <input type="submit" name="submit" value="Удалить PDF" class="btn btn-default"/>
                                </c:if>
                                <c:if test="${ticketAttribute.filePdfSecret != null }">
                                    <input type="submit" name="submit" value="Удалить PDF" class="btn btn-default"/>
                                </c:if>
                                <c:if test="${ticketAttribute.fileZip != null }">
                                    <input type="submit" name="submit" value="Удалить архив" class="btn btn-default"/>
                                </c:if>
                                <c:if test="${ticketAttribute.fileZipSecret != null }">
                                    <input type="submit" name="submit" value="Удалить архив" class="btn btn-default"/>
                                </c:if>
                            </form>
                        </div>
                    </c:if>
                <form:form commandName="ticketAttribute" method="POST" id="ticketform" action="${saveUrl}">

                    <div>
                        <form:input path="id" cssStyle="display: none"/>
                    </div>
                    <div class="form-group">
                        <form:label path="licenseNumber">Номер лицензионного договора</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="licenseNumber"/>
                    </div>
                    <div class="form-group">
                        <form:label path="licenseDate">Дата лицензионного договора</form:label><br/>
                        <form:input type="date" cssClass="form-control ${varclass}" path="licenseDateDTO"/>
                    </div>
                    <div class="form-group">
                        <form:label path="documentType.name">Тип документа</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="documentType.name" disabled="true"/>
                    </div>
                    <div class="form-group">
                        <form:label path="documentType.nameEng">Тип документа на английском</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="documentType.nameEng" disabled="true"/>
                    </div>
                    <div class="form-group">
                        <form:label path="fullNameCurator">ФИО научного руководителя</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="fullNameCurator" id="w-input-search"
                                    maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="fullNameCuratorEng">ФИО научного руководителя на английском</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="fullNameCuratorEng" maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="degreeOfCurator">Ученая степень руководителя</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="degreeOfCurator" id="degree"
                                    maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label
                                path="degreeOfCuratorEng">Ученая степень руководителя на английском</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="degreeOfCuratorEng" maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="posOfCurator">Должность руководителя</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="posOfCurator" id="position"
                                    maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="posOfCuratorEng">Должность руководителя на английском</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="posOfCuratorEng" maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <div class="form-group">
                            <form:label path="title">Заглавие работы</form:label>
                            <form:input cssClass="form-control ${varclass}" path="title" name="title" maxlength="255"/>
                        </div>
                        <div class="form-group">
                            <form:label path="titleEng">Заглавие работы на английском языке</form:label>
                            <form:input cssClass="form-control ${varclass}" path="titleEng" maxlength="255"/>
                        </div>
                        <div class="form-group">
                            <form:label path="placeOfPublic">Место публикации</form:label><br/>
                            <form:input cssClass="form-control ${varclass}" path="placeOfPublic" maxlength="255"/>
                        </div>
                        <div class="form-group">
                            <form:label path="placeOfPublicEng">Место публикации на английском</form:label><br/>
                            <form:input cssClass="form-control ${varclass}" path="placeOfPublicEng" maxlength="255"/>
                        </div>
                        <div class="form-group">
                            <form:label path="dateOfPublic">Год публикации</form:label><br/>
                            <form:input cssClass="form-control ${varclass}" path="dateOfPublic" disabled="true"
                                        maxlength="4"/>
                        </div>
                        <div class="form-group">
                            <form:label path="annotation">Аннотация</form:label>
                            <form:textarea path="annotation" rows="5" maxlength="1024"
                                           cssClass="form-control ${varclass}" cssStyle="max-width:100%"/>
                        </div>
                        <div class="form-group">
                            <form:label path="annotationEng">Аннотация на английском языке</form:label>
                            <form:textarea path="annotationEng" rows="5" maxlength="1024"
                                           cssClass="form-control ${varclass}" cssStyle="max-width:100%"/>
                        </div>
                        <div class="form-group">
                            <form:label path="word1">Ключевые слова</form:label>
                            <div class="row">
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="Первое слово"
                                                path="word1" maxlength="64"/>
                                </div>
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="Второе слово"
                                                path="word2" maxlength="64"/>
                                </div>
                            </div>

                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="Третье слово"
                                                path="word3" maxlength="64"/>
                                </div>
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="Четвертое слово"
                                                path="word4" maxlength="64"/>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <form:label path="word1Eng">Ключевые слова на английском языке</form:label>
                            <div class="row">
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="The first word"
                                                path="word1Eng" maxlength="64"/>
                                </div>
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="The second word"
                                                path="word2Eng" maxlength="64"/>
                                </div>
                            </div>

                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="The third word"
                                                path="word3Eng" maxlength="64"/>
                                </div>
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="The fourth word"
                                                path="word4Eng" maxlength="64"/>
                                </div>
                            </div>
                        </div>

                        <form:label path="typeOfUse.id">Свободный доступ из сети</form:label>
                        <form:select path="typeOfUse.id" cssClass="form-control ${varclass}" cssStyle="width: auto">
                            <c:forEach items="${typesOfUse}" var="typeOfUse">
                                <form:option value="${typeOfUse.id}"><c:out value="${typeOfUse.name}"/></form:option>>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <form:label path="direction">Направлениe подготовки</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="direction" disabled="true"
                                    maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="directionCode">Код направления подготовки</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="directionCode" disabled="true"
                                    maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="institute">Институт</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="institute" disabled="true"
                                    maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="groupNum">Группа</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="groupNum" disabled="true"
                                    maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="department">Кафедра</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="department" disabled="true"
                                    maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <form:label path="headOfDepartment">ФИО заведующего кафедрой</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="headOfDepartment" maxlength="255"/>
                    </div>
                    <c:if test="${ticketAttribute.status.id == 3 || ticketAttribute.status.id == 4}">
                        <div class="form-group">
                            <button type="submit" name="button" value="recordSheet" class="btn btn-default">
                                Регистрационный лист
                            </button>
                            <button type="submit" name="button" value="licenseAgreement" class="btn btn-default">
                                Лицензионный договор
                            </button>
                            <br/>
                            <button type="submit" name="button" value="save" class="btn btn-default">Сохранить
                                изменения
                            </button>
                            <button type="submit" name="button" value="ready" class="btn btn-default">Готова для
                                передачи в ИБК
                            </button>
                            <button type="submit" name="button" value="return" class="btn btn-default">Вернуть
                                студенту
                            </button>
                        </div>
                    </c:if>
                </form:form>
            </div>
            <div class="col-md-7" style="padding-top: 10px">
                <c:if test="${ticketAttribute.filePdf != null}">
                    <iframe src="<c:url value="/files/${ticketAttribute.id}.pdf" />"
                            style="width: 100%; height: 100vh"></iframe>
                </c:if>
            </div>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="<c:url value="/resources/js/uploadFile.js"/> "></script>
<script src="<c:url value="/resources/js/libs/jquery.autocomplete.min.js"/> "></script>

<script>
    $(document).ready(
        function ($) {
            $('.disabledEdit').prop("disabled", true);
        }
    );

    $('#ticketform').keydown(function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            return false;
        }
    });


    $('#w-input-search').autocomplete({
        serviceUrl: '${pageContext.request.contextPath}/getTags', // Страница для обработки запросов автозаполнения
        paramName: "tagName",
        minChars: 3, // Минимальная длина запроса для срабатывания автозаполнения
        delimiter: ",", // Разделитель для нескольких запросов, символ или регулярное выражение
        maxHeight: 400, // Максимальная высота списка подсказок, в пикселях
        width: 300, // Ширина списка
        zIndex: 9999, // z-index списка
        deferRequestBy: 300, // Задержка запроса (мсек), на случай, если мы не хотим слать миллион запросов, пока пользователь печатает. Я обычно ставлю 300.
        transformResult: function (response) {
            return {

                suggestions: $.map($.parseJSON(response), function (item) {

                    return {
                        value: (item.surname + ' ' + item.firstName + ' ' + item.secondName),
                        position: item.position,
                        degree: item.degree
                    };

                })
            };

        },
        onSelect: function (suggestion) {
            $('#position').val(suggestion.position);
            $('#degree').val(suggestion.degree);
        }
    });

</script>
</body>
</html>