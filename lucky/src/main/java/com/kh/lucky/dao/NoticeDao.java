package com.kh.lucky.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.NoticeDto;

@Repository
public class NoticeDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//시퀀스 생성
	public int sequence() {
		return sqlSession.selectOne("notice.sequence");
	}
	//등록
	public void insert(NoticeDto noticeDto) {
		sqlSession.insert("notice.save", noticeDto);
	}
	//선택 조회
	public NoticeDto selectOne(int noticeNo) {
		return sqlSession.selectOne("notice.find", noticeNo);
	}
	//목록 조회
	public List<NoticeDto> selectList() {
		return sqlSession.selectList("notice.list");
		
	}
	//수정
	public boolean edit(NoticeDto noticeDto) {
		return sqlSession.update("notice.edit", noticeDto) > 0;
	}
	//삭제
	public boolean delete(int noticeNo) {
		return sqlSession.delete("notice.delete", noticeNo) > 0;
	}
	//조회수 증가
	public boolean updateNoticeViewCount(int noticeNo) {
		return sqlSession.update(
				"notice.updateNoticeViewCount", noticeNo) > 0;
	}
	
	
}
