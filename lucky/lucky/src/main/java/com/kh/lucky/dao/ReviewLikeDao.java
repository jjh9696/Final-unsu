package com.kh.lucky.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.vo.LikeVO;

@Repository
public class ReviewLikeDao {

	@Autowired
	private SqlSession sqlSession;
	
    // 좋아요 등록
    public void insert(String memberId, int reviewNo) {
    	Map<String, Object> data = new HashMap<>();
    	data.put("memberId", memberId);
    	data.put("reviewNo", reviewNo);
    	
    	sqlSession.insert("reviewLike.save",data);
    }
	
    //좋아요 취소
    public boolean delete(String memberId, int reviewNo) {
    	Map<String, Object> data = new HashMap<>();
    	data.put("memberId", memberId);
    	data.put("reviewNo", reviewNo);
        int result = sqlSession.delete("reviewLike.delete", data);
        return result > 0;
    }
    
    //좋아요 클릭 여부 확인
    public boolean check(String memberId, int reviewNo) {
    	
    	Map<String, Object> data = new HashMap<>();
        data.put("memberId", memberId);
        data.put("reviewNo", reviewNo);
        
    	int count = sqlSession.selectOne("reviewLike.check", data);
    	return count > 0;
    }
    
    //좋아요 개수
    public int count(int reviewNo) {
    	return sqlSession.selectOne("reviewLike.count", reviewNo);
    	
    }
    
    // 좋아요 토글
    public boolean toggle(String memberId, int reviewNo) {
        // 이미 좋아요를 눌렀는지 여부 확인
        boolean alreadyLiked = check(memberId, reviewNo);

        if (alreadyLiked) {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            delete(memberId, reviewNo);
            return false; // 좋아요 취소됨
        }
        else {
            // 좋아요를 누르지 않았다면 좋아요 등록
            insert(memberId, reviewNo);
            return true; // 좋아요 등록됨
        }
    }
    
	
}
