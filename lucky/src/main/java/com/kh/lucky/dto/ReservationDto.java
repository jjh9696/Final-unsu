package com.kh.lucky.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservationDto {
	private int reservationNo; //  예약번호
	private String reservationTime; // 예약시간
	private String memberId; // 회원아이디
	private String gradeType; // 버스 등급
	private int routeNo; // 루트번호
	private int busNo; // 버스번호
	private int seatNo;	// 좌석번호
	private String reservationState; // 예약상태
	private int reservationCount; // 예약인원
	private String reservationType; // 예약타입
}
