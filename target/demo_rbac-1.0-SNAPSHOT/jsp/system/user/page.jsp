<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/img/favicon.ico">

    <title>Fixed top navbar example for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

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
        <li class="breadcrumb-item"><a href="/index">首页</a></li>
        <li class="breadcrumb-item">系统管理</li>
        <li class="breadcrumb-item active">用户管理</li>
    </ol>
    <div class="row">
        <div class="col-3">
            <ul id="orgTree" class="ztree"></ul>
        </div>
        <div class="col-9">
            <table id="userTable" class="table">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>登录名</th>
                    <th>用户角色</th>
                    <th>数据权限</th>
                    <th>修改</th>
                    <th>删除</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td><a href="/system/user/tomodifyrole?userId=${user.id}">编辑角色</a></td>
                        <td><a href="#">数据权限</a></td>
                        <td><a href="/system/user/toupdate?id=${user.id}">修改</a></td>
                        <td><a href="javascript:confim('确认删除吗？')?location.href='/system/user/delete?id=${user.id}':void(0)">删除</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="row">
        <div class="col-3">
        </div>
        <div class="col-6">
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <c:if test="${page>=1}">
                        <li>
                            <a class="page-link" href="/system/user/page?page=1">首页</a>
                        </li>
                    </c:if>
                    <c:if test="${page>1}">
                        <li>
                            <a class="page-link" href="/system/user/page?page=${page-1}"><<</a>
                        </li>
                    </c:if>
                    <c:if test="${page==1}">
                        <li>
                            <a class="page-link" href="#"><<</a>
                        </li>
                    </c:if>
                    <c:forEach begin="1" end="${total}" var="i">
                        <c:if test = "${i == page}">
                            <li class="page-item active"><a class="page-link" href="/system/user/page?page=${i}">${i}</a></li>
                        </c:if>
                        <c:if test = "${i != page}">
                            <li class="page-item"><a class="page-link" href="/system/user/page?page=${i}">${i}</a></li>
                        </c:if>
                    </c:forEach>
                    <c:if test="${page<total}">
                        <li>
                            <a class="page-link" href="/system/user/page?page=${page+1}">>></a>
                        </li>
                    </c:if>
                    <c:if test="${page==total}">
                        <li>
                            <a class="page-link" href="#">>></a>
                        </li>
                    </c:if>
                    <c:if test="${page<=total}">
                        <li>
                            <a class="page-link" href="/system/user/page?page=${total}">尾页</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
        <div class="col-3" style="text-align:right">
            <a class="btn btn-info" href="/system/user/toadd" role="button">新增用户</a>
        </div>
    </div>
</div>


<!-- Bootstrap core JavaScript
 ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/js/jquery-3.2.1.slim.min.js"></script>
<script src="/js/popper.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug
<script src="../../../../assets/js/ie10-viewport-bug-workaround.js"></script>
-->
</body>
</html>