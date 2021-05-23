function postStationInfo() {
    console.log(subwayStationResults);

    if(subwayStationResults !== "") {
        $('.sub_Result').val(subwayStationResults);
        $.ajax({
            type: "POST",
            url: "/mapAnalysis2",
            data: $("#postStationForm").serialize(),
            error: function (error) {
                console.log("error");
            },
            success: function (data) {
                console.log("success");

            }
        });
    } else {
        console.log("경유 지하철역 없음");
        $('#detail-route').show();
        $('#show_route').show();
        $('#traffic-chart-title').hide();
        $('#traffic-chart').hide();
    }
}

function  getStationInfo() {
    if(subwayStationResults !== "") {
        setTimeout(function () {
            $.ajax({
                type: "GET",
                url: "/mapAnalysis3",
                error: function (error) {
                    console.log("error");
                },
                success: function (data) {
                    console.log("success");
                    stationInfo = data;
                    summarizePubResult();
                }
            });
        }, 300);
    }
}

function postCarRouteInfo() {
    $('.car_Result').val(carLocationResults);
    $.ajax({
        type : "POST",
        url : "/mapAnalysis4",
        data : $("#postCarRouteForm").serialize(),
        error : function(error) {
            console.log("error");
        },
        success : function(data) {
            console.log("success");

        }
    });
}

function  getCarRouteInfo() {
    setTimeout(function () {
        $.ajax({
            type : "GET",
            url : "/mapAnalysis5",
            error : function(error) {
                console.log("error");
            },
            success : function(data) {
                console.log("success");
                carInfo = data;
                summarizeCarResult();
            }
        });
    }, 300);
}

function loadDisTopList(){
    if(topDisInfoList != null) {
        document.getElementById("disTop1").innerText = topDisInfoList[0].gu+" "+topDisInfoList[0].h_dong;
        document.getElementById("disTop1Km").innerText = topDisInfoList[0].dis+"km";
        document.getElementById("disTop2").innerText = topDisInfoList[1].gu+" "+topDisInfoList[1].h_dong;
        document.getElementById("disTop2Km").innerText = topDisInfoList[1].dis+"km";
        document.getElementById("disTop3").innerText = topDisInfoList[2].gu+" "+topDisInfoList[2].h_dong;
        document.getElementById("disTop3Km").innerText = topDisInfoList[2].dis+"km";
        document.getElementById("disTop4").innerText = topDisInfoList[3].gu+" "+topDisInfoList[3].h_dong;
        document.getElementById("disTop4Km").innerText = topDisInfoList[3].dis+"km";

    }
}
function loadClickTopList(){
    if(resultByClikck != null) {
        document.getElementById("clickTop1").innerText = resultByClikck[0].gu+" "+resultByClikck[0].h_dong;
        document.getElementById("clickTop2").innerText = resultByClikck[1].gu+" "+resultByClikck[1].h_dong;
        document.getElementById("clickTop3").innerText = resultByClikck[2].gu+" "+resultByClikck[2].h_dong;
        document.getElementById("clickTop4").innerText = resultByClikck[3].gu+" "+resultByClikck[3].h_dong;
    }
}

function charter_info_click(){  //전세가 클릭시
    document.getElementById("monthly-info1").style.display="none";
    document.getElementById("monthly-info2").style.display="none";
    document.getElementById("monthly-info3").style.display="none";
    document.getElementById("monthly-info4").style.display="none";
    document.getElementById("charter-info1").style.display="inline-block";
    document.getElementById("charter-info2").style.display="inline-block";
}
function monthly_info_click(){  //월세가 클릭시
    document.getElementById("charter-info1").style.display="none";
    document.getElementById("charter-info2").style.display="none";
    document.getElementById("monthly-info1").style.display="inline-block";
    document.getElementById("monthly-info2").style.display="inline-block";
    document.getElementById("monthly-info3").style.display="inline-block";
    document.getElementById("monthly-info4").style.display="inline-block";
}

//분야별 구글차트 그리기

//자동차 경로 분석 차트
function drawTrafficChart(carNumArr, len){
    if(len==2){ //지나는 경로가 1개인 경우
        var data = google.visualization.arrayToDataTable([
            ['시간대별', '교통량'],
            ['am 6-7시', carNumArr[0][0]],
            ['am 7-8시', carNumArr[0][1]],
            ['am 8-9시', carNumArr[0][2]],
            ['pm 5-6시', carNumArr[0][3]],
            ['pm 6-7시', carNumArr[0][4]],
            ['pm 7-8시', carNumArr[0][5]]
        ]);
        var options = {
            width: '100%',
            height: 200,
            vAxis: {format: 'short'},
            backgroundColor: { fill: "#e9f1f5" },
            colors: ['#f5e076'],
            legend:{
                textStyle:{
                    fontSize:'14'
                }
            }
        };
    }else {
        var data = google.visualization.arrayToDataTable([
            ['시간대별', '집부근 교통량', '학교,직장부근 교통량'],
            ['am 6-7시', carNumArr[0][0], carNumArr[len - 2][0]],
            ['am 7-8시', carNumArr[0][1], carNumArr[len - 2][1]],
            ['am 8-9시', carNumArr[0][2], carNumArr[len - 2][2]],
            ['pm 5-6시', carNumArr[0][3], carNumArr[len - 2][3]],
            ['pm 6-7시', carNumArr[0][4], carNumArr[len - 2][4]],
            ['pm 7-8시', carNumArr[0][5], carNumArr[len - 2][5]]
        ]);
        var options = {
            width: '100%',
            height: 200,
            vAxis: {format: 'short'},
            backgroundColor: {fill: "#e9f1f5"},
            colors: ['#f5e076', '#c5db8c',],
            legend: {
                textStyle: {
                    fontSize: '14'
                }
            }
        };
    }

    var chart = new google.charts.Bar(document.getElementById('traffic-chart'));
    chart.draw(data, google.charts.Bar.convertOptions(options));
}

