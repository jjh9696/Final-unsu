package com.kh.lucky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SearchResponseVO {
    private String routeStartTime;
	private String  startTerminalName;
	private String  EndTerminalName;
    private String gradeType;
    private String routeTime;
    private int routeKm;
    private int busNo;
    private int busSeat;
    private int routeNo;
}
