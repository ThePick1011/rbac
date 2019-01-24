<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="javascript:void(0)">${principal.username}</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse"
            aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/index.jsp">首页</a>
            </li>
            <c:forEach items="${pModuleSet}" var="pm">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="javascript:void(0)" id="dropdown" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">${pm.name}
                    </a>
                    <div class="dropdown-menu" aria-labelledby="dropdown">
                        <c:forEach items="${moduleList}" var="m">
                            <c:if test="${m.pid == pm.id}">
                                <a class="dropdown-item" href="${root}${m.code}">${m.name}</a>
                            </c:if>
                        </c:forEach>

                    </div>
                </li>
            </c:forEach>
            <li class="nav-item">
                <form id="logout" action="/logout" method="post">
                    <a class="nav-link" href="javascript:document.getElementById('logout').submit()">注销</a>
                </form>
            </li>
        </ul>
        <form class="form-inline mt-2 mt-md-0">
            <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>