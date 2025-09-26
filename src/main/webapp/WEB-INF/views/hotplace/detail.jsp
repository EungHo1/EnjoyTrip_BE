<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>
<article>
    <header>
        <h2>${hotplace.title}</h2>
        <p>작성자: ${hotplace.userName} | 방문일: ${hotplace.visitDate}</p>
    </header>
    <img src="${pageContext.request.contextPath}/uploads/${hotplace.imageFileName}" alt="${hotplace.title}" style="width: 100%;">
    <p>${hotplace.description}</p>
    <div id="map" style="width:100%; height:400px; background-color:#eee;">지도 표시 영역</div>
    <footer>
        <a href="${pageContext.request.contextPath}/hotplace?action=list" role="button" class="secondary">목록</a>
        <c:if test="${userInfo.userId eq hotplace.userId}">
            <a href="${pageContext.request.contextPath}/hotplace?action=mvform&hotplaceId=${hotplace.hotplaceId}" role="button">수정</a>
            <a href="${pageContext.request.contextPath}/hotplace?action=delete&hotplaceId=${hotplace.hotplaceId}" role="button" class="contrast">삭제</a>
        </c:if>
    </footer>
</article>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>