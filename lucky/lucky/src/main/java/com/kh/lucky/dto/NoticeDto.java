package com.kh.lucky.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NoticeDto {
	private int noticeNo;
	private String noticeTitle;
	private String noticeContent;
	private Date noticeWdate;
	private int noticeViewCount;
}
