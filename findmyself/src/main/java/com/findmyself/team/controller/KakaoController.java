package com.findmyself.team.controller;

import com.findmyself.team.KakaoApi;
import com.findmyself.team.data.domain.Member;
import com.findmyself.team.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class KakaoController {
    @Autowired
    private KakaoApi kakao;

    //회원 가입 위함
    private final MemberService memberService;

    //로그인 화면
    @GetMapping("login")
    public String openLogin(){
        return "login";
    }

    @RequestMapping(value = "/login/oauth")
    public String login(@RequestParam("code") String code, HttpSession session) {
        String access_Token = kakao.getAccessToken(code);
        HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
        System.out.println("로그인 성공");
        System.out.println("login Controller: "+userInfo);


        // 클라이언트의 id가 존재할 때
        if(userInfo.get("id")!=null){

            // 이미 존재하는 회원이면 => 로그인 되게
            List<Member> memberCheck = memberService.findById((Long) userInfo.get("id"));
            if(!memberCheck.isEmpty()){
                System.out.println("이미 존재하는 회원입니다.");
            }
            //존재하지 않다면
            else{
                // 회원 가입
                Member member = new Member();
                member.setId((Long) userInfo.get("id"));
                member.setDate((String) userInfo.get("time"));
                memberService.join(member);

                System.out.println("member = " + member);
            }
            //세션에 등록
            session.setAttribute("id",userInfo.get("id"));
        }

        return "redirect:/";
    }

    //로그아웃 버튼 눌림
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, HttpServletRequest req){

//        HttpSession kakao_check = req.getSession();

        //로그인을 한 상태인지 체크
        String id = (String) session.getAttribute("id");

        //로그인을 한 상태이고
        if(id != null)
        {
            System.out.println("userId = " + id);

            //카카오로 로그인 한 건지 체크
            Boolean kakao_get_check = (Boolean) session.getAttribute("kakao");

            if(kakao_get_check == true) //카카오로 로그인 한 거라면
            {
                this.kakao.kakaoLogout((String) session.getAttribute("access_Token"));
//                session.removeAttribute("access_Token");
//                session.removeAttribute("email");
//                session.removeAttribute("nickname");
//                session.removeAttribute("kakao");

                System.out.println("kakao logout");

                return "redirect:/";
            }

            System.out.println("logout");

            return "redirect:/";
        }
        //로그인을 하지 않은 상태라면
        System.out.println("did not login");

        return "redirect:/";
    }
}