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
		//데이터 확인
	    System.out.println("서버에서 나오는지?");
	    System.out.println("DAOReview No: " + reviewDto.getReviewNo());
	    System.out.println("DAOReview Title: " + reviewDto.getReviewTitle());
	    System.out.println("DAOReview Content: " + reviewDto.getReviewContent());
	    System.out.println("DAOReview Star: " + reviewDto.getReviewStar());
	    System.out.println("DAOReview Writer: " + reviewDto.getReviewWriter());
	    System.out.println("DAOReview ViewCount: " + reviewDto.getReviewViewCount());
	    System.out.println("DAOReview Wtime: " + reviewDto.getReviewWtime());
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
