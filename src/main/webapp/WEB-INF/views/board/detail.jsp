<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>
<article>
    <header>
        <h2>${article.subject}</h2>
        <p>작성자: ${article.userName} | 조회수: ${article.hit} | 작성일: ${article.registerTime}</p>
    </header>
    <hr>
    <div>
        ${article.content}
    </div>
    <hr>
    <footer>
        <a href="${pageContext.request.contextPath}/board?action=list" role="button" class="secondary">목록</a>
        <%-- 로그인한 사용자와 글 작성자가 같을 경우에만 수정/삭제 버튼 표시 --%>
        <c:if test="${userInfo.userId eq article.userId}">
            <a href="${pageContext.request.contextPath}/board?action=mvform&articleNo=${article.articleNo}" role="button">수정</a>
            <a href="${pageContext.request.contextPath}/board?action=delete&articleNo=${article.articleNo}" role="button" class="contrast">삭제</a>
        </c:if>
    </footer>
</article>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>