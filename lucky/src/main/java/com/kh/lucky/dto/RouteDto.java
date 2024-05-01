package com.kh.lucky.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RouteDto {
	private int routeNo; //노선번호
	private String routeTime; //소요시간
	private double routeKm; //운행거리
	private Date routeStartTime; //출발시간
	private Date routeEndTime; //도착시간
	private String routeStart; //출발지
	private String routeEnd; //도착지
	private int busNo; //버스 번호
	private String routeWay; //편도 or 왕복
}


