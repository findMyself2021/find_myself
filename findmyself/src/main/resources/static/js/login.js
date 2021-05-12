function click_login_btn(){ //로그인 or 로그아웃 버튼 클릭 시
    if(id != null){ //로그아웃 구현
        //alert(id+"님을 로그아웃 하겠습니다.");
        var logout_form = document.createElement("form");
        logout_form.id = "logout-form";
        logout_form.action="/logout";
        document.body.append(logout_form);
        document.getElementById("logout-form").submit();
    }else{  //로그인 구현
        //alert("로그인 하겠습니다.");
        var login_form = document.createElement("form");
        login_form.id = "login-form";
        login_form.action="/login";
        document.body.append(login_form);
        document.getElementById("login-form").submit();
    }
}