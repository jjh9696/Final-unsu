package com.kh.lucky.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.vo.SearchResponseVO;
import com.kh.lucky.vo.SearchVO;

@Repository
public class SearchDao {
	
	@Autowired
	private SqlSession sqlSession;
		
	//조회 전체
	public List<SearchResponseVO> selectList(SearchVO searchVO) {
		System.out.println("DAO확인 = "+searchVO);
        return sqlSession.selectList("search.list", searchVO);
    }

//	public List<SearchResponseVO> selectList(String gradeType, int routeEnd, int routeStart) {
//		 Map <String, Object> data = new HashMap<>();
//		 data.put("gradeType", gradeType);
//		 data.put("routeEnd", routeEnd);
//		 data.put("routeStart", routeStart);
//	       return sqlSession.selectList("search.list", data);
//	    }
	
	
	
	
}
