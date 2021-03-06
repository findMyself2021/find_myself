//출발지,도착지 마커
var marker_s, marker_e, marker_p;
var customOverlay;
var customOverlay_start;
var customOverlay_end;

//마커
var start_marker = '/image/start.png';
var end_marker = '/image/end2.png';
var size_marker = new kakao.maps.Size(32, 32);

//경로그림정보
var drawInfoArr = [];
var drawInfoArr2 = [];

var chktraffic = [];
var resultdrawArr = [];
var resultMarkerArr = [];

var carLocationResults = "";
var carInfo = ""; // 자동차 경로 분석 결과 요약 데이터
var carInfoArr, carTrafficArr, carSummaryArr, carNumArr;

//목적지 마커 표시
function initDestSearch(startX,startY,addr){
    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();

// 주소로 좌표를 검색합니다
    geocoder.addressSearch(addr, function(result, status) {

        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {

            // 목적지 좌표
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            var markerImage = new kakao.maps.MarkerImage(end_marker,size_marker);

            // 결과값으로 받은 위치를 마커로 표시합니다 (도착지)
            marker_e = new kakao.maps.Marker({
                map: map,
                position: coords,
                image: markerImage
            });

            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);

            //도착지 좌표값 전달
            searchCarRoute(startX,startY,result[0].y,result[0].x);
            searchCarRouteLocation(startX,startY,result[0].y,result[0].x);
            searchPubTransRoute(startY, startX, result[0].x, result[0].y);
            searchSubwayStations(startY, startX, result[0].x, result[0].y);

            var content = '<div class="customoverlay">' +
                '  <a href="#" onclick="return false;" target="_blank">' +
                '    <span class="title">도착지</span>' +
                '  </a>' +
                '</div>';


            customOverlay_end = new kakao.maps.CustomOverlay({
                map:map,
                position: new kakao.maps.LatLng(result[0].y,result[0].x),
                content: content,
                yAnchor: 0
            });
        }
        else{
            alert('도로명 주소를 입력해주세요');
        }
    });
}

