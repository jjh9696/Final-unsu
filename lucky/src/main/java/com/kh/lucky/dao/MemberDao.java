package com.kh.lucky.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.MemberDto;


@Repository
public class MemberDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<MemberDto> selectList(){
		return sqlSession.selectList("member.list");
	}
	public MemberDto selectOne(String memberId) {
		return sqlSession.selectOne("member.find", memberId);
	}
	
	// 아이디 중복검사할 때 필요함
	public MemberDto selectEmail(String memberEmail)	{
		return sqlSession.selectOne("member.find",memberEmail);
	}
	// 가입
	public void insert(MemberDto memberDto) {
		sqlSession.insert("member.save",memberDto);
	}
	public void updateMemberPw(MemberDto memberDto) {
		// TODO Auto-generated method stub
		
	}
	public List<MemberDto> selectMemberIdList(){
		return sqlSession.selectList("member.listMemberId");
	}
	
	// 아이디 중복검사할 때 필요함
	public MemberDto selectId(String memberId)	{
		return sqlSession.selectOne("member.findId",memberId);
	}
	
	public void plusMemberPoint(int totalPoint, String memberId) {
		Map<String, Object> info = new HashMap<>();
        info.put("memberPoint", totalPoint);
        info.put("memberId", memberId);
		sqlSession.update("member.plusMemberPoint",info);
	}
	
}
