<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>

<article>
    <header>
        <h2>나의 여행 계획 목록</h2>
        <a href="${pageContext.request.contextPath}/plan?action=mvform" role="button">새 계획 만들기</a>
    </header>

    <c:choose>
        <c:when test="${not empty planList}">
            <div class="grid">
                <c:forEach items="${planList}" var="plan">
                    <article>
                        <header>
                            <h5>${plan.title}</h5>
                        </header>
                        <p>
                            마지막 수정일: ${plan.lastModified} <br>
                                <%-- TODO: 계획에 포함된 장소 개수 등 요약 정보 표시 --%>
                        </p>
                        <footer>
                            <a href="${pageContext.request.contextPath}/plan?action=detail&planId=${plan.planId}" role="button" class="contrast">상세보기</a>
                        </footer>
                    </article>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <p>아직 작성한 여행 계획이 없습니다. 새 계획을 만들어보세요!</p>
        </c:otherwise>
    </c:choose>
</article>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>