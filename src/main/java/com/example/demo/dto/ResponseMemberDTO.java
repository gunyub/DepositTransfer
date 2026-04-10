package com.example.demo.dto;

import lombok.Data;

@Data
public class ResponseMemberDTO {
	private int id;
	private String username;
	private String password;
	private String role;
}
