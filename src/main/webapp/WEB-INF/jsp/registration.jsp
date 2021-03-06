<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheet/frame.css"/>">
</head>
<body>
<div class="page">
    <div class="form">
        <form method="post" enctype="multipart/form-data">
            <input type="hidden" name="method" value="REGISTER">
            <table align="center">
                <tr>
                    <td>Логин:</td>
                    <td>
                        <%--@elvariable id="user" type="com.github.company.dao.entity.User"--%>
                        <input title="Логин" type="text" name="login" placeholder="Придумайте логин"
                               value="${user.login}" size="20" required autofocus></td>
                </tr>
                <tr>
                    <td>Пароль:</td>
                    <td><input title="Пароль" type="password" name="password" placeholder="Придумайте пароль"
                               size="20" required></td>
                </tr>
                <tr>
                    <td>Фамилия:</td>
                    <td>
                        <input title="Фамилия" type="text" name="surname" placeholder="Ваша фамилию"
                               value="${user.surname}" size="20" required></td>
                </tr>
                <tr>
                    <td>Имя:</td>
                    <td>
                        <input title="Имя" type="text" name="firstName" placeholder="Ваше имя" size="20"
                               value="${user.firstName}" required></td>
                </tr>
                <tr>
                    <td>Отчество:</td>
                    <td>
                        <input title="Отчество" type="text" name="middleName" placeholder="Ваше отчество"
                               value="${user.middleName}" size="20" required></td>
                </tr>
                <tr>
                    <td>Телефон:</td>
                    <td><input title="Телефон" type="text" name="phone" pattern="380[0-9]{9}"
                               <c:if test="${user.phone==null}">value="380"</c:if>
                               <c:if test="${user.phone!=null}">value="${user.phone}"</c:if>
                               required></td>
                </tr>
                <tr>
                    <td>Фото:</td>
                    <td><input type="file" name="avatar" accept="image/jpeg,image/png,image/gif"></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">Данное поле можно оставить пустым
                        Максимальный размер файла 1024КБ
                    </td>
                </tr>
            </table>
            <br>
            <button>зарегистрироваться</button>
        </form>
        <%--@elvariable id="message" type="java.lang.String"--%>
        <c:if test="${message!=null}">
            <p class="server-answer">${message}</p><br>
        </c:if>
        <br>
        <form class="login-page" method="post">
            <input type="hidden" name="method" value="GO_LOGIN">
            <button>вход</button>
        </form>
    </div>
</div>
</body>
</html>