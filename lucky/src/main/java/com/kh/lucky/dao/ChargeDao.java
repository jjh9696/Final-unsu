package com.kh.lucky.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.ChargeDto;
import com.kh.lucky.vo.RequestChargeVO;

@Repository
public class ChargeDao {
	
	@Autowired
	private SqlSession sqlSession;

	public ChargeDto selectOne(int chargeNo) {
		return sqlSession.selectOne("charge.find",chargeNo);
	}
	public List<RequestChargeVO> selectPrice() {
		return sqlSession.selectOne("charge.find");
	}


}
