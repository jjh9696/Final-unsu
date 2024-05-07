package com.kh.lucky.vo;

import java.util.List;

import com.kh.lucky.dto.NoticeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BusVO {
	private int busNo;
	private String busNum;
}
