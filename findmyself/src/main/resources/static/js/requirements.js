function setValue(val, input_id) //슬라이더 값 표시
{
    document.getElementById(input_id).value=val;
    console.log(val);
}

function findAddress(){ //학교 or 직장 찾기 api
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById('school_company').value = data.roadAddress;
        }
    }).open();
}

function checkRemain(){ //전월세, 연령 선택 값 얻기
    var chk_home = document.getElementsByName('home-type');
    var home_type = null;
    for(var i=0; i<chk_home.length; i++){
        if(chk_home[i].checked == true){
            home_type = chk_home[i].value;
        }
    }

    var chk_age = document.getElementsByName('age-type');
    var age_type = null;
    for(var i=0; i<chk_age.length; i++){
        if(chk_age[i].checked == true){
            age_type = chk_age[i].value;
        }
    }

    if(home_type==null || age_type==null){
        return false;
    }else{ // 여기 수정 하기
        // /* <![CDATA[ */
        // /*[[ *{home_type} ]]*/ = home_type;
        // /*[[ *{age_type} ]]*/ = age_type;
        // /* ]]> */
        return true;
    }
}