//대중교통 경로 분석 차트
function drawTrafficChart_pub(stationNumArr, len){
    if(len==1){ //지나는 경로가 1개인 경우
        var data = google.visualization.arrayToDataTable([
            ['시간대별', '교통량'],
            ['am 6-7시', stationNumArr[0][0]],
            ['am 7-8시', stationNumArr[0][1]],
            ['am 8-9시', stationNumArr[0][2]],
            ['pm 5-6시', stationNumArr[0][3]],
            ['pm 6-7시', stationNumArr[0][4]],
            ['pm 7-8시', stationNumArr[0][5]]
        ]);
        var options = {
            width: '100%',
            height: 200,
            vAxis: {format: 'short'},
            backgroundColor: { fill: "#e9f1f5" },
            colors: ['#f5e076'],
            legend:{
                textStyle:{
                    fontSize:'14'
                }
            }
        };
    }else{
        var data = google.visualization.arrayToDataTable([
            ['시간대별', '집부근 교통량', '학교,직장부근 교통량'],
            ['am 6-7시', stationNumArr[0][0], stationNumArr[len-1][0]],
            ['am 7-8시', stationNumArr[0][1], stationNumArr[len-1][1]],
            ['am 8-9시', stationNumArr[0][2], stationNumArr[len-1][2]],
            ['pm 5-6시', stationNumArr[0][3], stationNumArr[len-1][3]],
            ['pm 6-7시', stationNumArr[0][4], stationNumArr[len-1][4]],
            ['pm 7-8시', stationNumArr[0][5], stationNumArr[len-1][5]]
        ]);
        var options = {
            width: '100%',
            height: 200,
            vAxis: {format: 'short'},
            backgroundColor: { fill: "#e9f1f5" },
            colors: ['#f5e076', '#c5db8c',],
            legend:{
                textStyle:{
                    fontSize:'14'
                }
            }
        };
    }
    var chart = new google.charts.Bar(document.getElementById('traffic-chart'));
    chart.draw(data, google.charts.Bar.convertOptions(options));
}
function drawHomeChart() {
    var data = google.visualization.arrayToDataTable([
        ['category', '비율'],
        ['아파트', apart],
        ['단독주택', dandok],
        ['다세대주택', dasede],
        ['오피스텔', officetel]
    ]);
    var options = {
        width: '100%',
        height: 200,
        backgroundColor: { fill: "#e9f1f5" }
    };
    var chart = new google.visualization.PieChart(document.getElementById('home_chart'));
    chart.draw(data, options);
}
function drawConvChart() {
    var data = google.visualization.arrayToDataTable([
        ['category', '비율'],
        ['오락',     joy],
        ['소매',      shop],
        ['음식',  food],
        ['생활서비스', life],
        ['스포츠',    sport],
        ['교육',    edu]
    ]);
    var options = {
        width: '100%',
        height: 200,
        backgroundColor: { fill: "#e9f1f5" }
    };
    var chart = new google.visualization.PieChart(document.getElementById('conv_chart'));
    chart.draw(data, options);
}
function drawSexChart() {
    var data = google.visualization.arrayToDataTable([
        ['Element', '비율', { role: 'style' }, { role: 'annotation' } ],
        ['남성', man_ratio, 'blue', man_ratio+'%' ],
        ['여성', woman_ratio, 'red', woman_ratio+'%' ]
    ]);
    var options = {
        width: '100%',
        height: 200,
        backgroundColor: { fill: "#e9f1f5" },
        hAxis: {
            minValue: 0,
            maxValue: 100
        },
    };
    if(man_ratio>woman_ratio){
        document.getElementById("gender_text").innerText="남성";
        document.getElementById("gender_diff").innerText=(man_ratio-woman_ratio).toFixed(2)+"%";
    }else if(woman_ratio>man_ratio){
        document.getElementById("gender_text").innerText="여성";
        document.getElementById("gender_diff").innerText=(woman_ratio-man_ratio).toFixed(2)+"%";
    }else{
        //비율 같은 경우...
    }
    var chart = new google.visualization.BarChart(document.getElementById('sex_chart'));
    chart.draw(data, options);
}
function drawAgeChart() {
    var data = google.visualization.arrayToDataTable([
        ['Element', '비율', { role: 'style' }, { role: 'annotation' } ],
        ['유소년', child, 'blue', child+'%' ],
        ['2030대', s2030, 'red', s2030+'%' ],
        ['4050대', s4050, 'red', s4050+'%' ],
        ['고령자', elder, 'red', elder+'%' ]
    ]);
    var options = {
        width: '100%',
        height: 200,
        backgroundColor: { fill: "#e9f1f5" },
        hAxis: {
            minValue: 0,
            maxValue: 100
        },
    };
    var chart = new google.visualization.BarChart(document.getElementById("age_chart"));
    chart.draw(data, options);
}