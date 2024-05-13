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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.ChargeDao;
import com.kh.lucky.dto.ChargeDto;
import com.kh.lucky.service.FareService;
import com.kh.lucky.vo.RequestChargeVO;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "요금 관리", description = "요금계산중")
@CrossOrigin
@RestController
@RequestMapping("/charge")
public class chargeRestController {
	@Autowired
	private FareService fareService;
	@Autowired
	private ChargeDao chargeDao;

	@GetMapping("/{chargeNo}/{routeKm}")
	public ResponseEntity<Integer> calculateFare(@PathVariable int chargeNo, @PathVariable int routeKm) {
		int fare = fareService.calculateFare(chargeNo, routeKm);
		System.out.println(routeKm);
		System.out.println(chargeNo);
		return ResponseEntity.ok(fare);
	}

	// 요금번호,노선번호,인원수 대로
	@PostMapping("/calculateFare")
	public ResponseEntity<Integer> calculateFare(@RequestBody RequestChargeVO requestChargeVO) {
	    int total = fareService.gradeTypeFare(requestChargeVO.getChargeType(), requestChargeVO.getRouteNo(), requestChargeVO.getCount());
	    System.out.println("토탈은?"+total);
	    return ResponseEntity.ok(total);
	}



	// 등록
	@PostMapping("/")
	public ChargeDto inser(@RequestBody ChargeDto chargeDto) {
		int sequence = chargeDao.sequence();
		chargeDto.setChargeNo(sequence);
		chargeDao.insert(chargeDto);
		return chargeDao.selectOne(sequence);
	}

	// 전체 수정
	@PutMapping("/")
	public ResponseEntity<?> editAll(@RequestBody ChargeDto chargeDto) {
		boolean result = chargeDao.editAll(chargeDto);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

	// 일부수정
	@PatchMapping("/")
	public ResponseEntity<?> edit(@RequestBody ChargeDto chargeDto) {
		boolean result = chargeDao.edit(chargeDto);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}

	// 삭제
	@DeleteMapping("/{chargeNo}")
	public ResponseEntity<?> delete(@PathVariable int chargeNo) {
		ChargeDto chargeDto = chargeDao.selectOne(chargeNo);
		boolean result = chargeDao.delete(chargeNo);
		if (result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(chargeDto);
	}
	
	//전체 리스트
	@GetMapping("/")
	public List<ChargeDto> list(){
		return chargeDao.selectList();
	}
}
