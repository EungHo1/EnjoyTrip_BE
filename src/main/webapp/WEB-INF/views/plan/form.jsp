<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>

<article>
    <header>
        <%-- 수정 모드일 때와 생성 모드일 때 제목을 다르게 표시 --%>
        <c:choose>
            <c:when test="${not empty plan}">
                <h2>여행 계획 수정</h2>
            </c:when>
            <c:otherwise>
                <h2>새 여행 계획 만들기</h2>
            </c:otherwise>
        </c:choose>
    </header>

    <form action="${pageContext.request.contextPath}/plan" method="post">
        <%-- action 값과 planId를 숨겨서 서블릿으로 전송 --%>
        <input type="hidden" name="action" value="${not empty plan ? 'update' : 'create'}">
        <input type="hidden" name="planId" value="${plan.planId}">

        <label for="title">계획 이름</label>
        <input type="text" id="title" name="title" value="${plan.title}" placeholder="예: 부산 2박 3일 식도락 여행" required>

        <hr>

        <h5>여행지 추가/관리</h5>
        <%-- TODO: 이 부분은 JavaScript를 사용하여 동적으로 관광지를 검색하고 추가/삭제/순서변경하는 UI로 만들어야 함 --%>
        <%-- 지금은 일단 저장된 경로만 보여주는 형태로 단순화 --%>
        <p><strong>[현재 경로]</strong></p>
        <c:choose>
            <c:when test="${not empty plan.courses}">
                <ol>
                    <c:forEach items="${plan.courses}" var="course">
                        <li>${course.title}</li>
                    </c:forEach>
                </ol>
            </c:when>
            <c:otherwise>
                <p>아직 추가된 여행지가 없습니다.</p>
            </c:otherwise>
        </c:choose>
        <small><em>* 실제 구현 시 관광지 검색 및 동적 추가 기능이 필요합니다.</em></small>

        <br><br>

        <button type="submit">계획 저장하기</button>
        <a href="javascript:history.back()" role="button" class="secondary">취소</a>
    </form>
</article>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>