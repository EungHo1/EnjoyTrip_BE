<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>
<article>
    <header>
        <h2>${category == 'notice' ? '공지사항' : '자유게시판'}</h2>
        <c:choose>
            <c:when test="${category == 'notice'}">
                <c:if test="${not empty sessionScope.userInfo && sessionScope.userInfo.userNo == 2}">
                    <a href="${pageContext.request.contextPath}/board?action=mvform&category=notice" role="button">공지 작성</a>
                </c:if>
            </c:when>

            <c:otherwise>
                <c:if test="${not empty sessionScope.userInfo}">
                    <a href="${pageContext.request.contextPath}/board?action=mvform&category=free" role="button">글쓰기</a>
                </c:if>
            </c:otherwise>
        </c:choose>
    </header>
    <table>
        <thead>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>조회수</th>
            <th>작성일</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${articleList}" var="article">
            <tr>
                <td>${article.articleNo}</td>
                <td><a href="${pageContext.request.contextPath}/board?action=detail&articleNo=${article.articleNo}">${article.subject}</a></td>
                <td>${article.userId}</td>
                <td>${article.hit}</td>
                <td>${article.registerTime}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</article>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>