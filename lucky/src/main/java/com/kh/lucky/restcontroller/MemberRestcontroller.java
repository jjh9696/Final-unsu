package com.kh.lucky.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.MemberDao;
import com.kh.lucky.dao.PointDao;
import com.kh.lucky.dto.MemberDto;
import com.kh.lucky.dto.PointDto;
import com.kh.lucky.service.FareService;
import com.kh.lucky.service.JwtService;
import com.kh.lucky.vo.MemberLoginVO;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "회원 정보 관리 도구")


@CrossOrigin
@RestController
@RequestMapping("/member")
public class MemberRestcontroller {

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private PointDao pointDao;
	@Autowired
	private FareService fareService;


	@GetMapping("/")
	public ResponseEntity<List<MemberDto>> list() {
		List<MemberDto> list = memberDao.selectList();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/{memberId}")
	public ResponseEntity<MemberDto> find(@PathVariable String memberId) {
		MemberDto memberDto = memberDao.selectOne(memberId);
		if (memberDto == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok().body(memberDto);
	}
	//회원가입
	@PostMapping("/join")//등록
	public void insert(@RequestBody MemberDto memberDto) {
		memberDao.insert(memberDto);
	}

	@PostMapping("/login")
	public ResponseEntity<MemberLoginVO> login(@RequestBody MemberDto memberDto) {
		// 아이디로 정보조회
		MemberDto findDto = memberDao.selectOne(memberDto.getMemberId());
		if (findDto == null) {
			return ResponseEntity.notFound().build(); // 404
		}
		// 비밀번호 비교
		boolean isValid = findDto.getMemberPw().equals(memberDto.getMemberPw());
		if (isValid) {// 성공
			String accessToken = jwtService.createAccessToken(findDto);
			String refreshToken = jwtService.createRefreshToken(findDto);

			return ResponseEntity.ok(MemberLoginVO.builder().memberId(findDto.getMemberId())
					.memberLevel(findDto.getMemberLevel()).accessToken(accessToken).refreshToken(refreshToken).build() // 200
			);
		} else {
			return ResponseEntity.status(401).build();
		}
	}

	// refresh token 으로 로그인하는 매핑
	// header에 있는 Authorization이라는 항목을 읽어 해석 한 뒤 결과를 반환	
	// refresh
	@PostMapping("/refresh")
	public ResponseEntity<MemberLoginVO> refresh(@RequestHeader("Authorization") String refreshToken) {
		// loginVO에 있는 정보가 실제 DB와 일치하는지 추가적으로 조회
		try {
			MemberLoginVO loginVO = jwtService.parse(refreshToken);
			MemberDto memberDto = memberDao.selectOne(loginVO.getMemberId());
			if (memberDto == null) {// 존재하지 않는 아이디 (강제예외)
				throw new Exception("존재하지 않는 아이디");
			}
			if (!loginVO.getMemberLevel().equals(memberDto.getMemberLevel())) {
				throw new Exception("정보 불일치");
			}
			// 위에서 필터링되지 않았다면 refresh token이 유효하다고 볼 수 있다
			// 사용자에게 새롭게 access token 을 발급한다
			// -> 보안을 위해서 refresh token도 재발급 한다
			String accessToken = jwtService.createAccessToken(memberDto);
			String newRefreshToken = jwtService.createRefreshToken(memberDto); // 기존 리프레쉬토큰은 폐기하고 새롭게 만든다
			return ResponseEntity.ok()
					.body(MemberLoginVO.builder().memberId(memberDto.getMemberId())
							.memberLevel(memberDto.getMemberLevel()).accessToken(accessToken)
							.refreshToken(newRefreshToken).build());
		} catch (Exception e) {
			return ResponseEntity.status(401).build();
		}
	}

	// 아이디 중복체크 성공!
	@GetMapping("/check/{memberId}")
	public ResponseEntity<Map<String, Boolean>> checkMemberId(@PathVariable String memberId) {
		MemberDto existingMember = memberDao.selectOne(memberId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("exists", existingMember != null);
		return ResponseEntity.ok().body(response);
	}
	
	
	//토큰 받아오고싶다
	@GetMapping("/token/{memberId}")
	public MemberDto selectMemberId(@RequestHeader("Authorization") String token) {
		MemberLoginVO loginVO = jwtService.parse(token);
		System.out.println("결과:"+loginVO);
		String memberId = loginVO.getMemberId();
	    return memberDao.selectOne(memberId);
	}
	
//	//포인트 증가
//	@PatchMapping("/point/{pointNo}")
//	public ResponseEntity<String> plusMemberPoint(@PathVariable int pointNo) {
//	    int updatedRows = memberDao.plusMemberPoint(pointNo);
//	    if (updatedRows > 0) {
//	        return ResponseEntity.ok().build();
//	    } else {
//	        return ResponseEntity.notFound().build();
//	    }
//	}
	
	
	//포인트 증가
//	@PatchMapping("/")
//	public 
	
	
	  @GetMapping("/points/{pointNo}")
	    public ResponseEntity<?> updateMemberPoints(@PathVariable int pointNo, String memberId) {
	        PointDto pointDto = fareService.selectOne(pointNo, memberId);
	        if (pointDto == null) {
	            return ResponseEntity.notFound().build();  // 포인트 정보가 없을 경우 404 에러 반환
	        }

	        // pointService 내에서 회원 정보가 업데이트 되는 로직이 수행됩니다.
	        return ResponseEntity.ok(pointDto);  // 성공적으로 처리된 경우, 처리된 포인트 정보 반환
	    }
}
