
function DrawPolygon(mapno) {

    //한국 전체 폴리곤 생성
    $.getJSON("/json/koreaMap.json", function(geojson) {

        var data = geojson.features;
        var coordinates = [];    //좌표 저장할 배열

        $.each(data, function(index, val) {

            coordinates = val.geometry.coordinates;

            if(val.geometry.type =="MultiPolygon")
            {
                displayKorea(coordinates,true,mapno);
            }
            else
            {
                displayKorea(coordinates,false,mapno);
            }
        });
    });

    //서울 전체 폴리곤 생성
    $.getJSON("/json/seoulCity.json", function(geojson) {

        var data = geojson.features;
        var coordinates = [];    //좌표 저장할 배열

        $.each(data, function(index, val) {

            coordinates = val.geometry.coordinates;

            displayArea(coordinates,mapno);
        });
    });
}

// 한국 폴리곤 => 어둡게
function displayKorea(coordinates,multi,mapno) {

    if(multi)
    {
        polygon = makeMultiPolygon(coordinates);
    }
    else
    {
        polygon = makePolygon(coordinates);
    }

    if(mapno==1){
        polygon.setMap(map1);
    }
    else{
        polygon.setMap(map);
    }
}

function makeMultiPolygon(coordinates){
    var path = [];
    var points = [];

    $.each(coordinates,function (index,val2)
    {
        var coordinates2 = [];

        $.each(val2[0],function (index,coordinate)
        {
            coordinates2.push(new kakao.maps.LatLng(coordinate[1],coordinate[0]));
        });
        path.push(coordinates2);
    });

    // 다각형을 생성합니다
    return new kakao.maps.Polygon({
        map: map, // 다각형을 표시할 지도 객체
        path: path,
        strokeWeight: 0,
        strokeColor: '#eeecec',
        strokeOpacity: 0,
        fillColor: '#aeaeae',
        fillOpacity: 0.6,
    });
}

function makePolygon(coordinates){
    var path = [];
    var points = [];

    $.each(coordinates[0], function(index, coordinate) {        //console.log(coordinates)를 확인해보면 보면 [0]번째에 배열이 주로 저장이 됨.  그래서 [0]번째 배열에서 꺼내줌.
        var point = new Object();
        point.x = coordinate[1];
        point.y = coordinate[0];
        points.push(point);
        path.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));            //new kako.maps.LatLng가 없으면 인식을 못해서 path 배열에 추가
    })

    // 다각형을 생성합니다
    return new kakao.maps.Polygon({
        map: map, // 다각형을 표시할 지도 객체
        path: path,
        strokeWeight: 0,
        strokeColor: '#eeecec',
        strokeOpacity: 0,
        fillColor: '#aeaeae',
        fillOpacity: 0.6,
    });
}

// 다각형을 생성, 색칠
function displayArea(coordinates,mapno) {

    var path = [];
    var points = [];

    $.each(coordinates[0], function(index, coordinate) {        //console.log(coordinates)를 확인해보면 보면 [0]번째에 배열이 주로 저장이 됨.  그래서 [0]번째 배열에서 꺼내줌.
        var point = new Object();
        point.x = coordinate[1];
        point.y = coordinate[0];
        points.push(point);
        path.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));            //new kako.maps.LatLng가 없으면 인식을 못해서 path 배열에 추가
    })

    if(mapno==1){
        // 다각형을 생성합니다
        var polygon = new kakao.maps.Polygon({
            map: map1, // 다각형을 표시할 지도 객체
            path: path,
            strokeWeight: 4,
            strokeColor: '#dae9f4',
            strokeOpacity: 0.2,
            fillColor: '#fff',
            fillOpacity: 0.3,
        });
    }
    else{
        // 다각형을 생성합니다
        var polygon = new kakao.maps.Polygon({
            map: map, // 다각형을 표시할 지도 객체
            path: path,
            strokeWeight: 4,
            strokeColor: '#dae9f4',
            strokeOpacity: 0.2,
            fillColor: '#fff',
            fillOpacity: 0.3,
        });
    }
}

