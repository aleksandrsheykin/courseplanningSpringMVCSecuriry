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
                <li class="active"><a href="registration">Registration</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>

<div class="container">

    <div class="starter-template">
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">

                <% String errorMsg = (String) request.getAttribute("errorMsg");
                    boolean firstNameError = false;
                    boolean lastNameError = false;
                    boolean limitError = false;
                    boolean mailError = false; %>

                <c:if test="${not empty firstName}">
                    <%mailError = true;%>
                </c:if>

                <form class="form" action="/planning/registration" method="post">
                    <h2 class="form-signin-heading">Registration</h2>

                    <div class="form-group <%=firstNameError?"has-error":"" %>">
                        <input type="text" class="form-control" placeholder="firstName" name="firstName" pattern=".{2,128}" required autofocus
                            <c:if test="${not empty firstName}">
                                    value="${firstName}"
                            </c:if> >
                    </div>

                    <div class="form-group <%=lastNameError?"has-error":"" %>">
                        <input type="text" class="form-control" placeholder="lastName" name="lastName" pattern=".{2,128}" required
                            <c:if test="${not empty lastName}">
                                   value="${lastName}"
                            </c:if> >
                    </div>

                    <div class="form-group <%=limitError?"has-error":"" %>">
                        <input type="number" class="form-control" placeholder="limit" name="limit" min="0" max="2147483646" step="1000" value="1000" required
                            <c:if test="${not empty limit}">
                                   value="${limit}"
                            </c:if> >
                    </div>

                    <div class="form-group <%=mailError?"has-error":"" %>">
                        <input type="email" class="form-control" placeholder="Mail" name="mail" pattern=".{4,128}" required
                            <c:if test="${not empty mail}">
                                   value="${mail}"
                            </c:if> >
                    </div>

                    <div class="form-group">
                        <input type="password" class="form-control" placeholder="Password" name="password" pattern=".{8,25}" required>
                    </div>

                    <div class="form-group">
                        <button class="btn btn-lg btn-success btn-block" type="submit" name="registration">Registration</button>
                    </div>
                </form>

                <c:if test="${not empty error.msg}">
                    <div class="alert alert-danger">${error.msg}</div>
                </c:if>

            </div>
            <div class="col-md-4"></div>
        </div>
    </div>
</div>

</body>
</html>