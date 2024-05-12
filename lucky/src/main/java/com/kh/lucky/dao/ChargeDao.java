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

	//조회
	public ChargeDto selectOne(int chargeNo) {
		return sqlSession.selectOne("charge.find", chargeNo);
	}
	public List<ChargeDto> selectOneByAge(String chargeType) {
		return sqlSession.selectList("charge.findByAge", chargeType);
	}

	public List<RequestChargeVO> selectPrice() {
		return sqlSession.selectOne("charge.find");
	}
	
	//전체 조회
	public List<ChargeDto> selectList(){
		return sqlSession.selectList("charge.list");
	}

	// 삭제
	public boolean delete(int chargeNo) {
		return sqlSession.delete("charge.delete", chargeNo) > 0;
	}

	// 전체 수정
	public boolean editAll(ChargeDto chargeDto) {
		return sqlSession.update("charge.editAll", chargeDto) > 0;
	}

	// 일부 수정
	public boolean edit(ChargeDto chargeDto) {
		return sqlSession.update("charge.edit", chargeDto) > 0;
	}

	// 시퀀스
	public int sequence() {
		return sqlSession.selectOne("charge.sequence");
	}

	// 등록
	public void insert(ChargeDto chargeDto) {
		sqlSession.insert("charge.add", chargeDto);
	}
}
