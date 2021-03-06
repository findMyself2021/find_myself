function postMatching(){
    var on = "/image/match_on.png";
    var off = "/image/match_off.png";

    //console.log("matching_ratio: "+matching_ratio);
    if(matching_ratio == 5){
        document.getElementById("match1").src = on;
        document.getElementById("match2").src = on;
        document.getElementById("match3").src = on;
        document.getElementById("match4").src = on;
        document.getElementById("match5").src = on;
    }else if(matching_ratio == 4){
        document.getElementById("match1").src = off;
        document.getElementById("match2").src = on;
        document.getElementById("match3").src = on;
        document.getElementById("match4").src = on;
        document.getElementById("match5").src = on;
    }else if(matching_ratio == 3){
        document.getElementById("match1").src = off;
        document.getElementById("match2").src = off;
        document.getElementById("match3").src = on;
        document.getElementById("match4").src = on;
        document.getElementById("match5").src = on;
    }else if(matching_ratio == 2){
        document.getElementById("match1").src = off;
        document.getElementById("match2").src = off;
        document.getElementById("match3").src = off;
        document.getElementById("match4").src = on;
        document.getElementById("match5").src = on;
    }else {
        document.getElementById("match1").src = off;
        document.getElementById("match2").src = off;
        document.getElementById("match3").src = off;
        document.getElementById("match4").src = off;
        document.getElementById("match5").src = on;
    }
}

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
        console.log("?????? ???????????? ??????");
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
        if(topDisInfoList.length >= 1){
            document.getElementById("disTop1").innerText = topDisInfoList[0].gu+" "+topDisInfoList[0].h_dong;
            document.getElementById("disTop1Km").innerText = topDisInfoList[0].dis+"km";
        }
        if(topDisInfoList.length >= 2){
            document.getElementById("disTop2").innerText = topDisInfoList[1].gu+" "+topDisInfoList[1].h_dong;
            document.getElementById("disTop2Km").innerText = topDisInfoList[1].dis+"km";
        }
        if(topDisInfoList.length >= 3) {
            document.getElementById("disTop3").innerText = topDisInfoList[2].gu + " " + topDisInfoList[2].h_dong;
            document.getElementById("disTop3Km").innerText = topDisInfoList[2].dis + "km";
        }
        if(topDisInfoList.length >= 4) {
            document.getElementById("disTop4").innerText = topDisInfoList[3].gu + " " + topDisInfoList[3].h_dong;
            document.getElementById("disTop4Km").innerText = topDisInfoList[3].dis + "km";
        }

    }
}
// function loadClickTopList(){
//     if(resultByClikck != null) {
//         document.getElementById("clickTop1").innerText = resultByClikck[0].gu+" "+resultByClikck[0].h_dong;
//         document.getElementById("clickTop2").innerText = resultByClikck[1].gu+" "+resultByClikck[1].h_dong;
//         document.getElementById("clickTop3").innerText = resultByClikck[2].gu+" "+resultByClikck[2].h_dong;
//         document.getElementById("clickTop4").innerText = resultByClikck[3].gu+" "+resultByClikck[3].h_dong;
//     }
// }
function loadSimilarTopList(){
    if(resultBySimilar != null){
        document.getElementById("similarTop1").innerText = resultBySimilar[0].gu+" "+resultBySimilar[0].h_dong;
        document.getElementById("similarTop2").innerText = resultBySimilar[1].gu+" "+resultBySimilar[1].h_dong;
        document.getElementById("similarTop3").innerText = resultBySimilar[2].gu+" "+resultBySimilar[2].h_dong;
        document.getElementById("similarTop4").innerText = resultBySimilar[3].gu+" "+resultBySimilar[3].h_dong;

        // document.getElementById("similarTop1per").innerText = 100 - resultBySimilar[0].dis+ "%";
        // document.getElementById("similarTop2per").innerText = 100 - resultBySimilar[1].dis+ "%";
        // document.getElementById("similarTop3per").innerText = 100 - resultBySimilar[2].dis+ "%";
        // document.getElementById("similarTop4per").innerText = 100 - resultBySimilar[3].dis+ "%";

        document.getElementById("similarTop1per").innerText = "1st";
        document.getElementById("similarTop2per").innerText = "2nd";
        document.getElementById("similarTop3per").innerText = "3rd";
        document.getElementById("similarTop4per").innerText = "4th";


    }
}
function charter_info_click(){  //????????? ?????????
    document.getElementById("monthly-info1").style.display="none";
    document.getElementById("monthly-info2").style.display="none";
    document.getElementById("monthly-info3").style.display="none";
    document.getElementById("monthly-info4").style.display="none";
    document.getElementById("charter-info1").style.display="inline-block";
    document.getElementById("charter-info2").style.display="inline-block";
}
function monthly_info_click(){  //????????? ?????????
    document.getElementById("charter-info1").style.display="none";
    document.getElementById("charter-info2").style.display="none";
    document.getElementById("monthly-info1").style.display="inline-block";
    document.getElementById("monthly-info2").style.display="inline-block";
    document.getElementById("monthly-info3").style.display="inline-block";
    document.getElementById("monthly-info4").style.display="inline-block";
}

