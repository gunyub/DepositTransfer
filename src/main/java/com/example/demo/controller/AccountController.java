package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.account.DepositDTO;
import com.example.demo.dto.account.ResponseAccountDTO;
import com.example.demo.service.DepositServlce;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class AccountController {
	
	private final DepositServlce dService;
	@ResponseBody
	@PostMapping("/restDeposit")
	public ResponseEntity<Map<String,Object>> restDeposit(@RequestBody DepositDTO dto) {
		System.out.println("amount========="+dto.getBlance());
		System.out.println("member_id========="+dto.getMember_id());
		ResponseAccountDTO RADTO = dService.deposit(dto);
		if( RADTO != null) {
			
			return ResponseEntity.ok(Map.of("amount",RADTO.getBlance()));
		}
		
		return null;
	}
}
