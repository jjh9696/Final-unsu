package com.kh.lucky.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.lucky.dao.BusDao;
import com.kh.lucky.dao.ChargeDao;
import com.kh.lucky.dao.RouteDao;
import com.kh.lucky.dto.BusDto;
import com.kh.lucky.dto.ChargeDto;
import com.kh.lucky.dto.RouteDto;

@Service
public class FareService {

	@Autowired
	RouteDao routeDao;

	@Autowired
	ChargeDao chargeDao;

	@Autowired
	BusDao busDao;

	public int calculateFare(int chargeNo, int routeNo) {
		ChargeDto chargeDto = chargeDao.selectOne(chargeNo); // 요금 정보 조회
		ChargeDto addChargeDto = chargeDao.selectOne(10);
		RouteDto routeDto = routeDao.selectOne(routeNo);
		System.out.println("요금번호 : " + chargeNo);
		if (chargeDto == null) {
			throw new RuntimeException("Charge info not found!");
		}

		int additionalCharge = addChargeDto.getChargePrice(); // 100 이 들어있을것으로 예상
		int baseFare = chargeDto.getChargePrice(); // 데이터베이스에서 가져온 기본 요금
		int km = (int) routeDto.getRouteKm();
		// 요금 계산: 기본 요금 + (거리(KM) x 거리당 추가 요금)
		// 여기서는 예를 들어 거리당 요금이 KM당 100원이라고 가정
		int additionalFare = (int) (km * additionalCharge);
		int totalFare = baseFare + additionalFare;
		System.out.println("기본금액 : " + baseFare);
		System.out.println("총금액 : " + totalFare);
		System.out.println("추가거리 : " + km);
		System.out.println("추가금액 : " + additionalFare);
		System.out.println("루트번호 : " + routeDto);
		System.out.println("차지번호 : " + chargeDto);
		return totalFare; // 계산된 총 요금 반환
	}

	// 좌석 선택 시 요금 계산
	public int gradeTypeFare(String chargeType, int routeNo, int count) {
		ChargeDto addChargeDto = chargeDao.selectOne(10);
		List<ChargeDto> chargesByAge = chargeDao.selectOneByAge(chargeType);
		if (chargesByAge.isEmpty()) {
			throw new RuntimeException("No charges found for the given age type.");
		}
		RouteDto routeDto = routeDao.selectOne(routeNo);
		// 버스 번호만 받아오면되는데 루트에서 뽑아올까?
		int busNo = routeDto.getBusNo();
		// 버스 정보와 등급 가져오기
		BusDto findGrade = busDao.selectOne(busNo);
		String busGrade = findGrade.getGradeType();

		// 버스 등급과 일치하는 요금 정보 찾기
		ChargeDto chargeDto = chargesByAge.stream().filter(charge -> charge.getGradeType().equals(busGrade)).findFirst()
				.orElseThrow(() -> new RuntimeException("No matching charge found for bus grade: " + busGrade));

		// 계산 로직...
		int baseFare = chargeDto.getChargePrice(); // 필터링된 요금 사용

		int km = (int) routeDto.getRouteKm();
		int additionalCharge = addChargeDto.getChargePrice(); // 예시 추가 요금
		int additionalFare = (int) (km * additionalCharge);
		int totalFare = (baseFare + additionalFare) * count;

		// 결과 출력
		System.out.println("Count: " + count);
		System.out.println("Base fare: " + baseFare);
		System.out.println("Total fare: " + totalFare);
		System.out.println("Additional km: " + km);
		System.out.println("Additional fare: " + additionalFare);
		System.out.println("Route number: " + routeDto);
		System.out.println("Bus grade by bus number: " + busGrade);
		System.out.println("Selected charge information: " + chargeDto);

		return totalFare;
	}

}
