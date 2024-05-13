package com.kh.lucky.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.lucky.dao.PointDao;
import com.kh.lucky.dto.PointDto;

@Service
public class PointService {

	@Autowired
	private PointDao pointDao;
	
	public PointDto waitPoint(String memberId, int pointAmount) {
		int sequence = pointDao.sequence();
		PointDto pointDto = new PointDto();
	    pointDto.setPointNo(sequence);
	    pointDto.setMemberId(memberId);
	    pointDto.setPointAmount(pointAmount);
	    
		pointDao.insertToken(sequence, memberId, pointAmount);
		System.out.println(pointDto);
		return pointDto;
	}

}
