package com.kh.lucky.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BusDto {
	private int BusNo; //버스번호
	private String BusNum; //차량번호
	private int busSeat; //좌석 수
	private String busStatus; //차량 상태
	private int driverNo; //기사 번호
	private String busModel; //버스 모델명
	private String gradeType; //버스 유형
}
