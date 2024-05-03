package com.kh.lucky.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.lucky.dao.SearchDao;
import com.kh.lucky.vo.SearchVO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchRestController {
	
	@Autowired
	private SearchDao searchDao;
	
	//조회 전체
	@GetMapping("/")
	public List<SearchVO> selectList(){
		return searchDao.selectList();
	}
	
	//전체 수정
	@PutMapping("/")
	public ResponseEntity<?> editAll(@RequestBody SearchVO searchVO){
		boolean result = searchDao.editAll(searchVO);
		if(result ==false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	//일부 수정
	@PatchMapping("/")
	public ResponseEntity<?> edit(@RequestBody SearchVO searchVO){
		boolean result = searchDao.edit(searchVO);
		if(result == false) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
}
