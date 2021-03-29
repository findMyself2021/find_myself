function searchSubwayStationAJAX() {
    //var stationName = document.getElementById('stationName').value;
    var xhr = new XMLHttpRequest();
    var url = "https://api.odsay.com/v1/api/searchStation?apiKey=VYYJtQrZq5ere3U%2BvOoPhLmqgvRTrFzcpLKrRaKvpcQ&lang=0&stationName=종로3가&stationClass=2";
    xhr.open("GET", url, true);
    xhr.send();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var resultObj = JSON.parse(xhr.responseText);
            console.log(resultObj.result);
            var resultArr = resultObj["result"]["station"];
            console.log(resultArr);

            var subwayNameArr = [];
            var subwayXArr = [];
            var subwayYArr = [];

            for (var i = 0; i < resultArr.length; i++) {
                subwayNameArr[i] = resultArr[i].stationName;
                subwayXArr[i] = resultArr[i].x;
                subwayYArr[i] = resultArr[i].y;
            }
        }
    }
}
function searchPubTransRoot(sx, sy, ex, ey) {

    // 출발 위치
    var sx1 = 126.93737555322481;
    var sy1 = 37.55525165729346;
    // 도착 위치
    var ex1 = 126.88265238619182;
    var ey1 = 37.481440035175375;

    $("#btn_select2")
        .click(
            function () {
                function searchPubTransPathAJAX() {
                    var xhr = new XMLHttpRequest();
                    //ODsay apiKey 입력
                    var url = "https://api.odsay.com/v1/api/searchPubTransPath?apiKey=VYYJtQrZq5ere3U%2BvOoPhLmqgvRTrFzcpLKrRaKvpcQ&SX="+sx1+"&SY="+sy1+"&EX="+ex1+"&EY="+ey1+"&apiKey={YOUR_API_KEY}";
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
                    xhr.open("GET", url, true);
                    xhr.send();
                    xhr.onreadystatechange = function() {
                        if (xhr.readyState == 4 && xhr.status == 200) {
                            var resultJsonData = JSON.parse(xhr.responseText);
                            drawKakaoMarker(sx1,sy1);					// 출발지 마커 표시
                            drawKakaoMarker(ex1,ey1);					// 도착지 마커 표시
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
    function drawKakaoMarker(x,y){
        var marker = new kakao.maps.Marker({
            position: new kakao.maps.LatLng(y, x),
            map : map
        });
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

                //지하철결과의 경우 노선에 따른 라인색상 지정하는 부분 (1,2호선의 경우만 예로 들음)
                if(data.result.lane[i].type == 1){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3,
                        strokeColor: '#003499'
                    });
                }else if(data.result.lane[i].type == 2){
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3,
                        strokeColor: '#37b42d'
                    });
                }else{
                    var polyline = new kakao.maps.Polyline({
                        map: map,
                        path: lineArray,
                        strokeWeight: 3
                    });
                }
            }
        }
    }
}