//????????? ???????????? ?????????

//????????? ?????? ?????? ??????
function drawTrafficChart(carNumArr, len){
    if(len==2){ //????????? ????????? 1?????? ??????
        var data = google.visualization.arrayToDataTable([
            ['????????????', '?????????'],
            ['am 6-7???', parseInt(carNumArr[0][0])],
            ['am 7-8???', parseInt(carNumArr[0][1])],
            ['am 8-9???', parseInt(carNumArr[0][2])],
            ['pm 5-6???', parseInt(carNumArr[0][3])],
            ['pm 6-7???', parseInt(carNumArr[0][4])],
            ['pm 7-8???', parseInt(carNumArr[0][5])]
        ]);
        var options = {
            width: '80%',
            height: 200,
            bars: 'vertical',
            vAxis: {format: 'short'},
            backgroundColor: { fill: "#e9f1f5" },
            colors: ['#f5e076'],
            legend:{
                textStyle:{
                    fontSize:'12'
                }
            }
        };
    }else {
        var data = google.visualization.arrayToDataTable([
            ['????????????', '??? ??????', '??????,?????? ??????'],
            ['am 6-7???', parseInt(carNumArr[0][0]), parseInt(carNumArr[len - 2][0])],
            ['am 7-8???', parseInt(carNumArr[0][1]), parseInt(carNumArr[len - 2][1])],
            ['am 8-9???', parseInt(carNumArr[0][2]), parseInt(carNumArr[len - 2][2])],
            ['pm 5-6???', parseInt(carNumArr[0][3]), parseInt(carNumArr[len - 2][3])],
            ['pm 6-7???', parseInt(carNumArr[0][4]), parseInt(carNumArr[len - 2][4])],
            ['pm 7-8???', parseInt(carNumArr[0][5]), parseInt(carNumArr[len - 2][5])]
        ]);
        var options = {
            width: '80%',
            height: 200,
            bars: 'vertical',
            vAxis: {format: 'short'},
            backgroundColor: {fill: "#e9f1f5"},
            colors: ['#f5e076','#c5db8c'],
            legend: {
                textStyle: {
                    fontSize: '12'
                }
            }
        };
    }
    document.getElementById("hide").style.display="block";
    var chart = new google.visualization.ColumnChart(document.getElementById('traffic-chart'));
    chart.draw(data, options);
}

