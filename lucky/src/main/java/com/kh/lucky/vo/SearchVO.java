package com.kh.lucky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SearchVO {
    private int routeStart;
    private int routeEnd;
    private String routeStartTime;
    private String gradeType;
}
