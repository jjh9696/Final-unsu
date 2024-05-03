package com.kh.lucky.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CertDto {
	private String certEmail;
	private String certCode;
	private Date certTime;
	
}
