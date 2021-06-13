function drawRelation() {
    //x,y
    //charter-traffic
    var data = google.visualization.arrayToDataTable([
        ['ID', '집값 평균', '교통 편의 수치 평균', '군집 번호',     '군집 내 행정동 수'],
        ['',26924.952651515152,59.80409090909091,'0번 군집',132],
        ['',38434.44680851064,71.64510638297872,'1번 군집',47],
        ['',20480.74251497006,44.39574850299401,'2번 군집',167],
        ['',58389.625,76.1175,'3번 군집',4],
        ['',16065.763333333334,25.86133333333333,'4번 군집',75],
        ['★',charter_traffic_value1,charter_traffic_value2,String(charter_traffic_no)+'번 군집',10]
    ]);

    var options = {
        title: '집값과 교통의 상관관계',
        hAxis: {title: '집값'},
        vAxis: {title: '교통 편의 수치'},
        backgroundColor: { fill: "#e9f1f5" },
        bubble: {
            textStyle: {
                auraColor: 'none',
            }
        }
    };

    var chart = new google.visualization.BubbleChart(document.getElementById('chart_relation'));
    chart.draw(data, options);
}

function drawRelation_gender_safety(){
    //x,y
    //Gender-Safety
    var data = google.visualization.arrayToDataTable([
        ['ID', '성비 평균', '안전수치 평균', '군집 번호',     '군집 내 행정동 수'],
        ['',95.5938211382113,85.1417886178861,'0번 군집',123],
        ['',88.20075,166.5878,'1번 군집',67],
        ['',102.296804123711,43.1809278350515,'2번 군집',97],
        ['',91.4734615384615,142.996153846153,'3번 군집',78],
        ['',93.5399999999999,120.5615,'4번 군집',60],
        ['★',gender_safety_value1,gender_safety_value2,String(gender_safety_No)+'번 군집',63]
    ]);

    var options = {
        title: '성비와 안전의 상관 관계',
        hAxis: {title: '성비'},
        vAxis: {title: '안전 수치'},
        backgroundColor: {fill: "#e9f1f5"},
        bubble: {
            textStyle: {
                auraColor: 'none',
            }
        }
    };
    var chart = new google.visualization.BubbleChart(document.getElementById('chart_relation_gender_safety'));
    chart.draw(data, options);
}