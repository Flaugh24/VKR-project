<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"         pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />

    <link rel="stylesheet" href="<c:url value="/resources/css/liveSearch.css"/>"/>

    <title>Заявка <c:out value="${ticketAttribute.id}" /></title>

</head>
<body style="overflow-y: hidden">

<c:url var="uploadUrl" value="/ticket/upload" />
<c:url var="deleteUrl" value="/ticket/deletepdf" />
<c:url var="deleteUrlrar" value="/ticket/deleterar" />
<c:url var="saveUrl" value="/ticket/edit?ticketId=${ticketAttribute.id}" />
<c:url var="pdfDocument" value="/pdfDocument?ticketId=${ticketAttribute.id}" />
<c:set var="coordinator" value="${coordinator}" />

<c:choose>
<c:when test="${ticketAttribute.status.id != '1'}">
    <c:set var="varclass" value="disabledEdit"/>
</c:when>
</c:choose>

<div class="container-fluid">
    <header>
        <jsp:include page="header.jsp" />
    </header>
    <div class="main">
        <div class="row">
            <div class="col-md-5" style="max-height: 100vh; overflow-y: auto; padding-bottom: 100px">
                <h2>Номер заявки <c:out value="${ticketAttribute.id}" /></h2>
                <c:if test="${ticketAttribute.status.id == 1}">
                    <c:if test="${ticketAttribute.filePdf == null }">
                        <div>
                            <form method="POST" action="${uploadUrl}" enctype="multipart/form-data">
                                <label for="ticketId"></label>
                                <input  name="ticketId" id="ticketId" value="${ticketAttribute.id}" style="display: none"/>
                                <div class="form-group">
                                    <label for="uploadFile">Загрузите файлы Вашей ВКР в формате PDF или ZIP</label>
                                    <input  class="${varclass}" name="uploadFile" id="uploadFile" type="file" accept=".zip,application/octet-stream,application/zip,application/x-zip,application/x-zip-compresse,application/pdf"/>
                                </div>
                                <div class="form-group">
                                    <input id="filePdf" type="submit" name="submit" value="Загрузить PDF" class="btn btn-default" disabled="disabled"/>

                                    <input id="fileZip" type="submit" name="submit" value="Загрузить Архив" class="btn btn-default" disabled="disabled"/>
                                </div>
                            </form>
                        </div>
                    </c:if>

                    <c:if test="${ticketAttribute.filePdf != null }">
                        <div>
                            <form method="POST" action="${deleteUrl}" enctype="multipart/form-data">
                                <title for="ticketId"></title>
                                <input  name="ticketId" id="ticketId1" value="${ticketAttribute.id}" style="display: none"/>
                                <div class="form-group">
                                    <label for="uploadFile1">Загрузите файлы Вашей ВКР в формате PDF или ZIP</label>
                                    <input  name="uploadFile" id="uploadFile1" type="file" accept="application/pdf"/>
                                </div>
                                <div class="form-group">
                                    <input type="submit" name="submit" value="Удалить PDF" class="btn btn-default"/>

                                    <input type="submit" name="submit" value="Загрузить Архив" class="btn btn-default"/>
                                </div>
                            </form>
                        </div>
                    </c:if>
                </c:if>
                <form:form commandName="ticketAttribute" method="POST">
                    <div>
                        <form:label path="id" cssStyle="display: none" />
                        <form:input path="id" cssStyle="display: none" />
                    </div>
                    <div class="form-group">
                        <form:label path="title">Тип документа</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="documentTypeName" disabled="true"/>
                    </div>
                    <div class="form-group">
                        <form:label path="title">Тип документа на английском</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="documentTypeNameEng" disabled="true"/>
                    </div>
                    <div class="form-group">
                        <form:label path="title">ФИО научного руководителя</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="sflNMaster" id="w-input-search"/>
                    </div>
                    <div class="form-group">
                        <form:label path="title">ФИО научного руководителя на английском</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="sflNMasterEng" />
                    </div>
                    <div class="form-group">
                    <form:label path="title">Ученая степень руководителя</form:label><br/>
                    <form:input cssClass="form-control ${varclass}" path="degreeOfCurator" id="degree"/>
                    </div>
                    <div class="form-group">
                    <form:label path="title">Ученая степень руководителя на английском</form:label><br/>
                    <form:input cssClass="form-control ${varclass}" path="degreeOfCuratorEng" disabled="true"/>
                    </div>
                    <div class="form-group">
                    <form:label path="title">Должность руководителя</form:label><br/>
                    <form:input cssClass="form-control ${varclass}" path="posOfCurator" id="position"/>
                    </div>
                    <div class="form-group">
                    <form:label path="title">Должность руководителя на английском</form:label><br/>
                    <form:input cssClass="form-control ${varclass}" path="posOfCuratorEng" disabled="true"/></div>
                    <div class="form-group">
                        <div class="form-group">
                            <form:label path="title">Заглавие работы</form:label>
                            <form:input cssClass="form-control ${varclass}" path="title"/>
                        </div>
                        <div class="form-group">
                            <form:label path="titleEng">Заглавие работы на английском языке</form:label>
                            <form:input cssClass="form-control ${varclass}" path="titleEng"/>
                        </div>
                        <div class="form-group">
                            <form:label path="title">Место публикации</form:label><br/>
                            <form:input cssClass="form-control ${varclass}" path="placeOfPublic" />
                        </div>
                        <div class="form-group">
                            <form:label path="title">Место публикации на английском</form:label><br/>
                            <form:input cssClass="form-control ${varclass}" path="placeOfPublicEng" />
                        </div>
                        <div class="form-group">
                            <form:label path="title">Год публикации</form:label><br/>
                            <form:input cssClass="form-control ${varclass}" path="dateOfPublic" disabled="true"/>
                        </div>


                        <div class="form-group">
                            <form:label path="annotation">Аннотация</form:label>
                            <form:textarea cssClass="form-control ${varclass}" path="annotation" rows="5" cssStyle="max-width:100%"/>
                        </div>
                        <div class="form-group">
                            <form:label path="annotationEng">Аннотация на английском языке</form:label>
                            <form:textarea cssClass="form-control ${varclass}" path="annotationEng" rows="5" cssStyle="max-width:100%"/>
                        </div>
                        <div class="form-group">
                            <form:label path="annotationEng">Ключевые слова</form:label>
                            <div class="row">
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="Первое слово" path="word1"/>
                                </div>
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="Второе слово" path="word2"/>
                                </div>
                            </div>

                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="Третье слово" path="word3"/>
                                </div>
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="Четвертое слово" path="word4"/>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <form:label path="annotationEng">Ключевые слова на английском языке</form:label>
                            <div class="row">
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="The first word" path="word1Eng"/>
                                </div>
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="The second word" path="word2Eng"/>
                                </div>
                            </div>

                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="The third word" path="word3Eng"/>
                                </div>
                                <div class="col-xs-6">
                                    <form:input cssClass="form-control ${varclass}" placeholder="The fourth word" path="word4Eng"/>
                                </div>
                            </div>
                        </div>

                        <form:label path="typeOfUseId">Свободный доступ из сети</form:label>
                        <form:select path="typeOfUseId" cssClass="form-control ${varclass}" cssStyle="width: auto">
                            <c:forEach items="${typeOfUse}" var="typeOfUse">
                                <form:option value="${typeOfUse.id}"><c:out value="${typeOfUse.name}" /></form:option>>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <form:label path="title">Направления подготовки</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="direction" disabled="true"/>
                    </div>
                    <div class="form-group">
                        <form:label path="title">Код направления подготовки</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="directionCode" disabled="true"/>
                    </div>
                    <div class="form-group">
                        <form:label path="title">Институт</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="institute" disabled="true"/>
                    </div>
                    <div class="form-group">
                        <form:label path="title">Группа</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="groupNum" disabled="true"/>
                    </div>
                    <div class="form-group">
                        <form:label path="title">Кафедра</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="department" disabled="true"/>
                    </div>
                    <div class="form-group">
                        <form:label path="title">ФИО заведующего кафедрой</form:label><br/>
                        <form:input cssClass="form-control ${varclass}" path="surFirstLastNameDir"/>
                    </div>
                    <c:if test="${ticketAttribute.status.id == 1}">
                    <div class="form-group">
                        <input type="submit" name="button" formaction="${saveUrl}" value="Сохранить изменения" class="btn btn-default"/>
                        <input type="submit" name="button" formaction="${saveUrl}" value="Отправить на проверку" class="btn btn-default"/>
                    </div>
                    </c:if>
                </form:form>
            </div>
            <div class="col-md-7" style="padding-top: 10px">
                <c:if test="${ticketAttribute.filePdf != null}">
                    <iframe src="<c:url value="/files/${ticketAttribute.id}.pdf" />" style="width: 100%; height: 100vh"/>
                </c:if>
            </div>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="<c:url value="/resources/js/uploadFile.js"/> "></script>
<script src="<c:url value="/resources/js/jquery.autocomplete.min.js"/> "></script>
<script>
    $(document).ready(
        function ($) {
            $('.disabledEdit').prop("disabled", true);
        }
    );


    $('#w-input-search').autocomplete({
        serviceUrl: '${pageContext.request.contextPath}/getTags', // Страница для обработки запросов автозаполнения
        paramName: "tagName",
        minChars: 3, // Минимальная длина запроса для срабатывания автозаполнения
        delimiter: ",", // Разделитель для нескольких запросов, символ или регулярное выражение
        maxHeight: 400, // Максимальная высота списка подсказок, в пикселях
        width: 300, // Ширина списка
        zIndex: 9999, // z-index списка
        deferRequestBy: 300, // Задержка запроса (мсек), на случай, если мы не хотим слать миллион запросов, пока пользователь печатает. Я обычно ставлю 300.
        transformResult: function(response) {
            return {

                suggestions: $.map($.parseJSON(response), function(item) {

                    return { value: (item.surname + ' ' + item.firstName + ' ' + item.secondName), position: item.position, degree: item.degree };

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