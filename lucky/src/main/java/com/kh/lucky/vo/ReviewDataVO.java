package com.kh.lucky.vo;

import java.util.List;

import com.kh.lucky.dto.ReviewDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReviewDataVO {
	private List<ReviewDto> list;
	private int count;
	private boolean last;
}
