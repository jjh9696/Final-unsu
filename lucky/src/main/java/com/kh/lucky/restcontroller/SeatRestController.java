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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.SeatDao;
import com.kh.lucky.dto.SeatDto;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "좌석관리", description = "route CRUD 구현")


@CrossOrigin
@RestController
@RequestMapping("/seat")
public class SeatRestController {
	
	@Autowired
	private SeatDao seatDao;
	
	//등록
	@PostMapping("/")
	public SeatDto insert(@RequestBody SeatDto seatDto) {
		int sequence = seatDao.sequence(); 
		seatDto.setSeatNo(sequence);
		seatDao.insert(seatDto);
		return seatDao.selectOne(sequence);
	}
	
	// 버스 번호에 따른 좌석 리스트
	@GetMapping("/{busNo}/seat")
	public List<SeatDto> selectList(@PathVariable int busNo){
		return seatDao.busNoBySeat(busNo);
	}
	
	//시트 테이블에 예약번호 조인
	@GetMapping("/reservation")
	public List<SeatDto> selectListbyreservation(){
		return seatDao.reservationNoBySeat();
	}
	
	//조회단일
	@GetMapping("/{seatNo}")
	public ResponseEntity<SeatDto> find(@PathVariable int seatNo ){
		SeatDto seatDto =seatDao.selectOne(seatNo);
		if(seatDto ==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(seatDto);
	}
	
	//전체 수정
	@PutMapping("/")
	public ResponseEntity<?>editAll(@RequestBody SeatDto seatDto){
		boolean result = seatDao.editAll(seatDto);
		if(result==false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
	//일부수정
	@PatchMapping
	public ResponseEntity<?> edit(@RequestBody SeatDto seatDto){
		boolean result = seatDao.editAll(seatDto);
		if(result ==false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	
	}
	
	//삭제
	@DeleteMapping("/{seatNo}")
	public ResponseEntity<?> delete(@PathVariable int seatNo){
		SeatDto seatDto = seatDao.selectOne(seatNo);
		boolean result =seatDao.delete(seatNo);
		if(result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(seatDto);
	}
}
