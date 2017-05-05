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
                <li class="active"><a href="main">Main</a></li>

                <c:if test="${user.isAdmin}">
                    <li><a href="admin">AdminPanel</a></li>
                </c:if>
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

                <table class="table table-striped">
                    <tr>
                        <th>plan_id</th>
                        <th>datePlan</th>
                        <th>quantity</th>
                        <th>cost</th>
                        <th>userId</th>
                        <th>productId</th>
                        <th></th><th></th>
                    </tr>
                    <c:forEach items="${requestScope.planList}" var="plan">
                        <tr>
                            <td><c:out value="${plan.id_plan}"></c:out></td>
                            <td><c:out value="${plan.datePlan}"></c:out></td>
                            <td><c:out value="${plan.quantity}"></c:out></td>
                            <td><c:out value="${plan.cost}"></c:out></td>
                            <td><c:out value="${plan.userId}"></c:out></td>
                            <td><c:out value="${plan.productId}"></c:out></td>
                            <td>
                                <a href="edit?id=<c:out value="${plan.id_plan}"></c:out>">edit</a>
                            </td>
                            <td>
                                <form method="POST" name="delete" id="deleteFormId_${plan.id_plan}">
                                    <input type="hidden" value="${plan.id_plan}" name="deleteId"/>
                                </form>
                                <a href="javascript://" onClick="document.getElementById('deleteFormId_${plan.id_plan}').submit(); return false;">
                                    delete
                                </a>
                            </td>
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


