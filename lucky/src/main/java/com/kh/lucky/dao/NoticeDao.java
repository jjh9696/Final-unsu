package com.kh.lucky.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.NoticeDto;
import com.kh.lucky.vo.NoticeDataVO;

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
	//조회수 순서로 조회
	public List<NoticeDto> viewCountList() {
		return sqlSession.selectList("notice.selectByViewCount");
	}
	
	//검색 조회
	public List<NoticeDto> searchList(String column, String keyword){
		Map<String, String> data = new HashMap<>();
		data.put("column", column);
		data.put("keyword", keyword);
	    return sqlSession.selectList("notice.searchList",data);
	}
	//최신순 목록에 보여줄 페이지
	public List<NoticeDto> selectListByPaging(int beginRow, int endRow) {
//		System.out.println("page : " + page + ", size : " + size);
//		int beginRow = page * size - (size-1);
//		int endRow = page * size;
//		int beginRow = (page - 1) * size + 1; // 시작 행 계산
//		int endRow = page * size; // 끝 행 계산
		
		Map<String, Object> data = new HashMap<>();
		data.put("beginRow", beginRow);
		data.put("endRow", endRow);
		return sqlSession.selectList("notice.listByPaging", data);
	}
	
	//조회순 목록에 보여줄 페이지
	public List<NoticeDto> selectViewListByPaging(int page, int size) {
		int beginRow = page * size - (size-1);
		int endRow = page * size;
		
		Map<String, Object> data = new HashMap<>();
		data.put("beginRow", beginRow);
		data.put("endRow", endRow);
		return sqlSession.selectList("notice.listViewByPaging", data);
	}
	//목록 개수
	public int count() {
		return sqlSession.selectOne("notice.count");
	}
	
	
}
