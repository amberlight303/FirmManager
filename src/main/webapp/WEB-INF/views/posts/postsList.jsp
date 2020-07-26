<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="firmManager" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <firmManager:htmlHeader title="Posts" enableMetaCsrf="true"
                            contextPath="${contextPath}"/>
</head>
<body id="menu-posts">
<div class="wrapper">
    <firmManager:bodyHeader contextPath="${contextPath}"/>
    <div class="container content-container">
        <div class="content clearfix">
            <div class="content-inner">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="btn btn-default add-post-btn" href="${contextPath}/admin/posts/addPost">Add New Post</a> <br>
                </sec:authorize>
                <c:forEach items="${posts}" var="post">
                    <div class="content-post">
                        <div class="post-title-time">
                            <div class="break-words">
                                <a href="${contextPath}/posts/${post.id}">${post.title}</a>
                            </div>
                            <div class="time">
                                <c:choose>
                                    <c:when test="${post.postUpdateDate != null}">
                                        <c:out value="Updated: "/>
                                        <fmt:formatDate value="${post.postUpdateDate}" pattern="dd.MM.yyyy, HH:mm"/>
                                        <br>
                                        <c:out value="Published: "/>
                                        <fmt:formatDate value="${post.postDate}" pattern="dd.MM.yyyy, HH:mm"/> <br>                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="Published: "/>
                                        <fmt:formatDate value="${post.postDate}" pattern="dd.MM.yyyy, HH:mm"/> <br>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <c:choose>
                            <c:when test="${post.imageFileName == null}">
                                <a href="${contextPath}/posts/${post.id}">
                                    <img class="post-image" src="${contextPath}/resources/images/noImage.png">
                                </a>
                                <br>
                            </c:when>
                            <c:otherwise>
                                <a href="${contextPath}/posts/${post.id}">
                                    <img class="post-image" src="${contextPath}/uploads/postsImages/${post.imageFileName}">
                                </a>
                                <br>
                            </c:otherwise>
                        </c:choose>
                        <div class="post-content break-words">
                            <p>
                                    ${post.contentPreview}&nbsp;
                                <a href="${contextPath}/posts/${post.id}">read more</a>
                            </p>
                        </div>
                        <div class="like-div clearfix">
                            <p id="post-${post.id}-likes" class="post-likes">
                                <c:if test="${post.amountOfLikes != 0}">
                                    ${post.amountOfLikes}
                                </c:if>
                            </p>
                            <form class="like-form" method="post"
                                  action="${contextPath}/posts/${post.id}/like">
                                <button type="submit" class="btn btn-default like-submit-btn">
                                    <p class="like-btn-p"><span class="glyphicon glyphicon-heart"></span></p>
                                </button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
                <br>

                <div class="prev-next-btns clearfix">
                    <c:choose>
                        <c:when test="${pagesOnTheLeft > 0}">
                            <a class="btn btn-default previous-btn"
                               href="${contextPath}/page/${currentPage - 1}">Previous</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-default previous-btn disabled">Previous</a>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${pagesOnTheRight > 0}">
                            <a class="btn btn-default next-btn"
                               href="${contextPath}/page/${currentPage + 1}">Next</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-default next-btn disabled">Next</a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <nav class="nav-pagination">
                    <ul class="pagination">
                        <c:if test="${pagesOnTheLeft==3}">
                            <li class="page-item">
                                <a class="page-link prev-post"
                                   href="${contextPath}/page/${currentPage - 1}">Previous</a>
                            </li>
                            <li class="page-item"><a class="page-link"
                                                     href="${contextPath}/page/1">1</a></li>
                            <li  class="page-item"><a class="page-link"
                                                      href="${contextPath}/page/2">2</a></li>
                            <li class="page-item"><a class="page-link"
                                                     href="${contextPath}/page/3">3</a></li>
                            <li class="page-item disabled"><a class="page-link">${currentPage}</a></li>
                        </c:if>
                        <c:if test="${pagesOnTheLeft==2}">
                            <li class="page-item">
                                <a class="page-link prev-post" href="${contextPath}/page/${currentPage - 1}">Previous</a>
                            </li>
                            <li class="page-item"><a class="page-link"
                                                     href="${contextPath}/page/1">1</a></li>
                            <li class="page-item"><a class="page-link"
                                                     href="${contextPath}/page/2">2</a></li>
                            <li class="page-item disabled"><a class="page-link">${currentPage}</a></li>
                        </c:if>
                        <c:if test="${pagesOnTheLeft==1}">
                            <li class="page-item">
                                <a class="page-link prev-post"
                                   href="${contextPath}/page/${currentPage - 1}">Previous</a>
                            </li>
                            <li class="page-item"><a class="page-link"
                                                     href="${contextPath}/page/1">1</a></li>
                            <li class="page-item disabled"><a class="page-link">${currentPage}</a></li>
                        </c:if>
                        <c:if test="${pagesOnTheLeft==0}">
                            <li class="page-item disabled"><a class="page-link">${currentPage}</a></li>
                        </c:if>
                        <c:if test="${pagesOnTheLeft>3}">
                            <li class="page-item">
                                <a class="page-link prev-post"
                                   href="${contextPath}/page/${currentPage - 1}">Previous</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="${contextPath}/page/1">1</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link etc">...</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link  pre-previous"
                                   href="${contextPath}/page/${currentPage - 2}">${currentPage - 2}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="${contextPath}/page/${currentPage - 1}">${currentPage - 1}</a>
                            </li>
                            <li class="page-item disabled">
                                <a class="page-link">${currentPage}</a>
                            </li>
                        </c:if>
                        <c:if test="${pagesOnTheRight==3}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${contextPath}/page/${numberOfPages - 2}">${numberOfPages - 2}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link"
                                   href="${contextPath}/page/${numberOfPages - 1}">${numberOfPages - 1}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link"
                                   href="${contextPath}/page/${numberOfPages}">${numberOfPages}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link next-post"
                                   href="${contextPath}/page/${currentPage + 1}">Next</a>
                            </li>
                        </c:if>
                        <c:if test="${pagesOnTheRight==2}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${contextPath}/page/${numberOfPages - 1}">${numberOfPages - 1}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link"
                                   href="${contextPath}/page/${numberOfPages}">${numberOfPages}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link next-post"
                                   href="${contextPath}/page/${currentPage + 1}">Next</a>
                            </li>
                        </c:if>
                        <c:if test="${pagesOnTheRight==1}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${contextPath}/page/${numberOfPages}">${numberOfPages}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link  next-post"
                                   href="${contextPath}/page/${currentPage + 1}">Next</a>
                            </li>
                        </c:if>
                        <c:if test="${pagesOnTheRight>3}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${contextPath}/page/${currentPage + 1}">${currentPage + 1}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link post-next"
                                   href="${contextPath}/page/${currentPage + 2}">${currentPage + 2}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link etc">...</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link"
                                   href="${contextPath}/page/${numberOfPages}">${numberOfPages}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link next-post"
                                   href="${contextPath}/page/${currentPage + 1}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
    <firmManager:footer/>
</div>
<script type="text/javascript">var contextPath = "${contextPath}";</script>
<firmManager:footer_scripts contextPath="${contextPath}"/>
<script type="text/javascript" src="${contextPath}/resources/js/likes-ajax.js"></script>
</body>
</html>

