package com.kh.lucky.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.SeatDto;

@Repository
public class SeatDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//시퀀스 생성
	public int sequence() {
		return sqlSession.selectOne("seat.sequence");
	}
	
	//등록
	public void insert(SeatDto seatDto) {
		sqlSession.insert("seat.insert",seatDto);
	}
	
	//선택 조회
	public SeatDto selectOne(int seatNo) {
		return sqlSession.selectOne("seat.find",seatNo);
	}
	
	//버스 번호에 대한 좌석 뽑기
	public List<SeatDto>busNoBySeat(int busNo){
		return sqlSession.selectList("seat.busNoBySeat",busNo);
	}
	// 좌석테이블에 예약번호 조인
	public List<SeatDto>reservationNoBySeat(){
		return sqlSession.selectList("seat.reservationNoBySeat");
	}
	
	//목록 조회
	public List<SeatDto> selectList(){
		return sqlSession.selectList("seat.list");
	}
	
	//일부수정
	public boolean edit(SeatDto seatDto) {
		return sqlSession.update("seat.edit",seatDto)>0;
	}
	
	//전체 수정
	public boolean editAll(SeatDto seatDto) {
		return sqlSession.update("seat.editAll",seatDto)>0;
	}
	//삭제
	public boolean delete(int seatNo) {
		return sqlSession.delete("seat.delete",seatNo)>0;
	}
	
}
