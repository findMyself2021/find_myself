
function findAddress(){ //학교 or 직장 찾기 api
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById('school_company').value = data.roadAddress;
        }
    }).open();
}

function check(){ //전월세, 연령 선택 값 얻기
    var chk_home = document.getElementsByName('home-type');
    var home_type;
    for(var i=0; i<chk_home.length; i++){
        if(chk_home[i].checked ==true){
            home_type = chk_home[i].value;
        }
    }

    var chk_age = document.getElementsByName('age-type');
    var age_type;
    for(var i=0; i<chk_age.length; i++){
        if(chk_age[i].checked == true){
            age_type = chk_age[i].value;
        }
    }

    if(home_type == "" || age_type == "" ){
        alert("유형 선택 안됨!");
        return false;
    }else{
        var form = document.choice_form;

        var home_type_input = document.createElement("input");
        home_type_input.setAttribute("type","hidden");
        home_type_input.setAttribute("name","home_type");
        home_type_input.setAttribute("value",home_type);
        form.append(home_type_input);

        var age_type_input = document.createElement("input");
        age_type_input.setAttribute("type","hidden");
        age_type_input.setAttribute("name","age_type");
        age_type_input.setAttribute("value",age_type);
        form.append(age_type_input);

        return true;
    }
    return true;
}

function hideMonthly(){
    console.log("전세 누름");
    $('#monthly_wrap').hide();
}
function showMonthly(){
    $('#monthly_wrap').show();
}
function divideValue(total){
    console.log(total);
    document.getElementById('joy').value = total;
    document.getElementById('life').value = total;
    document.getElementById('shop').value = total;
    document.getElementById('sport').value = total;
    document.getElementById('food').value = total;
    document.getElementById('edu').value = total;

    sliderStart();
}
function mergeValues() {
    var sum = parseInt(document.getElementById('joy').value) +
        parseInt(document.getElementById('life').value) +
        parseInt(document.getElementById('shop').value) +
        parseInt(document.getElementById('sport').value) +
        parseInt(document.getElementById('food').value) +
        parseInt(document.getElementById('edu').value);
    var total = parseInt(sum/6);

    //console.log(total);
    document.getElementById('total').value = total;

    sliderStart();
}