//???????????? ?????? ?????? ??????
function drawTrafficChart_pub(stationNumArr, len){
    if(len==1){ //????????? ????????? 1?????? ??????
        var data = google.visualization.arrayToDataTable([
            ['????????????', '?????????'],
            ['am 6-7???', parseInt(stationNumArr[0][0])],
            ['am 7-8???', parseInt(stationNumArr[0][1])],
            ['am 8-9???', parseInt(stationNumArr[0][2])],
            ['pm 5-6???', parseInt(stationNumArr[0][3])],
            ['pm 6-7???', parseInt(stationNumArr[0][4])],
            ['pm 7-8???', parseInt(stationNumArr[0][5])]
        ]);
        var options = {
            width: '80%',
            height: 200,
            bars: 'vertical',
            vAxis: {format: 'short'},
            backgroundColor: { fill: "#e9f1f5" },
            colors: ['#f5e076'],
            legend:{
                textStyle:{
                    fontSize:'12'
                }
            }
        };
    }else{
        var data = google.visualization.arrayToDataTable([
            ['????????????', '??? ??????', '??????,?????? ??????'],
            ['am 6-7???', parseInt(stationNumArr[0][0]), parseInt(stationNumArr[len-1][0])],
            ['am 7-8???', parseInt(stationNumArr[0][1]), parseInt(stationNumArr[len-1][1])],
            ['am 8-9???', parseInt(stationNumArr[0][2]), parseInt(stationNumArr[len-1][2])],
            ['pm 5-6???', parseInt(stationNumArr[0][3]), parseInt(stationNumArr[len-1][3])],
            ['pm 6-7???', parseInt(stationNumArr[0][4]), parseInt(stationNumArr[len-1][4])],
            ['pm 7-8???', parseInt(stationNumArr[0][5]), parseInt(stationNumArr[len-1][5])]
        ]);
        var options = {
            width: '80%',
            height: 200,
            bars: 'vertical',
            vAxis: {format: 'short'},
            backgroundColor: { fill: "#e9f1f5" },
            colors: ['#f5e076', '#c5db8c',],
            legend:{
                textStyle:{
                    fontSize:'12'
                }
            }
        };
    }
    document.getElementById("hide").style.display="block";
    var chart = new google.visualization.ColumnChart(document.getElementById('traffic-chart'));
    chart.draw(data, options);
}
function drawHomeChart() {
    var data = google.visualization.arrayToDataTable([
        ['category', '??????'],
        ['?????????', apart],
        ['????????????', dandok],
        ['???????????????', dasede],
        ['????????????', officetel]
    ]);
    var options = {
        width: '100%',
        height: 200,
        backgroundColor: { fill: "#e9f1f5" },
        colors: ['#EF5350', '#E57373','#EF9A9A','#FFCDD2']
    };
    var chart = new google.visualization.PieChart(document.getElementById('home_chart'));
    chart.draw(data, options);
}
function drawConvChart() {
    var data = google.visualization.arrayToDataTable([
        ['category', '??????'],
        ['??????',     joy],
        ['??????',      shop],
        ['??????',  food],
        ['???????????????', life],
        ['?????????',    sport],
        ['??????',    edu]
    ]);
    var options = {
        width: '100%',
        height: 200,
        backgroundColor: { fill: "#e9f1f5" },
        colors: ['#F2B705', '#F2CD5C','#F29F05','#F2D49B','#F2F2F2','#fcf0ca']
    };
    var chart = new google.visualization.PieChart(document.getElementById('conv_chart'));
    chart.draw(data, options);
}
function drawSexChart() {
    var data = google.visualization.arrayToDataTable([
        ['Element', '??????', { role: 'style' }, { role: 'annotation' } ],
        ['??????', man_ratio, '#168039', man_ratio+'%' ],
        ['??????', woman_ratio, '#45BF55', woman_ratio+'%' ]
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
        document.getElementById("gender_text").innerText="??????";
        document.getElementById("gender_diff").innerText=(man_ratio-woman_ratio).toFixed(2)+"%";
    }else if(woman_ratio>man_ratio){
        document.getElementById("gender_text").innerText="??????";
        document.getElementById("gender_diff").innerText=(woman_ratio-man_ratio).toFixed(2)+"%";
    }else{
        //?????? ?????? ??????...
    }
    var chart = new google.visualization.BarChart(document.getElementById('sex_chart'));
    chart.draw(data, options);
}
function drawAgeChart() {
    var data = google.visualization.arrayToDataTable([
        ['Element', '??????', { role: 'style' }, { role: 'annotation' } ],
        ['?????????', child, '#C3D7F0', child+'%' ],
        ['2030???', s2030, '#AAC8E6', s2030+'%' ],
        ['4050???', s4050, '#375F82', s4050+'%' ],
        ['?????????', elder, '#193250', elder+'%' ]
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

// function drawSimilarChart() {
//     var space = document.getElementById('similar-chart');
//     Plotly.d3.csv('https://raw.githubusercontent.com/findMyself2021/find_myself/master/findmyself/src/main/resources/static/csv/all_cluster_monthly.csv', function(err, rows){
//
//         function unpack(rows, key) {
//             return rows.map(function(row) { return row[key]; });
//         }
//
//         var data = [{
//             x: unpack(rows, 'x'),
//             y: unpack(rows, 'y'),
//             z: unpack(rows, 'z'),
//             mode: 'markers',
//             type: 'scatter3d',
//             marker: {
//                 color: 'rgb(23, 190, 207)',
//                 size: 2
//             }
//         },{
//             alphahull: 7,
//             opacity: 0.1,
//             type: 'mesh3d',
//             x: unpack(rows, 'x'),
//             y: unpack(rows, 'y'),
//             z: unpack(rows, 'z')
//         }];
//
//         var layout = {
//             autosize: true,
//             height: 480,
//             scene: {
//                 aspectratio: {
//                     x: 1,
//                     y: 1,
//                     z: 1
//                 },
//                 camera: {
//                     center: {
//                         x: 0,
//                         y: 0,
//                         z: 0
//                     },
//                     eye: {
//                         x: 1.25,
//                         y: 1.25,
//                         z: 1.25
//                     },
//                     up: {
//                         x: 0,
//                         y: 0,
//                         z: 1
//                     }
//                 },
//                 xaxis: {
//                     type: 'linear',
//                     zeroline: false
//                 },
//                 yaxis: {
//                     type: 'linear',
//                     zeroline: false
//                 },
//                 zaxis: {
//                     type: 'linear',
//                     zeroline: false
//                 }
//             },
//             title: '?????? ????????? ????????? ??????',
//             width: 477
//         };
//
//         Plotly.newPlot(space, data, layout);
//
//     });
// }
