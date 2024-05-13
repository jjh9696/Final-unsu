package com.kh.lucky.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.PointDto;

@Repository
public class PointDao {

	@Autowired
	private SqlSession sqlSession;

	// 포인트 시퀀스
	public int sequence() {
		return sqlSession.selectOne("point.sequence");
	}

	// 조회
	public List<PointDto> selectList() {
		return sqlSession.selectList("point.pointAllList");
	}

	// 단일 조회
	public PointDto selectOne(int pointNo) {
		return sqlSession.selectOne("point.find", pointNo);
	}
	
	//상태 목록
	public List<PointDto> selectState(String pointState) {
	    return sqlSession.selectList("point.pointStateList", pointState);
	}

	// 전체수정
	public boolean editAll(PointDto pointDto) {
		return sqlSession.update("point.editAll", pointDto) > 0;
	}

	// 일부수정
	public boolean edit(PointDto pointDto) {
		return sqlSession.update("point.edit", pointDto) > 0;
	}
	
	//기존..등록
	public void insert(PointDto pointDto) {
		sqlSession.insert("point.add", pointDto);
	}
	//아이디 토큰으로 받아서 등록
//	public void insertToken(int pointNo, String memberId, int pointAmount) {
//		Map<String, Object> data = new HashMap<>();
//		data.put("pointNo", pointNo);
//		data.put("memberId", memberId);
//		data.put("pointAmount", pointAmount);
//		sqlSession.insert("point.add",data);
//	}
}
