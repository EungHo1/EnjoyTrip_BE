<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>

<article>
    <header><h2>회원가입</h2></header>
    <form action="${pageContext.request.contextPath}/user" method="post">
        <input type="hidden" name="action" value="join">
        <c:if test="${not empty errorMsg}">
            <p style="color: red;">${errorMsg}</p>
        </c:if>
        <label for="userId">아이디</label>
        <input type="text" id="userId" name="userId" placeholder="사용할 아이디" required>

        <label for="userName">이름</label>
        <input type="text" id="userName" name="userName" placeholder="이름" required>

        <label for="userPassword">비밀번호</label>
        <input type="password" id="userPassword" name="userPassword" placeholder="비밀번호" required>

        <button type="submit">가입하기</button>
    </form>
</article>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>