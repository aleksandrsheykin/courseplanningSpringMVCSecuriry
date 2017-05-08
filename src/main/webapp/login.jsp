<%@ page import="main.utils.ErrorManager" %>
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
                <li class="active"><a href="login">Login</a></li>
                <li><a href="registration">Registration</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>

<div class="container">

    <div class="starter-template">
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">

                <% Boolean showError = false; %>
                <c:if test="${not empty param.error}">
                    <% showError = true; %>
                </c:if>

                <form class="form" role="form" action="${pageContext.request.contextPath}/login" method="post">
                    <h2 class="form-signin-heading">Login</h2>
                    <div class="form-group <%=showError?"has-error":"" %>">
                        <input type="email" class="form-control" placeholder="Mail" name="login" required autofocus>
                    </div>
                    <div class="form-group <%=showError?"has-error":"" %>">
                        <input type="password" class="form-control" placeholder="Password" name="password" required>
                    </div>
                    <button class="btn btn-lg btn-primary btn-block" type="submit">login</button>
                </form>

                <c:if test="${not empty param.error}">
                    <div class="alert alert-danger">Failed login or password</div>
                </c:if>

            </div>
            <div class="col-md-4"></div>
        </div>
    </div>
</div>

</body>
</html>
