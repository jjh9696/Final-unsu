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
	
	//포인트 구매대기 등록
	@PostMapping("/")
    public PointDto save(@RequestBody PointDto pointDto, @RequestHeader("Authorization") String token) {
        //데이터 확인
        System.out.println("서버에서 나오는지?");
        System.out.println("pointNo: " + pointDto.getPointNo());
        System.out.println("memberId: " + pointDto.getMemberId());
        System.out.println("pointAmout: " + pointDto.getPointAmount());

        String memberId = jwtService.parse(token).getMemberId();
        
        pointDto.setMemberId(memberId); // 사용자의 ID로 설정 (실제 사용자 ID를 설정해야 함)

        //시퀀스 번호 생성
        int sequence = pointDao.sequence();
        //번호 설정
        pointDto.setPointNo(sequence);
        //등록
        System.out.println("들어왓냐?"+memberId);
        pointDao.insert(pointDto);
        //결과 조회 반환
        return pointDao.selectOne(sequence);
    }
}
