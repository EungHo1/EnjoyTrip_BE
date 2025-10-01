<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>

<article>
    <header><h2>내 정보 관리</h2></header>

    <c:if test="${empty userInfo}">
        <p>로그인이 필요한 서비스입니다.</p>
        <a href="#" role="button">로그인 페이지로 이동</a>
    </c:if>

    <c:if test="${not empty userInfo}">
        <form action="${pageContext.request.contextPath}/user" method="post">
            <input type="hidden" name="action" value="update">

            <label for="userId">아이디 (수정 불가)</label>
            <input type="text" id="userId" name="userId" value="${userInfo.userId}" readonly>

            <label for="userName">이름</label>
            <input type="text" id="userName" name="userName" value="${userInfo.userName}" required>

            <label for="userPassword">새 비밀번호</label>
            <input type="password" id="userPassword" name="userPassword" placeholder="변경할 경우에만 입력">

            <button type="submit">정보 수정</button>
        </form> <hr> <form action="${pageContext.request.contextPath}/user" method="post" onsubmit="return confirm('정말 탈퇴하시겠습니까?');">
        <input type="hidden" name="action" value="delete">
        <button type="submit" class="contrast" role="button">회원 탈퇴</button>
    </form>
    </c:if>
</article>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>