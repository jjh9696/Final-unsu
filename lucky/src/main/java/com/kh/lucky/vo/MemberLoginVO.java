package com.kh.lucky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MemberLoginVO {
	private String memberId;
	private String memberLevel;
	private String accessToken;
	private String refreshToken;
}
