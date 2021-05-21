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
function drawTrafficChart(){
    var data = google.visualization.arrayToDataTable([
        ['시간대별', '집부근 교통량', '학교,직장부근 교통량'],
        ['am 6-7시', 1000, 400],
        ['am 7-8시', 1170, 460],
        ['am 8-9시', 660, 1120],
        ['pm 5-6시', 1000, 400],
        ['pm 6-7시', 1170, 460],
        ['pm 7-8시', 660, 1120]
    ]);
    var options = {
        width: '100%',
        height: 200,
        backgroundColor: { fill: "#e9f1f5" }
    };

    var chart = new google.charts.Bar(document.getElementById('subway-chart'));
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

function drawClusterChart() {
    var data = google.visualization.arrayToDataTable([
        ['Age', 'Weight'],
        [ 8,      12],
        [ 4,      5.5],
        [ 11,     14],
        [ 4,      5],
        [ 3,      3.5],
        [ 6.5,    7]
    ]);

    var options = {
        title: 'Age vs. Weight comparison',
        hAxis: {title: 'Age', minValue: 0, maxValue: 15},
        vAxis: {title: 'Weight', minValue: 0, maxValue: 15},
        legend: 'none',
        width: '100%',
        height: 200,
        backgroundColor: { fill: "#e9f1f5" }
    };

    var chart = new google.visualization.ScatterChart(document.getElementById('cluster_chart'));
    chart.draw(data, options);
}