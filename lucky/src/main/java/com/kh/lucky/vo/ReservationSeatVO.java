package com.kh.lucky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservationSeatVO {
	private int seatNo;
	private int seatRow;
	private int seatColumn;
	private int busNo;
	private int reservationSeatNo;
}
