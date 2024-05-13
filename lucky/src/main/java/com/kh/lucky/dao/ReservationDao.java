package com.kh.lucky.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.ReservationDto;
import com.kh.lucky.dto.TerminalDto;
import com.kh.lucky.vo.FilterTerminalVO;

@Repository
public class ReservationDao {

	@Autowired
	private SqlSession sqlSession;

	// 시퀀스
	public int sequence() {
		return sqlSession.selectOne("reservation.sequence");
	}

	// 등록
	public void insert(ReservationDto reservationDto) {
		sqlSession.insert("reservation.insert", reservationDto);
	}
	// 예약 등록
	public void save(ReservationDto reservationDto) {
		sqlSession.insert("reservation.save",reservationDto);
	}
	
	// 조회 전체
	public List<ReservationDto> selectList() {
		return sqlSession.selectList("reservation.list");
	}

	// 조회 (상세)
	public ReservationDto selectOne(int ReservationNo) {
		return sqlSession.selectOne("reservation.find", ReservationNo);
	}

	// 전체 수정
	public boolean editAll(ReservationDto reservationDto) {
		return sqlSession.update("reservation.editAll", reservationDto) > 0;
	}

	// 일부 수정
	public boolean edit(ReservationDto reservationDto) {
		return sqlSession.update("reservation.edit", reservationDto) > 0;

	}

	// 예약조회하기 위한 테이블을 띄우는 dao준비

	// 터미널 정보 불러오기 이거 필터링해서 불러오게 개조해야함
	public List<TerminalDto> selectStartList(String terminalRegion) {
		return sqlSession.selectList("reservation.list", terminalRegion);
	}
	// 
	public List<FilterTerminalVO> selectEndList(TerminalDto terminalDto) {
		return sqlSession.selectList("reservation.filterList", terminalDto);
	}


	// 터미널 정보를 중복제거하여 불러오기
	public List<TerminalDto> selectListTerminal() {
		return sqlSession.selectList("reservation.terminalList");
	}

	// 삭제
	public boolean delete(int ReservationNo) {
		return sqlSession.delete("reservation.delete", ReservationNo) > 0;
	}

}
