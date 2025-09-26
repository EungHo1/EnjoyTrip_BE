<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>

<article>
    <header><h2>로그인</h2></header>
    <form action="${pageContext.request.contextPath}/user" method="post">
        <input type="hidden" name="action" value="login">

        <label for="userId">아이디</label>
        <input type="text" id="userId" name="userId" placeholder="아이디를 입력하세요" required>

        <label for="userPassword">비밀번호</label>
        <input type="password" id="userPassword" name="userPassword" placeholder="비밀번호를 입력하세요" required>

        <button type="submit">로그인</button>
    </form>
</article>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>