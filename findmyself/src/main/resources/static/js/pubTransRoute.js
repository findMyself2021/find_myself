var sxArr = [], syArr = [];
var exArr = [], eyArr = [];
var startArrIdx = 0, endArrIdx = 0;
var test = 0;
var subwayStationResults = "";
var stationInfo = ""; // 대중교통 경로 분석 결과 요약 데이터
var stationInfoArr, stationTrafficArr, stationSummaryArr, stationNumArr;

// 대중교통 길찾기 지도에 표시하는 함수
function searchPubTransRoute(sx, sy, ex, ey) {

    $("#btn_select2")
        .click(
            function () {
                resettingMap();

                function searchPubTransPathAJAX() {
                    var xhr = new XMLHttpRequest();
                    //ODsay apiKey 입력
                    var url = "https://api.odsay.com/v1/api/searchPubTransPath?SX="+sx+"&SY="+sy+"&EX="+ex+"&EY="+ey+"&apiKey=VYYJtQrZq5ere3U%2BvOoPhLmqgvRTrFzcpLKrRaKvpcQ";
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
                    var url = "https://api.odsay.com/v1/api/loadLane?mapObject=0:0@"+mabObj+"&apiKey=VYYJtQrZq5ere3U%2BvOoPhLmqgvRTrFzcpLKrRaKvpcQ";
                    xhr.open("POST", url, true);
                    xhr.send();
                    xhr.onreadystatechange = function() {
                        if (xhr.readyState == 4 && xhr.status == 200) {
                            var resultJsonData = JSON.parse(xhr.responseText);

                            //즉시 실행 함수로 closure 발생 방지
                            (function () {
                                var currentsx = sx;
                                var currentsy = sy;
                                var currentex = ex;
                                var currentey = ey;
                                var currentResultJsonData = resultJsonData;

                                //도보 경로 표시와 시간 맞추기
                                setTimeout(function () {
                                    drawKakaoMarker(currentsx,currentsy, 0);					// 출발지 마커 표시
                                    drawKakaoMarker(currentex,currentey, 1);					// 도착지 마커 표시
                                    drawKakaoPolyLine(currentResultJsonData);
                                }, 1000);
                            }());
                        }
                    }
                }

            });

    // 지도위 마커 표시해주는 함수
    function drawKakaoMarker(x,y, position){
        var markerImage = new kakao.maps.MarkerImage(start_marker,size_marker);

        if(position == 0) {
            marker_s = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(y, x),
                image : markerImage,
                map : map
            });
        } else {
            markerImage = new kakao.maps.MarkerImage(end_marker,size_marker);
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

                //지하철결과, 버스결과의 경우 노선에 따른 라인색상 지정하는 부분 (1~9호선 색상 추가)
                if(data.result.lane[i].type === 1){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight : 3,
                        strokeOpacity : 0.8,
                        strokeColor: '#0D347F'
                    });
                }else if(data.result.lane[i].type === 2){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight : 3,
                        strokeOpacity : 0.8,
                        strokeColor: '#3B9F37'
                    });
                }else if(data.result.lane[i].type === 3){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight : 3,
                        strokeOpacity : 0.8,
                        strokeColor: '#fc4c02'
                    });
                }else if(data.result.lane[i].type == 4){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight : 3,
                        strokeOpacity : 0.8,
                        strokeColor: '#3165A8'
                    });
                }else if(data.result.lane[i].type == 5){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight : 3,
                        strokeOpacity : 0.8,
                        strokeColor: '#a05eb5'
                    });
                }else if(data.result.lane[i].type == 6){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight : 3,
                        strokeOpacity : 0.8,
                        strokeColor: '#904D23'
                    });
                }else if(data.result.lane[i].type == 7){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight : 3,
                        strokeOpacity : 0.8,
                        strokeColor: '#5B692E'
                    });
                }else if(data.result.lane[i].type == 8){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight : 3,
                        strokeOpacity : 0.8,
                        strokeColor: '#e31c79'
                    });
                }else if(data.result.lane[i].type == 9){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight : 3,
                        strokeOpacity : 0.8,
                        strokeColor: '#B39627'
                    });
                }else{ // 버스의 경우
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight : 3,
                        strokeOpacity : 0.8,
                        strokeColor: '#3673dd'
                    });
                }

                resultdrawArr.push(polyline);
            }
        }
    }
}