// 나중에 값 전달받기
function searchCarRoute(startX,startY,endX,endY) {

    // 출발지점
    // var startX = 37.56093749910637; => 경도(세로)
    // var startY = 126.99332009924663;

    var markerImage = new kakao.maps.MarkerImage(start_marker,size_marker);

    marker_s = new kakao.maps.Marker(
        {
            position : new kakao.maps.LatLng(startX,startY),
            image: markerImage,
            map : map
        });

    var content = '<div class="start_customoverlay">' +
        '  <a href="#" onclick="return false;" target="_blank">' +
        '    <span class="title">출발지</span>' +
        '  </a>' +
        '</div>';


    customOverlay_start = new kakao.maps.CustomOverlay({
        map:map,
        position: new kakao.maps.LatLng(startX,startY),
        content: content,
        yAnchor: 0
    });

    // 3. 경로탐색 API 사용요청
    $("#btn_select")
        .click(
            function() {
                //기존 맵에 있던 정보들 초기화
                resettingMap();

                // 교통최적 + 최소시간
                var searchOption = "2";

                // 교통 정보 표출 옵션
                var trafficInfochk = "N";

                //JSON TYPE EDIT [S]
                $
                    .ajax({
                        type : "POST",
                        url : "https://apis.openapi.sk.com/tmap/routes?version=1&format=json&callback=result",
                        async : false,
                        data : {
                            "appKey" : "l7xx054e772885bf4fd6bff6bbf96c1884af",
                            "startX" : startY,
                            "startY" : startX,
                            "endX" : endY,
                            "endY" : endX,
                            "reqCoordType" : "WGS84GEO",
                            "resCoordType" : "EPSG3857",
                            "searchOption" : searchOption,
                            "trafficInfo" : trafficInfochk
                        },
                        success : function(response) {

                            var resultData = response.features;

                            var tDistance = "총 거리 : "
                                + (resultData[0].properties.totalDistance / 1000)
                                    .toFixed(1) + "km";
                            var tTime = " 총 시간 : "
                                + (resultData[0].properties.totalTime / 60)
                                    .toFixed(0) + "분";

                            var taxiFare = " 예상 택시 요금 : "
                                + resultData[0].properties.taxiFare
                                + "원";

                            var content = '<div class="overlaybox">' +
                                '<div class="boxtitle">자동차 경로 검색 결과</div>'+
                                '   <ul>' +
                                '       <li class="up">' +
                                '           <span class="title" >'+tDistance+'</span>'+
                                '       </li>'+
                                '       <li>'+
                                '           <span class="title">'+tTime+'</span>'+
                                '       </li>'+
                                '       <li>'+
                                '           <span class="title">'+taxiFare+'</span>'+
                                '       </li>'+
                                '   </ul>'+
                                '</div>';

                            // 도착지가 출발지보다 아래에 있으면 => 커스텀 오버레이 밑으로 나타나게 하기
                            if(startX>endX){
                                var yanchor = 0;
                            }
                            else{
                                var yanchor = 1.2;
                            }
                            // 도착지가 왼쪽이라면
                            if(startY>endY){
                                var xanchor = 1;
                            }
                            else{
                                var xanchor = 0;
                            }

                            // 커스텀 오버레이를 생성합니다
                            customOverlay = new kakao.maps.CustomOverlay({
                                position: new kakao.maps.LatLng(endX,endY),// 도착지에 나타나게
                                content: content,
                                //왼쪽 오른쪽
                                xAnchor: xanchor, //0 => 오른쪽에 나타남
                                //위 아래
                                yAnchor: yanchor
                            });

                            customOverlay.setMap(map);

                            // $("#result").text(
                            //     tDistance + tTime + taxiFare);

                            //교통정보 표출 옵션값을 체크
                            if (trafficInfochk == "Y") {
                                for ( var i in resultData) { //for문 [S]
                                    var geometry = resultData[i].geometry;
                                    var properties = resultData[i].properties;

                                    if (geometry.type == "LineString") {
                                        //교통 정보도 담음
                                        chktraffic
                                            .push(geometry.traffic);
                                        var sectionInfos = [];
                                        var trafficArr = geometry.traffic;

                                        for ( var j in geometry.coordinates) {
                                            // 경로들의 결과값들을 포인트 객체로 변환
                                            var latlng = new kakao.maps.Point(
                                                geometry.coordinates[j][0],
                                                geometry.coordinates[j][1]);
                                            // 포인트 객체를 받아 좌표값으로 변환
                                            var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                                latlng);

                                            sectionInfos
                                                .push(convertPoint);
                                        }

                                        drawLine(sectionInfos,
                                            trafficArr);
                                    }
                                }//for문 [E]

                            } else {

                                for ( var i in resultData) { //for문 [S]
                                    var geometry = resultData[i].geometry;
                                    var properties = resultData[i].properties;

                                    if (geometry.type == "LineString") {
                                        for ( var j in geometry.coordinates) {
                                            // 경로들의 결과값들을 포인트 객체로 변환
                                            var latlng = new kakao.maps.Point(
                                                geometry.coordinates[j][0],
                                                geometry.coordinates[j][1]);
                                            // 포인트 객체를 받아 좌표값으로 변환
                                            var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                                latlng);
                                            // 포인트객체의 정보로 좌표값 변환 객체로 저장
                                            var convertChange = new kakao.maps.LatLng(
                                                convertPoint._lat,
                                                convertPoint._lng);
                                            // 배열에 담기
                                            drawInfoArr
                                                .push(convertChange);
                                        }
                                        drawLine(drawInfoArr,
                                            "0");
                                    } else {

                                        var markerImg = "";
                                        var pType = "";

                                        if (properties.pointType == "S") { //출발지 마커
                                            markerImg = start_marker;
                                            pType = "S";
                                        } else if (properties.pointType == "E") { //도착지 마커
                                            markerImg = end_marker;
                                            pType = "E";
                                        } else { //각 포인트 마커
                                            markerImg = "/image/check.png";
                                            pType = "P"
                                        }

                                        // 경로들의 결과값들을 포인트 객체로 변환
                                        var latlon = new kakao.maps.Point(
                                            geometry.coordinates[0],
                                            geometry.coordinates[1]);
                                        // 포인트 객체를 받아 좌표값으로 다시 변환
                                        var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                            latlon);

                                        var routeInfoObj = {
                                            markerImage : markerImg,
                                            lng : convertPoint._lng,
                                            lat : convertPoint._lat,
                                            pointType : pType
                                        };

                                        // Marker 추가
                                        addMarkers(routeInfoObj);
                                    }
                                }//for문 [E]
                            }
                        },
                        error : function(request, status, error) {
                            console.log("code:"
                                + request.status + "\n"
                                + "message:"
                                + request.responseText
                                + "\n" + "error:" + error);
                        }
                    });
                //JSON TYPE EDIT [E]
            });
}

