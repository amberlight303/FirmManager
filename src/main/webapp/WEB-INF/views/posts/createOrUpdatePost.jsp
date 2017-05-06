<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="firmManager" tagdir="/WEB-INF/tags" %>

<firmManager:layout activeMenuItem="menu-posts" title="${post['new']?'New Post':'Post'}">
    <div class="content-inner">
        <div class="content-post">
            <h2 align="center">
                <c:if test="${post['new']}">New </c:if> Post
            </h2>
            <c:if test="${not post['new']}">
                <c:choose>
                    <c:when test="${post.imageFileName == null}">
                        <img class="post-image" src="/resources/images/noImage.png"><br>
                    </c:when>
                    <c:otherwise>
                        <img class="post-image" src="/uploads/postsImages/${post.imageFileName}"><br>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <form:form modelAttribute="post"  method="post"
                       class="form-horizontal" enctype="multipart/form-data">
                <form:input type="hidden" path="id" value="${post.id}"/>
                <form:input type="hidden" path="oldPostImgName" value="${post.imageFileName}"/>
                <p>Title:</p>
                <form:textarea cssClass="js-textarea-minr1" path="title"/><br>
                <p>Main image:</p>
                <form:input cssClass="post-input-img" type="file" path="image" accept="image/jpeg,image/png,image/gif"/><br>
                <p>Content:</p>
                <form:textarea cssClass="js-textarea-minr10" path="content"/><br>
                <p>Content preview:</p>
                <form:textarea cssClass="js-textarea-minr10" path="contentPreview"/><br>
                <div class="upd-post-btn-wrapper">
                    <c:choose>
                        <c:when test="${post['new']}">
                            <button class="btn btn-default upd-post-btn" type="submit">Add Post</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default upd-post-btn" type="submit">Update Post</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form:form>
        </div>
    </div>
</firmManager:layout>