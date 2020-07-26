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
    <firmManager:htmlHeader title="Post" contextPath="${contextPath}"/>
</head>
<body id="menu-posts">
<div class="wrapper">
    <firmManager:bodyHeader contextPath="${contextPath}"/>
    <div class="container content-container">
        <div class="content clearfix">
            <div class="content-inner">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="btn btn-default post-btn"
                       href="${contextPath}/admin/posts/${post.id}/editPost">Edit post</a>
                    <form:form method="post" action="${contextPath}/admin/posts/${post.id}/deletePost">
                        <button class="btn btn-default post-btn" type="submit">
                            Delete post
                        </button>
                    </form:form>
                </sec:authorize>
                <div class="content-post">
                    <div class="post-title-time">
                        <div class="break-words">
                            <h4 align="center">${post.title}</h4>
                        </div>
                        <div class="time">
                            <c:choose>
                                <c:when test="${post.postUpdateDate != null}">
                                    <c:out value="Updated: "/>
                                    <fmt:formatDate value="${post.postUpdateDate}" pattern="dd.MM.yyyy, HH:mm"/>
                                    <br>
                                    <c:out value="Published: "/>
                                    <fmt:formatDate value="${post.postDate}" pattern="dd.MM.yyyy, HH:mm"/> <br>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="Published: "/>
                                    <fmt:formatDate value="${post.postDate}" pattern="dd.MM.yyyy, HH:mm"/> <br>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${post.imageFileName == null}">
                            <img class="post-image" src="${contextPath}/resources/images/noImage.png"><br>
                        </c:when>
                        <c:otherwise>
                            <img class="post-image" src="${contextPath}/uploads/postsImages/${post.imageFileName}"><br>
                        </c:otherwise>
                    </c:choose>
                    <div class="post-content break-words">
                        <p>
                            ${post.content}
                        </p>
                    </div>
                    <br>
                    <h2 align="center">Write your comment:</h2>
                    <spring:url value="${contextPath}/posts/{postId}/addComment" var="addCommentUrl">
                        <spring:param name="postId" value="${post.id}"/>
                    </spring:url>
                    <form:form id="comment-form" modelAttribute="comment" method="post" action="${addCommentUrl}"
                               class="form-horizontal">
                        <input type="hidden" name="id" value="${comment.id}"/>
                        <textarea id="comment-textarea" class="js-textarea-minr1-maxr10" name="text"></textarea>
                        <button style="display: block; margin: 10px auto;" class="btn btn-default" type="submit">Add Comment</button>
                    </form:form>
                    <div class="comments">
                        <c:forEach items="${post.comments}" var="comment">
                            <div class="comment-author-time">
                                <a href="${contextPath}/users/${comment.userAuthor.id}">
                                        ${comment.userAuthor.firstName}&nbsp;${comment.userAuthor.lastName}
                                </a>
                                <div class="time">
                                    <fmt:formatDate value="${comment.commentDate}" pattern="dd.MM.yyyy, HH:mm"/>
                                </div>
                            </div>
                            <div class="comment-text-wrapper">
                                <p class="comment-text">${comment.text}</p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <firmManager:footer/>
</div>
<script type="text/javascript">var contextPath = "${contextPath}";</script>
<firmManager:footer_scripts contextPath="${contextPath}"/>
<script src="${contextPath}/resources/js/libs/sockjs-1.0.2.min.js"></script>
<script src="${contextPath}/resources/js/libs/stomp.js"></script>
<script src="${contextPath}/resources/js/comments-ws.js"></script>
</body>
</html>