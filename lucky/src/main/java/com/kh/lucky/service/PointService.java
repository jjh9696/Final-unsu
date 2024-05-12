package com.kh.lucky.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.lucky.dao.PointDao;
import com.kh.lucky.dto.PointDto;

@Service
public class PointService {

	@Autowired
	private PointDao pointDao;
	
	public PointDto waitPoint(String loginId, int pointAmount) {
		int sequence = pointDao.sequence();
		PointDto pointDto = new PointDto();
	    pointDto.setPointNo(sequence);
	    pointDto.setMemberId(loginId);
	    pointDto.setPointAmount(pointAmount);
	    
		pointDao.insertToken(sequence, loginId, pointAmount);
		System.out.println(pointDto);
		return pointDto;
	}

}
