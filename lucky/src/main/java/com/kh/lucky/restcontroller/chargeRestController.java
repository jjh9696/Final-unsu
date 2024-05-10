package com.kh.lucky.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.ChargeDao;
import com.kh.lucky.dto.ChargeDto;
import com.kh.lucky.service.FareService;

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
	//요금번호,노선번호,인원수 대로 
	@GetMapping("/{chargeNo}/{routeNo}/{count}")
	public ResponseEntity<Integer> gradeTypeFare(@PathVariable int chargeNo, @PathVariable int routeNo, @PathVariable int count){
		int total = fareService.gradeTypeFare(chargeNo, routeNo, count);
		return ResponseEntity.ok(total);
	}
	
	//등록
	@PostMapping("/")
	public ChargeDto inser(@RequestBody ChargeDto chargeDto){
		int sequence = chargeDao.sequence();
		chargeDto.setChargeNo(sequence);
		chargeDao.insert(chargeDto);
		return chargeDao.selectOne(sequence);
	}
}
