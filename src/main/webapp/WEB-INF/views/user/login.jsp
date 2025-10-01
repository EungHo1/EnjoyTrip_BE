<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>

<article>
    <header><h2>로그인</h2></header>
    <form action="${pageContext.request.contextPath}/user" method="post">
        <c:if test="${not empty sessionScope.successMsg}">
            <p style="color: blue;">${sessionScope.successMsg}</p>
            <c:remove var="successMsg" scope="session" />
        </c:if>
        <input type="hidden" name="action" value="login">

        <label for="userId">아이디</label>
        <input type="text" id="userId" name="userId" placeholder="아이디를 입력하세요" required>

        <label for="userPassword">비밀번호</label>
        <input type="password" id="userPassword" name="userPassword" placeholder="비밀번호를 입력하세요" required>
        <br>
        <c:if test="${not empty errorMsg}">
            <p style="color: red;">${errorMsg}</p>
        </c:if>

        <button type="submit">로그인</button>

        <p>계정이 없으신가요? <a href="${pageContext.request.contextPath}/user?action=register-form">회원가입</a></p>

    </form>
</article>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>