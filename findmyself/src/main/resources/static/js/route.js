var markerInfo;
//출발지,도착지 마커
var marker_s, marker_e, marker_p;
//경로그림정보
var drawInfoArr = [];
var drawInfoArr2 = [];

var chktraffic = [];
var resultdrawArr = [];
var resultMarkerArr = [];

//목적지 마커 표시
function initCarSearch(addr){
    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();

// 주소로 좌표를 검색합니다
    geocoder.addressSearch(addr, function(result, status) {

        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {

            // 목적지 좌표
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            var size = new kakao.maps.Size(25, 32);//아이콘 크기 설정합니다.
            var img= '/image/marker_icon-icons.com_54388.png';

            var markerImage = new kakao.maps.MarkerImage(img,size);

            // 결과값으로 받은 위치를 마커로 표시합니다 (도착지)
            marker_e = new kakao.maps.Marker({
                map: map,
                position: coords,
                image: markerImage
            });

            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);

            //도착지 좌표값 전달
            initTmap(result[0].y,result[0].x);
            searchPubTransRoot(result[0].x, result[0].y);
            searchSubwayStations(result[0].x, result[0].y);
        }
        else{
            alert('도로명 주소를 입력해주세요');
        }
    });
}

// 나중에 값 전달받기
function initTmap(endX,endY) {

    // 출발지점

    var size = new kakao.maps.Size(25, 32);//아이콘 크기 설정합니다.
    var img= '/image/marker_icon-icons.com_54388.png';

    var markerImage = new kakao.maps.MarkerImage(img,size);

    marker_s = new kakao.maps.Marker(
        {
            position : new kakao.maps.LatLng(37.56093749910637,
                126.99332009924663),
            image: markerImage,
            map : map
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
                            "startX" : "126.99332009924663",
                            "startY" : "37.56093749910637",
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
                                '           <span class="title">'+tDistance+'</span>'+
                                '       </li>'+
                                '       <li>'+
                                '           <span class="title">'+tTime+'</span>'+
                                '       </li>'+
                                '       <li>'+
                                '           <span class="title">'+taxiFare+'</span>'+
                                '       </li>'+
                                '   </ul>'+
                                '</div>';


                            // 커스텀 오버레이를 생성합니다
                            var customOverlay = new kakao.maps.CustomOverlay({
                                position: new kakao.maps.LatLng(endX,endY),
                                content: content,
                                //왼쪽 오른쪽
                                xAnchor: 0,
                                //위 아래
                                yAnchor: 1.2
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
                                    } else {

                                        var markerImg = "";
                                        var pType = "";

                                        if (properties.pointType == "S") { //출발지 마커
                                            markerImg = "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png";
                                            pType = "S";
                                        } else if (properties.pointType == "E") { //도착지 마커
                                            markerImg = "http://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png";
                                            pType = "E";
                                        } else { //각 포인트 마커
                                            markerImg = "http://topopen.tmap.co.kr/imgs/point.png";
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
                                        // 마커 추가
                                        addMarkers(routeInfoObj);
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
                                            markerImg = "/image/marker_icon-icons.com_54388.png";
                                            pType = "S";
                                        } else if (properties.pointType == "E") { //도착지 마커
                                            markerImg = "/image/marker_icon-icons.com_54388.png";
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

function addComma(num) {
    var regexp = /\B(?=(\d{3})+(?!\d))/g;
    return num.toString().replace(regexp, ',');
}

//마커 생성하기
function addMarkers(infoObj) {
    var size = new kakao.maps.Size(25, 32);//아이콘 크기 설정합니다.

    if (infoObj.pointType == "P") { //포인트점일때는 아이콘 크기를 줄입니다.
        size = new kakao.maps.Size(8, 8);
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
            strokeColor : "#51BBA8",
            strokeWeight : 3,
            map : map
        });
        resultdrawArr.push(polyline_);
    }

}

// 대중교통 길찾기 지도에 표시하는 함수
function searchPubTransRoot(ex, ey) {

    // 출발 위치
    var sx1 = 126.99332009924663;
    var sy1 = 37.56093749910637;
    // 도착 위치

    $("#btn_select2")
        .click(
            function () {
                resettingMap();

                function searchPubTransPathAJAX() {
                    var xhr = new XMLHttpRequest();
                    //ODsay apiKey 입력
                    var url = "https://api.odsay.com/v1/api/searchPubTransPath?SX="+sx1+"&SY="+sy1+"&EX="+ex+"&EY="+ey+"&apiKey=VYYJtQrZq5ere3U%2BvOoPhLmqgvRTrFzcpLKrRaKvpcQ";
                    xhr.open("GET", url, true);
                    xhr.send();
                    xhr.onreadystatechange = function() {
                        if (xhr.readyState == 4 && xhr.status == 200) {
                            console.log( JSON.parse(xhr.responseText) ); // <- xhr.responseText 로 결과를 가져올 수 있음
                            //노선그래픽 데이터 호출
                            callMapObjApiAJAX((JSON.parse(xhr.responseText))["result"]["path"][0].info.mapObj);
                        }
                    }
                }

                //길찾기 API 호출
                searchPubTransPathAJAX();

                function callMapObjApiAJAX(mabObj){
                    var xhr = new XMLHttpRequest();
                    //ODsay apiKey 입력
                    var url = "https://api.odsay.com/v1/api/loadLane?apiKey=VYYJtQrZq5ere3U%2BvOoPhLmqgvRTrFzcpLKrRaKvpcQ&mapObject=0:0@"+mabObj+"&apiKey={YOUR_API_KEY}";
                    xhr.open("POST", url, true);
                    xhr.send();
                    xhr.onreadystatechange = function() {
                        if (xhr.readyState == 4 && xhr.status == 200) {
                            var resultJsonData = JSON.parse(xhr.responseText);
                            drawKakaoMarker(sx1,sy1, 0);					// 출발지 마커 표시
                            drawKakaoMarker(ex,ey, 1);					// 도착지 마커 표시
                            drawKakaoPolyLine(resultJsonData);		// 노선그래픽데이터 지도위 표시
                            // boundary 데이터가 있을경우, 해당 boundary로 지도이동
                            if(resultJsonData.result.boundary){
                                var boundary = new kakao.maps.LatLngBounds(
                                    new kakao.maps.LatLng(resultJsonData.result.boundary.top, resultJsonData.result.boundary.left),
                                    new kakao.maps.LatLng(resultJsonData.result.boundary.bottom, resultJsonData.result.boundary.right)
                                );
                                map.panToBounds(boundary);
                            }
                        }
                    }
                }

            });

    // 지도위 마커 표시해주는 함수
    function drawKakaoMarker(x,y, position){
        var size = new kakao.maps.Size(25, 32);
        var markerImage = new kakao.maps.MarkerImage("/image/marker_icon-icons.com_54388.png", size);

        if(position == 0) {
            marker_s = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(y, x),
                image : markerImage,
                map : map
            });
        } else {
            marker_e = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(y, x),
                image : markerImage,
                map : map
            });
        }
    }

    // 노선그래픽 데이터를 이용하여 지도위 폴리라인 그려주는 함수
    function drawKakaoPolyLine(data){
        var lineArray;

        for(var i = 0 ; i < data.result.lane.length; i++){
            for(var j=0 ; j <data.result.lane[i].section.length; j++){
                lineArray = null;
                lineArray = new Array();
                for(var k=0 ; k < data.result.lane[i].section[j].graphPos.length; k++){
                    lineArray.push(new kakao.maps.LatLng(data.result.lane[i].section[j].graphPos[k].y, data.result.lane[i].section[j].graphPos[k].x));
                }

                //지하철결과의 경우 노선에 따른 라인색상 지정하는 부분 (1~9호선 색상 추가 / 도보 표시 수정 필요)
                if(data.result.lane[i].type === 1){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3,
                        strokeColor: '#003499'
                    });
                }else if(data.result.lane[i].type === 2){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3,
                        strokeColor: '#37b42d'
                    });
                }else if(data.result.lane[i].type === 3){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3,
                        strokeColor: '#fc4c02'
                    });
                }else if(data.result.lane[i].type == 4){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3,
                        strokeColor: '#00a9e0'
                    });
                }else if(data.result.lane[i].type == 5){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3,
                        strokeColor: '#a05eb5'
                    });
                }else if(data.result.lane[i].type == 6){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3,
                        strokeColor: '#a9431e'
                    });
                }else if(data.result.lane[i].type == 7){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3,
                        strokeColor: '#67823a'
                    });
                }else if(data.result.lane[i].type == 8){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3,
                        strokeColor: '#e31c79'
                    });
                }else if(data.result.lane[i].type == 9){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3,
                        strokeColor: '#8c8279'
                    });
                }else{
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3,
                        strokeColor: '#b2ccff'
                    });
                }

                resultdrawArr.push(polyline);
            }
        }
    }
}

