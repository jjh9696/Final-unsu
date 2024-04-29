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

import com.kh.lucky.dao.DriverDao;
import com.kh.lucky.dto.DriverDto;

@CrossOrigin
@RestController
@RequestMapping("/driver")
public class DriverRestController {

	@Autowired
	private DriverDao driverDao;
	
	//조회
	@GetMapping("/")
	public List<DriverDto> list(){
		return driverDao.selectList();
	}
	
	//등록
	@PostMapping("/")
	public DriverDto insert(@RequestBody DriverDto driverDto) {
		int sequence = driverDao.sequence(); //시퀀스 번호 생성하고
		driverDto.setDriverNo(sequence); //시퀀스로 번호 설정
		driverDao.insert(driverDto); //등록
		return driverDao.selectOne(sequence); //반환하기
	}
	
	//전체수정
	@PutMapping("/")
	public ResponseEntity<?> editAll(@RequestBody DriverDto driverDto){
		boolean result = driverDao.editAll(driverDto);
		if(result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	//일부수정
	@PatchMapping("/")
	public ResponseEntity<?> edit(@RequestBody DriverDto driverDto){
		boolean result = driverDao.edit(driverDto);
		if(result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
	//삭제
	@DeleteMapping("/{driverNo}")
	public ResponseEntity<?> delete(@PathVariable int driverNo){
		DriverDto driverDto = driverDao.selectOne(driverNo); //dao안에있는 driverNo 정보를 꺼내나?
		boolean result = driverDao.delete(driverNo); //결과는 driverNo 삭제
		if(result == false) { //결과가 실패라면 
			return ResponseEntity.notFound().build(); //오류
		} 
		return ResponseEntity.ok().body(driverDto); //성공이면 dto 지우기
	}
	//상세
	@GetMapping("/{driverNo}")
	public ResponseEntity<DriverDto> find(@PathVariable int driverNo){
		DriverDto driverDto = driverDao.selectOne(driverNo);
		if(driverDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(driverDto);
	}
}