// 대중교통 길찾기 경로내의 지하철역 이름 검색하는 함수
function searchSubwayStations(sx, sy, ex, ey) {
    $("#btn_select2")
        .click(
            function () {

                // 출발 위치
                var sx1 = 126.99332009924663;
                var sy1 = 37.56093749910637;

                clearSEArray();
                insertSEArray(sx, sy, 0);

                var xhr = new XMLHttpRequest();
                var url = "https://api.odsay.com/v1/api/searchPubTransPathT?apiKey=VYYJtQrZq5ere3U%2BvOoPhLmqgvRTrFzcpLKrRaKvpcQ&lang=0&SX=" + sx + "&SY=" + sy + "&EX=" + ex + "&EY=" + ey + "&SearchPathType=0";
                xhr.open("GET", url, true);
                xhr.send();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        var resultObj = JSON.parse(xhr.responseText);
                        console.log(resultObj.result);

                        //경유역, 정류장, 환승회수 카운트
                        var station_cnt = 0, bus_cnt = 0, transfer_cnt = -1;

                        var stationResult = "";
                        var pathGuide = "";
                        var dupCheck = ""; // 환승시 중복역 확인
                        var totalTime = "총 소요시간 : " + resultObj.result.path[0].info.totalTime + "분";
                        for (var i = 0; i < resultObj.result.path[0].subPath.length; i++) {
                            if(resultObj.result.path[0].subPath[i].trafficType === 1) {
                                for(var j = 0; j < resultObj.result.path[0].subPath[i].passStopList.stations.length; j++) {
                                    if(dupCheck !== resultObj.result.path[0].subPath[i].passStopList.stations[j].stationName) {
                                        stationResult += resultObj.result.path[0].subPath[i].passStopList.stations[j].stationName + "/";
                                        station_cnt++;
                                    }
                                    dupCheck = resultObj.result.path[0].subPath[i].passStopList.stations[j].stationName;
                                }
                                insertSEArray(resultObj.result.path[0].subPath[i].startX, resultObj.result.path[0].subPath[i].startY, 1);
                                insertSEArray(resultObj.result.path[0].subPath[i].endX, resultObj.result.path[0].subPath[i].endY, 0);
                            } else if(resultObj.result.path[0].subPath[i].trafficType === 2) {
                                insertSEArray(resultObj.result.path[0].subPath[i].startX, resultObj.result.path[0].subPath[i].startY, 1);
                                insertSEArray(resultObj.result.path[0].subPath[i].endX, resultObj.result.path[0].subPath[i].endY, 0);
                            }

                            if(i !== resultObj.result.path[0].subPath.length-1) {
                                if(resultObj.result.path[0].subPath[i].trafficType === 1) {
                                    pathGuide += resultObj.result.path[0].subPath[i].startName + "역에서 "
                                        + resultObj.result.path[0].subPath[i].endName + "역으로 지하철 탑승하여 "
                                        + resultObj.result.path[0].subPath[i].sectionTime + "분 가량 이동 -> ";
                                    transfer_cnt++;
                                } else if (resultObj.result.path[0].subPath[i].trafficType === 2) {
                                    pathGuide += resultObj.result.path[0].subPath[i].startName + " 정거장에서 "
                                        + resultObj.result.path[0].subPath[i].endName + " 정거장으로 버스 탑승하여 "
                                        + resultObj.result.path[0].subPath[i].sectionTime + "분 가량 이동 -> ";
                                    bus_cnt += resultObj.result.path[0].subPath[i].passStopList.stations.length;
                                    transfer_cnt++;
                                } else {
                                    pathGuide += resultObj.result.path[0].subPath[i].sectionTime + "분 가량 도보 이동 -> "
                                }
                            } else {
                                if(resultObj.result.path[0].subPath[i].trafficType === 1) {
                                    pathGuide += resultObj.result.path[0].subPath[i].startName + "역에서 "
                                        + resultObj.result.path[0].subPath[i].endName + "역으로 지하철 탑승하여 "
                                        + resultObj.result.path[0].subPath[i].sectionTime + "분 가량 이동 후 목적지 도착 ";
                                    transfer_cnt++;
                                } else if (resultObj.result.path[0].subPath[i].trafficType === 2) {
                                    pathGuide += resultObj.result.path[0].subPath[i].startName + " 정거장에서 "
                                        + resultObj.result.path[0].subPath[i].endName + " 정거장으로 버스 탑승하여 "
                                        + resultObj.result.path[0].subPath[i].sectionTime + "분 가량 이동 후 목적지 도착 ";
                                    bus_cnt += resultObj.result.path[0].subPath[i].passStopList.stations.length;
                                    transfer_cnt++;
                                } else {
                                    pathGuide += resultObj.result.path[0].subPath[i].sectionTime + "분 가량 도보 이동 후 목적지 도착"
                                }
                            }
                        }
                        if(transfer_cnt === -1)
                            transfer_cnt = 0;

                        var content = '<div class="overlaybox">' +
                            '<div class="boxtitle">대중교통 경로 검색 결과</div>'+
                            '   <ul>' +
                            '       <li class="up">' +
                            '           <span class="title">'+totalTime+'</span>'+
                            '       </li>'+
                            '       <li>'+
                            '           <span class="title" id="route_num_cnt">'+"경유 횟수 : 지하철"+station_cnt+"개, 버스 "+bus_cnt+"개"+'</span>'+
                            '       </li>'+
                            '       <li>'+
                            '          <span class="title">'+"환승 횟수 : "+transfer_cnt+"번"+'</span>'+
                            '       </li>'+
                            '   </ul>'+
                            '</div>';


                        insertSEArray(ex, ey, 1);

                        // 도착지가 출발지보다 아래에 있으면 => 커스텀 오버레이 밑으로 나타나게 하기
                        if(sy>ey){
                            var yanchor = 0;
                        }
                        else{
                            var yanchor = 1.2;
                        }

                        // 도착지가 왼쪽이라면
                        if(sx>ex){
                            var xanchor = 1;
                        }
                        else{
                            var xanchor = 0;
                        }

                        // 커스텀 오버레이를 생성합니다
                        customOverlay = new kakao.maps.CustomOverlay({
                            position: new kakao.maps.LatLng(ey,ex),
                            content: content,
                            //왼쪽 오른쪽
                            xAnchor: xanchor,
                            //위 아래
                            yAnchor: yanchor
                        });

                        customOverlay.setMap(map);

                        // 대중교통 결과 띄움
                        // $("#subwayResult").text(str);

                        // console.log(str);

                        //약간의 좌표 오차 어떻게 처리할 지 생각하기
                        for(var i = 0; i < startArrIdx; i++) {
                            // Tmap 경로찾기 초당 처리횟수 2회 제한
                            if(i > 3) {
                                //즉시 실행 함수로 closure 발생 방지
                                (function () {
                                    var currentI = i;
                                    setTimeout(function () {
                                        searchWalkRoute(sxArr[currentI], syArr[currentI], exArr[currentI], eyArr[currentI], 2);
                                        console.log(sxArr[currentI]);
                                    }, 2000);
                                }());
                            } else if(i === 2 || i === 3) {
                                //즉시 실행 함수로 closure 발생 방지
                                (function () {
                                    var currentI2 = i;
                                    setTimeout(function () {
                                        searchWalkRoute(sxArr[currentI2], syArr[currentI2], exArr[currentI2], eyArr[currentI2], 2);
                                        console.log(sxArr[currentI2]);
                                    }, 1000);
                                }());
                            } else {
                                searchWalkRoute(sxArr[i], syArr[i], exArr[i], eyArr[i], 1);
                                console.log(sxArr[i]);
                            }

                            //오류 확인용
                            console.log(test);
                            test++;
                            if(test > 30)
                                break;
                        }
                        console.log(stationResult);
                        console.log(totalTime);
                        console.log(pathGuide);

                        $('#detail-route').hide();
                        $('#show_route').hide();
                        document.getElementById("show_route").innerText = pathGuide;

                        subwayStationResults = stationResult;
                    }
                }
            });
}

