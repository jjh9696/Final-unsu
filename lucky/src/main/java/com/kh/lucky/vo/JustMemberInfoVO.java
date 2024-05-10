package com.kh.lucky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//채팅 목록에 회원 아이디, 등급만 넘겨주기 위한 VO
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class JustMemberInfoVO {
	private String memberId;//회원아이디
	private String memberLevel;//회원등급
}	

