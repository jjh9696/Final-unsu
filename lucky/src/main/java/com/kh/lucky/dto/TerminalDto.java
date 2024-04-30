package com.kh.lucky.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TerminalDto {
	private int terminalId; //터미널 아이디
	private String terminalName; //터미널 이름
	private String terminalRegion; //터미널 지역
}
