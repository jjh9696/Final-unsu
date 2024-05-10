package com.kh.lucky.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReviewDto {
	private int reviewNo;
	private String reviewTitle;
	private String reviewContent;
	private Date reviewWtime;
	private String reviewWriter;
	private int reviewViewCount;
	private int reviewStar;
	
}
