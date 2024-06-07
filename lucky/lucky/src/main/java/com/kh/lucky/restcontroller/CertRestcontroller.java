package com.kh.lucky.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.CertDao;
import com.kh.lucky.dto.CertDto;
import com.kh.lucky.service.EmailService;
import com.kh.lucky.service.JwtService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "이메일 전송도구")

@CrossOrigin
@RestController
@RequestMapping("/cert")
public class CertRestcontroller {

	@Autowired
	private EmailService emailService;
	@Autowired
	private CertDao certDao;

	// 이메일 인증 서비스
	@PostMapping("/sendCert/{memberEmail}")
	public void sendCert(@PathVariable String memberEmail) {
		// emailService를 이용해서 인증번호를 보내는 코드
		emailService.sendCert(memberEmail);	
	}

	@PostMapping("/checkCert")
	public ResponseEntity<String> checkCert(@RequestBody CertDto certDto) {
		boolean isValid = certDao.checkValid(certDto.getCertEmail(), certDto.getCertCode());
		if (isValid) { // 인증 성공 시 인증번호 삭제
			certDao.delete(certDto.getCertEmail());
			return ResponseEntity.ok("인증 성공");
		} else {
			return ResponseEntity.badRequest().body("유효하지 않은 인증번호입니다.");
		}
	}

}
