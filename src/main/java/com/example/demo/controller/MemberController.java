package com.example.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RequestMemberDTO;
import com.example.demo.dto.ResponseMemberDTO;
import com.example.demo.service.MemberService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberService memberService;
	
	
	@GetMapping("/registerForm")
	public String registerFrom() {
		return "registerForm";
	}
	
	@PostMapping("/regist")
	public String regist(RequestMemberDTO dto) {
		if(memberService.regist(dto) == 1) {
			return "/loginForm";
		}else {
			return "redirect:/registerForm";
		}
		
	}
	
	@GetMapping("/loginForm")
	public String login() {
		return "loginForm";
	}
	
	@ResponseBody
	@PostMapping("/loginProc")
	public ResponseEntity<Map<String,String>> loginProc(@RequestBody LoginDTO dto) {
		Map<String,String> result = memberService.login(dto);
		if(result != null) {
			return ResponseEntity.ok(result);			
		}
		return null;
	}
	//--------------------- mypage -----------------------------
	@ResponseBody
	@PostMapping("/restMyPage")
	public String restMyPage(@RequestHeader("username")String username,
							 @RequestHeader("Authorization")String token) {
		if(username != null && !username.equals("") && !username.equals("null") &&
				token != null && !token.equals("") && !token.equals("null")) {
			System.out.println("token = "+token);
			System.out.println("token = "+username);
			if(memberService.checkToken(token)) {	
				System.out.println("토큰 확인");
				return "ok";
			}
			System.out.println("토큰확인시실패");
		}
		return "fail";
	}
	
	@GetMapping("/myPage")
	public String myPage(@RequestParam("username") String username, Model model) {
		Map<String,Object> info =memberService.getInfo(username);		
		model.addAttribute("member",info.get("member"));
		model.addAttribute("acc",info.get("acc"));
		System.out.println(model.getAttribute("acc"));
		return "myPage";
	}
}
