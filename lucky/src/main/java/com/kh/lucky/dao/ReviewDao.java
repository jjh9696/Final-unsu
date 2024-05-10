package com.kh.lucky.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.ReviewDto;

@Repository
public class ReviewDao {
	@Autowired
	private SqlSession sqlSession;
	
	//시퀀스 생성
	public int sequence() {
		return sqlSession.selectOne("review.sequence");
	}
	//등록
	public void insert(ReviewDto reviewDto) {
		sqlSession.insert("review.save", reviewDto);
	}
	//선택 조회
	public ReviewDto selectOne(int reviewNo) {
		return sqlSession.selectOne("review.find", reviewNo);
	}
	//목록 조회
	public List<ReviewDto> selectList(){
		return sqlSession.selectList("review.list");
	}
	//총 개수 구하기
	public int count() {
		return sqlSession.selectOne("review.count");
	}
	
	public List<ReviewDto> selectListByPaging(int page, int size) {
		int beginRow = page * size - (size-1);
		int endRow = page * size;
		
		Map<String, Object> data = new HashMap<>();
		data.put("beginRow", beginRow);
		data.put("endRow", endRow);
		return sqlSession.selectList("review.listByPaging", data);
	}
	
}
