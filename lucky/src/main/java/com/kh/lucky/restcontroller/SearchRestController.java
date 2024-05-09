package com.kh.lucky.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.SearchDao;
import com.kh.lucky.vo.SearchResponseVO;
import com.kh.lucky.vo.SearchVO;


@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchRestController {

	@Autowired
	private SearchDao searchDao;

	// 조회 전체
	@PostMapping("/")
	public List<SearchResponseVO> selectList(@RequestBody SearchVO request) {
		System.out.println("컨트롤러에서 확인"+request);
		return searchDao.selectList(request);
	}
	//조회 전체
//	@PostMapping("/")
//	public List<SearchVO> selectList(@RequestBody SearchVO VO){
//		return searchDao.selectList(VO.getStartTerminalId(),VO.getEndTerminalId(),VO.getStartTime(),VO.getEndTime() );
//	}

//	//전체 수정
//	@PutMapping("/")
//	public ResponseEntity<?> editAll(@RequestBody SearchVO searchVO){
//		boolean result = searchDao.editAll(searchVO);
//		if(result ==false) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok().build();
//	}
//	//일부 수정
//	@PatchMapping("/")
//	public ResponseEntity<?> edit(@RequestBody SearchVO searchVO){
//		boolean result = searchDao.edit(searchVO);
//		if(result == false) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok().build();
	
}
