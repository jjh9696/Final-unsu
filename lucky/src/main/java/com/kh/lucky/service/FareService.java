package com.kh.lucky.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.kh.lucky.dao.ChargeDao;
import com.kh.lucky.dao.RouteDao;
import com.kh.lucky.vo.RequestChargeVO;
import com.kh.lucky.dto.ChargeDto;
import com.kh.lucky.dto.RouteDto;

@Service
public class FareService {
	
	@Autowired
	RouteDao routeDao;
	
	@Autowired
	ChargeDao chargeDao;
	
	  public int calculateFare(int chargeNo, int routeNo) {
	        ChargeDto chargeDto = chargeDao.selectOne(chargeNo); // 요금 정보 조회]
	        RouteDto routeDto = routeDao.selectOne(routeNo);
	        
	        if (chargeDto == null) {
	            throw new RuntimeException("Charge info not found!");
	        }
	        
	        int baseFare = chargeDto.getChargePay(); // 데이터베이스에서 가져온 기본 요금
	        int km = (int) routeDto.getRouteKm();
	        // 요금 계산: 기본 요금 + (거리(KM) x 거리당 추가 요금)
	        // 여기서는 예를 들어 거리당 요금이 KM당 100원이라고 가정
	        int additionalFare = (int) (km * 100);
	        int totalFare = baseFare + additionalFare; 
	        
	        return totalFare; // 계산된 총 요금 반환
	    }
}
