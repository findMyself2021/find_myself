var hcode_list=[];


//여기서 mapno 들어옴
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
        polygon = makeMultiPolygon(coordinates,mapno);
    }
    else
    {
        polygon = makePolygon(coordinates,mapno);
    }

    if(mapno==1){
        polygon.setMap(map1);
    }
    else{
        polygon.setMap(map);
    }
}

//한국 전체 폴리곤
function makeMultiPolygon(coordinates,mapno){
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

function makePolygon(coordinates,mapno){
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

// 서울 전체 폴리곤
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

function Get_Hcode(list){
    for (var i=0;i<list.length;i++)
    {
        hcode_list[i] = list[i].h_code;
        // hcode_list.push(list[i].h_code);
    }
    // console.log(hcode_list);
}

//행정동 기준으로 폴리곤 생성
function Draw_HangJungDong(h_code, addr, fillColor, userId,mapno,list,cluster_name){  //(행정동코드, 학교및직장 주소, 색칠값, 사용자아이디, 맵 번호)
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

            //여기서 name 넘김 => list 사용해서 군집 값 구하기
            //특정 행정동만 그리기 => 매개 변수로 들어오는 h_code와 일치 하는 것만 색칠
            if(h_code == code)
            {
                  // alert(addr);
                if(mapno==1){   //군집 결과 그리기
                    //해당하는 인덱스
                    var index = hcode_list.indexOf(h_code);
                    if (cluster_name=="월세 군집"){
                        var deposit_avg = list[index].deposit_avg;
                        var monthly_avg = list[index].monthly_avg;
                        var monthly_min = list[index].monthly_min;

                        displayCluster(coordinates, name,code,addr, fillColor, userId,deposit_avg,monthly_avg,monthly_min,cluster_name);
                    }
                    else {
                        var min = list[index].min;
                        var max = list[index].max;
                        var avg = list[index].avg;

                        displayCluster(coordinates, name,code,addr, fillColor, userId,min,max,avg,cluster_name);
                    }
                }
                //추천 행정동 그리기
                else{
                    displayHangJungDong(coordinates, name,code,addr, fillColor, userId);
                }
            }
        })
    })
}

function Clustering_HangJungDong(category_num,list,userId,address, home_type){
    if(userId == null){
        alert("로그인 후 이용 가능합니다.");
        return;
    }
    deletePolygon(polygons1);
    Get_Hcode(list);

    //var fillColors = ["#0A3C7A","#115493","#1C75B7","#289CDB","#38C6FF","#b5e7ff"];

    //예산
    if(category_num==0){
        //3 - 1 - 4 -2 - 0
        var fillColors = ["#38C6FF","#115493","#289CDB","#0A3C7A","#1C75B7"];
        cluster_name = "전세 군집";
    }
    else if(category_num==1){
        // 3 - 1 - 2 - 0
        var fillColors = ["#289CDB","#115493","#1C75B7","#0A3C7A"];
        cluster_name = "월세 군집";
    }
    //교통
    else if (category_num==2){
        // 0 - 4 - 2 - 3 - 1
        var fillColors = ["#0A3C7A","#38C6FF","#1C75B7","#289CDB","#115493"];
        cluster_name = "교통 군집";
    }
    //편의시설
    else if(category_num==3){
        //2-0-3-1-4
        var fillColors = ["#115493","#289CDB","#0A3C7A","#1C75B7","#38C6FF"];
        cluster_name = "편의시설 군집";

    }
    //안전
    else if(category_num==4){
        //1 - 4 - 2 - 3 - 0
        var fillColors = ["#38C6FF","#0A3C7A","#1C75B7","#289CDB","#115493"];
        cluster_name = "안전 군집";
    }
    //성비
    else if(category_num==5){
        //0 - 4 - 1 - 2 - 3
        var fillColors = ["#0A3C7A","#1C75B7","#289CDB","#38C6FF","#115493"];
        cluster_name= "성비 군집";
    }
    //나이
    else if(category_num==6){
        //0 - 4 - 1 - 3 - 2 - 5
        var fillColors = ["#0a3c7a","#6977a5","#b3b8d2","#8e97bb","#42588f","#d9dbe8"];
        cluster_name="평균 나이 군집";
    }

    if(list != null && userId != null) {
        for(var i=0; i<list.length;i++){
            //군집 리스트 넘김
            Draw_HangJungDong(list[i].h_code,address,fillColors[list[i].no], userId,1,list,cluster_name);
        }
    }
}
var polygons1 = [];

