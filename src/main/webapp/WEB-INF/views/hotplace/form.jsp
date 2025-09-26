<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>
<article>
    <header><h2>${not empty hotplace ? '핫플레이스 수정' : '핫플레이스 등록'}</h2></header>
    <%-- 파일 업로드를 위해 enctype="multipart/form-data" 필수 --%>
    <form action="${pageContext.request.contextPath}/hotplace" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="${not empty hotplace ? 'update' : 'create'}">
        <input type="hidden" name="hotplaceId" value="${hotplace.hotplaceId}">
        <label for="title">장소 이름</label>
        <input type="text" id="title" name="title" value="${hotplace.title}" required>
        <label for="visitDate">방문 날짜</label>
        <input type="date" id="visitDate" name="visitDate" value="${hotplace.visitDate}" required>
        <label for="description">상세 설명</label>
        <textarea id="description" name="description" rows="5">${hotplace.description}</textarea>
        <label for="image">대표 사진</label>
        <input type="file" id="image" name="image">
        <button type="submit">저장</button>
    </form>
</article>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>