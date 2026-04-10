package com.example.demo.service;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IAccountDAO;
import com.example.demo.dao.IMemberDAO;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RequestMemberDTO;
import com.example.demo.dto.ResponseMemberDTO;
import com.example.demo.dto.account.CreateAccountDTO;
import com.example.demo.dto.account.ResponseAccountDTO;
import com.example.demo.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final IMemberDAO memberDAO;
	private final JwtUtil jwtUtil;
	private final IAccountDAO accDAO;
	// 회원가입
	@Transactional
	public int regist(RequestMemberDTO dto) {
		
		dto.setRole(dto.getRole().toUpperCase());
		if (memberDAO.regist(dto) == 1) {
			CreateAccountDTO accountDTO = new CreateAccountDTO();
			String account = "";
			for (int i = 0; i < 11; i++) {
				Random rand = new Random();
				int a = rand.nextInt(10)+1;
				account += a;
			}
			Optional<ResponseMemberDTO> member = memberDAO.findByUsername(dto.getUsername());
			accountDTO.setAccountNumber(Long.parseLong(account));
			accountDTO.setMember_id(member.get().getId());
			accountDTO.setBlance(0);
			if(memberDAO.createAcount(accountDTO) ==1) {
				return 1;
			}
		}
		return 0;
	}

	// 로그인
	public Map<String, String> login(LoginDTO dto) {
		Optional<ResponseMemberDTO> user = memberDAO.login(dto);

		if (user.isPresent()) {
			String token = jwtUtil.generateToken(user.get().getUsername(), user.get().getRole());
			return Map.of("username", user.get().getUsername(), "token", "Bearer " + token);
		}
		return null;
	}
	// 토큰검사
	public boolean checkToken(String getToken) {
		System.out.println("토큰확인하는곳"+getToken);
		
		if(getToken == null || !getToken.startsWith("Bearer ")) {
			return false;
		}
		String token = getToken.substring(7);
		System.out.println("정제된 토큰 : "+ token);
		
		if(jwtUtil.isValid(token) ) {
			String username = jwtUtil.getUsername(token);
			System.out.println(username);
			Optional<ResponseMemberDTO> member =  memberDAO.findByUsername(username);
			System.out.println(member.toString());
			if(member.isPresent()) {
				return true;			
			}	
		}
		return false;
	}
	
	// 정보 불러오기
	public Map<String,Object>  getInfo(String username) {
		Optional<ResponseMemberDTO> member = memberDAO.findByUsername(username);
		Optional<ResponseAccountDTO> acc = accDAO.getAccountInfo(member.get().getId());
		
		return Map.of("member",member.get(), "acc",acc.get());
	}

}
