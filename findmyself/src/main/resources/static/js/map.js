
function DrawPolygon() {

    //서울 전체 폴리곤 생성
    $.getJSON("/json/seoulCity.json", function(geojson) {

        var data = geojson.features;
        var coordinates = [];    //좌표 저장할 배열

        $.each(data, function(index, val) {

            coordinates = val.geometry.coordinates;

            displayArea(coordinates);
        })
    })
}

// 다각형을 생성, 색칠
function displayArea(coordinates) {

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
    var polygon = new kakao.maps.Polygon({
        map: map, // 다각형을 표시할 지도 객체
        path: path,
        strokeWeight: 3.4,
        strokeColor: '#4F7AAB',
        strokeOpacity: 0.72,
        fillColor: '#fff',
        fillOpacity: 0.8,
    });
}

//행정동 기준으로 폴리곤 생성
function Draw_HangJungDong(h_code, addr){

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
                displayHangJungDong(coordinates, name,code,addr);
            }
        })
    })
}

var polygons = [];

// 지도에 영역데이터를 폴리곤으로 표시합니다
for (var i = 0, len = areas.length; i < len; i++) {
    displayArea(areas[i]);
}


// 특정 행정동 색칠
function displayHangJungDong(coordinates, name,code,dest){
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

    // 나중에 추천 정도에 따라 이거 색 바꿈 => 변수 추가해야함
    var fillColor = "#4F7AAB";

    // 다각형을 생성합니다
    var polygon = new kakao.maps.Polygon({
        map: map, // 다각형을 표시할 지도 객체
        path: path,
        strokeWeight: 2,
        strokeColor: '#4F7AAB',
        strokeOpacity: 0.7,
        fillColor: fillColor,
        fillOpacity: 0.8,
    });

    polygons.push(polygon);            //폴리곤 제거하기 위한 배열

    // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다
    // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
    kakao.maps.event.addListener(polygon, 'mouseover', function(mouseEvent) {
        polygon.setOptions({
            fillColor : '#09f'
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
            fillColor : '#4F7AAB'
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

        document.write('<form action="/mapAnalysis" id="smb_form" method="post">' +
            '<input type="hidden" id="hcode" name="hcode" value="'+ code +'">' +
            '<input type="hidden" id="center_x" name="center_x" value="'+ center.lat() +'">' +
            '<input type="hidden" id="center_y" name="center_y" value="'+ center.lng() +'">' +
            '<input type="hidden" id="addr" name="addr" value="'+ dest +'">' +
            '</form>');
        document.getElementById("smb_form").submit();

    });
}
