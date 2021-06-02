function drawRelation() {
    //x,y
    //charter-traffic
    var data = google.visualization.arrayToDataTable([
        ['ID', 'House Price_Average', 'Traffic_Average', '군집 번호',     '해당 행정동 수'],
        ['',26924.952651515152,59.80409090909091,'0',132],
        ['',38434.44680851064,71.64510638297872,'1',47],
        ['',20480.74251497006,44.39574850299401,'2',167],
        ['',58389.625,76.1175,'3',4],
        ['',16065.763333333334,25.86133333333333,'4',75],
        ['★',charter_traffic_value1,charter_traffic_value2,String(charter_traffic_no),10]
    ]);

    var options = {
        title: '집값과 교통의 상관관계',
        hAxis: {title: 'House Price'},
        vAxis: {title: 'Traffic'},
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
        ['ID', 'Gender_Average', 'Safety_Average', '군집 번호',     '해당 행정동 수'],
        ['',95.5938211382113,85.1417886178861,'0',123],
        ['',88.20075,166.5878,'1',67],
        ['',102.296804123711,43.1809278350515,'2',97],
        ['',91.4734615384615,142.996153846153,'3',78],
        ['',93.5399999999999,120.5615,'4',60],
        ['★',gender_safety_value1,gender_safety_value2,String(gender_safety_No),63]
    ]);

    var options = {
        title: '성비와 안전의 상관 관계',
        hAxis: {title: 'Gender'},
        vAxis: {title: 'Safety'},
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