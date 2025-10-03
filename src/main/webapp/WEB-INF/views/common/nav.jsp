<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="root" value="${pageContext.request.contextPath }" />
<nav>
    <ul>
        <li><strong><a href="${pageContext.request.contextPath}/">EnjoyTrip</a></strong></li>
    </ul>
    <ul>
        <li><a href="${root}/attraction?action=list">지역별여행지</a></li>
        <li><a href="${root}/plan?action=view">나의여행계획</a></li>
        <li><a href="${root}/hotplace?action=mvlist">핫플레이스</a></li>
        <li><a href="${root}/board?action=list&category=free">자유게시판</a></li>
        <li><a href="${root}/board?action=list&category=notice">공지사항</a></li>

        <c:if test="${empty userInfo}">
            <%-- 로그인 안 된 경우 --%>
            <li><a href="${root}/user?action=login-form" role="button" class="secondary">로그인</a></li>
            <li><a href="${root}/user?action=register-form" role="button">회원가입</a></li>
        </c:if>

        <c:if test="${not empty userInfo}">
            <%-- 로그인 된 경우 --%>
            <li><a href="${root}/user?action=mypage&userNo=${userInfo.userNo}">${userInfo.userName}님</a></li>
            <li><a href="${root}/user?action=logout&userNo=${userInfo.userNo}" role="button" class="secondary">로그아웃</a></li>
        </c:if>
    </ul>
</nav>