// 대중교통 길찾기 경로내의 지하철역 이름 검색하는 함수
function searchSubwayStations(ex, ey) {
    $("#btn_select2")
        .click(
            function () {

                // 출발 위치
                var sx1 = 126.93737555322481;
                var sy1 = 37.55525165729346;

                var xhr = new XMLHttpRequest();
                //url => searchPath=1 -> 지하철 한정 (추후 수정)
                var url = "https://api.odsay.com/v1/api/searchPubTransPathT?apiKey=VYYJtQrZq5ere3U%2BvOoPhLmqgvRTrFzcpLKrRaKvpcQ&lang=0&SX=" + sx1 + "&SY=" + sy1 + "&EX=" + ex + "&EY=" + ey + "&SearchPathType=1";
                xhr.open("GET", url, true);
                xhr.send();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        var resultObj = JSON.parse(xhr.responseText);
                        console.log(resultObj.result);
                        var resultArr = resultObj["result"]["path"];
                        console.log(resultArr);

                        var str = "";
                        for (var i = 0; i < resultArr.length; i++) {
                            if(resultArr[0].subPath[i].trafficType == 1) {
                                for(var j = 0; j < resultArr[0].subPath[i].passStopList.stations.length - 1; j++) {
                                    str += "역이름 : " + resultArr[0].subPath[i].passStopList.stations[j].stationName + "/";
                                }
                                str += "역이름 : " + resultArr[0].subPath[i].passStopList.stations[j].stationName;
                            }
                        }
                        $("#subwayResult").text(str);
                    }
                }
            });
}

//초기화 기능
function resettingMap() {
    //기존마커는 삭제
    marker_s.setMap(null);
    marker_e.setMap(null);

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