//행정동 기준으로 폴리곤 생성
function Draw_HangJungDong(h_code, addr, fillColor, userId){
    //행정동 기준의 json 파일 불러옴
    $.getJSON("/json/seoulMap.json", function(geojson) {

        var data = geojson.features;
        var coordinates = [];    //좌표 저장할 배열
        var name = '';            //행정동 이름
        var code =''; //행정기관 코드

        $.each(data, function(index, val) {

            coordinates = val.geometry.coordinates;
            name = val.properties.adm_nm;
            code = val.properties.adm_cd2;

            //특정 행정동만 그리기
            if(h_code == code)
            {
                // alert(addr);
                displayHangJungDong(coordinates, name,code,addr, fillColor, userId);
            }
        })
    })
}

function Draw_HangJungDongs(h_code_list,fillColor){
    //행정동 기준의 json 파일 불러옴
    $.getJSON("/json/seoulMap.json", function(geojson) {

        var data = geojson.features;
        var coordinates = [];    //좌표 저장할 배열
        var name = '';            //행정동 이름
        var code =''; //행정기관 코드

        $.each(data, function(index, val) {

            coordinates = val.geometry.coordinates;
            name = val.properties.adm_nm;
            code = val.properties.adm_cd2;

            //특정 행정동만 그리기
            if(h_code_list == code)
            {
                // alert(addr);
                displayHangJungDong(coordinates, name,code,addr, fillColor, userId);
            }
        })
    })
}

var polygons = [];

// 특정 행정동 색칠
function displayHangJungDong(coordinates, name,code,dest, fillColor, userId){
    var path = [];
    var points = [];
    var bounds = new Tmapv2.LatLngBounds();

    $.each(coordinates[0], function(index, coordinate) {        //console.log(coordinates)를 확인해보면 보면 [0]번째에 배열이 주로 저장이 됨.  그래서 [0]번째 배열에서 꺼내줌.
        var point = new Object();
        point.x = coordinate[1];
        point.y = coordinate[0];
        points.push(point);
        path.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));            //new kako.maps.LatLng가 없으면 인식을 못해서 path 배열에 추가
        bounds.extend(new Tmapv2.LatLng(coordinate[1], coordinate[0]));
    })

    var center = bounds.getCenter();

    // 다각형을 생성합니다
    var polygon = new kakao.maps.Polygon({
        map: map, // 다각형을 표시할 지도 객체
        path: path,
        strokeWeight: 2,
        strokeColor: fillColor,
        strokeOpacity: 0.8,
        fillColor: fillColor,
        fillOpacity: 0.9,
    });

    polygons.push(polygon);            //폴리곤 제거하기 위한 배열

    // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다
    // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
    kakao.maps.event.addListener(polygon, 'mouseover', function(mouseEvent) {
        polygon.setOptions({
            fillColor : fillColor,
            strokeColor: fillColor
        });

        customOverlay.setContent('<div class="area">' + name + '</div>');

        customOverlay.setPosition(mouseEvent.latLng);
        customOverlay.setMap(map);
    });

    // 다각형에 mousemove 이벤트를 등록하고 이벤트가 발생하면 커스텀 오버레이의 위치를 변경합니다
    kakao.maps.event.addListener(polygon, 'mousemove', function(mouseEvent) {

        customOverlay.setPosition(mouseEvent.latLng);
    });

    // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
    // 커스텀 오버레이를 지도에서 제거합니다
    kakao.maps.event.addListener(polygon, 'mouseout', function() {
        polygon.setOptions({
            fillColor : fillColor,
            strokeColor: fillColor
        });
        customOverlay.setMap(null);
    });

    // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 해당 지역 확대, 행정동 코드 보임
    kakao.maps.event.addListener(polygon, 'click', function() {

        // customOverlay.setContent('<div class="area">' + code + '</div>');
        //
        // // 현재 지도 레벨에서 2레벨 확대한 레벨
        // var level = map.getLevel()-2;
        //
        // // 지도를 클릭된 폴리곤의 중앙 위치를 기준으로 확대합니다
        // map.setLevel(level, {anchor: centroid(points), animate: {
        //         duration: 350            //확대 애니메이션 시간
        //     }});
        //
        // deletePolygon(polygons);                    //폴리곤 제거

        // 추천 행정동 분석 - 거리 가까운
        findListByDistance(topInfoList,dest);

        // !!!!!!!!!!!!해당 행정동 분석화면으로 이동!!!!!!!!!!!!
        document.write('<form action="/mapAnalysis" id="smb_form" method="post">' +
            '<input type="hidden" id="hcode" name="hcode" value="'+ code +'">' +
            '<input type="hidden" id="center_x" name="center_x" value="'+ center.lat() +'">' +
            '<input type="hidden" id="center_y" name="center_y" value="'+ center.lng() +'">' +
            '<input type="hidden" id="addr" name="addr" value="'+ dest +'">' +
            '<input type="hidden" id="listByDistance" name="listByDistance" value="">' +
            '<input type="hidden" id="userId" name="userId" value="'+ userId +'">' +
            '</form>');
    });
}

