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
	    private Date routeStartTime;
	    private Date routeEndTime;
	    private String busNo;
	    private String gradeType;
}