// 자동차 경로 좌표 찾기
function searchCarRouteLocation(startX,startY,endX,endY) {
    $("#btn_select")
        .click(
            function() {
                // 교통최적 + 최소시간
                var searchOption = "2";

                // 교통 정보 표출 옵션
                var trafficInfochk = "N";

                $
                    .ajax({
                        type : "POST",
                        url : "https://apis.openapi.sk.com/tmap/routes?version=1&format=json&callback=result",
                        async : false,
                        data : {
                            "appKey" : "l7xx054e772885bf4fd6bff6bbf96c1884af",
                            "startX" : startY,
                            "startY" : startX,
                            "endX" : endY,
                            "endY" : endX,
                            "reqCoordType" : "WGS84GEO",
                            "resCoordType" : "WGS84GEO",
                            "searchOption" : searchOption,
                            "trafficInfo" : trafficInfochk
                        },
                        success : function(response) {

                            var resultData = response.features;

                            console.log(resultData);

                            var str = "";
                            for (var i = 0; i < resultData.length; i++) {
                                for(var j = 0; j < resultData[i].geometry.coordinates.length; j++) {
                                    str += resultData[i].geometry.coordinates[j] + ",";
                                }
                            }
                            carLocationResults = str;
                        },
                        error : function(error) {
                            console.log("error");
                        }
                    });
            });
}

//마커 생성하기
function addMarkers(infoObj) {
    var size = size_marker;

    if (infoObj.pointType == "P") { //포인트점일때는 아이콘 크기를 줄입니다.
        size = new kakao.maps.Size(4, 4);
    }

    var markerImage = new kakao.maps.MarkerImage(infoObj.markerImage,size);

    marker_p = new kakao.maps.Marker({
        position : new kakao.maps.LatLng(infoObj.lat, infoObj.lng),
        image: markerImage,
        map : map
    });

    resultMarkerArr.push(marker_p);
}

//라인그리기
function drawLine(arrPoint, traffic) {
    var polyline_;

    if (chktraffic.length != 0) {

        // 교통정보 혼잡도를 체크
        // strokeColor는 교통 정보상황에 다라서 변화
        // traffic :  0-정보없음, 1-원활, 2-서행, 3-지체, 4-정체  (black, green, yellow, orange, red)

        var lineColor = "";

        if (traffic != "0") {
            if (traffic.length == 0) { //length가 0인것은 교통정보가 없으므로 검은색으로 표시

                lineColor = "#06050D";
                //라인그리기[S]
                polyline_ = new kakao.maps.Polyline({
                    path : arrPoint,
                    strokeColor : lineColor,
                    strokeWeight : 6,
                    map : map
                });
                resultdrawArr.push(polyline_);
                //라인그리기[E]
            } else { //교통정보가 있음

                if (traffic[0][0] != 0) { //교통정보 시작인덱스가 0이 아닌경우
                    var trafficObject = "";
                    var tInfo = [];

                    for (var z = 0; z < traffic.length; z++) {
                        trafficObject = {
                            "startIndex" : traffic[z][0],
                            "endIndex" : traffic[z][1],
                            "trafficIndex" : traffic[z][2],
                        };
                        tInfo.push(trafficObject)
                    }

                    var noInfomationPoint = [];

                    for (var p = 0; p < tInfo[0].startIndex; p++) {
                        noInfomationPoint.push(arrPoint[p]);
                    }

                    //라인그리기[S]
                    polyline_ = new kakao.maps.Polyline({
                        path : noInfomationPoint,
                        strokeColor : "#06050D",
                        strokeWeight : 6,
                        map : map
                    });
                    //라인그리기[E]
                    resultdrawArr.push(polyline_);

                    for (var x = 0; x < tInfo.length; x++) {
                        var sectionPoint = []; //구간선언

                        for (var y = tInfo[x].startIndex; y <= tInfo[x].endIndex; y++) {
                            sectionPoint.push(arrPoint[y]);
                        }

                        if (tInfo[x].trafficIndex == 0) {
                            lineColor = "#06050D";
                        } else if (tInfo[x].trafficIndex == 1) {
                            lineColor = "#61AB25";
                        } else if (tInfo[x].trafficIndex == 2) {
                            lineColor = "#FFFF00";
                        } else if (tInfo[x].trafficIndex == 3) {
                            lineColor = "#E87506";
                        } else if (tInfo[x].trafficIndex == 4) {
                            lineColor = "#D61125";
                        }

                        //라인그리기[S]
                        polyline_ = new kakao.maps.Polyline({
                            path : sectionPoint,
                            strokeColor : lineColor,
                            strokeWeight : 6,
                            map : map
                        });
                        //라인그리기[E]
                        resultdrawArr.push(polyline_);
                    }
                } else { //0부터 시작하는 경우

                    var trafficObject = "";
                    var tInfo = [];

                    for (var z = 0; z < traffic.length; z++) {
                        trafficObject = {
                            "startIndex" : traffic[z][0],
                            "endIndex" : traffic[z][1],
                            "trafficIndex" : traffic[z][2],
                        };
                        tInfo.push(trafficObject)
                    }

                    for (var x = 0; x < tInfo.length; x++) {
                        var sectionPoint = []; //구간선언

                        for (var y = tInfo[x].startIndex; y <= tInfo[x].endIndex; y++) {
                            sectionPoint.push(arrPoint[y]);
                        }

                        if (tInfo[x].trafficIndex == 0) {
                            lineColor = "#06050D";
                        } else if (tInfo[x].trafficIndex == 1) {
                            lineColor = "#61AB25";
                        } else if (tInfo[x].trafficIndex == 2) {
                            lineColor = "#FFFF00";
                        } else if (tInfo[x].trafficIndex == 3) {
                            lineColor = "#E87506";
                        } else if (tInfo[x].trafficIndex == 4) {
                            lineColor = "#D61125";
                        }

                        //라인그리기[S]
                        polyline_ = new kakao.maps.Polyline({
                            path : sectionPoint,
                            strokeColor : lineColor,
                            strokeWeight : 6,
                            map : map
                        });
                        //라인그리기[E]
                        resultdrawArr.push(polyline_);
                    }
                }
            }
        } else {

        }
    } else {
        polyline_ = new kakao.maps.Polyline({
            path : arrPoint,
            strokeColor : "#4a6eff",
            strokeWeight : 3,
            strokeOpacity : 0.8,
            map : map
        });
        resultdrawArr.push(polyline_);
    }

}

