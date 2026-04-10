package com.example.demo.dto.account;

import lombok.Data;

@Data
public class ResponseAccountDTO {
	private int id;
	private Long account_number;
	private int blance;
	private int member_id;
}
