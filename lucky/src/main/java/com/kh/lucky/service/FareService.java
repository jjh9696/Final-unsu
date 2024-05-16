package com.kh.lucky.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.lucky.dao.BusDao;
import com.kh.lucky.dao.ChargeDao;
import com.kh.lucky.dao.MemberDao;
import com.kh.lucky.dao.PointDao;
import com.kh.lucky.dao.RouteDao;
import com.kh.lucky.dto.BusDto;
import com.kh.lucky.dto.ChargeDto;
import com.kh.lucky.dto.MemberDto;
import com.kh.lucky.dto.PointDto;
import com.kh.lucky.dto.RouteDto;

@Service
public class FareService {

   @Autowired
   private RouteDao routeDao;

   @Autowired
   private ChargeDao chargeDao;

   @Autowired
   private BusDao busDao;
   
   @Autowired
   private PointDao pointDao;
   
   @Autowired
   private MemberDao memberDao;

   public int calculateFare(int chargeNo, int routeNo) {
      ChargeDto chargeDto = chargeDao.selectOne(chargeNo); // 요금 정보 조회
      ChargeDto addChargeDto = chargeDao.selectOne(10);
      RouteDto routeDto = routeDao.selectOne(routeNo);
//      System.out.println("요금번호 : " + chargeNo);
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
//      System.out.println("기본금액 : " + baseFare);
//      System.out.println("총금액 : " + totalFare);
//      System.out.println("추가거리 : " + km);
//      System.out.println("추가금액 : " + additionalFare);
//      System.out.println("루트번호 : " + routeDto);
//      System.out.println("차지번호 : " + chargeDto);
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
//      System.out.println("Count: " + count);
//      System.out.println("Base fare: " + baseFare);
//      System.out.println("Total fare: " + totalFare);
//      System.out.println("Additional km: " + km);
//      System.out.println("Additional fare: " + additionalFare);
//      System.out.println("Route number: " + routeDto);
//      System.out.println("Bus grade by bus number: " + busGrade);
//      System.out.println("Selected charge information: " + chargeDto);

      return totalFare;
   }
   
   //번호 찾아서 조회
   public PointDto selectOne(int pointNo) {
       // pointNo를 사용하여 포인트 정보 조회
       PointDto pointDto = pointDao.selectOne(pointNo);
       if (pointDto == null) {
           return null;  // 조회된 포인트 정보가 없으면 null 반환
       }

       // 포인트 상태가 '결제대기'인 경우 로직 실행
//       boolean isOrder = "결제대기".equals(pointDto.getPointState());
//       if (isOrder) {
           String memberId = pointDto.getMemberId();
           System.out.println("잉?1 : "+ memberId);
           MemberDto memberDto = memberDao.selectId(memberId);
           System.out.println("잉?2 : "+ memberDto);
           // 포인트 금액만큼 회원 포인트 업데이트
           int totalPoint = memberDto.getMemberPoint() + pointDto.getPointAmount();
           System.out.println("잉?3 : "+ totalPoint);
           memberDto.setMemberPoint(totalPoint);
           System.out.println("잉?4 : "+ memberDto);
          
           memberDao.plusMemberPoint(totalPoint,memberId);
//           memberDto.setMemberPoint(totalPoint);
           // 업데이트된 회원 정보를 데이터베이스에 반영
           pointDto.setMemberId(memberId);  // PointDto에 memberId 설정
           memberDto.setMemberPoint(totalPoint);  // PointDto에 totalPoint 설정
           // 업데이트된 회원 정보를 반환하고자 한다면 여기서 memberDto를 반환할 수 있습니다.
           return pointDto;  // 또는 필요에 따라 memberDto를 반환할 수 있습니다.
//       }

       // 포인트 상태가 '결제대기'가 아니면 원래 포인트 정보 반환
//       return pointDto;
   }
}