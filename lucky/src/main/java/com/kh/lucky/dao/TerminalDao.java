package com.kh.lucky.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.TerminalDto;

@Repository
public class TerminalDao {

	@Autowired
	private SqlSession sqlSession;

	// 시퀀스
	public int sequence() {
		return sqlSession.selectOne("terminal.sequence");
	}

	// 등록
	public void insert(TerminalDto terminalDto) {
		sqlSession.insert("terminal.insert", terminalDto);
	}

	// 조회 전체
	public List<TerminalDto> selectList() {
		return sqlSession.selectList("terminal.list");
	}

	// 조회 (상세)
	public TerminalDto selectOne(int terminalId) {
		return sqlSession.selectOne("terminal.find", terminalId);
	}

	// 전체 수정
	public boolean editAll(TerminalDto terminalDto) {
		return sqlSession.update("terminal.editAll", terminalDto) > 0;
	}

	// 일부 수정
	public boolean edit(TerminalDto terminalDto) {
		return sqlSession.update("terminal.edit", terminalDto) > 0;
	}

	// 삭제
	public boolean delete(int terminalId) {
		return sqlSession.delete("terminal.delete", terminalId) > 0;
	}

	// 아이디로 터미널명 지역명 조회
	public TerminalDto getTerminalById(int terminalId) {
		return sqlSession.selectOne("terminal.findTerminalName", terminalId);
	}

	// 목록 개수
	public int count() {
		return sqlSession.selectOne("terminal.count");
	}
}
