package com.kh.lucky.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.service.FareService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "요금 관리", description = "요금계산중")
@CrossOrigin
@RestController
@RequestMapping("/charge")
public class chargeRestController {
	@Autowired
	private FareService fareService;

	@GetMapping("/{chargeNo}/{routeKm}")
	public ResponseEntity<Integer> calculateFare(@PathVariable int chargeNo, @PathVariable int routeKm) {
	    int fare = fareService.calculateFare(chargeNo, routeKm);
	    System.out.println(routeKm);
	    System.out.println(chargeNo);
	    return ResponseEntity.ok(fare);
	}
	
}
