<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=96d9fc335865f5611418176f59bdf202"></script>
<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>
<article>
    <header><h2>나만의 핫플레이스 등록</h2></header>
    <form action="${root}/hotplace" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="create">
        <input type="hidden" id="latitude" name="latitude">
        <input type="hidden" id="longitude" name="longitude">

        <p>지도에서 핫플레이스를 클릭하여 위치를 지정해주세요.</p>
        <div id="map" style="width:100%; height:400px;"></div>

        <label for="title">장소 이름</label>
        <input type="text" id="title" name="title" required>

        <label for="description">설명</label>
        <textarea id="description" name="description"></textarea>

        <label for="image">사진</label>
        <input type="file" id="image" name="image" accept="image/*">

        <button type="submit">핫플레이스 등록</button>
    </form>
</article>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const mapContainer = document.getElementById('map');
        const latInput = document.getElementById('latitude');
        const lngInput = document.getElementById('longitude');

        const mapOption = {
            center: new kakao.maps.LatLng(37.566826, 126.9786567),
            level: 3
        };
        const map = new kakao.maps.Map(mapContainer, mapOption);
        let marker = new kakao.maps.Marker(); // 클릭 위치를 표시할 마커

        // 지도 클릭 이벤트
        kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
            const latlng = mouseEvent.latLng;

            // hidden input에 위도, 경도 값 설정
            latInput.value = latlng.getLat();
            lngInput.value = latlng.getLng();

            // 마커 위치를 클릭한 위치로 옮기고 지도에 표시
            marker.setPosition(latlng);
            marker.setMap(map);
        });
    });
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>