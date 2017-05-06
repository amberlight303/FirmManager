<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="firmManager" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
    <firmManager:htmlHeader title="Posts" enableMetaCsrf="true"/>
</head>
<body id="menu-posts">
<div class="wrapper">
    <firmManager:bodyHeader/>
    <div class="container content-container">
        <div class="content clearfix">
            <div class="content-inner">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="btn btn-default add-post-btn" href="/admin/posts/addPost">Add New Post</a>
                </sec:authorize>
                <c:forEach items="${posts}" var="post">
                    <div class="content-post">
                        <div class="post-title-time">
                            <div class="break-words">
                                <a href="/posts/${post.id}">${post.title}</a>
                            </div>
                            <div class="time">
                                <fmt:formatDate value="${post.postDate}" pattern="dd.MM.yyyy, HH:mm"/> <br>
                            </div>
                        </div>
                        <c:choose>
                            <c:when test="${post.imageFileName == null}">
                                <a href="/posts/${post.id}">
                                    <img class="post-image" src="/resources/images/noImage.png">
                                </a>
                                <br>
                            </c:when>
                            <c:otherwise>
                                <a href="/posts/${post.id}">
                                    <img class="post-image" src="/uploads/postsImages/${post.imageFileName}">
                                </a>
                                <br>
                            </c:otherwise>
                        </c:choose>
                        <div class="post-content break-words">
                            <p>
                                    ${post.contentPreview}&nbsp;
                                <a href="/posts/${post.id}">read more</a>
                            </p>
                        </div>
                        <div class="like-div clearfix">
                            <p id="post-${post.id}-likes" class="post-likes">
                                <c:if test="${post.amountOfLikes != 0}">
                                    ${post.amountOfLikes}
                                </c:if>
                            </p>
                            <form class="like-form" method="post" action="/posts/${post.id}/like">
                                <button type="submit" class="btn btn-default like-submit-btn">
                                    <p class="like-btn-p">Like <span class="glyphicon glyphicon-heart"></span></p>
                                </button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
                <br>

                <div class="prev-next-btns clearfix">
                    <c:choose>
                        <c:when test="${pagesOnTheLeft > 0}">
                            <a class="btn btn-default previous-btn" href="/page/${currentPage - 1}">Previous</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-default previous-btn disabled">Previous</a>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${pagesOnTheRight > 0}">
                            <a class="btn btn-default next-btn" href="/page/${currentPage + 1}">Next</a>
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
                                <a class="page-link prev-post" href="/page/${currentPage - 1}">Previous</a>
                            </li>
                            <li class="page-item"><a class="page-link" href="/page/1">1</a></li>
                            <li  class="page-item"><a class="page-link" href="/page/2">2</a></li>
                            <li class="page-item"><a class="page-link" href="/page/3">3</a></li>
                            <li class="page-item disabled"><a class="page-link">${currentPage}</a></li>
                        </c:if>
                        <c:if test="${pagesOnTheLeft==2}">
                            <li class="page-item">
                                <a class="page-link prev-post" href="/page/${currentPage - 1}">Previous</a>
                            </li>
                            <li class="page-item"><a class="page-link" href="/page/1">1</a></li>
                            <li class="page-item"><a class="page-link" href="/page/2">2</a></li>
                            <li class="page-item disabled"><a class="page-link">${currentPage}</a></li>
                        </c:if>
                        <c:if test="${pagesOnTheLeft==1}">
                            <li class="page-item">
                                <a class="page-link prev-post" href="/page/${currentPage - 1}">Previous</a>
                            </li>
                            <li class="page-item"><a class="page-link" href="/page/1">1</a></li>
                            <li class="page-item disabled"><a class="page-link">${currentPage}</a></li>
                        </c:if>
                        <c:if test="${pagesOnTheLeft==0}">
                            <li class="page-item disabled"><a class="page-link">${currentPage}</a></li>
                        </c:if>
                        <c:if test="${pagesOnTheLeft>3}">
                            <li class="page-item">
                                <a class="page-link prev-post" href="/page/${currentPage - 1}">Previous</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="/page/1">1</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link etc">...</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link  pre-previous" href="/page/${currentPage - 2}">${currentPage - 2}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="/page/${currentPage - 1}">${currentPage - 1}</a>
                            </li>
                            <li class="page-item disabled">
                                <a class="page-link">${currentPage}</a>
                            </li>
                        </c:if>
                        <c:if test="${pagesOnTheRight==3}">
                            <li class="page-item">
                                <a class="page-link" href="/page/${numberOfPages - 2}">${numberOfPages - 2}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="/page/${numberOfPages - 1}">${numberOfPages - 1}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="/page/${numberOfPages}">${numberOfPages}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link next-post" href="/page/${currentPage + 1}">Next</a>
                            </li>
                        </c:if>
                        <c:if test="${pagesOnTheRight==2}">
                            <li class="page-item">
                                <a class="page-link" href="/page/${numberOfPages - 1}">${numberOfPages - 1}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="/page/${numberOfPages}">${numberOfPages}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link next-post" href="/page/${currentPage + 1}">Next</a>
                            </li>
                        </c:if>
                        <c:if test="${pagesOnTheRight==1}">
                            <li class="page-item">
                                <a class="page-link" href="/page/${numberOfPages}">${numberOfPages}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link  next-post" href="/page/${currentPage + 1}">Next</a>
                            </li>
                        </c:if>
                        <c:if test="${pagesOnTheRight>3}">
                            <li class="page-item">
                                <a class="page-link" href="/page/${currentPage + 1}">${currentPage + 1}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link post-next" href="/page/${currentPage + 2}">${currentPage + 2}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link etc">...</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="/page/${numberOfPages}">${numberOfPages}</a>
                            </li>
                            <li class="page-item">
                                <a class="page-link next-post" href="/page/${currentPage + 1}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
    <firmManager:footer/>
</div>
<firmManager:footer_scripts/>
<script type="text/javascript" src="/resources/js/likes-ajax.js"></script>
</body>
</html>

