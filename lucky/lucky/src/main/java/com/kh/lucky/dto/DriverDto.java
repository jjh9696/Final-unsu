package com.kh.lucky.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DriverDto {
	private int driverNo; //기사번호
	private String driverName; //기사이름
	private String driverContact; //기사 연락처
	private Date driverAge; //기사 나이
	private String driverLicense; //면허증 번호
	private Date driverDate; //면허증 취득일
}
