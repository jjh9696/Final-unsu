package com.kh.lucky.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TerminalDto {
	private int terminalId;
	private String terminalName;
	private String terminalRegion;

}
