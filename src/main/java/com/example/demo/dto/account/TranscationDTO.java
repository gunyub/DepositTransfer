package com.example.demo.dto.account;

import lombok.Data;

@Data
public class TranscationDTO {
	
	private Long sender_account;
	private Long receiver_account;
	private int amount;
	private String type;
}
