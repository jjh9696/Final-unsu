package com.kh.lucky.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.BusDao;
import com.kh.lucky.dto.BusDto;

@CrossOrigin
@RestController
@RequestMapping("/bus")
public class BusRestController {

	@Autowired
	private BusDao busDao;
	
	//등록
	@PostMapping("/")
	public BusDto insert(@RequestBody BusDto busDto) {
		int sequence = busDao.sequence();
		busDto.setBusNo(sequence);
		busDao.insert(busDto);
		return busDao.selectOne(sequence);
	}
}