//topInfoList(상위20개 행정동) 내 학교/직장까지 거리가 가까운 행정동 찾기
function findListByDistance(topInfoList,dest){
    var listByDistance = ""; //(행정동코드, 거리)

    for(var i=0; i<topInfoList.length;i++){
        findCenter1(i,topInfoList[i].h_code, dest);
    }
}

function sleep(ms) {
    const wakeUpTime = Date.now() + ms
    while (Date.now() < wakeUpTime) {}
}

function findCenter1(i,h_code, dest){
    //행정동 기준의 json 파일 불러옴
    $.getJSON("/json/seoulMap.json", function(geojson) {

        var data = geojson.features;
        var coordinates = [];    //좌표 저장할 배열
        //var name = '';            //행정동 이름
        var code =''; //행정기관 코드

        $.each(data, function(index, val) {

            coordinates = val.geometry.coordinates;
            //name = val.properties.adm_nm;
            code = val.properties.adm_cd2;

            //특정 행정동만 그리기
            if(h_code == code)
            {
                // alert(addr);
                findCenter2(i,h_code,coordinates,dest);
            }
        })
    })
}
function findCenter2(i,code,coordinates,dest) {

    var path = [];
    var points = [];
    var bounds = new Tmapv2.LatLngBounds();

    $.each(coordinates[0], function(index, coordinate) {        //console.log(coordinates)를 확인해보면 보면 [0]번째에 배열이 주로 저장이 됨.  그래서 [0]번째 배열에서 꺼내줌.
        var point = new Object();
        point.x = coordinate[1];
        point.y = coordinate[0];
        points.push(point);
        path.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));            //new kako.maps.LatLng가 없으면 인식을 못해서 path 배열에 추가
        bounds.extend(new Tmapv2.LatLng(coordinate[1], coordinate[0]));
    })

    var center = bounds.getCenter();
    findDestCoord(i,code,center.lat(),center.lng(),dest);
}
function findDestCoord(i,code,startX,startY,dest){
    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();

// 주소로 좌표를 검색합니다
    geocoder.addressSearch(dest, function(result, status) {

        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
            //도착지 좌표값 전달
            findRouteDistance(i,code,startX,startY,result[0].y,result[0].x);
        }
        else{
            alert('도로명 주소를 입력해주세요');
        }
    });
}
function findRouteDistance(i,code,startX,startY,endX,endY) {
    var tDistance = distance(startX,startY,endX,endY);
    var input = document.getElementById("listByDistance");
    var value_tmp = input.getAttribute("value");

    value_tmp+=tDistance+","+code+"/";
    input.setAttribute("value", value_tmp);

    console.log(value_tmp);
    if(i == 19){
        document.getElementById("smb_form").submit();
    }
}

function distance(startX,startY,endX,endY) {

    var theta = startY - endY;
    var dist = Math.sin(deg2rad(startX)) * Math.sin(deg2rad(endX)) + Math.cos(deg2rad(startX)) * Math.cos(deg2rad(endX)) * Math.cos(deg2rad(theta));

    dist = Math.acos(dist);
    dist = rad2deg(dist);
    dist = dist * 60 * 1.1515;

    dist = dist * 1.609344;

    return (dist);
}

function deg2rad(deg) {
    return (deg * Math.PI / 180.0);
}

function rad2deg(rad) {
    return (rad * 180 / Math.PI);
}
