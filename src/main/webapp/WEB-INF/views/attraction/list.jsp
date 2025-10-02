<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=96d9fc335865f5611418176f59bdf202"></script>

<body>
<jsp:include page="/WEB-INF/views/common/nav.jsp"/>
<article>
    <header>
        <h2>지역별 관광 정보</h2>
        <p>원하는 지역과 콘텐츠를 선택하여 여행지를 찾아보세요.</p>
    </header>

    <form id="search-form" action="${pageContext.request.contextPath}/attraction" method="get">
        <input type="hidden" name="action" value="list">
        <div class="grid">
            <select name="sidoCode" id="sidoCode">
                <option value="0" selected>시/도 선택</option>
                <c:forEach items="${sidoList}" var="sido">
                    <option value="${sido.sidoCode}">${sido.sidoName}</option>
                </c:forEach>
            </select>
            <select name="gugunCode" id="gugunCode">
                <option value="0" selected>구/군 선택</option>
            </select>
            <select name="contentTypeId" id="contentTypeId">
                <option value="0" selected>콘텐츠 종류</option>
                <c:forEach items="${contentTypeList}" var="type">
                    <option value="${type.contentTypeId}">${type.contentTypeName}</option>
                </c:forEach>
            </select>
            <button type="submit">검색</button>
        </div>
    </form>

    <hr>

    <div class="grid">
        <%-- 지도 표시 영역 --%>
        <aside>
            <div id="map" style="width:100%; height:600px; background-color:#eee;">
                <p style="text-align: center; padding-top: 50%;">지도 표시 영역</p>
            </div>
        </aside>

        <%-- 관광지 목록 --%>
        <section id="attraction-list-section">
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
<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {
        const root = '${pageContext.request.contextPath}';

        // 지도 초기화(안전하게 kakao 존재 여부 체크)
        const mapContainer = document.getElementById('map');
        const mapOption = {
            center: (typeof kakao !== 'undefined' && kakao.maps) ? new kakao.maps.LatLng(37.566826, 126.9786567) : {lat:37.566826, lng:126.9786567},
            level: 8
        };
        let map;
        try {
            if (typeof kakao !== 'undefined' && kakao.maps) {
                map = new kakao.maps.Map(mapContainer, mapOption);
            } else {
                console.warn('Kakao Maps API not available; map creation skipped.');
                // map이 setBounds를 호출할 때 안전하도록 더미 객체 제공
                map = { setBounds: function(){} };
            }
        } catch (e) {
            console.error('Error creating Kakao map:', e);
            map = { setBounds: function(){} };
        }

        let markers = [];

        // 시/도 -> 구/군
        document.getElementById('sidoCode').addEventListener('change', function () {
            let sidoCode = this.value;
            if (sidoCode == "0") {
                document.getElementById('gugunCode').innerHTML = '<option value="0" selected>구/군 선택</option>';
                return;
            }

            fetch(root + '/attraction?action=gugunList&sidoCode=' + encodeURIComponent(sidoCode))
                .then(response => response.json())
                .then(data => {
                    let gugunSelect = document.getElementById('gugunCode');
                    gugunSelect.innerHTML = '<option value="0" selected>구/군 선택</option>';
                    data.forEach(gugun => {
                        // JSP/EL 충돌 우려가 있으면 문자열 연결을 사용
                        gugunSelect.innerHTML += '<option value="' + gugun.gugunCode + '">' + gugun.gugunName + '</option>';
                    });
                })
                .catch(err => console.error('gugunList fetch error:', err));
        });

        // 폼 submit 리스너 등록 — 존재 여부 체크
        const searchForm = document.getElementById('search-form');
        if (!searchForm) {
            console.error('search-form element not found. id 확인하세요.');
            return;
        }
        console.log('search-form found, attaching submit handler');

        searchForm.addEventListener("submit", function (event) {
            event.preventDefault();
            let sidoCode = document.getElementById("sidoCode").value;
            let gugunCode = document.getElementById("gugunCode").value;
            let contentTypeId = document.getElementById("contentTypeId").value;

            let searchUrl = root + '/attraction?action=search&sidoCode=' + encodeURIComponent(sidoCode)
                + '&gugunCode=' + encodeURIComponent(gugunCode)
                + '&contentTypeId=' + encodeURIComponent(contentTypeId);

            fetch(searchUrl)
                .then(response => response.json())
                .then(data => {
                    displayMarkers(data);
                    displayList(data); // <<-- 이 한 줄만 추가하면 끝!
                }).catch(err => console.error('search fetch error:', err));
        });

        function displayMarkers(spots) {
            console.log(`Title: ${spot.title}, Lat: ${spot.latitude}, Lng: ${spot.longitude}`);
            markers.forEach(marker => marker.setMap(null));
            markers = [];

            if (!spots || spots.length === 0) return;

            const bounds = (typeof kakao !== 'undefined' && kakao.maps) ? new kakao.maps.LatLngBounds() : { extend: function(){} };

            spots.forEach(spot => {
                if (typeof kakao !== 'undefined' && kakao.maps) {
                    const position = new kakao.maps.LatLng(Number(spot.latitude), Number(spot.longitude));
                    const marker = new kakao.maps.Marker({
                        map: map,
                        position: position,
                        title: spot.title
                    });
                    markers.push(marker);
                    bounds.extend(position);
                }
            });
            if (map && typeof map.setBounds === 'function') map.setBounds(bounds);
        }

        // 검색 결과 목록을 테이블에 표시하는 함수
        function displayList(spots) {
            const listSection = document.querySelector("#attraction-list-section");
            listSection.innerHTML = ""; // 기존 내용 초기화

            const title = document.createElement("h5");
            title.textContent = "검색 결과";
            listSection.appendChild(title);

            if (!spots || spots.length === 0) {
                const msg = document.createElement("p");
                msg.textContent = "검색 결과가 없습니다.";
                listSection.appendChild(msg);
                return;
            }

            const table = document.createElement("table");

            // thead
            const thead = document.createElement("thead");
            thead.innerHTML = `
        <tr>
            <th>사진</th>
            <th>관광지명</th>
            <th>주소</th>
        </tr>
    `;
            table.appendChild(thead);

            // tbody
            const tbody = document.createElement("tbody");

            spots.forEach(spot => {
                const tr = document.createElement("tr");

                // 이미지 셀
                const tdImg = document.createElement("td");
                const img = document.createElement("img");
                img.src = spot.firstImage ? spot.firstImage : "https://placehold.co/100x80?text=Image";
                img.alt = "관광지 이미지";
                img.style.width = "100px";
                tdImg.appendChild(img);

                // 제목 셀
                const tdTitle = document.createElement("td");
                tdTitle.textContent = spot.title;

                // 주소 셀
                const tdAddr = document.createElement("td");
                tdAddr.textContent = spot.addr1;

                tr.appendChild(tdImg);
                tr.appendChild(tdTitle);
                tr.appendChild(tdAddr);

                tbody.appendChild(tr);
            });

            table.appendChild(tbody);
            listSection.appendChild(table);
        }
    });

</script>
</body><jsp:include page="/WEB-INF/views/common/footer.jsp"/>