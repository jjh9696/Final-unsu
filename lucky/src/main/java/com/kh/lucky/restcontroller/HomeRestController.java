package com.kh.lucky.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dto.TerminalDto;

import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "예약 관리중", description = "예약테스트")
@CrossOrigin
@RestController
@RequestMapping("/")
public class HomeRestController {

	@Autowired
	private BookingDao bookingDao;
	
	
	//조회
	@GetMapping("/")
	public List<TerminalDto> list(){
		return bookingDao.selectList();
	}
	
//	@GetMapping("/{terminalRegion}")
//	public ResponseEntity<List<TerminalDto>> find(@PathVariable String terminalRegion){
//		List<TerminalDto> terminalDto = bookingDao.selectList(terminalRegion);
//		System.out.println(terminalRegion);
//		if(terminalDto == null) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok().body(terminalDto);
//	}
	
	// 경로변수는 문제가 있고 post방식으로 보내는게 낫다고 해서 시도중
	// 리액트에서 지역을 선택하면 터미널을 보여줌
	@PostMapping("/")
	public ResponseEntity<List<TerminalDto>> search(@RequestBody Map<String, String> requestBody) {
	    String terminalRegion = requestBody.get("terminalRegion"); // 필요한건 terminalRegion 라는 이름으로 들어온 정보
	    List<TerminalDto> searchTerminalDto = bookingDao.selectList(terminalRegion);
	    // requestBody로 받은 terminalRegion의 정보를 DB에서 불러오는 구문을 실행하여 변수에 담음
	    if(searchTerminalDto == null || searchTerminalDto.isEmpty()) {
	    	// 담은 내용이 없거나 비어있으면 못찾음 리턴
	        return ResponseEntity.notFound().build();
	    }
	    // 있으면 searchTerminalDto를 body에 담아서 준다
	    return ResponseEntity.ok().body(searchTerminalDto);
	}
}
