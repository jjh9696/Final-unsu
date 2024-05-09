package com.kh.lucky.restcontroller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.TerminalDto;

@Repository
public class BookingDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 예약조회하기 위한 테이블을 띄우는 dao준비
	
	// 터미널 정보 불러오기
	public List<TerminalDto> selectList(String terminalRegion) {
	    return sqlSession.selectList("booking.list", terminalRegion);
	}
	
	// 터미널 정보를 중복제거하여 불러오기
	public List<TerminalDto> selectList() {
		return sqlSession.selectList("booking.terminalList");
	}
	
	
}
