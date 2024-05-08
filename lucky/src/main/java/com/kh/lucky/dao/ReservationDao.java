package com.kh.lucky.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.ReservationDto;

@Repository
public class ReservationDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//시퀀스
	public int sequence() {
		return sqlSession.selectOne("reservation.sequence");
	}
	//등록
	public void insert(ReservationDto reservationDto) {
		sqlSession.insert("reservation.insert",reservationDto);
	}
	// 조회 전체
	public List<ReservationDto> selectList(){
		return sqlSession.selectList("reservation.list");
	}
	//조회 (상세)
	public ReservationDto selectOne(int ReservationNo) {
		return sqlSession.selectOne("reservation.find",ReservationNo);
	}
	//전체 수정
	public boolean editAll(ReservationDto reservationDto) {
		return sqlSession.update("reservation.editAll",reservationDto)>0;
	}
	
	//일부 수정
	public boolean edit(ReservationDto reservationDto) {
		return sqlSession.update("reservation.edit",reservationDto) >0;
		
	}
	
	
	
	
	//삭제
	public boolean delete(int ReservationNo) {
		return sqlSession.delete("reservation.delete",ReservationNo)>0;
	}
	
	
}