//초기화 기능
function resettingMap() {
    //기존마커는 삭제
    marker_s.setMap(null);
    marker_e.setMap(null);
    customOverlay.setMap(null);
    customOverlay_start.setMap(null);
    customOverlay_end.setMap(null);

    if (resultMarkerArr.length > 0) {
        for (var i = 0; i < resultMarkerArr.length; i++) {
            resultMarkerArr[i].setMap(null);
        }
    }

    if (resultdrawArr.length > 0) {
        for (var i = 0; i < resultdrawArr.length; i++) {
            resultdrawArr[i].setMap(null);
        }
    }

    chktraffic = [];
    drawInfoArr = [];
    resultMarkerArr = [];
    resultdrawArr = [];
}

function summarizeCarResult() {
    carInfoArr = carInfo.split('|');
    carTrafficArr = carInfoArr[0].split('/');
    carSummaryArr = carInfoArr[1].split('/');
    carNumArr = carInfoArr[2].split('/');

    // 자세한 정보
    for(var i = 0; i < carTrafficArr.length-1; i++) {
        carTrafficArr[i] = carTrafficArr[i].split(',');
    }

    //요약 정보
    for(var j = 0; j < carSummaryArr.length-1; j++) {
        carSummaryArr[j] = carSummaryArr[j].split(',');
    }

    //교통량 정보
    for(var j = 0; j < carNumArr.length-1; j++) {
        carNumArr[j] = carNumArr[j].split(',');
    }

    console.log(carTrafficArr);
    console.log(carSummaryArr);
    console.log(carNumArr)

    var carRouteName = '';

    for(var j = 0; j < carSummaryArr.length-1; j++) {
        carRouteName += (carSummaryArr[j][0].split("부근"))[0];
        if(j != carSummaryArr.length-2 ){
            carRouteName += ' ';
        }
    }
    if(carSummaryArr.length != 1){
        showCarTrafficChart(carNumArr);
        $('#detail-route').show();
        $('#show_route').show();
        $('#traffic-chart-title').show();
        document.getElementById("show_route").innerText = carRouteName;
    }else{
        $('#detail-route').show();
        $('#show_route').show();
        document.getElementById("show_route").innerText = "상세 경로 표시하기에 짧은 거리 입니다.";
    }

}
function showCarTrafficChart(carNumArr){
    var len = carNumArr.length;
    drawTrafficChart(carNumArr, len);
}