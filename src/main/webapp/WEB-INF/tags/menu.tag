<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="contextPath" required="true" %>
<div class="menu-wrapper">
    <nav class="navbar navbar-default navbar-fixed-top"  role="navigation">
        <div class="custom-nav" >
            <div class="container">
                <div class="navbar-header" >
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#responsive_menu">
                        <span class="sr-only">Menu</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a href="${contextPath}/"><img class="navbar-logo" src="${contextPath}/resources/images/logo.png"/></a>
                </div>
                <div class="collapse navbar-collapse"  id="responsive_menu">
                    <ul class="nav navbar-nav main-nav">
                        <li id="menu-posts"><a href="${contextPath}/">POSTS</a></li>
                        <li id="menu-projects"><a href="${contextPath}/projects">PROJECTS</a></li>
                        <li id="menu-employees"><a href="${contextPath}/employees">EMPLOYEES</a></li>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <li id="menu-admin"><a href="${contextPath}/admin">ADMIN</a></li>
                        </sec:authorize>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a id="drop1" href="#" class="dropdown-toggle" data-toggle="dropdown">
                                ${currentUser.username}
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <c:choose>
                                        <c:when test="${currentUser.employee == null}">
                                            <a href="${contextPath}/users/${currentUser.id}">PROFILE</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${contextPath}/employees/${currentUser.employee.id}">PROFILE</a>
                                        </c:otherwise>
                                    </c:choose>
                                </li>
                                <li class="divider">
                                </li>
                                <li>
                                    <c:url var="logoutUrl" value="/logout"/>
                                    <form:form action="${logoutUrl}"
                                               method="post">
                                        <input type="submit" class="logout"
                                               value="LOG OUT" />
                                        <input type="hidden"
                                               name="${_csrf.parameterName}"
                                               value="${_csrf.token}"/>
                                    </form:form>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</div>