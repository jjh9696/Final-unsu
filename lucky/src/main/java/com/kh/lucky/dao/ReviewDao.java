package com.kh.lucky.dao;

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
	
}
