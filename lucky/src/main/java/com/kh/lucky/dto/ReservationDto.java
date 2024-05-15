package com.kh.lucky.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservationDto {
	private int reservationNo;
	private String reservationTime;
	private String memberId;
	private String gradeType;
	private int routeNo;
	private int busNo;
	private int seatNo;	
	private String reservationState;
	private Integer reservationAmount;
}
