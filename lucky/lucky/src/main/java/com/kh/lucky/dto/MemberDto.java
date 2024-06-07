package com.kh.lucky.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"memberPw"})
public class MemberDto {
	// 4월 29일 기준 컬럼 19개
	private String memberId; // 회원아이디
	private String memberPw; // 회원비밀번호
	private String memberName; // 회원이름
	private String memberBirth; // 회원생년월일
	private String memberEmail; // 회원이메일
	private String memberPhone; // 회원연락처
	private String memberLevel; // 회원등급(일반회원/관리자/최고관리자)
	private Date memberJoinDate; // 가입일시
	private Date memberLoginDate; // 로그인일시
	private int memberPoint; // 회원포인트
	private String memberZip; // 주소(우편번호)
	private String memberAddr1; // 주소(기본주소)
	private String memberAddr2; // 주소(상세주소)
	private String providerId; // 소셜로그인 아이디
	private String memberPrivacyAgree; // 개인정보 동의
	private String memberPrivacyDate; // 개인정보 동의일
	private String memberServiceAgree; // 서비스 이용약관
	private String memberBusAgree; // 운수 이용약관
	
}