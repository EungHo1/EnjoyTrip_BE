<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>
<article>
    <header><h2>${not empty article ? '글 수정' : '글쓰기'}</h2></header>
    <form action="${pageContext.request.contextPath}/board" method="post">
        <input type="hidden" name="action" value="${not empty article ? 'update' : 'create'}">
        <input type="hidden" name="articleNo" value="${article.articleNo}">
        <label for="subject">제목</label>
        <input type="text" id="subject" name="subject" value="${article.subject}" placeholder="제목을 입력하세요" required>
        <label for="content">내용</label>
        <textarea id="content" name="content" rows="10" placeholder="내용을 입력하세요" required>${article.content}</textarea>
        <button type="submit">저장</button>
    </form>
</article>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>