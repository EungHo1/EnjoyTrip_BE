<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav>
    <ul>
        <li><strong><a href="${pageContext.request.contextPath}/">EnjoyTrip</a></strong></li>
    </ul>
    <ul>
        <li><a href="#">지역별여행지</a></li>
        <li><a href="#">나의여행계획</a></li>
        <li><a href="#">핫플레이스</a></li>
        <li><a href="#">공유게시판</a></li>

        <c:if test="${empty userInfo}">
            <%-- 로그인 안 된 경우 --%>
            <li><a href="#" role="button" class="secondary">로그인</a></li>
            <li><a href="#" role="button">회원가입</a></li>
        </c:if>

        <c:if test="${not empty userInfo}">
            <%-- 로그인 된 경우 --%>
            <li><a href="#">${userInfo.name}님</a></li>
            <li><a href="#" role="button" class="secondary">로그아웃</a></li>
        </c:if>
    </ul>
</nav>