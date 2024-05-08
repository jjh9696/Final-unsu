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

import com.kh.lucky.dao.ReservationDao;
import com.kh.lucky.dto.ReservationDto;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@CrossOrigin
@RestController
@RequestMapping("/reservation")
public class ReservationController {
	
	@Autowired
	private ReservationDao reservationDao;
	
	//등록
	@PostMapping("/")
	public ReservationDto insert(@RequestBody ReservationDto reservationDto) {
		int sequence =  reservationDao.sequence();
		reservationDto.setReservationNo(sequence);
		reservationDao.insert(reservationDto);
		return reservationDao.selectOne(sequence);
	}
	
	//조회전체
	@GetMapping("/")
	public List<ReservationDto> selectList(){
		return reservationDao.selectList();
	}
	
	//조회 단일
	@GetMapping("/{reservationNo}")
	public ResponseEntity<ReservationDto> find(@PathVariable int reservationNo){
		ReservationDto reservationDto = reservationDao.selectOne(reservationNo);
		if(reservationDto ==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(reservationDto);
	}
	//전체 수정
	@PutMapping("/")
	public ResponseEntity<?> editAll(@RequestBody ReservationDto reservationDto){
		boolean result = reservationDao.editAll(reservationDto);
		if(result==false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
		
	}
	//일부수정
	@PatchMapping("/")
	public ResponseEntity<?> edit(@RequestBody ReservationDto reservationDto){
		boolean result = reservationDao.edit(reservationDto);
		if(result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
	//삭제
	@DeleteMapping("/{reservationNo}")
	public ResponseEntity<?> delete(@PathVariable int reservationNo){
		ReservationDto reservationDto = reservationDao.selectOne(reservationNo);
		boolean result = reservationDao.delete(reservationNo);
		if(result==false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(reservationDto);
	}
	
}
