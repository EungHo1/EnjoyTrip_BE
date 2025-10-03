<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>
<article>
    <header><h2>나의 여행 계획</h2></header>

    <section>
        <h4>현재 계획중인 여행</h4>
        <c:choose>
            <c:when test="${not empty sessionScope.planCart}">
                <form action="${pageContext.request.contextPath}/plan" method="post">
                    <input type="hidden" name="action" value="save">
                    <label for="planTitle">계획 이름</label>
                    <input type="text" id="planTitle" name="planTitle" placeholder="예: 나의 첫 부산 여행!" required>

                    <h5>담은 여행지 목록</h5>
                    <ul>
                        <c:forEach items="${sessionScope.planCart}" var="spot">
                            <li>${spot.title}</li>
                        </c:forEach>
                    </ul>
                    <button type="submit">이 계획 저장하기</button>
                </form>
            </c:when>
            <c:otherwise>
                <p>아직 계획에 담은 여행지가 없습니다. <a href="${pageContext.request.contextPath}/attraction?action=list">여행지 검색</a> 페이지에서 여행지를 추가해주세요.</p>
            </c:otherwise>
        </c:choose>
    </section>

    <hr>

    <%-- DB에 저장된 나의 모든 계획 목록 --%>
    <section>
        <h4>저장된 계획 목록</h4>
        <c:choose>
            <c:when test="${not empty savedPlans}">
                <ul>
                        <%-- 1. 바깥쪽 forEach가 '계획' 하나하나를 순회 --%>
                    <c:forEach items="${savedPlans}" var="plan">
                        <li>
                            <strong>${plan.title}</strong> (${plan.createDate})
                                <%-- 2. 안쪽 forEach가 그 '계획'에 포함된 '관광지 목록'을 순회 --%>
                            <ul>
                                <c:forEach items="${plan.attractions}" var="spot">
                                    <li>
                                        <img src="${spot.firstImage1 != '' ? spot.firstImage1 : 'https://placehold.co/50x40?text=Img'}" style="width: 50px;">
                                            ${spot.title}
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <p>저장된 여행 계획이 없습니다.</p>
            </c:otherwise>
        </c:choose>
    </section>

</article>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
