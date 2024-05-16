package com.kh.lucky.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.PaymentDto;

@Repository
public class PaymentDao {

	@Autowired
	private SqlSession sqlSession;

	// 조회 전체
	public List<PaymentDto> selectList() {
		return sqlSession.selectList("payment.list");
	}
	
	//등록
	public void insert(PaymentDto paymentDto) {
		sqlSession.insert("payment.insert",paymentDto);
	}
	//시퀀스
	public int sequence() {
		return sqlSession.selectOne("payment.sequence");
	}
	//상세
	public PaymentDto selectOne(int paymentNo) {
		return sqlSession.selectOne("payment.find", paymentNo);
	}
	
	
	//삭제
	public boolean delete(int paymentNo) {
		return sqlSession.delete("driver.delete",paymentNo) > 0;
	}
}
