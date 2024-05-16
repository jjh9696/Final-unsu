package com.kh.lucky.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.PaymentDao;
import com.kh.lucky.dto.PaymentDto;
import com.kh.lucky.service.JwtService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "결제 관리", description = "payment CRUD 구현")

@CrossOrigin
@RestController
@RequestMapping("/payment")
public class PaymentRestController {

	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private JwtService jwtService;
	
	//조회
	@GetMapping("/")
	public List<PaymentDto> list(){
		return paymentDao.selectList();
	}
	
	//등록
	@PostMapping("/")
	public PaymentDto insert(@RequestBody PaymentDto paymentDto, @RequestHeader("Authorization") String token) {
		String memberId = jwtService.parse(token).getMemberId();
		paymentDto.setMemberId(memberId);
		int sequence = paymentDao.sequence(); //시퀀스 번호 생성하고
		paymentDto.setPaymentNo(sequence); //시퀀스로 번호 설정
		paymentDao.insert(paymentDto); //등록
		return paymentDao.selectOne(sequence); //반환하기
	}
	
	//삭제
	@DeleteMapping("/{paymentNo}")
	public ResponseEntity<?> delete(@PathVariable int paymentNo){
		PaymentDto paymentDto = paymentDao.selectOne(paymentNo); //dao안에있는 driverNo 정보를 꺼내나?
		boolean result = paymentDao.delete(paymentNo); //결과는 driverNo 삭제
		if(result == false) { //결과가 실패라면 
			return ResponseEntity.notFound().build(); //오류
		} 
		return ResponseEntity.ok().body(paymentDto); //성공이면 dto 지우기
	}
	//상세
	@GetMapping("/{paymentNo}")
	public ResponseEntity<PaymentDto> find(@PathVariable int paymentNo){
		PaymentDto paymentDto = paymentDao.selectOne(paymentNo);
		if(paymentDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(paymentDto);
	}
}
