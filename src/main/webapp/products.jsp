<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap/css/starter-template.css" rel="stylesheet">
    <script type="text/javascript" src="bootstrap/jquery-3.2.1.min.js"></script>
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
                <li><a href="admin">AdminPanel</a></li>
                <li class="active"><a href="products">Products</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">${user.firstName} ${user.lastName}</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>

<script type="text/javascript">
    $( document ).ready(function() {
        $("#editFormContainer").hide();
    });

    function activateEditForm(id) {
        $("#productDescEdit").val($("#productDescList"+id).text());
        $("#productNameEdit").val($("#productNameList"+id).text());
        $("#productIdEdit").val(id);

        $("#editFormContainer").show();
        $("#addFormContainer").hide();
    }
</script>

<div class="container">

    <div class="starter-template">
        <div class="row">
            <div class="col-md-8">
                <h2>Products</h2>
            </div>
        </div>

        <div class="row">
            <div class="col-md-8">
                <table class="table table-striped">
                    <tr>
                        <th>name</th>
                        <th>description</th>
                        <th></th><th></th>
                    </tr>
                    <c:forEach items="${requestScope.productList}" var="product">
                        <tr>
                            <td id="productNameList${product.idProduct}"><c:out value="${product.name}"></c:out></td>
                            <td id="productDescList${product.idProduct}"><c:out value="${product.description}"></c:out></td>
                            <td><a href="javascript://" onclick="activateEditForm(${product.idProduct}); return false;">edit</a></td>
                            <td>
                                <form method="POST" name="delete" id="deleteFormId_${product.idProduct}">
                                    <input type="hidden" value="delete" name="action"/>
                                    <input type="hidden" value="${product.idProduct}" name="id"/>
                                </form>
                                <a href="javascript://" onClick="document.getElementById('deleteFormId_${product.idProduct}').submit(); return false;">
                                    delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <c:if test="${not empty error.msg}">
                    <div class="alert alert-danger alert-dismissable">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                            ${error.msg}
                    </div>
                </c:if>

            </div>
            <div class="col-md-4">

                <div class="panel panel-success" id="addFormContainer">
                    <div class="panel-heading">Add new product</div>
                    <div class="panel-body">

                        <form class="form" method="post">
                            <input type="hidden" name="action" value="add" />
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="name" name="name" pattern=".{2,128}" required />
                            </div>
                            <div class="form-group">
                                <textarea class="form-control" rows="3" placeholder="description" name="description" pattern=".{2,254}"></textarea>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-lg btn-success btn-block" type="submit">save</button>
                            </div>
                        </form>

                    </div>
                </div>

                <div class="panel panel-info" id="editFormContainer" style="display: none;">
                    <div class="panel-heading">Edit product</div>
                    <div class="panel-body">

                        <form class="form" method="post">
                            <input type="hidden" name="action" value="edit" />
                            <input type="hidden" name="id" value="" id="productIdEdit" />
                            <div class="form-group">
                                <input type="text" id="productNameEdit" class="form-control" placeholder="name" name="name" pattern=".{2,128}" required />
                            </div>
                            <div class="form-group">
                                <textarea class="form-control" id="productDescEdit" rows="3" placeholder="description" name="description" pattern=".{2,254}"></textarea>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-lg btn-info btn-block" id="editSave" type="submit">save</button>
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


