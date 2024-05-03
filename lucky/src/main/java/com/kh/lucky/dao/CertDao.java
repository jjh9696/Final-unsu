package com.kh.lucky.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.lucky.dto.CertDto;

@Repository
public class CertDao {

	@Autowired
	private SqlSession sqlSession;

	// 등록
	public void insert(CertDto certDto) {
		sqlSession.insert("cert.insert", certDto);
	}

	// 삭제
	public boolean delete(String certEmail) {
		return sqlSession.delete("cert.delete", certEmail) > 0;
	}

	// 검색
	public CertDto selectOne(String certEmail) {
		return sqlSession.selectOne("cert.selectOne", certEmail);
	}

	// 전송된 인증번호 확인
	public boolean checkValid(String certEmail, String certCode) {
		Map<String, String> params = new HashMap<>();
		params.put("certEmail", certEmail);
		params.put("certCode", certCode);

		CertDto result = sqlSession.selectOne("cert.checkValid", params);
		boolean isValid = result != null;
		return isValid;
	}

	
	public boolean deleteOver10min(String certEmail, String certCode) {
		// TODO Auto-generated method stub
		return false;
	}

//	//등록
//	 public void insert(CertDto certDto) {
//	    	sqlSession.insert("cert.insert", certDto);
//	    }
//	//메일 삭제
//	 public boolean delete(String certEmail) {
//			return sqlSession.delete("cert.delete", certEmail) > 0;
//		}
//	
//	 public CertDto selectOne(String certEmail) {
//	    	CertDto certDto = sqlSession.selectOne("cert.selectOne", certEmail);
//		//	if(certDto == null) throw new NoTargetException();
//			return certDto;
//		}
//	
//	// 5분이 지난 인증번호들을 삭제하는 메소드
//	public boolean deleteLegacy() {
//		String sql = "delete cert where cert_time < sysdate - 5/24/60";
//		return sqlSession.update(sql) > 0;
//	}
//    public boolean deleteOver10min(CertDto certDto) {
//    	int result = sqlSession.delete("cert.deleteOver10min", certDto);
//	//	if(result == 0) throw new NoTargetException();
//		return false;
//	}
}
