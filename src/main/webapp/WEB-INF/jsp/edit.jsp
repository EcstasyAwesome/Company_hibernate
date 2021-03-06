<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.github.company.util.Avatar" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Редактирование профиля</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheet/style.css"/>">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/static/top.jsp"/>
<main>
    <form id="avatar" method="post">
        <input type="hidden" name="method" value="DELETE">
    </form>
    <form method="post" enctype="multipart/form-data">
        <input type="hidden" name="method" value="UPDATE">
        <table align="center">
            <tr>
                <td rowspan="6" width="250" align="center">
                    <%--@elvariable id="sessionUser" type="com.github.company.dao.entity.User"--%>
                    <img src="<c:url value="${sessionUser.avatar}"/>" width="200">
                </td>
                <td width="110">Фамилия:</td>
                <td><input title="Фамилия" type="text" size="15" value="${sessionUser.surname}"
                           name="surname" required></td>
            </tr>
            <tr>
                <td>Имя:</td>
                <td><input title="Имя" type="text" size="15" value="${sessionUser.firstName}"
                           name="firstName" required></td>
            </tr>
            <tr>
                <td>Отчество:</td>
                <td><input title="Отчество" type="text" size="15" value="${sessionUser.middleName}"
                           name="middleName" required></td>
            </tr>
            <tr>
                <td>Телефон:</td>
                <td><input title="Телефон" type="text" size="15" value="${sessionUser.phone}"
                           pattern="380[0-9]{9}" name="phone" required></td>
            </tr>
            <tr>
                <td>Пароль:</td>
                <td><input title="Пароль" type="password" size="15" value="${sessionUser.password}"
                           name="password" required></td>
            </tr>
            <tr>
                <td>Фото:</td>
                <td><input type="file" name="avatar" accept="image/jpeg,image/png,image/gif">
                </td>
            </tr>
            <tr>
                <td align="center">
                    <c:if test="${sessionUser.avatar.equals(Avatar.DEFAULT_AVATAR)}">
                        <input form="avatar" type="submit" value="Удалить фото" disabled>
                    </c:if>
                    <c:if test="${!sessionUser.avatar.equals(Avatar.DEFAULT_AVATAR)}">
                        <input form="avatar" type="submit" value="Удалить фото">
                    </c:if>
                </td>
                <td colspan="2" align="center">
                    <small>Данное поле можно оставить пустым<br>
                        Максимальный размер файла 1024КБ
                    </small>
                    <%--@elvariable id="profileError" type="java.lang.String"--%>
                    <c:if test="${profileError!=null}">
                        <p class="server-answer">${profileError}</p>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td colspan="3" align="center">
                    <br><input type="submit" value="Сохранить изменения">
                </td>
            </tr>
        </table>
    </form>
</main>
<jsp:include page="/WEB-INF/jsp/static/bottom.jsp"/>
</body>
</html>