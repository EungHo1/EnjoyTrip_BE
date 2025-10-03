<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=96d9fc335865f5611418176f59bdf202"></script>
<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>
<article>
    <header>
        <h2>핫플레이스</h2>
        <c:if test="${not empty sessionScope.userInfo}">
            <a href="${root}/hotplace?action=mvform" role="button">나만의 핫플레이스 등록하기</a>
        </c:if>
    </header>
    <div id="map" style="width:100%; height:800px;"></div>
</article>

<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {
        const root = '${pageContext.request.contextPath}';
        const mapContainer = document.getElementById('map');

        let markers = [];

        const mapOption = {
            center: new kakao.maps.LatLng(37.566826, 126.9786567), // 서울 중심
            level: 10 // 전국을 보여주기 위해 레벨을 넓게 잡는다
        };
        const map = new kakao.maps.Map(mapContainer, mapOption);

        // 페이지가 로드되면 AJAX로 모든 핫플레이스 데이터를 요청한다.
        fetch(`${root}/hotplace?action=list`)
            .then(response => response.json())
            .then(data => displayMarkers(data))
            .catch(err => console.error("핫플레이스 데이터 로딩 실패:", err));

        // 마커와 인포윈도우를 표시하는 함수
        function displayMarkers(hotplaces) {
            // 서버에서 넘어온 데이터가 어떻게 생겼는지 최종 확인
            console.log("displayMarkers에 전달된 데이터:", hotplaces);

            // 기존 마커 제거
            markers.forEach(marker => marker.setMap(null));
            markers = [];

            if (!hotplaces || hotplaces.length === 0) return;

            hotplaces.forEach(place => {
                const position = new kakao.maps.LatLng(place.latitude, place.longitude);

                const marker = new kakao.maps.Marker({
                    map: map,
                    position: position,
                    title: place.title
                });

                // --- 인포윈도우 내용을 createElement로 만들기 ---

                // 1. 전체를 감싸는 div
                const contentDiv = document.createElement('div');
                contentDiv.style.padding = '5px';
                contentDiv.style.fontSize = '12px';
                contentDiv.style.minWidth = '150px'; // 최소 너비 지정

                // 2. 제목 (strong)
                const titleStrong = document.createElement('strong');
                titleStrong.textContent = place.title;

                // 3. 이미지 (img)
                const img = document.createElement('img');
                img.src = place.imageUrl ? place.imageUrl : 'https://placehold.co/150x100?text=No+Image';
                img.alt = '핫플레이스 이미지';
                img.width = 150;

                // 4. 설명 (span)
                const descSpan = document.createElement('span');
                descSpan.textContent = place.description;

                // 5. 작성자 (small)
                const authorSmall = document.createElement('small');
                authorSmall.textContent = '작성자: ' + place.userName;

                // 6. 조립하기: div 안에 순서대로 자식 요소들을 추가
                contentDiv.appendChild(titleStrong);
                contentDiv.appendChild(document.createElement('br'));
                contentDiv.appendChild(img);
                contentDiv.appendChild(document.createElement('br'));
                contentDiv.appendChild(descSpan);
                contentDiv.appendChild(document.createElement('br'));
                contentDiv.appendChild(authorSmall);

                // ---------------------------------------------

                const infowindow = new kakao.maps.InfoWindow({
                    content: contentDiv, // 문자열 대신 조립된 DOM 객체를 내용물로 전달
                    removable: true
                });

                kakao.maps.event.addListener(marker, 'click', function() {
                    infowindow.open(map, marker);
                });
            });
        }
    });
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>