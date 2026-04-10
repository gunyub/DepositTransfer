package com.example.demo.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RequestMemberDTO;
import com.example.demo.dto.ResponseMemberDTO;
import com.example.demo.dto.account.CreateAccountDTO;

@Mapper
public interface IMemberDAO {

	//회원가입
	int regist(RequestMemberDTO dto);
	//계좌생성
	int createAcount(CreateAccountDTO dto);
	Optional<ResponseMemberDTO> findByUsername(@Param("username") String username);
	//로그인
	Optional<ResponseMemberDTO> login(LoginDTO dto);
}
