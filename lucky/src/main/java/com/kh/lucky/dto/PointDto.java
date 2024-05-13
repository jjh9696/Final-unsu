package com.kh.lucky.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PointDto {
	private int pointNo; //번호
	private String memberId; //회원아이디
	private int pointAmount; //충전금액 
	private String pointTime; //충전시간
	private String pointState; //충전상태
}
