package com.kh.lucky.dao;

import java.util.List;

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
	public MemberDto selectEmail(String memberEmail)	{
		return sqlSession.selectOne("member.find",memberEmail);
	}
	public void updateMemberPw(MemberDto memberDto) {
		// TODO Auto-generated method stub
		
	}
}
