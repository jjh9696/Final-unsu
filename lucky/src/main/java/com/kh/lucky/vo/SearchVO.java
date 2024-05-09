package com.kh.lucky.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SearchVO {
	   private String routeStartName;
	    private String routeEndName;
	    private String routeStartTime;
	    private String routeEndTime;
	    private int busNo;
	    private String gradeType;
	    private String startTerminalId;
	    private String endTerminalId;
	    private String startTime;
	    private String endTime;
}
