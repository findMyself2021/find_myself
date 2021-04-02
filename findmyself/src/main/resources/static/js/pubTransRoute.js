var sxArr = [], syArr = [];
var exArr = [], eyArr = [];
var startArrIdx = 0, endArrIdx = 0;
var test = 0;

// 대중교통 길찾기 지도에 표시하는 함수
function searchPubTransRoute(ex, ey) {

    // 출발 위치
    var sx1 = 126.99332009924663;
    var sy1 = 37.56093749910637;

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
                    var url = "https://api.odsay.com/v1/api/loadLane?mapObject=0:0@"+mabObj+"&apiKey=VYYJtQrZq5ere3U%2BvOoPhLmqgvRTrFzcpLKrRaKvpcQ";
                    xhr.open("POST", url, true);
                    xhr.send();
                    xhr.onreadystatechange = function() {
                        if (xhr.readyState == 4 && xhr.status == 200) {
                            var resultJsonData = JSON.parse(xhr.responseText);

                            //즉시 실행 함수로 closure 발생 방지
                            (function () {
                                var currentsx1 = sx1;
                                var currentsy1 = sy1;
                                var currentex = ex;
                                var currentey = ey;
                                var currentResultJsonData = resultJsonData;

                                //도보 경로 표시와 시간 맞추기
                                setTimeout(function () {
                                    drawKakaoMarker(currentsx1,currentsy1, 0);					// 출발지 마커 표시
                                    drawKakaoMarker(currentex,currentey, 1);					// 도착지 마커 표시
                                    drawKakaoPolyLine(currentResultJsonData);
                                }, 1000);
                            }());
                            //drawKakaoMarker(sx1,sy1, 0);					// 출발지 마커 표시
                            //drawKakaoMarker(ex,ey, 1);					// 도착지 마커 표시
                            //drawKakaoPolyLine(resultJsonData);		// 노선그래픽데이터 지도위 표시
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

                //지하철결과, 버스결과의 경우 노선에 따른 라인색상 지정하는 부분 (1~9호선 색상 추가)
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
                }else{ // 버스의 경우
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3,
                        strokeColor: '#86E57F'
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
                var sx1 = 126.99332009924663;
                var sy1 = 37.56093749910637;

                clearSEArray();
                insertSEArray(sx1, sy1, 0);

                var xhr = new XMLHttpRequest();
                var url = "https://api.odsay.com/v1/api/searchPubTransPathT?apiKey=VYYJtQrZq5ere3U%2BvOoPhLmqgvRTrFzcpLKrRaKvpcQ&lang=0&SX=" + sx1 + "&SY=" + sy1 + "&EX=" + ex + "&EY=" + ey + "&SearchPathType=0";
                xhr.open("GET", url, true);
                xhr.send();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        var resultObj = JSON.parse(xhr.responseText);
                        console.log(resultObj.result);

                        var str = "";
                        for (var i = 0; i < resultObj.result.path[0].subPath.length; i++) {
                            if(resultObj.result.path[0].subPath[i].trafficType == 1) {
                                for(var j = 0; j < resultObj.result.path[0].subPath[i].passStopList.stations.length - 1; j++) {
                                    str += "역이름 : " + resultObj.result.path[0].subPath[i].passStopList.stations[j].stationName + "/";
                                }
                                str += "역이름 : " + resultObj.result.path[0].subPath[i].passStopList.stations[j].stationName;
                                insertSEArray(resultObj.result.path[0].subPath[i].startX, resultObj.result.path[0].subPath[i].startY, 1);
                                insertSEArray(resultObj.result.path[0].subPath[i].endX, resultObj.result.path[0].subPath[i].endY, 0);
                            } else if(resultObj.result.path[0].subPath[i].trafficType == 2) {
                                insertSEArray(resultObj.result.path[0].subPath[i].startX, resultObj.result.path[0].subPath[i].startY, 1);
                                insertSEArray(resultObj.result.path[0].subPath[i].endX, resultObj.result.path[0].subPath[i].endY, 0);
                            }
                        }
                        insertSEArray(ex, ey, 1);

                        $("#subwayResult").text(str);
                        console.log(str);

                        //약간의 좌표 오차 어떻게 처리할 지 생각하기
                        for(var i = 0; i < startArrIdx; i++) {
                            // Tmap 경로찾기 초당 처리횟수 2회 제한
                            if(i > 1) {
                                //즉시 실행 함수로 closure 발생 방지
                                (function () {
                                    var currentI = i;
                                    setTimeout(function () {
                                        searchWalkRoute(sxArr[currentI], syArr[currentI], exArr[currentI], eyArr[currentI], 2);
                                        console.log(sxArr[currentI]);
                                    }, 1000);
                                }());
                            } else if(i == 0){
                                searchWalkRoute(sxArr[i], syArr[i], exArr[i], eyArr[i], 0);
                                console.log(sxArr[i]);
                            } else {
                                searchWalkRoute(sxArr[i], syArr[i], exArr[i], eyArr[i], 1);
                                console.log(sxArr[i]);
                            }
                            console.log(i);
                            console.log(test);
                        }
                    }
                }
            });
}

// 보행자 길찾기 경로 탐색 함수
function searchWalkRoute(sx, sy, ex, ey, option) {

    var drawInfoArr = [];

    searchRoute(sx, sy, ex, ey, option);

    function searchRoute(sx, sy, ex, ey, option) {

        var size = new kakao.maps.Size(25, 32);//아이콘 크기 설정.
        var img= '/image/marker_icon-icons.com_54388.png';

        var markerImage = new kakao.maps.MarkerImage(img,size);

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