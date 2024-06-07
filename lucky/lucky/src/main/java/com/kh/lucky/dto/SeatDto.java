package com.kh.lucky.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Builder @Data
public class SeatDto {
	private int seatNo;
	private int seatRow;
	private int seatColumn;
	private int busNo;
}