// 특정 행정동 색칠
function displayHangJungDong(coordinates, name, code, addr, fillColor, userId){
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

    // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 해당 지역 확대, 행정동 코드 보임\
    // 클릭한 행정동의 분석화면으로 넘어감 !
    kakao.maps.event.addListener(polygon, 'click', function() {
        // 추천 행정동 분석 - 거리 가까운
        findListByDistance(topInfoList,addr);

        // find.do로 보내는 form 직접 작성
        document.write('<form action="/mapAnalysis" id="smb_form" method="post">' +
            '<input type="hidden" id="hcode" name="hcode" value="'+ code +'">' +
            '<input type="hidden" id="center_x" name="center_x" value="'+ center.lat() +'">' +
            '<input type="hidden" id="center_y" name="center_y" value="'+ center.lng() +'">' +
            '<input type="hidden" id="addr" name="addr" value="'+ addr +'">' +
            '<input type="hidden" id="listByDistance" name="listByDistance" value="">' +
            '<input type="hidden" id="userId" name="userId" value="'+ userId +'">' +
            '</form>');
    });
}

// 군집 분석용(displayHangJungDong과 비슷한 기능)
function displayCluster(coordinates, name,code,addr, fillColor, userId,min,max,avg,cluster_name){
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
        map: map1, // 다각형을 표시할 지도 객체
        path: path,
        strokeWeight: 2,
        strokeColor: fillColor,
        strokeOpacity: 0.8,
        fillColor: fillColor,
        fillOpacity: 0.9,
    });

    polygons1.push(polygon);            //폴리곤 제거하기 위한 배열

    // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다
    // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
    kakao.maps.event.addListener(polygon, 'mouseover', function(mouseEvent) {
        polygon.setOptions({
            fillColor : fillColor,
            strokeColor: fillColor
        });

        customOverlay.setContent('<div class="area">' + name + '</div>');

        // displayCluster(coordinates, name,code,addr, fillColor, userId,deposit_avg,monthly_avg,monthly_min,cluster_name);
        // displayCluster(coordinates, name,code,addr, fillColor, userId,min,max,avg,cluster_name){

        if (cluster_name=="월세 군집"){
            var content = '<div class="overlaybox">' +
                '<div class="boxtitle">'+cluster_name+ ' 상세 정보</div>'+
                '   <ul>' +
                '       <li class="up">' +
                '           <span class="title">보증금 평균: '+parseInt(min)+ ' 만원</span>'+
                '       </li>'+
                '       <li>'+
                '           <span class="title">월세 평균: '+parseInt(max)+ ' 만원</span>'+
                '       </li>'+
                '       <li>'+
                '           <span class="title">월세 최소: '+parseInt(avg)+ ' 만원</span>'+
                '       </li>'+
                '   </ul>'+
                '</div>';
        }
        else if (cluster_name=="전세 군집"){
            var content = '<div class="overlaybox">' +
                '<div class="boxtitle">'+cluster_name+ ' 상세 정보</div>'+
                '   <ul>' +
                '       <li class="up">' +
                '           <span class="title">보증금 평균: '+parseInt(avg)+' 만원</span>'+
                '       </li>'+
                '       <li>'+
                '           <span class="title">보증금 최소: '+parseInt(min)+' 만원</span>'+
                '       </li>'+
                '       <li>'+
                '           <span class="title">보증금 최대: '+parseInt(max)+' 만원</span>'+
                '       </li>'+
                '   </ul>'+
                '</div>';
        }
        else if (cluster_name=="평균 나이 군집"){
            var content = '<div class="overlaybox">' +
                '<div class="boxtitle">'+cluster_name+ ' 상세 정보</div>'+
                '   <ul>' +
                '       <li class="up">' +
                '           <span class="title">군집 평균 나이: '+parseInt(avg)+' 세</span>'+
                '       </li>'+
                '       <li>'+
                '           <span class="title">군집 내 최소(평균) 나이: '+parseInt(min)+' 세</span>'+
                '       </li>'+
                '       <li>'+
                '           <span class="title">군집 내 최대(평균) 나이: '+parseInt(max)+' 세</span>'+
                '       </li>'+
                '   </ul>'+
                '</div>';
        }
        else{
            var content = '<div class="overlaybox">' +
                '<div class="boxtitle">'+cluster_name+ ' 상세 정보</div>'+
                '   <ul>' +
                '       <li class="up">' +
                '           <span class="title">평균: '+avg+'</span>'+
                '       </li>'+
                '       <li>'+
                '           <span class="title">최소: '+min+'</span>'+
                '       </li>'+
                '       <li>'+
                '           <span class="title">최대: '+max+'</span>'+
                '       </li>'+
                '   </ul>'+
                '</div>';
        }

        customOverlay.setPosition(mouseEvent.latLng);
        customOverlay.setMap(map1);
        // 커스텀 오버레이를 생성합니다

        customOverlay1 = new kakao.maps.CustomOverlay({
            position: new kakao.maps.LatLng(37.59865057069701, 126.79716762447826),
            content: content,
            //왼쪽 오른쪽
            xAnchor: 0.3, //0 => 오른쪽에 나타남
            //위 아래
            yAnchor: 0.91
        });

        customOverlay1.setMap(map1);

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
        customOverlay1.setMap(null);
    });

    // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 해당 지역 확대, 행정동 코드 보임
    kakao.maps.event.addListener(polygon, 'click', function() {

        // 추천 행정동 분석 - 거리 가까운
        var value = findListByDistance(topInfoList, addr);

        if(value!=false){
            // find.do로 보내는 form 직접 작성
            document.write('<form action="/mapAnalysis" id="smb_form" method="post">' +
                '<input type="hidden" id="hcode" name="hcode" value="'+ code +'">' +
                '<input type="hidden" id="center_x" name="center_x" value="'+ center.lat() +'">' +
                '<input type="hidden" id="center_y" name="center_y" value="'+ center.lng() +'">' +
                '<input type="hidden" id="addr" name="addr" value="'+ addr +'">' +
                '<input type="hidden" id="listByDistance" name="listByDistance" value="">' +
                '<input type="hidden" id="userId" name="userId" value="'+ userId +'">' +
                '</form>');
        }
    });
}

