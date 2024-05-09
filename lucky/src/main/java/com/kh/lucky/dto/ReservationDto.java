package com.kh.lucky.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservationDto {
	private int reservationNo;
	private Date reservationDate;
	private Date reservationTime;
	private String memberId;
	private String gradeType;
	private int routeNo;
	private int busNo;
	private int seatNo;	
}