// 보행자 길찾기 경로 탐색 함수
function searchWalkRoute(sx, sy, ex, ey, option) {

    var drawInfoArr = [];

    searchRoute(sx, sy, ex, ey, option);

    function searchRoute(sx, sy, ex, ey, option) {

        //기존 맵에 있던 정보들 초기화
        if(option === 0) {
            resettingMap();
        }

        //JSON TYPE EDIT [S]
        $
            .ajax({
                method : "POST",
                url : "https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&format=json&callback=result",
                async : false,
                data : {
                    "appKey" : "l7xxb9472bf6b9384348b0109b1ba92a9c43",
                    "startX" : sx,
                    "startY" : sy,
                    "endX" : ex,
                    "endY" : ey,
                    "reqCoordType" : "WGS84GEO",
                    "resCoordType" : "EPSG3857",
                    "startName" : "출발지",
                    "endName" : "도착지"
                },
                success : function(response) {
                    var resultData = response.features;

                    drawInfoArr = [];

                    for ( var i in resultData) { //for문 [S]
                        var geometry = resultData[i].geometry;

                        if (geometry.type == "LineString") {
                            for ( var j in geometry.coordinates) {
                                // 경로들의 결과값(구간)들을 포인트 객체로 변환
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
                                drawInfoArr.push(convertChange);
                            }
                        }
                    }//for문 [E]

                    //즉시 실행 함수로 closure 발생 방지
                    if(option === 0) {
                        (function () {
                            var currentDrawInfoArr = drawInfoArr;

                            //두번째 이후의 도보 경로 표시와 시간 맞추기
                            setTimeout(function () {
                                drawLine(currentDrawInfoArr);
                            }, 1300);
                        }());
                    } else if (option === 1) {
                        (function () {
                            var currentDrawInfoArr = drawInfoArr;

                            //두번째 이후의 도보 경로 표시와 시간 맞추기
                            setTimeout(function () {
                                drawLine(currentDrawInfoArr);
                            }, 1000);
                        }());
                    } else {
                        drawLine(drawInfoArr);
                    }
                },
                error : function(request, status, error) {
                    console.log("code:" + request.status + "\n"
                        + "message:" + request.responseText + "\n"
                        + "error:" + error);
                }
            });
    }

    function drawLine(arrPoint) {
        var polyline_;

        polyline_ = new kakao.maps.Polyline({
            path : arrPoint,
            strokeColor : "#DD0000",
            strokeWeight : 3,
            strokeOpacity : 0.8,
            map: map
        });
        resultdrawArr.push(polyline_);
    }
}

