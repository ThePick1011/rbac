<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="root"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${root}/img/favicon.ico">

    <title>Fixed top navbar example for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="${root}/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <style type="text/css">
        body {
            min-height: 75rem;
            padding-top: 4.5rem;
        }
    </style>
</head>

<body>
<%@include file="/jsp/nav.jsp" %>

<div class="container">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${root}/index">首页</a></li>
        <li class="breadcrumb-item">系统管理</li>
        <li class="breadcrumb-item active">修改模块</li>
    </ol>
    <div class="row">
        <div class="col-3">
            <ul id="orgTree" class="ztree"></ul>
        </div>
        <div class="col-9">
            <form action="${root}/system/role/modifymodule" method="post">
                <input type="hidden" name="roleId" value="${param.id}"/>
                <c:forEach items="${modules}" var="m">
                    <div class="form-check">
                        <a class="btn btn-info module" href="javascript:void(0)" role="button" data-check="true">${m.name}</a>
                        <div></div>
                        <c:forEach items="${m.children}" var="m2">
                            <c:set var="ck" value="${ids.contains(m2.id)}"/>
                            <div class="form-check form-check-inline">
                                <label class="form-check-label">
                                    <input name="moduleId" class="form-check-input" type="checkbox" value="${m2.id}" ${ck?'checked':''}> ${m2.name}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
                <button type="submit" class="btn btn-primary">提交</button>
            </form>
        </div>
    </div>
</div>


<!-- Bootstrap core JavaScript
 ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="${root}/js/jquery-3.2.1.slim.min.js"></script>
<script src="${root}/js/popper.min.js"></script>
<script src="${root}/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug
<script src="../../../../assets/js/ie10-viewport-bug-workaround.js"></script>
-->
<script>
    $(".module").click(function(){
        // 获取初始选中值
        var check = $(this).data("check");
        // 取反
        $(this).data("check", !check);
        // 按初始选中值勾选关联的复选框
        $(this).parent().find(":checkbox").prop("checked", check);
    });
</script>
</body>
</html>