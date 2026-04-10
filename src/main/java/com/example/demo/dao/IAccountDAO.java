package com.example.demo.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.account.DepositDTO;
import com.example.demo.dto.account.ResponseAccountDTO;
import com.example.demo.dto.account.TranscationDTO;

@Mapper
public interface IAccountDAO {

	//계좌 정보 가져오기
	Optional<ResponseAccountDTO> getAccountInfo(@Param("member_id")int member_id);
	//입금
	int depositUpdate(DepositDTO dto);
	
	// 계좌번호로 돈 업데이트
	int accNumberUpdateToPlus(@Param("account_number")Long account_number, @Param("blance")int blance);
	int accNumberUpdateToMinus(@Param("account_number")Long account_number, @Param("blance")int blance);
	
	// 계좌번호로 사람찾기 
	ResponseAccountDTO getAccountInfoTonNumber(@Param("account_number")Long account_number);
	
	// 계좌번호로 입금하기 입급테이블에 기록남기기
	int transferUpdate(TranscationDTO dto);
	// 계
}
