package com.kh.lucky.vo;

import java.util.List;

import com.kh.lucky.dto.NoticeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//페이지네이션 + 무한스크롤
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NoticeDataVO {
	private List<NoticeDto> list;
	//private int count; //pageVO에 존재
	//private boolean last; //pageVO에 존재
	private PageVO pageVO;
}
