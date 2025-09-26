<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>
<article>
    <header>
        <h2>핫플레이스 자랑</h2>
        <a href="${pageContext.request.contextPath}/hotplace?action=mvform" role="button">핫플 등록</a>
    </header>
    <div class="grid">
        <c:forEach items="${hotplaceList}" var="hotplace">
            <article>
                <header>
                    <img src="${pageContext.request.contextPath}/uploads/${hotplace.imageFileName}" alt="${hotplace.title}">
                </header>
                <h5>${hotplace.title}</h5>
                <p><small>by ${hotplace.userName}</small></p>
                <footer>
                    <a href="${pageContext.request.contextPath}/hotplace?action=detail&hotplaceId=${hotplace.hotplaceId}" role="button" class="contrast">자세히 보기</a>
                </footer>
            </article>
        </c:forEach>
    </div>
</article>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>