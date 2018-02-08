<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/register.css">
</head>
<body>
<div class="page">
    <div class="form">
        <form id="add" method="post" enctype="multipart/form-data">
            <input type="hidden" name="method" value="REGISTER">
            <table align="center">
                <tr>
                    <td>Логин:</td>
                    <td><input type="text" name="user_login" placeholder="Придумайте логин"
                               value="${login}" size="20" required autofocus></td>
                </tr>
                <tr>
                    <td>Пароль:</td>
                    <td><input type="password" name="user_password" placeholder="Придумайте пароль"
                               size="20" required></td>
                </tr>
                <tr>
                    <td>Фамилия:</td>
                    <td><input type="text" name="user_surname" placeholder="Ваша фамилию"
                               value="${surname}" size="20" required></td>
                </tr>
                <tr>
                    <td>Имя:</td>
                    <td><input type="text" name="user_firstName" placeholder="Ваше имя" size="20"
                               value="${firstName}" required></td>
                </tr>
                <tr>
                    <td>Отчество:</td>
                    <td><input type="text" name="user_secondName" placeholder="Ваше отчество"
                               value="${secondName}" size="20" required></td>
                </tr>
                <tr>
                    <td>Телефон:</td>
                    <td><input style="" type="text" name="user_phoneNumber" pattern="[0-9]{12}"
                               <c:if test="${phoneNumber==null}">value="380"</c:if>
                               <c:if test="${phoneNumber!=null}">value="${phoneNumber}"</c:if>
                               size="20" required></td>
                </tr>
                <tr>
                    <td>Фото:</td>
                    <td><input type="file" name="user_avatar" accept="image/jpeg,image/png,image/gif"></td>
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
        <c:if test="${message!=null}">
            <p class="message">${message}</p><br>
        </c:if>
        <form class="login-page" method="post">
            <input type="hidden" name="method" value="GO_LOGIN">
            <button>вход</button>
        </form>
    </div>
</div>
</body>
</html>