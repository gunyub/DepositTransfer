package com.example.demo.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IAccountDAO;
import com.example.demo.dto.account.ResponseAccountDTO;
import com.example.demo.dto.account.TranscationDTO;
import com.example.demo.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class TranscationService {
	private final IAccountDAO dao;
	private final JwtUtil jwtUtil;
	private final MemberService memberService;
	
	//내 계좌 번호 가져오기
	public Long getAccount_number(String token) {
		System.out.println("서비스에서 "+token);
		boolean result = memberService.checkToken(token);
		String getToken = token.substring(7);
		System.out.println(result);
		if(result) {			
			System.out.println("서비스에서" + jwtUtil.getUsername(getToken));
			String username =jwtUtil.getUsername(getToken);
			Map<String,Object> memberAccount = memberService.getInfo(username);
			System.out.println("계좌정보 : "+memberAccount.get("acc"));
			ResponseAccountDTO accDTO =(ResponseAccountDTO) memberAccount.get("acc");
			return accDTO.getAccount_number();
			
		}
		return -1L;
	}
	
	@Transactional
	public String transfer(TranscationDTO dto) {
		ResponseAccountDTO senderDTO = dao.getAccountInfoTonNumber(dto.getSender_account());
		ResponseAccountDTO reciverDTO = dao.getAccountInfoTonNumber(dto.getReceiver_account());
		System.out.println("받는 사람 계좌정보"+reciverDTO);
		if(reciverDTO == null ) {
			return "없는 계좌번호입니다.";
		}
		if(dto.getAmount() > senderDTO.getBlance()) {
			throw new RuntimeException("잔액이 부족합니다");
		}else {
			dao.accNumberUpdateToMinus(senderDTO.getAccount_number(), dto.getAmount());
			dao.accNumberUpdateToPlus(reciverDTO.getAccount_number(), dto.getAmount());
		}
		TranscationDTO sender = new TranscationDTO(); 
		sender.setSender_account(dto.getSender_account());
		sender.setReceiver_account(dto.getReceiver_account());
		sender.setAmount(dto.getAmount());
		sender.setType(dto.getType().toUpperCase()); 

		
		TranscationDTO resiver = new TranscationDTO(); 
		resiver.setSender_account(dto.getSender_account());
		resiver.setReceiver_account(dto.getReceiver_account());
		resiver.setAmount(dto.getAmount());
		resiver.setType("DEPOSIT");

		dao.transferUpdate(sender);
		dao.transferUpdate(resiver);
		return "ok";
	}
	
}
