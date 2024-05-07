package com.kh.lucky.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.TerminalDao;
import com.kh.lucky.dto.TerminalDto;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "터미널 관리", description = "terminal CRUD 구현")

@CrossOrigin
@RestController
@RequestMapping("/terminal")
public class TerminalRestController {

	@Autowired
	private TerminalDao terminalDao;

	// 등록
	@PostMapping("/")
	public TerminalDto insert(@RequestBody TerminalDto terminalDto) {
		int sequence = terminalDao.sequence();
		terminalDto.setTerminalId(sequence);
		terminalDao.insert(terminalDto);
		return terminalDao.selectOne(sequence);
	}

	// 조회 전체
	@GetMapping("/")
	public List<TerminalDto> selectList() {
		return terminalDao.selectList();
	}

	// 조회 단일
//	@GetMapping("/{terminalId}")
//	public ResponseEntity<TerminalDto> find(@PathVariable int terminalId) {
//		TerminalDto busDto = terminalDao.selectOne(terminalId);
//		if (busDto == null) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok().body(busDto); // 정보가있으면 바디로 조회
//	}

	// 전체 수정
	@PutMapping("/")
	public ResponseEntity<?> editAll(@RequestBody TerminalDto terminalDto) {
		boolean result = terminalDao.editAll(terminalDto);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

	// 일부수정
	@PatchMapping("/")
	public ResponseEntity<?> edit(@RequestBody TerminalDto terminalDto) {
		boolean result = terminalDao.edit(terminalDto);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

	// 삭제
	@DeleteMapping("/{terminalId}")
	public ResponseEntity<?> delete(@PathVariable int terminalId) {
		TerminalDto terminalDto = terminalDao.selectOne(terminalId);
		boolean result = terminalDao.delete(terminalId);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(terminalDto);
	}

	//터미널아이디 검색해서 터미널명,지역조회하기
	@GetMapping("/{terminalId}")
	public ResponseEntity<TerminalDto> getTerminalById(@PathVariable int terminalId) {
		TerminalDto terminalDto = terminalDao.getTerminalById(terminalId);
		if(terminalDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(terminalDto);
	}
}
