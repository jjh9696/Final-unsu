package com.kh.lucky.restcontroller;

import java.util.List;
import java.util.Map;

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

import com.kh.lucky.dao.ReservationDao;
import com.kh.lucky.dto.ReservationDto;
import com.kh.lucky.dto.TerminalDto;
import com.kh.lucky.vo.FilterTerminalVO;



@CrossOrigin
@RestController
@RequestMapping("/reservation")
public class ReservationController {
	
	@Autowired
	private ReservationDao reservationDao;
	
	
	//조회
	@GetMapping("/")
	public List<TerminalDto> list(){
		return reservationDao.selectListTerminal();
	}
	// 리액트에서 지역을 선택하면 터미널을 보여줌
	@PostMapping("/")
	public ResponseEntity<List<TerminalDto>> searchStart(@RequestBody Map<String, String> requestBody) {
	    String terminalRegion = requestBody.get("terminalRegion"); // 필요한건 terminalRegion 라는 이름으로 들어온 정보
	    List<TerminalDto> searchTerminalDto = reservationDao.selectStartList(terminalRegion);
	    // requestBody로 받은 terminalRegion의 정보를 DB에서 불러오는 구문을 실행하여 변수에 담음
	    if(searchTerminalDto == null || searchTerminalDto.isEmpty()) {
	    	// 담은 내용이 없거나 비어있으면 못찾음 리턴
	        return ResponseEntity.notFound().build();
	    }
	    // 있으면 searchTerminalDto를 body에 담아서 준다
	    return ResponseEntity.ok().body(searchTerminalDto);
	}
	@PostMapping("/end")
	public List<FilterTerminalVO> searchEnd(@RequestBody TerminalDto terminalDto) {
		return reservationDao.selectEndList(terminalDto);
	}
	
	// 예약 인서트하는 매핑
	@PostMapping("/save")
	public ReservationDto save(@RequestBody ReservationDto reservationDto) {
		int sequence = reservationDao.sequence();
		reservationDto.setReservationNo(sequence);
		reservationDao.save(reservationDto);
		System.out.println("이건나오니?"+reservationDto);
		return reservationDao.selectOne(sequence);
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
