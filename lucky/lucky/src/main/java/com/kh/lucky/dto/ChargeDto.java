package com.kh.lucky.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ChargeDto {
	private int chargeNo;
	private String chargeType;
	private int chargePassenger;
	private String gradeType;
	private int busNo;
	private int chargePrice;
}
