package com.kh.lucky.dto;


import java.sql.Date;

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
	private float reviewStar;
	
	public String getBoardWriterStr() {
		if(reviewWriter == null)
			return "탈퇴한사용자";
		else
			return reviewWriter;
	}
}
