package com.kh.lucky.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.MemberDao;
import com.kh.lucky.dto.MemberDto;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "회원 정보 관리 도구")

@CrossOrigin
@RestController
@RequestMapping("/member")
public class MemberRestcontroller {
	
	@Autowired
	private MemberDao memberDao;
	
	@GetMapping("/")
	public ResponseEntity<List<MemberDto>> list() {
		List<MemberDto> list = memberDao.selectList();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{memberId}")
	public ResponseEntity<MemberDto> find(@PathVariable String memberId) {
		MemberDto memberDto = memberDao.selectOne(memberId);
		if(memberDto == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(memberDto);
	}
}
