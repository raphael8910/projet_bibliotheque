<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Connexion Bibliothecaire</title>
</head>
<body>
    <h2>Connexion Bibliothecaire</h2>
    <form:form modelAttribute="bibliothecaire" method="post" action="/login">
        <div>
            <label>Pseudo:</label>
            <form:input path="pseudo"/>
        </div>
        <div>
            <label>Mot de passe:</label>
            <form:password path="motdepasse"/>
        </div>
        <div>
            <input type="submit" value="Se connecter"/>
        </div>
    </form:form>
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>
</body>
</html>