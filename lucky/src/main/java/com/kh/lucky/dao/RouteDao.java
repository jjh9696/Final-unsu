package com.kh.lucky.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.RouteDto;
import com.kh.lucky.vo.RequestChargeVO;
import com.kh.lucky.vo.RouteTimesVO;

@Repository
public class RouteDao {

	@Autowired
	private SqlSession sqlSession;

	// 시퀀스 생성
	public int sequence() {
		return sqlSession.selectOne("route.sequence");
	}

	// 등록
	public void insert(RouteDto routeDto) {
		sqlSession.insert("route.insert", routeDto);
	}

	// 선택 조회
	public RouteDto selectOne(int routeNo) {
		return sqlSession.selectOne("route.find", routeNo);
	}

	// 목록 조회
	public List<RouteDto> selectList() {
		return sqlSession.selectList("route.list");
	}

	// 일부 수정
	public boolean edit(RouteDto routeDto) {
		return sqlSession.update("route.edit", routeDto) > 0;
	}

	// 전체 수정
	public boolean editAll(RouteDto routeDto) {
		return sqlSession.update("route.editAll", routeDto) > 0;
	}

	// 삭제
	public boolean delete(int routeNo) {
		return sqlSession.delete("route.delete", routeNo) > 0;
	}
	
	//VO 리스트
	public List<RouteTimesVO> getTimeList() {
	    return sqlSession.selectList("route.getTimeList");
	}
	
	
}
