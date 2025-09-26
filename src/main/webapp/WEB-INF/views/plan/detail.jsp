<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>

<article>
    <header>
        <h2>${plan.title}</h2>
        <a href="${pageContext.request.contextPath}/plan?action=list" role="button" class="secondary outline">목록으로</a>
        <a href="${pageContext.request.contextPath}/plan?action=mvform&planId=${plan.planId}" role="button" class="secondary">수정</a>
        <a href="${pageContext.request.contextPath}/plan?action=delete&planId=${plan.planId}" role="button" class="contrast">삭제</a>
    </header>

    <div class="grid">
        <section>
            <h5>여행 경로</h5>
            <table>
                <thead>
                <tr>
                    <th>순서</th>
                    <th>장소명</th>
                    <th>주소</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${plan.courses}" var="course" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${course.title}</td>
                        <td>${course.addr1}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>
        <aside>
            <div id="map" style="width:100%; height:500px; background-color:#eee;">
                <%-- TODO: 여기에 여행 경로를 표시하는 지도 구현 --%>
                <p style="text-align: center; padding-top: 50%;">지도 표시 영역</p>
            </div>
        </aside>
    </div>
</article>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>