function insertSEArray(x, y, select) {

    if(select == 0) {
        sxArr[startArrIdx] = x;
        syArr[startArrIdx] = y;
        startArrIdx++;
        console.log('start success');
    } else if(select == 1) {
        exArr[endArrIdx] = x;
        eyArr[endArrIdx] = y;
        endArrIdx++;
        console.log('end success');
    } else {
        console.log('insertSEArray Error');
    }
}

function  clearSEArray() {
    sxArr = [];
    syArr = [];
    exArr = [];
    eyArr = [];
    startArrIdx = 0;
    endArrIdx = 0;
}

function summarizePubResult() {
    stationInfoArr = stationInfo.split('|');
    stationTrafficArr = stationInfoArr[0].split('/');
    stationSummaryArr = stationInfoArr[1].split('/');
    stationNumArr = stationInfoArr[2].split('/');

    // 자세한 정보
    for(var i = 0; i < stationTrafficArr.length-1; i++) {
        stationTrafficArr[i] = stationTrafficArr[i].split(',');
    }

    // 요약 정보
    for(var j = 0; j < stationSummaryArr.length-1; j++) {
        stationSummaryArr[j] = stationSummaryArr[j].split(',');
    }

    // 승하차인원 정보
    for(var j = 0; j < stationNumArr.length-1; j++) {
        stationNumArr[j] = stationNumArr[j].split(',');
    }

    console.log(stationTrafficArr);
    console.log(stationSummaryArr);
    console.log(stationNumArr);

    showPubTrafficChart(stationNumArr);

    var pubRouteName = '';

    $('#detail-route').show();
    $('#show_route').show();
    $('#traffic-chart-title').show();
}
function showPubTrafficChart(carNumArr){
    var len = carNumArr.length;
    drawTrafficChart_pub(carNumArr, len);
}