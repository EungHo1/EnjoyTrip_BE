<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>

<article>
    <header>
        <h2>나만의 여행 계획을 세워보세요!</h2>
        <p>전국의 관광 정보를 기반으로 최고의 여행을 계획하고 공유할 수 있습니다.</p>
    </header>
    <img src="${pageContext.request.contextPath}/assets/images/main.jpg" alt="여행 관련 대표 이미지"
         style="width: 100%; height: auto;">
</article>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>