//topInfoList(상위20개 행정동) 내 학교/직장까지 거리가 가까운 행정동 찾기
function findListByDistance(topInfoList,addr){    //(추천 행정동 리스트, 학교및직장 주소, 맵 번호)
    if(topInfoList == null){
        alert("찾기 버튼을 눌러주세요!");
        return false;
    }
    else{
        var topList_cnt = topInfoList.length;
        for(var i=0; i<topList_cnt;i++){
            if(i == topList_cnt-1){ //마지막 원소인 경우 -> form 제출위해 알리기
                findCenter1(topInfoList[i].h_code, addr,1);
            }else{
                findCenter1(topInfoList[i].h_code, addr);
            }
        }
    }
}

//특정 행정동의 중심 좌표 구하기1
function findCenter1(h_code, addr, isEnd){
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
                findCenter2(h_code,coordinates,addr, isEnd);
            }
        })
    })
}

//특정 행정동의 좌표 구하기2
function findCenter2(code,coordinates,addr,isEnd) {

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
    findDestCoord(code,center.lat(),center.lng(),addr,isEnd);
}

//학교및직장 주소기반 좌표 구하기
function findDestCoord(code,startX,startY,addr,isEnd){
    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();

    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(addr, function(result, status) {

        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
            //도착지 좌표값 전달
            findRouteDistance(code,startX,startY,result[0].y,result[0].x,isEnd);
        }
        else{
            alert('도로명 주소를 입력해주세요');
        }
    });
}

//특정 행정동 - 직장/학교 사이의 거리 구하기1: 계산한 거리값 실제로 넘겨주는 부분
function findRouteDistance(code,startX,startY,endX,endY,isEnd) {  //(행정동코드, 행정동x, 행정동y, 학교및직장x, ...)

    var tDistance = distance(startX,startY,endX,endY);  //계산된 거리 값
    var input = document.getElementById("listByDistance");  //submit form 내 거리 속성 엘리먼트
    var value_tmp = input.getAttribute("value");  // 이전에 저장된 거리 값(기존 값)

    value_tmp+=tDistance+","+code+"/";  //거리1,코드1/거리2,코드2/ ... 형태로 저장 (기존 값에 추가하는 형태)
    input.setAttribute("value", value_tmp); //submit form 내 거리 속성의 값 할당

    if(isEnd==1){ //추천 행정동 리스트의 마지막 순서인 경우 submit
        document.getElementById("smb_form").submit();
    }
}

//특정 행정동 - 직장/학교 사이의 거리 구하기2: 실제 좌표간의 거리 계산
function distance(startX,startY,endX,endY) {

    var theta = startY - endY;
    var dist = Math.sin(deg2rad(startX)) * Math.sin(deg2rad(endX)) + Math.cos(deg2rad(startX)) * Math.cos(deg2rad(endX)) * Math.cos(deg2rad(theta));

    dist = Math.acos(dist);
    dist = rad2deg(dist);
    dist = dist * 60 * 1.1515;

    dist = dist * 1.609344;

    return (dist);
}

//degree to radian
function deg2rad(deg) {
    return (deg * Math.PI / 180.0);
}

//radian to degree
function rad2deg(rad) {
    return (rad * 180 / Math.PI);
}

function deletePolygon(polygons){
    for(var i=0;i<polygons.length;i++){
        polygons[i].setMap(null);
    }
    polygons1=[];
}