package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.account.TranscationDTO;
import com.example.demo.service.TranscationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TranscationController {
	
	private final TranscationService tService;
	// 인덱스페이지에서 송금페이지 이동시 
	@ResponseBody
	@PostMapping("/member/transcation")
	public String 토큰확인후계좌번호(HttpServletRequest request, HttpSession session) {
		System.out.println("서블릿에서 "+request.getHeader("Authorization"));
		if(tService.getAccount_number(request.getHeader("Authorization")) != -1L) {
			Long account_number = tService.getAccount_number(request.getHeader("Authorization"));
			session.setAttribute("account_number", account_number);
			System.out.println(account_number);
			return "ok";			
		}
		return "fail";
	}
	@GetMapping("/transcationPage")
	public String tPage(HttpSession session, Model model) {
		model.addAttribute("account_number",session.getAttribute("account_number"));
		return "transcation";
	}
	
	// 송금 페이지에서 송금하기 누름
	@PostMapping("/transferProc")
	public String transferProc(TranscationDTO dto, Model model) {

		String s = tService.transfer(dto);
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(s);
		try {
			tService.transfer(dto);
			return "redirect:/myPage";
		} catch (RuntimeException e) {
			model.addAttribute("success", "fail");
		    model.addAttribute("msg", "오류 발생: " + e.getMessage());
		    return "transcationPage"; 	
		}
	}
}
