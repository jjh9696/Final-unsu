package com.kh.lucky.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.vo.SearchVO;

@Repository
public class SearchDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//등록
	public void insert(SearchVO searchVO) {
		sqlSession.insert("search.insert",searchVO);
	}
	//조회 전체
	public List<SearchVO> selectList(String startTerminalId, String endTerminalId, String startTime, String endTime){
		Map <String, Object> data = new HashMap<>();
		data.put("startTerminalId", startTerminalId);
		data.put("endTerminalId", endTerminalId);
		data.put("startTime", startTime);
		data.put("endTime", endTime);
		return sqlSession.selectList("search.list", data);
	}
	
	//전체 수정
	public boolean editAll(SearchVO searchVO) {
		return sqlSession.update("search.editAll",searchVO)>0;
	}
	//일부 수정
	public boolean edit(SearchVO searchVO) {
		return sqlSession.update("search.edit",searchVO)>0;
	}
	
	
	
}
