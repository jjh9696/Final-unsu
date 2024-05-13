package com.kh.lucky.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.PointDao;
import com.kh.lucky.dto.PointDto;
import com.kh.lucky.service.JwtService;
import com.kh.lucky.service.PointService;
import com.kh.lucky.vo.MemberLoginVO;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "포인트", description = "그냥 결제하지마세요")
@CrossOrigin
@RestController
@RequestMapping("/point")
public class PointRestController {

	@Autowired
	private PointDao pointDao;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private PointService pointService;

	// 전체 조회
	@GetMapping("/")
	public List<PointDto> selectList() {
		return pointDao.selectList();
	}

	//번호 찾아서 조회
	@GetMapping("/{pointNo}")
	public PointDto selectOne(@PathVariable int pointNo) {
		return pointDao.selectOne(pointNo);
	}
	//상태찾아서 조회
	@GetMapping("/state/{pointState}")
	public List<PointDto> selectList(@PathVariable String pointState) {
		return pointDao.selectState(pointState);
	}

	// 전체 수정
	@PutMapping("/")
	public ResponseEntity<?> editAll(@RequestBody PointDto pointDto) {
		boolean result = pointDao.editAll(pointDto);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

	// 일부수정
	@PatchMapping("/")
	public ResponseEntity<?> edit(@RequestBody PointDto pointDto) {
		boolean result = pointDao.edit(pointDto);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
	//등록
	@PostMapping("/{memberId}/{pointAmount}")
	public PointDto insert(@RequestHeader("Authorization") String token, int pointAmount) {
	    // JWT 토큰을 파싱하여 memberId를 추출합니다.
	    MemberLoginVO loginVO = jwtService.parse(token);
		String memberId = loginVO.getMemberId();
	    
	    return pointService.waitPoint(memberId, pointAmount);
	}

	//토큰 받아오고싶다
//	@GetMapping("/")
//	public List<PointDto> pointAllList(@RequestHeader("Authorization") String token) {
//		MemberLoginVO loginVO = jwtService.parse(token);
//		String memberId = loginVO.getMemberId();
//	    return pointDao.selectList();
//	}
}
