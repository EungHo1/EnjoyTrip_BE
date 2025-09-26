<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>
<article>
    <header><h2>공지사항</h2></header>
    <table>
        <thead>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성일</th>
            <th>조회수</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${noticeList}" var="notice">
            <tr>
                <td>${notice.noticeNo}</td>
                <td><a href="${pageContext.request.contextPath}/notice?action=detail&noticeNo=${notice.noticeNo}">${notice.subject}</a></td>
                <td>${notice.registerTime}</td>
                <td>${notice.hit}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</article>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>