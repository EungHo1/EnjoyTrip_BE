<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>

<article>
    <header>
        <h2>지역별 관광 정보</h2>
        <p>원하는 지역과 콘텐츠를 선택하여 여행지를 찾아보세요.</p>
    </header>

    <form action="${pageContext.request.contextPath}/attraction" method="get">
        <input type="hidden" name="action" value="list">
        <div class="grid">
            <select name="sidoCode" id="sidoCode">
                <option value="0" selected>시/도 선택</option>
                <%-- TODO: 서블릿에서 시/도 목록(sidoList)을 받아와 <c:forEach>로 채워야 함 --%>
            </select>
            <select name="gugunCode" id="gugunCode">
                <option value="0" selected>구/군 선택</option>
                <%-- TODO: 시/도 선택 시 JS로 해당 구/군 목록을 비동기 로딩해야 함 --%>
            </select>
            <select name="contentTypeId" id="contentTypeId">
                <option value="0" selected>콘텐츠 종류</option>
                <%-- TODO: 관광(12), 문화(14), 축제(15), 숙박(32), 쇼핑(38), 음식(39) 등 --%>
            </select>
            <button type="submit">검색</button>
        </div>
    </form>

    <hr>

    <div class="grid">
        <%-- 지도 표시 영역 --%>
        <aside>
            <div id="map" style="width:100%; height:600px; background-color:#eee;">
                <%-- TODO: Kakao Map API 등을 사용하여 여기에 지도를 표시해야 함 --%>
                <p style="text-align: center; padding-top: 50%;">지도 표시 영역</p>
            </div>
        </aside>

        <%-- 관광지 목록 --%>
        <section>
            <h5>검색 결과</h5>
            <c:choose>
                <c:when test="${not empty attractionList}">
                    <table>
                        <thead>
                        <tr>
                            <th>사진</th>
                            <th>관광지명</th>
                            <th>주소</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${attractionList}" var="attraction">
                            <tr>
                                <td>
                                    <img src="${attraction.firstImage != '' ? attraction.firstImage : 'https://placehold.co/100x80?text=Image'}"
                                         alt="관광지 이미지" style="width: 100px;">
                                </td>
                                <td>${attraction.title}</td>
                                <td>${attraction.addr1}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p>검색 결과가 없습니다. 조건을 선택하여 검색해주세요.</p>
                </c:otherwise>
            </c:choose>
        </section>
    </div>
</article>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>