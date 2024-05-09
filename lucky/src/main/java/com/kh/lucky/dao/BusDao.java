package com.kh.lucky.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.BusDto;
import com.kh.lucky.vo.BusVO;

@Repository
public class BusDao {

	@Autowired
	private SqlSession sqlSession;

	// 시퀀스
	public int sequence() {
		return sqlSession.selectOne("bus.sequence");
	}

	// 등록
	public void insert(BusDto busDto) {
		sqlSession.insert("bus.insert", busDto);
	}

	// 조회 전체
	public List<BusDto> selectList() {
		return sqlSession.selectList("bus.list");
	}

	// 조회 (상세)
	public BusDto selectOne(int busNo) {
		return sqlSession.selectOne("bus.find", busNo);
	}

	// 전체 수정
	public boolean editAll(BusDto busDto) {
		return sqlSession.update("bus.editAll", busDto) > 0;
	}

	// 일부 수정
	public boolean edit(BusDto busDto) {
		return sqlSession.update("bus.edit", busDto) > 0;
	}

	// 삭제
	public boolean delete(int busNo) {
		return sqlSession.delete("bus.delete", busNo) > 0;
	}

	// 버스번호로 차량번호 조회
	public BusVO getBusNum(int busNo) {
		return sqlSession.selectOne("bus.findBusNum", busNo);
	}
}