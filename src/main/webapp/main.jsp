<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap/css/starter-template.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="bootstrap/js/bootstrap-select.js"></script>
    <link rel="stylesheet" href="bootstrap/css/bootstrap-select.css">
</head>
<script type="text/javascript">

    function activateEditForm(idPlan, idProduct) {
        $("#editIdPlan").val(idPlan);
        $("#editDate").val($("#planListDate"+idPlan).text());
        $("#editQuantity").val($("#planListQuantity"+idPlan).text());
        $("#editCost").val($("#planListCost"+idPlan).text());
        $("#editIdProduct").val(idProduct);
        $(".selectpicker").selectpicker("refresh");

        $("#editFormContainer").show();
        $("#addFormContainer").hide();
    }
</script>
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
                <li><a href="admin">AdminPanel</a></li>
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
            <div class="col-md-8">
                <h2>Planning shopping</h2>
            </div>
        </div>

        <div class="row">
            <div class="col-md-9">

                <table class="table table-striped">
                    <tr>
                        <th>date</th>
                        <th>product</th>
                        <th>quantity</th>
                        <th>cost</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <c:forEach items="${requestScope.planList}" var="plan">
                        <tr>
                            <td id="planListDate${plan.id_plan}"><c:out value="${plan.datePlan}"></c:out></td>
                            <td><c:out value="${plan.product.name}"></c:out></td>
                            <td id="planListQuantity${plan.id_plan}"><c:out value="${plan.quantity}"></c:out></td>
                            <td id="planListCost${plan.id_plan}"><c:out value="${plan.cost}"></c:out></td>
                            <td>
                                <a href="javascript://" onclick="activateEditForm(${plan.id_plan}, ${plan.product.idProduct}); return false;">edit</a>
                            </td>
                            <td>
                                <form method="POST" name="delete" action="maindel" id="deleteFormId_${plan.id_plan}">
                                    <input type="hidden" value="${plan.id_plan}" name="idPlan"/>
                                </form>
                                <a href="javascript://" onClick="document.getElementById('deleteFormId_${plan.id_plan}').submit(); return false;">
                                    delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

            </div>

            <div class="col-md-3">
                <div class="panel panel-success" id="addFormContainer">
                    <div class="panel-heading">Add new plan</div>
                    <div class="panel-body">
                        <form class="form" method="post" action="mainadd">
                            <div class="form-group">
                                <jsp:useBean id="now" class="java.util.Date" />
                                <fmt:formatDate var="dd" value="${now}" pattern="yyyy-MM-dd" />
                                <input type="date" class="form-control" name="date" value="${dd}" required />
                            </div>
                            <div class="form-group">
                                <select id="tokens" class="selectpicker" data-live-search="true" name="idProduct">
                                    <c:forEach items="${requestScope.productList}" var="product">
                                        <option data-tokens="first" value="${product.idProduct}" ><c:out value="${product.name}"></c:out></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <input type="number" class="form-control" name="quantity" placeholder="quantity" min="0" max="2147483646" required />
                            </div>
                            <div class="form-group">
                                <input type="number" class="form-control" name="cost" placeholder="cost" min="0" max="2147483646" required />
                            </div>
                            <div class="form-group">
                                <button class="btn btn-lg btn-success btn-block" type="submit">save</button>
                            </div>
                        </form>
                    </div>
                </div>


                <div class="panel panel-info" id="editFormContainer" style="display: none;">
                    <div class="panel-heading">Edit new plan</div>
                    <div class="panel-body">
                        <form class="form" method="post" action="mainedit">
                            <input type="hidden" value="" id="editIdPlan" name="idPlan">
                            <div class="form-group">
                                <input type="date" class="form-control" name="date" value="" id="editDate" required />
                            </div>
                            <div class="form-group">
                                <select class="selectpicker" data-live-search="true" name="idProduct" id="editIdProduct">
                                    <c:forEach items="${requestScope.productList}" var="product">
                                        <option data-tokens="first" value="${product.idProduct}" ><c:out value="${product.name}"></c:out></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <input type="number" class="form-control" name="quantity" placeholder="quantity" id="editQuantity" min="0" max="2147483646" required />
                            </div>
                            <div class="form-group">
                                <input type="number" class="form-control" name="cost" placeholder="cost" id="editCost" min="0" max="2147483646" required />
                            </div>
                            <div class="form-group">
                                <button class="btn btn-lg btn-info btn-block" type="submit">save</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>


