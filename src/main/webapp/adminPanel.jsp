<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap/css/starter-template.css" rel="stylesheet">
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index">Planning</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="index">Index</a></li>
                <li><a href="main">Main</a></li>
                <li><a href="login">Login</a></li>
                <li><a href="registration">Registration</a></li>
                <li class="active"><a href="admin">AdminPanel</a></li>
                <li><a href="products">Products</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">${user.firstName} ${user.lastName}</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>

<div class="container">

    <div class="starter-template">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <h2>AdminPanel</h2>
                <h3>UserList</h3>
                <table class="table table-striped">
                    <tr>
                        <th>id_user</th>
                        <th>firstName</th>
                        <th>lastName</th>
                        <th>mail</th>
                        <th>limit</th>
                        <th>isAdmin</th>
                        <th>isBlocked</th>
                    </tr>
                    <c:forEach items="${requestScope.userList}" var="user">
                        <tr>
                            <td><c:out value="${user.idUser}"></c:out></td>
                            <td><c:out value="${user.firstName}"></c:out></td>
                            <td><c:out value="${user.lastName}"></c:out></td>
                            <td><c:out value="${user.mail}"></c:out></td>
                            <td><c:out value="${user.limit}"></c:out></td>
                            <c:if test="${user.isAdmin}">
                                <td><span class="glyphicon glyphicon-ok"></span></td>
                            </c:if>
                            <c:if test="${not user.isAdmin}">
                                <td><span class="glyphicon glyphicon-remove"></span></td>
                            </c:if>
                            <c:if test="${user.isBlocked}">
                                <td><span class="glyphicon glyphicon-ok"></span></td>
                            </c:if>
                            <c:if test="${not user.isBlocked}">
                                <td><span class="glyphicon glyphicon-remove"></span></td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="col-md-2"></div>
        </div>
    </div>
</div>

</body>
</html>


