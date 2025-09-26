<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>
<article>
    <header>
        <h2>${notice.subject}</h2>
        <p>작성일: ${notice.registerTime} | 조회수: ${notice.hit}</p>
    </header>
    <hr>
    <div>
        ${notice.content}
    </div>
    <hr>
    <footer>
        <a href="${pageContext.request.contextPath}/notice?action=list" role="button" class="secondary">목록으로</a>
    </footer>
</article>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>