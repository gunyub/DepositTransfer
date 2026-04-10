package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dao.IAccountDAO;
import com.example.demo.dao.IMemberDAO;
import com.example.demo.dto.account.DepositDTO;
import com.example.demo.dto.account.ResponseAccountDTO;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class DepositServlce {
	
	private final IAccountDAO accDAO;
	
	public ResponseAccountDTO deposit(DepositDTO dto) {
		if(accDAO.depositUpdate(dto) ==1) {
			Optional<ResponseAccountDTO> acc = accDAO.getAccountInfo(dto.getMember_id());
			return acc.get();
		}
		return null;
	}
}
